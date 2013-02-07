/*
 * @(#)LocaleUtility.java $$version ${date}
 *
 * Copyright 2007 NHN Corp. All rights Reserved.
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package m2.android.archetype.util;

import java.util.Locale;

import m2.android.archetype.base.M3Application;
import android.content.Context;
import android.content.res.Configuration;

/**
 *
 * @author nhn
 */
public class LocaleUtility {
	public static final String CATEGORY = LocaleUtility.class.getSimpleName();
	private static final String VIETNAM_LANGUAGE = "vi";

	public static void updateDefaultLocale(Context context) {
		Locale locale = getSystemLocale(context);
		Locale.setDefault(locale);

		Configuration config = new Configuration();
		config.locale = locale;
		context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
	}

	public static boolean isLocaleKorean(Context context) {
		Locale locale = getSystemLocale(context);
		if (locale.getLanguage().equals(Locale.KOREA.getLanguage())) {
			return true;
		}
		return false;
	}

	public static boolean isLocaleEnglish(Context context) {
		Locale locale = getSystemLocale(context);
		if (locale.getLanguage().equals(Locale.US.getLanguage())) {
			return true;
		}
		return false;
	}
	
	public static boolean isLocaleLineBand(Context context) {
		Locale locale = context.getResources().getConfiguration().locale;
		
		String country = locale.getCountry();
		
		// 일본 , 대만 , 태국
		String[] countries = new String[] {"kr", "jp", "tw", "th"};
		for (String c : countries) {
			if (c.equalsIgnoreCase(country)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isLocaleMe2day(Context context) {
		Locale locale = context.getResources().getConfiguration().locale;
		
		String country = locale.getCountry();
		
		// 한국, 미국, 한국, 일본, 중국(간체), 번체
		String[] countries = new String[] {Locale.KOREA.getCountry(), Locale.US.getCountry(), Locale.JAPAN.getCountry(), Locale.CHINA.getCountry(), Locale.TAIWAN.getCountry()};
		for (String c : countries) {
			if (c.equalsIgnoreCase(country)) {
				return true;
			}
		}
		return false;
	}

	public static Locale getSystemLocale(Context context) {
		if (context == null) {
			context = M3Application.getCurrentApplication();
		}

		if (context == null) {
			return Locale.KOREA;
		}

		Locale locale = context.getResources().getConfiguration().locale;
		String language = locale.getLanguage();
		String country = locale.getCountry();

		if (country.equals(Locale.TRADITIONAL_CHINESE.getCountry())) {
			return Locale.TRADITIONAL_CHINESE;
		}

		if (language.equals(Locale.KOREA.getLanguage())) {
			return Locale.KOREA;
		}

		if (language.equals(Locale.CHINA.getLanguage())) {
			return Locale.CHINA;
		}

		if (language.equals(Locale.JAPAN.getLanguage())) {
			return Locale.JAPAN;
		}

		return Locale.US;
	}

	public static String getSystemLocaleString(Context context) {
		Locale locale = getSystemLocale(context);
		return locale.toString().replace("_", "-");
	}

	public static String getSystemLocaleString(Locale locale) {
		return locale.toString().replace("_", "-");
	}

	public static String getSystemLocaleStringUsedAppStat(Context context) {
		Locale mlocale = context.getResources().getConfiguration().locale;
		return mlocale.toString().replace("_", "-");
	}
	
	public static String getSystemLanagage(Context context) {
		Locale locale = getSystemLocale(context);
		return locale.getLanguage();
	}
	
	public static boolean isEnglshLanagage(Context context) {
		boolean retVal = false;
		String systemLanguage = getSystemLocale(context).getLanguage();
		if ("en".equalsIgnoreCase(systemLanguage)) {
			retVal = true;
		}
		return retVal;
	}
	
	public static boolean isKoreanLanagage(Context context) {
		boolean retVal = false;
		String systemLanguage = getSystemLocale(context).getLanguage();
		if ("ko".equalsIgnoreCase(systemLanguage)) {
			retVal = true;
		}
		return retVal;
	}
}
