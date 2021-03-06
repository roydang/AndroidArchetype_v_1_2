package m2.android.archetype.base;

import java.io.File;
import java.io.IOException;

import m2.android.archetype.sharedpref.UserSharedPrefModel;
import m2.android.archetype.util.AppInfoUtility;
import m2.android.archetype.util.Logger;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import com.nhn.android.archetype.base.AABaseApplication;

/**
 * 미투데이 어플리케이션 Singleton Config를 관리한다.
 * 
 * @author telltale
 * 
 */
public class M3Application extends AABaseApplication {

	private static Logger logger = Logger.getLogger(M3Application.class);

	private static M3Application instance;
	
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
	@Override
	public void onCreate() {
		super.onCreate();
		setCurrentApplication(this);
	
	}
	public static M3Application getCurrentApplication() {
		return instance;
	}
	
	public static Context getGlobalContext() {
		return getCurrentApplication().getApplicationContext();
	}
	
	public static M3Application getCurrentApplication(Context context) {
		setCurrentApplication(context);
		
		return instance;
	}	

	public static void setCurrentApplication(Context context) {
		logger.d("setCurrentApplication: %s", context.getApplicationContext().getClass().getSimpleName());
		instance = (M3Application) context.getApplicationContext();
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

			logger.d("##externalCacheFolder.exists(%s)", externalCacheFolder.exists());
			if (!externalCacheFolder.exists()) {
				boolean result = externalCacheFolder.mkdirs();
				logger.d("##externalCacheFolder.result(%s)", result);
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




	// m2base 용 API - m2base 가 필요없어지면 삭제 필요
	@Override
	public String getAppKey() {		
		return AppInfoUtility.APP_KEY;
	}

	@Override
	public String getAppSig() {
		return AppInfoUtility.getAppSig();
	}

	@Override
	public String getUserAgent() {		
		return AppInfoUtility.getUserAgentStr(getCurrentApplication());
	}

	@Override
	public String getUserId() {
		return AppInfoUtility.getUserId();
	}
	

	@Override
	public String getFullAuthToken() {
		return AppInfoUtility.getFullAuthToken();
	}
	
}
