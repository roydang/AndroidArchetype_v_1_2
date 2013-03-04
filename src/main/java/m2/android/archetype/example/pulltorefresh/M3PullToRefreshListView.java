package m2.android.archetype.example.pulltorefresh;

import java.util.ArrayList;
import java.util.List;

import m2.android.archetype.example.R;
import m2.android.archetype.template.TemplateListAdapter;
import m2.android.archetype.template.TemplateListViewEventListener;
import m2.android.archetype.template.TemplateListViewProcessListener;
import m2.android.archetype.util.Logger;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nhn.android.archetype.base.object.BaseObj;

public class M3PullToRefreshListView extends PullToRefreshListView {

	public static final int GROUP_NONE = 0;
	public static final int GROUP_ALPHA = 0x10;
	public static final int GROUP_ALPHA_DESC = 0x11;
	public static final int GROUP_DATE = 0x20;
	public static final int GROUP_DATE_DESC = 0x21;
	public static final int GROUP_MANUAL = 0x30;
	public static final int GROUP_MANUAL_DESC = 0x31;
	
	public static final String GROUP_HEADER_KEY = "GROUP_HEADER";
	public static final String GROUP_HEADER_TEXT_KEY = "GROUP_HEADER_TEXT";
	
	private static Logger logger = Logger.getLogger(M3PullToRefreshListView.class);
	
	private TemplateListAdapter adapter;
	private List<BaseObj> objList = new ArrayList<BaseObj>();
	private int layoutId;
	
	public M3PullToRefreshListView(Context context) {
		super(context);
		init(null);
	}

//	public M3PullToRefreshListView(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//		init(attrs);
//	}

	public M3PullToRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}
	
	public TemplateListAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(TemplateListAdapter adapter) {
		this.adapter = adapter;
		this.adapter.setEventListener(getEventListener());
		this.adapter.setProcessListener(getProcessListener());
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
		return getAdapter().getGroupMode();
	}

	public void setGroupMode(int groupMode) {
		getAdapter().setGroupMode(groupMode);
	}

	public String getGroupKey() {
		return getAdapter().getGroupKey();
	}

	public void setGroupKey(String groupKey) {
		getAdapter().setGroupKey(groupKey);
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
					}
				}
			}
		} catch (Exception e) {
			logger.e(e);
		}
	}
	
	private void init(AttributeSet attrs) {
		applyAttributeSet(attrs);
		logger.d("layoutId: %s", getLayoutId());
		
		if (getLayoutId() > 0) {
			adapter = new TemplateListAdapter(getContext(), getLayoutId(), objList);
			this.setAdapter(adapter);
		}
	}
	
	public void addAllObjList(List<? extends BaseObj> objList) {
		getObjList().addAll(objList);
	}
	
	public void addObj(BaseObj obj) {
		getObjList().add(obj);
	}
	
	public void addObj(int idx, BaseObj obj) {
		getObjList().add(idx, obj);
	}
	
	public void removeObj(BaseObj obj) {
		getObjList().remove(obj);
	}
	
	public void clearObjList() {
		getObjList().clear();
	}
	
	public int getObjCount() {
		return getObjList().size();
	}
	
	public void setEventListener(TemplateListViewEventListener eventListener) {
		if (getAdapter() != null) {
			getAdapter().setEventListener(eventListener);
		}
	}
	
	public TemplateListViewEventListener getEventListener() {
		if (getAdapter() != null) {
			return getAdapter().getEventListener();
		}
		
		return null;
	}
	
	public void setProcessListener(TemplateListViewProcessListener processListener) {
		if (getAdapter() != null) {
			getAdapter().setProcessListener(processListener);
		}
	}
	
	public TemplateListViewProcessListener getProcessListener() {
		if (getAdapter() != null) {
			return getAdapter().getProcessListener();
		}
		
		return null;
	}
	
	public void refreshList() {
		if (getAdapter() != null) {
			logger.d("notifyDataSetChanged: %s", getObjList().size());
			getAdapter().notifyDataSetChanged();
		}
	}

}
