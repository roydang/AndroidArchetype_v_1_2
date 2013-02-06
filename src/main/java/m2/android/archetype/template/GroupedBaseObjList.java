package m2.android.archetype.template;

import java.util.List;

import com.nhn.android.archetype.base.object.BaseObj;

public class GroupedBaseObjList {
	public static final int GROUPED_LANG_TYPE_TOP   = 0;  //고정아이템
	public static final int GROUPED_LANG_TYPE_HOME  = 1;  //자국어
	public static final int GROUPED_LANG_TYPE_ENG   = 2;  //영어
	public static final int GROUPED_LANG_TYPE_OTHER = 3;  //타국어
	public static final int GROUPED_LANG_TYPE_ETC   = 4;  //숫자,기호등
	
	private String id;
	private String headerText;
	private int languageType = GROUPED_LANG_TYPE_HOME; 	 	//0:자국어, 1:영어, 2:타국어, 3:그외 기타
	private BaseObj tempObj;
	private List<BaseObj> objList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHeaderText() {
		return headerText;
	}

	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}

	public int getLanguageType() {
		return languageType;
	}

	public void setLanguageType(int languageType) {
		this.languageType = languageType;
	}
	
	public BaseObj getTempObj() {
		return tempObj;
	}

	public void setTempObj(BaseObj tempObj) {
		this.tempObj = tempObj;
	}
	
	public List<BaseObj> getObjList() {
		return objList;
	}

	public void setObjList(List<BaseObj> objList) {
		this.objList = objList;
	}
}
