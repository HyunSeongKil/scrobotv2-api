package kr.co.sootechsys.scrobot.misc;

import java.util.UUID;

public class Util {
  
  public static String getShortUuid(){
    return UUID.randomUUID().toString().split("-")[0];
  }
}
