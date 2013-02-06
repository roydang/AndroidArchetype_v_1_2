/*
 * TemplateListView.java $version 2012. 09. 13
 *
 * Copyright 2012 NHN Corp. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package m2.android.archetype.template;

import java.util.ArrayList;
import java.util.List;

import m2.android.archetype.example.R;
import m2.android.archetype.util.AppInfoUtility;
import m2.android.archetype.util.Logger;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;

import com.nhn.android.archetype.base.object.BaseObj;
import com.nhn.android.archetype.base.theme.ThemeListView;

/**
 * 템플릿 리스트 뷰
 * 
 * ! 해당 데이터가 없으면 gone 존재하면 visible (에 : !bandType ) 
 * ~ 해당 데이터가 있으면 gone 없으면 visible (에 : ~bandType )
 * # 해당 데이터가 입력한 타입이면 visible 아니면 gone (예 : #Band 혹은 #com.nhn.android.band.object.Band)
 * + 데이터가 존재하지 않으면 android:text 의 값을 쓴다. (예 : +cellphone )
 * , 데이터 구분 (예 : author.thumbnail,author.name)
 * ? default값 (예 : commentsCount?0 )
 * : data type (예 : pubDate:date ) 
 * @author psbreeze
 *
 */
public class TemplateListView extends ThemeListView {
	public static final int GROUP_NONE = 0;
	public static final int GROUP_ALPHA = 0x10;
	public static final int GROUP_ALPHA_DESC = 0x11;
	public static final int GROUP_DATE = 0x20;
	public static final int GROUP_DATE_DESC = 0x21;
	public static final int GROUP_SCHEDULE = 0x30;
	public static final int GROUP_SCHEDULE_DESC = 0x31;
	public static final int GROUP_MANUAL = 0x40;
	public static final int GROUP_MANUAL_DESC = 0x41;
	public static final int GROUP_LINEALPHA = 0x50;
	public static final int GROUP_LINEALPHA_DESC = 0x51;
	
	public static final String GROUP_HEADER_KEY = "GROUP_HEADER";
	public static final String GROUP_HEADER_TEXT_KEY = "GROUP_HEADER_TEXT";
	
	private static Logger logger = Logger.getLogger(TemplateListView.class);
	
	private TemplateListAdapter adapter;
	private List<BaseObj> objList = new ArrayList<BaseObj>();
	private int layoutId;
	
	private String groupKey = null;
	private int groupMode = GROUP_NONE;
	
	public TemplateListView(Context context) {
		super(context);
		init(null);
	}

