package m2.android.archetype.example.ormlite.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.nhn.android.archetype.base.object.BaseObj;

@SuppressWarnings("unchecked")
public class Media extends BaseObj implements Parcelable {
	
	private static final String MEDIA_TYPE = "type";
	private static final String MEDIA_ME2PHOTO = "me2photo";
	private static final String MEDIA_ME2VIDEO = "me2video";
	private static final String MEDIA_ME2CAST = "me2cast";
	private static final String MEDIA_ME2MOVIE = "me2movie";
	private static final String MEDIA_ME2MUSIC = "me2music";
	private static final String MEDIA_ME2BOOK = "me2book";
	private static final String ME2PHOTO_PHOTOURL = "photoUrl";
	private static final String SOURCE = "source";
	private static final String CONTENTURL = "contentUrl";
	private static final String PROVIDER = "provider";
	private static final String PHOTOID = "photoId";
	private static final String PHOTOURL = "photoUrl";
	private static final String PERMALINK = "permalink";
	private static final String ORIGINURL = "originUrl";
	private static final String THUMBNAIL = "thumbnail";
	private static final String WIDTH = "width";
	private static final String HEIGHT = "height";
	private static final String PHOTONO = "photoNo";


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

	public String getType() {
		return getString(MEDIA_TYPE);
	}

	public void setType(String type) {
		put(MEDIA_TYPE, type);
	}

	public String getMediaSource() {
		return getString(SOURCE);
	}

	public void setMediaSource(String me2castSource) {
		put(SOURCE, me2castSource);
	}

	public String getContentUrl() {
		return getString(CONTENTURL);
	}

	public void setContentUrl(String contentUrl) {
		put(CONTENTURL, contentUrl);
	}

	public String getSource() {
		return getString(SOURCE);
	}

	public void setSource(String source) {
		put(SOURCE, source);
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

	public String getPermalink() {
		return getString(PERMALINK);
	}

	public void setPermalink(String permalink) {
		put(PERMALINK, permalink);
	}

	public String getPhotoUrl() {
		return getString(PHOTOURL);
	}

	public void setPhotoUrl(String photoUrl) {
		put(PHOTOURL, photoUrl);
	}

	public int describeContents() {
		return 0;
	}

	public static Parcelable.Creator<Media> getCreator() {
		return CREATOR;
	}

	public void writeToParcel(Parcel dest, int flags) {

		dest.writeString(this.getType());
		dest.writeString(this.getContentUrl());
		dest.writeString(this.getSource());
		dest.writeString(this.getProvider());
		dest.writeString(this.getPhotoId());
		dest.writeString(this.getPermalink());
		dest.writeString(this.getPhotoUrl());

	}

	public static final Parcelable.Creator<Media> CREATOR = new Creator<Media>() {
		public Media createFromParcel(Parcel source) {
			Media obj = new Media();

			obj.setType(source.readString());
			obj.setContentUrl(source.readString());
			obj.setSource(source.readString());
			obj.setProvider(source.readString());
			obj.setPhotoId(source.readString());
			obj.setPermalink(source.readString());
			obj.setPhotoUrl(source.readString());

			return obj;
		}

		public Media[] newArray(int size) {
			return new Media[size];
		}
	};
}
