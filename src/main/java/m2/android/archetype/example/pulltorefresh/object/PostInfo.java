package m2.android.archetype.example.pulltorefresh.object;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.nhn.android.archetype.base.object.BaseObj;

@SuppressWarnings("unchecked")
public class PostInfo extends BaseObj implements Parcelable {
	private static final String SCOPE = "scope";
	private static final String PERMALINK = "permalink";
	private static final String PLINK = "plink";
	private static final String BODY = "body";
	private static final String TEXTBODY = "textBody";
	private static final String KIND = "kind";
	private static final String ICON = "icon";
	private static final String ORIGIN_POST = "origin_post";
	private static final String TAGTEXT = "tagText";
	private static final String TAGS = "tags";
	private static final String ME2DAYPAGE = "me2dayPage";
	private static final String PUBDATE = "pubDate";
	private static final String SEARCHKEYWORD = "searchKeyword";
	private static final String SEARCHCOUNT = "searchCount";
	private static final String COMMENTSCOUNT = "commentsCount";
	private static final String METOOCOUNT = "metooCount";
	private static final String COMMENTCLOSED = "commentClosed";
	private static final String CONTENTTYPE = "contentType";
	private static final String ICONURL = "iconUrl";
	private static final String CALLBACKURL = "callbackUrl";
	private static final String AUTHOR = "author";
	private static final String LOCATION = "location";
	private static final String MEDIA = "media";
	private static final String FROMAPP = "fromapp";
	private static final String PINGBACK_TO = "pingback_to";
	private static final String DOMAIN = "domain";
	private static final String METOO_AT = "metoo_at";
	private static final String METOO_BY = "metoo_by";
	private static final String MULTIMEDIA = "multimedia";
	private static final String NOTE = "note";
	private static final String POSTID = "postId";
	private static final String IDENTIFIER = "identifier";
	private static final String BAND_ID = "band_id";
	private static final String BAND_NAME = "band_name";
	
	
	
	
	public String getScope() {
		return getString(SCOPE);
	}

