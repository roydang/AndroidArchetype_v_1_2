package m2.android.archetype.example.pulltorefresh.object;


import android.os.Parcel;
import android.os.Parcelable;

import com.nhn.android.archetype.base.object.BaseObj;

@SuppressWarnings("unchecked")
public class Multimedia extends BaseObj implements Parcelable {
	private static final String TYPE = "type";
	private static final String PROVIDER = "provider";
	private static final String PHOTOID = "photoId";
	private static final String PHOTOURL = "photoUrl";
	private static final String PERMALINK = "permalink";
	private static final String ORIGINURL = "originUrl";
	private static final String THUMBNAIL = "thumbnail";
	private static final String WIDTH = "width";
	private static final String HEIGHT = "height";
	private static final String PHOTONO = "photoNo";
	private static final String TITLE = "title";
	private static final String IMAGEURL = "imageUrl";
	private static final String CONTENTURL = "contentUrl";
	private static final String DOMAIN = "domain";
	private static final String IDENTIFIER = "identifier";
	
	
	
	
	public String getType() {
		return getString(TYPE);
	}

	public void setType(String type) {
		put(TYPE, type);
	}
	
	
	public String getProvider() {
		return getString(PROVIDER);
	}

	public void setProvider(String provider) {
		put(PROVIDER, provider);
	}
	
	
	public String getPhotoId() {
		return getString(PHOTOID);
	}

	public void setPhotoId(String photoId) {
		put(PHOTOID, photoId);
	}
	
	
	public String getPhotoUrl() {
		return getString(PHOTOURL);
	}

	public void setPhotoUrl(String photoUrl) {
		put(PHOTOURL, photoUrl);
	}
	
	
	public String getPermalink() {
		return getString(PERMALINK);
	}

	public void setPermalink(String permalink) {
		put(PERMALINK, permalink);
	}
	
	
	public String getOriginUrl() {
		return getString(ORIGINURL);
	}

	public void setOriginUrl(String originUrl) {
		put(ORIGINURL, originUrl);
	}
	
	
	public String getThumbnail() {
		return getString(THUMBNAIL);
	}

	public void setThumbnail(String thumbnail) {
		put(THUMBNAIL, thumbnail);
	}
	
	
	public int getWidth() {
		return getInt(WIDTH);
	}

	public void setWidth(int width) {
		put(WIDTH, width);
	}
	
	
	public int getHeight() {
		return getInt(HEIGHT);
	}

	public void setHeight(int height) {
		put(HEIGHT, height);
	}
	
	
	public int getPhotoNo() {
		return getInt(PHOTONO);
	}

	public void setPhotoNo(int photoNo) {
		put(PHOTONO, photoNo);
	}
	
	
	public String getTitle() {
		return getString(TITLE);
	}

	public void setTitle(String title) {
		put(TITLE, title);
	}
	
	
	public String getImageUrl() {
		return getString(IMAGEURL);
	}

	public void setImageUrl(String imageUrl) {
		put(IMAGEURL, imageUrl);
	}
	
	
	public String getContentUrl() {
		return getString(CONTENTURL);
	}

	public void setContentUrl(String contentUrl) {
		put(CONTENTURL, contentUrl);
	}
	
	
	public String getDomain() {
		return getString(DOMAIN);
	}

	public void setDomain(String domain) {
		put(DOMAIN, domain);
	}
	
	
	public int getIdentifier() {
		return getInt(IDENTIFIER);
	}

	public void setIdentifier(int identifier) {
		put(IDENTIFIER, identifier);
	}
	

	
	public int describeContents() {
		return 0;
	}

	public static Parcelable.Creator<Multimedia> getCreator() {
		return CREATOR;
	}

	public void writeToParcel(Parcel dest, int flags) {
	
		dest.writeString(this.getType());
		dest.writeString(this.getProvider());
		dest.writeString(this.getPhotoId());
		dest.writeString(this.getPhotoUrl());
		dest.writeString(this.getPermalink());
		dest.writeString(this.getOriginUrl());
		dest.writeString(this.getThumbnail());
		dest.writeInt(this.getWidth());
		dest.writeInt(this.getHeight());
		dest.writeInt(this.getPhotoNo());
		dest.writeString(this.getTitle());
		dest.writeString(this.getImageUrl());
		dest.writeString(this.getContentUrl());
		dest.writeString(this.getDomain());
		dest.writeInt(this.getIdentifier());
	}

	public static final Parcelable.Creator<Multimedia> CREATOR = new Creator<Multimedia>() {
		public Multimedia createFromParcel(Parcel source) {
			Multimedia obj = new Multimedia();
	
			obj.setType(source.readString());
			obj.setProvider(source.readString());
			obj.setPhotoId(source.readString());
			obj.setPhotoUrl(source.readString());
			obj.setPermalink(source.readString());
			obj.setOriginUrl(source.readString());
			obj.setThumbnail(source.readString());
			obj.setWidth(source.readInt());
			obj.setHeight(source.readInt());
			obj.setPhotoNo(source.readInt());
			obj.setTitle(source.readString());
			obj.setImageUrl(source.readString());
			obj.setContentUrl(source.readString());
			obj.setDomain(source.readString());
			obj.setIdentifier(source.readInt());
			return obj;
		}

		public Multimedia[] newArray(int size) {
			return new Multimedia[size];
		}
	};
}
