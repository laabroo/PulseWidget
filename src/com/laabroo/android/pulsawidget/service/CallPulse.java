package com.laabroo.android.pulsawidget.service;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

public class CallPulse extends Service {
	private static final String TAG = "CallPulse";
	private String ConditionOff = "I'am Death";
	private String ConditionOn = "I'am alive";
	int Sleep = 1000;
	boolean isRunning = true;

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, ConditionOn);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, ConditionOff);
		isRunning = false;

	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					Intent filteredRespond = new Intent("myIntent");
					String data = pulseValue("*888#");
					Log.i(TAG, data);
					filteredRespond.putExtra("serviceData", data);
					sendBroadcast(filteredRespond);

					Thread.sleep(Sleep);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		});

		thread.start();
	}

	private String pulseValue(String number) {
		checPulse(number);
		return number;

	}

	private void checPulse(String number) {
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse(number));

	}

}
