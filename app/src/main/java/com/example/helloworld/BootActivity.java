package com.example.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.helloworld.api.Service;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BootActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_boot);


	}

	@Override
	protected void onResume() {
		super.onResume();

//		Handler handler = new Handler();
//		handler.postDelayed(new Runnable() {
//			private int abcd = 0;
//
//			public void run() {
//				startLoginActivity();
//			}
//		}, 1000);

		OkHttpClient client = Service.getShareClient();

		//请求对象
//		Request request = new Request.Builder()
//				.url("http://172.27.0.51:8080/membercenter/api/hello")
		Request request = Service.requestBuilderWithApi("hello")
				.method("GET",null)
				.build();

		//enqueue是入队的意思，enqueue（）是将request请求加入队列中等待执行
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, final IOException e) {
				BootActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(BootActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
					}
				});
			}

			@Override
			public void onResponse(Call call, final Response response) throws IOException {
				Log.d("response", response.toString());

				BootActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						try {
							Toast.makeText(BootActivity.this, response.body().string(), Toast.LENGTH_SHORT).show();
						} catch (IOException e) {
							e.printStackTrace();
						}
						startLoginActivity();
					}
				});
			}
		});
	}

	void startLoginActivity(){
		Intent itnt = new Intent(this, LoginActivity.class);
		startActivity(itnt);
		finish();
	}
}
