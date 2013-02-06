package m2.android.archetype.template;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import m2.android.archetype.example.R;
import m2.android.archetype.util.CellphoneNumberUtility;
import m2.android.archetype.util.FastHtml;
import m2.android.archetype.util.Logger;
import m2.android.archetype.util.Utility;
import m2.android.archetype.util.date.DateUtility;
import m2.android.archetype.util.date.SimpleDateFormatFactory;
import android.content.Context;
import android.text.Html;

public class TemplateDataParser {
	private static Logger logger = Logger.getLogger(TemplateDataParser.class);
	
	public Object parse(Context context, String type, Object data) {
		if (type.equals("pubdate")) {
			if (data != null) {
				SimpleDateFormat format = SimpleDateFormatFactory.get(SimpleDateFormatFactory.TIME_FORMAT);
				try {
					Date date = format.parse(data.toString());
					return DateUtility.getPubdateText(context, date, R.string.list_dateformat_date2);
				} catch (ParseException e) {
					logger.e(e);
				}
			}
		} else if (type.equals("pubdateabsolute")) {
			if (data != null) {
				SimpleDateFormat format = SimpleDateFormatFactory.get(SimpleDateFormatFactory.TIME_FORMAT);
				try {
					Date date = format.parse(data.toString());
					return DateUtility.getPubdateAbsoluteText(context, date);
				} catch (ParseException e) {
					logger.e(e);
				}
			}
		} else if (type.equals("updatedpubdate")) {
			if (data != null) {
				SimpleDateFormat format = SimpleDateFormatFactory.get(SimpleDateFormatFactory.TIME_FORMAT_2);
				try {
					Date date = format.parse(data.toString());
					return DateUtility.getPubdateText(context, date);
				} catch (ParseException e) { 
					logger.e(e);
				}
			}
		} else if (type.equals("pubdatetime")) {
			if (data != null) {
				SimpleDateFormat format = SimpleDateFormatFactory.get(SimpleDateFormatFactory.TIME_FORMAT);
				try {
					Date date = format.parse(data.toString());
					return DateUtility.getPubdateText(context, date);
				} catch (ParseException e) { 
					logger.e(e);
				}
			}
		} else if (type.equals("released")) {
			if (data != null) {
				Date date = DateUtility.getDate(data.toString(), DateUtility.TIME_FORMAT_4);
				if (date != null) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					return String.format("%tY. %tm. %td", cal, cal, cal);
				}
			}
		} else if (type.equals("cellphone")) {
			if (data != null) {
				return CellphoneNumberUtility.nationalFormattedNumber(data.toString());
			} else {
				return data;
			}
		} else if (type.equals("schedule")) {
			//YYYY.MM 포맷으로 그룹 인덱싱
			SimpleDateFormat format = SimpleDateFormatFactory.get(SimpleDateFormatFactory.TIME_FORMAT);
			try {
				Date date = format.parse(data.toString());
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				
				String yearMonth = String.format(context.getString(R.string.dateformat_year_month), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
				return yearMonth;
			} catch (ParseException e) {
				logger.e(e);
			}
		} else if (type.equals("html")) {
			if (data != null) {
				return Html.fromHtml(data.toString());
			}
		}  else if (type.equals("fasthtml")) {
			if (data != null) {
				return FastHtml.fromHtml(data.toString());
			}
		}  else if (type.equals("post")) {
			if (data != null) {
				String text = data.toString();
				CharSequence ret = null;
				
				if (text.indexOf('<') >= 0) {
					text = text.replace("<a href='", "<b><font color='#e65802' href='");
					text = text.replace("</a>", "</font></b>");
					text = text.replace("\n", " <br>");
					ret = Html.fromHtml(text);
				} else {
					ret = text.replace("&lt;", "<").replace("&gt;", ">").replace("&amp;", "&").replace("\n", " \n");
				}
				
				return WebLinkTextParser.parse(ret);
			}
		} else if (type.equals("cover")) {
			if (data != null) {
				String thumbnail = data.toString();
				if (Utility.isNotNullOrEmpty(thumbnail) && thumbnail.startsWith("COVER_")) {
					int selectedBandCover = 1; 
					if (Utility.isNotNullOrEmpty(Utility.getOnlyNumber(thumbnail))) {
						selectedBandCover = Integer.parseInt(Utility.getOnlyNumber(thumbnail));
					}
					int coverResId = 0;
					
					
					return coverResId;
				}
			}
		} else if (type.equals("band")) {
			if (data != null) {
				String color = data.toString();
				if (Utility.isNotNullOrEmpty(color) && color.startsWith("BAND_")) {
					int selectedBandColor = 1; 
					if (Utility.isNotNullOrEmpty(Utility.getOnlyNumber(color))) {
						selectedBandColor = Integer.parseInt(Utility.getOnlyNumber(color));
					}
					
					int colorResId =0;
					
					return colorResId;
				} 
			}
		} else if (type.equals("distance")) {
			if (data != null) {
				String relLocation = data.toString();
				String relWord = "m";
				
				double dRelLocation = Double.valueOf(relLocation);
				if (dRelLocation > 1000) {
					relLocation = String.format("%,.2f", dRelLocation / 1000);
					relWord = "km";
				}
				
				return relLocation + relWord;
			}
		} else if (type.equals("count")) {
			if (data != null) {
				String postCount = data.toString();
				int count = Integer.parseInt(postCount);
				if (count > 9999) {
					return "9999+";
				} else {
					return postCount;
				}
			}
		} else if (type.equals("postscount")) {
			if (data != null) {
				String postCount = data.toString();
				int count = Integer.parseInt(postCount);
				if (count > 9999) {
					return "9,999+";
				} else if (count == 0){
					return null;
				} else {
					NumberFormat numberFormat = new DecimalFormat("#,###");
					return numberFormat.format(count);
				}
			}
		} else if (type.equals("friendscount")) {
			if (data != null) {
				String postCount = data.toString();
				int count = Integer.parseInt(postCount);
				if (count == 0){
					return null;
				} else {
					NumberFormat numberFormat = new DecimalFormat("#,###");
					return String.format(context.getResources().getString(R.string.friend_count), numberFormat.format(count));
				}
			}
		}  else if (type.equals("tag")) {
			if (data != null) {
				String tag = data.toString(); 
				if (Utility.isNullOrEmpty(tag)) {
					return " ";
				} else {
					return tag;
				}
			}
		} 
		
		return data;
	}
}


