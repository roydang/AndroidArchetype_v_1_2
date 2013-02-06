package m2.android.archetype.example.ormlite.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.nhn.android.archetype.base.object.BaseObj;

@SuppressWarnings("unchecked")
public class MetooPost extends BaseObj implements Parcelable {
	private static final String METOO_BY = "metoo_by";
	private static final String METOO_AT = "metoo_at";
	private static final String POST = "post";
	
	
	
	
	public MetooBy getMetooBy() {
		return (MetooBy) getBaseObj(METOO_BY, MetooBy.class);
	}

	public void setMetooBy(MetooBy metooBy) {
		put(METOO_BY, metooBy);
	}
	
	
	public String getMetooAt() {
		return getString(METOO_AT);
	}

	public void setMetooAt(String metooAt) {
		put(METOO_AT, metooAt);
	}
	
	
	public Post getPost() {
		return (Post) getBaseObj(POST, Post.class);
	}

	public void setPost(Post post) {
		put(POST, post);
	}
	

	
	public int describeContents() {
		return 0;
	}

	public static Parcelable.Creator<MetooPost> getCreator() {
		return CREATOR;
	}

	public void writeToParcel(Parcel dest, int flags) {
	
		dest.writeParcelable((Parcelable)this.getMetooBy(), flags);
		dest.writeString(this.getMetooAt());
		dest.writeParcelable((Parcelable)this.getPost(), flags);
	}

	public static final Parcelable.Creator<MetooPost> CREATOR = new Creator<MetooPost>() {
		public MetooPost createFromParcel(Parcel source) {
			MetooPost obj = new MetooPost();
	
			obj.setMetooBy((MetooBy)source.readParcelable(MetooBy.class.getClassLoader()));
			obj.setMetooAt(source.readString());
			obj.setPost((Post)source.readParcelable(Post.class.getClassLoader()));
			return obj;
		}

		public MetooPost[] newArray(int size) {
			return new MetooPost[size];
		}
	};
}
