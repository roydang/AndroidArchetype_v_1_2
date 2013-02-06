package m2.android.archetype.base;

public interface ParameterConstants {
	public static final String PARAM_WHERE 				= "param_where";
	
	//VALUES OF FROM_WHERE
	public static final int WHERE_ACCEPT_INVITATION		= 1;
	public static final int WHERE_CREATE_GROUP			= 2;
	public static final int WHERE_BAND_LIST				= 3;
	public static final int WHERE_CUSTOM_URL			= 4;
	public static final int WHERE_POST_VIEW				= 5;
	public static final int WHERE_BOARD_LIST			= 6;
	public static final int WHERE_PUSH_STATUS			= 7;
	public static final int WHERE_ALBUM					= 8;
	public static final int WHERE_CHAT_POST_BAND		= 9;
	public static final int WHERE_MAP_GOOGLE			= 10;
	public static final int WHERE_MAP_NAVER				= 11;
	public static final int WHERE_LOGIN					= 12;
	public static final int WHERE_NAVER_LOGIN			= 13;
	public static final int WHERE_ME2DAY_LOGIN			= 14;
	public static final int WHERE_ME2DAY_INFO_INPUT		= 15;
	public static final int WHERE_NAVER_INFO_INPUT		= 16;

	//VALUES OF BAND_SET_TYPE 
	public static final int BAND_SET_TYPE_NORMAL		= 1;
	public static final int BAND_SET_TYPE_UNREGI		= 2;
	
	//VALUES OF REQUEST CODES
	public static final int REQ_CODE_MANUALLY_ADD		= 100;
	public static final int REQ_CODE_ADD_MEMBER			= 101;
	public static final int REQ_CODE_INVITATION_HOME	= 102;
	public static final int REQ_CODE_CHANGE_MYINFO		= 103;
	public static final int REQ_CODE_CHANGE_PHONE		= 104;
	public static final int REQ_CODE_CONFIG				= 105;
	public static final int REQ_CODE_CONFIG_DETAIL		= 106;
	public static final int REQ_CODE_FRIEND_SEARCH		= 107;
	public static final int REQ_CODE_SEND_SMS			= 108;
	public static final int REQ_CODE_FRIENDSHIP_REQUEST = 109;
	public static final int REQ_CODE_FRIENDSHIP_ACCEPT	= 110;
	public static final int REQ_CODE_SERVICE_NOTICE		= 111;
	public static final int REQ_CODE_MINI_BROWSER		= 112;
	public static final int REQ_CODE_BIRTHDAY_FRIENDS 	= 113;
	public static final int REQ_CODE_DECLARATION	 	= 114;
	public static final int REQ_CODE_AUTH_EMAIL		 	= 115;

	public static final int REQ_CODE_PHOTO_VIEWER 		= 202;
	public static final int REQ_CODE_POST_VIEW	 		= 203;
	public static final int REQ_CODE_PUSH_START 		= 205;
	public static final int REQ_CODE_CROP_PHOTO 		= 206;
	public static final int REQ_CODE_VOTE 				= 207;
	public static final int REQ_CODE_METOO_LIST			= 208;
	public static final int REQ_CODE_GALLERY			= 209;
	public static final int REQ_CODE_NOTE				= 210;
	public static final int REQ_CODE_NOTICENTER			= 211;
	public static final int REQ_CODE_LOGIN				= 212;
	public static final int REQ_CODE_JOIN				= 213;
	public static final int REQ_CODE_MODIFY_TAG			= 214;
	public static final int REQ_CODE_TALK_ROOM			= 215;
	public static final int REQ_CODE_NEIGHBOR_POI_DETAIL= 216;

	
	public static final int REQ_CODE_ALBUM_UPLOAD 		= 231;
	public static final int REQ_CODE_PHOTO_UPLOAD 		= 232;
	public static final int REQ_CODE_SCHEDULE_CRATE     = 400;
	public static final int REQ_CODE_SCHEDULE_UPDATE    = 401;
	public static final int REQ_CODE_CHAT_CLOSE 		= 500;
	public static final int REQ_CODE_PUSH_BAND			= 511;
	public static final int REQ_CODE_PUSH_MODE			= 512;
	
