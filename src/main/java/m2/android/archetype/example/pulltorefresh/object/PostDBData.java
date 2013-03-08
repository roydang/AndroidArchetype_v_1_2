package m2.android.archetype.example.pulltorefresh.object;

import com.j256.ormlite.field.DatabaseField;

public class PostDBData {
	
	
	@DatabaseField(generatedId = true, columnName = "_id")
	int id;
	
	@DatabaseField(unique = true)
	String post_id;

	@DatabaseField
	String body;

	@DatabaseField
	String tagtext;

	@DatabaseField
	String face;
	
	@DatabaseField
	String photoUrl;
//	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
//	AuthorDBData author;


	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTagtext() {
		return tagtext;
	}

	public void setTagtext(String tagtext) {
		this.tagtext = tagtext;
	}



	public String getPost_id() {
		return post_id;
	}

	public void setPost_id(String post_id) {
		this.post_id = post_id;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	

}
