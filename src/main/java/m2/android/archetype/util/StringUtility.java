package m2.android.archetype.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import m2.android.archetype.base.BaseConstants;
import m2.android.archetype.config.AppBuildCheckFlag;
import m2.android.archetype.example.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

public class StringUtility {

	private static Logger logger = Logger.getLogger(StringUtility.class);
	
	/**
	 * 해당 메시지에서 M2 초대 URL만 추출해서 리턴
	 * @param fullSmsMsg
	 * @return
	 */
	public static String pickOutM2InvitationUrl(String fullSmsMsg) {
		logger.d("pickOutM2InvitationUrl(), fullSmsMsg(%s)", fullSmsMsg);
		String url = "";
		if (Utility.isNullOrEmpty(fullSmsMsg)) {
			logger.w("pickOutM2InvitationUrl(), fullSmsMsg is null");
			return url;
		}
		
		
		int startIndex = fullSmsMsg.indexOf("http://me2.do/");
		String tempStr = fullSmsMsg.substring(startIndex);
		int endIndex = tempStr.indexOf(" ");
		if (endIndex != -1) {
			url  = tempStr.substring(0, endIndex);
		} else {
			url  = tempStr.substring(0, tempStr.length());
		}
		
		logger.d("pickOutM2InvitationUrl(), picked out M2 url(%s)", url);
		return url;
	}
	
	public static String generateM2InvitationUrlParam(List<String> param) {
		if (param == null) {
			logger.w("generateM2InvitationUrlParam(), param is null");
			return null;
		}
		
		logger.d("generateM2InvitationUrlParam(), param.size(%s)", param.size());
		
		StringBuffer urls = new StringBuffer();
		int count = 0;
		
		for (int i = 0; i < param.size(); i++) {
			if (count > 20) {
				break;
			}
			urls.append(param.get(i)).append(",");
			count++;
		}
		
		logger.d("generateM2InvitationUrlParam(), urls(%s)", urls.toString());
		return urls.toString();
	}
	
	public static String pickoutSmsAuthNumber(String param) {
		String authNumber = "";
		
		int startIndex = param.indexOf('[');
		int endIndex = param.indexOf(']');
		if (startIndex != -1 && endIndex != -1) {
			if (startIndex < endIndex) {
				authNumber = param.substring(startIndex + 1, endIndex);
			}
		}
		
		return authNumber;
	}
	
