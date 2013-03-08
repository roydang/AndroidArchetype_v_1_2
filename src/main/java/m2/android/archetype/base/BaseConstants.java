package m2.android.archetype.base;


public interface BaseConstants {

	public static final String CUSTOM_SCHEME = "m2app";
	public static final String RET_FAIL = "-1";
	public static final String RET_SUCCESS = "0";
	public static final String RET_UNAUTHORIZED = "401";

	public static boolean flagUseGzip = true;
	public static final String ENC_TYPE = "gzip";
	public static final String CARRIER_OTHER = "OTHER";
	public static final String COUNTRY_CODE_KOREA = "+82";
	public static final String COUNTRY_CODES[] = {"+82", "+1", "+81", "+86", "+61", "+44", "+1", "+63", "+852", "+66"};
	
	
	// Definition for the Handler Message ID
	// 1~9 : Common
	public static final int MSG_TIMER_EXPIRED = 1;

	public static final String NAVER_MAP_KEY = "qzaBE37Zx9BaCNFJzj4qvU7rWPqncq2QOgWrh2Da8GtkpbrE5uKc6E2cnXyvLUY9";
	public static final String NAVER_CAMERA_APP_PACKAGE_NAME	= "com.nhn.android.ncamera";	
	public static final String PHONE_NUMBER_INVALID_FORMAT = "INVALID_FORMAT";
	
	//더보기 View mode
	public static final int MODE_PREVIOUS_VIEW = 0;
	public static final int MODE_NEXT_VIEW = 1;
	public static final int MODE_INIT_VIEW = 2;
	public static final int MODE_UPDATE_VIEW = 3;

	//글감 추가
	public static final String CONTENT_DOMAIN_MUSIC_ALBUM = "music_album";
	public static final String CONTENT_DOMAIN_BOOK = "book";
	public static final String CONTENT_DOMAIN_MOVIE = "movie";
	public static final String CONTENT_DOMAIN_TV = "tv";
	public static final String CONTENT_DOMAIN_CARTOON = "cartoon";
	public static final String CONTENT_DOMAIN_MUSIC = "music";
	public static final String CONTENT_DOMAIN_SCHEDULE = "schedule";
	public static final String CONTENT_DOMAIN_ME2SPOT = "me2spot";

	//CUSTOM URL HOST 정보
	public static final String APPURL_HOST_INVITAION 	= "invitation";
	public static final String APPURL_INVITATION_URL	= "url";


	public static final String PATTERN_BIRTHDAY_ORG = "MMdd";
	public static final String PATTERN_BIRTHDAY_DIS	= "MM.dd";

	public static final String PATTERN_SINCE_ORG 	= "yyyyMMdd";
	public static final String PATTERN_SINCE_DIS	= "yyyy.MM.dd";
	
	// 1depth 탭메뉴 하단 메뉴 
	public static final String TABMENU_STREAM 		= "stream";
	public static final String TABMENU_MY 			= "my";
	public static final String TABMENU_FRIENDS 		= "friends";
	public static final String TABMENU_PEOPLE 		= "people";
	public static final String TABMENU_NEIGHBOR		= "neighbor";
	
	// 나는 2depth 메뉴
	
	// 1depth 탭메뉴 하단 메뉴 
	public static final String TAM_MY_STORY 		= "my_story";
	public static final String TAM_MY_PHOTO			= "my_photo";
	public static final String TAM_MY_THEME 		= "my_theme";
	public static final String TAM_MY_METOO 		= "my_metoo";
	
	public static final int MAX_LEN_MEMBER_NAME 	= 10;
	public static final int MAX_LEN_BAND_NAME 		= 10;
	public static final int MAX_LEN_MY_DESC 		= 10;
	public static final int MAX_LEN_SCHEDULE_TITLE 	= 20;
	public static final int MAX_LEN_SCHEDULE_DESC 	= 50;
	
	//프로필 / 커버 이미지 Crop 관련
	public static final int CROP_OUTPUT_X_COVER 	= 640;
	public static final int CROP_OUTPUT_Y_COVER 	= 881;
	public static final int CROP_ASPECT_X_COVER 	= 85;
	public static final int CROP_ASPECT_Y_COVER 	= 117;
	
