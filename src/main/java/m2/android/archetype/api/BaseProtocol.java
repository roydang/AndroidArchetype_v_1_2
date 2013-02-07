/*
 * @(#)Me2dayProtocol.java $$version ${date}
 *
 * Copyright 2007 NHN Corp. All rights Reserved.
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package m2.android.archetype.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import m2.android.archetype.api.BaseProtocolEnum.Me2dayURL;
import m2.android.archetype.base.BaseConstants;
import m2.android.archetype.base.M3Application;
import m2.android.archetype.base.ParameterConstants;
import m2.android.archetype.util.CryptoUtility;
import m2.android.archetype.util.LocaleUtility;
import m2.android.archetype.util.Logger;
import m2.android.archetype.util.StringUtility;
import m2.android.archetype.util.Utility;

import org.apache.http.client.utils.URIUtils;

import android.content.Context;
import android.text.TextUtils;

import com.nhn.android.archetype.base.util.internal.M2baseUtility;

/**
 * 미투데이 api Protocol
 * 미투데이 api 참조.
 * @author telltale
 *
 */
public class BaseProtocol {

	private static Logger logger = Logger.getLogger(BaseProtocol.class);

	/////////////////////////////////////////////////////////////////////////////
	/////////// 채팅 API START  ////////////////////////////////////////////

	/**
	 *  해당 메소드는 RTCS 서버를 위해 사용된다 추후 삭제 필요하다.
	 * @param scheme
	 * @param host
	 * @param port
	 * @param path
	 * @param query
	 * @param fragment
	 * @return
	 * @throws URISyntaxException
	 */
	public static URI createURI(String scheme, String host, int port, String path, String query, String fragment) throws URISyntaxException {
		
		logger.d("createURI HOST=%s", host);
	
		return URIUtils.createURI(scheme, host, port, path, query, fragment);
		
	}
	/////////// 채팅 API END ///////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////

	
	
	
	//////////// M3 Test//////////////////////
	public static String metoo(String postId) {
		StringBuilder url = Me2dayURL.DO_METOO.getURL();
		if (postId != null) {
			BaseProtocolEnum.addParam(url, "post_id", postId);
		} 
		
		Utility.appendSigUrl(url, false);
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String getStreamPostCombi(String direction, String targetPostdate, int streamDataType, String streamDataScope, String resolution) {
		
		StringBuilder url;
		if (streamDataType == BaseConstants.STREAM_DATA_TYPE_ALL) {
			url = Me2dayURL.STREAM.getURL();
			
			if (direction != null && direction.equals("after")) {
				BaseProtocolEnum.addParam(url, "count", 41);
			} else {
				BaseProtocolEnum.addParam(url, "count", 40);
			}
			BaseProtocolEnum.addParam(url, direction, targetPostdate);
			BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_RESOLUTION_TYPE, resolution);
		} else {
			String bandUrl;
			if (streamDataType != BaseConstants.STREAM_DATA_TYPE_BAND) {
				bandUrl = String.format(Me2dayURL.GET_POST.getURL().toString(), M3Application.getCurrentApplication().getLoginId()); 
			} else {
				bandUrl = String.format(Me2dayURL.GET_POST.getURL().toString(), streamDataScope);
			}
			
			url = new StringBuilder(bandUrl);
			
			if (streamDataType == BaseConstants.STREAM_DATA_TYPE_FRIENDGROUP) {
				url.append("scope=friend[group[").append(URLEncoder.encode(streamDataScope)).append("]]&");
				
			} else if (streamDataType == BaseConstants.STREAM_DATA_TYPE_SCOPE) {
				url.append("scope=friend[").append(URLEncoder.encode(streamDataScope)).append("]&");
			}
			
			if (direction != null && direction.equals("to")) {
				BaseProtocolEnum.addParam(url, "count", 41);
				BaseProtocolEnum.addParam(url, "to", targetPostdate);
			} else {
				BaseProtocolEnum.addParam(url, "count", 40);
				BaseProtocolEnum.addParam(url, "before", targetPostdate);
			}
			BaseProtocolEnum.addParam(url, "stream_format", true);
			
		}
		BaseProtocolEnum.addParam(url, "include_multimedia", true);
		
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
		
	}
	
