package m2.android.archetype.template;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import m2.android.archetype.util.Logger;
import m2.android.archetype.util.StringUtility;
import m2.android.archetype.widget.BackgroundRelativeLayoutView;
import m2.android.archetype.widget.HtmlTextView;
import m2.android.archetype.widget.RoundProgress;
import m2.android.archetype.widget.UrlImageView;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.nhn.android.archetype.base.image.ImageHelper;
import com.nhn.android.archetype.base.object.BaseObj;

//! 해당 데이터가 없으면 gone 존재하면 visible (에 : !bandType ) 
//~ 해당 데이터가 있으면 gone 없으면 visible (에 : ~bandType )
//# 해당 데이터가 입력한 타입이면 visible 아니면 gone (예 : #Band 혹은 #com.nhn.android.band.object.Band)
//+ 데이터가 존재하지 않으면 android:text 의 값을 쓴다. (예 : +cellphone )
//, 데이터 구분 (예 : author.thumbnail,author.name)
//? default값 (예 : commentsCount?0 )
//: data type (예 : pubDate:date )

public class TemplateManager {
	private static Logger logger = Logger.getLogger(TemplateManager.class);

	private TemplateEventListener eventListener;
	private TemplateNotifyListener notifyListener;
	private List<TagData> viewDatas = null;
	private List<EventData> eventDatas = null;
	private List<BaseObj> dataList = null;
	private Map<Object, List<ProcessedData>> processedDataMap = Collections.synchronizedMap(new HashMap<Object, List<ProcessedData>>());

	private TemplateDataParser parser = new TemplateDataParser();
	private Context context;

	private OnClickListener clickListener;
	private OnLongClickListener longClickListener;

	public TemplateManager(Context context) {
		this.context = context;
	}

	public TemplateManager(Context context, TemplateDataHolder holder) {
		this.context = context;
		
		if (holder != null) {
			this.viewDatas = holder.getViewDatas();
			this.eventDatas = holder.getEventDatas();
		}
	}

	public TemplateEventListener getEventListener() {
		return eventListener;
	}

	public void setEventListener(TemplateEventListener eventListener) {
		this.eventListener = eventListener;
	}

	public TemplateNotifyListener getNotifyListener() {
		return notifyListener;
	}

	public void setNotifyListener(TemplateNotifyListener notifyListener) {
		this.notifyListener = notifyListener;
	}

	public List<BaseObj> getDataList() {
		return dataList;
	}

	public void setDataList(List<BaseObj> dataList) {
		this.dataList = dataList;
	}

	public List<TagData> getViewDatas() {
		return viewDatas;
	}

	public void setViewDatas(List<TagData> viewDatas) {
		this.viewDatas = viewDatas;
	}

	public List<EventData> getEventDatas() {
		return eventDatas;
	}

	public void setEventDatas(List<EventData> eventDatas) {
		this.eventDatas = eventDatas;
	}

	public TemplateDataParser getParser() {
		return parser;
	}

	public void setParser(TemplateDataParser parser) {
		this.parser = parser;
	}

	public Context getContext() {
		return context;
	}

	public void clearCache() {
		processedDataMap = Collections.synchronizedMap(new HashMap<Object, List<ProcessedData>>());
	}
	
	public TemplateDataHolder getDataHolder() {
		TemplateDataHolder holder = new TemplateDataHolder();
		holder.setEventDatas(eventDatas);
		holder.setViewDatas(viewDatas);
		
		return holder;
	}

	private int getTypeId(String type) {
		return type.hashCode() * 100 + type.length();
	}
	
	private TagData getTagData(View view, int type) {
		TagData tagData = new TagData();
		tagData.targetId = view.getId();
		tagData.expr = view.getTag().toString().trim();

		if (view instanceof TextView) {
			tagData.inititalValue = ((TextView) view).getText().toString().trim();
		} else if (view instanceof ImageView) {
			tagData.inititalValue = ((ImageView) view).getDrawable();
		}
		
		if (tagData.expr.startsWith("#")) {
			tagData.type = getTypeId(tagData.expr.substring(1));
			tagData.typeHead = true;
		} else {
			tagData.type = type;
		}

		return tagData;
	}

	@SuppressWarnings("unchecked")
	private final View findViewById(ViewGroup rootView, int id) {
		WeakReference<View> refView = (WeakReference<View>) rootView.getTag(id);
		if (refView == null || refView.get() == null) {
			View view = rootView.findViewById(id);
			rootView.setTag(id, new WeakReference<View>(view));
			
			return view;
		}

		return refView.get();
	}

