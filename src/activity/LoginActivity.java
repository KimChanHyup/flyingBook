package activity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.flyingbook.R;

import etc.Book;
import etc.Util;

public class LoginActivity extends Activity {
	Util util;
	Button btnjoin, btnlogin;
	EditText inputID, inputPW;

	HttpPost httppost;
	HttpResponse response;
	HttpClient httpclient;
	List<NameValuePair> nameValuePairs;
	ProgressDialog dialog = null;

	ArrayList<Book> bookList = new ArrayList<Book>();
	ArrayList<Book> rentBookList = new ArrayList<Book>();
	ArrayList<Book> reservBookList = new ArrayList<Book>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		util = new Util();

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy); // 강제적으로 네트워크 접속

		btnjoin = (Button) findViewById(R.id.signUp);
		btnlogin = (Button) findViewById(R.id.signIn);
		inputID = (EditText) findViewById(R.id.textId);
		inputPW = (EditText) findViewById(R.id.textPassword);

		btnjoin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(LoginActivity.this,
						SignUpActivity.class));
			}
		}); // 회원가입 화면 Intent 처리

		btnlogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog = ProgressDialog.show(LoginActivity.this, "",
						"잠시만 기다려주세요...", true);
				// 로그인버튼 누르고 잠시 기다리는 동안 출력되는 다이얼로그
				new Thread(new Runnable() {
					public void run() {
						Looper.prepare();
						login();
						Looper.loop();
					}
				}).start();
			}
		});
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

	void login() {
		// TODO Auto-generated method stub
		try {
			httpclient = new DefaultHttpClient();
			httppost = new HttpPost(Util.SERVER_ADDRESS + "logcheck.php");

			nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("id", inputID.getText()
					.toString()));
			nameValuePairs.add(new BasicNameValuePair("password", inputPW
					.getText().toString()));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// response = httpclient.execute(httppost);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();

			final String response = httpclient.execute(httppost,
					responseHandler);
			System.out.println(response);
			runOnUiThread(new Runnable() {
				public void run() {
					dialog.dismiss();
				}
			});

			if (response.equalsIgnoreCase("User Found")) {

				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(LoginActivity.this, "로그인에 성공하였습니다!",
								Toast.LENGTH_LONG).show();
						// 로그인에 성공하면 토스트메시지 출력하고
						dialog = ProgressDialog.show(LoginActivity.this, "",
								"서버에서 데이터를 불러오고 있습니다...", true);

					}
				});
				// data가져오기

				AQuery aquery = new AQuery(getBaseContext());

				Map<String, String> params = new HashMap<String, String>();
				// params.put("User_ID", inputID.getText().toString());

				final String sendRegIdUrl = Util.SERVER_ADDRESS
						+ "booklist.php";
				aquery.ajax(sendRegIdUrl, params, String.class,
						new AjaxCallback<String>() {

							@Override
							public void callback(String url, String result,
									AjaxStatus status) {
								super.callback(url, result, status);
								try {
									JSONObject obj = new JSONObject(result);
									JSONArray arr = obj.getJSONArray("book");
									setList(bookList, arr);
								} catch (JSONException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								return;
							}
						});

				// my rent 도서 data가져오기

				aquery = new AQuery(getBaseContext());

				params = new HashMap<String, String>();
				params.put("U_id", inputID.getText().toString());

				final String sendRegIdUrl2 = Util.SERVER_ADDRESS
						+ "rentlist.php";
				aquery.ajax(sendRegIdUrl2, params, String.class,
						new AjaxCallback<String>() {

							@Override
							public void callback(String url, String result,
									AjaxStatus status) {
								super.callback(url, result, status);
								try {
									JSONObject obj = new JSONObject(result);
									JSONArray arr = obj
											.getJSONArray("rentBook");
									setList(rentBookList, arr);
								} catch (JSONException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								return;
							}
						});
				// my reserv data가져오기

				aquery = new AQuery(getBaseContext());

				params = new HashMap<String, String>();
				params.put("U_id", inputID.getText().toString());

				final String sendRegIdUrl3= Util.SERVER_ADDRESS
						+ "reservlist.php";
				aquery.ajax(sendRegIdUrl3, params, String.class,
						new AjaxCallback<String>() {

							@Override
							public void callback(String url, String result,
									AjaxStatus status) {
								super.callback(url, result, status);
								try {
									System.out.println(result);
									JSONObject obj = new JSONObject(result);
									JSONArray arr = obj
											.getJSONArray("reservBook");
									setList(reservBookList, arr);
								} catch (JSONException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								return;
							}
						});

				// 로그인 성공시 다음화면으로 넘어감!
				Thread.sleep(5000);
				Intent intent = new Intent(this, MainActivity.class);
				System.out.println("check login : " + bookList.size());
				intent.putParcelableArrayListExtra("bookList", this.bookList);
				intent.putParcelableArrayListExtra("rentBookList", this.rentBookList);
				intent.putParcelableArrayListExtra("reservBookList", this.reservBookList);
				intent.putExtra("U_id", inputID.getText().toString());
				
				
				intent.putParcelableArrayListExtra("rentBookList", rentBookList);
				intent.putParcelableArrayListExtra("reservBookList", reservBookList);
				runOnUiThread(new Runnable() {
					public void run() {
						dialog.dismiss();
					}
				});
				startActivity(intent);
				finish();
			} else {
				Toast.makeText(LoginActivity.this, "로그인에 실패하셨습니다!",
						Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			dialog.dismiss();
			System.out.println("Exception: " + e.toString());
		}
	}

	public void setList(ArrayList<Book> List, JSONArray arr)
			throws JSONException, IOException, ParseException {
		for (int i = 0; i < arr.length(); i++) {
			Book book = new Book();

			JSONObject obj2 = (JSONObject) arr.get(i);
			book.setName(obj2.get("Book_name").toString());
			book.setDescription(obj2.get("Book_description").toString());
			book.setAuthor(obj2.get("Book_author").toString());
			book.setRentCount(Integer.parseInt(obj2.get("Book_rentCount")
					.toString()));
			book.setCategory(obj2.get("Book_category").toString());

			book.setUpdateDay(obj2.get("Book_updateDay").toString());
			book.setImage_name(obj2.get("Book_image").toString());
			book.setImage(null);
			/*
			 * URL Url = new URL(Util.SERVER_ADDRESS + "image/" +
			 * obj2.get("Book_image").toString()); URLConnection conn =
			 * Url.openConnection(); conn.connect(); BufferedInputStream bis =
			 * new BufferedInputStream( conn.getInputStream()); Bitmap bm =
			 * BitmapFactory.decodeStream(bis); bis.close(); bm = imgResize(bm);
			 * book.setImage(bm);
			 */
			List.add(book);
		}
	}

	public Bitmap imgResize(Bitmap bitmap) {
		int x = 70, y = 70; // 바꿀 이미지 사이즈
		Bitmap output = Bitmap.createBitmap(x, y, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		canvas.drawBitmap(bitmap, 0, 0, null);
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Rect src = new Rect(0, 0, w, h);
		Rect dst = new Rect(0, 0, x, y);// 이 크기로 변경됨
		canvas.drawBitmap(bitmap, src, dst, null);
		return output;
	}
}
