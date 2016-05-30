package adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.flyingbook.R;

import etc.Book;

public class MyBookAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Book> arrData;
	private LayoutInflater inflater;

	// private Typeface mTypeface;

	public MyBookAdapter(Context c, ArrayList<Book> arr) {
		// mTypeface = Typeface.createFromAsset(c.getAssets(),
		// "fonts/BMHANNA_11yrs_ttf.ttf");
		this.context = c;
		this.arrData = arr;
		inflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return arrData.size();
	}

	public Object getItem(int position) {
		return arrData.get(position).getName();
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.layout_book, parent, false);
		}

		ImageView image = (ImageView) convertView.findViewById(R.id.image);
		image.setImageBitmap(arrData.get(position).getImage());

		TextView name = (TextView) convertView.findViewById(R.id.title);
		name.setText(arrData.get(position).getName());
		// name.setTypeface(mTypeface);

		TextView text = (TextView) convertView.findViewById(R.id.author);
		text.setText(arrData.get(position).getAuthor());
		// text.setTypeface(mTypeface);

		
		return convertView;
	}

}