	/** * 문자열를 nLength로 지정한 길이만큼 자른 후 그 결과를 리턴 
	* @param strValue 입력 문자열, 예) "미투도우미, 봉도사화이팅요, daniel798, 야웅토르, 키엘"
	* @param nLength 자른 후의 문자열 길이, 예) 4 
	* @param strTail 말줄임 표식. ex) "..." * 
	* @return String 자른 문자열에 말줄임 표식을 붙인 결과
	*  수행결과는 아래와 같다. 
		[0] LEN (7) ORG (미투도우...)
		[1] LEN (7) ORG (봉도사화...)
		[2] LEN (7) ORG (dani...)
		[3] LEN (4) ORG (키엘)
	* */
	public static String convertElipsedString(String strValue, int nLength) {
		String strTail = "..."; 
		if (TextUtils.isEmpty(strValue)) {
			return null;    
		}
		
		String strReturn = null;    
		// 문자열이 지정한 길이보다 길면 자르고, 아니면 그냥 반환한다.    
		if (strValue.length() > nLength) { 
			strReturn = strValue.substring(0, nLength) + strTail;
		} else {
			strReturn = strValue;    
		}
		return strReturn;
	}
	/**
	 * local 이 한국인지 체크해서 한국일경우만 하이픈 번호 반환
	 * @param param 국제전화 포맷의 휴대폰 번호, 예) 821036267777
	 * @param context
	 * @return
	 */	
	public static String hyphenFormattingNumber(String param, Context context) {
		String hyphenedPhoneNum = "";
		String countryCode = StringUtility.pickOutCuntryCode(param);
		String phoneNum = StringUtility.pickOutPhoneNumber(param);
		
		if (Utility.isNotNullOrEmpty(countryCode)) {
			if (BaseConstants.COUNTRY_CODE_KOREA.equalsIgnoreCase(countryCode)) {	
				hyphenedPhoneNum = StringUtility.hyphenFormattingKoreaCallNumber("0" + phoneNum);			
			}  else {
				hyphenedPhoneNum = phoneNum;
			}
		} else {
			hyphenedPhoneNum = param;
		}

		return hyphenedPhoneNum;
	}
	/**
	 * 한국일 경우, 전화번호 hyphen 처리
	 * @param param
	 * @return
	 */
    public static String hyphenFormattingKoreaCallNumber(String param) {
    	StringBuffer buffer = new StringBuffer();
    	if (Utility.isNullOrEmpty(param)) {
    		logger.w("hyphenFormattingKoreaCallNumber(), param is null");
    		return buffer.toString();
    	}
    	
    	int len = param.length();
    	//logger.d("hyphenFormattingKoreaCallNumber, param(%s) len(%s)", param, len);
    	
    	if (len < 9) {
    		return param;
    	}
    	
    	//뒷자리부터 4, 4, n자리만큼 잘라낸다.
    	//0070012345678, 00700-1234-5678, 	n-4-4, over 11
    	//01012345678, 	 010-1234-5678, 	n-4-4, 11
    	//07044446666, 	 070-4444-6666, 	n-4-4, 11
    	//0115557777,  	 011-555-7777,  	n-3-4, 10
    	//0618889999,  	 061-888-9999,  	n-3-4, 10	 
    	//0233334444,  	 02-3333-4444,  	n-4-4, 10
    	//024567890,   	 02-467-7890,   	n-3-4, 9
    	//114,         	 114,           	n, under 8
    	
    	if (len >= 11) {
    		//n-4-4
    		buffer.append(param.substring(0, len-8)).append('-');
        	buffer.append(param.substring(len-8, len-4)).append('-');
        	buffer.append(param.substring(len-4, len));
    	} else if (len == 10) {
    		if (param.substring(0, 2).equals("02")) {
    			//n-4-4
    			buffer.append(param.substring(0, len-8)).append('-');
            	buffer.append(param.substring(len-8, len-4)).append('-');
            	buffer.append(param.substring(len-4, len));
    		} else {
    			//n-3-4
    			buffer.append(param.substring(0, len-7)).append('-');
            	buffer.append(param.substring(len-7, len-4)).append('-');
            	buffer.append(param.substring(len-4, len));
    		}
    	} else if (len == 9) {
    		//n-3-4
    		buffer.append(param.substring(0, len-7)).append('-');
        	buffer.append(param.substring(len-7, len-4)).append('-');
        	buffer.append(param.substring(len-4, len));
    	} else {
    		//n
    		return param;
    	}
    	return buffer.toString();
    }
    
    /**
     * 휴대폰 번호에서 국가코드 추출해서 리턴
     * @param cellphone
     * @return
     */
    public static String pickOutCuntryCode(String cellphone) {
        String foundCode = null;
        
		if (Utility.isNullOrEmpty(cellphone)) {
        	logger.d("pickOutCuntryCode(), cellphone is null.");
        	return foundCode;
        }
		
		if (cellphone.length() < 9) {
			logger.d("pickOutCuntryCode(), cellphone is invalid.");
        	return foundCode;
		}
		
        //logger.d("pickOutCuntryCode(%s)", cellphone);
        
        for (int i = 0; i < BaseConstants.COUNTRY_CODES.length; i++) {
        	String code = Utility.getOnlyNumber(BaseConstants.COUNTRY_CODES[i]);
        	String temp = cellphone.substring(0, code.length());
        	//logger.d("pickOutCuntryCode(%s, %s)", code, temp);
        	
        	if (code.equals(temp)) {
        		//logger.d("pickOutCuntryCode(), FOUND code(%s)", code);
        		foundCode = "+" + code;
        		break;
        	}
        	
        }
        return foundCode;
	}
    
