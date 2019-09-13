package com.app.broadcastreceiversample.Receivers;

import java.util.Objects;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import com.app.broadcastreceiversample.Activities.MainActivity;

/*
	This Receiver Will Notify About The Incoming Calls
 */
public class CallReceiver extends BroadcastReceiver {
	private static final String TAG = CallReceiver.class.getName();
	
	@Override
	public void onReceive(Context context , Intent intent) {
		
		Toast.makeText(context , TAG + intent.getAction() , Toast.LENGTH_SHORT).show();
		String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
		assert state != null;
		if ( state.equals(TelephonyManager.EXTRA_STATE_RINGING) ) {
			String number = Objects.requireNonNull(intent.getExtras()).getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
			MainActivity.eventListeners.onCall(true , number);
		}
		else {
			MainActivity.eventListeners.onCall(false , "");
		}
	}
}
