package com.example.aceriot;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class Welcom extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		findViewById(R.id.button).setOnClickListener(this);
	}

	@Override
	public void onDestroy() {
		// mHandlerTime.removeCallbacks(timerRun);
		super.onDestroy();
	}

	@Override
	public void onClick(View view) {
		//Intent intent = new Intent();
		Intent intent = new Intent(Welcom.this,MainActivity.class);
		startActivity(intent);
		
	}

}
