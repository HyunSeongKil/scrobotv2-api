package kr.co.sootechsys.scrobot.domain;


/**
 * 열거형
 */
public enum DbProduct {
  MySQL("MySQL"), MariaDB("MariaDB");

  private String value;


  DbProduct(String dbType) {
    this.value = dbType;
  }

  public String getValue() {
    return this.value;
  }
}
