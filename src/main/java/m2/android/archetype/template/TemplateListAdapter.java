/*
 * TemplateListAdapter.java $version 2012. 09. 13
 *
 * Copyright 2012 NHN Corp. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package m2.android.archetype.template;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

import m2.android.archetype.util.AppInfoUtility;
import m2.android.archetype.util.Logger;
import m2.android.archetype.util.StringUtility;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.nhn.android.archetype.base.object.BaseObj;

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
public class TemplateListAdapter extends ArrayAdapter<BaseObj> {
	private static Logger logger = Logger.getLogger(TemplateListAdapter.class);
	
	private TemplateListViewProcessListener processListener;
	private TemplateListViewEventListener eventListener;
	private TemplateListCacheGenerator cacheGen;
	
	private List<BaseObj> objList;
	private TemplateManager templateManager;
	
	private int layoutId;
	private int groupMode = TemplateListView.GROUP_NONE;
	private String groupKey;
	private boolean useMultithreadCacheGan = true;
	
	private GroupSorter manualGroupSorter = null;

	private OnClickListener clickListener;
	private OnLongClickListener longClickListener;

	public TemplateListAdapter(Context context, int layoutId, List<BaseObj> objList) {
		super(context, layoutId, objList);
		this.objList = objList;
		this.layoutId = layoutId;
	}

	public List<BaseObj> getObjList() {
		return objList;
	}
	
	public void addAllObjList(List<? extends BaseObj> objList) {
		getObjList().addAll(objList);
	}
	
	public void addObj(int idx, BaseObj obj) {
		getObjList().add(idx, obj);
	}
	
	public void clearObjList() {
		getObjList().clear();
	}
	
	public TemplateManager getTemplateManager() {
		if (templateManager == null) {
			templateManager = new TemplateManager(getContext());
			templateManager.setEventListener(new TemplateEventListener() {
				public boolean onViewLongClicked(View view, BaseObj baseObj) {
					if (getEventListener() != null) {
						return getEventListener().onViewLongClicked(view, baseObj);
					}
					
					return false;
				}
				
				public void onViewClicked(View view, BaseObj baseObj) {
					if (getEventListener() != null) {
						getEventListener().onViewClicked(view, baseObj);
					}
				}
			});
			
			templateManager.setNotifyListener(new TemplateNotifyListener() {
				public void onNotify() {
					notifyDataSetChanged();
				}
			});
		}
		
		return templateManager;
	}

	public void setTemplateManager(TemplateManager templateManager) {
		this.templateManager = templateManager;
	}

	public TemplateListViewProcessListener getProcessListener() {
		return processListener;
	}

	public void setProcessListener(TemplateListViewProcessListener processListener) {
		this.processListener = processListener;
	}

	public TemplateListViewEventListener getEventListener() {
		return eventListener;
	}

	public void setEventListener(TemplateListViewEventListener eventListener) {
		this.eventListener = eventListener;
	}

	public TemplateDataParser getParser() {
		return getTemplateManager().getParser();
	}

	public void setParser(TemplateDataParser parser) {
		getTemplateManager().setParser(parser);
	}

	public boolean isMultithreadCacheGan() {
		return useMultithreadCacheGan;
	}

	public void setMultithreadCacheGan(boolean useMultithreadCacheGan) {
		this.useMultithreadCacheGan = useMultithreadCacheGan;
	}

	@Override
	public BaseObj getItem(int position) {
		if (position < getObjList().size()) {
			return getObjList().get(position);
		}
		
		return null;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}
	
	public int getGroupMode() {
		return groupMode;
	}

	public void setGroupMode(int groupMode) {
		this.groupMode = groupMode;
	}

	public String getGroupKey() {
		return groupKey;
	}

	public void setGroupKey(String groupKey) {
		this.groupKey = groupKey;
	}

	public GroupSorter getManualGroupSorter() {
		return manualGroupSorter;
	}

	public void setManualGroupSorter(GroupSorter manualGroupSorter) {
		this.manualGroupSorter = manualGroupSorter;
	}

	private OnClickListener getOnClickListener() {
		if (clickListener == null) {
			clickListener = new OnClickListener() {
				public void onClick(View v) {
					if (getEventListener() != null) {
						getEventListener().onItemClicked(v, (BaseObj) v.getTag());
					}
				}
			};
		}
		
		return clickListener;
	}
	
	private OnLongClickListener getOnLongClickListener() {
		if (longClickListener == null) {
			longClickListener = new OnLongClickListener() {
				public boolean onLongClick(View v) {
					if (getEventListener() != null) {
						return getEventListener().onItemLongClicked(v, (BaseObj) v.getTag());
					}
					
					return false;
				}
			};
		}
		
		return longClickListener;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflator = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		BaseObj baseObj = getItem(position);

		if (convertView == null) {
			convertView = inflator.inflate(layoutId, null);
		}
		
		convertView.setTag(baseObj);
		convertView.setOnClickListener(getOnClickListener());
		convertView.setOnLongClickListener(getOnLongClickListener());
		
		long startTime = System.currentTimeMillis();
		getTemplateManager().processView(position, (ViewGroup) convertView, getObjList(), baseObj);
		
		if (getProcessListener() != null) {
			getProcessListener().onProcessView(position, convertView, baseObj);
		}

		long elapsedTime = System.currentTimeMillis() - startTime;
		
		if (elapsedTime > 100) {
			logger.w("getView시간이 너무 오래걸려요! : %s / %s", elapsedTime, position);
		}
		
		return convertView;
	}
	
	private void groupSort() {
		List<GroupedBaseObjList> groupedBaseObjList = null;
		
		switch (getGroupMode()) {
		case TemplateListView.GROUP_NONE:
			return;
			
		case TemplateListView.GROUP_ALPHA:
			groupedBaseObjList = new GroupNameSorter().group(getContext(), getObjList(), getGroupKey(), true);
			break;
			
		case TemplateListView.GROUP_ALPHA_DESC:
			groupedBaseObjList = new GroupNameSorter().group(getContext(), getObjList(), getGroupKey(), false);
			break;
			
		case TemplateListView.GROUP_DATE:
			groupedBaseObjList = new GroupDateSorter().group(getContext(), getObjList(), getGroupKey(), true);
			break;
			
		case TemplateListView.GROUP_DATE_DESC:
			groupedBaseObjList = new GroupDateSorter().group(getContext(), getObjList(), getGroupKey(), false);
			break;
			
		case TemplateListView.GROUP_SCHEDULE:
			groupedBaseObjList = new GroupScheduleSorter().group(getContext(), getObjList(), getGroupKey(), true);
			break;
			
		case TemplateListView.GROUP_SCHEDULE_DESC:
			groupedBaseObjList = new GroupScheduleSorter().group(getContext(), getObjList(), getGroupKey(), false);
			break;
			
		case TemplateListView.GROUP_MANUAL:
			groupedBaseObjList = getManualGroupSorter().group(getContext(), getObjList(), getGroupKey(), true);
			break;
			
		case TemplateListView.GROUP_MANUAL_DESC:
			groupedBaseObjList = getManualGroupSorter().group(getContext(), getObjList(), getGroupKey(), false);
			break;
			
		}
		
		for (BaseObj obj : getObjList()) {
			obj.put(TemplateListView.GROUP_HEADER_KEY, false);
		}
		
		if (groupedBaseObjList != null) {
			List<BaseObj> newObjList = new ArrayList<BaseObj>();
			for (GroupedBaseObjList gbo : groupedBaseObjList) {
				List<BaseObj> groupList = gbo.getObjList();
				BaseObj headerObj = groupList.get(0);
				headerObj.put(TemplateListView.GROUP_HEADER_KEY, true);
				headerObj.put(TemplateListView.GROUP_HEADER_TEXT_KEY, gbo.getHeaderText());
				newObjList.addAll(gbo.getObjList());
			}
			
			objList.clear();
			objList.addAll(newObjList);
		}
	}
	
	private WeakReference<ViewGroup> tempViewRef;
	private int tempLayoutId = 0;
	//private int lastObjCount = 0;
	
	@SuppressLint("NewApi")
	@Override
	public void notifyDataSetChanged() {
		getTemplateManager().clearCache();
		
		boolean notifyList = true;
		
		if (groupMode != TemplateListView.GROUP_NONE && StringUtility.isNotNullOrEmpty(groupKey)) {
			groupSort();
		}
		
		if (useMultithreadCacheGan) {
			ViewGroup tempView = null;
			
			if (tempViewRef != null) {
				tempView = tempViewRef.get();
				
				if (tempView == null) {
					tempViewRef = null;
				}
			}
			
			if (tempView == null || tempLayoutId != layoutId) {
				try {
					LayoutInflater inflator = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					tempView = (ViewGroup) inflator.inflate(layoutId, null);
					tempViewRef = new WeakReference<ViewGroup>(tempView);
					tempLayoutId = layoutId;
				} catch (RejectedExecutionException ree) {
					logger.e(ree);
				} catch (Exception e) {
					logger.e(e);
				} catch (Error err) {
					logger.e(err);
				}	
			}

			if (tempView != null) {
				if (cacheGen != null) {
					cacheGen.cancel();
				}
				
				cacheGen = new TemplateListCacheGenerator(this, getTemplateManager(), tempView, objList, false);
				
				if (AppInfoUtility.isICSCompatibility()) {
					cacheGen.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				} else {
					cacheGen.execute();
				}
			}
		}
		
		if (notifyList) {
			forceNotifyDataSetChanged();
		}
	}
	
	protected final void forceNotifyDataSetChanged() {
		super.notifyDataSetChanged();
	}
	
	public void clearViewCache() {
		getTemplateManager().clearCache();
	}
	
	private static class TemplateListCacheGenerator extends AsyncTask<Void, Void, Void> {
		private TemplateListAdapter adapter;
		private TemplateManager templateManager;
		private ViewGroup tempView;
		private volatile boolean cancel = false;
		private List<BaseObj> objList;
		private boolean notifyList = false;
		
		public TemplateListCacheGenerator(TemplateListAdapter adapter, TemplateManager templateManager, ViewGroup tempView, List<BaseObj> objList, boolean notifyList) {
			this.adapter = adapter;
			this.templateManager = templateManager;
			this.tempView = tempView;
			this.objList = objList;
			this.notifyList = notifyList;
		}

		public void cancel() {
			cancel = true;
			templateManager = null;
			tempView = null;
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			try {
				for (int i=objList.size()-1;i>=0;i--) {
					// objList가 invalidate 된 경우 체크
					// objList를 thread-safe하게 만들지 않는 이유는 CacheGen이 반드시 정상적으로 돌 필요는 없기 때문이다
					if (i >= objList.size()) {
						return null;
					}
					
					if (cancel == true || templateManager == null) {
						return null;
					}
					
					if (templateManager.hasProcessCache(i)) {
						continue;
					}

					templateManager.processView(i, tempView, objList, objList.get(i), true);
				}
			} catch (Exception e) {
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (notifyList) {
				adapter.forceNotifyDataSetChanged();
			}
		}
	}
}