	//나는 커버 이미지
	//프로필 / 커버 이미지 Crop 관련
	public static final int CROP_OUTPUT_X_COVER_BG 	= 640;
	public static final int CROP_OUTPUT_Y_COVER_BG 	= 427;
	public static final int CROP_ASPECT_X_COVER_BG 	= 480;
	public static final int CROP_ASPECT_Y_COVER_BG 	= 320;
	
	//v3.0.0 글쓰기
	public static final int MAX_MSG_TAG_LENGTH = 150;	
	public static final String HTTP_PREFIX = "http://";
	public static final String HTTPS_PREFIX = "https://";
	public static final String M_HTTP_PREFIX = "http://m.";
	public static final String HOST_PREFIX = "me2day.net";
	public static final String FTP_PREFIX = "ftp://";		
	public static final int TAB_PHOTO_ALBUM_LIST = 1;
	public static final int TAB_VIDEO_ALBUM_LIST = 2;	
	public static final int DEFAULT_MAX_COUNT = 5;
	public static final String PHOTO_CAMERA_FILENAME = "me2day_camera.jpg";
	public static final String PHOTO_RESIZE_FILENAME = "me2day_resized.jpg";	
	public static final int IMAGE_MIME_TYPE = 1;
	public static final int VIDEO_MIME_TYPE = 2;	
	public static final String URL_NAVER_REALTED_APP = "http://m.naver.com/services.html";	
	public static final int CONTENT_NONE = 1000;
	public static final int CONTENT_MUSIC = 1001;
	public static final int CONTENT_MOVIE = 1002;
	public static final int CONTENT_BOOK = 1003;	
	public static final int AUTOCOMPLETE_LISTVIEW_ROW_COUNT = 20;
	public static final String ME2_HELP = "me2help";
	public static final String NAVER_WEBTOON = "naver_webtoon";
	public static final int DEFAULT_REQUEST_COUNT = 20;
	public static final int MAX_RECORDING_MILLISECOND = 5 * 60000;
	public static final int TAB_CONTENT_MOVIE_LIST = 1;
	public static final int TAB_CONTENT_MUSIC_LIST = 2;
	public static final int TAB_CONTENT_BOOK_LIST = 3;
	
	/** 글감검색 */
	
	public static final String POI_SEARCH_KIND_SCHEDULE = "schedule";	

	/** 글상세 */
	public static final String VIDEO_URL_REPLACE = "logo";
	public static final String BACKGROUND_VIDEO_SIZE = "f255"; // 255 or 510
	public static final String ALBUM_LOAD_SIZE = "20";
	
	/**
	 * 글목록
	 */
	public static final String SCOPE_CONTENT_PHOTO = "content[me2photo]";
	public static final String SCOPE_CONTENT_THEME = "content[music|book|movie]";
	public static final String DIRECTION_BEFORE = "before";
	public static final String DIRECTION_AFTER = "after";
	public static final String DIRECTION_TO = "to";
	public static final String DIRECTION_DEFAULT = null;
	public static final String WRITE_MY_METOO_DIALOG_ID = "write_my_metoo";
	public static final int COUNT = 40;
	
	// 모아보는 scope parameter
	public static final int STREAM_DATA_TYPE_ALL 			= 201;
	public static final int STREAM_DATA_TYPE_SCOPE 			= 202;
	public static final int STREAM_DATA_TYPE_BAND 			= 203;
	public static final int STREAM_DATA_TYPE_FRIENDGROUP	= 204;
	
	//친구들은 scope parameter
	public static final int FRIENDS_DATA_TYPE_ALL 			= 401;
	public static final int FRIENDS_DATA_TYPE_SCOPE 			= 402;
	public static final int FRIENDS_DATA_TYPE_FRIENDGROUP	= 403;
	public static final int FRIENDS_DATA_TYPE_FAVORITE	= 404;
	public static final int FRIENDS_DATA_TYPE_RSS	= 405;
	public static final int FRIENDS_DATA_TYPE_BF	= 406;
	public static final int FRIENDS_DATA_TYPE_SUPPORTER	= 407;
	
