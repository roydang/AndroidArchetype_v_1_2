package m2.android.archetype.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import m2.android.archetype.sharedpref.UserSharedPrefModel;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

public class AppInfoUtility {
	private static Logger logger = Logger.getLogger(AppInfoUtility.class);
	private static String versionName = null;
	
	public static final String APP_KEY = "8270995ff1c87825f70efbbef1b874d0"; //Me2day
	public static final String APP_SEC = "ZWM2NThkNDZlMjg3ODY2Y2YzOTQyZDAxM2YyMDY2OTQ="; // 미투데이

	public static final String RSA_EXPONENT = "010001";
	public static final String RSA_MODULUS = "F1F07304C7C9CFB7C64FBE75C2C0274081C4B1D04BAE0F905CF251426100B86E802633E9AD849AD1598B855C26E336B2975E0B84D673F521562F5E6E6FCDF89BBF08A6C3D99A2C3E86488CFD00397B562D06158BC4E04BBBA9B66786A454B83A142FC1BA3F7FD8C862E58CF171F60CF3B0F258C0F39915200910B29841D3D1F1";

	public static final String NONCE = "ffffffff";
	public static final String USER_AGENT_PREFIX = "me2day/";
	
	private static String userId;
	private static String fullAuthToken;
	private static String userAgent;
	
	public String getAppKey() {
		return APP_KEY;
	}
	/**
	 * Query때마다 현재시간으로 보내주어야 서버에서 TimeoutException이 생기지 않는다.
	 */
	public static String getAppSig() {
		Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		String ts = String.format("%d", cal.getTimeInMillis());
		String sig = CryptoUtility.md5(ts + NONCE + APP_KEY + APP_SEC);
		String aSig = CryptoUtility.base64(ts + "$$" + NONCE + "$$" + sig);
		return aSig;
	}
	public static String getUserId() {
		if (userId == null) {
			userId = UserSharedPrefModel.get().getUserId();;
		} 
		return userId;
	}
	public static String getFullAuthToken() {
		if (fullAuthToken == null) {
			fullAuthToken = UserSharedPrefModel.get().getFullAuthToken();
		}
		return fullAuthToken;
	}
	
	/**
	 * USER AGENT 구성
	 * @param context
	 * @return
	 */
	public static String getUserAgentStr(Context context) {
		
		
		if (userAgent == null) {
			String appVersion = null;
			String strDeviceName = "devicename";
	
			try {
				appVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
				strDeviceName = Build.MANUFACTURER + " " + Build.MODEL;
			} catch (PackageManager.NameNotFoundException ex) {
				appVersion = "x.x";
			}
	
			strDeviceName = Build.MANUFACTURER + " " + Build.MODEL;
			userAgent = (USER_AGENT_PREFIX + appVersion + " (Android OS " + Build.VERSION.RELEASE + ";" + strDeviceName + ")");
		}
		return userAgent;
	}
	
	public final static int getVersionCode(Context context) {
		int versionCode = 0;

		try {
			PackageInfo i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			versionCode = i.versionCode;
		} catch (Exception e) {
			// do something
		}

		return versionCode;
	}

	public final static String getVersionName(Context context) {
		if (versionName == null) {
			try {
				PackageInfo i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
				versionName = i.versionName;
			} catch (Exception e) {
				// do something
			}
		}

		return versionName;
	}


	/**
	 * 단말 버전과 서버에서 온 버전을 비교해서 서버 버전이 더 높으면 true 반환
	 * 
	 * @param deviceVersion
	 * @param serverVersion
	 * @return
	 */
	public final static boolean isExistUpdateVersion(String deviceVersion, String serverVersion) {
		try {
			logger.d("deviceVersion=" + deviceVersion);
			logger.d("serverVersion=" + serverVersion);

			if (deviceVersion == null || serverVersion == null || deviceVersion.length() == 0 || serverVersion.length() == 0) {
				return false;
			}

			int lv, mv, hv;
			int orgLv, orgMv, orgHv;

			int idx1, idx2;

			// Major version check
			idx1 = serverVersion.indexOf('.');
			idx2 = deviceVersion.indexOf('.');

			if (idx1 == -1 || idx2 == -1) { // 버전에 . 이없으면 false
				return false;
			}

			hv = Integer.parseInt(serverVersion.substring(0, idx1));
			orgHv = Integer.parseInt(deviceVersion.substring(0, idx2));

			logger.d("hv: %s orgHv: %s", hv, orgHv);

			if (hv > orgHv) {
				return true;
			} else if (hv < orgHv) {
				return false;
			}

			// Minor version check
			mv = Integer.parseInt(serverVersion.substring(idx1 + 1, serverVersion.indexOf('.', idx1 + 1)));
			orgMv = Integer.parseInt(deviceVersion.substring(idx2 + 1, deviceVersion.indexOf('.', idx2 + 1)));
			if (mv > orgMv) {
				return true;
			} else if (mv < orgMv) {
				return false;
			}

			// Hotfix version check
			idx1 = serverVersion.indexOf('.', idx1 + 1);
			idx2 = deviceVersion.indexOf('.', idx2 + 1);

			int lastIdx1, lastIdx2 = 0;
			lastIdx1 = serverVersion.indexOf('.', idx1 + 1);
			lastIdx2 = deviceVersion.indexOf('.', idx2 + 1);
			if (lastIdx1 <= 0) {
				lastIdx1 = serverVersion.length();
			}
			if (lastIdx2 <= 0) {
				lastIdx2 = deviceVersion.length();
			}
			lv = Integer.parseInt(serverVersion.substring(idx1 + 1, lastIdx1));
			orgLv = Integer.parseInt(deviceVersion.substring(idx2 + 1, lastIdx2));
			if (lv > orgLv) {
				return true;
			} else if (lv < orgLv) {
				return false;
			}

			return false;
		} catch (Exception e) {
			logger.e(e);
		}

		return false;
	}
	
	public final static boolean isJellyBean41Compatibility() {
		return Build.VERSION.SDK_INT >= 16;
	}
	
	public final static boolean isJellyBean42Compatibility() {
		return Build.VERSION.SDK_INT >= 17;
	}

	public final static boolean isICSCompatibility() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}
	
	public final static boolean isGingerBreadCompatibility() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
	}
	
	public final static boolean isGingerBreadMR1Compatibility() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1;
	}	
	
	public final static boolean isUnderFroyo() {
		return Build.VERSION.SDK_INT <= Build.VERSION_CODES.FROYO;
	}
	
	public static void playVideo(String url, Activity activity) {
		if (url != null && url.length() > 0) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.parse(url), "video/*");
			activity.startActivity(intent);
		}
	}

	public void playAudio(String url, Activity activity) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse(url), "audio/*");
		activity.startActivity(intent);
	}
}
