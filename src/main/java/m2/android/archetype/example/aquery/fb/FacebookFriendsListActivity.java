package m2.android.archetype.example.aquery.fb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import m2.android.archetype.example.R;
import m2.android.archetype.example.FacebookSdk.internal.Utility;
import m2.android.archetype.example.aquery.fb.base.FeedMode;
import m2.android.archetype.example.aquery.fb.base.PQuery;
import m2.android.archetype.example.aquery.fb.base.PageAdapter;
import m2.android.archetype.example.aquery.fb.obj.FacebookFriend;
import m2.android.archetype.example.aquery.fb.obj.FacebookFriends;
import m2.android.archetype.util.Logger;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.nhn.android.archetype.base.object.BaseObj;


public class FacebookFriendsListActivity extends BaseFacebookActivity {	
	public static final String USER_NAME = "aq.fb.user.name";
	public static long DAY = 24 * 3600 * 1000;
	public static long MONTH = 30 * DAY;
	
	private static Logger logger = Logger.getLogger(FacebookFriendsListActivity.class);
	
	private FriendsAdapter friends;
	private List<FacebookFriend> items;
	
	@Override
	protected void init(Bundle savedInstanceState) {
		initView();
	}

	@Override
	protected int getContainerView() {
		return R.layout.activity_friends;
	}
	
	@Override
	public void refresh(){		
		ajaxFriends(-1);
	}
	
	private void initView(){	 
		friends = new FriendsAdapter();
    	
    	aq.id(R.id.list);
    	
    	aq.adapter(friends).scrolledBottom(this, "scrolledBottom").itemClicked(this, "itemClicked");
    	
    	ListView lv = aq.getListView();
    	lv.setItemsCanFocus(false);
    	lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    	
    	ajaxFriends(0);
    	
    	aq.id(R.id.edit_input).textChanged(this, "searchChanged");
		aq.id(R.id.button_done).clicked(this, "doneClicked");
	}
    
	private void ajaxFriends(long expire){		
		String url = FeedMode.FRIENDS.getUrl() + "?limit=3000&locale=" + locale;
		aq.auth(handle).transformer(transformer).ajax(url, BaseObj.class, expire, this, "friendCb");
		logger.d("ajaxFriends url = %s", url);
	}
	
	public void friendCb(String url, BaseObj jo, AjaxStatus status) {		
		logger.d("jo = %s", jo);
		
		if(jo != null){
			friends.clear();
			FacebookFriends todays = (FacebookFriends)jo.as(FacebookFriends.class);
			items = todays.getFacebookFriends();
			
			Collections.sort(items, new NameComparator());
			
			String next = todays.getString("paging");		
			friends.add(filter(null, items), next);
			
			if(status.expired(MONTH)){
				logger.d("expired", status.getTime());
				refresh();
			}
			
			aq.id(R.id.list).visible();
			
			logger.d("done");
			
		}else{
			logger.d("error!");
		}
		
		
	}

	private List<FacebookFriend> filter(CharSequence s, List<FacebookFriend> items){
    	
    	String str = null;
    	
    	if(s != null){
    		str = s.toString();
    	}else{
    		str = aq.id(R.id.edit_input).getEditable().toString();
    	}
    	
    	String[] terms = str.split("[\\s]+");
    	
    	
    	List<FacebookFriend> result = new ArrayList<FacebookFriend>(); 
    	
    	for(FacebookFriend item: items){
    		
    		String name = item.getFbFriendName().toLowerCase();
    		
    		boolean miss = false;
    		
    		for(String term: terms){
    		
	    		if(!name.contains(term)){	    		
	    			miss = true;
	    			break;
	    		}
	    		
    		}

    		if(!miss){
    			result.add(item);
    		}
    	}
    	
    	return result;
    }

	public void itemClicked(AdapterView<?> parent, View view, int pos, long id){
		 
		FacebookFriend entity = getItem(view);
		if(entity == null) return;
		
		PQuery aq = aq2.recycle(view);
		aq.id(R.id.checkbox_select);
		
		boolean checked = aq.isChecked();
		aq.checked(!checked);
		
		entity.setChecked(!checked);
	}
	
	public void searchChanged(CharSequence s, int start, int before, int count) { 
	    
    	if(items == null) return;
    	
    	friends.clear();
    	friends.add(filter(s, items), null);
    	
	} 
	
	public void doneClicked(View view){
		
		
		Intent result = new Intent();
		ArrayList<FacebookFriend> selects = getSelected();
		
		result.putExtra("selected", selects);
		
		logger.d("on stop", selects);
		
		setResult(RESULT_OK, result);
		
		finish();
	}
	
	private ArrayList<FacebookFriend> getSelected(){
		
		ArrayList<FacebookFriend> result = new ArrayList<FacebookFriend>();
		
		if(items != null){
		
			for(FacebookFriend entity: items){
				
				if(entity.isChecked()){
					result.add(entity);
				}
				
			}
		}
		
		return result;
		
	}
	
	private FacebookFriend getItem(View view){
    	PQuery aq = aq2.recycle(view).parent(R.id.parent);
    	return (FacebookFriend) aq.getTag();
    }
	
	private static class NameComparator implements Comparator<FacebookFriend>{

		@Override
		public int compare(FacebookFriend lhs, FacebookFriend rhs) {
			
			String name1 = lhs.getFbFriendName();
			String name2 = rhs.getFbFriendName();
			
			if(name1 == null) name1 = "";
			if(name2 == null) name2 = "";
			
			return name1.compareToIgnoreCase(name2);
		}
		
	}
	
	private class FriendsAdapter extends PageAdapter<FacebookFriend> {
		
		public View render(int position, View convertView, ViewGroup parent) {
			
			boolean init = convertView == null;
							
			convertView = aq.inflate(convertView, R.layout.item_friend, parent);					
				
			/*
			if(init){
				initItemView(convertView);
			}
			*/
			PQuery aq = aq2.recycle(convertView);
			
			FacebookFriend item = (FacebookFriend) getItem(position);
			
			aq.tag(item);
			
			aq.id(R.id.text_name).text(item.getFbFriendName());
			
			String tb = item.getFbFriendFaceUrl();
			
			if (Utility.isNullOrEmpty(tb)) {
				item.setFbFriendFaceUrl("http://graph.facebook.com/" + item.getFbFriendId() + "/picture");
				tb = item.getFbFriendFaceUrl();
			}
			
			if(aq.shouldDelay(convertView, parent, tb, 0)){
				aq.id(R.id.image_tb).clear();
			}else{
				aq.id(R.id.image_tb).image(tb, true, true, 0, 0, null, AQuery.FADE_IN_FILE);
			}
			
			return convertView;
			
		}
		
	}	
}