	public static final String SCOPE_CELLPHONE					= "cellphone";
	
	
	// 글상세 
	public static final int OPTION_MENUID_TRANSLATE = 100;			/** 번역하기 */
	public static final int OPTION_MENUID_TRANSLATE_ORIGINAL = 101;	/** 원문글 보기 */
	public static final int OPTION_MENUID_SHARE_VIA = 102;			/** 공유하기 */	
	public static final int OPTION_MENUID_COPY_PERMERLINK = 103;	/** URL 복사 */
	public static final int OPTION_MENUID_COPY_TEXT = 104;			/** 복사하기 */
	public static final int OPTION_MENUID_EDIT_TAG = 105;			/** 태그수정 */
	public static final int OPTION_MENUID_ALARM_ON = 106;			/** 개별글 알림 받기 */
	public static final int OPTION_MENUID_ALARM_OFF = 107;			/** 개별글 알림 끄기 */
	public static final int OPTION_MENUID_DELETE_POST = 108;		/** 글삭제 */
	public static final int OPTION_MENUID_DECLARATION = 109;		/** 신고하기 */
	public static final int OPTION_MENUID_EDIT_LIST = 110;			/** 리스트 수정하기 */
	
	//LINK 설정 TEXT
	public static final String LINK_HEAD = "<a href='http://%s'>";
	public static final String LINK_ME2DAY_HEAD = "<a href='http://me2day.net/%s'>";
	public static final String LINK_TAIL = "</a>";
	
	
	public static final String PARAM_IS_FRIEND			= "is_friend";	
	public static final String PARAM_PERSON_ID 			= "person_id";
	public static final String PARAM_PERSON_NICKNAME 	= "person_nickname";
	public static final String PARAM_PERSON_OBJ 		= "person_obj";
	
	//프로필
	public static final String PARAM_PROFILE 			= "profile";
	public static final String PARAM_NICKNAME 			= "nickname";
	public static final String PARAM_DESCRIPTION 		= "description";
	
	public static final String RELATIONSHIP_SMS_FRIEND = "friend_sms";
	public static final String RELATIONSHIP_NOT_FRIEND = "none";
	public static final String RELATIONSHIP_PIN = "pin";
	public static final String RELATIONSHIP_FRIEND = "friend";
	public static final String RELATIONSHIP_RECEIVED = "friend_received";
	public static final String RELATIONSHIP_REQUEST_FRIEND = "friend_request";
	public static final String RELATIONSHIP_PIN_REQUEST_FRIEND = "pin|friend_request";
	public static final String VALUE_FRIEND_UNSET = "unset";
	
	public static final String BLOCK_TYPE_POST = "post";
	public static final String BLOCK_TYPE_CHAT = "chat";
	public static final String BLOCK_TYPE_ALL = "all";
	public static final String BLOCK_TYPE_NONE = "none";
	
	public static final String DIRECTION_NEXT = "next";
	
	//미투 / 소환 한 글 
	public static final String SCOPE_METOO = "metoo";
	public static final String SCOPE_MENTIONED = "mentioned";

	//포스팅 
	public static final String MENU_POST = "post";
	
	//친구들은
	public static final String SCOPE_ORDER_INTIMACY = "intimacy";
	public static final String SCOPE_ORDER_LAST_POSTED_AT = "last_posted_at";
	public static final int FRIENDS_SPINNER_ALL   				= 1;
	public static final int FRIENDS_SPINNER_FRIEND_FAVORITE		= 2;
	public static final int FRIENDS_SPINNER_FRIEND_BF			= 3;
	public static final int FRIENDS_SPINNER_FRIEND_SUPPORTER	= 4;
	public static final int FRIENDS_SPINNER_FRIEND_RSS			= 5;
	public static final int FRIENDS_SPINNER_FRIEND_GROUP 		= 6;
	public static final int FRIENDS_SPINNER_NONE_GROUP			= 7;
	
	public static final int SCOPE_INDEX_INIT = -1;
	public static final int SCOPE_INDEX_PHONEBOOK = 0;
	public static final int SCOPE_INDEX_SHAKE = 1;
	public static final int SCOPE_INDEX_REC_FRIEND = 1;
	
	public static final String SCOPE_MY_CONTENT_THEME = "theme";
	public static final String SCOPE_MY_CONTENT_PHOTO = "photo";
	
	// 설정 도움말/문의 및 신고 URL
	public static final String URL_SETTING_HELP 					= "http://m.help.naver.com/serviceMain.nhn?falias=mo_me2day_app&type=faq";
	public static final String URL_SETTING_QUESTIONS_DECLARATION	= "http://m.help.naver.com/serviceMain.nhn?falias=mo_me2day_app&type=faq#mail";
	
	//사람들은
	public static final int APPLY_CHAT = 1;
	public static final int READY_CHAT = 2;
	public static final int ONGOING_CHAT = 3;
}
