package com.tcd.yaatra.WifiDirectP2PHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.util.Log;

public class ServiceDiscoveryReceiver extends BroadcastReceiver {

    private static final String TAG = "ServiceDiscoveryReceiver";

    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private ConnectionInfoListener connectionInfoListener;

    /**
     * @param manager  WifiP2pManager system service
     * @param channel  Wifi p2p channel
     * @param connectionInfoListener listener to handle wifi direct events
     */
    public ServiceDiscoveryReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel,
                                    ConnectionInfoListener connectionInfoListener) {
        super();
        this.manager = manager;
        this.channel = channel;
        this.connectionInfoListener = connectionInfoListener;
    }

    /*
     * (non-Javadoc)
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
     * android.content.Intent)
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, action);
        if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

            if (manager == null) {
                return;
            }

            NetworkInfo networkInfo = (NetworkInfo) intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

            if (networkInfo.isConnected()) {

                // we are connected with the other device, request connection
                // info to find group owner IP
                Log.d(TAG,
                        "Connected to p2p network. Requesting network details");
                manager.requestConnectionInfo(channel, connectionInfoListener);
            } else {
                // It's a disconnect
            }
        }

        else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION
                .equals(action)) {

            WifiP2pDevice device = (WifiP2pDevice) intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);
            Log.d(TAG, "Device status -" + device.status);
            manager.requestConnectionInfo(channel, connectionInfoListener);

        }

        else if(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)){

            Log.d(TAG, "Peer Changed");

            WifiP2pDeviceList devices = (WifiP2pDeviceList)intent.getParcelableExtra(WifiP2pManager.EXTRA_P2P_DEVICE_LIST);

        }
    }
}
