package m2.android.archetype.example.ormlite.object;


import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.nhn.android.archetype.base.object.BaseObj;

@SuppressWarnings("unchecked")
public class Streams extends BaseObj implements Parcelable {
	private static final String STREAM = "stream";
	
	
	
	
	public List<Stream> getStream() {
		return (List<Stream>) getList(STREAM, Stream.class);
	}

	public void setStream(List<Stream> stream) {
		put(STREAM, stream);
	}
	

	
	public int describeContents() {
		return 0;
	}

	public static Parcelable.Creator<Streams> getCreator() {
		return CREATOR;
	}

	public void writeToParcel(Parcel dest, int flags) {
	
		dest.writeList(this.getStream());
	}

	public static final Parcelable.Creator<Streams> CREATOR = new Creator<Streams>() {
		public Streams createFromParcel(Parcel source) {
			Streams obj = new Streams();
	
			obj.setStream(null); ArrayList<Stream> streamList = new ArrayList<Stream>(); source.readList(streamList, Stream.class.getClassLoader()); obj.setStream(streamList);
			return obj;
		}

		public Streams[] newArray(int size) {
			return new Streams[size];
		}
	};
}