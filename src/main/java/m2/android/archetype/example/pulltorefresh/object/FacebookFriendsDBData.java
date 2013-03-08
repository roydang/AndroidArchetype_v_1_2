package m2.android.archetype.example.pulltorefresh.object;

import com.j256.ormlite.field.DatabaseField;


@SuppressWarnings("unchecked")
public class FacebookFriendsDBData {
	
	@DatabaseField(generatedId = true, columnName = "_key")
	int key;
	@DatabaseField
	String id;
	@DatabaseField
	String name;
	@DatabaseField
	String face;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFace() {
		return face;
	}
	public void setFace(String face) {
		this.face = face;
	}
	

}