	//VALUES OF RESULT CODE
	public static final int RES_CODE_POST_DELETE 		= 1000;
	public static final int RES_CODE_POST_WRITE 		= 1001;
	public static final int RES_CODE_COMMENT_UPDATE 	= 1002;
	public static final int RES_CODE_POST_NOTICE_CHANGED		= 1003;
	public static final int RES_CODE_REDIRECT_TARGET_SETTING	= 1020;
	public static final int RES_CODE_REDIRECT_TARGET_BANDLIST	= 1021;
	public static final int RES_CODE_BAND_UNREGIST				= 1022;
	public static final int RES_CODE_POST_BAND_CHANGE			= 1023;
	public static final int RES_CODE_UPDATE_LIST				= 1024;

	//커버, 프로필 사진 수정
	public static final int REQUEST_ALBUM_UPLOAD_CODE = 21;
	public static final int REQUEST_PHOTO_UPLOAD_CODE = 22;
	public static final int REQUEST_PROFILE_EDIT_CODE = 23;
	public static final int REQUEST_CROP_PHOTO_CODE = 24;
	
	public static final String REQUEST_SHOW_ORIGIN_TUMB = "show_origin_thumb";
	public static final int REQ_CODE_REQUEST_FRIEND		= 25;
	
	//BROADCAST
	public static final String BROADCAST_POSTING_COMPLETED = "com.nhn.android.me2day.posting.COMPLETED";
	public static final String BROADCAST_COMMENT_UPDATED = "com.nhn.android.me2day.posting.COMPLETED";
	public static final String BROADCAST_FINISH_ACTIVITY = "com.nhn.android.me2day.posting.FINISH_ACTIVITY";
	public static final String BROADCAST_MAP_INIT_COMPLETED = "com.nhn.android.me2day.menu.neighbor.map.init.COMPLETED";
	public static final String BROADCAST_CHAT_BADGE_UPDATE = "com.nhn.android.me2day.chat.badge.UPDATE";

	// CACHE KEY
	public static final String KEY_POSTING_NOTIFICATIONS = "posting_notifications";	



	///// DEFINITION OF INTENT PARAMETER //////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////

