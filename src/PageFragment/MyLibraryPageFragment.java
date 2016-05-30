package PageFragment;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import activity.BookInfoActivity;
import adapter.MyBookAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.flyingbook.R;

import etc.Book;
import etc.Util;

public class MyLibraryPageFragment extends Fragment implements OnClickListener {

	int index;
	boolean FLAG = true;
	Book sendBook;

	ArrayList<Book> myBookList = new ArrayList<Book>();
	ArrayList<Book> categoryList = new ArrayList<Book>();
	String U_id;
	MyBookAdapter adapter;

	public static MyLibraryPageFragment newInstance(String index,
			ArrayList<Book> myBookList, String U_id) {
		MyLibraryPageFragment pageFragment = new MyLibraryPageFragment();
		Bundle bundle = new Bundle();
		bundle.putString("index", index);
		bundle.putParcelableArrayList("myBookList", myBookList);
		System.out.println("1. size : "+myBookList.size());
		bundle.putString("U_id", U_id);
		pageFragment.setArguments(bundle);
		return pageFragment;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.myBookList = getArguments().getParcelableArrayList("myBookList");

		System.out.println("2. size : "+myBookList.size());
		this.U_id = getArguments().getString("U_id");

		for (int i = 0; i < myBookList.size(); i++) {

			URL Url = null;
			try {
				Url = new URL(Util.SERVER_ADDRESS + "image/"
						+ myBookList.get(i).getImage_name());
			} catch (MalformedURLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			URLConnection conn = null;
			try {
				conn = Url.openConnection();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				conn.connect();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			BufferedInputStream bis = null;
			try {
				bis = new BufferedInputStream(conn.getInputStream());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Bitmap bm = BitmapFactory.decodeStream(bis);
			try {
				bis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// bm = imgResize(bm);
			// System.out.println(bookList.get(i).getImage_name());
			myBookList.get(i).setImage(bm);

		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.listview_book, container, false);

		this.U_id = getArguments().getString("U_id");
		int index = Integer.parseInt(getArguments().getString("index")
				.toString());
		this.index = index;

		adapter = new MyBookAdapter(view.getContext(), categoryList);
		ListView listView = (ListView) view.findViewById(R.id.books);

		listView.setAdapter(adapter);
		
		categoryList.clear();
		for (int i = 0; i < myBookList.size(); i++) {
			categoryList.add(myBookList.get(i));
		}

		adapter.notifyDataSetChanged();

		SharedPreferences sp = this.getActivity().getSharedPreferences("sp",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		editor.commit();

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			// 1) AdapterView<?> parentView
			// parent는 클릭된 항목의 부모뷰인 어댑터 뷰이다.
			// 리스트 뷰의 항목을 클릭 했다면, parent는 Listview 이다.
			// 2) View v
			// view는 사용자가 클릭한 항목에 해당하는 뷰이다.
			// 3) int position
			// Listview의 선택된 항목의 위치
			// 4) long id
			// id는 position 과 동일하다고 보면됨.
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 아이템 클릭시에 구현할 내용은 여기에.
				sendBook = categoryList.get(position);

				Intent intent = new Intent(getActivity(),
						BookInfoActivity.class);
				/*
				 * ComponentName componentName = new ComponentName("activity",
				 * "activity.BookInfoActivity");
				 * intent.setComponent(componentName);
				 */
				intent.putExtra("sendBook", sendBook);
				intent.putExtra("U_id", U_id);
				// intent.putExtra("bitmap", sendStore.getImage());

				startActivity(intent);
			}
		});
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
