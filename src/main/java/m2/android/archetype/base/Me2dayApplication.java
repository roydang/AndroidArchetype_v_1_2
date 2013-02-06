package m2.android.archetype.base;

import java.io.File;
import java.io.IOException;

import m2.android.archetype.sharedpref.UserSharedPrefModel;
import m2.android.archetype.util.Logger;
import m2.android.archetype.util.Utility;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;

import com.nhn.android.archetype.base.AABaseApplication;

/**
 * 미투데이 어플리케이션 Singleton Config를 관리한다.
 * 
 * @author telltale
 * 
 */
public class Me2dayApplication extends AABaseApplication {

	private static Logger logger = Logger.getLogger(Me2dayApplication.class);
	private String userAgent = null; 
	
	private static Me2dayApplication instance;
	private static Activity activity;
	private static Fragment fragment;
	
	private static String externalStorageDirectory;
	private static String externalStorageMe2dayDirectory;
	static {
		if (Build.VERSION.SDK_INT >= 17) { //Build.VERSION_CODES.JELLY_BEAN_MR1 // 4.2 이상
			externalStorageDirectory = "/sdcard/Android/data/";
			externalStorageMe2dayDirectory = "/sdcard/me2day";
		} else {
			externalStorageDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/";
			externalStorageMe2dayDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() + "/me2day";
		}
	}

	public static Me2dayApplication getCurrentApplication() {
		return instance;
	}
	
	public static Context getGlobalContext() {
		return getCurrentApplication().getApplicationContext();
	}
	
	public static Me2dayApplication getCurrentApplication(Context context) {
		setCurrentApplication(context);
		
		return instance;
	}	

	public static void setCurrentApplication(Context context) {
		logger.d("setCurrentApplication: %s", context.getApplicationContext().getClass().getSimpleName());
		instance = (Me2dayApplication) context.getApplicationContext();
	}
	
	public static Activity getCurrentActivity() {
		
		return activity;
	}
	
	public static void setCurrentActivity(Activity currentactivity) {
		logger.d("setCurrentActivity: %s", currentactivity.getClass().getName());
		activity = currentactivity;
	}
	
	public static void setCurrentFragmet(Fragment currentFragment) {
		fragment = currentFragment;
	}
	public static Fragment getCurrentFragmet() {
		return fragment;
	}
	public static Handler getCurrentHandler() {
		return instance.getHandler();
	}
	public static Handler getCurrentBackgroundHandler() {
		return instance.getBackgroundHandler();
	}
	

	static public boolean isAvailableExternalMemory() {
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else {
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}

		return (mExternalStorageAvailable == true && mExternalStorageWriteable == true);
	}


	public String getLoginId() {
		return UserSharedPrefModel.get().getUserId();
	}

	public String getFullAuthToken() {
		return UserSharedPrefModel.get().getFullAuthToken();
	}

	// private boolean mIsCreatedCacheFolder = false;
	public void createCacheFolder() {
		if (isAvailableExternalMemory() == false) {
			return;
		}

		File file = new File(externalStorageDirectory + getPackageName());
		if (file.exists() == false) {
			boolean result = file.mkdirs();
			if (result) {
				// do something
			}
		}
		File cachefile = new File(file.getAbsolutePath() + "/cache");
		if (cachefile.exists() == false) {
			boolean result = file.mkdirs();
			if (result) {
				// do something
			}
		}

		File imageFile = new File(file.getAbsoluteFile() + "/images/camera");
		if (imageFile.exists() == false) {
			boolean result = imageFile.mkdirs();
			if (result) {
				// do something
			}
		}
		// mIsCreatedCacheFolder = true;
	}

	private File externalCacheFolder = null;

	public File getExternalCacheFolder() {
		if (isAvailableExternalMemory() == false) {
			return null;
		}

		if (externalCacheFolder == null) {
			externalCacheFolder = new File(externalStorageDirectory + getPackageName() + "/cache");

			if (!externalCacheFolder.exists()) {
				boolean result = externalCacheFolder.mkdirs();
				if (result) {
					// do something
				}
			}

			// create no media
			File nomedia = new File(externalCacheFolder, ".nomedia");
			logger.d("search nomedia: %s", nomedia.exists());
			if (!nomedia.exists()) {
				try {
					logger.d("Create nomedia");
					nomedia.createNewFile();
				} catch (IOException e) {
					logger.e(e);
				}
			}
		}

		return externalCacheFolder;
	}

	public File getExternalImagesFolder() {
		
		return getExternalImagesFolder(UserSharedPrefModel.get().getPhotoFolderType());
	}
	
	public File getExternalImagesCacheFolder() {
		return getExternalImagesFolder(UserSharedPrefModel.PHOTOFOLDER_TYPE_CACHE);
	}

	public File getExternalImagesFolder(int type) {
		if (isAvailableExternalMemory() == false) {
			return null;
		}

		File folder = null;

		if (type == UserSharedPrefModel.PHOTOFOLDER_TYPE_CACHE) {
			folder = new File(externalStorageDirectory + getPackageName() + "/images");
		} else if (type == UserSharedPrefModel.PHOTOFOLDER_TYPE_SD) {
			folder = new File(externalStorageMe2dayDirectory);
		}
		
		File cameraFolder = new File(folder.getAbsolutePath() + "/camera");
		if (cameraFolder != null && cameraFolder.exists() == false) {
			boolean result = cameraFolder.mkdirs();
			if (result) {
				// do something
			}
		}
		
		return folder;
	}
	
	public File getExternalFolder() {
		if (isAvailableExternalMemory() == false) {
			return null;
		}

		File folder = new File(externalStorageMe2dayDirectory);

		File cameraFolder = new File(folder.getAbsolutePath() + "/camera");
		if (cameraFolder != null && cameraFolder.exists() == false) {
			boolean result = cameraFolder.mkdirs();
			if (result) {
				// do something
			}
		}

		return folder;
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



	@Override
	public String getAppKey() {		
		return BaseConstants.APP_KEY;
	}

	@Override
	public String getAppSig() {
		return Utility.getAppSig();
	}

	@Override
	public String getUserAgent() {
		if (userAgent == null) {
			userAgent = createUserAgentStr(getCurrentApplication());
		}
		return userAgent;
	}

	@Override
	public String getUserId() {
		return UserSharedPrefModel.get().getUserId();
	}
	
	/**
	 * USER AGENT 구성
	 * @param context
	 * @return
	 */
	public static String createUserAgentStr(Context context) {
		String appVersion = null;
		String strDeviceName = "devicename";

		try {
			appVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
			strDeviceName = Build.MANUFACTURER + " " + Build.MODEL;
		} catch (PackageManager.NameNotFoundException ex) {
			appVersion = "x.x";
		}

		strDeviceName = Build.MANUFACTURER + " " + Build.MODEL;
		String strUserAgentValue = ("me2day/" + appVersion + " (Android OS " + Build.VERSION.RELEASE + ";" + strDeviceName + ")");
		return strUserAgentValue;
	}
	
}
