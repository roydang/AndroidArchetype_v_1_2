/*
 * @(#)SimpleDateFormatFactory.java $$version 2011.06.13
 *
 * Copyright 2007 NHN Corp. All rights Reserved.
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package m2.android.archetype.util.date;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import m2.android.archetype.util.StringUtility;

/**
 * SimpleDateFormat 을 직접 사용하지 않고 결과를 재활용하기 위한 용도
 * @author 김백기
 */
public class SimpleDateFormatFactory {
	public static final String TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZZZZ"; //+0900 포맷
	public static final String TIME_FORMAT_2 = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String TIME_FORMAT_3 = "yyyy-MM-dd HH:mm:ss";
	public static final String TIME_FORMAT_4 = "yyyy-MM-dd";
	public static final String TIME_FORMAT_5 = "yyyy_MM_dd_HH_mm_ss";
	
	private static ThreadLocal<Map<String, SimpleDateFormat>> pool = new ThreadLocal<Map<String, SimpleDateFormat>>();

	public static SimpleDateFormat get(String format) {
		return get(format, null);
	}

	public static SimpleDateFormat get(String format, String timeZone) {
		String key = format;

		if (StringUtility.isNotNullOrEmpty(timeZone)) {
			key += " " + timeZone;
		}

		Map<String, SimpleDateFormat> dateFormatPool = pool.get();
		if (dateFormatPool == null) {
			dateFormatPool = new HashMap<String, SimpleDateFormat>();
			pool.set(dateFormatPool);
		}
		
		if (dateFormatPool.containsKey(key) == false) {
			SimpleDateFormat formatter = new SimpleDateFormat(format);

			if (StringUtility.isNotNullOrEmpty(timeZone)) {
				formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
			}

			dateFormatPool.put(key, formatter);
		}

		return dateFormatPool.get(key);
	}
}
