package m2.android.archetype.example.pulltorefresh.object;


import android.os.Parcel;
import android.os.Parcelable;

import com.nhn.android.archetype.base.object.BaseObj;

@SuppressWarnings("unchecked")
public class OriginPost extends BaseObj implements Parcelable {
	/**
	 * 
	 */
	private static final String POST_ID = "post_id";
	private static final String PERMALINK = "permalink";
	private static final String PLINK = "plink";
	private static final String BODY = "body";
	private static final String TEXTBODY = "textBody";
	private static final String AUTHOR = "author";
	private static final String COMMENTCOUNT = "commentCount";
	private static final String COMMENTINDEX = "commentIndex";
	
	
	
	
	public String getPostId() {
		return getString(POST_ID);
	}

	public void setPostId(String postId) {
		put(POST_ID, postId);
	}
	
	
	public String getPermalink() {
		return getString(PERMALINK);
	}

	public void setPermalink(String permalink) {
		put(PERMALINK, permalink);
	}
	
	
	public String getPlink() {
		return getString(PLINK);
	}

	public void setPlink(String plink) {
		put(PLINK, plink);
	}
	
	
	public String getBody() {
		return getString(BODY);
	}

	public void setBody(String body) {
		put(BODY, body);
	}
	
	
	public String getTextBody() {
		return getString(TEXTBODY);
	}

	public void setTextBody(String textBody) {
		put(TEXTBODY, textBody);
	}
	
	
	public Author getAuthor() {
		return (Author) getBaseObj(AUTHOR, Author.class);
	}

	public void setAuthor(Author author) {
		put(AUTHOR, author);
	}
	
	
	public int getCommentCount() {
		return getInt(COMMENTCOUNT);
	}

	public void setCommentCount(int commentCount) {
		put(COMMENTCOUNT, commentCount);
	}
	
	
	public int getCommentIndex() {
		return getInt(COMMENTINDEX);
	}

	public void setCommentIndex(int commentIndex) {
		put(COMMENTINDEX, commentIndex);
	}
	

	
	public int describeContents() {
		return 0;
	}

	public static Parcelable.Creator<OriginPost> getCreator() {
		return CREATOR;
	}

	public void writeToParcel(Parcel dest, int flags) {
	
		dest.writeString(this.getPostId());
		dest.writeString(this.getPermalink());
		dest.writeString(this.getPlink());
		dest.writeString(this.getBody());
		dest.writeString(this.getTextBody());
		dest.writeParcelable((Parcelable)this.getAuthor(), flags);
		dest.writeInt(this.getCommentCount());
		dest.writeInt(this.getCommentIndex());
	}

	public static final Parcelable.Creator<OriginPost> CREATOR = new Creator<OriginPost>() {
		public OriginPost createFromParcel(Parcel source) {
			OriginPost obj = new OriginPost();
	
			obj.setPostId(source.readString());
			obj.setPermalink(source.readString());
			obj.setPlink(source.readString());
			obj.setBody(source.readString());
			obj.setTextBody(source.readString());
			obj.setAuthor((Author)source.readParcelable(Author.class.getClassLoader()));
			obj.setCommentCount(source.readInt());
			obj.setCommentIndex(source.readInt());
			return obj;
		}

		public OriginPost[] newArray(int size) {
			return new OriginPost[size];
		}
	};
}
	