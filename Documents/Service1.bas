Type=Service
Version=2.71
@EndOfDesignText@
#Region  Service Attributes 
	#StartAtBoot: False
#End Region

Sub Process_Globals
	Private manager As WifiManager
	Private discoveredDevices As List
	Private server As ServerSocket
	Private client As Socket
	Private port As Int = 13138
	Private astream As AsyncStreams
	Private directConnected As Boolean
	Private groupIp As String
	Public lblEnabledText, lblConnectedText, lblDevicesText As String
	Public btnSendHelloEnabled = False, btnConnectEnabled = True As Boolean
End Sub
Sub Service_Create
	server.Initialize(port, "server")
	manager.Initialize("manager")
End Sub

Sub Manager_EnabledChanged (Enabled As Boolean)
	Log("Enabled: " & Enabled)
	lblEnabledText = Enabled
	UpdateUI
End Sub
Public Sub Connect
	lblConnectedText = "Trying to connect..."
	If directConnected = False Then
		'create the p2p connection
		If discoveredDevices.IsInitialized = False OR discoveredDevices.Size = 0 Then 
			ToastMessageShow("No peered devices.", True)
			Return
		End If
		Dim dev As WifiDevice = discoveredDevices.Get(0)
		manager.Connect(dev.MacAddress)
	Else
		If astream.IsInitialized Then Return 'there is already a network connection
		'there is already a p2p connection
		ConnectNetwork
	End If
	UpdateUI
End Sub

Public Sub Discover
	lblDevicesText = "Searching for devices"
	manager.DiscoverPeers
	UpdateUI
End Sub

Sub Manager_PeersDiscovered (Success As Boolean, Devices As List)
	Log("peers: " & Success)
	If Success Then
		discoveredDevices = Devices
		lblDevicesText = Devices.Size
	Else
		lblDevicesText = "0"
	End If
	UpdateUI
End Sub

Public Sub SendMessage(Message As String)
	astream.Write(Message.GetBytes("UTF8"))
End Sub

Sub Manager_ConnectionChanged (Connected As Boolean, GroupOwnerIp As String)
	directConnected = Connected
	groupIp = GroupOwnerIp
	If Connected Then
		lblConnectedText = "Wifi Direct connected (" & GroupOwnerIp & ")"
		ConnectNetwork
	Else
		lblConnectedText = "false"
	End If
	btnConnectEnabled = Not(Connected)
	UpdateUI
End Sub

Sub ConnectNetwork
	If groupIp = "127.0.0.1" Then
		server.Listen
	Else
		Log("trying to connect to server socket.")
		client.Initialize("client")
		client.Connect(groupIp, port, 10000)
	End If
End Sub



Sub client_Connected (Successful As Boolean)
	If Successful Then
		StartAsyncStreams(client)
	Else
		lblConnectedText = "client failed to connect."
		btnConnectEnabled = True
		UpdateUI
	End If
End Sub

Sub server_NewConnection (Successful As Boolean, NewSocket As Socket)
	Log("server_NewConnection " & Successful)
	If Successful Then
		StartAsyncStreams(NewSocket)
	End If
	server.Listen
End Sub

Private Sub UpdateUI
	CallSub(Main, "UpdateUI")
End Sub

Sub StartAsyncStreams(s As Socket)
	Log("AsyncStreams started")
	astream.InitializePrefix(s.InputStream, False,  s.OutputStream, "astreams")
	btnSendHelloEnabled = True
	btnConnectEnabled = False
	lblConnectedText = "Sockets are connected :-)"
	UpdateUI
End Sub

Sub astreams_NewData (Buffer() As Byte)
	ToastMessageShow("Message from other device: " & BytesToString(Buffer, 0, Buffer.Length, "UTF8"), True)
End Sub

Sub astreams_Error
	Log("astreams_Error")
	ToastMessageShow(LastException, True)
	Log(LastException)
	astream.Close
	astreams_Terminated
End Sub

Sub astreams_Terminated
	Log("astreams_Terminated")
	btnSendHelloEnabled = False
	btnConnectEnabled = True
	lblConnectedText = "Sockets disconnected"
	UpdateUI
End Sub

Sub Service_Start (StartingIntent As Intent)

End Sub

Sub Service_Destroy

End Sub
