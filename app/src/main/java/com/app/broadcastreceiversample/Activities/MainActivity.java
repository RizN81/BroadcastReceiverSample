package com.app.broadcastreceiversample.Activities;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.app.broadcastreceiversample.Listeners.EventListeners;
import com.app.broadcastreceiversample.Others.Constants;
import com.app.broadcastreceiversample.R;
import com.app.broadcastreceiversample.Services.NetworkSchedulerService;
import com.tapadoo.alerter.Alerter;

public class MainActivity extends AppCompatActivity implements EventListeners {
	TextView txtNetwork, txtCall;
	public static EventListeners eventListeners;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		eventListeners = this;
		permissions();
		txtNetwork = findViewById(R.id.txtNetwork);
		txtCall = findViewById(R.id.txtCall);
		scheduleJob();
	}
	
	private void permissions() {
		
		requestPermissions(new String[] {"android.permission.INTERNET" , "android.permission.ACCESS_WIFI_STATE" , "android.permission.ACCESS_NETWORK_STATE" , "android.permission.CALL_PHONE"} , Constants.PERMISSION_CODE);
	}
	
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	private void scheduleJob() {
		
		JobInfo myJob = new JobInfo.Builder(0 , new ComponentName(this , NetworkSchedulerService.class))
				.setRequiresCharging(true)
				.setMinimumLatency(1000)
				.setOverrideDeadline(2000)
				.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
				.setPersisted(true)
				.build();
		
		JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
		assert jobScheduler != null;
		jobScheduler.schedule(myJob);
	}
	
	@Override
	protected void onStop() {
		// A service can be "started" and/or "bound". In this case, it's "started" by this Activity
		// and "bound" to the JobScheduler (also called "Scheduled" by the JobScheduler). This call
		// to stopService() won't prevent scheduled jobs to be processed. However, failing
		// to call stopService() would keep it alive indefinitely.
		stopService(new Intent(this , NetworkSchedulerService.class));
		super.onStop();
	}
	
	@Override
	protected void onStart() {
		
		super.onStart();
		// Start service and provide it a way to communicate with this class.
		Intent startServiceIntent = new Intent(this , NetworkSchedulerService.class);
		startService(startServiceIntent);
	}
	
	@Override
	public void isConnectivityChange(boolean isOn) {
		
		if ( isOn ) {
			txtNetwork.setText("Internet Is On");
			Alerter.create(this)
					.setTitle(R.string.app_name)
					.setText("Internet Is On")
					.setBackgroundColorRes(R.color.network)
					.setIcon(R.drawable.ic_network_wifi_black_24dp)
					.setIconColorFilter(0)
					.show();
		}
		else {
			txtNetwork.setText("Internet Is Off");
			Alerter.create(this)
					.setTitle(R.string.app_name)
					.setText("Internet Is Off")
					.setBackgroundColorRes(R.color.no_network)
					.setIcon(R.drawable.ic_signal_wifi_off_black_24dp)
					.setIconColorFilter(0)
					.show();
		}
	}
	
	@Override
	public void onCall(boolean isCall , String number) {
		
		if ( isCall ) {
			txtCall.setText("Call Received or Made [ " + number + " ]");
			Alerter.create(this)
					.setTitle(R.string.app_name)
					.setText("Call Received or Made [ " + number + " ]")
					.setBackgroundColorRes(R.color.incoming_call)
					.setIcon(R.drawable.ic_call_black_24dp)
					.setIconColorFilter(0)
					.show();
		}
		else {
			txtCall.setText("No Call Received or Made");
		}
	}
}
