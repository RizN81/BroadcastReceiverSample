package com.app.broadcastreceiversample.Listeners;

public interface EventListeners {
	void isConnectivityChange(boolean isOn);
	
	void onCall(boolean isCall,String number);
}
