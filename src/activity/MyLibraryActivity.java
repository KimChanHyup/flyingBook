package activity;

import java.util.ArrayList;

import PageFragment.MyLibraryPageFragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.flyingbook.R;

import etc.Book;

public class MyLibraryActivity extends FragmentActivity implements
		OnClickListener {

	// private Typeface mTypeface;
	private static final int NUMBER_OF_PAGERS = 2;
	public static int REQUEST_ENABLE_INTENT = 0;
	private ViewPager mViewPager;
	private MyFragmentPagerAdapter mMyFragmentPagerAdapter;

	Button btn1;
	Button btn2;
	String U_id;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mylibrary);
		/*
		 * if (mTypeface == null) mTypeface =
		 * Typeface.createFromAsset(getAssets(), "fonts/BMHANNA_11yrs_ttf.ttf");
		 */
		// ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
		// setGlobalFont(root);

		Intent intent = getIntent();
		U_id = intent.getExtras().getString("U_id");

		display();
		setLayout_ListView();

		btn1.setTextColor(Color.RED);

		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(
				getSupportFragmentManager());
		mViewPager.setAdapter(mMyFragmentPagerAdapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				ColorBlack();
				if (position == 0) {
					btn1.setTextColor(Color.RED);
				} else if (position == 1) {
					btn2.setTextColor(Color.RED);
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffest,
					int positionOffsetPixels) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}

		});

	}

	/*
	 * void setGlobalFont(ViewGroup root) { for (int i = 0; i <
	 * root.getChildCount(); i++) { View child = root.getChildAt(i); if (child
	 * instanceof EditText) continue; else if (child instanceof TextView)
	 * ((TextView) child).setTypeface(mTypeface); else if (child instanceof
	 * ViewGroup) setGlobalFont((ViewGroup) child); } }
	 */
	public void ColorBlack() {

		btn1.setTextColor(Color.BLACK);
		btn2.setTextColor(Color.BLACK);
	}

	private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
		ArrayList<Book> rentBookList;
		ArrayList<Book> reservBookList;

		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);

			// TODO Auto-generated constructor stub
			Intent intent = getIntent();
			String U_id = intent.getExtras().getString("U_id");
			rentBookList = intent.getExtras().getParcelableArrayList(
					"rentBookList");
			reservBookList = intent.getExtras().getParcelableArrayList(
					"reservBookList");
			System.out.println("check size rent2 : "+rentBookList.size());
			System.out.println("check size reserv2: "+reservBookList.size());
		}

		@Override
		public Fragment getItem(int index) {
			// TODO Auto-generated method stub
			if (index == 0)
				return MyLibraryPageFragment.newInstance("" + index,
						rentBookList, U_id);
			else
				return MyLibraryPageFragment.newInstance("" + index,
						reservBookList, U_id);
			// else
			// return PageFragment_two.newInstance("My Message " + index);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return NUMBER_OF_PAGERS;
		}
	}

	public static int list_Count = 0;
	public static ArrayAdapter<Book> adapter;

	private void display() {
		btn1 = (Button) findViewById(R.id.btnRent);
		btn2 = (Button) findViewById(R.id.btnReservation);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
	}

	private void setLayout_ListView() {
		adapter = new ArrayAdapter<Book>(this,
				android.R.layout.simple_list_item_1);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btnRent)
			setCurrentInflateItem(0);
		else if (v.getId() == R.id.btnReservation)
			setCurrentInflateItem(1);
	}

	private void setCurrentInflateItem(int type) {
		if (type == 0) {
			mViewPager.setCurrentItem(0);
		} else if (type == 1) {
			mViewPager.setCurrentItem(1);
		}

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