	public TemplateListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	public TemplateListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}
	
	public TemplateListAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(TemplateListAdapter adapter) {
		this.adapter = adapter;
		
		if (adapter != null && groupKey != null && groupMode != GROUP_NONE) {
			adapter.setGroupKey(groupKey);
			adapter.setGroupMode(groupMode);
		}
		
		super.setAdapter(adapter);
	}

	public List<BaseObj> getObjList() {
		return objList;
	}

	public int getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(int layoutId) {
		this.layoutId = layoutId;
		init(null);
	}
	
	public int getGroupMode() {
		return adapter.getGroupMode();
	}

	public void setGroupMode(int groupMode) {
		adapter.setGroupMode(groupMode);
		
		if (groupMode != GROUP_NONE) {
			setUseMultithreadCacheGan(false);
		}
	}

	public String getGroupKey() {
		return adapter.getGroupKey();
	}

	public void setGroupKey(String groupKey) {
		adapter.setGroupKey(groupKey);
	}

	public GroupSorter getManualGroupSorter() {
		return adapter.getManualGroupSorter();
	}

	public void setManualGroupSorter(GroupSorter manualGroupSorter) {
		adapter.setManualGroupSorter(manualGroupSorter);
	}

	private void applyAttributeSet(AttributeSet attrs) {
		try {
			if (attrs != null) {
				TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TemplateListView);
				
				final int count = a.getIndexCount();
				for (int i=0;i<count;i++) {
					int attr = a.getIndex(i);
					
					switch(attr) {
					case R.styleable.TemplateListView_layoutId:
						layoutId = a.getInt(attr, 0);
						break;
					
					case R.styleable.TemplateListView_groupKey:
						groupKey = a.getString(attr);
						break;
						
					case R.styleable.TemplateListView_groupMode:
						String _groupMode = a.getString(attr);
						
						if ("alpha".equalsIgnoreCase(_groupMode)) {
							groupMode = GROUP_ALPHA;
						} else if ("alphaDesc".equalsIgnoreCase(_groupMode)) {
							groupMode = GROUP_ALPHA_DESC;
						} else if ("date".equalsIgnoreCase(_groupMode)) {
							groupMode = GROUP_DATE;
						} else if ("dateDesc".equalsIgnoreCase(_groupMode)) {
							groupMode = GROUP_DATE_DESC;
						} else if ("schedule".equalsIgnoreCase(_groupMode)) {
							groupMode = GROUP_SCHEDULE;
						} else if ("scheduleDesc".equalsIgnoreCase(_groupMode)) {
							groupMode = GROUP_SCHEDULE_DESC;
						} else if ("manual".equalsIgnoreCase(_groupMode)) {
							groupMode = GROUP_MANUAL;
						} else if ("manualDesc".equalsIgnoreCase(_groupMode)) {
							groupMode = GROUP_MANUAL_DESC;
						}  else if ("lineAlpha".equalsIgnoreCase(_groupMode)) {
							groupMode = GROUP_LINEALPHA;
						} else if ("lineAlphaDesc".equalsIgnoreCase(_groupMode)) {
							groupMode = GROUP_LINEALPHA_DESC;
						} else {
							groupMode = GROUP_NONE;
						}
						
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.e(e);
		}
	}
	
	@SuppressLint("NewApi")
	private void init(AttributeSet attrs) {
		applyAttributeSet(attrs);
		logger.d("layoutId: %s", layoutId);
		
		this.setScrollingCacheEnabled(true);
		this.setCacheColorHint(Color.TRANSPARENT);

		if (AppInfoUtility.isICSCompatibility()) {
			this.setFriction(0.005f);
		}
		
		if (layoutId > 0) {
			adapter = new TemplateListAdapter((Activity) getContext(), layoutId, objList);
			adapter.setEventListener(getEventListener());
			adapter.setProcessListener(getProcessListener());
			this.setAdapter(adapter);
		}
	}
	
	public boolean addAllObjList(List<? extends BaseObj> objList) {
		return getObjList().addAll(objList);
	}
	
	public boolean addObj(BaseObj obj) {
		return objList.add(obj);
	}
	
	public void addObj(int idx, BaseObj obj) {
		objList.add(idx, obj);
	}
	
	public boolean removeObj(BaseObj obj) {
		return objList.remove(obj);
	}
	
	public void clearObjList() {
		objList.clear();
	}
	
	public BaseObj updateObj(int idx, BaseObj obj) {
		return objList.set(idx, obj);
	}
	
	public int getObjCount() {
		return objList.size();
	}
	
	public void setEventListener(TemplateListViewEventListener eventListener) {
		if (adapter != null) {
			adapter.setEventListener(eventListener);
		}
	}
	
	public TemplateListViewEventListener getEventListener() {
		if (adapter != null) {
			return adapter.getEventListener();
		}
		
		return null;
	}
	
	public void setProcessListener(TemplateListViewProcessListener processListener) {
		if (adapter != null) {
			adapter.setProcessListener(processListener);
		}
	}
	
	public TemplateListViewProcessListener getProcessListener() {
		if (adapter != null) {
			return adapter.getProcessListener();
		}
		
		return null;
	}
	
	public boolean isUseMultithreadCacheGan() {
		if (adapter != null) {
			return adapter.isMultithreadCacheGan();
		}
		
		return false;
	}

	public void setUseMultithreadCacheGan(boolean useMultithreadCacheGan) {
		if (adapter != null) {
			adapter.setMultithreadCacheGan(useMultithreadCacheGan);
		}
	}
	
	public void refreshList() {
		if (adapter != null) {
			logger.d("notifyDataSetChanged: %s", getObjList().size());
			adapter.notifyDataSetChanged();
		}
	}
}
