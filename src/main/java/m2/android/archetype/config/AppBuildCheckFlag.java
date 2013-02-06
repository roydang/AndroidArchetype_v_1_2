package m2.android.archetype.config;

import m2.android.archetype.api.BaseProtocolEnum;

import com.nhn.android.archetype.base.util.internal.M2baseLogger;
/**
 * 
 * 앱 빌드시 반드시 체크해서 수정해야 하는 플래그 클래스
 * 
 * 디버그 모드 및 로그 등의 플래그를 설정할 수 있다.
 * 
 * @author nhn
 */
/**
 * 
 * 앱 빌드시 반드시 체크해서 수정해야 하는 플래그 클래스
 * 
 * 디버그 모드 및 로그 등의 플래그를 설정할 수 있다.
 * 
 * @author nhn
 */
public class AppBuildCheckFlag {

	
	//########################################################### //
	//###### 아래의 변수는 사내 배포시에 false 마켓 등록시엔 true  #### //
	//########################################################### //
	public static boolean VERSION_CHECK_MARKET_RELEASE_MODE = true;
	
	
	//############################################################### //
	//###### 아래의 변수들은 수정하지 않는다 default 값은 마켓등록 모드 #### //
	//############################################################### //
	
	// 상용 배포시에는 REAL 로 고정 0:REAL, 1:STAGING, 2:MOCKUP, 3:API별 독립적용
	public static int API_MODE = BaseProtocolEnum.API_MODE_REAL; // default RealMode
	
	public static boolean STRICT_MODE = false;
	public static boolean DEBUG_MODE = false;
	
	// nelo 전송여부. 로그인정보까지 전송하므로 반드시 리얼 배포시는 FALSE로 처리해야 함.
	public static boolean ERROR_REPORTER_INIT = false; // 체험판 배포에는 오류 보고가 포함되기 때문에 	
	public static boolean SEND_NELO = false; 	
	public static boolean LOG_ON_ERROR = false;	
	
	// 상용배포시 google map key 도 변경해야함
	// include_neighbor_google_map.xml 파일에 있는 key 변경	
	
	public static String TEST_NAVER_ID = "";
	public static String TEST_NAVER_PW = "";
	public static String TEST_METOO_ID = "";
	public static String TEST_METOO_PW = "";


	
	//강제 구글맵 실행
	public static boolean MAP_FORCE_GOOGLE	= false;
	static {
		if (VERSION_CHECK_MARKET_RELEASE_MODE) {
			initMarketReleaseMode();
		} else {
			initDebugReleaseMode();
		}
	}
	//############################################################//
	
	/**
	 * debug 모드 일경우 아래의 변수를 적절히 세팅한다.
	 */
	private static void initDebugReleaseMode() {
		
		TEST_NAVER_ID = "";
		TEST_NAVER_PW = "";
		TEST_METOO_ID = "";
		TEST_METOO_PW = "";
		
		API_MODE = BaseProtocolEnum.API_MODE_INDEPENDENT;
		STRICT_MODE = false; 
		DEBUG_MODE = true;
		ERROR_REPORTER_INIT = true;
		SEND_NELO = true;
		LOG_ON_ERROR = true;
		MAP_FORCE_GOOGLE = false; // true 로 바꿀경우 include_neighbor_google_map.xml 파일의 API KEY 변경
		
		// m2base 관련 로그 설정
		M2baseLogger.DEBUG_MODE = true;
		M2baseLogger.LOG_ON_ERROR = true;
		
	}
	/**
	 * 아래의 메소드는 수정하지 않는다
	 */
	private static void initMarketReleaseMode() {
		
		
		API_MODE = BaseProtocolEnum.API_MODE_REAL;
		STRICT_MODE = false; 
		DEBUG_MODE = false;
		ERROR_REPORTER_INIT = false;
		SEND_NELO = false;
		LOG_ON_ERROR = false;
		MAP_FORCE_GOOGLE = false;
	}
	 

	
}
