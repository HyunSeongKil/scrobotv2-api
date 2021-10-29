package kr.co.sootechsys.scrobot.misc;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;


/**
 * 유틸
 */
public class Util {


  public static void main(String[] args) {
    String secretKey = "sootechsys.co.kr-0123456789-0123456789-0123456789-0123456789-0123456789-0123456789";

    try {
      System.out.println(encodeAes(secretKey, "password"));
    } catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException
        | BadPaddingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }


  /**
   * SecretKeySpec 생성
   * 
   * @param key 키
   * @return
   * @throws UnsupportedEncodingException
   */
  private static SecretKeySpec createSecretKeySpec(String key) throws UnsupportedEncodingException {
    byte[] keyBytes = new byte[16];
    byte[] b = key.getBytes("UTF-8");
    int len = b.length;
    if (len > keyBytes.length) {
      len = keyBytes.length;
    }
    System.arraycopy(b, 0, keyBytes, 0, len);
    return new SecretKeySpec(keyBytes, "AES");

  }



  /**
   * aes로 암호화
   * 
   * @param key
   * @param plainText
   * @return
   * @throws UnsupportedEncodingException
   * @throws NoSuchAlgorithmException
   * @throws NoSuchPaddingException
   * @throws InvalidKeyException
   * @throws InvalidAlgorithmParameterException
   * @throws IllegalBlockSizeException
   * @throws BadPaddingException
   */
  public static String encodeAes(String key, String plainText)
      throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    SecretKeySpec keySpec = createSecretKeySpec(key);

    Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
    String iv = key.substring(0, 16);
    c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));

    byte[] encrypted = c.doFinal(plainText.getBytes("UTF-8"));
    String enStr = new String(Base64.encodeBase64(encrypted));

    return enStr;
  }



  /**
   * aes로 복호화
   * 
   * @param key
   * @param cipherText
   * @return
   * @throws NoSuchAlgorithmException
   * @throws NoSuchPaddingException
   * @throws InvalidKeyException
   * @throws InvalidAlgorithmParameterException
   * @throws UnsupportedEncodingException
   * @throws IllegalBlockSizeException
   * @throws BadPaddingException
   */
  public static String decodeAes(String key, String cipherText)
      throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
    Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
    String iv = key.substring(0, 16);
    c.init(Cipher.DECRYPT_MODE, createSecretKeySpec(key), new IvParameterSpec(iv.getBytes("UTF-8")));

    byte[] byteStr = Base64.decodeBase64(cipherText.getBytes());

    return new String(c.doFinal(byteStr), "UTF-8");
  }

  public static String getShortUuid() {
    return UUID.randomUUID().toString().split("-")[0];
  }


  public static String join(Collection coll, String deli) {
    StringBuffer sb = new StringBuffer();

    coll.forEach(x -> {
      sb.append(x).append(deli);
    });


    return sb.toString();
  }


  public static boolean isEmpty(Object obj) {
    if (null == obj) {
      return true;
    }


    if (String.class == obj.getClass()) {
      return (0 == ((String) obj).trim().length());
    }

    if (List.class == obj.getClass() || ArrayList.class == obj.getClass()) {
      return 0 == ((List<?>) obj).size();
    }


    throw new RuntimeException("NOT IMPL " + obj.getClass());
  }


  public static String getPkColmnName(String tableName) {
    return tableName + "_pk";
  }
}