	public static String getFriends(String userId, String scope, int friendDataType, int page, int itemCount, boolean relation) {
		String strUrl = String.format(Me2dayURL.GET_FRIENDS.getURL().toString(), userId);
		StringBuilder url = new StringBuilder(strUrl);
		
		if (friendDataType == BaseConstants.STREAM_DATA_TYPE_FRIENDGROUP) {
			url.append("scope=group[").append(URLEncoder.encode(scope)).append("]&");
		} else {
			BaseProtocolEnum.addParam(url, "scope", scope);	
		}
		
		BaseProtocolEnum.addParam(url, "offset", page);
		BaseProtocolEnum.addParam(url, "count", itemCount);
		if (relation) {
			BaseProtocolEnum.addParam(url, "include_relation", relation);
		}
		BaseProtocolEnum.addParam(url, "order", BaseConstants.SCOPE_ORDER_INTIMACY);
		BaseProtocolEnum.addParam(url, "sub_order", BaseConstants.SCOPE_ORDER_LAST_POSTED_AT);
		
		Utility.appendSigUrl(url, false);
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	/**
	 * SEARHC_PERSON
	 * 사람을 찾는다.
	 * @param keyword - 검색키워드
	 */
	static public String searchPerson(String keyword, int offset, int count) {
		StringBuilder url = Me2dayURL.SEARCH_PERSON.getURL();
		
		BaseProtocolEnum.addParam(url, "query", keyword);
		if (offset != 0) {
			BaseProtocolEnum.addParam(url, "offset", offset);
		}	

		BaseProtocolEnum.addParam(url, "count", count);

		Utility.appendSigUrl(url, true);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}

	
	public static String getPins(String userId, int page, int itemCount) {
		StringBuilder url = Me2dayURL.GET_PINS.getURL();
		BaseProtocolEnum.addParam(url, "user", userId);
		BaseProtocolEnum.addParam(url, "offset", page);
		BaseProtocolEnum.addParam(url, "count", itemCount);
		BaseProtocolEnum.addParam(url, "sub_order", BaseConstants.SCOPE_ORDER_LAST_POSTED_AT);
		Utility.appendSigUrl(url, true);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
		
	//////////// M3 Test//////////////////////
	
	public static String getStream() {

		
		StringBuilder url = Me2dayURL.STREAM.getURL();
		
		BaseProtocolEnum.addParam(url, "count", 40);
		BaseProtocolEnum.addParam(url, "include_multimedia", true);
		
		Utility.appendSigUrl(url);

		String urlText = url.toString();
		logger.d("[REQUEST_API M3] getStream \n%s", urlText);
		return urlText;
	}
	public static String getStream(String direction, String targetPostdate, String scope) {

		
		StringBuilder url = Me2dayURL.STREAM.getURL();
		if (direction != null && direction.equals("after")) {
			BaseProtocolEnum.addParam(url, "count", 41);
		} else {
			BaseProtocolEnum.addParam(url, "count", 40);
		}
		BaseProtocolEnum.addParam(url, direction, targetPostdate);
		BaseProtocolEnum.addParam(url, "include_multimedia", true);
		
		if (StringUtility.isNotNullOrEmpty(scope)) {
			BaseProtocolEnum.addParam(url, "scope", scope);
		}
		
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String getPostsStreamFormat(String userId, String beforeDate, int count, String scope) {
		String strUrl = String.format(Me2dayURL.GET_POST.getURL().toString(), userId);
		StringBuilder url = new StringBuilder(strUrl);

		if (StringUtility.isNotNullOrEmpty(beforeDate)) {
			BaseProtocolEnum.addParam(url, "before", beforeDate);
		}
		BaseProtocolEnum.addParam(url, "count", count);
		BaseProtocolEnum.addParam(url, "include_multimedia", true);		
		BaseProtocolEnum.addParam(url, "stream_format", true);
		
		if (StringUtility.isNotNullOrEmpty(scope)) {
			BaseProtocolEnum.addParam(url, "scope", scope);
		}
		
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String getPosts(String userId, String beforeDate, String toDate, int count, String scope) {
		String strUrl = String.format(Me2dayURL.GET_POST.getURL().toString(), userId);
		StringBuilder url = new StringBuilder(strUrl);
		
		BaseProtocolEnum.addParam(url, "to", toDate);
		BaseProtocolEnum.addParam(url, "before", beforeDate);
		BaseProtocolEnum.addParam(url, "count", count);
		BaseProtocolEnum.addParam(url, "include_multimedia", true);
		
		if (StringUtility.isNotNullOrEmpty(scope)) {
			BaseProtocolEnum.addParam(url, "scope", scope);
		}
		
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String uploadCoverImage(String userId, String category) {
		String strUrl = String.format(Me2dayURL.UPLOAD_PHOTO.getURL().toString(), userId);
		StringBuilder url = new StringBuilder(strUrl);
		
		BaseProtocolEnum.addParam(url, "photo_category", category);
		Utility.appendSigUrl(url, false);
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	
	// 현재 사용하는 곳이 없음
	@Deprecated 
	public static String getPostsSimple(String userId, String scope, String before, int count, boolean useSimpleFormat) {
		String strUrl = String.format(Me2dayURL.GET_POST.getURL().toString(), userId);
		StringBuilder url = new StringBuilder(strUrl);
		
		BaseProtocolEnum.addParam(url, "scope", scope);
		BaseProtocolEnum.addParam(url, "before", before);
		BaseProtocolEnum.addParam(url, "count", count);
		BaseProtocolEnum.addParam(url, "use_simple_format", useSimpleFormat);
		
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	
	////////// 글쓰기 관련 API /////////////////////////
	
	/**
	 * v3.0.0 upload_photo API
	 * @param userId
	 * @return
	 */
	public static String uploadPhoto(String userId) {
		StringBuilder url = Me2dayURL.UPLOAD_PHOTO.getURL();
		StringBuilder buffer = new StringBuilder();
		buffer.append(String.format(url.toString(), userId));
		
		Utility.appendSigUrl(buffer, false);

		String urlText = buffer.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	/**
	 * v3.0.0 upload_photo API
	 * @param userId
	 * @return
	 */
	public static String uploadVideo(String userId) {
		StringBuilder url = Me2dayURL.UPLOAD_VIDEO.getURL();
		StringBuilder buffer = new StringBuilder();
		buffer.append(String.format(url.toString(), userId));
		
		Utility.appendSigUrl(buffer, false);

		String urlText = buffer.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	/**
	 * v3.0.0 create_post API
	 * @param photoIds
	 * @return
	 */
	public static String createPost(String userId) {
		StringBuilder url = Me2dayURL.CREATE_POST.getURL();
		StringBuilder buffer = new StringBuilder();
		buffer.append(String.format(url.toString(), userId));
		
//		if (photoIds != null && photoIds.size() > 0) {
//			StringBuffer sb = new StringBuffer();
//			for (String photoId : photoIds) {
//				sb.append(photoId);
//				sb.append(";");
//			}
//			
//			BaseProtocolEnum.addParam(buffer, "attachment_id", sb);
//		}
//		
//		if (Utility.isNotNullOrEmpty(pingbackPostId)) {
//			BaseProtocolEnum.addParam(buffer, "pingback_post_id", pingbackPostId);
//		}
		
		Utility.appendSigUrl(buffer, false);

		String urlText = buffer.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}


	////////// 로그인, 회원가입 /////////////////////////
	public static String getLocationAgreeSettingUrl(boolean agree)	{
		StringBuilder url = Me2dayURL.UPDATE_OZ_USER_SETTING.getURL();
		
		String userId = M3Application.getCurrentApplication().getLoginId();
		if (!TextUtils.isEmpty(userId)) {
			BaseProtocolEnum.addParam(url, "user_id", userId);
		}
		
		if (agree) {
			BaseProtocolEnum.addParam(url, "oz_location_info_consent_yn", "y");
		} else {
			BaseProtocolEnum.addParam(url, "oz_location_info_consent_yn", "n");
		}
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}

	public static String getLocationAgreeInfoUrl()	{		
		StringBuilder url = Me2dayURL.GET_OZ_USER_SETTING_INFO.getURL();
		
		String userId = M3Application.getCurrentApplication().getLoginId();
		if (!TextUtils.isEmpty(userId)) {
			BaseProtocolEnum.addParam(url, "user_id", userId);
		}
		
		M3Application application = M3Application.getCurrentApplication();
		if (!LocaleUtility.isLocaleKorean(application)) {
			BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_LOCALE, LocaleUtility.getSystemLocaleString(application));
		}

		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	/**
	 * get_FriendsForAutocomplete api
	 * 자동 소환 list 가져온다.
	 */

	public static String getAutocompleteFriendGroup() {
		StringBuilder url = Me2dayURL.GET_AUTOCOMPLETE_FRIEND_LIST.getURL();
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}

	public static String getStartToken() {
		StringBuilder url = Me2dayURL.GET_START_TOKEN.getURL();

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String getRegisterMe2dayUser(String token, String id, String password, String nickname, String email, boolean allowOuterRecommend, 
			String cellphone, String cellphoneCarrier, String timezone, Boolean signupLocation) {

		StringBuilder url = Me2dayURL.REGISTER_USER.getURL();
		
		token = CryptoUtility.generateCredential(token);
		password = CryptoUtility.generateCredential(password);

		BaseProtocolEnum.addParam(url, "credential", token);
		BaseProtocolEnum.addParam(url, "id", id);
		BaseProtocolEnum.addParam(url, "password", password);
		BaseProtocolEnum.addParam(url, "nickname", nickname);
		BaseProtocolEnum.addParam(url, "allow_outer_recommend", allowOuterRecommend ? "1" : "0");

		if (!TextUtils.isEmpty(email)) {
			BaseProtocolEnum.addParam(url, "email", email);
		}
		
		BaseProtocolEnum.addParam(url, "cellphone", TextUtils.isEmpty(cellphone) ? "" : cellphone);
		BaseProtocolEnum.addParam(url, "cellphone_carrier", TextUtils.isEmpty(cellphoneCarrier) ? "" : cellphoneCarrier);
		BaseProtocolEnum.addParam(url, "timezone", timezone);
		BaseProtocolEnum.addParam(url, "signup_location", signupLocation ? "1" : "0");
			
		// 이정우 차장님이 회원가입완료시 넣어달라고 했음
		BaseProtocolEnum.addParam(url, "signup_from", "android_" + M2baseUtility.getVersionName(M3Application.getCurrentApplication()));	
		
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	//프로필 정보 수정
	static public String setProfile() {
		StringBuilder url = Me2dayURL.SET_PROFILE.getURL();

		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	//프로필 사진 변경
	static public String setFace() {
		StringBuilder url = Me2dayURL.UPLOAD_FACE.getURL();

		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}

	/**
	 * 
	 * @param blockType : block_type : 차단해제하고자하는 종류 "post"|"chat"
	 * @param userId : block_user_id : 차단해제하고자하는 유저 아이디
	 * @return
	 */
	public static String setUnBlock(String blockType, String userId) {
		StringBuilder url = Me2dayURL.SET_UNBLOCK.getURL();
		BaseProtocolEnum.addParam(url, "block_type", blockType);
		BaseProtocolEnum.addParam(url, "block_user_id", userId);
		
		Utility.appendSigUrl(url, false);
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	/**
	 * 
	 * @param blockType : block_type : 블럭하고자하는 종류 "post"|"chat"
	 * @param userId : block_user_id : 블럭하고자하는 유저 아이디
	 * @param continuedBlock : continued_block : 글차단일경우 계속차단하고자할때 true 로 지정 (지정안할시 default "하루만 차단")
	 * @return
	 */
	
	public static String setBlock(String blockType, String userId, String continuedBlock) {
		StringBuilder url = Me2dayURL.SET_BLOCK.getURL();
		BaseProtocolEnum.addParam(url, "block_type", blockType);
		BaseProtocolEnum.addParam(url, "block_user_id", userId);
		BaseProtocolEnum.addParam(url, "continued_block", continuedBlock);
		Utility.appendSigUrl(url, false);
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}

	
	/**
	 * 미투데이 Id 상세정보를 가져온다.
	 * @param userId - 미투데이 Id
	 */
	public static String getPerson(String userId) {		
		String strUrl = String.format(Me2dayURL.GET_PERSON.getURL().toString(), userId);
		StringBuilder url = new StringBuilder(strUrl);
		BaseProtocolEnum.addParam(url, "include_plain_description", true);
		BaseProtocolEnum.addParam(url, "include_cover_image", true);
		BaseProtocolEnum.addParam(url, "include_block_status", true);
		Utility.appendSigUrl(url, true);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String getFriendPerson(String userId) {		
		String strUrl = String.format(Me2dayURL.GET_PERSON.getURL().toString(), userId);
		StringBuilder url = new StringBuilder(strUrl);
		BaseProtocolEnum.addParam(url, "include_plain_description", true);
		BaseProtocolEnum.addParam(url, "include_cover_image", true);
		Utility.appendSigUrl(url, true);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	/**
	 * 친구가 속한 그룹 API
	 */
	static public String getFriendgroupOfFriend(String friendId) {
		StringBuilder url = Me2dayURL.GET_FRIENDGROUP_OF.getURL();
		BaseProtocolEnum.addParam(url, "friend_id", friendId);

		Utility.appendSigUrl(url, true);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	/**
	 * 그룹 지정 API
	 */
	static public String setFriendGroup(String friendId, String groupId) {
		StringBuilder url = Me2dayURL.SET_FRIENDGROUP.getURL();
		BaseProtocolEnum.addParam(url, "friend_id", friendId);
		BaseProtocolEnum.addParam(url, "group_id", groupId);
		
		Utility.appendSigUrl(url, true);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	
	/**
	 * 본 API는 글감을 검색할 때 사용
	 * 
	 * @param domain 	글감 도메인
	 * @param query 	검색 키워드
	 * @return 요청 url
	 */
	public static String getThemes(String domain, String query) {
		StringBuilder url = Me2dayURL.GET_THEME.getURL();
		
		BaseProtocolEnum.addParam(url, "domain", domain);
		if (TextUtils.isEmpty(query) == false) {
			BaseProtocolEnum.addParam(url, "query", query);
		}

		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	
	public static String getSpotCategories() {
		StringBuilder url = Me2dayURL.GET_SPOT_CATEGORIES.getURL();
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String generateShortUrl(String original_url, String url_type) {
		StringBuilder url = Me2dayURL.GENERATE_SHORT_URL.getURL();

		BaseProtocolEnum.addParam(url, "url", original_url);
		BaseProtocolEnum.addParam(url, "url_type", url_type);
		
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}

	public static String getBands(String userId) {
		StringBuilder url = Me2dayURL.GET_BANDS.getURL();
		BaseProtocolEnum.addParam(url, "user_id", userId);
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	/**
	 * POI SPOT들을 찾는다.(search_spots)
	 * @author daniel jihoon oh
	 * 
	 * @param longitude : 경도
	 * @param latitude : 위도
	 * @param keyword  : SPOT 키워드
	 * @param offset   : 가져올 spot 시작포인트
	 * @param count    : 한방에 가져올 spot 개수
	 * @param kind     : 일반 버전(1km 이내 영역) null / 거리 제한 없는 버전 : schedule 
	 * @return
	 */
	static public String searchSpot(double longitude, double latitude, String keyword, 
		String poiCategoryId, int offset, int count, String kind) {
		StringBuilder url = Me2dayURL.SEARCH_SPOTS.getURL();

		BaseProtocolEnum.addParam(url, "longitude", longitude);
		BaseProtocolEnum.addParam(url, "latitude", latitude);
		BaseProtocolEnum.addParam(url, "poi_category_id", poiCategoryId);
		if (keyword != null) {
			BaseProtocolEnum.addParam(url, "keyword", keyword);
		}
		BaseProtocolEnum.addParam(url, "offset", offset);
		BaseProtocolEnum.addParam(url, "count", count);
		
		if (BaseConstants.POI_SEARCH_KIND_SCHEDULE.equals(kind)) {
			url.append("&kind=").append(BaseConstants.POI_SEARCH_KIND_SCHEDULE);
		}
		
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	static public String registerUserSpot(String spotName, double selectLongitude, double selectLatitude, 
		String address, int poiCategoryId, String phoneNumber) {
		StringBuilder url = Me2dayURL.REGISTER_USER_SPOT.getURL();

		BaseProtocolEnum.addParam(url, "name", spotName);
		BaseProtocolEnum.addParam(url, "category_id", poiCategoryId);
		BaseProtocolEnum.addParam(url, "address", address);
		BaseProtocolEnum.addParam(url, "phone", phoneNumber);
		BaseProtocolEnum.addParam(url, "longitude", selectLongitude);
		BaseProtocolEnum.addParam(url, "latitude", selectLatitude);
		
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}

	public static String getRegisterNaverUser(String token, String naverId, String userId, String nickname, boolean openPersonalInfo, 
			boolean recommendToNaverFriend, boolean allowOuterRecommend, String phone) {

		StringBuilder url = Me2dayURL.REGISTER_USER_NAVER.getURL();
		
		token = CryptoUtility.generateCredential(token);
		BaseProtocolEnum.addParam(url, "credential", token);
		BaseProtocolEnum.addParam(url, "naver_id", naverId);
		BaseProtocolEnum.addParam(url, "user_id", userId);
		BaseProtocolEnum.addParam(url, "nickname", nickname);
		BaseProtocolEnum.addParam(url, "open_personal_info", openPersonalInfo ? "1" : "0");
		BaseProtocolEnum.addParam(url, "recommend_to_naver_friend", recommendToNaverFriend ? "1" : "0");
		BaseProtocolEnum.addParam(url, "allow_outer_recommend", allowOuterRecommend ? "1" : "0");

		// 이정우 차장님이 회원가입완료시 넣어달라고 했음
		BaseProtocolEnum.addParam(url, "signup_from", "android_" + M2baseUtility.getVersionName(M3Application.getCurrentApplication()));	
		
		if (TextUtils.isEmpty(phone)) {
			BaseProtocolEnum.addParam(url, "phone", phone);
		}
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}

	public static String getComments(String postId, int page, int itemCount) {		
		StringBuilder url = Me2dayURL.GET_COMMENTS.getURL();
		
		BaseProtocolEnum.addParam(url, "post_id", postId);		
		BaseProtocolEnum.addParam(url, "page", String.format("page_%d", page));
		BaseProtocolEnum.addParam(url, "items_per_page", itemCount);
		BaseProtocolEnum.addParam(url, "order", "desc");

		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String getAuthLoginStart(String authType, String userid) {		
		StringBuilder url = Me2dayURL.AUTH_LOGIN_START.getURL();
		
		BaseProtocolEnum.addParam(url, "auth_type", authType);
		BaseProtocolEnum.addParam(url, "user_id", userid);

		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}	
	


	public static String getCheckNaverUser(String token, String naverId, String password, String locale) {
		StringBuilder url = Me2dayURL.CHECK_NAVER_USER.getURL();
		
		token = CryptoUtility.generateCredential(token);
		password = CryptoUtility.generateCredential(password);
		
		BaseProtocolEnum.addParam(url, "version", "2");
		BaseProtocolEnum.addParam(url, "credential", token);
		BaseProtocolEnum.addParam(url, "naver_id", naverId);
		BaseProtocolEnum.addParam(url, "password", password);
		
		if (!TextUtils.isEmpty(locale)) {
			BaseProtocolEnum.addParam(url, "locale", locale);
		}
		
		BaseProtocolEnum.removeLastFlag(url);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}

	public static String getCheckMe2dayUser(String token, String naverId, String locale) {
		StringBuilder url = Me2dayURL.CHECK_ME2DAY_USER.getURL();

		token = CryptoUtility.generateCredential(token);
		
		BaseProtocolEnum.addParam(url, "credential", token);
		BaseProtocolEnum.addParam(url, "naver_id", naverId);
		
		if (!TextUtils.isEmpty(locale)) {
			BaseProtocolEnum.addParam(url, "version", "2");
		}

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String getCheckAuthEmail(String token, String id) {
		StringBuilder url = Me2dayURL.CHECK_AUTH_EMAIL.getURL();

		token = CryptoUtility.generateCredential(token);
		
		BaseProtocolEnum.addParam(url, "credential", token);
		BaseProtocolEnum.addParam(url, "id", id);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}

	public static String getResendAuthEmail(String token, String id, String email) {
		StringBuilder url = Me2dayURL.RESEND_AUTH_EMAIL.getURL();

		token = CryptoUtility.generateCredential(token);
		
		BaseProtocolEnum.addParam(url, "credential", token);
		BaseProtocolEnum.addParam(url, "id", id);
		BaseProtocolEnum.addParam(url, "email", email);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}

	public static String getFriendGroup() {
		StringBuilder url = Me2dayURL.GET_FRIENDGROUP.getURL();
	
		BaseProtocolEnum.addParam(url, "scope", "all");
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String getPublicFriendGroup(String userId) {
		StringBuilder url = Me2dayURL.GET_FRIENDPUBLICGROUP.getURL();
		StringBuilder buffer = new StringBuilder();		
		buffer.append(String.format(url.toString(), userId));
		BaseProtocolEnum.addParam(buffer, "scope", "public");

		Utility.appendSigUrl(buffer, false);

		String urlText = buffer.toString();
		logger.d("[REQUEST_API M3]\n%s", urlText);
		return urlText;
	}
	
	////////// 글상세 /////////////////////////
	public static String getPostDetails(String postId, String deviceId, String resolutionType)	{		
		StringBuilder url = Me2dayURL.GET_POST_DETAILS.getURL();
		StringBuilder buffer = new StringBuilder();		
		buffer.append(String.format(url.toString(), postId));

		if (!TextUtils.isEmpty(deviceId)) {
			BaseProtocolEnum.addParam(buffer, ParameterConstants.PARAM_DEVICE_ID, deviceId);
		}
		BaseProtocolEnum.addParam(buffer, ParameterConstants.PARAM_LOCALE, LocaleUtility.getSystemLocaleString(M3Application.getCurrentApplication()));
		BaseProtocolEnum.addParam(buffer, ParameterConstants.PARAM_RESOLUTION_TYPE, resolutionType);

		Utility.appendSigUrl(buffer, false);

		String urlText = buffer.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String getContent(String domain, String identifier, String key)	{		
		StringBuilder url = Me2dayURL.GET_CONTENT.getURL();

		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_DOMAIN, domain);
		if (!TextUtils.isEmpty(identifier)) {
			BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_IDENTIFIER, identifier);
		}
		if (!TextUtils.isEmpty(key)) {
			BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_KEY, key);
		}

		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String checkMetooable(String postId) {
		StringBuilder url = Me2dayURL.CHECK_METOOABLE.getURL();

		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_POST_ID, postId);
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String cancelMetoo(String postId) {
		StringBuilder url = Me2dayURL.CANCEL_METOO.getURL();

		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_POST_ID, postId);
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}

	public static String getMetoos(String postId, int page, int itemsPerPage) {
		StringBuilder url = Me2dayURL.GET_METOOS.getURL();

		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_POST_ID, postId);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_PAGE, page);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_ITEMS_PER_PAGE, itemsPerPage);
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String updateTags(String postId, String tags) {
		StringBuilder url = Me2dayURL.UPDATE_TAGS.getURL();

		if (!TextUtils.isEmpty(postId)) {
			BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_POST, postId);
		}
		
		if (!TextUtils.isEmpty(tags)) {
			BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_TAGS, tags);
		}

		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String deletePost(String postId) {
		StringBuilder url = Me2dayURL.DELETE_POST.getURL();

		if (!TextUtils.isEmpty(postId)) {
			BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_POST_ID, postId);
		}

		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String deleteComment(String commentId) {
		StringBuilder url = Me2dayURL.DELETE_COMMENT.getURL();

		if (!TextUtils.isEmpty(commentId)) {
			BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_COMMENT_ID, commentId);
		}

		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}

	public static String getAlarmNotiPost(String deviceType, String deviceId, String deviceToken, String postId, String authorUserId, String locale) {
		StringBuilder url = Me2dayURL.GET_NOTI_POST.getURL();

		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_DEVICE_TYPE, deviceType);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_DEVICE_ID, deviceId);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_DEVICE_TOKEN, deviceToken);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_POST_ID, postId);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_AUTHOR_USER_ID, authorUserId);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_LOCALE, locale);

		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String setAlarmNotiPost(int deviceType, String deviceId, String deviceToken, String postId, String authorUserId, boolean isEnable, String locale) {
		StringBuilder url = Me2dayURL.SET_NOTI_POST.getURL();

		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_DEVICE_TYPE, deviceType);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_DEVICE_ID, deviceId);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_DEVICE_TOKEN, deviceToken);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_POST_ID, postId);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_AUTHOR_USER_ID, authorUserId);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_IS_ENABLE, isEnable);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_LOCALE, locale);

		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String getPostViewComments(String postId, int page, int itemPerPage, String resolutionType) {		
		StringBuilder url = Me2dayURL.GET_COMMENTS.getURL();
		
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_POST_ID, postId);		
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_PAGE, String.format("page_%d", page));
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_ITEMS_PER_PAGE, itemPerPage);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_ORDER, ParameterConstants.PARAM_ORDER_ASC);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_RESOLUTION_TYPE, resolutionType);
				

		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String createComment(String postId, String body, String stickerCode, String resolutionType, boolean pingback) {		
		StringBuilder url = Me2dayURL.CREATE_COMMENT.getURL();
		
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_POST_ID, postId);		
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_BODY, body);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_STICKER_CODE, stickerCode);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_RESOLUTION_TYPE, resolutionType);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_PINGBACK, pingback ? "true" : "false");
//		BaseProtocolEnum.addParam(url, "intentional_error", "true");

		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}

	public static String friendship(String userId, String scope, String value, String message) {
		return friendship(userId, scope, value, message, null, null, null, null);
	}
	
	
	/**
	 * 친구신청 api
	 * @param userId
	 * @param scope 친구 신청(scope=friend&value=on)
	 * @param value
	 * @param message
	 * @param addSmsFriend 관심친구 지정 / true or false
	 * @param groupId
	 * @param isPin 친구 신청 시 구독 여부  / true or false
	 * @param isBlock 친구 끊기 시 차단할지 여부  / true or false
	 * @return
	 */
	
	public static String friendship(String userId, String scope, String value, String message, String addSmsFriend, String groupId, String isPin, String isBlock) {
		StringBuilder url = Me2dayURL.FRIENDSHIP.getURL();
		
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_USER_ID, userId);		
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_SCOPE, scope);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_VALUE, value);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_MESSAGE, message);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_ADD_SMS_FRIEND, addSmsFriend);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_GROUP_ID, groupId);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_IS_PIN_USER, isPin);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_IS_BLOCK_USER, isBlock);
		
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}

	public static String pin(String userId, boolean pin) {
		StringBuilder url;
		if (pin) {
			url =  Me2dayURL.PIN.getURL();
		} else {
			url =  Me2dayURL.UNPIN.getURL();
		}
		
		BaseProtocolEnum.addParam(url, "user", userId);
		
		Utility.appendSigUrl(url, true);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String addKeywords(String keyword) {		
		StringBuilder url = Me2dayURL.ADD_KEYWORDS.getURL();	
		BaseProtocolEnum.addParam(url, "keyword", keyword);
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String removeKeywords(String keyword) {		
		StringBuilder url = Me2dayURL.REMOVE_KEYWORDS.getURL();	
		BaseProtocolEnum.addParam(url, "keyword", keyword);
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String getKeywords() {		
		StringBuilder url = Me2dayURL.GET_KEYWORDS.getURL();	
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String getMainKeywords() {		
		StringBuilder url = Me2dayURL.GET_MAIN_KEYWORDS.getURL();	
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	//////////사람들은 관련 API /////////////////////////
	
	/**
	 * 사람들은_오늘
	 * @return
	 */
	public static String getToday() {		
		StringBuilder url = Me2dayURL.TODAY.getURL();	
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	/**
	 * 사람들은_인기포토
	 * @return
	 */
	public static String getTodayContents(String scope, int offset, int count) {		
		StringBuilder url = Me2dayURL.GET_TODAY_CONTENTS.getURL();	
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_SCOPE, scope);
		BaseProtocolEnum.addParam(url, "offset", offset);
		BaseProtocolEnum.addParam(url, "count", count);
		
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	
	public static String getTodayEventContents() {
		StringBuilder url = Me2dayURL.GET_TODAY_EVENT_CONTENTS.getURL();	
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	/**
	 * 사람들은_인기리뷰_관련글
	 * @return
	 */
	public static String getPostsByContent(String targetId, String domain, String identifier, int page, int count) {
		StringBuilder url = Me2dayURL.GET_POSTS_BY_CONTENT.getURL();	
		
		if (TextUtils.isEmpty(targetId) == false) {
			BaseProtocolEnum.addParam(url, "target_id", targetId);
		}
		
		if (TextUtils.isEmpty(identifier) == false) {
			BaseProtocolEnum.addParam(url, "identifier", identifier);
		}
		
		BaseProtocolEnum.addParam(url, "domain", domain);
		BaseProtocolEnum.addParam(url, "page", page);
		BaseProtocolEnum.addParam(url, "count", count);
		
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	/**
	 * 사람들은_이벤트_채팅 응모하기
	 * @return
	 */
	public static String applyToChat(String chatId) {
		StringBuilder url = Me2dayURL.APPLY_TO_CHAT.getURL();	
		
		String userId = M3Application.getCurrentApplication().getLoginId();
		BaseProtocolEnum.addParam(url, "user_id", userId);
		BaseProtocolEnum.addParam(url, "me2live_chat_id", chatId);
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	/**
	 * @author bcfamily
	 * 글검색 (with json)
	 * http://me2day.net/api/search.json
	 * @param search.json?scope=all&order=recent&query=bcfamily&offset=0&count=20
	 */
	public static String searchPosts(String scope, String order, String query, int offset, int count) {
		StringBuilder url = Me2dayURL.SEARCH.getURL();
		
		BaseProtocolEnum.addParam(url, "scope", scope);
		BaseProtocolEnum.addParam(url, "order", order);
		if (query != null) {
			BaseProtocolEnum.addParam(url, "query", query);
		}
		BaseProtocolEnum.addParam(url, "offset", offset);
		BaseProtocolEnum.addParam(url, "count", count);
		BaseProtocolEnum.addParam(url, "include_multimedia", true);
		
		Utility.appendSigUrl(url, true);

		String urlText = url.toString();
		logger.d("[REQUEST_API] search_posts \n%s", urlText);
		return urlText;
	}

	
	public static String getLatestAppVersion(String kind, String version) {

		StringBuilder url = Me2dayURL.GET_LATEST_APP_VERSION.getURL();
		BaseProtocolEnum.addParam(url, "version", version);		
		BaseProtocolEnum.addParam(url, "kind", kind);
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;	
	}
	
	public static String getNearBySpots(String latitude, String longitude, String scope, String order, String range, String count) {

		StringBuilder url = Me2dayURL.GET_NEARBY_SPOT.getURL();
		BaseProtocolEnum.addParam(url, "latitude", latitude);		
		BaseProtocolEnum.addParam(url, "longitude", longitude);
		BaseProtocolEnum.addParam(url, "scope", scope);
		BaseProtocolEnum.addParam(url, "order", order);
		BaseProtocolEnum.addParam(url, "range", range);
		BaseProtocolEnum.addParam(url, "count", count);
		
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	public static String getNearByPosts(String latitude, String longitude, String scope, String order, String range, String count) {

		StringBuilder url = Me2dayURL.GET_NEARBY_POSTS.getURL();
		BaseProtocolEnum.addParam(url, "latitude", latitude);		
		BaseProtocolEnum.addParam(url, "longitude", longitude);
		BaseProtocolEnum.addParam(url, "scope", scope);
		BaseProtocolEnum.addParam(url, "order", order);
		BaseProtocolEnum.addParam(url, "range", range);
		BaseProtocolEnum.addParam(url, "count", count);
		
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	public static String getNearByPhotos(String latitude, String longitude, String scope, String range, String count) {

		StringBuilder url = Me2dayURL.GET_NEARBY_PHOTOS.getURL();
		BaseProtocolEnum.addParam(url, "latitude", latitude);		
		BaseProtocolEnum.addParam(url, "longitude", longitude);
		BaseProtocolEnum.addParam(url, "scope", scope);
		BaseProtocolEnum.addParam(url, "range", range);
		BaseProtocolEnum.addParam(url, "count", count);
		
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	public static String getNearByHotSpots(String latitude, String longitude, String range, String count) {

		StringBuilder url = Me2dayURL.GET_NEARBY_HOT_SPOTS.getURL();
		BaseProtocolEnum.addParam(url, "latitude", latitude);		
		BaseProtocolEnum.addParam(url, "longitude", longitude);
		BaseProtocolEnum.addParam(url, "range", range);
		BaseProtocolEnum.addParam(url, "count", count);
		
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String getPostsByContent(String domain, String identifier, String key, String count, String before) {

		StringBuilder url = Me2dayURL.GET_POSTS_BY_CONTENT.getURL();
		BaseProtocolEnum.addParam(url, "domain", domain);		
		BaseProtocolEnum.addParam(url, "identifier", identifier);
		BaseProtocolEnum.addParam(url, "key", null);
		BaseProtocolEnum.addParam(url, "count", count);
		if (before != null) {
			BaseProtocolEnum.addParam(url, "before", before);
		}
		
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	/**
	 * 	
	 * @param userId
	 * @param scope 미투를 찍은(metooed), 내가 작성한(by_me), default(all)
	 * @param before
	 * @param contentType
	 * @return
	 */
	public static String getContentsPosts(String userId, String scope, String before, String contentType, int count) {
		StringBuilder url = Me2dayURL.GET_CONTENT_POST_BY_ME.getURL();
		StringBuilder buffer = new StringBuilder();
		buffer.append(String.format(url.toString(), userId));
		BaseProtocolEnum.addParam(buffer, "scope", scope);
		BaseProtocolEnum.addParam(buffer, "content_type", contentType);
		BaseProtocolEnum.addParam(buffer, "before", before);
		BaseProtocolEnum.addParam(buffer, "count", count);

		Utility.appendSigUrl(buffer, false);

		String urlText = buffer.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	public static String getHotSpots() {

		StringBuilder url = Me2dayURL.HOT_SPOTS.getURL();
			
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	//친구들은 관련 api
	

	/**
	 * 휴대폰 등록여부 확인, 회원인증후 사용
	 * @author daniel jihoon oh
	 * @param
	 * 	scope : 여러 scope는 delimeter '|'를 사용하여 연결가능
	 * 	- "cellphone"   : 휴대폰 정보 요청시
	 *  - "ext_service" : 외부연동 정보 요청시
	 *  @return
	 */
	public static String getSettings(String scope) {
		StringBuilder url = Me2dayURL.GET_SETTINGS.getURL();
		
		BaseProtocolEnum.addParam(url, "scope", scope);
		Utility.appendSigUrl(url, true);
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		
		return urlText;
	}

	/**
	 * 통신사 목록 가져오기, 회원인증 필요없음.
	 * @author daniel jihoon oh
	 * @param none
	 * @return
	 */
	public static String getCellphoneCountry() {
		StringBuilder url = Me2dayURL.GET_CELLPHONE_COUNTRY.getURL();
		
		Utility.appendSigUrl(url, false);
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}

	/**
	 * 휴대폰 인증번호 받기, 회원인증후 사용
	 * @author daniel jihoon oh
	 * @param
	 * 	number  : 휴대폰 번호, 필수
	 *  country : 통신사명, 필수
	 * @return
	 */
	static public String requestCellphoneAuth(String number, String country) {
		StringBuilder url = Me2dayURL.REQ_CELLPHONE_AUTH.getURL();
		
		BaseProtocolEnum.addParam(url, "cellphone_number", number);
		BaseProtocolEnum.addParam(url, "cellphone_country", country);
		Utility.appendSigUrl(url, true);
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}

	/**
	 * 휴대폰 인증 및 등록, 회원인증후 사용
	 * @author daniel jihoon oh
	 * @param
	 * 	number  : 휴대폰 번호, 필수
	 *  country : 통신사명, 필수
	 *  authNumber : 인증번호, 필수
	 * @return
	 */
	static public String authCellphone(String number, String country, String authNumber) {
		StringBuilder url = Me2dayURL.AUTH_CELLPHONE.getURL();
		
		BaseProtocolEnum.addParam(url, "cellphone_number", number);
		BaseProtocolEnum.addParam(url, "cellphone_country", country);
		BaseProtocolEnum.addParam(url, "auth_number", authNumber);
		Utility.appendSigUrl(url, true);
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}

	/**
	 * 초대문자 보내기, 회원인증후 사용
	 * @author daniel jihoon oh
	 * @param
	 * 	number  : 휴대폰 번호, delimeter '|'를 사용하여 연결가능
	 * @return
	 */
	static public String sendInvitationByPhone(String cellphones, String token) {
		StringBuilder url = Me2dayURL.SEND_INVITAION_SMS.getURL();
		
		BaseProtocolEnum.addParam(url, "cellphones", cellphones);
		BaseProtocolEnum.addParam(url, "invitation_token", token);
		Utility.appendSigUrl(url, true);
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}

	static public String handshake(String latitude, String longitude, String requestedAt) {
		StringBuilder url = Me2dayURL.HANDSHAKE.getURL();
		 
		BaseProtocolEnum.addParam(url, "latitude", latitude);
		BaseProtocolEnum.addParam(url, "longitude", longitude);
		BaseProtocolEnum.addParam(url, "requested_at", requestedAt);
		
		Utility.appendSigUrl(url, true);
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}

	static public String getHandshakes(String handshakeId) {
		StringBuilder url = Me2dayURL.GET_HANDSHAKE.getURL();
		
		BaseProtocolEnum.addParam(url, "handshake_id", handshakeId);
		
		Utility.appendSigUrl(url, true);
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	
	
	/** 
	 * 친구초대 
	 */
	// 초대 토큰 요청
	public static String createInvitationToken(String inviteType) {
		StringBuilder url = Me2dayURL.CREATE_INVITATION_TOKEN.getURL();
		
		BaseProtocolEnum.addParam(url, "type", inviteType);
		
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	// 초대 정보 조회
	public static String getInvitationInfo(String inviteToken) {
		StringBuilder url = Me2dayURL.GET_INVITATION_INFO.getURL();
		
		BaseProtocolEnum.addParam(url, "invitation_token", inviteToken);
		
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	// 초대수락(친구맺기)
	public static String acceptFriendship(String inviteToken, String inviteUserId) {
		StringBuilder url = Me2dayURL.ACCEPT_FRIENDSHIP.getURL();
		
		BaseProtocolEnum.addParam(url, "invitation_token", inviteToken);
		BaseProtocolEnum.addParam(url, "invitor_user_id", inviteUserId);
		
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	// 추천친구로 보냄
	public static String addRecommendFriendship(String inviteToken, String inviteUserId) {
		StringBuilder url = Me2dayURL.ADD_RECOMMENT_FRIENDSHIP.getURL();
		
		BaseProtocolEnum.addParam(url, "invitation_token", inviteToken);
		BaseProtocolEnum.addParam(url, "invitor_user_id", inviteUserId);
		
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}

	// 초대수락 거부(device_id로 거절log저장함)
	public static String rejectFriendRequest(String inviteToken, String inviteUserId, String deviceId) {
		StringBuilder url = Me2dayURL.REJECT_FRIEND_REQUEST.getURL();
		
		BaseProtocolEnum.addParam(url, "invitation_token", inviteToken);
		BaseProtocolEnum.addParam(url, "invitor_user_id", inviteUserId);
		BaseProtocolEnum.addParam(url, "device_id", deviceId);
		
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	// 똑똑
	public static String visitLogAble(String userId){
		StringBuilder url = Me2dayURL.VISIT_LOG_ABLE.getURL();
		
		BaseProtocolEnum.addParam(url, "owner_id", userId);
		
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String visitLogAdd(String userId, String message, String stickerCode) {
		StringBuilder url = Me2dayURL.VISIT_LOG_ADD.getURL();
		
		BaseProtocolEnum.addParam(url, "owner_id", userId);
		BaseProtocolEnum.addParam(url, "message", message);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_STICKER_CODE, stickerCode);
		
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String visitLogNew(String userId) {
		StringBuilder url = Me2dayURL.VISIT_LOG_NEW.getURL();
		
		BaseProtocolEnum.addParam(url, "owner_id", userId);
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String visitLogList(String userId, String next, int count, String resolutionType) {
		StringBuilder url = Me2dayURL.VISIT_LOG_LIST.getURL();
		
		BaseProtocolEnum.addParam(url, "owner_id", userId);
		BaseProtocolEnum.addParam(url, "next", next);
		BaseProtocolEnum.addParam(url, "count", count);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_RESOLUTION_TYPE, resolutionType);
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	
	public static String visitLogSet(String value) {
		StringBuilder url = Me2dayURL.VISIT_LOG_SET.getURL();
		
		BaseProtocolEnum.addParam(url, "value", value);
		
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	

	/** 
	 * 알림센터 
	 */
	// 알림센터-새소식
	public static String noticenter_new(int offset, int limit) {
		StringBuilder url = Me2dayURL.NOTICENTER_NEW.getURL();
		
		BaseProtocolEnum.addParam(url, "offset", offset);
		BaseProtocolEnum.addParam(url, "limit", limit);

		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	// 소환받은 글
	public static String noticenter_stream(String scope, int count, String direction, String date, String resolution) {
		StringBuilder url = Me2dayURL.STREAM.getURL();
		
		BaseProtocolEnum.addParam(url, "scope", scope);
		BaseProtocolEnum.addParam(url, "count", count);
		BaseProtocolEnum.addParam(url, direction, date);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_RESOLUTION_TYPE, resolution);
		BaseProtocolEnum.addParam(url, "include_multimedia", true);
		
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		logger.d("[REQUEST_API M3] getStream \n%s", urlText);
		return urlText;
	}

	// 알림센터-받은미투목록
	public static String noticenter_metoo(int offset, int limit) {
		StringBuilder url = Me2dayURL.NOTICENTER_METOO.getURL();
		
		BaseProtocolEnum.addParam(url, "offset", offset);
		BaseProtocolEnum.addParam(url, "limit", limit);

		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	// 알림센터-지우기
	public static String noticenter_delete(String id) {
		StringBuilder url = Me2dayURL.NOTICENTER_DELETE.getURL();
		
		BaseProtocolEnum.addParam(url, "id", id);

		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	// 알림센터-New체크
	public static String noticenter_is_new() {
		StringBuilder url = Me2dayURL.NOTICENTER_IS_NEW.getURL();
		
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}

	// 알림센터-친구생일자목록
	public static String noticenter_birthday_friends() {
		StringBuilder url = Me2dayURL.NOTICENTER_BIRTHDAY_FRIENDS.getURL();
		
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	// 알림센터-받은친구신청 리스트
	public static String get_friendship_request(String userId, String user_ids) {
		StringBuilder url = Me2dayURL.GET_FRIENDSHIP_REQUEST.getURL();
		StringBuilder buffer = new StringBuilder();		
		buffer.append(String.format(url.toString(), userId));
		BaseProtocolEnum.addParam(url, "user_ids", user_ids);
		Utility.appendSigUrl(buffer, false);

		String urlText = buffer.toString();
		logger.d("[REQUEST_API M3]\n%s", urlText);
		return urlText;
	}
	
	// 친구신청 수락
	public static String acceptFriendshipRequest(String friendship_request_id, String message) {
		StringBuilder url = Me2dayURL.ACCEPT_FRIENDSHIP_REQUEST.getURL();
		
		BaseProtocolEnum.addParam(url, "friendship_request_id", friendship_request_id);
		BaseProtocolEnum.addParam(url, "message", message);
		
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	// 알림센터-밴드위임 수락/거절
	public static String acceptBandLeadership(String userId, String bandId, String value) {
		StringBuilder url = Me2dayURL.MEMBERSHIP.getURL();
		
		BaseProtocolEnum.addParam(url, "user_id", userId);
		BaseProtocolEnum.addParam(url, "band_id", bandId);
		BaseProtocolEnum.addParam(url, "scope", "accept_leadership");
		BaseProtocolEnum.addParam(url, "value", value);
		
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	// 알림센터-밴드초대 수락/거절
	public static String inviteRequestBand(String userId, String bandId, String value, boolean isExposure) {
		StringBuilder url = Me2dayURL.MEMBERSHIP.getURL();
		
		BaseProtocolEnum.addParam(url, "user_id", userId);
		BaseProtocolEnum.addParam(url, "band_id", bandId);
		BaseProtocolEnum.addParam(url, "scope", "invite_request");
		BaseProtocolEnum.addParam(url, "value", value);
		BaseProtocolEnum.addParam(url, "exposure", isExposure);
		
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	
	// 알림센터-밴드가입신청 수락/거절
	public static String acceptBandMembership(String targetUserId, String bandId, String value, String message) {
		StringBuilder url = Me2dayURL.MEMBERSHIP.getURL();
		
		BaseProtocolEnum.addParam(url, "user_id", targetUserId);
		BaseProtocolEnum.addParam(url, "band_id", bandId);
		BaseProtocolEnum.addParam(url, "scope", "accept_membership");
		BaseProtocolEnum.addParam(url, "message", message);
		BaseProtocolEnum.addParam(url, "value", value);		
		
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	// 알림센터-커버/프로필사진/한줄소개가 변경된 그룹 목록
	public static String noticenterGroupDetails(String id) {
		StringBuilder url = Me2dayURL.NOTICETNER_GROUP_DETAILS.getURL();
		
		BaseProtocolEnum.addParam(url, "id", id);

		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	// 알림센터-band 조회
	public static String getBand(String bandId) {
		StringBuilder url = Me2dayURL.GET_BAND.getURL();
		
		BaseProtocolEnum.addParam(url, "band_id", bandId);

		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}

	// 알림 토큰 갱신 
	public static String setDeviceToken(String deviceToken, String deviceId, String deviceType) {
		StringBuilder url = Me2dayURL.SET_DEVICE_TOKEN.getURL();
		
		BaseProtocolEnum.addParam(url, "device_token", 	deviceToken);
		BaseProtocolEnum.addParam(url, "device_id ", 	deviceId);
		BaseProtocolEnum.addParam(url, "device_type", 	deviceType);
		
		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}

	//공지사항 가져오기
	public static String getBlogPosts(int offSet, int count, String language) {
		StringBuilder url = Me2dayURL.GET_BLOG_POSTS.getURL();
		
		BaseProtocolEnum.addParam(url, "offset", offSet);
		BaseProtocolEnum.addParam(url, "count", count);
		BaseProtocolEnum.addParam(url, "language", language);

		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	//모아보는 설정 가져오기
	public static String getStreamScope() {
		StringBuilder url = Me2dayURL.GET_STREAM_SCOPE.getURL();
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	//모아보는 설정
	public static String setStreamScope(String scope, boolean allowSync, String bandIds) {
		StringBuilder url = Me2dayURL.SET_STREAM_SCOPE.getURL();
		
		BaseProtocolEnum.addParam(url, "scope", scope);
		BaseProtocolEnum.addParam(url, "allow_sync", allowSync);
		BaseProtocolEnum.addParam(url, "stream_band_ids", bandIds);
		
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	//디바이스 토큰설정
	public static String setRegisterNoti(int version, String deviceToken, int deviceType, String deviceId, String deviceTimezone) {
		StringBuilder url = Me2dayURL.REGISTER_NOTI.getURL();
		
		BaseProtocolEnum.addParam(url, "version", version);
		BaseProtocolEnum.addParam(url, "device_token", deviceToken);
		BaseProtocolEnum.addParam(url, "device_type", deviceType);
		BaseProtocolEnum.addParam(url, "device_id", deviceId);
		BaseProtocolEnum.addParam(url, "device_time_zone_offset", deviceTimezone);
		
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	//알림설정
	public static String setNoti(int version, String deviceToken, int deviceType, String deviceId,
								String deviceTimezone, String extParam) {
		StringBuilder url = Me2dayURL.SET_NOTI.getURL();	
		
		BaseProtocolEnum.addParam(url, "version", version);
		BaseProtocolEnum.addParam(url, "device_token", deviceToken);
		BaseProtocolEnum.addParam(url, "device_type", deviceType);
		BaseProtocolEnum.addParam(url, "device_id", deviceId);
		BaseProtocolEnum.addParam(url, "device_time_zone_offset", deviceTimezone);
		url.append(extParam).append("&");
		
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	//알림설정정보
	public static String getNoti(int version, String deviceId) {
		StringBuilder url = Me2dayURL.GET_NOTI.getURL();
		
		BaseProtocolEnum.addParam(url, "version", version);
		BaseProtocolEnum.addParam(url, "device_id", deviceId);
		
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	
	////////////////////// 로그인 유지 ///////////////////////////////
	static public String redirectAfterAuth(String loadUrl) {
		StringBuilder url = Me2dayURL.REDIRECT_AFTER_AUTH.getURL();

		if (!TextUtils.isEmpty(url)) {
			BaseProtocolEnum.addParam(url, "redirect_url", loadUrl);
		} else {
			logger.d("[REQUEST_API] loadUrl 이 없네?");
		}

		Utility.appendSigUrl(url, false);

		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String getSpot(String spotId) {
		StringBuilder url = Me2dayURL.GET_SPOT.getURL();
		
		BaseProtocolEnum.addParam(url, "spot_id", spotId);
				
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
		
	}
	
	

	public static String getStickerGroups(int resolutionType) {
		StringBuilder url = Me2dayURL.STICKER_GROUPS.getURL();

		BaseProtocolEnum.addParam(url, "resolution_type", resolutionType);
		
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String setTimeZone(String timeZone) {
		StringBuilder url = Me2dayURL.SET_TIMEZONE.getURL();

		BaseProtocolEnum.addParam(url, "time_zone", timeZone);
		
		Utility.appendSigUrl(url, true);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String getTimeZone() {
		StringBuilder url = Me2dayURL.COMPARE_TIMEZONE.getURL();

		Utility.appendSigUrl(url, true);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	/*
	 * 
	 * 글카테고리_목록
	 */
	public static String getPostCategory(boolean includeMainCategory, String bandId) {
		StringBuilder url = Me2dayURL.POST_CATEGORY.getURL();
		BaseProtocolEnum.addParam(url, "include_main_category", includeMainCategory);
		BaseProtocolEnum.addParam(url, "band_id", bandId);
		BaseProtocolEnum.addParam(url, ParameterConstants.PARAM_LOCALE, LocaleUtility.getSystemLocaleString(M3Application.getCurrentApplication()));

		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	
	/*
	 * 카테고리 삭제
	 */
	public static String deleteCategory(String aCategory, String categories) {
		StringBuilder url = Me2dayURL.DELETE_CATEGORY.getURL();
		logger.d(">> categories is %s", categories);
		BaseProtocolEnum.addParam(url, "post_group_no", aCategory);
		BaseProtocolEnum.addParam(url, "post_group_nos", categories);
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	
	/*
	 * 
	 * 카테고리 생성
	 */
	public static String addPostCategory(int categoryNo, String categoryName, String iconUrl) {
		StringBuilder url = Me2dayURL.ADD_POST_CATEGORY.getURL();
		
		BaseProtocolEnum.addParam(url, "post_category_no", categoryNo);
		BaseProtocolEnum.addParam(url, "post_group_name", categoryName);
		BaseProtocolEnum.addParam(url, "post_icon_url", iconUrl);
		
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	/*
	 * 
	 * 카테고리 변경
	 */
	public static String changePostCategory(int categoryNo, String postId) {
		StringBuilder url = Me2dayURL.CHANGE_POST_CATEGORY.getURL();
		
		BaseProtocolEnum.addParam(url, "post_group_no", categoryNo);
		BaseProtocolEnum.addParam(url, "post_id", postId);
		
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	/*
	 * 
	 * 카테고리 수정
	 */
	public static String modifyPostCategory(int categoryNo, int groupNo, String groupName, String iconUrl) {
		StringBuilder url = Me2dayURL.MODIFY_POST_CATEGORY.getURL();
		
		BaseProtocolEnum.addParam(url, "post_group_no", groupNo);
		BaseProtocolEnum.addParam(url, "post_category_no", categoryNo);
		BaseProtocolEnum.addParam(url, "post_group_name", groupName);
		BaseProtocolEnum.addParam(url, "post_icon_url", iconUrl);
		
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	//카테고리
	public static String getPostGroup(String includeMainCategory) {
		StringBuilder url = Me2dayURL.GET_LIST.getURL();
		BaseProtocolEnum.addParam(url, "include_main_category", includeMainCategory);
		
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	public static String todayCategoryPosts(int categoryNo, int offset, int count) {
		StringBuilder url = Me2dayURL.TODAY_CATEGORY_POST.getURL();
		BaseProtocolEnum.addParam(url, "category_no", Integer.toString(categoryNo));
		BaseProtocolEnum.addParam(url, "offset", Integer.toString(offset));
		BaseProtocolEnum.addParam(url, "count", Integer.toString(count));
		
		Utility.appendSigUrl(url, false);
		
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	
	//글로벌 회원가입- 아이디 찾기
	public static String getGlobalLoginSearchId(Context context) {
		StringBuilder url = new StringBuilder(BaseProtocolEnum.GLOBAL_LOGIN_SEARCH_ID + "?");
		if (!LocaleUtility.isKoreanLanagage(context)) {
			BaseProtocolEnum.addParam(url, "lang", "en_US");
		}
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	//글로벌 회원가입- 패스워드 찾기
	public static String getGlobalLoginSearchPasswd(Context context) {
		StringBuilder url = new StringBuilder(BaseProtocolEnum.GLOBAL_LOGIN_SEARCH_PASSWORD + "?");
		if (!LocaleUtility.isKoreanLanagage(context)) {
			BaseProtocolEnum.addParam(url, "lang", "en_US");
		}
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}
	//글로벌 회원가입- 가입 
	public static String getGlobalLoginJoin(Context context) {
		StringBuilder url = new StringBuilder(BaseProtocolEnum.GLOBAL_LOGIN_JOIN + "?");	
		BaseProtocolEnum.addParam(url, "todo", "start");
		if (!LocaleUtility.isKoreanLanagage(context)) {
			BaseProtocolEnum.addParam(url, "lang", "en_US");
		}
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}	
	//글로벌 회원가입- 회원정보 
	public static String getGlobalLoginInfo(Context context) {
		StringBuilder url = new StringBuilder(BaseProtocolEnum.GLOBAL_LOGIN_INFO + "?");	
		if (!LocaleUtility.isKoreanLanagage(context)) {
			BaseProtocolEnum.addParam(url, "lang", "en_US");
		}
		String urlText = url.toString();
		BaseProtocolEnum.printLogUrl(urlText);
		return urlText;
	}	
}	/* End of class BaseProtocol */

