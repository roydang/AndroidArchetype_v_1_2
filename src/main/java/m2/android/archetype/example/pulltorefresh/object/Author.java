package m2.android.archetype.example.pulltorefresh.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.nhn.android.archetype.base.object.BaseObj;

@SuppressWarnings("unchecked")
public class Author extends BaseObj implements Parcelable {
	
	
	@DatabaseField(generatedId = true, columnName = "_id")
	int id;
	@DatabaseField
	String nickname;
	@DatabaseField
	String face;
	
	
	private static final String ID = "id";
	private static final String NICKNAME = "nickname";
	private static final String FACE = "face";
	private static final String ME2DAYHOME = "me2dayHome";
	private static final String RELATIONSHIP = "relationship";
	private static final String REALNAME = "realname";
	private static final String HOMEPAGE = "homePage";
	
	
	public String getId() {
		return getString(ID);
	}

	public void setId(String id) {
		put(ID, id);
	}
	
	
	public String getNickname() {
		return getString(NICKNAME);
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
		put(NICKNAME, nickname);
	}
	
	
	public String getFace() {
		return getString(FACE);
	}

	public void setFace(String face) {
		this.face = face;
		put(FACE, face);
	}
	
	
	public String getMe2dayHome() {
		return getString(ME2DAYHOME);
	}

	public void setMe2dayHome(String me2dayHome) {
		put(ME2DAYHOME, me2dayHome);
	}
	
	
	public String getRelationship() {
		return getString(RELATIONSHIP);
	}

	public void setRelationship(String relationship) {
		put(RELATIONSHIP, relationship);
	}
	
	
	public String getRealname() {
		return getString(REALNAME);
	}

	public void setRealname(String realname) {
		put(REALNAME, realname);
	}
	
	
	public String getHomePage() {
		return getString(HOMEPAGE);
	}

	public void setHomePage(String homePage) {
		put(HOMEPAGE, homePage);
	}
	

	
	public int describeContents() {
		return 0;
	}

	public static Parcelable.Creator<Author> getCreator() {
		return CREATOR;
	}

	public void writeToParcel(Parcel dest, int flags) {
	
		dest.writeString(this.getId());
		dest.writeString(this.getNickname());
		dest.writeString(this.getFace());
		dest.writeString(this.getMe2dayHome());
		dest.writeString(this.getRelationship());
		dest.writeString(this.getRealname());
		dest.writeString(this.getHomePage());
	}

	public static final Parcelable.Creator<Author> CREATOR = new Creator<Author>() {
		public Author createFromParcel(Parcel source) {
			Author obj = new Author();
	
			obj.setId(source.readString());
			obj.setNickname(source.readString());
			obj.setFace(source.readString());
			obj.setMe2dayHome(source.readString());
			obj.setRelationship(source.readString());
			obj.setRealname(source.readString());
			obj.setHomePage(source.readString());
			return obj;
		}

		public Author[] newArray(int size) {
			return new Author[size];
		}
	};
}
