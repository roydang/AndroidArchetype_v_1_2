package m2.android.archetype.api;

import java.net.URLEncoder;

import m2.android.archetype.base.M3Application;
import m2.android.archetype.config.AppBuildCheckFlag;
import m2.android.archetype.sharedpref.UserSharedPrefModel;
import m2.android.archetype.util.Logger;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

public class BaseProtocolEnum {
	
	private static Logger logger = Logger.getLogger(BaseProtocolEnum.class);

	public static final int API_MODE_REAL 		= 0;
	public static final int API_MODE_STAGING 	= 1;
	public static final int API_MODE_MOCKUP 	= 2;
	public static final int API_MODE_INDEPENDENT= 3;
	
	public static final int ME2_DOMAIN			= 11;
	public static final int ME2_API_DOMAIN		= 12;
	public static final int ME2_UP_DOMAIN		= 13;
	
	public static final String URL_PREFIX_HTTP  = "http://";
	public static final String URL_PREFIX_HTTPS = "https://";
	
	public static final String HOST_NAME 		= "me2day.net";
	public static final String API_HOST_NAME 	= "api.me2day.net";
	public static final String UP_HOST_NAME = "up.me2day.net";

	
	public static final String STAGING_ME2DAY_API_HOST_NAME = "61.247.198.243";	//api.me2day.net 도메인의 Staging 장비
	public static final String STAGING_ME2DAY_HOST_NAME = "61.247.207.199"; //me2day.net 도메인의 Staging 장비
	
	public static final String NAVER_STATIC_MAP_URL 	= "http://apis.naver.com/me2dayMobileApp/map/staticMap.xml";
	public static final String GOOGLE_STATIC_MAP_URL 	= "http://maps.google.com/maps/api/staticmap";


	public static final String nClickAlphaUrlPrefix = "http://alpha-cc.naver.com/cc?nsc=androidapp.me2day3&a=";
	public static final String nClickRealUrlPrefix = "http://cc.naver.com/cc?nsc=androidapp.me2day3&a=";
	public static final String nClickUrlPostfix = "&m=0&u=about%3Ablank";
	
	public final static String LCS_DOMAIN = "lcs.naver.com";
//	private final static String LCS_DOMAIN = "alpha-lcs.naver.com";
	public final static String LCS_URL_PREFIX = "http://" + LCS_DOMAIN + "/m?u=";
	
	public static final String NOTICE_SERVER_REAL 		= "http://api.appnotice.naver.com/notice/list.nhn?os=android";
	public static final String NOTICE_SERVER_REAL_TEST 	= "http://api.appnotice.naver.com/notice/list.nhn?os=android&br=test";
	public static final String NOTICE_SERVER_REAL_ALPHA = "http://alpha.api.appnotice.naver.com/notice/list.nhn?os=android";
	
	
	//http://api.appnotice.naver.com/notice/list.nhn?os=android&app=me2day&ver=3.0&appVer=84 완성URL sample
	public static final String NOTICE_SERVER_COUNT_REAL 		= "http://api.appnotice.naver.com/notice/count.nhn?os=android";
	public static final String NOTICE_SERVER_COUNT_REAL_TEST 	= "http://api.appnotice.naver.com/notice/count.nhn?os=android&br=test";
	public static final String NOTICE_SERVER_COUNT_REAL_ALPHA 	= "http://alpha.api.appnotice.naver.com/notice/count.nhn?os=android";
	
	//글로벌 회원가입
	public static final String GLOBAL_LOGIN_SEARCH_ID 		= "https://nid.naver.com/mobile/user/global/idInquiry.nhn";
	public static final String GLOBAL_LOGIN_SEARCH_PASSWORD = "https://nid.naver.com/mobile/user/global/pwInquiry.nhn";
	public static final String GLOBAL_LOGIN_JOIN 			= "https://nid.naver.com/user/mobile_join.nhn";
	public static final String GLOBAL_LOGIN_INFO 			= "https://nid.naver.com/mobile/user/global/accountInfo.nhn";
	
	
	 enum Me2dayURL {

