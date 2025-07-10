package Utility;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class EnDeCrypt {

	private static String key = "Bar12345Bar12345Bar12345Bar12345";

	public static String encrypt(final String pInput) {
		String encryptedtext = null;
		try {
			final SecretKeySpec aesKey = new SecretKeySpec(key.getBytes(), "AES");
			final Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			final byte[] encrypted = cipher.doFinal(pInput.getBytes());
			encryptedtext = DatatypeConverter.printBase64Binary(encrypted);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedtext;
	}

	public static String dencrypt(final String pInput) {
		String decrypted = null;
		try {
			final SecretKeySpec aesKey = new SecretKeySpec(key.getBytes(), "AES");
			final Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			final byte[] encrypted = DatatypeConverter.parseBase64Binary(pInput);
			decrypted = new String(cipher.doFinal(encrypted));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decrypted;
	}
}
