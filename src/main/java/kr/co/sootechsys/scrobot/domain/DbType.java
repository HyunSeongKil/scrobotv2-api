package kr.co.sootechsys.scrobot.domain;

public enum DbType {
  MYSQL("MYSQL"), MARIA_DB("MARIA_DB");

  private String value;


  DbType(String dbType) {
    this.value = dbType;
  }

  public String getValue() {
    return this.value;
  }
}
