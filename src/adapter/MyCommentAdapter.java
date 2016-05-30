package adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.flyingbook.R;

import etc.Comment;

public class MyCommentAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Comment> arrData;
	private LayoutInflater inflater;

	public MyCommentAdapter(Context c, ArrayList<Comment> arr) {
		this.context = c;
		this.arrData = arr;
		inflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return arrData.size();
	}

	public Object getItem(int position) {
		return arrData.get(position).getText();
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.layout_comment, parent,
					false);
		}

		RatingBar ratingBar = (RatingBar) convertView
				.findViewById(R.id.ratingBar);
		ratingBar.setRating(arrData.get(position).getGradeStar());

		TextView date = (TextView) convertView.findViewById(R.id.date); // 현재 날짜
		date.setText(arrData.get(position).getDate());

		TextView text = (TextView) convertView.findViewById(R.id.text);
		text.setText(arrData.get(position).getText());

		TextView id = (TextView) convertView.findViewById(R.id.id);
		id.setText(arrData.get(position).getId());

		return convertView;
	}

}