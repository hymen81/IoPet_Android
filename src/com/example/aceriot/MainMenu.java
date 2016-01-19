package com.example.aceriot;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

public class MainMenu extends Activity{
	
	// private Button button;
	 //   private Button.OnClickListener listener = new Button.OnClickListener(){

	      //  @Override
	      //  public void onClick(View v) {
	            // TODO Auto-generated method stub
	          //  Intent intent = new Intent();
	          //  intent.setClass(MainActivity2.this, MainActivity.class);
	          //  startActivity(intent);
	          //  finish();
	      //  }
	        
	   // };
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent myIntent = new Intent();
            myIntent = new Intent(MainMenu.this, MainActivity.class);
            startActivity(myIntent);
            this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        // TODO Auto-generated method stub
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main_menu);
	        //button = (Button)findViewById(R.id.button);
	       // button.setOnClickListener(listener );
	    }

}
