package m2.android.archetype.example.ormlite.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.nhn.android.archetype.base.object.BaseObj;

@SuppressWarnings("unchecked")
public class Stream extends BaseObj implements Parcelable {
	private static final String POST = "post";
	private static final String METOO_POST = "metoo_post";
	private static final String COMMENT = "comment";

	public Post getPost() {
		return (Post) getBaseObj(POST, Post.class, false);
	}

	public void setPost(Post post) {
		put(POST, post);
	}

	public MetooPost getMetooPost() {
		return (MetooPost) getBaseObj(METOO_POST, MetooPost.class, false);
	}

	public void setMetooPost(MetooPost metooPost) {
		put(METOO_POST, metooPost);
	}

	public Comment getComment() {
		return (Comment) getBaseObj(COMMENT, Comment.class, false);
	}

	public void setComment(Comment comment) {
		put(COMMENT, comment);
	}

	public int describeContents() {
		return 0;
	}

	public static Parcelable.Creator<Stream> getCreator() {
		return CREATOR;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable((Parcelable) this.getPost(), flags);
		dest.writeParcelable((Parcelable) this.getMetooPost(), flags);
		dest.writeParcelable((Parcelable) this.getComment(), flags);
	}

	public static final Parcelable.Creator<Stream> CREATOR = new Creator<Stream>() {
		public Stream createFromParcel(Parcel source) {
			Stream obj = new Stream();

			obj.setPost((Post) source.readParcelable(Post.class.getClassLoader()));
			obj.setMetooPost((MetooPost) source.readParcelable(MetooPost.class.getClassLoader()));
			obj.setComment((Comment) source.readParcelable(Comment.class.getClassLoader()));
			return obj;
		}

		public Stream[] newArray(int size) {
			return new Stream[size];
		}
	};
}
