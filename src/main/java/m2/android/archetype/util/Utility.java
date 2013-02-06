package m2.android.archetype.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Pattern;

import m2.android.archetype.base.BaseConstants;
import m2.android.archetype.example.R;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpMessage;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.nhn.android.archetype.base.object.ApiResponse;

public class Utility {

	private static Logger logger = Logger.getLogger(Utility.class);

	/**
	 * 무조건 한국인 경우, 010-XXXX-XXXX 형식으로 리턴한다.
	 * @param pContext
	 * @param isOrigin
	 * @return
	 */

	public static final String[] EXT_VIDEO_TYPES = {".avi", ".wmv", ".mpg", ".mpeg", ".mov", ".asf", ".mp4", ".skm", ".k3g", ".3gp"};
	public static final String[] EXT_IMAGE_TYPES = {".bmp", ".jpg", ".jpeg", ".png", ".tif", ".tiff", ".gif"};

	public static String getMyPhoneNumber(Context pContext, boolean isOrigin) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) pContext.getSystemService(Context.TELEPHONY_SERVICE);
		String origPhoneNumber = mTelephonyMgr.getLine1Number();

		logger.d("getMyPhoneNumber(), isOrigin (%s) origPhoneNumber (%s)", isOrigin, origPhoneNumber);
		//SIM Card에 번호가 저장되어 있지 않은 경우 또는 단순 Play 전용의 단말일 경우
		if (isNullOrEmpty(origPhoneNumber)) {
			return null;
		}

		// 폰에 저장된 번호 그대로를 리턴
		if (isOrigin) {
			return origPhoneNumber;
		}

		String onlyNum = getOnlyNumber(origPhoneNumber);
		String koreaNum = onlyNum;
		if (onlyNum.substring(0, 2).equals("82")) {
			koreaNum = onlyNum.substring(2);
		}
		
		String finalNum = "0" + deleteFirstZero(koreaNum);
		
		logger.d("getMyPhoneNumber(), finalNum(%s)", finalNum);
		return finalNum;
	}
	    
	/**
	 * +, - 같은 문자 제외후 숫자만 추출
	 * @param str
	 * @return
	 */
	public static String getOnlyNumber(String param) {
		if (param == null) {
			logger.w("getOnlyNumber(), param is null");
			return "";
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < param.length(); i++) {
			if (Character.isDigit(param.charAt(i))) {
				sb.append(param.charAt(i));
			}
		}
		
		return sb.toString();
	}

	/**
	 * 사용자가 입력한 전화번호에서 앞의 0으로 시작되는 모든 숫자 제거
	 * @param phoneNumber
	 * @return
	 */
	public static String deleteFirstZero(String phoneNumber) {
		if (isNullOrEmpty(phoneNumber)) {
			logger.w("phoneNumber is null");
			return null;
		}
		//logger.d("deleteFirstZero(), phoneNumber(%s)", phoneNumber);

		int newIndex = 0;
		for (int i = 0; i < phoneNumber.length(); i++) {
			char ch = phoneNumber.charAt(i);
			if (ch != '0') {
				newIndex = i;
				break;
			}
		}
		return phoneNumber.substring(newIndex);
	}

	/**
	 * locale이 한국일 경우, 국가번호 제거후 0 붙여서 리턴
	 * @param globalPhoneNumber
	 * @return
	 */
	public static String makeKoreaPhoneNumber(String globalPhoneNumber, Context context) {		
		return Utility.makeKoreaPhoneNumber(globalPhoneNumber);
	}
	
	/**
	 * locale이 한국일 경우, 국가번호 제거후 0 붙여서 리턴
	 * @param globalPhoneNumber
	 * @return
	 */
	public static String makeKoreaPhoneNumber(String globalPhoneNumber) {
		if (globalPhoneNumber == null || globalPhoneNumber.length() < 5) {
			logger.w("Invalid globalPhoneNumber (%s)", globalPhoneNumber);
			return null;
		}

		StringBuffer koreaNumber = new StringBuffer();
		koreaNumber.append("0");
		koreaNumber.append(globalPhoneNumber.substring(2));

		return koreaNumber.toString();
	}

	/**
	 * 국가번호가 포함된 글로벌 전화번호 생성후 리턴
	 * @param mCountryCode : '+'가 포함된 국가번호, 예)+82
	 * @param mPhoneNumber : 앞자리 '0'이 포함된 전화번호, 예) 01012345678
	 * @return
	 */
	public static String makeGlobalPhoneNumber(String mCountryCode, String mPhoneNumber) {
		if (Utility.isNullOrEmpty(mCountryCode) ) {
			logger.w("makeGlobalPhoneNumber(), mCountryCode is null");
			mCountryCode = "+82";
		}
		
		if (Utility.isNullOrEmpty(mPhoneNumber) ) {
			logger.w("makeGlobalPhoneNumber(), mPhoneNumber is null");
			return null;
		}
		
		logger.d("makeGlobalPhoneNumber(), mCountryCode(%s) mPhoneNumber(%s)", mCountryCode, mPhoneNumber);
		StringBuffer cellphone = new StringBuffer();
		cellphone.append(Utility.getOnlyNumber(mCountryCode));
		cellphone.append(Utility.deleteFirstZero(Utility.getOnlyNumber(mPhoneNumber)));
		
		logger.d("makeGlobalPhoneNumber(), CHECK POINT Return cellphone(%s)", cellphone.toString());
		return cellphone.toString();
	}

	/**
	 * Http Header를 살펴볼 때 사용합니다.
	 */
	public static void logHttpClient(HttpMessage httpMessage) {
		HeaderIterator iterator = httpMessage.headerIterator();
		while (iterator.hasNext()) {
			Header header = iterator.nextHeader();
			logger.d("logHttpClient(), header(%s, %s)", header.getName(), header.getValue());
		}
	}

	/**
	 * Stream을 문자열로 바꿉니다.
	 * 주로 디버그를 위해 사용합니다.
	 */
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//do something
		}
		return sb.toString();
	}

	public static boolean isNullOrEmpty(String str) {
		if (str == null || str.length() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isStringNullOrEmpty(String str) {
		return !isNotStringOrNullOrEmpty(str);
	}

	public static boolean isNotStringOrNullOrEmpty(String str) {
		if (str != null && str.length() > 0) {
			str = str.trim();
			if (!"null".equals(str)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isNotNullOrEmpty(String str) {
		return !isNullOrEmpty(str);
	}

	public static String getImageCacheName(String uri) {
		uri = uri.replaceAll("http://static[0-9].me2day.com", "@");
		uri = uri.replaceAll("http://static[0-9].me2day.net", "@");
		uri = uri.replace("http://", "@");
		uri = uri.replaceAll("[\\,/,:,*,?,\",<,>,|]", "-");
		uri += ".cache.png";

		return uri;
	}
	public static void appendSigUrl(StringBuilder url) {
		appendSigUrl(url, true);
	}
	public static void appendSigUrl(StringBuilder url, boolean appAmp) {
		if (appAmp == true) {
			url.append("&");
		}
		url.append("akey=");
		url.append(BaseConstants.APP_KEY);
		url.append("&asig=");
		url.append(getAppSig());
	}

	/**
	 * Query때마다 현재시간으로 보내주어야 서버에서 TimeoutException이 생기지 않는다.
	 */
	public static String getAppSig() {
		Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		String ts = String.format("%d", cal.getTimeInMillis());
		String sig = CryptoUtility.md5(ts + BaseConstants.NONCE + BaseConstants.APP_KEY + BaseConstants.APP_SEC);
		String aSig = CryptoUtility.base64(ts + "$$" + BaseConstants.NONCE + "$$" + sig);
		return aSig;
	}

	/**
	 * @author : Daniel Ji-Hoon Oh
	 * URL 상의 이미지를 로딩하여 Drawable 객체로 변환
	 * @param url : 로딩할 이미지
	 * @return : 변환된 drawable 객체
	 */
	public static Drawable loadImageThruUrl(String url) {
		InputStream is = null;
		try {
			is = (InputStream)new URL(url).getContent();
			Drawable drawable = Drawable.createFromStream(is, "src");
			is.close();
			return drawable;
		} catch (Exception e) {
			//예외 발생시에 열려있던 stream 닫아주자.
			if (is != null) {
				try {
					is.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
			return null;
		}
	}



	/*public static void showToastErrorMsg(String message, String description) {
		if (isNullOrEmpty(description)) {
			Toast.makeText(Me2dayApplication.getGlobalContext(), message, Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(Me2dayApplication.getGlobalContext(), description, Toast.LENGTH_SHORT).show();
		}
	}*/

	/**
	 * ACTION_SEND intent 전송시 특정 앱으로 바로 이동
	 * @param context
	 * @param pkgName
	 * @return
	 */
	public static Intent getTarketIntent(Context context, String pkgName) {
		logger.d("getTarketIntent(), pkgName(%s)", pkgName);

	    Intent targetIntent = new Intent(Intent.ACTION_MAIN, null);
	    targetIntent.addCategory(Intent.CATEGORY_LAUNCHER);

	    PackageManager packageManager = context.getPackageManager();
	    List<ResolveInfo> list = packageManager.queryIntentActivities(targetIntent, 0);
        for (ResolveInfo resolveInfo : list) {
            String currPkgName = resolveInfo.activityInfo.packageName;
            logger.d("getTarketIntent(), currPkgName(%s)", currPkgName);

            if (isNotNullOrEmpty(currPkgName) && currPkgName.startsWith(pkgName)) {
            	targetIntent.setPackage(currPkgName);
                return targetIntent;
            }
        }
	    return null;
	}

	/**
	 * Samsung GalaxyS		SHW-M110S
	 * Samsung GalaxyTab7 	SHW-M180S
	 * @param activity
	 * @return
	 */
	public static List<String> getSmsM2UrlsUsingGalaxyNormalUri(Activity activity) { 
		List<String> m2UrlList = new ArrayList<String>();
		
		if (activity == null) {
			logger.w("getSmsM2UrlsUsingGalaxyNormalUri(), activity is null");
			return m2UrlList;
		}
		
		long oneDaySeconds = 86400000;    //24*60*60*1000 하루치의 숫자를 빼준다
	    Uri uriSmsInbox = Uri.parse("content://com.sec.mms.provider/message"); 	//Galaxy Tab
	    
	    //SMS 받은날짜 최신순으로 정열
	    //Cursor cursor = managedQuery(uriSmsInbox, null, null, null, "RegTime DESC"); Both are same 
	    Cursor cursor = activity.getContentResolver().query(uriSmsInbox, null, null, null, "RegTime DESC"); 
	    if (cursor == null) {
	    	logger.w("getSmsM2UrlsUsingGalaxyNormalUri(), cursor is null");
			return m2UrlList;
	    }
	    
	    long validTime = System.currentTimeMillis() - oneDaySeconds;
	    //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//String strValidTime = dateFormat.format(new Date(validTime));
		
		try {
			activity.startManagingCursor(cursor);
		    while (cursor.moveToNext()) {
		    	/* 칼럼 출력용
		    	for (int i = 0; i < cursor.getColumnCount(); i++) { 
		    		//logger.d("getSmsM2UrlsUsingGalaxyNormalUri(), [INDEX:%s] cursor.getColumnName(%s) cursor.getString(%s)", i, cursor.getColumnName(i), cursor.getString(i)); 
		    		logger.d("getSmsM2UrlsUsingGalaxyNormalUri(), [INDEX:%s] cursor.getColumnName(%s)", i, cursor.getColumnName(i)); 
		        } 
		    	logger.d("getSmsM2UrlsUsingGalaxyNormalUri(), One row finished ***************************");
		    	*/
		    	
				int mainType = cursor.getInt(cursor.getColumnIndexOrThrow("MainType")); //0:받은문자, 1:보낸문자
				//logger.d("getSmsM2UrlsFromGalaxyNormal(), MainType(%s)", mainType);
				
				//받은 SMS가 아니면 continue
				if (mainType != 0) {
					continue;
				}
				
				long regTime = cursor.getLong(cursor.getColumnIndexOrThrow("RegTime"));
				//String strRegTime = dateFormat.format(new Date(regTime));
				//logger.d("getSmsM2UrlsFromGalaxyNormal(), strRegTime(%s) strValidTime(%s)", strRegTime, strValidTime);
				
				//받은지 24시간 지나지 않은 SMS일 경우
				if (validTime < regTime) {
					String title = cursor.getString(cursor.getColumnIndexOrThrow("Title"));
					//logger.d("getSmsM2UrlsFromGalaxyTab(), Title(%s)", title);
					//String cellphone = cursor.getString(cursor.getColumnIndex("MDN1st"));
					//logger.d("getSmsM2UrlsFromGalaxyTab(), cellphone(%s)", cellphone);
					
					//만약 SMS 본문에 M2 초대 URL이 포함되어 있을 경우, 목록에 저장 
					if (title.contains("http://me2.do/")) {
						String url = StringUtility.pickOutM2InvitationUrl(title);
						logger.d("getSmsM2UrlsUsingGalaxyNormalUri(), Invitation URL : FOUND, url(%s)", url);
						if (Utility.isNotNullOrEmpty(url)) {
							m2UrlList.add(url);
						}
					} 
					
				} else {
					//최신 문자순으로 정렬했기 때문에 하루이상 지났다면 그냥 중단 
					break;
				}
		    } 
		    
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if (cursor != null && cursor.isClosed() == false) {
				cursor.close();
			}
		}
		
		if (m2UrlList == null || m2UrlList.size() < 1) {
			logger.d("getSmsM2UrlsUsingGalaxyNormalUri(), Invitation URL : NOT FOUND");
		}
		return m2UrlList;
	}
	
	/**
	 * LGE Optimus Black	LG-KU5900
	 * LGE Optimus One		LG-KU3700
	 * LGE Optimus Q2		LG-LU6500
	 * Samsung GalaxyNexus	SHW-M420S
	 * @param activity
	 * @return
	 */
	public static List<String> getSmsM2UrlsUsingDefaultUri(Activity activity) { 
		List<String> m2UrlList = new ArrayList<String>();
		
		if (activity == null) {
			logger.w("getSmsM2UrlsUsingDefaultUri(), activity is null");
			return m2UrlList;
		}
		
		long oneDaySeconds = 86400000;    //24*60*60*1000 하루치의 숫자를 빼준다
	    Uri uriSmsInbox = Uri.parse("content://sms/inbox"); 
	    
	    //SMS 받은날짜 최신순으로 정열
	    Cursor cursor = activity.getContentResolver().query(uriSmsInbox, null, null, null, "date DESC"); 
	    if (cursor == null) {
	    	logger.w("getSmsM2UrlsUsingDefaultUri(), cursor is null");
			return m2UrlList;
	    }
	    
	    long validTime = System.currentTimeMillis() - oneDaySeconds;
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strValidTime = dateFormat.format(new Date(validTime));
		
		try {
			activity.startManagingCursor(cursor);
		    while (cursor.moveToNext()) {
		    	
		    	/* 칼럼 출력용
		    	for (int i = 0; i < cursor.getColumnCount(); i++) { 
		    		logger.d("getSmsM2UrlsUsingDefaultUri(), [INDEX:%s] cursor.getColumnName(%s) cursor.getString(%s)", i, cursor.getColumnName(i), cursor.getString(i)); 
		        } 
		    	logger.d("getSmsM2UrlsUsingDefaultUri(), One row finished ***************************");
		        */
		        
				long date = cursor.getLong(cursor.getColumnIndexOrThrow("date"));
				//String strRegTime = dateFormat.format(new Date(date));
				//logger.d("getSmsM2UrlsUsingDefaultUri(), strRegTime(%s) strValidTime(%s)", strRegTime, strValidTime);
				
				//받은지 24시간 지나지 않은 SMS일 경우
				if (validTime < date) {
					String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
					//logger.d("getSmsM2UrlsUsingDefaultUri(), body(%s)", body);
					//String address = cursor.getString(cursor.getColumnIndex("address"));
					//logger.d("getSmsM2UrlsUsingDefaultUri(), address(%s)", address);
				
					//만약 SMS 본문에 M2 초대 URL이 포함되어 있을 경우, 목록에 저장 
					if (body.contains("http://me2.do/")) {
						String url = StringUtility.pickOutM2InvitationUrl(body);
						logger.d("getSmsM2UrlsUsingDefaultUri(), Invitation URL : FOUND, url(%s)", url);
						if (Utility.isNotNullOrEmpty(url)) {
							m2UrlList.add(url);
						}
					} 
					
				} else {
					//최신 문자순으로 정렬했기 때문에 하루이상 지났다면 그냥 중단 
					break;
				}
		    } 
		    
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if (cursor != null && cursor.isClosed() == false) {
				cursor.close();
			}
		}
		
		if (m2UrlList == null || m2UrlList.size() < 1) {
			logger.d("getSmsM2UrlsUsingDefaultUri(), Invitation URL : NOT FOUND");
		}
		return m2UrlList;
	}
	
	/**
	 * Samsung GalaxyA		SHW-M100S
	 * Samsung GalaxyS		SHW-M110S
	 * Samsung GalaxyK		SHW-M130K
	 * Samsung GalaxyU		SHW-M130L
	 * Samsung GalaxyS2		SHW-M250S/K/L
	 * Samsung GalaxyNote	SHV-E160S/K/L
	 * Samsung GalaxyTab7 	SHW-M180S/K/L
	 * Samsung GalaxyTab10	SHW-M380S/K/L
	 * @return
	 */
	public static boolean isGalaxySmsBox() {
		//삼성에서 customizing한 SMS CP URI를 사용하는 단말 목록 정의
		final String[] enableDevices = new String[]{
			"SHW-M110", 	//Samsung GalaxyS
			"SHW-M250", 	//Samsung Galaxy2(S/K/L)
			"SHV-E160", 	//Samsung GalaxyNote(S/K/L)
			"SHW-M180"		//Samsung GalaxyTab7(S/K/L)
		};
		
		String deviceName = Build.MANUFACTURER + " " + Build.MODEL;
		
		logger.d("isGalaxySmsBox(), deviceName(%s)", deviceName);
		for (String device : enableDevices) {
			if (deviceName.contains(device)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 현재 보고 있는 화면일 경우, 
	 * @param context
	 * @return
	 */
	public static boolean isMe2ActivityRunning(Context context) {
		boolean isMe2AppActive = false;

		ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasks = activityManager.getRunningTasks(1);
		if (tasks != null && tasks.size() == 1) {
			RunningTaskInfo taskInfo = tasks.get(0);
			ComponentName topActivity = taskInfo.topActivity;
			String className = topActivity.getClassName();

			if (Utility.isNotNullOrEmpty(className) && className.startsWith(context.getPackageName())) {
				isMe2AppActive = true;
			}
			logger.d("isMe2ActivityRunning(%s, %s)", className, isMe2AppActive);
		}
		
		return isMe2AppActive;
	}
	
//    public static void finishBackActivity(String currentActivityName) {
//		Activity activity = null;
//
//		try {
//			if (BaseActivity.getActivities().size() > 0) {
//				for (int i = BaseActivity.getActivities().size() - 1; i == 0; i--) {
//					try {
//						activity = BaseActivity.getActivities().get(i);
//						if (!activity.getLocalClassName().toString().equals(currentActivityName)) {
//							BaseActivity.getActivities().remove(activity);
//							activity.finish();
//						}
//					} catch (Exception e) {
//						logger.e(e);
//					}
//				}
//			}
//		} catch (Exception e) {
//			logger.e(e);
//		}
//	}
	
	public static interface GetAddressListener {
		public void onReceive(String address);
	}
	public static void getAddress(final Context context, final double lat, final double lng, final GetAddressListener listener) {
		
		new Handler().post(new Runnable() {
			
			@Override
			public void run() {
				String address = getAddress(context, lat, lng, false);
				listener.onReceive(address);
			}
		});
	}
	public static String getAddress(Context context, double lat, double lng, boolean skipLocality) {
		logger.d("Called getAddress()");
		StringBuilder geoString = new StringBuilder();
		try {
			Geocoder goecoder = new Geocoder(context, Locale.getDefault());

			Address adr = goecoder.getFromLocation(lat, lng, 1).get(0);
			if (!skipLocality) {
				if (adr.getAdminArea() != null) {
					geoString.append(adr.getAdminArea()).append(" ");
					logger.d("AdminArea: %s", adr.getAdminArea());
				}
				if (adr.getLocality() != null) {
					geoString.append(adr.getLocality()).append(" ");
					logger.d("Locality: %s", adr.getLocality());
				}
			}

			if (adr.getThoroughfare() != null) {
				geoString.append(adr.getThoroughfare()).append(" ");
				logger.d("Thoroughfare: %s", adr.getThoroughfare());
			}
			if (adr.getFeatureName() != null) {
				geoString.append(adr.getFeatureName());
				logger.d("FeatureName: %s", adr.getFeatureName());
			}
			
			if (!"".equals(geoString.toString())) {
				geoString.append("\n\n");
			}
		} catch (Exception e) {
			//do something
		}
		return geoString.toString();
	}


	
	public static void showResponseErrorToast(final Activity activity, final ApiResponse result) {
		
		logger.d(">>> Called showToastErrorMessage()");
		
		if (activity!= null && !activity.isFinishing()) {
			
			if (result == null) {
				return;
			}
			
			new Handler().post(new Runnable() {				
				@Override
				public void run() {
					String description = result.getDescription();
					String message = result.getMessage();
					
					try {
						if (Utility.isNullOrEmpty(description)) {
							Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
						}
					} catch (Exception e) {
						
					}
				}
			});
		}	
	}
	public static void showToast(final Activity activity, final String message) {
		if (activity!= null && !activity.isFinishing()) {
		
			new Handler().post(new Runnable() {				
				@Override
				public void run() {
				
					try {						
						Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
					} catch (Exception e) {
						
					}
				}
			});
		}	
	}
	
	public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
	
	/** 숫자인지 확인 하는 함수 */
	static public boolean isNumeric(String s) { 
	    java.util.regex.Pattern pattern = Pattern.compile("^\\d+$"); 
	    return pattern.matcher(s).matches(); 
	} 
	
	public static void printLog(Intent intent) {

		if (logger.isDebugEnabled()) {

			Bundle bundle = intent.getExtras();

			try {
				if (bundle != null) {
					Set<String> key = bundle.keySet();

					Iterator<String> iter = key.iterator();
					while (iter.hasNext()) {
						String keyStr = iter.next();
						if (keyStr != null) {
							Object value = bundle.get(keyStr);
							logger.d("Intent key(%s), value(%s)", keyStr, value);
						}
					}
				}
			} catch (Exception e) {
				logger.e(e);
			}
		}
	}
	
	public static boolean isVideoType(String fileName) {
		if (Utility.isNotStringOrNullOrEmpty(fileName)) {
			for (String ext : EXT_VIDEO_TYPES) {
				if (fileName.endsWith(ext)) {
					return true;
				}
			}
		}

		return false;
	}
	
	public static boolean isImageType(String fileName) {
		if (Utility.isNotStringOrNullOrEmpty(fileName)) {
			for (String ext : EXT_IMAGE_TYPES) {
				if (fileName.endsWith(ext)) {
					return true;
				}
			}
		}

		return false;
	}

}
