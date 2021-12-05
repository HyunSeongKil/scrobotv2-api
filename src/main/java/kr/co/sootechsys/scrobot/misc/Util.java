package kr.co.sootechsys.scrobot.misc;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
      System.out.println("sha512\t" + "plainText:password" + "\tcipherText:" + sha512("password"));
    } catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
        | InvalidAlgorithmParameterException | IllegalBlockSizeException
        | BadPaddingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * typescript용 코드 생성 - FormGroup
   * 
   * @param dtoClass dto클래스
   * @return
   */
  public static String createFormGroupString(Class<?> dtoClass) {
    StringBuffer sb = new StringBuffer();

    sb.append(" this.form = new FormGroup({");

    Field[] fields = dtoClass.getDeclaredFields();
    for (Field f : fields) {
      sb.append(" " + f.getName() + " : new FormControl(''),");
    }
    sb.append("});");

    return sb.toString();
  }

  /**
   * typescript용 코드 생성 - export interface Item
   * 
   * @param dtoClass dto클래스
   * @return
   */
  public static String createItemString(Class<?> dtoClass) {
    StringBuffer sb = new StringBuffer();
    sb.append("export interface " + dtoClass.getSimpleName() + " { ");

    Field[] fields = dtoClass.getDeclaredFields();
    for (Field f : fields) {
      sb.append(" " + f.getName() + " : ");

      if (String.class == f.getType() || Date.class == f.getType()) {
        sb.append(" string;");
      } else if (Integer.class == f.getType() || Long.class == f.getType() || Double.class == f.getType()) {
        sb.append(" number;");
      } else {
        sb.append(" any;");
      }
    }

    sb.append(" };");

    return sb.toString();
  }

  /**
   * sha512 암호화
   * 
   * @param plainText 평문
   * @return 암호문
   */
  public static String sha512(String plainText) {
    try {
      // getInstance() method is called with algorithm SHA-512
      MessageDigest md = MessageDigest.getInstance("SHA-512");

      // digest() method is called
      // to calculate message digest of the input string
      // returned as array of byte
      byte[] messageDigest = md.digest(plainText.getBytes());

      // Convert byte array into signum representation
      BigInteger no = new BigInteger(1, messageDigest);

      // Convert message digest into hex value
      String hashtext = no.toString(16);

      // Add preceding 0s to make it 32 bit
      while (hashtext.length() < 32) {
        hashtext = "0" + hashtext;
      }

      // return the HashText
      return hashtext;
    }

    // For specifying wrong message digest algorithms
    catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
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
      throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
      InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
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
      throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException,
      UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
    Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
    String iv = key.substring(0, 16);
    c.init(Cipher.DECRYPT_MODE, createSecretKeySpec(key), new IvParameterSpec(iv.getBytes("UTF-8")));

    byte[] byteStr = Base64.decodeBase64(cipherText.getBytes());

    return new String(c.doFinal(byteStr), "UTF-8");
  }

  public static String getShortUuid() {
    return UUID.randomUUID().toString().split("-")[0];
  }

  /**
   * 파일 확장자 추출
   * 
   * @param filename 파일 명
   * @return
   */
  public static Optional<String> getFileExt(String filename) {
    return Optional.ofNullable(filename).filter(f -> f.contains("."))
        .map(f -> f.substring(filename.lastIndexOf(".") + 1));
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
