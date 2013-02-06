package m2.android.archetype.util;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import m2.android.archetype.base.BaseConstants;

import com.nhn.android.archetype.base.util.Base64Utility;

/**
 * 암호 문자열을 암호화/복호화하기 위한 유틸 클래스
 * Usage:
 * - 암호화 : String crypto = CryptoUtility.encrypt(masterpassword, cleartext)
 * - 복호화 : String cleartext = CryptoUtility.decrypt(masterpassword, crypto)
 * @author ferenc.hechler
 */
public class CryptoUtility {

	private static Logger logger = Logger.getLogger(CryptoUtility.class);

	public static String encrypt(String seed, String cleartext) throws Exception {
		byte[] rawKey = getRawKey(seed.getBytes());
		byte[] result = encrypt(rawKey, cleartext.getBytes());
		return toHex(result);
	}

	public static String decrypt(String seed, String encrypted) throws Exception {
		byte[] rawKey = getRawKey(seed.getBytes());
		byte[] enc = toByte(encrypted);
		byte[] result = decrypt(rawKey, enc);
		return new String(result);
	}

	private static byte[] getRawKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(seed);
		kgen.init(128, sr); // 192 and 256 bits may not be available
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		return raw;
	}

	private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	public static String toHex(String txt) {
		return toHex(txt.getBytes());
	}

	public static String fromHex(String hex) {
		return new String(toByte(hex));
	}

	public static byte[] toByte(String hexString) {
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++) {
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
		}
		return result;
	}

	public static String toHex(byte[] buf) {
		if (buf == null) {
			return "";
		}

		StringBuffer result = new StringBuffer(2 * buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}
		return result.toString();
	}

	private final static String HEX = "0123456789ABCDEF";
	private static void appendHex(StringBuffer sb, byte b) {
		sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
	}

	/**
	 * Master Password로 사용되는 Seed 값을 userid 문자열과 이 문자열을 hash 처리한 값을 조합으로 생성
	 * @param userId
	 * @return
	 */
	public static String createCryptoSeed(String userId) {
		return userId + userId.hashCode();
	}

	//// MOVED FROM Utility.java
	////////////////////////////////////////////////////////////////////

	public static String base64(String value) {
		Base64Utility base64 = new Base64Utility(false);
		String encodeValue = base64.encode(value);
		return encodeValue;
	}

	//private -> public 으로 임시 변경
	public static String md5(String value) {
		try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] messageDigest = md.digest(value.getBytes());
	        BigInteger number = new BigInteger(1, messageDigest);
	        String md5 = number.toString(16);

	        while (md5.length() < 32) {
				md5 = "0" + md5;
			}
	        return md5;

	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	/**
	 * 생성된 Credential Key 문자열을 RSA 알고리즘을 이용하여 암호화 한후,
	 * Base64 스트링으로 인코딩하고, 다시 URL Escaping 까지 완료하여 Credential 문자열을 생성한다.
	 * @param token
	 * @return
	 */
	public static String generateCredential(String token) {

		String rsaStr = null;
		byte[] input = token.getBytes();

		BigInteger modulus  = new BigInteger(1, hexStringToByteArray(BaseConstants.RSA_MODULUS));
		BigInteger exponent = new BigInteger(1, hexStringToByteArray(BaseConstants.RSA_EXPONENT));

		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(modulus, exponent);

			//Build the public Key by means of the modulus and exponent specs
			Key pubKey = keyFactory.generatePublic(pubKeySpec);
			//logger.d("encodeCredentialUsingRsa(), pubKey (%s)", pubKey);

			//Indicate the cipher to use the pubKey which has just been built
			Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);

			//Encrypt with the public key
			byte[] output = cipher.doFinal(input);

			//Convert RSA String to Base64 String
 			//Android 2.2 later 에서만 가능, 2.1에서는 java.lang.NoClassDefFoundError: android.util.Base64 발생
			//String base64Str = Base64.encodeToString(output, Base64.DEFAULT); //Success Code

			//그래서 아래와 같이 로직바꿈. Android 2.1
			String base64Str = "";
			Base64Utility base64 = new Base64Utility(false);
			base64Str = base64.encode(output);
			//base64Str = Base64Coder.encodeBytes(output, Base64Coder.NO_OPTIONS);
			//logger.d("encodeCredentialUsingRsa(), base64Str  (%s)", base64Str);

			//For URL Escaping
//			rsaStr = URLEncoder.encode(base64Str);
			rsaStr = base64Str;

		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			return rsaStr;
		} catch (BadPaddingException e) {
			e.printStackTrace();
			return rsaStr;
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
			return rsaStr;
		} catch (NoSuchPaddingException e1) {
			e1.printStackTrace();
			return rsaStr;
		} catch (InvalidKeyException e1) {
			e1.printStackTrace();
			return rsaStr;
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return rsaStr;
	}

	private static byte[] hexStringToByteArray(String hexStr) {
		int len = hexStr.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte)((Character.digit(hexStr.charAt(i), 16) << 4)
					+ Character.digit(hexStr.charAt(i + 1), 16));
		}
		return data;
	}
	
	public static String getCredential_OTP(String sessionToken, String otpServer, String otpInput) {
		String credential = null;

		//삼성 갤럭시탭에서 아래의 코드에서 NullPointerException 발생을 리포팅하여 예외처리 추가
		try {
			logger.d("Called getCredential(), otpServer=%s, otpInput=%s", otpServer, otpInput);

			StringBuffer credentialKey = new StringBuffer();

			credentialKey.append(String.format("%c", sessionToken.length()));
			credentialKey.append(sessionToken);

			credentialKey.append(String.format("%c", otpServer.length()));
			credentialKey.append(otpServer);

			credentialKey.append(String.format("%c", otpInput.length()));
			credentialKey.append(otpInput);

			//생성된 Credential Key 문자열을 RSA 알고리즘을 이용하여 암호화 한후,
			//Base64 스트링으로 인코딩하고, 다시 URL Escaping 까지 완료하여
			//Credential 문자열을 생성한다.
			credential = generateCredential(credentialKey.toString());
//			Utility.d(category, "*** Encoded credential=" + credential);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return credential;
	}
	
	public static String getCredential(String sessionToken, String userid, String password) {
		String credential = null;

		//삼성 갤럭시탭에서 아래의 코드에서 NullPointerException 발생을 리포팅하여 예외처리 추가
		try {
			logger.d("Called getCredential(), userid=%s, password=%s", userid, password);

			StringBuffer credentialKey = new StringBuffer();

			credentialKey.append(String.format("%c", sessionToken.length()));
			credentialKey.append(sessionToken);

			credentialKey.append(String.format("%c", userid.length()));
			credentialKey.append(userid);

			credentialKey.append(String.format("%c", password.length()));
			credentialKey.append(password);

			//생성된 Credential Key 문자열을 RSA 알고리즘을 이용하여 암호화 한후,
			//Base64 스트링으로 인코딩하고, 다시 URL Escaping 까지 완료하여
			//Credential 문자열을 생성한다.
			credential = generateCredential(credentialKey.toString());
//			Utility.d(category, "*** Encoded credential=" + credential);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return credential;
	}
}
