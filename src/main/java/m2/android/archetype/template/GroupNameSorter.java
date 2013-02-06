package m2.android.archetype.template;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import android.content.Context;

import com.nhn.android.archetype.base.object.BaseObj;

public class GroupNameSorter implements GroupSorter {
	private static final String ORDER_DEFAULT = "$DEFAULT$";
	private static final String ORDER_DEFAULT_HEADER = "0-9";
	private static final String[] ORDER = {
		"ㄱ","ㄲ","ㄴ","ㄷ","ㄸ","ㄹ","ㅁ","ㅂ","ㅃ","ㅅ","ㅆ","ㅇ","ㅈ","ㅉ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ",
		"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z", ORDER_DEFAULT};
    
	private static final char HANGUL_BEGIN_UNICODE = 44032; // 가
	private static final char HANGUL_END_UNICODE = 55203; // 힣
	private static final char[] INITIAL_SOUND = { 'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ','ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ' };
	private static final char[] INITIAL_SOUND_LETTER = { '가', '까', '나', '다', '따', '라','마', '바', '빠', '사', '싸', '아', '자', '짜', '차', '카', '타', '파', '하', HANGUL_END_UNICODE+1};
	
	//public static final char TOP_ORDER = INITIAL_SOUND_LETTER[0];
	public static final char TOP_ORDER = '힗'; //설마 이 문자가 문두에 오늘 케이스는 없겠지? 
	
    private static char getInitalSound(char hanChar) {
    	for (int i=0;i<INITIAL_SOUND_LETTER.length-1;i++) {
    		if (hanChar >= INITIAL_SOUND_LETTER[i] && hanChar < INITIAL_SOUND_LETTER[i+1]) {
    			return INITIAL_SOUND[i];
    		}
    	}
        
        return hanChar;
    }
    
    private static boolean isHangul(char unicode) {
        return HANGUL_BEGIN_UNICODE <= unicode    && unicode <= HANGUL_END_UNICODE;
    }
    
	public List<GroupedBaseObjList> group(Context context, List<BaseObj> objList, final String groupKey, final boolean asc) {
		List<BaseObj> tempList = new ArrayList<BaseObj>();
		tempList.addAll(objList);
		
		Collections.sort(tempList, new Comparator<BaseObj>() {
			public int compare(BaseObj lhs, BaseObj rhs) {
				if (asc) {
					return lhs.getString(groupKey, "").compareTo(rhs.getString(groupKey, ""));
				} else {
					return rhs.getString(groupKey, "").compareTo(lhs.getString(groupKey, ""));
				}
			}
		});
		
		LinkedHashMap<String, GroupedBaseObjList> map = new LinkedHashMap<String, GroupedBaseObjList>();
		
		for (String order : ORDER) {
			GroupedBaseObjList gbo = new GroupedBaseObjList();
			gbo.setObjList(new ArrayList<BaseObj>());
			gbo.setId(order);
			
			if (order.equals(ORDER_DEFAULT)) {
				gbo.setHeaderText(ORDER_DEFAULT_HEADER);
			} else {
				gbo.setHeaderText(order);
			}
			
			map.put(order, gbo);
		}
		
		for (BaseObj obj : tempList) {
			String name = obj.getString(groupKey, "").toUpperCase();
			GroupedBaseObjList gbo = null;
			
			if (name.length() > 0) {
				char firstLetter = name.charAt(0);	
				
				if (Character.isDigit(firstLetter)) {
					gbo = map.get(ORDER_DEFAULT);
				} else if (firstLetter >= 'A' && firstLetter <= 'Z') {
					gbo = map.get(Character.toString(firstLetter));
				} else if (isHangul(firstLetter)) {
					gbo = map.get(Character.toString(getInitalSound(firstLetter)));
				} else {
					gbo = map.get(ORDER_DEFAULT);
				}
			} else {
				gbo = map.get(ORDER_DEFAULT);
			}
			
			gbo.getObjList().add(obj);
		}
		
		List<GroupedBaseObjList> ret = new ArrayList<GroupedBaseObjList>();
		for (Entry<String, GroupedBaseObjList> entry : map.entrySet()) {
			GroupedBaseObjList obj = entry.getValue();
			
			if (obj.getObjList().size() > 0) {
				ret.add(obj);
			}
		}
		
		return ret;
	}
}
