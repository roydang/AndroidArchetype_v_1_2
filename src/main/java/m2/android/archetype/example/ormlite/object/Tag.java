package m2.android.archetype.example.ormlite.object;
import android.os.Parcel;
import android.os.Parcelable;

import com.nhn.android.archetype.base.object.BaseObj;

@SuppressWarnings("unchecked")
public class Tag extends BaseObj implements Parcelable {
	private static final String NAME = "name";
	private static final String URL = "url";
	
	
	
	
	public String getName() {
		return getString(NAME);
	}

	public void setName(String name) {
		put(NAME, name);
	}
	
	
	public String getUrl() {
		return getString(URL);
	}

	public void setUrl(String url) {
		put(URL, url);
	}
	

	
	public int describeContents() {
		return 0;
	}

	public static Parcelable.Creator<Tag> getCreator() {
		return CREATOR;
	}

	public void writeToParcel(Parcel dest, int flags) {
	
		dest.writeString(this.getName());
		dest.writeString(this.getUrl());
	}

	public static final Parcelable.Creator<Tag> CREATOR = new Creator<Tag>() {
		public Tag createFromParcel(Parcel source) {
			Tag obj = new Tag();
	
			obj.setName(source.readString());
			obj.setUrl(source.readString());
			return obj;
		}

		public Tag[] newArray(int size) {
			return new Tag[size];
		}
	};
}
