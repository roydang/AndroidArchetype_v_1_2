package m2.android.archetype.example.pulltorefresh.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.nhn.android.archetype.base.object.BaseObj;

@SuppressWarnings("unchecked")
public class Location extends BaseObj implements Parcelable {
	private static final String NAME = "name";
	private static final String LONGITUDE = "longitude";
	private static final String LATITUDE = "latitude";
	
	
	public String getName() {
		return getString(NAME);
	}

	public void setName(String name) {
		put(NAME, name);
	}
	
	
	public String getLongitude() {
		return getString(LONGITUDE);
	}

	public void setLongitude(String longitude) {
		put(LONGITUDE, longitude);
	}
	
	
	public String getLatitude() {
		return getString(LATITUDE);
	}

	public void setLatitude(String latitude) {
		put(LATITUDE, latitude);
	}
	

	
	public int describeContents() {
		return 0;
	}

	public static Parcelable.Creator<Location> getCreator() {
		return CREATOR;
	}

	public void writeToParcel(Parcel dest, int flags) {
	
		dest.writeString(this.getName());
		dest.writeString(this.getLongitude());
		dest.writeString(this.getLatitude());
	}

	public static final Parcelable.Creator<Location> CREATOR = new Creator<Location>() {
		public Location createFromParcel(Parcel source) {
			Location obj = new Location();
	
			obj.setName(source.readString());
			obj.setLongitude(source.readString());
			obj.setLatitude(source.readString());
			return obj;
		}

		public Location[] newArray(int size) {
			return new Location[size];
		}
	};
}
	