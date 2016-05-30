package activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.flyingbook.R;

import etc.Book;

public class MainActivity extends Activity {

	Button btnShowList, btnRegister, btnMyLibrary, btnMessage, btnRank,
			btnLogout, btnSignUp, btnReturn;

	String U_id;
	ArrayList<Book> bookList;
	ArrayList<Book> rentBookList;
	ArrayList<Book> reservBookList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnShowList = (Button) findViewById(R.id.ButtonShowList);
		btnMyLibrary = (Button) findViewById(R.id.ButtonMyLibrary);
		btnRank = (Button) findViewById(R.id.ButtonRanking);
		btnRegister = (Button) findViewById(R.id.ButtonRegister);
		btnMessage = (Button) findViewById(R.id.ButtonMessage);
		btnReturn = (Button) findViewById(R.id.ButtonReturn);
		btnLogout = (Button) findViewById(R.id.ButtonLogout);
		btnSignUp = (Button) findViewById(R.id.ButtonSignUp);

		Intent intent = getIntent();
		U_id = intent.getExtras().getString("U_id");
		bookList = intent.getExtras().getParcelableArrayList("bookList");

		
		btnShowList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						ShowListActivity.class);
				intent.putParcelableArrayListExtra("bookList", bookList);
				intent.putExtra("U_id", U_id);
				

				startActivity(intent);

			}
		});

		btnMyLibrary.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						MyLibraryActivity.class);
				intent.putParcelableArrayListExtra("rentBookList", rentBookList);
				intent.putParcelableArrayListExtra("reservBookList", reservBookList);
				intent.putExtra("U_id", U_id);
				startActivity(intent);

			}
		});
		btnRank.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this,
						ShowListActivity.class));

			}
		});
		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this,
						ShowListActivity.class));

			}
		});
		btnMessage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				startActivity(new Intent(MainActivity.this,
						ShowListActivity.class));

			}
		});

		btnReturn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				startActivity(new Intent(MainActivity.this,
						ShowListActivity.class));

			}
		});
		btnLogout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						LoginActivity.class);

				startActivity(intent);
				finish();

			}

		});

		btnSignUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this,
						SignUpActivity.class));

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

}
