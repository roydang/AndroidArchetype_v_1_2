package m2.android.archetype.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import m2.android.archetype.util.Logger;
import m2.android.archetype.util.Utility;
import android.util.Log;

import com.nhn.android.archetype.base.worker.Worker;

public class Me2dayConfigLoader {

	private static Logger logger = Logger.getLogger(Me2dayConfigLoader.class);

	public static final String DEFAULT_CONFIG_FILE_NAME = "/sdcard/m3-config.properties";
	private static final String LOG_TAG = "M3";

	// 0:REAL, 1:STAGING, 2:MOCKUP
	public static final String API_MODE = "api_mode";
	public static final String STRICT_MODE = "strict_mode";
	public static final String DEBUG_MODE = "debug_mode";
	// nelo 전송여부. 로그인정보까지 전송하므로 반드시 리얼 배포시는 FALSE로 처리해야 함.
	public static final String SEND_NELO = "send_nelo";
	public static final String LOG_ON_ERROR = "log_on_error";
	// get_lastest_app_version 시 사용하는 flag 임 디버그 모드와 상용모드 분리버전체크
	// 상용 배포시에는 반드시 true 변경
	public static final String VERSION_CHECK_MARKET_RELEASE_MODE = "market_release_mode";
	private static final String CONFIG_TEST_NAVER_ID = "test_naver_id";
	private static final String CONFIG_TEST_NAVER_PW = "test_naver_pw";
	private static final String CONFIG_TEST_ME2DAY_ID = "test_me2day_id";
	private static final String CONFIG_TEST_ME2DAY_PW = "test_me2day_pw";

	/*
	 * 개발 모드 api_mode=1 strict_mode=false debug_mode=true send_nelo=true
	 * log_on_error=true market_release_mode=false
	 */

	/*
	 * 리얼 모드 api_mode=0 strict_mode=false debug_mode=false send_nelo=false
	 * log_on_error=false market_release_mode=true
	 */

	Properties config;

	public void load() {

		Log.i(LOG_TAG, "##" + LOG_TAG + "##  loadProperty config load");

		File propertyFile = new File(DEFAULT_CONFIG_FILE_NAME);

		if (propertyFile.exists()) {

			config = new Properties();
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(propertyFile);
				config.load(fis);
				loadComplete();
				logger.d(toString());

				Log.i(LOG_TAG, "##" + LOG_TAG + "## me2day loadProperty config load success");
			} catch (Exception e) {
				Log.i(LOG_TAG, String.format("##" + LOG_TAG + "## me2day loadProperty config load fail(%s)", e.getMessage()));
				logger.w(e.getMessage());
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						// - do nothing
					}
				}
			}
		}
	}

	private void loadComplete() {

		AppBuildCheckFlag.API_MODE = getApiMode();
		AppBuildCheckFlag.STRICT_MODE = isStrictMode();
		AppBuildCheckFlag.DEBUG_MODE = isDebugMode();
		AppBuildCheckFlag.SEND_NELO = isSendNelo();
		AppBuildCheckFlag.LOG_ON_ERROR = isLogOnError();
		AppBuildCheckFlag.VERSION_CHECK_MARKET_RELEASE_MODE = isMarketReleaseMode();

		AppBuildCheckFlag.TEST_NAVER_ID = getTestNaverId();
		AppBuildCheckFlag.TEST_NAVER_PW = getTestNaverPw();
		AppBuildCheckFlag.TEST_METOO_ID = getTestMe2dayId();
		AppBuildCheckFlag.TEST_METOO_PW = getTestMe2dayPw();

	}

	public int getApiMode() {
		String apiMode = config.getProperty(API_MODE, null);
		int mode = -1;
		if (apiMode != null && apiMode.length() > 0) {
			mode = Integer.parseInt(apiMode);
		}
		return mode;
	}

	public boolean isStrictMode() {
		return booleanPropertyValue(STRICT_MODE);
	}

	public boolean isDebugMode() {
		return booleanPropertyValue(DEBUG_MODE);
	}

	public boolean isSendNelo() {
		// 리얼배포전 확인해야 함.
		return booleanPropertyValue(SEND_NELO, true);
	}

	public boolean isLogOnError() {
		return booleanPropertyValue(LOG_ON_ERROR);
	}

	public boolean isMarketReleaseMode() {
		return booleanPropertyValue(VERSION_CHECK_MARKET_RELEASE_MODE);
	}

	public String getTestNaverId() {
		return config.getProperty(CONFIG_TEST_NAVER_ID, "");
	}

	public String getTestNaverPw() {
		return config.getProperty(CONFIG_TEST_NAVER_PW, "");
	}

	public String getTestMe2dayId() {
		return config.getProperty(CONFIG_TEST_ME2DAY_ID, "");
	}

	public String getTestMe2dayPw() {
		return config.getProperty(CONFIG_TEST_ME2DAY_PW, "");
	}

	private boolean booleanPropertyValue(String key, boolean defaultValue) {
		boolean isResult = false;
		String mode = config.getProperty(key, "");
		if (Utility.isNotNullOrEmpty(mode)) {
			if ("true".equals(mode)) {
				isResult = true;
			}
		}
		return isResult;
	}

	private boolean booleanPropertyValue(String key) {
		return booleanPropertyValue(key, false);
	}

	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("\n").append(API_MODE).append("=").append(getApiMode()).append(" || \n");
		sb.append(STRICT_MODE).append("=").append(isStrictMode()).append(" || \n");
		sb.append(DEBUG_MODE).append("=").append(isDebugMode()).append(" || \n");
		sb.append(SEND_NELO).append("=").append(isSendNelo()).append(" || \n");
		sb.append(LOG_ON_ERROR).append("=").append(isLogOnError()).append(" || \n");
		sb.append(VERSION_CHECK_MARKET_RELEASE_MODE).append("=").append(isMarketReleaseMode()).append(" || \n");
		sb.append(CONFIG_TEST_NAVER_ID).append("=").append(getTestNaverId()).append(" || \n");
		sb.append(CONFIG_TEST_NAVER_PW).append("=").append(getTestNaverPw()).append(" || \n");
		sb.append(CONFIG_TEST_ME2DAY_ID).append("=").append(getTestMe2dayId()).append(" || \n");
		sb.append(CONFIG_TEST_ME2DAY_PW).append("=").append(getTestMe2dayPw()).append(" || \n");

		return sb.toString();
	}

	public static void loadProperty() {
		Log.i(LOG_TAG, "##" + LOG_TAG + "## me2day loadProperty start");
		Worker worker = new Worker() {
			@Override
			protected void doWork() {
				new Me2dayConfigLoader().load();
			}
		};
		worker.post();
	}

}
