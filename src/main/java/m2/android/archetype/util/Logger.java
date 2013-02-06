package m2.android.archetype.util;

import java.util.logging.Level;

import m2.android.archetype.config.AppBuildCheckFlag;
import android.util.Log;

public class Logger {
	private String TAG = "unknown";

	private static boolean isFirstCall = true;
	private static java.util.logging.Logger loggerWire;
	private static java.util.logging.Logger loggerHeaders;

	private String category;
	private Boolean debugEnabled = null;
	private Boolean errorEnabled = null;

	public String getCategory() {
		return category;
	}

	public boolean isDebugEnabled() {
		if (debugEnabled == null) {
			return AppBuildCheckFlag.DEBUG_MODE;
		}

		return debugEnabled;
	}

	public void setDebugEnabled(boolean debugEnabled) {
		this.debugEnabled = debugEnabled;
	}

	public boolean isErrorEnabled() {
		if (errorEnabled == null) {
			return AppBuildCheckFlag.LOG_ON_ERROR;
		}

		return debugEnabled;
	}

	public void setErrorEnabled(boolean errorEnabled) {
		this.errorEnabled = errorEnabled;
	}

	private Logger(String category) {
		this.category  = category;
	}

	/**
	 * 디버그 로그
	 */
	public void d(String msg, Object... params) {
		if (isDebugEnabled()) {
			if (params != null && params.length > 0) {
				msg = String.format(msg, params);
			}

			// dmJe : 너무 긴 문자열은 잘라서 출력한다. : 더 좋은 퍼포먼스 로직이 있으면 수정해 주세요.
			if (msg.length() > 3000) {
				Log.d(TAG, "--------------------------------------------------------------");

				while (true) {
					if (msg.length() > 3000) {
						String split = msg.substring(0, 3000);
						Log.d(TAG, split);

						msg = msg.substring(3000);
					} else {
						Log.d(TAG, msg);
						Log.d(TAG, "--------------------------------------------------------------");
						break;
					}
				}
			} else {
				Log.d(TAG, msg);
			}

			/*
			 * use httpclient logging
			 adb shell setprop log.tag.org.apache.http VERBOSE
			 adb shell setprop log.tag.org.apache.http.wire VERBOSE
			 adb shell setprop log.tag.org.apache.http.headers VERBOSE
			 */
			if (isFirstCall == true) {
				//LOST_LOGGER_DUE_TO_WEAK_REFERENCE 문제로 아래와 같이 변경
				loggerWire = java.util.logging.Logger.getLogger("org.apache.http.wire");
				loggerWire.setLevel(Level.FINEST);
				//java.util.logging.Logger.getLogger("org.apache.http.headers").setLevel(java.util.logging.Level.FINEST);
				System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
				System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
				System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "debug");
				System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "debug");
				//System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.headers", "debug");

				isFirstCall = false;
			}
		}
	}

	public void i(String msg, Object... params) {
		if (isDebugEnabled()) {
			if (params != null && params.length > 0) {
				msg = String.format(msg, params);
			}


			// dmJe : 너무 긴 문자열은 잘라서 출력한다. : 더 좋은 퍼포먼스 로직이 있으면 수정해 주세요.
			if (msg.length() > 3000) {
				Log.i(TAG, "--------------------------------------------------------------");

				while (true) {
					if (msg.length() > 3000) {
						String split = msg.substring(0, 3000);
						Log.i(TAG, split);

						msg = msg.substring(3000);
					} else {
						Log.i(TAG, msg);
						Log.i(TAG, "--------------------------------------------------------------");
						break;
					}
				}
			} else {
				Log.i(TAG, msg);
			}

			/*
			 * use httpclient logging
			 adb shell setprop log.tag.org.apache.http VERBOSE
			 adb shell setprop log.tag.org.apache.http.wire VERBOSE
			 adb shell setprop log.tag.org.apache.http.headers VERBOSE
			 */
			if (isFirstCall == true) {
				//LOST_LOGGER_DUE_TO_WEAK_REFERENCE 문제로 아래와 같이 변경
				loggerWire = java.util.logging.Logger.getLogger("org.apache.http.wire");
				loggerWire.setLevel(Level.FINEST);
				//java.util.logging.Logger.getLogger("org.apache.http.headers").setLevel(java.util.logging.Level.FINEST);
				System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
				System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
				System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "debug");
				System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "debug");
				//System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.headers", "debug");

				isFirstCall = false;
			}
		}
	}

	public void w(String msg, Object... params) {
		if (params != null && params.length > 0) {
			msg = String.format(msg, params);
		}

		Log.d(TAG, String.format("[WARN] %s", msg));
	}

	/**
	 *
	 * @param category
	 * @param e
	 */
	public void e(Throwable e) {
		e(e.toString(), e, false);
	}

	public void e(Throwable e, boolean dump) {
		e(e.toString(), e, dump);
	}

	/**
	 *
	 * @param category
	 * @param msg
	 * @param e
	 */
	public void e(String msg, Throwable e, boolean dump) {
		if (isErrorEnabled()) {
			Log.d(TAG, "[ERROR] " + msg);

			if (e != null) {
				Log.d(TAG, e.toString());
				StackTraceElement[] elem = e.getStackTrace();
				if (elem != null && elem.length > 0) {
					for (int i = 0; i < elem.length; i++) {
						if (i > 8) {
							break;
						}
						Log.d(TAG, String.format("%s (%s)", elem[i].getClassName(), elem[i].getLineNumber()));
					}
				}
				Log.e(category, e.toString(), e);
			}

			if (isFirstCall == true) {
				loggerWire = java.util.logging.Logger.getLogger("org.apache.http.wire");
				loggerWire.setLevel(Level.FINEST);
				loggerHeaders = java.util.logging.Logger.getLogger("org.apache.http.headers");
				loggerHeaders.setLevel(Level.FINEST);

				System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
				System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
				System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "debug");
				System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "debug");
				System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.headers", "debug");

				isFirstCall = false;
			}
		}
	}

	public void logStackTrace() {
		if (isDebugEnabled()) {
			try {
				throw new RuntimeException();
			} catch (Exception e) {
				StackTraceElement[] elem = e.getStackTrace();
				if (elem != null && elem.length > 1) {
					for (int i = 1; i < elem.length; i++) {
						if (i > 8) {
							break;
						}

						StackTraceElement info = elem[i];
						d("CALLSTACK(%s): %s.%s (%s)", i, info.getClassName(), info.getMethodName(), info.getLineNumber());
					}
				}
			}
		}
	}

	public static Logger getLogger(String category) {
		Logger logger = new Logger(category);
		logger.setTAG("#M2# " + category);
		return logger;
	}

	@SuppressWarnings("rawtypes")
	public static Logger getLogger(Class clazz) {
		return getLogger(clazz.getSimpleName());
	}

	public String getTAG() {
		return TAG;
	}

	public void setTAG(String tAG) {
		TAG = tAG;
	}
}
