package com.example.aceriot;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class MyHttpPost {
	protected static final int REFRESH_DATA = 0x00000001;
	private static MyHttpPost instance = null;
	private String res = "";
	private MainActivity ma;

	public static MyHttpPost getInstance() {
		if (instance == null) {
			instance = new MyHttpPost();
		}
		return instance;
	}

	public void SetMainActivity(MainActivity mainActivity) {
		ma = mainActivity;
	}
	
	public void StartHttpPost(String url, List<BasicNameValuePair> params)
	{
		Thread t = new Thread(new sendPostRunnable(url, params));
		t.start();
	}
	
	private String GetPostRes() {

		return res;
		// Intent intent = new Intent();
		// intent.setClass(MainActivity.this,MainMenu.class);
		// startActivity(intent);
		// finish();

	}

	public String httpPOST(String url, List<BasicNameValuePair> params) {
		HttpPost post = new HttpPost(url);
		try {
			// 送出HTTP request

			// List<BasicNameValuePair> postData = new
			// ArrayList<BasicNameValuePair>();
			// postData.add("name="+"1411070");
			// postData.add("password"+);
			// params.get(0).toString();

			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			post.setHeader(HTTP.CONTENT_TYPE,
					"application/x-www-form-urlencoded");
			// post.
			// 取得HTTP response
			HttpResponse httpResponse = new DefaultHttpClient().execute(post);
			// 檢查狀態碼，200表示OK
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// 取出回應字串
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());
				return strResult;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	public Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case REFRESH_DATA :
					String result = null;
					if (msg.obj instanceof String) {
						result = (String) msg.obj;
						res = result;
						String[] serverData = res.split(",");
						
						ma.setFeederWeight(serverData[ServerData.weight]);
						ma.setPetID(serverData[ServerData.petID]);
						//ma.setPetImage(serverData[ServerData.petID]);
						//Toast.makeText(ma, res, Toast.LENGTH_LONG).show();
						// GetPostRes(result);
					}
					if (result != null)
						// Toast.makeText(MainActivity.this, result,
						// Toast.LENGTH_LONG).show();
						break;
			}
		}
	};

}

class sendPostRunnable implements Runnable {
	protected static final int REFRESH_DATA = 0x00000001;
	String uri = null;
	List<BasicNameValuePair> postData;
	// 建構子，設定要傳的字串
	public sendPostRunnable(String url, List<BasicNameValuePair> params) {
		this.uri = url;
		postData = params;
	}
	@Override
	public void run() {
		MyHttpPost mhp = MyHttpPost.getInstance();
		String result = mhp.httpPOST(uri, postData);
		mhp.mHandler.obtainMessage(REFRESH_DATA, result).sendToTarget();
	}
}