	public static final String PARAM_ID 				= "id";
	public static final String PARAM_BAND_COLOR			= "band_color";
	public static final String PARAM_BAND_ID			= "band_id";
	public static final String PARAM_BAND_NAME			= "band_name";
	public static final String PARAM_BAND_OBJ			= "band_obj";
	public static final String PARAM_BAND_LIST			= "band_list";
	public static final String PARAM_BAND_SELECT_LIST	= "band_select_list";
	public static final String PARAM_BAND_SET_TYPE		= "band_set_type";
	public static final String PARAM_BIRTHDAY			= "birthday";
	public static final String PARAM_COMMENT_COUNT		= "comment_count";
	public static final String PARAM_DEVICE_OBJ			= "device_obj";
	public static final String PARAM_CROP_IMG_FILE		= "crop_img_file";
	public static final String PARAM_DIALOG_MESSAGE		= "dialogMessage";
	public static final String PARAM_FB_GROUP_ID		= "fb_group_id";
	public static final String PARAM_FB_GROUP_NAME		= "fb_group_name";
	public static final String PARAM_FLAG_SHOW_KAFB		= "flag_show_kafb";
	public static final String PARAM_FROM_WHRE			= "from_where";
	public static final String PARAM_INVALIDATE_BAND_OBJ= "invalidate_band_obj";
	public static final String PARAM_INVITER_ID			= "inviter_id";
	public static final String PARAM_INVITATION_HINT	= "invitation_hint";
	public static final String PARAM_INVITATION_MODE	= "invitation_mode";
	public static final String PARAM_INVITATION_OBJ		= "invitation_obj";
	public static final String PARAM_INVITEE_OBJ		= "invitee_obj";
	public static final String PARAM_INVITE_TYPE 		= "invite_type";	
	public static final String PARAM_MAILET_LIST 		= "mailet_list";
	public static final String PARAM_MEMBER_COUNT 		= "member_count";
	public static final String PARAM_MEMBER_LIST 		= "member_list";
	public static final String PARAM_MENU_TYPE	 		= "menu_type";
	public static final String PARAM_MY_MENU_TYPE	 	= "my_menu_type";
	public static final String PARAM_NEED_FORCE			= "need_force";
	public static final String PARAM_NEED_ANIMATION		= "need_animation";
	public static final String PARAM_PHONE_CHANGE		= "phone_change";
	public static final String PARAM_PHONE_NUMBER		= "phone_number";
	public static final String PARAM_PHOTO_COUNT		= "photo_count";
	public static final String PARAM_POST_OBJ			= "post_obj";
	public static final String PARAM_POST_ID			= "post_id";
	public static final String PARAM_POSTID				= "postId";
	public static final String PARAM_POST_MESSAGE		= "message";
	public static final String PARAM_NICKNAME			= "nickname";
	public static final String PARAM_POSITION 			= "position";
	public static final String PARAM_POSITION_FROM_BOARD= "position_board";
	public static final String PARAM_POSTING_DATA		= "postingData";
	public static final String PARAM_POSTVIEW_MOVECOMMENT= "postview_movecomment";
	public static final String PARAM_PROFILE_OBJ		= "profile_obj";
	public static final String PARAM_PROFILE			= "profile";
	public static final String PARAM_PUSH_PHOTO_NO		= "push_photo_no";
	public static final String PARAM_PUSH_TYPE			= "pushType";
	public static final String PARAM_PUSH_PAYLOAD		= "pushPayload";
	public static final String PARAM_PUSH_TOAST_DATA	= "pushToastData";
	public static final String PARAM_REDIRECT_SETTING	= "redirect_setting";
	public static final String PARAM_ROOM_ID 			= "room_id";
	public static final String PARAM_ROOM_KIND 			= "rooom_kind";	 //방종류, "mailet":일대일채팅, "group":그룹채팅
	public static final String PARAM_ROOM_NAME 			= "room_name";
	public static final String PARAM_ROOM_SET_MEMBERS   = "room_set_members";
	public static final String PARAM_ROOM_USER_TYPE 	= "room_user_type"; // user_type
	public static final String PARAM_SCHEDULE_OBJ		= "schedule_obj";
	public static final String PARAM_SEARCHED_FRIEND 	= "searched_friend";	
	public static final String PARAM_SMS_ID				= "sms_id";
	public static final String PARAM_URL 				= "url";
	public static final String PARAM_SHORT_URL			= "short_url";
	public static final String PARAM_USER_NAME			= "user_name";
	public static final String PARAM_USER_FACE			= "user_face";
	public static final String PARAM_WRITE_PHOTOUPLOAD	= "write_photoupload";
	public static final String PARAM_VOTEPARAM_OBJ		= "vote_param";
	public static final String PARAM_SEARCH_DOMAIN 		= "search_domain";
	public static final String PARAM_SEARCH_KEYWORD 	= "search_keyword";
	public static final String PARAM_PAGE 				= "page";
	public static final String PARAM_ITEMS_PER_PAGE	 	= "items_per_page";
	public static final String PARAM_METOO_COUNT	 	= "metoo_count";
	public static final String PARAM_ORDER			 	= "order";
	public static final String PARAM_ORDER_ASC		 	= "asc";
	public static final String PARAM_ORDER_DESC		 	= "desc";
	public static final String PARAM_BODY			 	= "body";
	public static final String PARAM_PINGBACK		 	= "pingback";
	public static final String PARAM_POST_POSITION		= "post_position";
	public static final String PARAM_STICKER_CODE	 	= "sticker_code";
	public static final String PARAM_RESOLUTION_TYPE	= "resolution_type";
	
	public static final String PARAM_VISIT_FRIEND		= "visit_friend";
	
	public static final String PARAM_PEOPLE_MENU_TYPE	= "people_menu_type";
	
	public static final String PARAM_FRIEND_LIST 		= "friend_list";
	public static final String PARAM_FROM_BAND_LIST 	= "from_band_list";	//true:미투밴드에서 들어온 글일경우, false:미투밴드 이외의 메뉴에서 들어온 글일 경우
	public static final String PARAM_FROM_MAIN 			= "from_main";
	public static final String PARAM_FROM_PROFILE 		= "from_profile_list";
	public static final String PARAM_FROM_POST_LIST		= "from_post_list";
	public static final String PARAM_PHOTO_LIST			= "photo_list";
	
	public static final String PARAM_DESCRIPTION 		= "description";
	public static final String PARAM_KEY 				= "key";
	public static final String PARAM_PROVIDER 			= "provider";
	public static final String PARAM_CONTENT_TYPE 		= "content_type";
	
	public static final String PARAM_SPOT_ID 			= "spot_id";
	public static final String PARAM_ADDRESS 			= "address";
	
	public static final String PARAM_LOCALE 			= "locale";
	public static final String PARAM_VIEW_TYPE 			= "paramViewType";
	public static final String VIEW_TYPE_LOGIN 			= "viewTypeLogin";
	public static final String VIEW_TYPE_JOIN 			= "viewTypeJoin";
	public static final String PARAM_GLOBAL_REGISTER 	= "globalRegister";
	public static final String PARAM_PROCESS_REGISTER 	= "processRegister";
	public static final String PARAM_DOMAIN				= "domain";
	public static final String PARAM_IDENTIFIER			= "identifier";
	