	private synchronized void checkAndInitViewDatas(ViewGroup rootView, ViewGroup currentRootView, int type) {
		if (viewDatas == null) {
			initViewDatas(rootView, currentRootView, type);
		}
	}
	
	private void initViewDatas(ViewGroup rootView, ViewGroup currentRootView, int type) {
		if (viewDatas == null) {
			viewDatas = new ArrayList<TagData>();
		}

		if (eventDatas == null) {
			eventDatas = new ArrayList<EventData>();
		}

		if (currentRootView == null) {
			currentRootView = rootView;
		}

		int childCount = currentRootView.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View view = currentRootView.getChildAt(i);

			if (view.getId() <= 0) {
				// id가 없으면 동작하지 않는다.
				return;
			}

			TagData currentTagData = null;
			if (view.getTag() != null) {
				currentTagData = getTagData(view, type);
				viewDatas.add(currentTagData);
			}

			EventData eventData = null;
			if (view.isClickable()) {
				eventData = new EventData();
				eventData.targetId = view.getId();
				eventData.expr = "";

				if (view.getTag() != null) {
					eventData.expr = view.getTag().toString().trim();
				}

				eventData.clickable = true;
			}

			if (view.isLongClickable()) {
				if (eventData == null) {
					eventData = new EventData();
					eventData.targetId = view.getId();
				}
				eventData.longClickable = true;
			}

			if (eventData != null) {
				eventDatas.add(eventData);
			}

			if (view instanceof ViewGroup) {
				if (currentTagData != null) {
					initViewDatas(rootView, (ViewGroup) view, currentTagData.type);
				} else {
					initViewDatas(rootView, (ViewGroup) view, type);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private Object getProf(String expr, Object defaultValue, BaseObj obj) {
		if (expr.indexOf(".") > 0) {
			String[] properties = expr.split("\\.");

			Map<String, Object> rootMap = obj.getDataMap();

			for (int i = 0; i < properties.length - 1; i++) {
				rootMap = (Map<String, Object>) rootMap.get(properties[i]);
				if (rootMap == null) {
					return defaultValue;
				}
			}

			Object val = rootMap.get(properties[properties.length - 1]);
			if (val != null) {
				return val;
			} else {
				return defaultValue;
			}
		}

		return obj.get(expr, defaultValue);
	}

	private Object getClassCheck(String expr, BaseObj obj) {
		String v = expr.substring(1);

		if (v.indexOf(".") > 0) {
			return obj.getClass().toString().equals(v);
		} else {
			return obj.getClass().getSimpleName().equals(v);
		}
	}

	private Object getValue(String expr, BaseObj obj) {
		String v = expr.trim();
		String defaultValue = null;
		String type = null;

		if (v.startsWith("#")) {
			return getClassCheck(expr, obj);
		}

		v = getExprKey(v);

		if (v.indexOf("?") > 0) {
			String[] dv = v.split("\\?");
			v = dv[0];
			defaultValue = dv[1];
		}

		if (v.indexOf(":") > 0) {
			String[] tv = v.split("\\:");
			v = tv[0];
			type = tv[1].toLowerCase();
		}

		Object ret = getProf(v, defaultValue, obj);
		if (StringUtility.isNotNullOrEmpty(type) && getParser() != null) {
			ret = getParser().parse(getContext(), type, ret);
		}

		return ret;
	}

	private Object[] getValues(TagData data, BaseObj obj) {
		String[] exprs = null;
		Object[] ret = null;
		
		if (data.expr.indexOf(",") > 0) {
			exprs = data.expr.split("\\,");
			ret = new Object[exprs.length];
		} else {
			exprs = new String[1];
			ret = new Object[1];
			
			exprs[0] = data.expr;
		}

		for (int i = 0; i < exprs.length; i++) {
			ret[i] = getValue(exprs[i], obj);
		}

		return ret;
	}

	@SuppressWarnings("rawtypes")
	private boolean hasData(Object[] value) {
		if (value == null || value.length == 0) {
			return false;
		}

		boolean ret = false;
		for (Object v : value) {
			if (v != null) {

				if (v instanceof Boolean && v.equals(true)) {
					ret = true;
				}

				if (v instanceof Integer && ((Integer) v).intValue() != 0) {
					ret = true;
				}

				if (v instanceof Long && ((Long) v).longValue() != 0) {
					ret = true;
				}

				if (v instanceof List && ((List) v).size() > 0) {
					ret = true;
				}

				if (v instanceof CharSequence) {
					if (v instanceof String) {
						if (StringUtility.isNotNullOrEmpty((String) v) && !v.equals("false")) {
							ret = true;
						}
					} else {
						ret = true;
					}
				}
				
				if (v instanceof Map) {
					ret = true;
				}

				if (ret) {
					break;
				}
			}
		}

		return ret;
	}

	private boolean isShown(View view) {
		while (true) {
			if (view == null) {
				return true;
			}

			if (view.getVisibility() == View.GONE) {
				return false;
			}

			view = (ViewGroup) view.getParent();
		}
	}

	private String getExprKey(String expr) {
		if (expr.startsWith("!") || expr.startsWith("~") || expr.startsWith("+")) {
			return expr.substring(1);
		}

		return expr;
	}

	private void updateSubView(View view, Object[] value, boolean visible, TagData data, BaseObj obj, ProcessedData processedData, boolean cacheOnly) {
		if (view instanceof CompoundButton) {
			processedData.data = visible;
			
			if (!cacheOnly) {
				((CompoundButton) view).setChecked((Boolean) processedData.data);
			}
		} else if (view instanceof RoundProgress) {
			if (value[0] != null) {
				processedData.data = value[0].toString();
			}

			RoundProgress progress = (RoundProgress) view;
			progress.setProgressKey((String) processedData.data);
			if (!cacheOnly && visible) {
				progress.start();
			}
		} else if (view instanceof HtmlTextView) {
			if (StringUtility.isNotNullOrEmpty((String) data.inititalValue)) {
				if (data.expr.startsWith("+")) {
					if (visible) {
						processedData.data = (String) value[0];
					} else {
						processedData.data = (String) data.inititalValue;
					}
				} else {
					String initStr = (String) data.inititalValue;
					if (initStr.indexOf("%") >= 0) {
						processedData.data = StringUtility.safeFormat((String) data.inititalValue, (String) data.inititalValue,value);
					} else {
						if (value[0] != null && value[0] instanceof Boolean == false) {
							processedData.data = value[0].toString();
						} else {
							processedData.data = initStr;
						}
					}
				}

				if (!cacheOnly) {
					((HtmlTextView) view).setHtmlText((String) processedData.data);
				}
			} else {
				if (value[0] != null) {
					processedData.data = value[0].toString();
				}

				if (!cacheOnly) {
					((HtmlTextView) view).setHtmlText((String) processedData.data);
				}
			}
		} else if (view instanceof TextView) {
			if (StringUtility.isNotNullOrEmpty((String) data.inititalValue)) {
				if (data.expr.startsWith("+")) {
					if (visible) {
						processedData.data = (String) value[0];
					} else {
						processedData.data = (String) data.inititalValue;
					}
				} else {
					String initStr = (String) data.inititalValue;
					if (initStr.indexOf("%") >= 0) {
						processedData.data = StringUtility.safeFormat((String) data.inititalValue, (String) data.inititalValue,value);
					} else {
						if (value[0] != null && value[0] instanceof Boolean == false) {
							processedData.data = value[0].toString();
						} else {
							processedData.data = initStr;
						}
					}
				}

				if (processedData.data != null) {
					if (processedData.data instanceof CharSequence == false) {
						processedData.data = processedData.data.toString();
					}

					if (!cacheOnly) {
						((TextView) view).setText((CharSequence) processedData.data);
					}
				}
			} else {
				if (value[0] != null) {
					processedData.data = value[0];
				}

				if (processedData.data != null) {
					if (processedData.data instanceof CharSequence == false) {
						processedData.data = processedData.data.toString();
					}

					if (!cacheOnly) {
						((TextView) view).setText((CharSequence) processedData.data);
					}
				}
			}
		} else if (view instanceof UrlImageView) {
			if (value[0] != null) {
				processedData.data = value[0].toString();
			}

			if (!cacheOnly) {
				((UrlImageView) view).setUrl((String) processedData.data);
			}
		} else if (view instanceof BackgroundRelativeLayoutView) {
			if (value[0] != null) {
				processedData.data = value[0].toString();
			}

			if (!cacheOnly) {
				if (StringUtility.isNotNullOrEmpty((String) processedData.data)) {
					BitmapDrawable bitmapDrawable = new BitmapDrawable(ImageHelper.getFromCache((String) processedData.data));
					view.setBackgroundDrawable(bitmapDrawable.getCurrent());
				}
			}
		}
	}

	private void updateView(View view, TagData data, BaseObj obj, List<ProcessedData> processedDatas, boolean cacheOnly) {
		Object[] value = getValues(data, obj);
		ProcessedData processedData = new ProcessedData();
		processedData.targetId = view.getId();
		processedData.expr = data.expr;
		processedDatas.add(processedData);

		boolean visible = hasData(value);
		if (data.expr.startsWith("!")) {
			if (visible) {
				processedData.visible = true;
			} else {
				processedData.visible = false;
			}
		} else if (data.expr.startsWith("~")) {
			if (visible) {
				processedData.visible = false;
			} else {
				processedData.visible = true;
			}
		} else if (data.expr.startsWith("#")) {
			if (visible) {
				processedData.visible = true;
			} else {
				processedData.visible = false;
			}
		} else {
			processedData.visible = true;
		}

		if (!cacheOnly) {
			view.setVisibility(processedData.visible ? View.VISIBLE : View.GONE);
		}
		
		if (processedData.visible == false) {
			return;
		}

		if (!isShown(view)) {
			processedData.visible = false;
			return;
		}

		updateSubView(view, value, visible, data, obj, processedData, cacheOnly);
	}

	private void processRadioButtonEvent(RadioButton btn, final EventData eventData, final BaseObj obj) {
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String exprKey = getExprKey(eventData.expr);
				int pos = 0;

				if (getDataList() != null) {
					for (int i = 0; i < getDataList().size(); i++) {
						BaseObj baseObj = getDataList().get(i);

						if (baseObj == obj) {
							pos = i;
							baseObj.put(exprKey, true);
						} else {
							baseObj.put(exprKey, false);
						}
					}
				}

				logger.d("setRadioClick(%s)", pos);
				Iterator<Entry<Object, List<ProcessedData>>> iter = processedDataMap.entrySet().iterator();
				for (;iter.hasNext();) {
					Entry<Object, List<ProcessedData>> entry = iter.next();
					Object key = entry.getKey();
					List<ProcessedData> value = entry.getValue();

					boolean currentItem = false;
					if (key instanceof Integer && ((Integer) key).equals(pos)) {
						currentItem = true;
					}

					for (ProcessedData data : value) {
						if (StringUtility.isNotNullOrEmpty(data.expr) && data.expr.equalsIgnoreCase(eventData.expr)) {
							data.data = currentItem;
						}
					}
				}

				getEventListener().onViewClicked(v, obj);

				if (getNotifyListener() != null) {
					getNotifyListener().onNotify();
				}
			}
		});
	}

