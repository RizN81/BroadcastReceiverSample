package com.app.broadcastreceiversample.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;
import com.app.broadcastreceiversample.Activities.MainActivity;

/*
	This Receiver will notify the connectivity status of user device
 */
public class ConnectivityReceiver extends BroadcastReceiver {
	private static final String TAG = ConnectivityReceiver.class.getName();
	
	@Override
	public void onReceive(Context context , Intent intent) {
		
		Toast.makeText(context , TAG + intent.getAction() , Toast.LENGTH_SHORT).show();
		if ( isOnline(context) ) {
			MainActivity.eventListeners.isConnectivityChange(true);
			Log.d(TAG , "#### Internet is on ####");
		}
		else {
			MainActivity.eventListeners.isConnectivityChange(false);
			Log.d(TAG , "#### Internet is off ####");
		}
	}
	
	/*
		This method will use the connectivity manager to check if internet is available or not
	
	 */
	public boolean isOnline(Context context) {
		
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		assert cm != null;
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		//should check null because in airplane mode it will be null
		return (netInfo != null && netInfo.isConnected());
	}
}
