package m2.android.archetype.example.ormlite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import m2.android.archetype.api.BaseProtocol;
import m2.android.archetype.base.BaseFragment;
import m2.android.archetype.example.R;
import m2.android.archetype.example.ormlite.object.Media;
import m2.android.archetype.example.ormlite.object.MetooPost;
import m2.android.archetype.example.ormlite.object.Multimedia;
import m2.android.archetype.example.ormlite.object.Post;
import m2.android.archetype.example.ormlite.object.Stream;
import m2.android.archetype.example.ormlite.object.Streams;
import m2.android.archetype.sharedpref.UserSharedPrefModel;
import m2.android.archetype.template.TemplateListViewEventListener;
import m2.android.archetype.util.Logger;
import m2.android.archetype.util.Utility;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.nhn.android.archetype.base.object.ApiResponse;
import com.nhn.android.archetype.base.object.BaseObj;
import com.nhn.android.archetype.base.worker.JsonWorker;
import com.nhn.android.archetype.base.worker.listener.PreloadJsonListener;

@SuppressLint("ParserError")
public class StreamStoryFragment extends BaseFragment {
	private static Logger logger = Logger.getLogger(StreamStoryFragment.class);
	
	private View rootView;
	private M3PullToRefreshListView listView; 
	List<BaseObj> objList;
	private final String WRITE_MY_METOO_DIALOG_ID = "write_my_metoo";
	
	public StreamStoryFragment() {
		super();
	}
	
	@Override
	public void onAttach(Activity activity) { 
		super.onAttach(activity); 
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_stream_story, null);
	    return rootView;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	    initUI();
	}

	public void initUI() { 
		listView = (M3PullToRefreshListView) rootView.findViewById(R.id.stream_story_list);
		listView.setLayoutId(R.layout.fragment_stream_story_item);
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				
				new GetDataTask().execute();
//				loadData();
			}
			
		});
		
		listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				Toast.makeText(getActivity(), "End of List!", Toast.LENGTH_SHORT).show();
			}
		});
		
		loadData();

	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(1000);
			
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			loadData();
//			
//			listView.addObj(0, objList.get(objList.size()-1));
//			listView.refreshList();
//
//			// Call onRefreshComplete when the list has been refreshed.
			listView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}
	private void updatePostList(BaseObj response) {
		Streams streams = response.as(Streams.class);
		List<Stream> streamList = streams.getStream();
		objList = new ArrayList<BaseObj>();
	
		for (Stream stream : streamList) {
			if (stream.contains("post")) {
				Post post = stream.getPost();
				objList.add(post);
				
				Media media = post.getMedia();
				if (media != null && Utility.isNotNullOrEmpty(media.getType())) {
					String type = media.getType();
					post.put("has_"+type, true);
				}
				
				List<Multimedia> multiMedia = post.getMultimedia();
				if(multiMedia != null) {
					for(int i = 0; i < multiMedia.size(); i++) {
						Multimedia obj =  multiMedia.get(i);
						if (obj.getType() == "me2movie") {
							post.put("has_me2movie_obj", obj);
						}
					}
				}
				
			} else if (stream.contains("metoo_post")) {
				MetooPost metooPost = stream.getMetooPost();
				objList.add(metooPost);
			}
		}
		
		
		
		listView.clearObjList();
		listView.addAllObjList(objList);
		listView.refreshList();
		listView.onRefreshComplete();
		listView.setEventListener(new TemplateListViewEventListener() {
			public void onItemClicked(View view, BaseObj baseObj) {

//				Intent intent = new Intent(getActivity(), PostViewActivity.class);
//				intent.putExtra(ParameterConstants.PARAM_POST_OBJ, baseObj);
//				startActivity(intent);
				
			}

			public boolean onItemLongClicked(View view, BaseObj baseObj) {
				return false;
			}

			public void onViewClicked(View view, BaseObj baseObj) {
				
			}

			public boolean onViewLongClicked(View view, BaseObj baseObj) {
				return false;
			}
		});
	}

	private void loadData() {
		UserSharedPrefModel userPref = UserSharedPrefModel.get();
		userPref.setFullAuthToken("e60441b2282721247261d04b41b11f07");
		userPref.setUserId("chs_test");
		String url = BaseProtocol.getStream();
		JsonWorker jsonWorker = new JsonWorker(url, new PreloadJsonListener() {
			
			@Override
			public void onSuccess(BaseObj response) {
				logger.d("on Sccess response=%s", response);
				if (response != null) {
					updatePostList(response);
				
				}
				
			}
			
			@Override
			public void onError(int statusCode,  ApiResponse result) {
				logger.d("on onError response=%s statusCode=%s", result, statusCode);
				
			}
			
			@Override
			public void onPreload(BaseObj response, Date cachedDate) {
				logger.d("on onPreload Sccess response=%s", response);
				if (response != null) {
					updatePostList(response);
				}
				
			}

		
		});
		jsonWorker.post();
	}

}
