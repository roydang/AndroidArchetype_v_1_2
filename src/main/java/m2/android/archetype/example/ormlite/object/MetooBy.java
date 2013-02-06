package m2.android.archetype.example.ormlite.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.nhn.android.archetype.base.object.BaseObj;

@SuppressWarnings("unchecked")
public class MetooBy extends BaseObj implements Parcelable {
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
		put(NICKNAME, nickname);
	}
	
	
	public String getFace() {
		return getString(FACE);
	}

	public void setFace(String face) {
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

	public static Parcelable.Creator<MetooBy> getCreator() {
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

	public static final Parcelable.Creator<MetooBy> CREATOR = new Creator<MetooBy>() {
		public MetooBy createFromParcel(Parcel source) {
			MetooBy obj = new MetooBy();
	
			obj.setId(source.readString());
			obj.setNickname(source.readString());
			obj.setFace(source.readString());
			obj.setMe2dayHome(source.readString());
			obj.setRelationship(source.readString());
			obj.setRealname(source.readString());
			obj.setHomePage(source.readString());
			return obj;
		}

		public MetooBy[] newArray(int size) {
			return new MetooBy[size];
		}
	};
}