package m2.android.archetype.example.pulltorefresh.object;

import com.j256.ormlite.field.DatabaseField;


@SuppressWarnings("unchecked")
public class AuthorDBData {
	
	@DatabaseField(generatedId = true, columnName = "_id")
	int id;
	@DatabaseField
	String nickname;
	@DatabaseField
	String face;

	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getFace() {
		return face;
	}
	public void setFace(String face) {
		this.face = face;
	}
	

}
