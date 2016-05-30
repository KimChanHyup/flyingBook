package PageFragment;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import activity.BookInfoActivity;
import adapter.MyBookAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class ShowListPageFragment extends Fragment implements OnClickListener {

	int index;
	boolean FLAG = true;
	Book sendBook;

	ArrayList<Book> bookList = new ArrayList<Book>();
	/*
	 * ArrayList<Book> romanceBookList = new ArrayList<Book>(); ArrayList<Book>
	 * sportsBookList = new ArrayList<Book>(); ArrayList<Book> actionBookList =
	 * new ArrayList<Book>(); ArrayList<Book> inferBookList = new
	 * ArrayList<Book>();
	 */
	ArrayList<Book> categoryList = new ArrayList<Book>();

	String U_id;
	MyBookAdapter adapter;

	public static ShowListPageFragment newInstance(String index,
			ArrayList<Book> bookList, String U_id) {

		ShowListPageFragment pageFragment = new ShowListPageFragment();
		Bundle bundle = new Bundle();
		bundle.putString("index", index);
		bundle.putParcelableArrayList("bookList", bookList);
		bundle.putString("U_id", U_id);
		pageFragment.setArguments(bundle);
		return pageFragment;

	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("fragment onCreate ȣ��");
		this.bookList = getArguments().getParcelableArrayList("bookList");
		this.U_id = getArguments().getString("U_id");
		
		
		for (int i = 0; i < bookList.size(); i++) {

			URL Url = null;
			try {
				Url = new URL(Util.SERVER_ADDRESS + "image/"
						+ bookList.get(i).getImage_name());
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
			bookList.get(i).setImage(bm);

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
		for (int i = 0; i < bookList.size(); i++) {
			categoryList.add(bookList.get(i));
		}

		adapter.notifyDataSetChanged();

		SharedPreferences sp = this.getActivity().getSharedPreferences("sp",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		editor.commit();

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			// 1) AdapterView<?> parentView
			// parent�� Ŭ���� �׸��� �θ���� ����� ���̴�.
			// ����Ʈ ���� �׸��� Ŭ�� �ߴٸ�, parent�� Listview �̴�.
			// 2) View v
			// view�� ����ڰ� Ŭ���� �׸� �ش��ϴ� ���̴�.
			// 3) int position
			// Listview�� ���õ� �׸��� ��ġ
			// 4) long id
			// id�� position �� �����ϴٰ� �����.
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO ������ Ŭ���ÿ� ������ ������ ���⿡.
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
	public void onActivityCreated(Bundle savedInstanceState) {
		System.out.println("on Activity Creadted ȣ��");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
		System.out.println("on Start ȣ��");
	}

	@Override
	public void onResume() {
		super.onResume();
		System.out.println("on resume ȣ��");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
