package m2.android.archetype.util;

import m2.android.archetype.base.BaseConstants;
import m2.android.archetype.base.Me2dayApplication;
import m2.android.archetype.config.AppBuildCheckFlag;
import m2.android.archetype.sharedpref.UserSharedPrefModel;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class CellphoneNumberUtility {
	private static Logger logger = Logger.getLogger(CellphoneNumberUtility.class);
	
	public static final int INDEX_BY_COUNTRY_CODE 		= 1;
	public static final int INDEX_BY_ISO_3166_CODE 		= 2;
	public static final int INDEX_BY_ISO_LOCALE_INFO 		= 3;
	
	/**
	 * 아래의 정보들을 순차적으로 저장하는 enum 값 정의
	 * ISO-3166 Alpha Code
	 * Country Number 
	 * Locale 
	 * CountryName
	 */
	public static enum CountryType {
		CountryType1	("KR",	"82",	"ko-KR", "Korea Republic"),
		CountryType2	("CA",	"1",	"en-CA", "Canada"),
		CountryType3	("US",	"1",	"en-US", "USA"),
		CountryType4	("GB",	"44",	"en-GB", "United Kingdom"),
		CountryType5	("JP",	"81",	"ja-JP", "Japan"),
		CountryType6	("CN",	"86",	"zh-CN", "China"),
		CountryType7	("RU",	"7",	"ru-RU", "Russia"),
		CountryType8	("FR",	"33",	"fr-FR", "France"),
		CountryType9	("ES",	"34",	"es-ES", "Spain"), //ca-ES
		CountryType10	("IT",	"39",	"it-IT", "Italy"),
		CountryType11	("DE",	"49",	"de-DE", "Germany"),
		CountryType12	("AU",	"61",	"en-AU", "Australia"),
		CountryType13	("PH",	"63",	"en-PH", "Philippines"),
		CountryType14	("NZ",	"64",	"en-NZ", "New Zealand"),
		CountryType15	("SG",	"65",	"en-SG", "Singapore"),  //zh-SG
		CountryType16	("TH",	"66",	"th-TH", "Thailand"),
		CountryType17	("TR",	"90",	"tr-TR", "Turkey"),
		CountryType18	("HK",	"852",	"zh-HK", "Hong Kong"),
		CountryType19	("TW",	"886",	"zh-TW", "Taiwan"),
		CountryType20	("KZ",	"7",	"kk-KZ", "Kazakhstan"),
		CountryType21	("ZA",	"27",	"en-ZA", "South Africa Republic"),
		CountryType22	("NL",	"31",	"nl-NL", "Netherlands"),
		CountryType23	("AT",	"43",	"de-AT", "Austria"),
		CountryType24	("MX",	"52",	"es-MX", "Mexico"),
		CountryType25	("AR",	"54",	"es-AR", "Argentina"),
		CountryType26	("BR",	"55",	"pt-BR", "Brazil"),
		CountryType27	("CL",	"56",	"es-CL", "Chile"),
		CountryType28	("ID",	"62",	"in-ID", "Indonesia"),
		CountryType29	("VN",	"84",	"vi-VN", "Vietnam"),
		CountryType30	("IN",	"91",	"en-IN", "India"),  //hi-IN 
		CountryType31	("PT",	"351",	"pt-PT", "Portugal"),
		CountryType32	("SA",	"966",	"ar-SA", "Saudi Arabia"),
		CountryType33	("UZ",	"998",	"uz-Cyrl-UZ", "Uzbekistan"), //uz-UZ, uz-Latn-UZ
		CountryType34	("EG",	"20",	"ar-EG", "Egypt"),
		CountryType35	("HU",	"36",	"hu-HU", "Hungary"),
		CountryType36	("CH",	"41",	"fr-CH", "Switzerland"), //de-CH, it-CH
		CountryType37	("DK",	"45",	"da-DK", "Denmark"),
		CountryType38	("SE",	"46",	"sv-SE", "Sweden"),
		CountryType39	("NO",	"47",	"no-NO", "Norway"),
		CountryType40	("PL",	"48",	"pl-PL", "Poland"),
		CountryType41	("MY",	"60",	"ms-MY", "Malaysia"),
		CountryType42	("PK",	"92",	"ur-PK", "Pakistan"),  //en-PK
		CountryType43	("FI",	"358",	"fi-FI", "Finland"),
		CountryType44	("UA",	"380",	"uk-UA", "Ukraine"),
		CountryType45	("CZ",	"420",	"cs-CZ", "Czech"),
		CountryType46	("GT",	"502",	"es-GT", "Guatemala"),
		CountryType47	("EC",	"593",	"es-EC", "Ecuador"),
		CountryType48	("PY",	"595",	"es-PY", "Paraguay"),
		CountryType49	("MO",	"853",	"en-US", "Macao"),    //NULL
		CountryType50	("KH",	"855",	"km-KH", "Cambodia"), 
		CountryType51	("BD",	"880",	"bn-BD", "Bangladesh"),	
		CountryType52	("AE",	"971",	"ar-AE", "UAE"),
		CountryType53	("QA",	"974",	"ar-QA", "Qatar"),
		CountryType54	("KG",	"996",	"ky-KG", "Kyrgizstan"), 
		CountryType55	("GR",	"30",	"el-GR", "Greece"),
		CountryType56	("BE",	"32",	"nl-BE", "Belgium"),  //fr-BE
		CountryType57	("RO",	"40",	"ro-RO", "Rumania"),   
		CountryType58	("CO",	"57",	"es-CO", "Colombia"),
		CountryType59	("NG",	"234",	"en-NG", "Nigeria"),  //ig-NG
		CountryType60	("IE",	"353",	"en-IE", "Ireland"),  //ga-IE 
		CountryType61	("BY",	"375",	"be-BY", "Belarus"),
		CountryType62	("SK",	"421",	"sk-SK", "Slovakia"),
		CountryType63	("CR",	"506",	"es-CR", "Costa Rica"),
		CountryType64	("FJ",	"679",	"en-US", "Fiji"),  //NULL
		CountryType65	("LA",	"856",	"lo-LA", "Laos"),  
		CountryType66	("KW",	"965",	"ar-KW", "Kuwait"),
		CountryType67	("IL",	"972",	"iw-IL", "Israel"),
		CountryType68	("MN",	"976",	"mn-MN", "Monglia"), 
		CountryType69	("MA",	"212",	"ar-MA", "Morocco"),
		CountryType70	("BN",	"673",	"ms-BN", "Brunei"),
		CountryType71	("KE",	"254",	"sw-KE", "Kenya");  //en-KE  
		
		private final String iso3166AlphaCode;
		private final String countryCode;
		private final String localeInfo;
		private final String countryName;

		private CountryType(String iso3166AlphaCode, String countryCode, String localeInfo, String countryName) {
			this.iso3166AlphaCode = iso3166AlphaCode;
			this.countryCode = countryCode;
			this.localeInfo = localeInfo;
			this.countryName = countryName;
		}

		public String getIso3166AlphaCode() {
			return this.iso3166AlphaCode;
		}
		
		public String getCountryCode() {
			return countryCode;
		}
		
		public String getLocaleInfo() {
			return this.localeInfo;
		}
		
		public String getCountryName() {
			return this.countryName;
		}
		
		public static CountryType getCountryType(int position) {
			if (position < 0) {
				position = 0;
			}
			CountryType[] typeArr = CountryType.values();
			
			try {
				return typeArr[position];
			} catch (Exception e) {
				return typeArr[0];
			}
		}
	}
	
	public static int getCountryTypeLength() {
		return CountryType.values().length;
	}
	
	public static CountryType getCountryType(int position) {
		return CountryType.getCountryType(position - 1);
	}
	
	public static CountryType getCountryType(String countryCode) {
		int idx = getCountryIndex(INDEX_BY_COUNTRY_CODE, countryCode);
		return getCountryType(idx);
	}
	
	public static String getIso3166AlphaCode(int index) {
		return getCountryType(index).getIso3166AlphaCode();
	}

	public static String getCountryCode(int index) {
		return getCountryType(index).getCountryCode();
	}
	
	
	public static String getLocaleInfo(int index) {
		return getCountryType(index).getLocaleInfo();
	}
	
	public static String getCountryName(int index) {
		return getCountryType(index).getCountryName();
	}
	
	public static int getCountryIndex(int type, String param) {
		int retVal = 1;
		
		if (StringUtility.isNotNullOrEmpty(param)) {
			for (int i = 0; i < CountryType.values().length; i++) {
				CountryType countryType =  CountryType.getCountryType(i);
				String temp = null;
				
				if (type == INDEX_BY_COUNTRY_CODE) {
					temp = countryType.getCountryCode();
				} else if (type == INDEX_BY_ISO_3166_CODE) {
					temp = countryType.getIso3166AlphaCode();
				} else if (type == INDEX_BY_ISO_LOCALE_INFO) {
					temp = countryType.getLocaleInfo();
				}
				
				if (param.equalsIgnoreCase(temp)) {
					retVal = i + 1;
					break;
				} 
			}
		}
		return retVal;
	}
	
	/////////////////////////////////////////////////
	
	/** TODO
	 * 로그인 여부에 상관없이 상황에 맞게 최선을 다해서 국가번호를 끄집어내 전달
	 * 1단계 : 사용자가 입력했던 국가번호(전화번호)가 최우선
	 * 2단계 : 국가번호가 없으면, 통신사 MCC 코드로 국가번호 역으로 추출
	 * 3단계 : 이래도 국가번호가 없으면, 서버에 IP 기반 국가번호 질의
	 * 4단계 : 그래도 국가번호가 없으면, 로케일 정보로 국가번호 추출 
	 * @return 국가번호, 예)82, 81, 1, 852
	 */
	public static String whatIsMyCountryCode(boolean includePlusChar) {
		UserSharedPrefModel user = UserSharedPrefModel.get();
		String countryCode = null;
		
		//1단계 : 사용자가 입력했던 국가번호(전화번호)가 최우선
		String userCellphone = user.getCellphone();
		//logger.d("whatIsMyCountryInfo() userCellphone (%s, %s)", userCellphone, includePlusChar);
		if (StringUtility.isNotNullOrEmpty(userCellphone)) {
			countryCode = CellphoneNumberUtility.pickOutCountryCode(userCellphone, false);
			if (StringUtility.isNotNullOrEmpty(countryCode)) {
				//logger.d("whatIsMyCountryInfo() countryCode by cellphone (%s)", countryCode);
				if (includePlusChar) {
					countryCode = "+" + countryCode;
				} 
				return countryCode;
			}
		}
		
		//2단계 : 국가번호가 없으면, 통신사 MCC 코드로 국가번호 역으로 추출
		String iso3166AlphaCode = CellphoneNumberUtility.getIso3166AlphaCodeByMCC();
		//logger.d("whatIsMyCountryInfo() iso3166AlphaCode (%s)", iso3166AlphaCode);
		if (StringUtility.isNotNullOrEmpty(iso3166AlphaCode)) {
			countryCode = CellphoneNumberUtility.getCountryCodeByIso3166AlphaCode(iso3166AlphaCode);
			if (StringUtility.isNotNullOrEmpty(countryCode)) {
				//logger.d("whatIsMyCountryInfo() countryCode by iso3166AlphaCode (%s)", countryCode);
				if (includePlusChar) {
					countryCode = "+" + countryCode;
				} 
				return countryCode;
			}
		}
		
		//3단계 : 이래도 국가번호가 없으면, 서버에 IP 기반 국가번호 질의
		String iso3166AlphaCodeFromServer = user.getIso3166CodeFromServer();
		//logger.d("whatIsMyCountryInfo() iso3166AlphaCodeFromServer (%s)", iso3166AlphaCodeFromServer);
		if (StringUtility.isNotNullOrEmpty(iso3166AlphaCodeFromServer)) {
			countryCode = CellphoneNumberUtility.getCountryCodeByIso3166AlphaCode(iso3166AlphaCodeFromServer);
			if (StringUtility.isNotNullOrEmpty(countryCode)) {
				//logger.d("whatIsMyCountryInfo() countryCode by iso3166AlphaCodeFromServer (%s)", countryCode);
				if (includePlusChar) {
					countryCode = "+" + countryCode;
				} 
				return countryCode;
			}
		}
		return null;
	}
	
	/** TODO
	 * 로그인 여부에 상관없이 상황에 맞게 최선을 다해서 ISO3166 Alpha-2 코드를 끄집어내 전달
	 * 1단계 : 사용자가 입력했던 국가번호(전화번호)로 찾기
	 * 2단계 : 국가번호가 없으면, 통신사 MCC 코드로 찾기
	 * 3단계 : 이래도 국가번호가 없으면, 서버에서 IP 기반으로 찾기
	 * 4단계 : 그래도 국가번호가 없으면, 로케일 정보로 찾기
	 * @param afterRegister : 가입전 or 가입후 여부
	 * @return ISO3166 Alpha-2 코드, 예) KR, US, JP, CN, TH
	 */
//	public static String whatIsMyIso3166Code(boolean afterRegister) {
//		if (AppBuildCheckFlag.DEBUG_MODE) {
//			if (StringUtility.isNotNullOrEmpty(AppBuildCheckFlag.DEBUG_LOCALE)) {
//				return AppBuildCheckFlag.DEBUG_LOCALE;
//			}
//		}
//		
//		UserPreference user = UserPreference.get();
//		String iso3166Code = null;
//		String countryCode = null;
//		
//		//1단계 : 사용자가 입력했던 국가번호(전화번호)로 찾기
//		String userCellphone = user.getCellphone();
//		//logger.d("whatIsMyIso3166Code() getCellphone (%s, %s)", userCellphone, afterRegister);
//		if (StringUtility.isNotNullOrEmpty(userCellphone)) {
//			countryCode = CellphoneNumberUtility.pickOutCountryCode(userCellphone, false);
//			if (StringUtility.isNotNullOrEmpty(countryCode)) {
//				//logger.d("whatIsMyIso3166Code() iso3166Code by cellphone (%s)", iso3166Code);
//				iso3166Code = CellphoneNumberUtility.getIso3166AlphaCodeByCountryCode(countryCode);
//				if (StringUtility.isNotNullOrEmpty(iso3166Code)) {
//					return iso3166Code.toUpperCase();
//				}
//			}
//		}
//		
//		//2단계 : 국가번호가 없으면, 통신사 MCC 코드로 찾기
//		iso3166Code = CellphoneNumberUtility.getIso3166AlphaCodeByMCC();
//		//logger.d("whatIsMyIso3166Code() getIso3166AlphaCodeByMCC (%s)", iso3166Code);
//		if (StringUtility.isNotNullOrEmpty(iso3166Code)) {
//			return iso3166Code.toUpperCase();
//		}
//		
//		//단말에서 ISO3166 코드를 알수 없을때, 서버쪽에 IP 기반으로 찾아주라고 요청할지 체크
//		//회원 가입시점에 단말에서 해당 코드를 찾아낼수 있는지 여부를 체크하기 위해 아래 if문 적용
//		//여기까지 왔다는건 앞단계에서 못찾았다는 의미
//		if (afterRegister == false) {
//			return null;
//		}
//		
//		//3단계 : 이래도 국가번호가 없으면, 서버에서 IP 기반으로 찾기
//		iso3166Code = user.getIso3166CodeFromServer();
//		//logger.d("whatIsMyCountryInfo() getIso3166CodeFromServer (%s)", iso3166Code);
//		if (StringUtility.isNotNullOrEmpty(iso3166Code)) {
//			return iso3166Code.toUpperCase();
//		}
//		return null;
//	}
	
	/**
	 * 로컬 포맷 처리 
	 * @param param 
	 * @return
	 */	
	public static String formattedNumberByCountryCode(String param) {
		//logger.d("nationalFormattedNumber(), BEFORE(%s)", param);
		
		String formattedPhoneNum = "";
		String countryCode = CellphoneNumberUtility.pickOutCountryCode(param, true);
		String myCountryCode = CellphoneNumberUtility.pickOutCountryCode(UserSharedPrefModel.get().getCellphone(), true);
		
		//logger.d("nationalFormattedNumber(), countryCode(%s) myCountryCode(%s)", countryCode, myCountryCode);
		//국가코드가 있을 경우
		if (StringUtility.isNotNullOrEmpty(countryCode)) {
			CountryType countryType = CellphoneNumberUtility.getCountryType(Utility.getOnlyNumber(countryCode));
			
			if (countryCode.equals(myCountryCode)) {
				//로그인한 사용자와 국가코드가 같을 경우
				formattedPhoneNum = doFormattedNumber(param, countryType.getIso3166AlphaCode(), false);	
			}  else {
				if (StringUtility.isNullOrEmpty(myCountryCode) && countryCode.equals(BaseConstants.COUNTRY_CODE_KOREA)) {
					//최초 로그인 사용자는 저장된 자신의 번호가 없으므로 사용자가 입력한 국가코드만 봐서,
					//만약 한국이면, 한국식 포맷팅 처리
					formattedPhoneNum = doFormattedNumber(param, countryType.getIso3166AlphaCode(), false);	
				} else {
					//로그인한 사용자와 국가코드가 다를 경우 > "+"만 붙여서 처리
					//logger.d("nationalFormattedNumber(), ---> INTERNATIONAL");
					param = "+" + param;
					formattedPhoneNum = doFormattedNumber(param, countryType.getIso3166AlphaCode(), true);	
				}
			}
		} else {
			//국가코드가 없거나 지원되는 국가코드가 아니면 넘어온 인자 그대로 다시 넘겨줌 
			formattedPhoneNum = param;
		}
		
		//logger.d("nationalFormattedNumber(), AFTER(%s)", formattedPhoneNum);
		return formattedPhoneNum;
	}
	
	private static String doFormattedNumber(String param, String iso3166AlphaCode, boolean isInternational) {
		//logger.d("doFormattedNumber(%s, %s, %s)", param, iso3166AlphaCode, isInternational);
		
		String formattedPhoneNum = "";
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance(); 
		
		try {   
			PhoneNumber numberProto = phoneUtil.parse(param, iso3166AlphaCode); 
			if (isInternational) {
				formattedPhoneNum = phoneUtil.format(numberProto, PhoneNumberFormat.INTERNATIONAL);
			} else {
				formattedPhoneNum = phoneUtil.format(numberProto, PhoneNumberFormat.NATIONAL);
			}
		} catch (NumberParseException e) {  
			e.printStackTrace();
			formattedPhoneNum = "+" + param; 
		}
		
		//logger.d("doFormattedNumber() formattedPhoneNum(%s)", formattedPhoneNum);
		return formattedPhoneNum;
	}

	/**
	 * 단말에 저장된 번호를 리턴
	 * 만약, 저장된 번호가 이미 국가번호를 포함하고 있을 경우, 국가번호를 제외하고 리턴
	 * 최종적으로 앞쪽의 0은 모두 제거했다가 다시 붙여줌. 
	 * @param countryCode : +가 포함된 국가코드
	 * @param useOrigin
	 * @return
	 */
	public static String getMyPhoneNumber(String countryCode, boolean useOrigin) {
		String finalNum = null;
		
		TelephonyManager mTelephonyMgr = (TelephonyManager) Me2dayApplication.getCurrentApplication().getSystemService(Context.TELEPHONY_SERVICE);
		String origPhoneNumber = mTelephonyMgr.getLine1Number();

		//logger.d("getMyPhoneNumber(), countryCode(%s) useOrigin (%s) origPhoneNumber (%s)", countryCode, useOrigin, origPhoneNumber);
		//SIM Card에 번호가 저장되어 있지 않은 경우 또는 단순 Play 전용의 단말일 경우
		if (StringUtility.isNullOrEmpty(origPhoneNumber) || StringUtility.isNullOrEmpty(countryCode)) {
			return null;
		}

		// 폰에 저장된 번호 그대로를 리턴
		if (useOrigin) {
			return origPhoneNumber;
		}

		String countryCodeWithoutPlusChar = Utility.getOnlyNumber(countryCode);
		String iso3661AlphaCode = CellphoneNumberUtility.getIso3166AlphaCodeByCountryCode(countryCodeWithoutPlusChar);
		String cellphoneOnlyNumber = Utility.getOnlyNumber(origPhoneNumber);
		
		//logger.d("getMyPhoneNumber(), BEFORE countryCodeWithoutPlusChar(%s) cellphoneOnlyNumber(%s)", countryCodeWithoutPlusChar, cellphoneOnlyNumber);
		
		try {
			if (cellphoneOnlyNumber.startsWith(countryCodeWithoutPlusChar)) {
				cellphoneOnlyNumber = cellphoneOnlyNumber.substring(countryCodeWithoutPlusChar.length());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		finalNum = getPhoneNumberNationalFormat(iso3661AlphaCode, cellphoneOnlyNumber, true);
		//logger.d("getMyPhoneNumber(), My phonenumber is (%s)", finalNum);
		return finalNum;
	}
	
	/**
	 * 휴대폰 번호에서 국가코드 추출해서 리턴
	 * @param cellphone '+'문자가 제거된 국제전화번호, 예) 821033001234
	 * @return 추출된 국가번호, 예) +82, or 82
	 */
	public static String pickOutCountryCode(String cellphone, boolean includePlusChar) {
		String foundCode = null;
		//logger.d("pickOutCountryCode(), cellphone(%s) includePlusChar(%s)", cellphone, includePlusChar);
		if (StringUtility.isNullOrEmpty(cellphone) || cellphone.length() < 6) {
			//logger.d("pickOutCountryCode(), cellphone is invalid.");
			return foundCode;
		}

		//숫자만 다시 재구성
		cellphone = Utility.getOnlyNumber(cellphone);
		//logger.d("pickOutCountryCode(%s)", cellphone);
		
		for (int i = 0; i < CellphoneNumberUtility.CountryType.values().length; i++) {
			CountryType cType = CellphoneNumberUtility.CountryType.getCountryType(i);
			String code = cType.getCountryCode();
			if (code.equals(cellphone.substring(0, code.length()))) {
				if (includePlusChar) {
					foundCode = "+" + cType.getCountryCode();
				} else {
					foundCode = cType.getCountryCode();
				}
				break;
			}
		}
		
		//logger.d("pickOutCountryCode() foundCode(%s)", foundCode);
		return foundCode;
	}
	
	/**
	 * 국가코드에 매칭되는 ISO 3166 코드를 추출
	 * @param countryCode
	 * @return
	 */
	public static String getIso3166AlphaCodeByCountryCode(String countryCode) {
		//logger.d("getIso3166AlphaCodeByCountryCode() countryCode (%s)", countryCode);
		String iso3166AlphaCode = null;
		
		if (StringUtility.isNotNullOrEmpty(countryCode)) {
			countryCode = Utility.getOnlyNumber(countryCode);
			
			for (int i = 0; i < CountryType.values().length; i++) {
				CountryType countryType =  CountryType.getCountryType(i);
				if (countryCode.equalsIgnoreCase(countryType.getCountryCode())) {
					iso3166AlphaCode = countryType.getIso3166AlphaCode();
					break;
				}
			}
		}
		
		//logger.d("getIso3166AlphaCodeByCountryCode() iso3166AlphaCode (%s)", iso3166AlphaCode);
		return iso3166AlphaCode;
	}
	
	/** TODO
	 * ISO3166 Alpha Code에 매칭되는 국가코드 리턴
	 * @param iso3166AlphaCode : 국가코드, 예) KR
	 * @return 국가번호 예) 82
	 */
	public static String getCountryCodeByIso3166AlphaCode(String iso3166AlphaCode) {
		String countryCode = null;
		
		if (StringUtility.isNotNullOrEmpty(iso3166AlphaCode)) {
			for (int i = 0; i < CountryType.values().length; i++) {
				CountryType countryType =  CountryType.getCountryType(i);
				if (iso3166AlphaCode.equalsIgnoreCase(countryType.getIso3166AlphaCode())) {
					countryCode = countryType.getCountryCode();
					break;
				}
			}
		}
		return countryCode;
	}
	
	/**
	 * Locale 정보에 매칭되는 국가코드 리턴
	 * @param localeInfo : 국가코드, 예) KR
	 * @return 국가번호 예) 82
	 */
	public static String getCountryCodeByLocale(String locale) {
		String countryCode = null;
		
		if (StringUtility.isNotNullOrEmpty(locale)) {
			for (int i = 0; i < CountryType.values().length; i++) {
				CountryType countryType =  CountryType.getCountryType(i);
				if (locale.equalsIgnoreCase(countryType.getLocaleInfo())) {
					countryCode = countryType.getCountryCode();
					break;
				}
			}
		}
		return countryCode;
	}
	
	/**
	 * MCC(Mobile Country Code) 이용해서 시스템의 ISO 국가코드 리턴
	 * Returns the ISO country code equivalent of the current registered operator's MCC (Mobile Country Code). 
	 * Availability: Only when user is registered to a network. 
	 * Result may be unreliable on CDMA networks (use getPhoneType() to determine if on a CDMA network).
	 * @return
	 */
	public static String getIso3166AlphaCodeByMCC() {
		String retVal = null;
		try {
			Me2dayApplication bandApp = Me2dayApplication.getCurrentApplication();
	        TelephonyManager telMgr = (TelephonyManager) bandApp.getSystemService(Context.TELEPHONY_SERVICE); 
	        //현재 등록된 망 사업자의 MCC(Mobile Country Code)에 대한 ISO 국가코드 반환
	    	retVal = telMgr.getNetworkCountryIso().toUpperCase();  
	    	//logger.d("getIso3166AlphaCodeByMCC(%s)", retVal);
		} catch (Exception e) {
			e.printStackTrace();
		} 
        return retVal;
	}
	
	/**
	 * 전화번호 유효성 체크 
	 * @param iso3166AlphaCode : ISO 3166-1 two-letter country code, ex)KR, JP
	 * @param phoneNumber : 로컬 or 국제 전화번호, ex) 01036263505 or 821036263505
	 * @return
	 */
	public static boolean isValidPhoneNumber(String iso3166AlphaCode, String phoneNumber) {
		//logger.d("isValidPhoneNumber(%s, %s)", iso3166AlphaCode, phoneNumber);
		boolean retVal = false;
		
		if (StringUtility.isNullOrEmpty(iso3166AlphaCode) || StringUtility.isNullOrEmpty(phoneNumber)) {
			logger.w("isValidPhoneNumber(), phoneNumber or iso3166AlphaCode is null");
			return retVal;
		}
		
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance(); 
		PhoneNumber numberProto = null;
		
		try {
			numberProto = phoneUtil.parse(phoneNumber, iso3166AlphaCode);
		} catch (NumberParseException e) {
			e.printStackTrace();
			return retVal;
		}
		
		if (numberProto == null) {
			return retVal;
		}
		
		retVal = phoneUtil.isValidNumber(numberProto);
		//logger.d("isValidPhoneNumber(), retVal(%s)", retVal);
		return retVal;
	}

	/**
	 * 모바일 번호 여부 체크
	 * @param iso3166AlphaCode
	 * @param phoneNumber
	 * @return
	 */
	public static boolean isMobilePhoneNumber(String iso3166AlphaCode, String phoneNumber) {
		//logger.d("isMobilePhoneNumber(%s, %s)", iso3166AlphaCode, phoneNumber);
		boolean retVal = false;
		
		if (StringUtility.isNullOrEmpty(iso3166AlphaCode) || StringUtility.isNullOrEmpty(phoneNumber)) {
			logger.w("isMobilePhoneNumber(), phoneNumber or iso3166AlphaCode is null");
			return retVal;
		}
		
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance(); 
		PhoneNumber numberProto = null;
		
		try {
			numberProto = phoneUtil.parse(phoneNumber, iso3166AlphaCode);
		} catch (NumberParseException e) {
			e.printStackTrace();
			return retVal;
		}
		
		if (numberProto == null) {
			return retVal;
		}
		
		PhoneNumberType type = phoneUtil.getNumberType(numberProto);
		if (type == PhoneNumberType.MOBILE || type == PhoneNumberType.FIXED_LINE_OR_MOBILE) {
			retVal = true;
		}
		
		//logger.d("isValidPhoneNumber(), retVal(%s)", retVal);
		return retVal;
	}

	/**
	 * National(Local) 포맷의 전화번호 추출
	 * @param iso3166AlphaCode : ISO 3166-1 two-letter country code, ex)KR, JP
	 * @param phoneNumber : 로컬 or 국제 전화번호, ex) 01036263505 or 821036263505
	 * @return National(Local) 포맷의 전화번호 리턴 예) (267) 333-1234
	 */
	public static String getPhoneNumberNationalFormat(String iso3166AlphaCode, String phoneNumber, boolean onlyNumber) {
		//logger.d("getPhoneNumberNationalFormat(%s, %s, %s)", iso3166AlphaCode, phoneNumber, onlyNumber);
		if (StringUtility.isNullOrEmpty(iso3166AlphaCode) || StringUtility.isNullOrEmpty(phoneNumber)) {
			logger.w("getPhoneNumberNationalFormat(), phoneNumber or iso3166AlphaCode is null");
			return null;
		}
				
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance(); 
		try {   
			PhoneNumber numberProto = phoneUtil.parse(phoneNumber, iso3166AlphaCode);
			String retNumber = phoneUtil.format(numberProto, PhoneNumberFormat.NATIONAL);

			if (onlyNumber) {
				phoneNumber = Utility.getOnlyNumber(retNumber);
			} else {
				phoneNumber = retNumber;
			}
		} catch (NumberParseException e) {
			logger.e(e);
		}
		
		//logger.d("getPhoneNumberNationalFormat() phoneNumber(%s)", phoneNumber);
		return phoneNumber;
	}
	
	/**
	 * 요놈은 국제전화 포맷(821076882222)의 전화번호에서 로컬 전화번호만 추출해 내는 함수
	 * @param phoneNumber
	 * @return
	 */
	public static String getPhoneNumberNationalFormat(String phoneNumber) {
		//logger.d("getPhoneNumberNationalFormat(%s)", phoneNumber);
		if (StringUtility.isNullOrEmpty(phoneNumber)) {
			logger.w("getPhoneNumberNationalFormat(), phoneNumber is null");
			return null;
		}
				
		String countryCode = CellphoneNumberUtility.pickOutCountryCode(phoneNumber, true);
		if (StringUtility.isNullOrEmpty(countryCode)) {
			logger.w("getPhoneNumberNationalFormat(), countryCode is null");
			return phoneNumber;
		}
		
		String iso3166Code = CellphoneNumberUtility.getIso3166AlphaCodeByCountryCode(countryCode);
		if (StringUtility.isNullOrEmpty(iso3166Code)) {
			logger.w("getPhoneNumberNationalFormat(), iso3166Code is null");
			return phoneNumber;
		}
		
		String localPhone = CellphoneNumberUtility.getPhoneNumberNationalFormat(iso3166Code, phoneNumber, true);
		//logger.d("getPhoneNumberNationalFormat() localPhone(%s)", localPhone);
		return localPhone;
	}
	
	
	
	/**
	 * International 포맷의 전화번호 추출
	 * @param iso3166AlphaCode : ISO 3166-1 two-letter country code, ex)KR, JP
	 * @param phoneNumber : 로컬 or 국제 전화번호, ex) 01036263505 or 821036263505
	 * @return International 포맷의 전화번호 리턴 예) +1 267-333-1234
	 */
	public static String getPhoneNumberInternationalFormat(String iso3166AlphaCode, String phoneNumber) {
		//logger.d("getPhoneNumberInternationalFormat(%s, %s)", iso3166AlphaCode, phoneNumber);
		if (StringUtility.isNullOrEmpty(iso3166AlphaCode) || StringUtility.isNullOrEmpty(phoneNumber)) {
			logger.w("getPhoneNumberInternationalFormat(), phoneNumber or iso3166AlphaCode is null");
			return null;
		}
				
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance(); 
		try {   
			PhoneNumber numberProto = phoneUtil.parse(phoneNumber, iso3166AlphaCode);
			return phoneUtil.format(numberProto, PhoneNumberFormat.INTERNATIONAL);
		} catch (NumberParseException e) {
			logger.e(e);
		}
		
		return phoneNumber;
	}
	
	/**
	 * E164 포맷의 전화번호 추출
	 * @param iso3166AlphaCode : ISO 3166-1 two-letter country code, ex)KR, JP
	 * @param phoneNumber : 로컬 or 국제 전화번호, ex) 01036263505 or 821036263505
	 * @Param includePlusChar : 리턴시 + 문자 포함여부
	 * @return E164 포맷의 전화번호에서 +만 제거후 리턴 예) 821036263505
	 */
	public static String getPhoneNumberE164Format(String iso3166AlphaCode, String phoneNumber, boolean includePlusChar) {
		logger.d("getPhoneNumberE164Format(%s, %s)", iso3166AlphaCode, phoneNumber);
		if (StringUtility.isNullOrEmpty(iso3166AlphaCode) || StringUtility.isNullOrEmpty(phoneNumber)) {
			logger.w("getPhoneNumberE164Format(), phoneNumber or iso3166AlphaCode is null");
			return null;
		}
		
		String e164Number = null;
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance(); 
		try {   
			PhoneNumber numberProto = phoneUtil.parse(phoneNumber, iso3166AlphaCode);
			if (includePlusChar) {
				e164Number = phoneUtil.format(numberProto, PhoneNumberFormat.E164);
			} else {
				e164Number = Utility.getOnlyNumber(phoneUtil.format(numberProto, PhoneNumberFormat.E164));
			}
		} catch (NumberParseException e) {
			e164Number= null;
			logger.e(e);
		}
		
		return e164Number;
	}
	
	/**
	 * 국가코드에 매칭되는 국가명 리턴
	 * @param countryCode
	 * @return
	 */
	public static String getCountryNameByCountryCode(String countryCode) {
		logger.d("getCountryNameByCountryCode() countryCode (%s)", countryCode);
		String countryName = null;
		
		if (StringUtility.isNotNullOrEmpty(countryCode)) {
			countryCode = Utility.getOnlyNumber(countryCode);
			
			for (int i = 0; i < CountryType.values().length; i++) {
				CountryType countryType =  CountryType.getCountryType(i);
				if (countryCode.equalsIgnoreCase(countryType.getCountryCode())) {
					countryName = countryType.getCountryName();
					break;
				}
			}
		}
		
		logger.d("getCountryNameByCountryCode() countryName (%s)", countryName);
		return countryName;
	}
	/**
	 * 로컬 포맷 처리 
	 * @param param 
	 * @return
	 */	
	public static String nationalFormattedNumber(String param) {
		//logger.d("nationalFormattedNumber(), BEFORE(%s)", param);
		
		String formattedPhoneNum = "";
		String countryCode = CellphoneNumberUtility.pickOutCuntryCode(param);
		
		UserSharedPrefModel model = UserSharedPrefModel.get();
		String myCountryCode = CellphoneNumberUtility.pickOutCuntryCode(model.getCellphone());
		
		//logger.d("nationalFormattedNumber(), countryCode(%s) myCountryCode(%s)", countryCode, myCountryCode);
		//국가코드가 있을 경우
		if (Utility.isNotNullOrEmpty(countryCode)) {
			CountryType countryType = CellphoneNumberUtility.getCountryType(countryCode);
			
			if (countryCode.equals(myCountryCode)) {
				//로그인한 사용자와 국가코드가 같을 경우
				formattedPhoneNum = doFormattedNumber(param, countryType.getIso3166AlphaCode(), false);	
			}  else {
				if (Utility.isNullOrEmpty(myCountryCode) && countryCode.equals(BaseConstants.COUNTRY_CODE_KOREA)) {
					//최초 로그인 사용자는 저장된 자신의 번호가 없으므로 사용자가 입력한 국가코드만 봐서,
					//만약 한국이면, 한국식 포맷팅 처리
					formattedPhoneNum = doFormattedNumber(param, countryType.getIso3166AlphaCode(), false);	
				} else {
					//로그인한 사용자와 국가코드가 다를 경우 > "+"만 붙여서 처리
					//logger.d("nationalFormattedNumber(), ---> INTERNATIONAL");
					param = "+" + param;
					formattedPhoneNum = doFormattedNumber(param, countryType.getIso3166AlphaCode(), true);	
				}
			}
		} else {
			//국가코드가 없거나 지원되는 국가코드가 아니면 넘어온 인자 그대로 다시 넘겨줌 
			formattedPhoneNum = param;
		}
		
		//logger.d("nationalFormattedNumber(), AFTER(%s)", formattedPhoneNum);
		return formattedPhoneNum;
	}
	/**
	 * 휴대폰 번호에서 국가코드 추출해서 리턴
	 * 
	 * @param cellphone '+'문자가 제거된 국제전화번호, 예) 821033001234
	 * @return 추출된 국가번호, 예) +82, or 82
	 */
	public static String pickOutCuntryCode(String cellphone) {
		String foundCode = null;

		if (Utility.isNullOrEmpty(cellphone) || cellphone.length() < 6) {
			logger.d("pickOutCuntryCode(), cellphone is invalid.");
			return foundCode;
		}

		//숫자만 다시 재구성
		cellphone = Utility.getOnlyNumber(cellphone);
		//logger.d("pickOutCuntryCode(%s)", cellphone);
		
		for (int i = 0; i < CellphoneNumberUtility.CountryType.values().length; i++) {
			CountryType cType = CellphoneNumberUtility.CountryType.getCountryType(i);
			String code = cType.getCountryCode();
			if (code.equals(cellphone.substring(0, code.length()))) {
				foundCode = "+" + cType.getCountryCode();
				break;
			}
		}
		
		//logger.d("pickOutCuntryCode() foundCode(%s)", foundCode);
		return foundCode;
	}
}