	public static final String PARAM_COMMENT_ID 		= "comment_id";
	public static final String PARAM_COMMENTID 			= "commentId";
	public static final String PARAM_DEVICE_TYPE 		= "device_type";
	public static final String PARAM_DEVICE_ID 			= "device_id";
	public static final String PARAM_DEVICE_TOKEN 		= "device_token";
	public static final String PARAM_AUTHOR_USER_ID 	= "author_user_id";
	public static final String PARAM_IS_ENABLE 			= "is_enable";
	public static final String PARAM_POST 				= "post";
	public static final String PARAM_TAGS 				= "tags";
	public static final String PARAM_RESULT_TAG 		= "result_tag";
	public static final String PARAM_TEXT 				= "text";
	public static final String PARAM_DELETE_POST_ID 	= "delete_post_id";
	public static final String PARAM_IS_ALARM_ENTER 	= "is_alarm_enter";
	public static final String PARAM_VISIBLE			= "visible";
	public static final String PARAM_GONE				= "gone";
	public static final String PARAM_NEED_POST			= "needPost";
	public static final String PARAM_MEDIA_PATH			= "mediaPath";
	public static final String PARAM_MODIFY_TAG			= "modifyTag";

	public static final String PARAM_RESULT_CODE		= "result_code";
	public static final String PARAM_RESULT_MESSAGE 	= "result_message";
	public static final String PARAM_RESULT_DESCRIPTION = "result_description";
	
	public static final String PARAM_IS_ENABLE_RECOMMEND = "is_enable_recommend";
	public static final String PARAM_IS_CHANGEABLE 		= "is_changable";
	public static final String PARAM_IS_SUCCESS 		= "is_success";
	public static final String PARAM_MESSAGE 			= "message";
	public static final String PARAM_SCOPE 				= "scope";
	public static final String PARAM_VALUE 				= "value";
	public static final String PARAM_USER				= "user";
	public static final String PARAM_UID				= "uid";
	public static final String PARAM_MEMO				= "memo";
	public static final String PARAM_IS_NEW				= "isNew";
	public static final String PARAM_IS_ABLE			= "isAble";
	
	//친구 신청
	
	public static final String PARAM_ADD_SMS_FRIEND		= "add_sms_friend";
	public static final String PARAM_GROUP_ID			= "group_id";
	public static final String PARAM_FRIENDSHIP			= "friendship";
	public static final String PARAM_IS_PIN_USER		= "is_pin_user";
	public static final String PARAM_IS_BLOCK_USER		= "is_block_user";
			
			
	public static final String PARAM_POI_OBJ 			= "poi_obj";
	public static final String PARAM_ORIG_REQ 			= "orig_req";
	public static final String ORIG_REQ_VAL_POST_VIEW 	= "postView";
	public static final String ORIG_REQ_VAL_POI_SPOT_VIEW = "poiSpotView";
	public static final String PARAM_POI_LATITUDE		= "latitude";
	public static final String PARAM_POI_LONGITUDE		= "longitude";
	
	//v3.0.0 사람들은
	public static final String PARAM_TARGET_ID			= "target_id";
	public static final String PARAM_DEFAULT_PAGE_ID	= "default_page_id";
	public static final String PARAM_TITLE				= "title";
	public static final String PARAM_EXIST_KEYWORD		= "exist_keyword";
	
	//v3.0.0 글쓰기
	public static final String PARAM_EDIT_POST_MESSAGE = "param_edit_post_message";
	public static final String PARAM_EDIT_TAG_MESSAGE = "param_edit_tag_message";
	public static final String PARAM_SET_POSTING_DATA = "param_set_posting_data";
	public static final String PARAM_WRITE_MODE = "param_write_mode";
	public static final String PARAM_LINK_ADDRESS = "param_link_address";
	public static final String PARAM_ATTACH_LINK = "attach_link";
	public static final String PARAM_ATTACH_PHOTO = "attach_photo";
	public static final String PARAM_SELECTED_COUNT = "param_selected_count";
	public static final String PARAM_SPOT_OBJ = "spot_obj";
	public static final String PARAM_EMOTICON_INDEX = "param_emoticon_index";
	
	public static final String PARAM_REQ_CODE = "param_request_code";
	public static final String PARAM_RES_CODE = "param_result_code";
	