	public void setScope(String scope) {
		put(SCOPE, scope);
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
	
	
	public String getKind() {
		return getString(KIND);
	}

	public void setKind(String kind) {
		put(KIND, kind);
	}
	
	
	public String getIcon() {
		return getString(ICON);
	}

	public void setIcon(String icon) {
		put(ICON, icon);
	}
	
	
	public String getOriginPost() {
		return getString(ORIGIN_POST);
	}

	public void setOriginPost(String originPost) {
		put(ORIGIN_POST, originPost);
	}
	
	
	public String getTagText() {
		return getString(TAGTEXT);
	}

	public void setTagText(String tagText) {
		put(TAGTEXT, tagText);
	}
	
	
	public List<Tag> getTags() {
		return (List<Tag>) getList(TAGS, Tag.class);
	}

	public void setTags(List<Tag> tags) {
		put(TAGS, tags);
	}
	
	
	public String getMe2dayPage() {
		return getString(ME2DAYPAGE);
	}

	public void setMe2dayPage(String me2dayPage) {
		put(ME2DAYPAGE, me2dayPage);
	}
	
	
	public String getPubDate() {
		return getString(PUBDATE);
	}

	public void setPubDate(String pubDate) {
		put(PUBDATE, pubDate);
	}
	
	
	public String getSearchKeyword() {
		return getString(SEARCHKEYWORD);
	}

	public void setSearchKeyword(String searchKeyword) {
		put(SEARCHKEYWORD, searchKeyword);
	}
	
	
	public int getSearchCount() {
		return getInt(SEARCHCOUNT);
	}

	public void setSearchCount(int searchCount) {
		put(SEARCHCOUNT, searchCount);
	}
	
	
	public int getCommentsCount() {
		return getInt(COMMENTSCOUNT);
	}

	public void setCommentsCount(int commentsCount) {
		put(COMMENTSCOUNT, commentsCount);
	}
	
	
	public int getMetooCount() {
		return getInt(METOOCOUNT);
	}

	public void setMetooCount(int metooCount) {
		put(METOOCOUNT, metooCount);
	}
	
	
	public boolean getCommentClosed() {
		return getBoolean(COMMENTCLOSED);
	}

	public void setCommentClosed(boolean commentClosed) {
		put(COMMENTCLOSED, commentClosed);
	}
	
	
	public String getContentType() {
		return getString(CONTENTTYPE);
	}

	public void setContentType(String contentType) {
		put(CONTENTTYPE, contentType);
	}
	
	
	public String getIconUrl() {
		return getString(ICONURL);
	}

	public void setIconUrl(String iconUrl) {
		put(ICONURL, iconUrl);
	}
	
	
	public String getCallbackUrl() {
		return getString(CALLBACKURL);
	}

	public void setCallbackUrl(String callbackUrl) {
		put(CALLBACKURL, callbackUrl);
	}
	
	
	public Author getAuthor() {
		return (Author) getBaseObj(AUTHOR, Author.class);
	}

	public void setAuthor(Author author) {
		put(AUTHOR, author);
	}
	
	
	public Location getLocation() {
		return (Location) getBaseObj(LOCATION, Location.class);
	}

	public void setLocation(Location location) {
		put(LOCATION, location);
	}
	
	
	public Media getMedia() {
		return (Media) getBaseObj(MEDIA, Media.class);
	}

	public void setMedia(Media media) {
		put(MEDIA, media);
	}
	
	
	public String getFromapp() {
		return getString(FROMAPP);
	}

	public void setFromapp(String fromapp) {
		put(FROMAPP, fromapp);
	}
	
	
	public String getPingbackTo() {
		return getString(PINGBACK_TO);
	}

	public void setPingbackTo(String pingbackTo) {
		put(PINGBACK_TO, pingbackTo);
	}
	
	
	public String getDomain() {
		return getString(DOMAIN);
	}

	public void setDomain(String domain) {
		put(DOMAIN, domain);
	}
	
	
	public String getMetooAt() {
		return getString(METOO_AT);
	}

	public void setMetooAt(String metooAt) {
		put(METOO_AT, metooAt);
	}
	
	
	public String getMetooBy() {
		return getString(METOO_BY);
	}

	public void setMetooBy(String metooBy) {
		put(METOO_BY, metooBy);
	}
	
	
	public List<Multimedia> getMultimedia() {
		return (List<Multimedia>) getList(MULTIMEDIA, Multimedia.class);
	}

	public void setMultimedia(List<Multimedia> multimedia) {
		put(MULTIMEDIA, multimedia);
	}
	
	
	public String getNote() {
		return getString(NOTE);
	}

	public void setNote(String note) {
		put(NOTE, note);
	}
	
	
	public String getPostId() {
		return getString(POSTID);
	}

	public void setPostId(String postId) {
		put(POSTID, postId);
	}
	
	
	public String getIdentifier() {
		return getString(IDENTIFIER);
	}

	public void setIdentifier(String identifier) {
		put(IDENTIFIER, identifier);
	}
	
	
	public String getBandId() {
		return getString(BAND_ID);
	}

	public void setBandId(String bandId) {
		put(BAND_ID, bandId);
	}
	
	
	public String getBandName() {
		return getString(BAND_NAME);
	}

	public void setBandName(String bandName) {
		put(BAND_NAME, bandName);
	}
	

	
	public int describeContents() {
		return 0;
	}

	public static Parcelable.Creator<PostInfo> getCreator() {
		return CREATOR;
	}

	public void writeToParcel(Parcel dest, int flags) {
	
		dest.writeString(this.getScope());
		dest.writeString(this.getPermalink());
		dest.writeString(this.getPlink());
		dest.writeString(this.getBody());
		dest.writeString(this.getTextBody());
		dest.writeString(this.getKind());
		dest.writeString(this.getIcon());
		dest.writeString(this.getOriginPost());
		dest.writeString(this.getTagText());
		dest.writeList(this.getTags());
		dest.writeString(this.getMe2dayPage());
		dest.writeString(this.getPubDate());
		dest.writeString(this.getSearchKeyword());
		dest.writeInt(this.getSearchCount());
		dest.writeInt(this.getCommentsCount());
		dest.writeInt(this.getMetooCount());
		dest.writeInt(this.getCommentClosed() ? 1 : 0);
		dest.writeString(this.getContentType());
		dest.writeString(this.getIconUrl());
		dest.writeString(this.getCallbackUrl());
		dest.writeParcelable((Parcelable)this.getAuthor(), flags);
		dest.writeParcelable((Parcelable)this.getLocation(), flags);
		dest.writeParcelable((Parcelable)this.getMedia(), flags);
		dest.writeString(this.getFromapp());
		dest.writeString(this.getPingbackTo());
		dest.writeString(this.getDomain());
		dest.writeString(this.getMetooAt());
		dest.writeString(this.getMetooBy());
		dest.writeList(this.getMultimedia());
		dest.writeString(this.getNote());
		dest.writeString(this.getPostId());
		dest.writeString(this.getIdentifier());
		dest.writeString(this.getBandId());
		dest.writeString(this.getBandName());
	}

	public static final Parcelable.Creator<PostInfo> CREATOR = new Creator<PostInfo>() {
		public PostInfo createFromParcel(Parcel source) {
			PostInfo obj = new PostInfo();
	
			obj.setScope(source.readString());
			obj.setPermalink(source.readString());
			obj.setPlink(source.readString());
			obj.setBody(source.readString());
			obj.setTextBody(source.readString());
			obj.setKind(source.readString());
			obj.setIcon(source.readString());
			obj.setOriginPost(source.readString());
			obj.setTagText(source.readString());
			obj.setTags(null); ArrayList<Tag> tagList = new ArrayList<Tag>(); source.readList(tagList, Tag.class.getClassLoader()); obj.setTags(tagList);
			obj.setMe2dayPage(source.readString());
			obj.setPubDate(source.readString());
			obj.setSearchKeyword(source.readString());
			obj.setSearchCount(source.readInt());
			obj.setCommentsCount(source.readInt());
			obj.setMetooCount(source.readInt());
			obj.setCommentClosed(source.readInt() == 0 ? false : true);
			obj.setContentType(source.readString());
			obj.setIconUrl(source.readString());
			obj.setCallbackUrl(source.readString());
			obj.setAuthor((Author)source.readParcelable(Author.class.getClassLoader()));
			obj.setLocation((Location)source.readParcelable(Location.class.getClassLoader()));
			obj.setMedia((Media)source.readParcelable(Media.class.getClassLoader()));
			obj.setFromapp(source.readString());
			obj.setPingbackTo(source.readString());
			obj.setDomain(source.readString());
			obj.setMetooAt(source.readString());
			obj.setMetooBy(source.readString());
			obj.setMultimedia(null); ArrayList<Multimedia> multimediaList = new ArrayList<Multimedia>(); source.readList(multimediaList, Multimedia.class.getClassLoader()); obj.setMultimedia(multimediaList);
			obj.setNote(source.readString());
			obj.setPostId(source.readString());
			obj.setIdentifier(source.readString());
			obj.setBandId(source.readString());
			obj.setBandName(source.readString());
			return obj;
		}

		public PostInfo[] newArray(int size) {
			return new PostInfo[size];
		}
	};
}