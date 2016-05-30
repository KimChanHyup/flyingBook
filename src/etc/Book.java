package etc;

import java.util.Date;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {

	private String name;
	private String description;
	private String author;
	private int rentCount;
	private String category;
	private String updateDay;
	private Bitmap image;
	private String image_name;

	public Book() {

	}

	public Book(String name, String description, String author, int rentCount,
			String category, String updateDay, String image_name) {
		this.name = name;
		this.description = description;
		this.author = author;
		this.rentCount = rentCount;
		this.category = category;
		this.updateDay = updateDay;
		this.image = null;
		this.image_name = image_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getRentCount() {
		return rentCount;
	}

	public void setRentCount(int rentCount) {
		this.rentCount = rentCount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getUpdateDay() {
		return updateDay;
	}

	public void setUpdateDay(String updateDay) {
		this.updateDay = updateDay;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public String getImage_name() {
		return image_name;
	}

	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub

		dest.writeString(name);
		dest.writeString(description);
		dest.writeString(author);
		dest.writeInt(rentCount);
		dest.writeString(category);
		dest.writeString(updateDay);
		dest.writeString(image_name);
		// dest.writeParcelable(image, flags);

	}

	public static final Parcelable.Creator<Book> CREATOR = new Creator<Book>() {

		@Override
		public Book createFromParcel(Parcel src) {
			ClassLoader loader = Bitmap.class.getClassLoader();

			String name = src.readString();
			String description = src.readString();
			String author = src.readString();
			int rentCount = src.readInt();
			String category = src.readString();
			String updateDay =src.readString();
			//Bitmap image = src.readParcelable(loader);
			String image_name = src.readString();

			// rentCount,
			return new Book(name, description, author, rentCount, category,
					updateDay, image_name);

		}

		@Override
		public Book[] newArray(int size) {
			return new Book[size];
		}

	};
}
