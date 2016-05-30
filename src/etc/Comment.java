package etc;

//implements Parcelable
public class Comment {
	private String id;
	private String text;
	private String date;
	private float grade_star;

	public Comment(String id, String text, String date, float grade_star) {
		this.id = id;
		this.text = text;
		this.date = date;
		this.grade_star = grade_star;
	}

	public Comment() {
	}

	public float getGradeStar() {
		return grade_star;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setGradeStar(float grade_star) {
		this.grade_star = grade_star;
	}

	public String getText() {
		return text;
	}

	public String getId() {
		return id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setId(String id) {
		this.id = id;
	}
	// @Override
	// public int describeContents() {
	// // TODO Auto-generated method stub
	// return 0;
	// }
	// @Override
	// public void writeToParcel(Parcel dest, int flags) {
	// // TODO Auto-generated method stub
	// dest.writeInt(image);
	// dest.writeString(title);
	// dest.writeString(text);
	// dest.writeString(tell);
	//
	// }
	//
	// public static final Parcelable.Creator<Sub> CREATOR = new Creator<Sub>()
	// {
	//
	// @Override
	// public Sub createFromParcel(Parcel src) {
	// int image = src.readInt();
	// String title = src.readString();
	// String text = src.readString();
	// String tell = src.readString();
	//
	// return new Sub(image, title, text, tell);
	//
	// }
	//
	// @Override
	// public Sub[] newArray(int size) {
	// return new Sub[size];
	// }
	//
	// };
}