    /**
     * 휴대폰 번호에서 국가코드를 제외하고 리턴
     * @param cellphone 821036267777
     * @return 1036267777
     */
    public static String pickOutPhoneNumber(String cellphone) {
    	String onlyPhoneNum = null;
        
		if (Utility.isNullOrEmpty(cellphone)) {
        	logger.d("pickOutPhoneNumber(), cellphone is null.");
        	return onlyPhoneNum;
        }
		
		if (cellphone.length() < 9) {
			logger.d("pickOutPhoneNumber(), cellphone is invalid.");
        	return onlyPhoneNum;
		}
		
        //logger.d("pickOutPhoneNumber(%s)", cellphone);
        
        for (int i = 0; i < BaseConstants.COUNTRY_CODES.length; i++) {
        	String code = Utility.getOnlyNumber(BaseConstants.COUNTRY_CODES[i]);
        	String temp = cellphone.substring(0, code.length());
        	//logger.d("pickOutPhoneNumber(%s, %s)", code, temp);
        	
        	if (code.equals(temp)) {
        		//logger.d("pickOutPhoneNumber(), FOUND code(%s)", code);
        		
        		onlyPhoneNum = Utility.deleteFirstZero(cellphone.substring(code.length()));
        		break;
        	}
        }
     
        return onlyPhoneNum;
	}
    
	/**
	 *
	 * @param str
	 * @return
	 */
	static public boolean isNullOrEmpty(String str) {
		if (str == null || str.length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 *
	 * @param str
	 * @return
	 */
	static public boolean isStringNullOrEmpty(String str) {
		return !isNotStringOrNullOrEmpty(str);
	}

	/**
	 *
	 * @param str
	 * @return
	 */
	static public boolean isNotStringOrNullOrEmpty(String str) {
		if (str != null && str.length() > 0) {
			str = str.trim();
			if (!"null".equals(str)) {
				return true;
			}
		}
		return false;
	}

	/**
	 *
	 * @param str
	 * @return
	 */
	static public boolean isNotNullOrEmpty(String str) {
		return !isNullOrEmpty(str);
	}
	
	/**
	 *
	 * @param srcText
	 * @return
	 */
	static public String getPlainText(String srcText) {
		String linkText = srcText.toString();
		Pattern pattern1 = Pattern.compile("\"([^\"]*)\":(http|https)://([\\S]*)");
		Pattern pattern2 = Pattern.compile("\"([^\"]*)\":(http|https)://([\\S]*)[\\s]");

		Matcher matcher = pattern1.matcher(linkText);
		//boolean bMatch = false;
		while (matcher.find() && matcher.groupCount() > 1) {
			Matcher m2 = pattern2.matcher(linkText);
			if (m2.find() && m2.groupCount() > 1) {
				linkText = m2.replaceFirst(m2.group(1));
			} else {
				linkText = matcher.replaceFirst(matcher.group(1));
			}
			matcher = pattern1.matcher(linkText);
			//bMatch = true;
		}

		/*if (bMatch) {
			Utility.d("Me2dayUIUtility", String.format("getPlainText [%s]", linkText));
		}*/

		return linkText;
	}
	
	public static String[] checkUrlChatting(String postText) {
		String returnArr[] = new String[]{"",""};
		Pattern pattern = Pattern.compile("(?<!href=')(http|https|ftp)://[^\\s^\\.]+(\\.[^\\s^\\.]+)*");
	    Matcher matcher = pattern.matcher(postText);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
        	if (!matcher.group().toString().contains("'>") && !matcher.group().toString().contains("</a>")) {
        		matcher.appendReplacement(sb, "<a href='" + matcher.group() + "'>" + matcher.group() + "</a>");
        		returnArr[1] = matcher.group();
        	}            
        }
        matcher.appendTail(sb);
        returnArr[0] = sb.toString();
        
        return returnArr;
	}
	
	public final static String escapeHtml(String param) {
		if (param != null) {
			param = param.replace("&lt;", "<").replace("&gt;", ">").replace("&amp;", "&");
		}
		
		return param;
	}
	
	public final static String format(String formatString, Object...args) {
		// 마켓버전일때만 죽지 않도록 처리
		// 개발버전의 경우엔 앱이 죽는게 오히려 빨리 원인 파악이 가능
		if (AppBuildCheckFlag.VERSION_CHECK_MARKET_RELEASE_MODE) {
			try {
				return String.format(formatString, args);
			} catch (Exception e) {
				logger.e(e);
			}
			
			return formatString;
		}
		
		return String.format(formatString, args);
	}
	
	public final static String safeFormat(String formatString, String defaultValue, Object...args) {
		try {
			return String.format(formatString, args);
		} catch (Exception e) {
			logger.e(e);
		}
		
		return defaultValue;
	}
	
	static final public boolean equals(String str1, String str2) {
		if (str1 == str2) {
			return true;
		}
		
		if (str1 == null) {
			return false;
		}
		
		return str1.equals(str2);
	}
}
