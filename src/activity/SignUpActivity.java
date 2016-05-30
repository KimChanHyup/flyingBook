package activity;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flyingbook.R;

import etc.Book;
import etc.Util;

//
public class SignUpActivity extends Activity {

	HttpPost httppost;
	HttpResponse response;
	HttpClient httpclient;
	List<NameValuePair> nameValuePairs;
	ProgressDialog dialog = null;

	String checkID;
	Button joinOk, dupcheck;// 중복검사
	EditText edtname, edtid, edtpass, edtPhone, edtAddress;
	private static final String SERVER_ADDRESS = Util.SERVER_ADDRESS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		/*
		 * Bundle bundle = getIntent().getExtras(); Intent intent = getIntent();
		 * 
		 * final Book receiveBook = bundle.getParcelable("parcelableKey");
		 */
		checkID = "";
		
		joinOk = (Button) findViewById(R.id.ButtonSignUp);
		dupcheck = (Button) findViewById(R.id.signIn);
		edtname = (EditText) findViewById(R.id.textName);
		edtid = (EditText) findViewById(R.id.textId);
		edtpass = (EditText) findViewById(R.id.textPassword);
		edtPhone = (EditText) findViewById(R.id.textPhoneNumber);
		edtAddress = (EditText) findViewById(R.id.textAddress);

		joinOk.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// 회원가입버튼 누르고 잠시 기다리는 동안 출력되는 다이얼로그

				join();

			}
		});
		dupcheck.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String id = edtid.getText().toString();

				try {
					httpclient = new DefaultHttpClient();
					httppost = new HttpPost(Util.SERVER_ADDRESS
							+ "/overlapcheck.php");
					nameValuePairs = new ArrayList<NameValuePair>(1);
					nameValuePairs.add(new BasicNameValuePair("id", edtid
							.getText().toString()));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					ResponseHandler<String> responseHandler = new BasicResponseHandler();

					final String response = httpclient.execute(httppost,
							responseHandler);
					checkID = response;
					if (response.equals(edtid.getText().toString())) {
						Toast.makeText(SignUpActivity.this, "중복확인 완료.",
								Toast.LENGTH_LONG).show();

					} else {
						edtid.setText("");

						Toast.makeText(SignUpActivity.this, "이미 존재하는 id입니다.",
								Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					Log.e("Error", e.getMessage());
				}

			}
		});
	}

	void join() {

		if (edtname.getText().toString().equals("")
				|| edtid.getText().toString().equals("")
				|| edtpass.getText().toString().equals("")
				|| edtPhone.getText().toString().equals("")
				|| edtAddress.getText().toString().equals("")) {
			Toast.makeText(SignUpActivity.this, "Enter an error.",
					Toast.LENGTH_LONG).show();
			return;
		}

		if (checkID.equals(edtid.getText().toString())) {
			String name = edtname.getText().toString();
			String id = edtid.getText().toString();
			String password = edtpass.getText().toString();
			String phonenum = edtPhone.getText().toString();
			String address = edtAddress.getText().toString();

			try {
				httpclient = new DefaultHttpClient();
				httppost = new HttpPost(Util.SERVER_ADDRESS + "/signup.php");
				nameValuePairs = new ArrayList<NameValuePair>(5);
				nameValuePairs.add(new BasicNameValuePair("id", edtid.getText()
						.toString()));// ""안에있는게 php, edtid은
										// 아이디
										// 입력창에있는거 받아온거
				nameValuePairs.add(new BasicNameValuePair("password", edtpass
						.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("name", edtname
						.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("phonenum", edtPhone
						.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("address", edtAddress
						.getText().toString()));

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));// 위값들을다
																				// http에
																				// 보내줌
				
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				
				httpclient.execute(httppost, responseHandler);// 다시받아옴
				checkID = "";
				Toast.makeText(this, "회원가입 완료", Toast.LENGTH_LONG).show();
				finish();
			} catch (Exception e) {
				dialog.dismiss();
				Log.e("Error", e.getMessage());
			}
		} else {
			Toast.makeText(this, "중복확인을 해주세요.", Toast.LENGTH_LONG).show();
		}

	}

	/*
	 * else { edtname.setText(""); edtid.setText(""); edtpass.setText("");
	 * edtPhone.setText(""); edtAddress.setText("");
	 * 
	 * Toast.makeText(JoinActivity.this, "정보를 모두 입력해주세요!",
	 * Toast.LENGTH_LONG).show(); }
	 */

	

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
}