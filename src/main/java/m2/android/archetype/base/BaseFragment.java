package m2.android.archetype.base;

import m2.android.archetype.util.Logger;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment {
	private static Logger logger = Logger.getLogger(BaseFragment.class);
	private static final String LOG_PREFIX = "#BaseFragment# (%s) fragment life-cycle (%s)";


	@Override
	public void onAttach(Activity activity) {
		printLifeCycleLog("onAttach");
		Me2dayApplication.setCurrentApplication(activity);
		Me2dayApplication.setCurrentActivity(activity);
		Me2dayApplication.setCurrentFragmet(this);
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		printLifeCycleLog("onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		printLifeCycleLog("onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		printLifeCycleLog("onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		printLifeCycleLog("onStart");
		super.onStart();
	}

	@Override
	public void onResume() {
		printLifeCycleLog("onResume");
		super.onResume();
	}

	@Override
	public void onPause() {
		printLifeCycleLog("onPause");
		super.onPause();
	}

	@Override
	public void onStop() {
		printLifeCycleLog("onStop");
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		printLifeCycleLog("onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		printLifeCycleLog("onDestroy");
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		printLifeCycleLog("onDetach");
		super.onDetach();
	}

	private void printLifeCycleLog(String func) {
		if (logger.isDebugEnabled()) {
			logger.d(String.format(LOG_PREFIX, this.getClass().getName(), func));
		}
	}

}
