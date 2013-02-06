/*
 * @(#)UserSharedPrefModel.java $$version ${date}
 *
 * Copyright 2007 NHN Corp. All rights Reserved.
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package m2.android.archetype.sharedpref;

import java.util.ArrayList;
import java.util.List;

import m2.android.archetype.util.StringUtility;
import android.content.Context;
import android.location.Location;

import com.nhn.android.archetype.base.object.BaseObj;

public class UserSharedPrefModel extends BaseSharedPrefModel {
	
	
	public static final int PHOTOFOLDER_TYPE_SD = 0;
	public static final int PHOTOFOLDER_TYPE_CACHE = 1;
	
	public static final int PHOTO_UPLOAD_QUALITY_SPEED = 0; // w640 // default 설정
	public static final int PHOTO_UPLOAD_QUALITY_NORMAL = 1; // w1280
	public static final int PHOTO_UPLOAD_QUALITY_ORIGINAL = 2; // 원본

	private static final String PREF_KEY_NAME = "USER"; 

	private static final String KEY_FULL_AUTH_TOKEN = "full_auth_token";
	private static final String KEY_USER_ID = "user_id";
	private static final String KEY_LOGIN_TYPE = "login_type";
	private static final String KEY_INPUT_USER_ID = "input_user_id";
	private static final String KEY_INPUT_PASSWORD = "input_password";
	
	private static final String KEY_LOGIN_PROCESS = "loginProcess";
	private static final String KEY_ONLY_LOGIN_PROCESS = "loginOnlyProcess";
	private static final String KEY_ONLY_LOGIN_PROCESS_SUB = "loginOnlyProcessSub";
	private static final String KEY_REGISTER_AFTER_PROCESS = "registerAfterProcess";
	private static final String KEY_REGISTER_PROFILE_PHOTO_PATH = "registerProfilePhotoPath";
	private static final String KEY_IS_CONFIRM_GEOINFO = "isConfirmGeoInfo";
	private static final String KEY_UPDATE_FINISH = "updateFinish";
	private static final String KEY_LAST_LINK_URL = "lastLinkUrl";
	private static final String KEY_HOT_SPOTS = "hotSpots";
	
	// User 정보 
	private static final String KEY_USER_OPENID = "openid";
	private static final String KEY_USER_NICKNAME = "nickname";
	private static final String KEY_USER_FACE = "face";
	private static final String KEY_USER_DESCRIPTION = "description";
	private static final String KEY_USER_HOMEPAGE = "homepage";
	private static final String KEY_USER_EMAIL = "email";
	private static final String KEY_USER_CELLPHONE = "cellphone";
	private static final String KEY_USER_MESSENGER = "messenger";
	private static final String KEY_USER_REALNAME = "realname";
	private static final String KEY_USER_BIRTHDAY = "birthday";
	private static final String KEY_USER_LOCATION = "location";
	private static final String KEY_USER_CELEBRITY = "celebrity";
	private static final String KEY_USER_ME2DAYHOME = "me2dayHome";
	private static final String KEY_USER_RSSDAILY = "rssDaily";
	private static final String KEY_USER_INVITEDBY = "invitedBy";
	private static final String KEY_USER_FRIENDSCOUNT = "friendsCount";
	private static final String KEY_USER_PINMECOUNT = "pinMeCount";
	private static final String KEY_USER_RELATIONSHIP = "relationship";
	private static final String KEY_USER_UPDATED = "updated";
	private static final String KEY_USER_TOTALPOSTS = "totalPosts";
	private static final String KEY_USER_REGISTERED = "registered";
	private static final String KEY_USER_POSTICONS = "postIcons";
	private static final String KEY_USER_BANDS = "bands";
	private static final String KEY_USER_FRIENDGROUP = "friendGroup";
	private static final String KEY_USER_AUTOCOMPLETE_FRIENDGROUP = "autocompleteFriendGroup";
	private static final String KEY_USER_AUTOACCEPT = "autoAccept";
	private static final String KEY_USER_SEX = "sex";
	private static final String KEY_USER_STREAM_DATA_TYPE = "streamDataType";
	private static final String KEY_USER_STREAM_DATA_SCOPE_PARAM = "streamDataScopeParam";
	private static final String KEY_USER_STREAM_SELECTED_MENU = "streamSelectedMenu";
	private static final String	KEY_USER_COVER_URL	= "coverImageUrl";

	private static final String KEY_LAST_CONTACTS_COUNT = "last_contacts_count";
	private static final String KEY_NOTICE_CHECK_DATE 	= "notice_check_date";
	private static final String KEY_PHOTO_SAVE_TYPE 	= "photo_save_type";
	private static final String KEY_PHOTO_UPLOAD_QUALITY = "photo_upload_quality";
	
	private static final String KEY_TODAY_KEYWORD = "today_keyword";	
	private static final String KEY_USER_KEYWORD = "user_keyword";	
	private static final String KEY_USER_KEYWORD_COUNT = "user_keyword_count";	
	private static final String KEY_KEYBOARD_HEIGHT = "keyboard_height";
	
	private static final String KEY_ORIGINFACE = "originface";
	private static final String KEY_ORIGIN_FACE_THUMB = "orgin_face_thumb";
	private static final String KEY_COUNTPHOTO = "countPhoto";
	private static final String KEY_COUNTTHEME = "countTheme";
	private static final String KEY_COUNTMETOO = "countMetoo";
	
	private static final String KEY_ISO3166CODE_FROM_SERVER = "iso3166code_from_server";
	
	private static UserSharedPrefModel instance = new UserSharedPrefModel();
	
	private UserSharedPrefModel() {
	}
	
	
	public static UserSharedPrefModel get() {
		return instance;
	}
	

	@Override
	public String getPrefName() {
		return PREF_KEY_NAME;
	}

	@Override
	public int getPrefMode() {
		return Context.MODE_PRIVATE;
	}

	// Full Auth Token
	public String getFullAuthToken() {
		return (String) get(KEY_FULL_AUTH_TOKEN, "");
	}

	public void setFullAuthToken(String param) {
		put(KEY_FULL_AUTH_TOKEN, param);
	}
	
	public boolean isLogin() {
		String fullAuthToken = getFullAuthToken();
		if (StringUtility.isNotNullOrEmpty(fullAuthToken)) {
			return true;
		} else {
			return false;
		}
	}
	

	// 유저 커버 이미지
	public String getCoverImageUrl() {
		return (String) get(KEY_USER_COVER_URL, "");
	}
	
	public void setCoverImageUrl(String param) {
		put(KEY_USER_COVER_URL, param);
	}
	
	// 유저 아이디
	public String getUserId() {
		return (String) get(KEY_USER_ID, "");
	}

	public void setUserId(String param) {
		put(KEY_USER_ID, param);
	}

	// 입력된 유저 아이디
	public String getInputUserId() {
		return (String) get(KEY_INPUT_USER_ID, "");
	}

	public void setInputUserId(String param) {
		put(KEY_INPUT_USER_ID, param);
	}

	// 입력된 유저 패스워드
	public String getInputPassword() {
		return (String) get(KEY_INPUT_PASSWORD, "");
	}

	public void setInputPassword(String param) {
		put(KEY_INPUT_PASSWORD, param);
	}

	// 로그인 타입
	public String getLoginType() {
		return (String) get(KEY_LOGIN_TYPE, "");
	}

	public void setLoginType(String param) {
		put(KEY_LOGIN_TYPE, param);
	}

	
	public int getPhotoFolderType() {
		return (Integer) get(KEY_PHOTO_SAVE_TYPE, PHOTOFOLDER_TYPE_SD);
	}
	
	public void setPhotoFolderType(int type) {
		put(KEY_PHOTO_SAVE_TYPE, type);
	}
	
	public int getPhotoUploadQuality() {
		return (Integer) get(KEY_PHOTO_UPLOAD_QUALITY, PHOTO_UPLOAD_QUALITY_NORMAL);
	}
	
	public void setPhotoUploadQuality(int type) {
		put(KEY_PHOTO_UPLOAD_QUALITY, type);
	}

	// 마지막으로 로드한 Phonebook timestamp
	public Integer getLastContactsCount() {
		return (Integer) get(KEY_LAST_CONTACTS_COUNT, 0);
	}

	public void setLastContactsCount(Integer param) {
		put(KEY_LAST_CONTACTS_COUNT, param);
	}
	
	// 공지사항 확인 시간
	public String getNoticeCheckDate() {
		return (String)get(KEY_NOTICE_CHECK_DATE);
	}

	public void setNoticeCheckDate(String timeStamp) {
		put(KEY_NOTICE_CHECK_DATE, timeStamp);
	}
	
	
	/** 로그인, 회원가입에서 사용 */
	public Boolean isOnlyLoginProcess() {
		return (Boolean) get(KEY_ONLY_LOGIN_PROCESS, false);
	}
	
	public void setOnlyLoginProcess(Boolean value) {
		put(KEY_ONLY_LOGIN_PROCESS, value);
	}

	public Boolean isOnlyLoginProcessSub() {
		return (Boolean) get(KEY_ONLY_LOGIN_PROCESS_SUB, false);
	}
	
	public void setOnlyLoginProcessSub(Boolean value) {
		put(KEY_ONLY_LOGIN_PROCESS_SUB, value);
	}

	public Boolean isLoginProcess() {
		return (Boolean) get(KEY_LOGIN_PROCESS, false);
	}
	
	public void setLoginProcess(Boolean value) {
		put(KEY_LOGIN_PROCESS, value);
	}
	
	// 회원가입 후 처리할 일이 있는지 확인용
	public Boolean getRegister_AfterProcess() {
		return (Boolean) get(KEY_REGISTER_AFTER_PROCESS, false);
	}
	
	public void setRegister_AfterProcess(Boolean value) {
		put(KEY_REGISTER_AFTER_PROCESS, value);
	}
	
	// 회원가입 후 업로드 할 포토 경로
	public String getRegister_ProfilePhotoPath() {
		return (String) get(KEY_REGISTER_PROFILE_PHOTO_PATH, "");
	}
	
	// 
	public void setRegister_ProfilePhotoPath(String value) {
		put(KEY_REGISTER_PROFILE_PHOTO_PATH, value);
	}	

	// 
	public Boolean getIsConfirmGeoInfo(String loginId) {
		String key = String.format("%s_%s", KEY_IS_CONFIRM_GEOINFO, loginId);
		return (Boolean) get(key, 0);
	}
	
	// 
	public void setIsConfirmGeoInfo(String loginId, boolean value) {
		String key = String.format("%s_%s", KEY_IS_CONFIRM_GEOINFO, loginId);
		put(key, value);
	}

	// 키보드 높이 값
	public int getKeyboardHeight() {
		return (Integer) get(KEY_KEYBOARD_HEIGHT, 0);
	}

	public void setKeyboardHeight(int height) {
		put(KEY_KEYBOARD_HEIGHT, height);
	}	
	
	// 
	public Boolean getUpdateFinish() {
		return (Boolean) get(KEY_UPDATE_FINISH, false);
	}
	
	// 
	public void setUpdateFinish(Boolean value) {
		put(KEY_UPDATE_FINISH, value);
	}

	public String getLastLinkUrl() {
		return (String) get(KEY_LAST_LINK_URL);
	}
	
	// 
	public void setLastLinkUrl(String url) {
		put(KEY_LAST_LINK_URL, url);
	}
	


	public String getOpenid() {
		return (String) get(KEY_USER_OPENID);
	}

	public void setOpenid(String openid) {
		put(KEY_USER_OPENID, openid);
	}
	
	
	public String getNickname() {
		return (String) get(KEY_USER_NICKNAME);
	}

	public void setNickname(String nickname) {
		put(KEY_USER_NICKNAME, nickname);
	}
	
	
	public String getFace() {
		return (String) get(KEY_USER_FACE);
	}

	public void setFace(String face) {
		put(KEY_USER_FACE, face);
	}
	
	
	public String getDescription() {
		return (String) get(KEY_USER_DESCRIPTION);
	}

	public void setDescription(String description) {
		put(KEY_USER_DESCRIPTION, description);
	}
	
	
	public String getHomepage() {
		return (String) get(KEY_USER_HOMEPAGE);
	}

	public void setHomepage(String homepage) {
		put(KEY_USER_HOMEPAGE, homepage);
	}
	
	
	public String getEmail() {
		return (String) get(KEY_USER_EMAIL);
	}

	public void setEmail(String email) {
		put(KEY_USER_EMAIL, email);
	}
	
	
	public String getCellphone() {
		return (String) get(KEY_USER_CELLPHONE);
	}

	public void setCellphone(String cellphone) {
		put(KEY_USER_CELLPHONE, cellphone);
	}
	
	
	public String getMessenger() {
		return (String) get(KEY_USER_MESSENGER);
	}

	public void setMessenger(String messenger) {
		put(KEY_USER_MESSENGER, messenger);
	}
	
	
	public String getRealname() {
		return (String) get(KEY_USER_REALNAME);
	}

	public void setRealname(String realname) {
		put(KEY_USER_REALNAME, realname);
	}
	
	
	public String getBirthday() {
		return (String) get(KEY_USER_BIRTHDAY);
	}

	public void setBirthday(String birthday) {
		put(KEY_USER_BIRTHDAY, birthday);
	}
	

	
	public String getOrginFaceThumb() {
		return (String) get(KEY_ORIGIN_FACE_THUMB);
	}
	
	// 
	public void setOriginFaceThumb(String url) {
		put(KEY_ORIGIN_FACE_THUMB, url);
	}
	

	
	public int getCountPhoto() {
		if (get(KEY_COUNTPHOTO) != null) {
			return (Integer) get(KEY_COUNTPHOTO);
		} else {
			return 0;
		}
	}

	public void setCountPhoto(int countPhoto) {
		put(KEY_COUNTPHOTO, countPhoto);
	}
	
	
	public int getCountTheme() {
		if (get(KEY_COUNTTHEME) != null) {
			return (Integer) get(KEY_COUNTTHEME);
		} else {
			return 0;
		}
	}

	public void setCountTheme(int countTheme) {
		put(KEY_COUNTTHEME, countTheme);
	}
	
	
	public int getCountMetoo() {
		if (get(KEY_COUNTMETOO) != null) {
			return (Integer) get(KEY_COUNTMETOO);
		} else {
			return 0;
		}
	}

	public void setCountMetoo(int countMetoo) {
		put(KEY_COUNTMETOO, countMetoo);
	}
	
	public String getMe2dayHome() {
		return (String) get(KEY_USER_ME2DAYHOME);
	}

	public void setMe2dayHome(String me2dayHome) {
		put(KEY_USER_ME2DAYHOME, me2dayHome);
	}
	
	
	public String getRssDaily() {
		return (String) get(KEY_USER_RSSDAILY);
	}

	public void setRssDaily(String rssDaily) {
		put(KEY_USER_RSSDAILY, rssDaily);
	}
	
	
	public String getInvitedBy() {
		return (String) get(KEY_USER_INVITEDBY);
	}

	public void setInvitedBy(String invitedBy) {
		put(KEY_USER_INVITEDBY, invitedBy);
	}
	
	
	public int getFriendsCount() {
		return (Integer) get(KEY_USER_FRIENDSCOUNT, 0);
	}

	public void setFriendsCount(int friendsCount) {
		put(KEY_USER_FRIENDSCOUNT, friendsCount);
	}
	
	
	public int getPinMeCount() {
		return (Integer) get(KEY_USER_PINMECOUNT, 0);
	}

	public void setPinMeCount(int pinMeCount) {
		put(KEY_USER_PINMECOUNT, pinMeCount);
	}
	
	
	public String getRelationship() {
		return (String) get(KEY_USER_RELATIONSHIP);
	}

	public void setRelationship(String relationship) {
		put(KEY_USER_RELATIONSHIP, relationship);
	}
	
	
	public String getUpdated() {
		return (String) get(KEY_USER_UPDATED);
	}

	public void setUpdated(String updated) {
		put(KEY_USER_UPDATED, updated);
	}
	
	
	public int getTotalPosts() {
		return (Integer) get(KEY_USER_TOTALPOSTS, 0);
	}

	public void setTotalPosts(int totalPosts) {
		put(KEY_USER_TOTALPOSTS, totalPosts);
	}
	
	
	public String getRegistered() {
		return (String) get(KEY_USER_REGISTERED);
	}

	public void setRegistered(String registered) {
		put(KEY_USER_REGISTERED, registered);
	}
	
	

	public void setBands(String bandsJson) {
		put(KEY_USER_BANDS, bandsJson);
	}
	

	public boolean getAutoAccept() {
		return (Boolean) get(KEY_USER_AUTOACCEPT);
	}

	public void setAutoAccept(boolean autoAccept) {
		put(KEY_USER_AUTOACCEPT, autoAccept);
	}
	
	
	public String getSex() {
		return (String) get(KEY_USER_SEX);
	}

	public void setSex(String sex) {
		put(KEY_USER_SEX, sex);
	}
	
	public int getStreamDataType() {
		return (Integer) get(KEY_USER_STREAM_DATA_TYPE, 0);
	}

	public void setStreamDataType(int dataType) {
		put(KEY_USER_STREAM_DATA_TYPE, dataType);
	}
	
	public String getStreamDataScopeParam() {
		return (String) get(KEY_USER_STREAM_DATA_SCOPE_PARAM);
	}

	public void setStreamDataScopeParam(String scopeParam) {
		put(KEY_USER_STREAM_DATA_SCOPE_PARAM, scopeParam);
	}
	public int getStreamSelectedMenu() {
		return (Integer) get(KEY_USER_STREAM_SELECTED_MENU, 0);
	}

	public void setStreamSelectedMenu(int selectedMenu) {
		put(KEY_USER_STREAM_SELECTED_MENU, selectedMenu);
	}
	

	//서버로부터 받아놓은 IP로 식별된 ISO3166 코드
	public String getIso3166CodeFromServer() {
		return (String) get(KEY_ISO3166CODE_FROM_SERVER, "");
	}

	public void setIso3166CodeFromServer(String param) {
		put(KEY_ISO3166CODE_FROM_SERVER, param);
	}
		
}