	public static final String VALUE_WHERE_POSTVIEW = "wherePostView";
	public static final String VALUE_WHERE_PROFILEVIEW = "wherepProfileView";
	
	public static final int REQ_CODE_WRITE_MODE = 598;
	public static final int REQ_CODE_WRITE_POSTING = 599;
	public static final int REQ_CODE_PHOTO_ALBUM = 600;
	public static final int REQ_CODE_PHOTO_CAMERA = 601;
	public static final int REQ_CODE_PHOTO_EDIT = 602;
	public static final int REQ_CODE_LOCATION = 603;
	public static final int REQ_CODE_CONTENT = 604;
	public static final int REQ_CODE_VOICE_MEMO = 608;
	public static final int REQ_CODE_LINK = 609;
	public static final int REQ_CODE_REGISTER_SPOT = 610;
	public static final int REQ_CODE_CATEGORY = 611;
	public static final int REQ_CODE_CATEGORY_MENU = 612;
	public static final int REQ_CODE_SETTING_CATEGORY = 613;
	public static final int REQ_CODE_ANOTHER_PHOTO_ALBUM = 614;
	public static final int REQ_CODE_ANOTHER_VIDEO_ALBUM = 615;
	
	public static final int RES_CODE_COMPLETE_UPLOAD_POSTING = 999;
	public static final int RES_CODE_WRITE_POSTING = 1000;
	public static final int RES_CODE_ATTACH_PHOTO = 1001;
	public static final int RES_CODE_ATTACH_VIDEO = 1002;
	public static final int RES_CODE_SET_CURRENT_LOCATION = 1003;
	public static final int RES_CODE_SET_SPOT = 1004;
	public static final int RES_CODE_SET_CONTENT = 1005;
	public static final int RES_CODE_ADD_LINK = 1006;
	public static final int RES_CODE_SET_CATEGORY = 1007;
	
	public static final String MULTIMEDIA_TYPE_MOVIE = "me2movie";
	public static final String MULTIMEDIA_TYPE_BOOK = "me2book";
	public static final String MULTIMEDIA_TYPE_MUSIC = "music_album";
	public static final String MULTIMEDIA_TYPE_PHOTO = "me2photo";
	public static final String MULTIMEDIA_TYPE_SPOT = "me2spot";
	public static final String MULTIMEDIA_TYPE_PHOTOTHUMB = "me2photoThumb";
	public static final String MULTIMEDIA_TYPE_VIDEO = "me2video";
	public static final String MULTIMEDIA_TYPE_CAST = "me2cast";
	
	// v3.0 로그인 관련 
	public static final String PARAM_LOGIN_SUCCESS = "login_success";
	
	// 글상세
	public static final int MEDIA_TYPE_NONE  	= 0;
	public static final int MEDIA_TYPE_PHOTO 	= 1;
	public static final int MEDIA_TYPE_VIDEO 	= 2;
	public static final int MEDIA_TYPE_CAST  	= 3;
	public static final int MEDIA_TYPE_MUSIC 	= 4;
	public static final int MEDIA_TYPE_MOVIE 	= 5;
	public static final int MEDIA_TYPE_BOOK 	= 6;

	public static final int CAST_STATE_STOP 	= 0;
	public static final int CAST_STATE_LOADING 	= 1;
	public static final int CAST_STATE_PLAYING 	= 2;
	
	public static final String	TAG_POST_FAIL								= "tag_post_fail";
	public static final String	PARAM_PHOTO_URL								= "photo_url";
	public static final String	PARAM_SHOW_SAVE_BUTTON						= "show_save_button";
	
	public static final String	PARAM_IS_SHOW_KEYBOARD						= "is_show_keyboard";			// 키보드를 올린다.
	public static final String	PARAM_IS_MOVE_LAST_COMMENT					= "is_move_last_comment";		// 마지막 댓글 위치로 이동 시킨다.
	public static final String	PARAM_IS_SET_AUTO_RECALL_ID					= "is_set_auto_recall_id";		// 자동소환 ID를 넣어준다.
	public static final String	PARAM_FROM_MY_STORY							= "from_my_story";
	
