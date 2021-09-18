package kr.co.sootechsys.scrobot.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class Util {

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
