package com.example.aceriot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Build;

import com.example.aceriot.MyHttpPost;
//import com.example.aceriot.MyHttpPost.sendPostRunnable;

//import org.alljoyn.ioe.controlpanelbrowser.DeviceDetailFragment.DeviceDetailCallback;
//import org.alljoyn.ioe.controlpanelbrowser.DeviceList.DeviceContext;

public class MainActivity extends Activity implements OnClickListener {

	private Button login;
	private EditText bowlID, pass;
	private Spinner weigths;
	private TextView feederWeight;
	private TextView petID;
	
	private ImageView petImage;

	private String[] weightList = {"100g", "150g", "200g", "250g", "300g"};

	private Handler timerHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
				case 1 :
					//Toast.makeText(MainActivity.this, "PostEnd",
						//	Toast.LENGTH_SHORT).show();
					break;
				default :
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		login = (Button) findViewById(R.id.buttonLogin);
		bowlID = (EditText) findViewById(R.id.bowlID);
		
		// pass = (EditText)findViewById(R.id.pass);
		weigths = (Spinner) findViewById(R.id.weigth);
		feederWeight = (TextView) findViewById(R.id.feederWeight);
		petID = (TextView) findViewById(R.id.PetID);
		petImage = (ImageView) findViewById(R.id.imageView1);
		login.setOnClickListener(this);

		ArrayAdapter<String> lunchList = new ArrayAdapter<String>(
				MainActivity.this, android.R.layout.simple_spinner_item,
				weightList);
		weigths.setAdapter(lunchList);
		
		Resources res = getResources();
		Drawable drawable = res.getDrawable(R.drawable.bkcolor);
		this.getWindow().setBackgroundDrawable(drawable);

		// timer.schedule(task, 1000);
		timerHandler.postDelayed(myRunnable, 1000);
	
	}

	@Override
	public void onDestroy() {
		// mHandlerTime.removeCallbacks(timerRun);
		if (timerHandler != null) {
			timerHandler.removeCallbacks(myRunnable);
		}
		super.onDestroy();
	}
	
	private Runnable myRunnable = new Runnable() {
		public void run() {
			List<BasicNameValuePair> postData = new ArrayList<BasicNameValuePair>();
			postData.add(new BasicNameValuePair("device", "android"));
			postData.add(new BasicNameValuePair("name", bowlID.getText()
					.toString()));
			//postData.add(new BasicNameValuePair("getWeight", "true"));
			
			
			// postData.add(new BasicNameValuePair("password",
			// pass.getText().toString()));
			// Log.d("BL", "Acc:"+acc.toString());
			// Log.d("BL", "Pass:"+pass.toString());
			MyHttpPost myHttpPost = MyHttpPost.getInstance();
			myHttpPost.SetMainActivity(MainActivity.this);

			String url = "http://iopet543.appspot.com/";
			myHttpPost.StartHttpPost(url, postData);

			timerHandler.postDelayed(this, 1000);

			Message message = new Message();
			message.what = 1;
			timerHandler.sendMessage(message);
		}
	};

	@Override
	public void onClick(View view) {
		List<BasicNameValuePair> postData = new ArrayList<BasicNameValuePair>();
		postData.add(new BasicNameValuePair("device", "android"));
		postData.add(new BasicNameValuePair("name", bowlID.getText().toString()));
		postData.add(new BasicNameValuePair("weight", weigths.getSelectedItem()
				.toString()));
		postData.add(new BasicNameValuePair("startFeed", "true"));
		// postData.add(new BasicNameValuePair("password",
		// pass.getText().toString()));
		// Log.d("BL", "Acc:"+acc.toString());
		// Log.d("BL", "Pass:"+pass.toString());
		MyHttpPost myHttpPost = MyHttpPost.getInstance();
		myHttpPost.SetMainActivity(this);

		String url = "http://iopet543.appspot.com/";
		myHttpPost.StartHttpPost(url, postData);
		//Intent intent = new Intent();
		//intent.setClass(MainActivity.this, MainMenu.class);
		//startActivity(intent);
		//finish();

	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void setFeederWeight(String s) {
		feederWeight.setText(s);
	}
	
	public void setPetID(String s)
	{
		petID.setText(s);
		setPetImage(s);
	}
	
	private void setPetImage(String s)
	{
		//File imgFile=new File("file:///storage/emulated/0/ioPet/"+s+".jpg");	
		//if(imgFile.exists()){
			Bitmap b = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/ioPet/"+s+".jpg");
		   // Bitmap petBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());		 		   
		    petImage.setImageBitmap(b);
		//}
		
	}

}
