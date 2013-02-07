package m2.android.archetype.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import m2.android.archetype.base.BaseConstants;
import m2.android.archetype.base.M3Application;
import m2.android.archetype.example.R;
import m2.android.archetype.util.Logger;
import m2.android.archetype.util.Utility;
import android.content.Context;
import android.view.View;

public class DateUtility {
	private static Logger logger = Logger.getLogger(DateUtility.class);
	
	public static final String TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZZZZ"; //+0900 포맷
	public static final String TIME_FORMAT_2 = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String TIME_FORMAT_3 = "yyyy-MM-dd HH:mm:ss";
	public static final String TIME_FORMAT_4 = "yyyy-MM-dd";
	public static final String TIME_FORMAT_5 = "yyyy_MM_dd_HH_mm_ss";
	public static final String TIME_FORMAT_6 = "'T'HH:mm:ss";

	public static final String FIRST_DATE = "st";
	public static final String SECOND_DATE = "nd";
	public static final String THIRD_DATE = "rd";
	public static final String OTHER_DATE = "th";
	
	public static String[] dayOfWeek = {"", "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
	public static String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	
	private static final long RIGHT_NOW_SPAN_SIZE = 60 * 1000;
	private static final long MINUTE_SPAN_SIZE = 60 * 60 * 1000;
	private static final long HOUR_SPAN_SIZE = 24 * 60 * 60 * 1000;
	private static final long DAY_SPAN_SIZE = 24 * 24 * 60 * 60 * 1000;
	
	
	public static String getMonthEngName(int month) {
		if (month < 0 || month > 11) {
			return null;
		}
		return monthName[month];
	}
	
	static public String getDateSimple(long datelong) {
		Date date = new Date(datelong);
		SimpleDateFormat df = SimpleDateFormatFactory.get("yyyy_MM_dd_HH_mm_ss");
		String st = df.format(date);
		return st;
	}
	
	static public String getDateSimple() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat df = SimpleDateFormatFactory.get("yyyy_MM_dd_HH_mm_ss");
		String st = df.format(date);
		return st;
	}
	
	/**
	 * Date 형식으로 들어온 일자를 yyyy.mm.dd로 리턴
	 * @param param
	 * @return
	 */
	static public String getDateSimple(Date param) {
		StringBuffer buff = new StringBuffer();
		Calendar cal = Calendar.getInstance();
	    cal.setTime(param);
	    
	    buff.append(cal.get(Calendar.YEAR)).append(".");
	    buff.append(cal.get(Calendar.MONTH) + 1).append(".");
	    buff.append(cal.get(Calendar.DAY_OF_MONTH));
	    logger.d("setPhotoInfo(), RegisteredAt(%s)", buff.toString());
	    
	    return buff.toString();
	}

	/**
	 * 현재 일자/시간을 문자열 형태로 리턴한다.
	 */
	static public String getCurrentDateTime() {
		return getDateTime(new Date());
	}
	
	static public String getCurrentDate() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat df = SimpleDateFormatFactory.get(TIME_FORMAT_4);
		String st = df.format(date);
		
