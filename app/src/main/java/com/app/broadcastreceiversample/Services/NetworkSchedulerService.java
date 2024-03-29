package com.app.broadcastreceiversample.Services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;
import com.app.broadcastreceiversample.Application.App;
import com.app.broadcastreceiversample.Listeners.EventListeners;
import com.app.broadcastreceiversample.Others.Constants;
import com.app.broadcastreceiversample.Receivers.ConnectivityReceiver;

/**
 * Service to handle callbacks from the JobScheduler. Requests scheduled with the JobScheduler
 * ultimately land on this service's "onStartJob" method.
 * @author jiteshmohite
 */
public class NetworkSchedulerService extends JobService implements
        EventListeners {

    private static final String TAG = NetworkSchedulerService.class.getSimpleName();

    private ConnectivityReceiver connectivityReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service created");
        connectivityReceiver = new ConnectivityReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service destroyed");
    }

    /**
     * When the app's MainActivity is created, it starts this service. This is so that the
     * activity and this service can communicate back and forth. See "setUiCallback()"
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        return START_NOT_STICKY;
    }


    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob" + connectivityReceiver);
        registerReceiver(connectivityReceiver , new IntentFilter(Constants.CONNECTIVITY_ACTION));
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob");
        unregisterReceiver(connectivityReceiver);
        return true;
    }

    @Override
    public void isConnectivityChange(boolean isOn) {
        if (!App.isInterestingActivityVisible()) {
            String message = isOn ? "Good! Connected to Internet" : "Sorry! Not connected to internet";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    public void onCall(boolean isCall , String number) {
    
    }
}
