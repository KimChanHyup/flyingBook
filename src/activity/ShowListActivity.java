package activity;

import java.util.ArrayList;

import PageFragment.ShowListPageFragment;
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

public class ShowListActivity extends FragmentActivity implements
		OnClickListener {

	// private Typeface mTypeface;

	private static final int NUMBER_OF_PAGERS = 5;
	public static int REQUEST_ENABLE_INTENT = 0;
	private ViewPager mViewPager;
	private MyFragmentPagerAdapter mMyFragmentPagerAdapter;

	boolean ASC = true;
	boolean Popular = false;
	boolean Newest = false;

	Button btn1;
	Button btn2;
	Button btn3;
	Button btn4;
	Button btn5;
	Button btnASC;
	Button btnPopular;
	Button btnNewest;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sholist);

		/*
		 * if (mTypeface == null) mTypeface =
		 * Typeface.createFromAsset(getAssets(), "fonts/BMHANNA_11yrs_ttf.ttf");
		 */
		// ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
		// setGlobalFont(root);

		Intent intent = getIntent();
		final String U_id = intent.getExtras().getString("U_id");
		

		display();
		setLayout_ListView();

		btn1.setTextColor(Color.RED);
		btnASC.setTextColor(Color.RED);

		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(
				getSupportFragmentManager());
		mViewPager.setOffscreenPageLimit(NUMBER_OF_PAGERS);
		mViewPager.setAdapter(mMyFragmentPagerAdapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				ColorBlack();
				if (position == 0) {
					btn1.setTextColor(Color.RED);
				} else if (position == 1) {
					btn2.setTextColor(Color.RED);
				} else if (position == 2) {
					btn3.setTextColor(Color.RED);
				} else if (position == 3) {
					btn4.setTextColor(Color.RED);
				} else if (position == 4) {
					btn5.setTextColor(Color.RED);
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
		btn3.setTextColor(Color.BLACK);
		btn4.setTextColor(Color.BLACK);
		btn5.setTextColor(Color.BLACK);
	}

	private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
		Intent intent = getIntent();
		final String U_id = intent.getExtras().getString("U_id");
		
		ArrayList<Book> bookList;
		ArrayList<Book> comicBookList;
		ArrayList<Book> romanceBookList;
		ArrayList<Book> sportsBookList;
		ArrayList<Book> actionBookList;
		ArrayList<Book> inferBookList;

		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);

			// TODO Auto-generated constructor stub
			Intent intent = getIntent();
			bookList = intent.getExtras().getParcelableArrayList("bookList");

			comicBookList = new ArrayList<Book>();
			romanceBookList = new ArrayList<Book>();
			sportsBookList = new ArrayList<Book>();
			actionBookList = new ArrayList<Book>();
			inferBookList = new ArrayList<Book>();

			for (int i = 0; i < bookList.size(); i++) {
				if (bookList.get(i).getCategory().equals("comic")) {
					comicBookList.add(bookList.get(i));
				} else if (bookList.get(i).getCategory().equals("romance")) {
					romanceBookList.add(bookList.get(i));
				} else if (bookList.get(i).getCategory().equals("sports")) {
					sportsBookList.add(bookList.get(i));
				} else if (bookList.get(i).getCategory().equals("action")) {
					actionBookList.add(bookList.get(i));
				} else if (bookList.get(i).getCategory().equals("infer")) {
					inferBookList.add(bookList.get(i));
				}

			}

		}

		@Override
		public Fragment getItem(int index) {
			// TODO Auto-generated method stub
			String categoryList = null;
			if (index == 0) {
				return ShowListPageFragment.newInstance("" + index,
						comicBookList, U_id);
			} else if (index == 1) {
				return ShowListPageFragment.newInstance("" + index,
						romanceBookList, U_id);
			} else if (index == 2) {
				return ShowListPageFragment.newInstance("" + index,
						sportsBookList, U_id);
			} else if (index == 3) {
				return ShowListPageFragment.newInstance("" + index,
						actionBookList, U_id);
			} else {
				return ShowListPageFragment.newInstance("" + index,
						inferBookList, U_id);
			}
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
		btn1 = (Button) findViewById(R.id.btn01);
		btn2 = (Button) findViewById(R.id.btn02);
		btn3 = (Button) findViewById(R.id.btn03);
		btn4 = (Button) findViewById(R.id.btn04);
		btn5 = (Button) findViewById(R.id.btn05);

		btnASC = (Button) findViewById(R.id.btnASC);
		btnPopular = (Button) findViewById(R.id.btnPopular);
		btnNewest = (Button) findViewById(R.id.btnNewest);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);

		btnASC.setOnClickListener(this);
		btnPopular.setOnClickListener(this);
		btnNewest.setOnClickListener(this);
	}

	private void setLayout_ListView() {
		adapter = new ArrayAdapter<Book>(this,
				android.R.layout.simple_list_item_1);

	}

	@Override
	public void onClick(View v) {

		// TODO Auto-generated method stub
		if (v.getId() == R.id.btn01)
			setCurrentInflateItem(0);
		else if (v.getId() == R.id.btn02)
			setCurrentInflateItem(1);
		else if (v.getId() == R.id.btn03)
			setCurrentInflateItem(2);
		else if (v.getId() == R.id.btn04)
			setCurrentInflateItem(3);
		else if (v.getId() == R.id.btn05)
			setCurrentInflateItem(4);
		else if (v.getId() == R.id.btnASC) {
			btnASC.setTextColor(Color.RED);
			btnPopular.setTextColor(Color.BLACK);
			btnNewest.setTextColor(Color.BLACK);
			ASC = true;
			Popular = false;
			Newest = false;
		} else if (v.getId() == R.id.btnPopular) {
			btnASC.setTextColor(Color.BLACK);
			btnPopular.setTextColor(Color.RED);
			btnNewest.setTextColor(Color.BLACK);
			ASC = false;
			Popular = true;
			Newest = false;
		} else if (v.getId() == R.id.btnNewest) {
			btnASC.setTextColor(Color.BLACK);
			btnPopular.setTextColor(Color.BLACK);
			btnNewest.setTextColor(Color.RED);
			ASC = false;
			Popular = false;
			Newest = true;
		}

	}

	private void setCurrentInflateItem(int type) {
		if (type == 0) {
			mViewPager.setCurrentItem(0);
		} else if (type == 1) {
			mViewPager.setCurrentItem(1);
		} else if (type == 2) {
			mViewPager.setCurrentItem(2);
		} else if (type == 3) {
			mViewPager.setCurrentItem(3);
		} else if (type == 4) {
			mViewPager.setCurrentItem(4);
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
