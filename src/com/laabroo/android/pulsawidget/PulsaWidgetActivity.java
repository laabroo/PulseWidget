package com.laabroo.android.pulsawidget;

import java.util.Date;

import com.laabroo.android.pulsawidget.service.CallPulse;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

public class PulsaWidgetActivity extends Activity {
	private static final String TAG = "PulsaWidgetActivity";
	private String off = "OFF";
	Button button;
	EditText editText;
	ComponentName service;
	Intent intentService;
	BroadcastReceiver broadcastReceiver;

	private String mStart = "Service let's started.";
	private String mStop = "Service was stoped.";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		editText = (EditText) findViewById(R.id.editConsole);
		intentService = new Intent(this, CallPulse.class);
		service = startService(intentService);

		button = (Button) findViewById(R.id.buttonStop);

		editText.setText(mStart);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				try {
					stopService(new Intent(intentService));
					editText.setText(mStop);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		IntentFilter intentFilter = new IntentFilter("myIntent");
		broadcastReceiver = new LocalReceiver();
		registerReceiver(broadcastReceiver, intentFilter);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			stopService(intentService);
			unregisterReceiver(broadcastReceiver);

		} catch (Exception e) {
			e.printStackTrace();
			Log.i(TAG, e.getMessage());
		}
		Log.i(TAG, off);

	}

	public class LocalReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String serviceData = intent.getStringExtra("serviceData");
			Log.e(TAG, serviceData + " -- " + new Date().toLocaleString());
			String now = "\n" + serviceData + " --- "
					+ new Date().toLocaleString();
			editText.append(now);

		}

	}

}