	public static final int TYPE_DECLARATION_POST			= 1;
	public static final int TYPE_DECLARATION_COMMENT		= 2;
	
	
	//친구 신청 관련
	public static final String PARAM_FRIEND_REQUEST = "friend_request";
	public static final String PARAM_VALUE_OFF = "off";
	public static final String PARAM_VALUE_ON = "on";
	public static final String PARAM_SCOPE_FRIEND = "friend";
	public static final String PARAM_SCOPE_FRIEND_REQUEST = "friend_request";
	public static final String PARAM_SCOPE_FRIEND_SMS = "friend_sms";
	public static final String PARAM_FRIENDSHIP_REQUEST_OBJECT = "friendship_request";
	public static final String PARAM_TITLE_COLOR_BLACK = "title_color_black";
	

	//프로필
	public static final String PARAM_USER_ID = "user_id";
	public static final String PARAM_FACE = "user_face";
	
	public static final int REQ_CODE_BLOCK_POST_ONEDAY = 300;
	public static final int REQ_CODE_BLOCK_POST_FOREVER = 301;
	public static final int REQ_CODE_BLOCK_CHAT = 302;
	public static final int REQ_CODE_UN_FRIEND = 303;
	public static final int REQ_PROFILE_EDIT_CODE = 304;
	public static final int REQ_FRIEND_CODE = 305;
	public static final int REQ_PROFILE_DETAIL = 306;
	
	public static final int RES_CODE_UPDATE_PERSON_OBJ = 700;

	public static final String PARAM_IMAGE_PATH = "image_path";
	public static final String PARAM_IMAGE_CROP_TARGET_PATH = "image_crop_target_path";
	public static final String PARAM_IMAGE_CROP_OUTPUT_X = "image_crop_output_x";
	public static final String PARAM_IMAGE_CROP_OUTPUT_Y = "image_crop_output_y";
	
	public static final String	PARAM_COUNT_PHOTO			= "myPhoto";
	public static final String	PARAM_COUNT_CONTENT			= "myContent";
	public static final String	PARAM_COUNT_METOO			= "myMetoo";
	public static final String	PARAM_CURRENT_POSITION			= "current_position";
	
	// 초대 관련
	public static final String	PARAM_INVITATION_INVITOR			= "invitor";
	public static final String	PARAM_INVITATION_TYPE				= "invitationType";
	public static final String	PARAM_INVITATION_CONFIRM			= "invitationConfirm";
	public static final String	PARAM_INVITATION_TOKEN				= "created_token";
	public static final String	PARAM_INVITATION_LOGIN_USER_ID		= "login_user_id";
	public static final String	PARAM_INVITATION_NICKNAME			= "nickname";
	public static final String	PARAM_INVITATION_USER_ID			= "user_id";
	public static final String	PARAM_INVITATION_NAME				= "name";
	public static final String	PARAM_INVITATION_BIRTHDAY			= "birthday";
	public static final String	PARAM_INVITATION_PROFILE_URL		= "profile_url";
	public static final String	PARAM_INVITATION_IS_FRIEND			= "is_friend";
	public static final String	PARAM_INVITATION_CREATED_AT			= "created_at";
	public static final String	VALUE_INVITATION_KAKAOTALK			= "kakaotalk";
	public static final String	PARAM_INVITE_TYPE_KAKAOTALK			= "kakaotalk";
	public static final String	PARAM_INVITE_TYPE_SMS				= "sms";

	public static final String	PARAM_PERSON_ID						= "person_id";
	public static final String	PARAM_PERSON_LIST					= "person_list";
	public static final String	PARAM_PERSON_NICKNAME				= "person_nickname";
	public static final String	PARAM_PERSON_OBJ					= "person_obj";
	public static final String	PARAM_SHOW_INVITE_MESSAGE			= "showInviteMessage";
	
	// 로그인/회원가입 관련 
	public static final String	PARAM_REG_AUTO_LOGIN				= "auto_login";
	public static final String	PARAM_AUTO_LOGIN_KIND				= "login_kind";
	
	public static final String	VALUE_AUTH_TYPE_NAVER				= "naver";
	public static final String	VALUE_AUTH_TYPE_ME2DAY				= "me2day";
	public static final String	VALUE_AUTH_TYPE_SIMPLE				= "simple";
	public static final String	VALUE_AUTH_TYPE_OTP					= "otp";
	
	//카테고리 관련
	public static final String PARAM_CATEGORY_NO					= "category_no";
	public static final String PARAM_CATEGORY_FROM_WRITING			= "category_from_writing";	
	public static final String PARAM_CATEGORY_ICON_URL				= "category_icon_url";
	public static final String PARAM_CATEGORY_DESCRIPTION			= "category_description";
	public static final String PARAM_CATEGORY_FROM_SETTING			= "category_from_setting";	
}