		logger.d("getCurrentDate() = %s", st);
		return st;
	}
	
	static public String getDateTime(Date date) {
		SimpleDateFormat df = SimpleDateFormatFactory.get(TIME_FORMAT);
		String st = df.format(date);
		
		logger.d("getDateTime() = %s", st);
		return st;
	}
	
	/**
	 * 정해진 형식의 문자열을 Date객체로 바꿔준다.
	 */
	static public Date getDate(String pubDateText, String timeformat) {
		return getDate(pubDateText, timeformat, "GMT");
	}
	
	/**
	 * 정해진 형식의 문자열을 Date 객체로 바꿔준다.
	 */
	static public Date getDate(String pubDateText, String timeformat, String timeZone) {
		Date date = null;
		try {
			SimpleDateFormat dateFormater = null;
			if (pubDateText.endsWith("Z")) {
				dateFormater = SimpleDateFormatFactory.get(TIME_FORMAT_2, timeZone);
			} else {
				dateFormater = SimpleDateFormatFactory.get(timeformat, timeZone);
			}
			date = dateFormater.parse(pubDateText);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	static public String getAbsolutePubdateText(View view, String pubDateText, String timeformat, int afterhour) {
		if (pubDateText == null) {
			logger.d("getAbsolutePubdateText(), pubDateText is null");
			return null;
		}

		StringBuffer buffer = new StringBuffer();
		Date pubDate = getDate(pubDateText, timeformat);

		SimpleDateFormat formatter = SimpleDateFormatFactory.get(view.getResources().getString(afterhour));
		String tmp1 = formatter.format(pubDate);
		String dateString = null;

		if (M3Application.getCurrentApplication() != null) {
			String tmp2 = tmp1.replace("AM", M3Application.getCurrentApplication().getString(R.string.am));
			dateString = tmp2.replace("PM", M3Application.getCurrentApplication().getString(R.string.pm));
		} else {
			dateString = tmp1;
		}

		buffer.append(dateString.toLowerCase());
		String bufferText = buffer.toString();

		return bufferText;
	}
	
	/**
	 * 해당 일에 해당하는 영문 약자 요일 텍스트를 반환
	 * DAY_OF_WEEK리턴값이 일요일(1), 월요일(2), 화요일(3) ~~ 토요일(7)을 반환
	 * 예) SUN, MON, TUE, WED, THU, FRI, SAT
	 * @param date : 일시, 포맷) yyyy-MM-dd'T'HH:mm:ssZZZZ
	 * @return
	 */
	static public String getDayOfWeekEngText(String date) {
		String strDayOfWeek = "DAY";
		if (Utility.isNullOrEmpty(date)) {
			logger.d("getDayOfWeekEngText(), date is null");
			return strDayOfWeek;
		}
		
		Date dateStartAt = DateUtility.getDate(date, DateUtility.TIME_FORMAT);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateStartAt);
		
		int temp = cal.get(Calendar.DAY_OF_WEEK);
		if (temp < 1 || temp > 7) {
			logger.d("getDayOfWeekEngText(), date is invalid, temp(%s)", temp);
			return strDayOfWeek;
		}
		
		strDayOfWeek = DateUtility.dayOfWeek[cal.get(Calendar.DAY_OF_WEEK)];
		return strDayOfWeek;
	}
	
	/**
	 * 요놈은 Date 인자를 받음
	 * @param date
	 * @return
	 */
	static public String getDayOfWeekEngText(Date date) {
		String strDayOfWeek = "DAY";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int temp = cal.get(Calendar.DAY_OF_WEEK);
		logger.d("getDayOfWeekEngText(), temp(%s)", temp);
		if (temp < 1 || temp > 7) {
			logger.w("getDayOfWeekEngText(), date is invalid, temp(%s)", temp);
			return strDayOfWeek;
		}
		
		strDayOfWeek = DateUtility.dayOfWeek[cal.get(Calendar.DAY_OF_WEEK)];
		return strDayOfWeek;
	}
	
	/**
	 * 해당 일에 해당하는 day 값을 반환
	 * @param date : date : 일시, 포맷) yyyy-MM-dd'T'HH:mm:ssZZZZ
	 * @return
	 */
	static public int getDayOfMonth(String date) {
		int dayVal = 1;
		if (Utility.isNullOrEmpty(date)) {
			logger.d("getDayOfMonth(), date is null");
			return dayVal;
		}
		
		Date dateStartAt = DateUtility.getDate(date, DateUtility.TIME_FORMAT);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateStartAt);
		
		dayVal = cal.get(Calendar.DAY_OF_MONTH);
		return dayVal;
	}
	
	/**
	 * 날짜에 해당하는 YYYY.MM 문자열을 반환
	 * @return
	 */
	static public String getYearMonth(Date date) {
		StringBuffer bufYearMonth = new StringBuffer();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		bufYearMonth.append(cal.get(Calendar.YEAR)).append(".");
		bufYearMonth.append(String.format("%02d", cal.get(Calendar.MONTH) + 1));
		return bufYearMonth.toString();
	}
	public static String getNowDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		int mon = cal.get(Calendar.DAY_OF_MONTH);
		return Integer.toString(mon);
	}
	/**
	 * MMdd 형식의 날짜 문자열을 MM.dd 형식으로 변환
	 * @return
	 */
	public static String convertToM2BirthdayFormat(String paramDate) {
		logger.d("convertToM2BirthdayFormat(), paramDate(%s)", paramDate);
		String retStr = null;
		if (Utility.isNullOrEmpty(paramDate)) {
			logger.w("convertToM2BirthdayFormat(), there is invalid parameter");
			return retStr;
		}
	
		if (paramDate.length() != 4) {
			logger.w("convertToM2BirthdayFormat(), invalid date format(%s)", paramDate);
			return retStr;
		}
	
		try {
			DateFormat sdFormat = new SimpleDateFormat(BaseConstants.PATTERN_BIRTHDAY_ORG);
			Date birthDay = sdFormat.parse(paramDate);
			SimpleDateFormat formatter = new SimpleDateFormat(BaseConstants.PATTERN_BIRTHDAY_DIS);
			retStr = formatter.format(birthDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	
		logger.w("convertToM2BirthdayFormat(), paramDate(%s) retStr(%s)", paramDate, retStr);
		return retStr;
	}

	/**
	 * yyyyMMdd 형식의 날짜 문자열을 yyyy.MM.dd 형식으로 변환
	 * @return
	 */
	public static String convertToM2SinceFormat(String paramDate) {
		logger.d("convertToM2SinceFormat(), paramDate(%s)", paramDate);
		String retStr = null;
		if (Utility.isNullOrEmpty(paramDate)) {
			logger.w("convertToM2SinceFormat(), there is invalid parameter");
			return retStr;
		}
	
		if (paramDate.length() != 8) {
			logger.w("convertToM2SinceFormat(), invalid date format(%s)", paramDate);
			return retStr;
		}
	
		try {
			DateFormat sdFormat = new SimpleDateFormat(BaseConstants.PATTERN_SINCE_ORG);
			Date since = sdFormat.parse(paramDate);
			SimpleDateFormat formatter = new SimpleDateFormat(BaseConstants.PATTERN_SINCE_DIS);
			retStr = formatter.format(since);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	
		logger.w("convertToM2SinceFormat(), paramDate(%s) retStr(%s)", paramDate, retStr);
		return retStr;
	}

	public static String getPubdateText(Context context, String pubDate, String timeFormat) {
		SimpleDateFormat format = SimpleDateFormatFactory.get(timeFormat);
		try {
			Date date = format.parse(pubDate);
			return DateUtility.getPubdateText(context, date);
		} catch (ParseException e) {
			logger.e(e);
		}
		
		return pubDate;
	}

	public static String getPubdateText(Context context, Date pubDate) {
		return getPubdateText(context, pubDate, R.string.list_dateformat);
	}
	// yyyy년 mm월 dd일 오후 hh:mm 형식으로 변환
	public static String getPubdateAbsoluteText(Context context, Date pubDate) {
		String date = "";
		
		try {

			SimpleDateFormat formatter = SimpleDateFormatFactory.get(context.getResources().getString(R.string.list_full_dateformat));
			date = formatter.format(pubDate);
		} catch (Exception e) {
		}
		return date;
	}
	public static String getPubdateText(Context context, Date pubDate, int dateFormatResId) {
		StringBuffer buffer = new StringBuffer();
		
		try {
			if (pubDate == null) {
				return buffer.toString();
			}
			Calendar current = Calendar.getInstance();
	
			TimeSpan timeSpan = new TimeSpan();
			timeSpan.set(pubDate.getTime(), current.getTimeInMillis());
	
			long spanSize = timeSpan.size();
			if (spanSize <= RIGHT_NOW_SPAN_SIZE) {
				buffer.append(context.getString(R.string.rightnow));
			} else if (spanSize >= 0 && spanSize <= MINUTE_SPAN_SIZE) {
				buffer.append(spanSize / (60 * 1000));
				buffer.append(context.getString(R.string.beforeminute).replace("&nbsp;", " "));
			} else if (spanSize >= 0 && spanSize <= HOUR_SPAN_SIZE) {
				buffer.append(spanSize / (60 * 60 * 1000));
				buffer.append(context.getString(R.string.beforehour).replace("&nbsp;", " "));
			} else if (spanSize >= 0 && spanSize <= DAY_SPAN_SIZE) {
				buffer.append(spanSize / (24 * 60 * 60 * 1000));
				buffer.append(context.getString(R.string.beforeday).replace("&nbsp;", " "));
			} else {
				SimpleDateFormat formatter = SimpleDateFormatFactory.get(context.getResources().getString(dateFormatResId));
				String tmp1 = formatter.format(pubDate);
				String tmp2 = tmp1.replace("AM", context.getString(R.string.am));
				String dateString = tmp2.replace("PM", context.getString(R.string.pm));
				buffer.append(dateString.toLowerCase());
			}
		} catch (Exception e) {
		}
		
		String bufferText = buffer.toString();
	
		return bufferText;
	}
}