	private void processCheckBoxEvent(CheckBox btn, final EventData eventData, final BaseObj obj) {
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String exprKey = getExprKey(eventData.expr);
				int pos = 0;

				if (getDataList() != null) {
					for (int i = 0; i < getDataList().size(); i++) {
						BaseObj baseObj = getDataList().get(i);

						if (baseObj == obj) {
							pos = i;
							baseObj.put(exprKey, true);
						}
					}
				}

				logger.d("setCheckBoxClick(%s)", pos);
				
				Iterator<Entry<Object, List<ProcessedData>>> iter = processedDataMap.entrySet().iterator();
				
				for (;iter.hasNext();) {
					Entry<Object, List<ProcessedData>> entry = iter.next();
					
					Object key = entry.getKey();
					List<ProcessedData> value = entry.getValue();

					boolean currentItem = false;
					if (key instanceof Integer && ((Integer) key).equals(pos)) {
						currentItem = true;
					}

					for (ProcessedData data : value) {
						if (StringUtility.isNotNullOrEmpty(data.expr) && data.expr.equalsIgnoreCase(eventData.expr)) {
							data.data = currentItem;
						}
					}
				}

				getEventListener().onViewClicked(v, obj);

				if (getNotifyListener() != null) {
					getNotifyListener().onNotify();
				}
			}
		});
	}
	
	private OnClickListener getOnClickListener() {
		if (clickListener == null) {
			clickListener = new OnClickListener() {
				public void onClick(View v) {
					getEventListener().onViewClicked(v, (BaseObj) v.getTag());
				}
			};
		}
		
		return clickListener;
	}
	
	private OnLongClickListener getOnLongClickListener() {
		if (longClickListener == null) {
			longClickListener = new OnLongClickListener() {
				public boolean onLongClick(View v) {
					return getEventListener().onViewLongClicked(v, (BaseObj) v.getTag());
				}
			};
		}
		
		return longClickListener;
	}
	
	private synchronized void putIntoProcessDataMap(Object key, List<ProcessedData> processedData) {
		if (!processedDataMap.containsKey(key)) {
			processedDataMap.put(key, processedData);
		}
	}

	private void processViewEvent(ViewGroup rootView, final BaseObj obj) {
		if (eventListener == null) {
			return;
		}

		for (EventData eventData : eventDatas) {
			View view = findViewById(rootView, eventData.targetId);
			view.setTag(obj);

			if (eventData.clickable) {
				if (view instanceof RadioButton) {
					processRadioButtonEvent((RadioButton) view, eventData, obj);
				} else if (view instanceof CheckBox) {
					processCheckBoxEvent((CheckBox) view, eventData, obj);
				} else {
					view.setOnClickListener(getOnClickListener());
				}
			}

			if (eventData.longClickable) {
				view.setOnLongClickListener(getOnLongClickListener());
			}
		}
	}

	public void processView(ViewGroup rootView, BaseObj obj) {
		processView(0, rootView, null, obj);
	}
	
	public void processView(Object key, ViewGroup rootView, List<BaseObj> dataList, BaseObj obj) {
		processView(key, rootView, dataList, obj, false);
	}

	public void processView(Object key, ViewGroup rootView, List<BaseObj> dataList, BaseObj obj, boolean cacheOnly) {
		this.dataList = dataList;

		//long startTime = System.nanoTime();

		checkAndInitViewDatas(rootView, null, 0);
		
		if (!cacheOnly) {
			processViewEvent(rootView, obj);
		
			List<ProcessedData> processedData = (List<ProcessedData>) processedDataMap.get(key);
			if (processedData != null) {
				for (ProcessedData data : processedData) {
					View view = findViewById(rootView, data.targetId);
	
					if (view != null) {
						view.setVisibility(data.visible ? View.VISIBLE : View.GONE);
	
						if (data.data == null && !data.visible) {
							continue;
						}
	
						if (view instanceof CompoundButton) {
							((CompoundButton) view).setChecked((Boolean) data.data);
						} else if (view instanceof RoundProgress) {
							RoundProgress progress = (RoundProgress) view;
							progress.setProgressKey((String) data.data);
							progress.start();
						} else if (view instanceof HtmlTextView) {
							((HtmlTextView) view).setHtmlText((String) data.data);
						} else if (view instanceof TextView) {
							((TextView) view).setText((CharSequence) data.data);
						} else if (view instanceof UrlImageView) {
							((UrlImageView) view).setUrl((String) data.data);
						} else if (view instanceof BackgroundRelativeLayoutView) {
							BitmapDrawable bitmapDrawable = new BitmapDrawable(ImageHelper.getFromCache((String) data.data));
							view.setBackgroundDrawable(bitmapDrawable.getCurrent());
						}
					}
				}
				
				return;
			}
		}

		List<ProcessedData> processedData = new ArrayList<ProcessedData>();
		if (!cacheOnly) {
			putIntoProcessDataMap(key, processedData);
		}
		
		int currentObjType = getTypeId(obj.getClass().getSimpleName());
		
		for (TagData tagData : viewDatas) {
			if (tagData.typeHead == false && tagData.type != 0 && tagData.type != currentObjType) {
				continue;
			}
			
			View view = findViewById(rootView, tagData.targetId);
			if (view != null) {
				updateView(view, tagData, obj, processedData, cacheOnly);
			}
		}
		
		if (cacheOnly) {
			putIntoProcessDataMap(key, processedData);
		}
	}
	
	public boolean hasProcessCache(Object key) {
		return processedDataMap.containsKey(key);
	}

	private static class TagData {
		private int targetId;
		private String expr;
		private Object inititalValue;
		private int type;
		private boolean typeHead;
	}

	private static class EventData {
		private int targetId;
		private String expr;
		private boolean clickable;
		private boolean longClickable;
	}

	private static class ProcessedData {
		private int targetId;
		private String expr;
		private Object data;
		private boolean visible;
	}
	
	public static class TemplateDataHolder {
		private List<TagData> viewDatas;
		private List<EventData> eventDatas;
		
		public List<TagData> getViewDatas() {
			return viewDatas;
		}
		
		public void setViewDatas(List<TagData> viewDatas) {
			this.viewDatas = viewDatas;
		}
		
		public List<EventData> getEventDatas() {
			return eventDatas;
		}
		
		public void setEventDatas(List<EventData> eventDatas) {
			this.eventDatas = eventDatas;
		}
	}
}
