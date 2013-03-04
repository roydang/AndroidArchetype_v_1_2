package m2.android.archetype.example.pulltorefresh.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.nhn.android.archetype.base.object.BaseObj;

@SuppressWarnings("unchecked")
public class Comment extends BaseObj implements Parcelable {
	private static final String COMMENTID = "commentId";
	private static final String BODY = "body";
	private static final String TEXTBODY = "textBody";
	private static final String PUBDATE = "pubDate";
	private static final String AUTHOR = "author";
	private static final String COMMENTSCOUNT = "commentsCount";
	private static final String COMMENTINDEX = "commentIndex";
	private static final String ISBANDPOST = "isBandPost";
	private static final String POST_INFO = "post_info";
	
	
	
	
	public String getCommentId() {
		return getString(COMMENTID);
	}

	public void setCommentId(String commentId) {
		put(COMMENTID, commentId);
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
	
	
	public String getPubDate() {
		return getString(PUBDATE);
	}

	public void setPubDate(String pubDate) {
		put(PUBDATE, pubDate);
	}
	
	
	public Author getAuthor() {
		return (Author) getBaseObj(AUTHOR, Author.class);
	}

	public void setAuthor(Author author) {
		put(AUTHOR, author);
	}
	
	
	public int getCommentsCount() {
		return getInt(COMMENTSCOUNT);
	}

	public void setCommentsCount(int commentsCount) {
		put(COMMENTSCOUNT, commentsCount);
	}
	
	
	public int getCommentIndex() {
		return getInt(COMMENTINDEX);
	}

	public void setCommentIndex(int commentIndex) {
		put(COMMENTINDEX, commentIndex);
	}
	
	
	public boolean getIsBandPost() {
		return getBoolean(ISBANDPOST);
	}

	public void setIsBandPost(boolean isBandPost) {
		put(ISBANDPOST, isBandPost);
	}
	
	
	public PostInfo getPostInfo() {
		return (PostInfo) getBaseObj(POST_INFO, PostInfo.class);
	}

	public void setPostInfo(PostInfo postInfo) {
		put(POST_INFO, postInfo);
	}
	

	
	public int describeContents() {
		return 0;
	}

	public static Parcelable.Creator<Comment> getCreator() {
		return CREATOR;
	}

	public void writeToParcel(Parcel dest, int flags) {
	
		dest.writeString(this.getCommentId());
		dest.writeString(this.getBody());
		dest.writeString(this.getTextBody());
		dest.writeString(this.getPubDate());
		dest.writeParcelable((Parcelable)this.getAuthor(), flags);
		dest.writeInt(this.getCommentsCount());
		dest.writeInt(this.getCommentIndex());
		dest.writeInt(this.getIsBandPost() ? 1 : 0);
		dest.writeParcelable((Parcelable)this.getPostInfo(), flags);
	}

	public static final Parcelable.Creator<Comment> CREATOR = new Creator<Comment>() {
		public Comment createFromParcel(Parcel source) {
			Comment obj = new Comment();
	
			obj.setCommentId(source.readString());
			obj.setBody(source.readString());
			obj.setTextBody(source.readString());
			obj.setPubDate(source.readString());
			obj.setAuthor((Author)source.readParcelable(Author.class.getClassLoader()));
			obj.setCommentsCount(source.readInt());
			obj.setCommentIndex(source.readInt());
			obj.setIsBandPost(source.readInt() == 0 ? false : true);
			obj.setPostInfo((PostInfo)source.readParcelable(PostInfo.class.getClassLoader()));
			return obj;
		}

		public Comment[] newArray(int size) {
			return new Comment[size];
		}
	};
}
