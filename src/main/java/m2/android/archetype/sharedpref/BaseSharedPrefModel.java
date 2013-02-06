/*
 * @(#)BaseSharedPrefModel.java $$version ${date}
 *
 * Copyright 2007 NHN Corp. All rights Reserved.
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package m2.android.archetype.sharedpref;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import m2.android.archetype.base.Me2dayApplication;
import m2.android.archetype.util.Logger;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhn.android.archetype.base.object.BaseObj;


/**
 *
 * @author nhn
 */
public class BaseSharedPrefModel {
	
	private static Logger logger = Logger.getLogger(BaseSharedPrefModel.class);
	
	private String prefName;
	private int prefMode;

	private boolean autoCommit = true;

	private Context context;

	private Map<String, Object> dataMap;
	private Map<String, Object> dirtyMap;

	public BaseSharedPrefModel() {
	}

	public BaseSharedPrefModel(Context context) {
		setContext(context);
	}

	public BaseSharedPrefModel(Context context, String prefName, int prefMode) {
		setContext(context);
		setPrefName(prefName);
		setPrefMode(prefMode);
	}

	public BaseSharedPrefModel(Context context, String prefName, int prefMode, boolean userDependent) {
		setContext(context);
		setPrefName(prefName);
		setPrefMode(prefMode);
	}

	public String getPrefName() {
		return prefName;
	}

	public void setPrefName(String prefName) {
		this.prefName = prefName;
	}

	public int getPrefMode() {
		return prefMode;
	}

	public void setPrefMode(int prefMode) {
		this.prefMode = prefMode;
	}

	public boolean getAutoCommit() {
		return autoCommit;
	}

	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
	}

	public Context getContext() {
		if (context == null) {
			return Me2dayApplication.getCurrentApplication();
		}

		return context;
	}

	public void setContext(Context context) {
		this.context = context.getApplicationContext();
	}

	private SharedPreferences getSharedPreferences() {
		//exception !
		return getContext().getSharedPreferences(getPrefName(), getPrefMode());
	}

	protected Map<String, Object> getDataMap() {
		if (dataMap == null) {
			dataMap = new HashMap<String, Object>();
		}

		return dataMap;
	}

	protected void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	protected Map<String, Object> getDirtyMap() {
		if (dirtyMap == null) {
			dirtyMap = new HashMap<String, Object>();
		}

		return dirtyMap;
	}

	protected void setDirtyMap(Map<String, Object> dirtyMap) {
		this.dirtyMap = dirtyMap;
	}

	public BaseObj getList(String key) {
		
		String jsonStr = (String)get(key);
		BaseObj baseObj = BaseObj.parse(jsonStr);
		
		return baseObj;
	}
	public Object get(String key) {
		return get(key, null);
	}

	public Object get(String key, Object defaultValue) {
		if (!getDataMap().containsKey(key)) {
			SharedPreferences pref = getSharedPreferences();
			if (pref.contains(key)) {
				Map<String , ?> prefData = pref.getAll();

				for (Entry<String, ?> entry : prefData.entrySet()) {
					if (!getDataMap().containsKey(entry.getKey())) {
						getDataMap().put(entry.getKey(), entry.getValue());
						getDirtyMap().put(entry.getKey(), entry.getValue());
					}
				}
			}
		}

		if (getDataMap().containsKey(key)) {
			return getDataMap().get(key);
		}

		return defaultValue;
	}

	@SuppressLint("NewApi")
	public void put(final String key, final Object value) {
		if (getAutoCommit()) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
				SharedPreferences pref = getSharedPreferences();

				Editor editor = pref.edit();
				putData(editor, key, value);
				editor.apply();
			} else {
				SharedPreferences pref = getSharedPreferences();

				Editor editor = pref.edit();
				putData(editor, key, value);
				editor.commit();
			}
		}

		getDataMap().put(key, value);
	}
	
	@SuppressLint("NewApi")
	public void putList(final String key, final List<?> listObj) {
		String value = "";
		
		if (getAutoCommit()) {			
			
			ObjectMapper mapper = new ObjectMapper();
			try {				
				String jsonStr = mapper.writeValueAsString(listObj);
				value = jsonStr;		
			} catch (Exception e) {
				logger.e(e);
				return;
			}			
			
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
				SharedPreferences pref = getSharedPreferences();

				Editor editor = pref.edit();
				putData(editor, key, value);
				editor.apply();
			} else {
				SharedPreferences pref = getSharedPreferences();

				Editor editor = pref.edit();
				putData(editor, key, value);
				editor.commit();
			}
		}

		getDataMap().put(key, value);
	}
	
	public void put(String key, boolean value) {
		put(key, Boolean.valueOf(value));
	}

	public void put(String key, int value) {
		put(key, Integer.valueOf(value));
	}

	public void put(String key, long value) {
		put(key, Long.valueOf(value));
	}

	@SuppressLint("NewApi")
	public void commit() {
		if (getAutoCommit()) {
			return;
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			SharedPreferences pref = getSharedPreferences();
			Editor editor = pref.edit();

			for (Entry<String, Object> entry : getDataMap().entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();

				if (!getDirtyMap().containsKey(key) || getDirtyMap().get(key) != value) {
					putData(editor, key, value);
					getDirtyMap().put(key, value);
				}
			}

			editor.apply();
		} else {
			SharedPreferences pref = getSharedPreferences();
			Editor editor = pref.edit();

			for (Entry<String, Object> entry : getDataMap().entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();

				if (!getDirtyMap().containsKey(key) || getDirtyMap().get(key) != value) {
					putData(editor, key, value);
					getDirtyMap().put(key, value);
				}
			}

			editor.commit();
		}
		
		setAutoCommit(true);
	}

	@SuppressLint("NewApi")
	public void clear() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			SharedPreferences pref = getSharedPreferences();
			Editor editor = pref.edit();
			editor.clear();
			editor.apply();
		} else {
			SharedPreferences pref = getSharedPreferences();
			Editor editor = pref.edit();
			editor.clear();
			editor.commit();
		}
		
		getDataMap().clear();
		getDirtyMap().clear();
	}

	private void putData(Editor editor, String key, Object value) {
		if (value instanceof Integer) {
			editor.putInt(key, (Integer)value);
		} else if (value instanceof Boolean) {
			editor.putBoolean(key, (Boolean)value);
		} else if (value instanceof Long) {
			editor.putLong(key, (Long)value);
		} else if (value instanceof Float) {
			editor.putFloat(key, (Float)value);
		} else {
			if (value != null) {
				editor.putString(key, value.toString());
			} else {
				editor.putString(key, null);
			}
		}
	}
}
