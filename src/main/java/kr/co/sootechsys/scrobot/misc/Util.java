package kr.co.sootechsys.scrobot.misc;

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
}
