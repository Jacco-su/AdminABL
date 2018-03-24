package com.dream.socket.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

public class Des {
  private static final String PASSWORD_CRYPT_KEY = "TTMJ_123";
  private static final String ALGORITHM = "DES";

  public Des() {
  }

  public static final byte[] encrypt(byte[] data, byte[] key) throws Exception {
    SecureRandom sr = new SecureRandom();
    DESKeySpec dks = new DESKeySpec(key);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    SecretKey securekey = keyFactory.generateSecret(dks);
    Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
    cipher.init(1, securekey, sr);
    return cipher.doFinal(data);
  }

  public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    DESKeySpec keySpec = new DESKeySpec(key);
    SecretKey securekey = keyFactory.generateSecret(keySpec);
    Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
    cipher.init(2, securekey);
    return cipher.doFinal(data);
  }
}
