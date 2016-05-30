package activity;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
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

import adapter.MyCommentAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flyingbook.R;

import etc.Book;
import etc.Comment;
import etc.Util;
import java.util.Calendar;

public class BookInfoActivity extends Activity {

	HttpPost httppost;
	HttpResponse response;
	HttpClient httpclient;
	List<NameValuePair> nameValuePairs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bookinfo);

		Bundle bundle = getIntent().getExtras();

		//Intent intent = getIntent();

		final String U_id = bundle.getString("U_id");
		System.out.println("bookInfo: "+ U_id);
		final Book receiveBook = bundle.getParcelable("sendBook");

		ImageView image = (ImageView) findViewById(R.id.ImageView);
		TextView bookName = (TextView) findViewById(R.id.bookName);
		TextView bookAuthor = (TextView) findViewById(R.id.bookAuthor);
		TextView bookDescription = (TextView) findViewById(R.id.bookDescription);
		TextView bookUpdate = (TextView) findViewById(R.id.bookupdate);
		Button btnComment = (Button) findViewById(R.id.btnComment);

		bookName.setText("책 이름 : " + receiveBook.getName());
		bookAuthor.setText("저자 : " + receiveBook.getAuthor());
		bookDescription.setText(receiveBook.getDescription());
		bookUpdate.setText(receiveBook.getUpdateDay().toString());
		

		final ArrayList<Comment> midList = new ArrayList<Comment>();
		ListView list = (ListView) findViewById(R.id.comments);

		final MyCommentAdapter adapter = new MyCommentAdapter(this, midList);
		list.setAdapter(adapter);

		try { // 이미지 넣기

			httpclient = new DefaultHttpClient();
			httppost = new HttpPost(Util.SERVER_ADDRESS + "image.php");

			nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("bookName", receiveBook
					.getName().toString()));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
			response = httpclient.execute(httppost);

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String response = httpclient.execute(httppost, responseHandler);

			URL url = new URL(Util.SERVER_ADDRESS + "image/" + response);
			URLConnection conn = url.openConnection();
			conn.connect();

			BufferedInputStream bis = new BufferedInputStream(
					conn.getInputStream());
			Bitmap bm = BitmapFactory.decodeStream(bis);
			bis.close();

			image.setImageBitmap(bm);

		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}

		try { // 후기 가져오기

			httpclient = new DefaultHttpClient();
			httppost = new HttpPost(Util.SERVER_ADDRESS + "Comment.php");

			nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("bookName", receiveBook
					.getName().toString()));
			System.out.println(receiveBook.getName().toString());

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			final String response = httpclient.execute(httppost,
					responseHandler);
			System.out.println(response);
			getXmlData("Comment.xml", midList, "comment", "U_id", "grade");
			adapter.notifyDataSetChanged();

		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}

		btnComment.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LayoutInflater inflater = LayoutInflater
						.from(BookInfoActivity.this);
				AlertDialog.Builder builder = new AlertDialog.Builder(
						BookInfoActivity.this);
				builder.setTitle(null);
				View customDialogView = inflater.inflate(
						R.layout.layout_comment_dialog, null, false);
				final RatingBar ratingBar = (RatingBar) customDialogView
						.findViewById(R.id.ratingBar);
				ratingBar.setStepSize((float) 0.5);
				ratingBar.setRating((float) 2.5);

				ratingBar
						.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

							@Override
							public void onRatingChanged(RatingBar ratingBar,
									float rating, boolean fromUser) {
								// TODO Auto-generated method stub

							}
						});
				final EditText editText = (EditText) customDialogView
						.findViewById(R.id.comment);
				Button btnComplete = (Button) customDialogView // 입력버튼
						.findViewById(R.id.complete);

				builder.setView(customDialogView);

				final AlertDialog mAlertDialog = builder.create();
				mAlertDialog.show();

				btnComplete.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						try {
							httpclient = new DefaultHttpClient();
							httppost = new HttpPost(Util.SERVER_ADDRESS
									+ "inputComment.php");
							
							nameValuePairs = new ArrayList<NameValuePair>(4);
							nameValuePairs.add(new BasicNameValuePair(
									"book_name", receiveBook.getName()
											.toString()));
							nameValuePairs.add(new BasicNameValuePair(
									"grade_star", "" + ratingBar.getRating()));
							nameValuePairs.add(new BasicNameValuePair(
									"comment", editText.getText().toString()));
							nameValuePairs.add(new BasicNameValuePair("U_id",
									U_id.toString()));
							
							httppost.setEntity(new UrlEncodedFormEntity(
									nameValuePairs,"utf-8"));
							
							// response = httpclient.execute(httppost);
							ResponseHandler<String> responseHandler = new BasicResponseHandler();
							final String response = httpclient.execute(
									httppost, responseHandler);
							
							Calendar calendar = Calendar.getInstance();

							Comment comment = new Comment();
							comment.setId(U_id.toString());
							comment.setGradeStar(ratingBar.getRating());
							comment.setText(editText.getText().toString());
							comment.setDate(calendar.get(calendar.YEAR) + "-"
									+ (calendar.get(calendar.MONTH) + 1) + "-"
									+ calendar.get(calendar.DAY_OF_MONTH));
							
							midList.add(0, comment);
							adapter.notifyDataSetChanged();
						
							Toast.makeText(getApplicationContext(), "후기작성 완료.",
									Toast.LENGTH_LONG).show();

						} catch (Exception e) {
							System.out.println("Exception: " + e.toString());
						}
						mAlertDialog.dismiss();
					}
				});
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

	private void getXmlData(String filename, ArrayList<Comment> midList,
			String str1, String str2, String str3) {

		String rss = Util.SERVER_ADDRESS + "xml/";

		try {// XML 파싱을 위한 과정
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			URL server = new URL(rss + filename);
			InputStream is = server.openStream();
			xpp.setInput(is, "UTF-8");

			int eventType = xpp.getEventType();
			Comment comment = new Comment();
			// XML 문서가 끝날때 까지 반복 작업합니다.
			while (eventType != XmlPullParser.END_DOCUMENT) {
				/*
				 * <tag> : START_TAG content : TEXT </tag> : END_TAG
				 */

				// 시작태그를 만났을 경우
				if (eventType == XmlPullParser.START_TAG) {
					if (xpp.getName().equals(str1)) {
						comment.setText(xpp.nextText());
					} else if (xpp.getName().equals(str2)) {
						comment.setId(xpp.nextText());
					} else if (xpp.getName().equals(str3)) {
						comment.setGradeStar(Float.parseFloat(xpp.nextText()));
						midList.add(comment);
						// 새로운 객체를 만든다.
						comment = new Comment();
					}

				}

				eventType = xpp.next();
			}
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
		}
	}
}