package m2.android.archetype.widget;

import java.util.HashMap;

import m2.android.archetype.util.StringUtility;

public class ProgressManager {
	private static HashMap<String, ProgressAdapter> adapterMap;
	
	public static final synchronized void put(String id, ProgressAdapter adapter) {
		if (StringUtility.isNullOrEmpty(id) || adapter == null) {
			return;
		}
	
		if (adapterMap == null) {
			adapterMap = new HashMap<String, ProgressAdapter>();
		}
		
		adapterMap.put(id, adapter);
	}
	
	public static final synchronized ProgressAdapter get(String id) {
		if (StringUtility.isNullOrEmpty(id)) {
			return null;
		}
		
		if (adapterMap == null) {
			adapterMap = new HashMap<String, ProgressAdapter>();
		}
		
		return adapterMap.get(id);
	}
	
	public static final synchronized void remove(String id) {
		if (StringUtility.isNullOrEmpty(id)) {
			return;
		}
	
		if (adapterMap == null) {
			adapterMap = new HashMap<String, ProgressAdapter>();
		}
		
		if (adapterMap.containsKey(id)) {
			adapterMap.remove(id);		
		}
	}
	
	public static final synchronized void clear() {
		adapterMap = null;
	}
	
	public interface ProgressAdapter {
		int getMax();
		int getProgress();
	}
}
