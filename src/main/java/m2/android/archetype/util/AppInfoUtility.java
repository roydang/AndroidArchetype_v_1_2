package m2.android.archetype.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;

public class AppInfoUtility {
	private static Logger logger = Logger.getLogger(AppInfoUtility.class);
	private static String versionName = null;
	
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
	

}
