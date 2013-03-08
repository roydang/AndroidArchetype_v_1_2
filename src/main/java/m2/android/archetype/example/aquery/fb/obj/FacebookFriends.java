package m2.android.archetype.example.aquery.fb.obj;

import java.util.List;

import com.nhn.android.archetype.base.object.BaseObj;

@SuppressWarnings("serial")
public class FacebookFriends extends BaseObj {
	private static final String FACEBOOK_FRIENDS = "data";
	
	@SuppressWarnings("unchecked")
	public List<FacebookFriend> getFacebookFriends() {
		return (List<FacebookFriend>)getList(FACEBOOK_FRIENDS, FacebookFriend.class);
	}
	
	public int size() {
		try {
			return getFacebookFriends().size();
		} catch (Exception e) {
			return 0;
		}
	}
	
	public FacebookFriend get(int idx) {
		try {
			return getFacebookFriends().get(idx);
		} catch (Exception e) {
			return null;
		}
	}
}
