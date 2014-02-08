package com.watch.communitywatch;

import java.util.ArrayList;
import java.util.Collection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.widget.Toast;


public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager mManager;
    private Channel mChannel;
    private MainActivity mActivity;
    PeerListListener myPeerListListener;
    WifiP2pDeviceList peers;
    WifiP2pConfig config;
    WifiP2pDevice device;


    public WiFiDirectBroadcastReceiver(WifiP2pManager manager, Channel channel,
            MainActivity activity) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Check to see if Wi-Fi is enabled and notify appropriate activity
        	int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                //Toast.makeText(context, "Wifi P2P is enabled", 1000);
                mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
        			
        			@Override
        			public void onSuccess() {
        				// TODO Auto-generated method stub
        				
        			}
        			
        			@Override
        			public void onFailure(int reason) {
        				// TODO Auto-generated method stub
        				
        			}
        		});
            } else {
            	Toast.makeText(context, "Wifi P2P is not enabled", 1000);
            }

        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // Call WifiP2pManager.requestPeers() to get a list of current peers
        	if (mManager != null) {
                mManager.requestPeers(mChannel, myPeerListListener);
                myPeerListListener.onPeersAvailable(peers);
                
                ArrayList <WifiP2pDevice> devices = (ArrayList) peers.getDeviceList();
                device = devices.get(0);
               
                config.deviceAddress = device.deviceAddress;
                mManager.connect(mChannel, config, new ActionListener() {

                    @Override
                    public void onSuccess() {
                        //success logic
                    }

                    @Override
                    public void onFailure(int reason) {
                        //failure logic
                    }
                });


        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Respond to new connection or disconnections
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            // Respond to this device's wifi state changing
        }
    }
    }
    }