		 ADD_STAT 							("/api/add_stat.json",							ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 STREAM 							("/api/stream.json",							ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 UPLOAD_PHOTO						("/api/upload_photo/%s.json",					ME2_UP_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 UPLOAD_VIDEO						("/api/upload_video/%s.json",					ME2_UP_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 CREATE_POST						("/api/create_post/%s.json",					ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 UPDATE_OZ_USER_SETTING				("/api/update_oz_user_setting.xml",				ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_OZ_USER_SETTING_INFO			("/api/get_oz_user_setting_info.xml",			ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),		

		 GET_START_TOKEN					("/api/get_start_token.json",					ME2_DOMAIN,			URL_PREFIX_HTTPS,	API_MODE_REAL),
		 AUTH_LOGIN_START					("/api/auth_login_start.xml",					ME2_DOMAIN,			URL_PREFIX_HTTPS,	API_MODE_REAL),
		 AUTH_LOGIN_GET_TOKEN				("/api/auth_login_get_token.xml",				ME2_DOMAIN,			URL_PREFIX_HTTPS,	API_MODE_REAL),
		 
		 REGISTER_USER						("/api/register_user.json",						ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_AUTOCOMPLETE_FRIEND_LIST 		("/api/get_friends_for_autocomplete.json", 		ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_PERSON							("/api/get_person/%s.json",						ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 REGISTER_USER_NAVER				("/api/register_user_naver.json",				ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_COMMENTS						("/api/get_comments.json",						ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_THEME							("/api/get_themes.json", 						ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),	
		 GET_SPOT_CATEGORIES				("/api/get_spot_categories.json", 				ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 SEARCH_SPOTS						("/api/search_spots.json",						ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_BANDS							("/api/get_bands.json",							ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 SIMPLE_LOGIN						("/api/simple_login.xml",						ME2_API_DOMAIN,		URL_PREFIX_HTTPS,	API_MODE_REAL),
		 CHECK_NAVER_USER					("/api/check_naver_user.json",					ME2_DOMAIN,			URL_PREFIX_HTTPS,	API_MODE_REAL),
		 CHECK_ME2DAY_USER					("/api/get_userinfo_with_naver_id.json",		ME2_DOMAIN,			URL_PREFIX_HTTPS,	API_MODE_REAL),
		 CHECK_AUTH_EMAIL					("/api/check_auth_email.json",					ME2_DOMAIN,			URL_PREFIX_HTTPS,	API_MODE_REAL),
		 RESEND_AUTH_EMAIL					("/api/resend_auth_email.json",					ME2_DOMAIN,			URL_PREFIX_HTTPS,	API_MODE_REAL),
		 DO_METOO							("/api/metoo.json",								ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 CANCEL_METOO						("/api/cancel_metoo.json",						ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_FRIENDGROUP					("/api/get_friendgroup.json",					ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_FRIENDPUBLICGROUP				("/api/get_friendgroup/%s.json",				ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_POST							("/api/get_posts/%s.json",						ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_POST_SIMPLE					("/api/get_post/%s.json",						ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_KEYWORDS						("/api/get_keywords.json",						ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 ADD_KEYWORDS						("/api/add_keywords.json",						ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 REMOVE_KEYWORDS					("/api/remove_keywords.json",					ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_MAIN_KEYWORDS					("/api/get_main_keywords.json",					ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_CONTENT_POST_BY_ME				("/api/get_content_posts_by_me/%s.json",		ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 REDIRECT_AFTER_AUTH				("/api/redirect_after_auth.json",				ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),

		 //프로필
		 SET_PROFILE						("/api/set_profile.json",						ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 SET_UNBLOCK						("/api/unblock_user.json",						ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 SET_BLOCK							("/api/block_user.json",						ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 UPLOAD_FACE						("/api/m2/upload_face.json",					ME2_UP_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 
		 //친구들은						
		 GET_SETTINGS						("/api/get_settings.json",						ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_CELLPHONE_COUNTRY				("/api/get_cellphone_country.json",				ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 REQ_CELLPHONE_AUTH					("/api/request_cellphone_auth.json",			ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 AUTH_CELLPHONE						("/api/auth_cellphone.json",					ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 SEND_INVITAION_BY_PHONE			("/api/send_invitation_by_phone.json",			ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 SEND_INVITAION_SMS					("/api/external_invitation/send_invitation_sms.json", ME2_DOMAIN,	URL_PREFIX_HTTP,	API_MODE_REAL),
		 
		 HANDSHAKE							("/api/handshake.json",							ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_HANDSHAKE						("/api/get_handshakes.json",					ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 
		 // 글상세
		 GET_POST_DETAILS					("/api/get_post_details/%s",					ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_CONTENT						("/api/get_content.json",						ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 CHECK_METOOABLE					("/api/check_metooable.json",					ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_METOOS							("/api/get_metoos.json",						ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 UPDATE_TAGS						("/api/update_tags.json",						ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 DELETE_POST						("/api/delete_post.json",						ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 DELETE_COMMENT						("/api/delete_comment.json",					ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_NOTI_POST						("/api/get_noti_post.json",						ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 SET_NOTI_POST						("/api/set_noti_post.json",						ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 PIN								("/api/pin.json",								ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 UNPIN								("/api/unpin.json",								ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_PINS							("/api/get_pins.json",							ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 SEARCH_PERSON						("/api/search_person.json",						ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 FRIENDSHIP							("/api/friendship.json",						ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_FRIENDSHIP_REQUEST				("/api/get_friendship_requests/%s.json",		ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 SET_FRIENDGROUP					("/api/set_friendgroup.json",					ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 CREATE_COMMENT						("/api/create_comment.json",					ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_FRIENDS						("/api/get_friends/%s.json",					ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_FRIENDGROUP_OF					("/api/get_friendgroup_of_friend.json",			ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 TODAY								("/api/today.json",								ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_LATEST_APP_VERSION				("/api/get_latest_app_version.json",			ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 REGISTER_USER_SPOT					("/api/register_user_spot.json",				ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_NEARBY_SPOT					("/api/get_nearby_spots.json",					ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_NEARBY_POSTS					("/api/get_nearby_posts.json",					ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_NEARBY_HOT_SPOTS				("/api/get_nearby_hot_spots.json",				ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_NEARBY_PHOTOS					("/api/get_nearby_photos.json",					ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_TODAY_CONTENTS					("/api/get_today_contents.json",				ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_LIVE_CELEBRITIES				("/api/get_live_celebrities.json",				ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_BEST_CONTENTS					("/api/get_best_contents.json",					ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_TODAY_EVENT_CONTENTS			("/api/get_today_event_contents.json",			ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 HOT_SPOTS							("/api/hot_spots.json",							ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_POSTS_BY_CONTENT				("/api/get_posts_by_content.json",				ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 APPLY_TO_CHAT						("/api/me2live_chat/apply_to_chat.json",		ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 SEARCH								("/api/search.json",							ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_SPOT							("/api/get_spot.json",							ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 GENERATE_SHORT_URL					("/api/generate_short_url.json",				ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 
		 //똑똑
		 VISIT_LOG_SET						("/api/visit_log_set.json",						ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 VISIT_LOG_ADD						("/api/visit_log_add.json",						ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 VISIT_LOG_LIST						("/api/visit_log_list.json",					ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 VISIT_LOG_NEW						("/api/visit_log_new.json",						ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 VISIT_LOG_ABLE						("/api/visit_log_able.json",					ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 
		 // 초대
		 CREATE_INVITATION_TOKEN			("/api/external_invitation/create_invitation_token.json",	ME2_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_INVITATION_INFO				("/api/external_invitation/get_invitation_info.json",		ME2_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 ACCEPT_FRIENDSHIP					("/api/external_invitation/accept_friendship.json",			ME2_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 ADD_RECOMMENT_FRIENDSHIP			("/api/external_invitation/add_recommend_friendship.json",	ME2_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 REJECT_FRIEND_REQUEST				("/api/external_invitation/reject_friend_request.json",		ME2_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 
		 // 알림센터
		 NOTICENTER_NEW						("/api/noticenter_new.json",					ME2_API_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 NOTICENTER_METOO					("/api/noticenter_metoo.json",					ME2_API_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 NOTICENTER_DELETE					("/api/noticenter_delete.json",					ME2_API_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 NOTICENTER_IS_NEW					("/api/noticenter_is_new.json",					ME2_API_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 NOTICENTER_BIRTHDAY_FRIENDS		("/api/noticenter_birthday_friends.json",		ME2_API_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 ACCEPT_FRIENDSHIP_REQUEST			("/api/accept_friendship_request.json",			ME2_API_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 MEMBERSHIP							("/api/membership.json",						ME2_API_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 NOTICETNER_GROUP_DETAILS			("/api/noticenter_group_details.json",			ME2_API_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_BAND							("/api/get_band.json",							ME2_API_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 
		 // Push & 설정
		 SET_DEVICE_TOKEN					("/api/set_device_token.json",					ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_BLOG_POSTS						("/api/get_blog_posts.json",					ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),			 
		 GET_STREAM_SCOPE					("/api/get_stream_scope.json",					ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 SET_STREAM_SCOPE					("/api/set_stream_scope.json",					ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 REGISTER_NOTI						("/api/register_noti.json",						ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 SET_NOTI							("/api/set_noti.json",							ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_NOTI							("/api/get_noti.json",							ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 
		 // 채팅
		 GET_ROOMS							("/api/get_rooms.json",							ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 ENTER_ROOM							("/api/enter_room.json",						ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_ROOM_MEMBERS					("/api/get_room_members.json",					ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 GET_MESSAGES						("/api/get_messages.json",						ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 CREATE_ROOM						("/api/create_room.json",						ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 
		 // 스티커 		 
		 STICKER_GROUPS						("/api/sticker_groups.json",					ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 
		 SET_TIMEZONE						("/api/set_user_time_zone.json",				ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),
		 COMPARE_TIMEZONE					("/api/get_user_time_zone.json",				ME2_DOMAIN,			URL_PREFIX_HTTP,	API_MODE_REAL),			
		 
		 // 글카테고리 목록 		 
		 GET_LIST							("/api/post_group/list.json",					ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 TODAY_CATEGORY_POST				("/api/today_category_posts.json",				ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 ADD_POST_CATEGORY					("/api/post_category/add.json",					ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 CHANGE_POST_CATEGORY				("/api/post_category/change_post_category.json",ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 POST_CATEGORY						("/api/post_category/list.json",				ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 DELETE_CATEGORY					("/api/post_category/delete.json",				ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL),
		 MODIFY_POST_CATEGORY				("/api/post_category/modify.json",				ME2_API_DOMAIN,		URL_PREFIX_HTTP,	API_MODE_REAL);
		 
		 
		 private String url;
		 private int 	domainType;
		 private String urlPrefix;
		 private int 	apiModeType;
		 
		 private Me2dayURL(String url, int domainType, String urlPrefix, int apiModeType) {
			 this.url = url;
			 this.domainType = domainType;
			 this.urlPrefix = urlPrefix;
			 
			 // 스테이징 적용 여부를 독립적으로 적용 하고자 할때
			 if (AppBuildCheckFlag.API_MODE == API_MODE_INDEPENDENT) {
				 this.apiModeType = apiModeType;
			 } else {
				 this.apiModeType = AppBuildCheckFlag.API_MODE;
			 }
		 }
		 
		 public StringBuilder getURL() {
			 
			StringBuilder sb = new StringBuilder();
			sb.append(this.urlPrefix);
			
			if (apiModeType == API_MODE_STAGING) {	
				
				if (domainType == ME2_API_DOMAIN) {
					sb.append(STAGING_ME2DAY_API_HOST_NAME).append(this.url).append("?");
				} else if (domainType == ME2_UP_DOMAIN) {
					sb.append(UP_HOST_NAME).append(this.url).append("?");
				} else {
					sb.append(STAGING_ME2DAY_HOST_NAME).append(this.url).append("?");
				}
			} else if (apiModeType == API_MODE_REAL) {
				
				if (domainType == ME2_API_DOMAIN) {
					sb.append(API_HOST_NAME).append(this.url).append("?");
				} else if (domainType == ME2_UP_DOMAIN) {
					sb.append(UP_HOST_NAME).append(this.url).append("?");
				} else {
					sb.append(HOST_NAME).append(this.url).append("?");
				}
			}
			
			return sb;
		 }
	 }
	private static String[] stagingWarningMsgs = new String[] {
		"난 니가 Staging에 접근하는것을 알고있다.",
		"넌 지금 Staging에 접근하고 있다.",
		"아빠, staging에 접속하면 월급이 깍인데~",
		"staging에 접속하면 안조으다~"
	};

	private static long statgingLastNotiTime = 0;
	private static void checkStagingApi() {
		if (!AppBuildCheckFlag.DEBUG_MODE) {
			try {
				long current = System.currentTimeMillis();
				if (current - statgingLastNotiTime > 80000) {
					statgingLastNotiTime = current;

					if (M3Application.getCurrentApplication() != null) {
						int idx = (int)((Math.random() * 100) % 4);

						final Toast toast = Toast.makeText(M3Application.getCurrentApplication(), stagingWarningMsgs[idx], Toast.LENGTH_SHORT);
						toast.show();

						new Handler().postDelayed(new Runnable() {
							public void run() {
								toast.cancel();
							}
						}, 1000);
					}
				}
			} catch (Exception e) {
				logger.e(e);
			}
		}
	}
	
	public static StringBuilder addParam(StringBuilder buffer, String name, Object value) {
		return addParam(buffer, name, value, false);
	}
	
	public static StringBuilder addParam(StringBuilder buffer, String name, Object value, boolean nullParam) {
		if (nullParam) {
			String str = "";
			if (value != null) {
				str = value.toString();
			}
			
			buffer.append(name + "=").append(URLEncoder.encode(str)).append("&");
		} else {
			if (value != null) {
				String str = value.toString();
				if (str.length() > 0) {
					buffer.append(name + "=").append(URLEncoder.encode(str)).append("&");
				}
			}
		}
		
		return buffer;
	}
	
	// 마지막 기호를 제거한다.
	public static StringBuilder removeLastFlag(StringBuilder buffer) {
		if (buffer != null) {
			int len = buffer.length();
			if (len > 0) {
				String str = buffer.substring(len-1, len);
				if (!TextUtils.isEmpty(str)) {
					if (str.equals("?") || str.equals("&")) {
						return buffer.deleteCharAt(len-1);
					}
				}
			}
		}
		return buffer;
	}

	public static void printLogUrl(String url) {
		
		if (AppBuildCheckFlag.DEBUG_MODE) {

			String loginId = UserSharedPrefModel.get().getUserId();
			String fullAuthToken = UserSharedPrefModel.get().getFullAuthToken();
			StringBuilder sb = new StringBuilder(url);
			sb.append("&uid=").append(loginId).append("&ukey=").append("full_auth_token%20").append(fullAuthToken);

			logger.d("[REQUEST_API M3]\n%s", sb.toString());
		}
	}
}
