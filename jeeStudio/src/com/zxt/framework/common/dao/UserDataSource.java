package com.zxt.framework.common.dao;

public class UserDataSource
{
  private String loginDS;
  private String currentDS;
  private int loginYear;
  private int currentYear;

  public void reset()
  {
    this.currentDS = this.loginDS;
    this.currentYear = this.loginYear;
  }

  public String getLoginDS()
  {
    return this.loginDS;
  }

  public void setLoginDS(String loginDS)
  {
    this.loginDS = loginDS;
    this.currentDS = loginDS;
    String[] s = loginDS.split(";");
    this.loginYear = new Integer(s[0]).intValue();
    this.currentYear = this.loginYear;
  }

  public String getCurrentDS()
  {
    return this.currentDS;
  }

  public void setCurrentDS(String currentDS)
  {
    this.currentDS = currentDS;
    String[] s = currentDS.split(";");
    this.currentYear = new Integer(s[0]).intValue();
  }

  public int getLoginYear()
  {
    return this.loginYear;
  }

  public void setLoginYear(int loginYear)
  {
    this.loginDS = this.loginDS.replaceAll(String.valueOf(this.loginYear), String.valueOf(loginYear));
    this.loginYear = loginYear;
    this.currentYear = this.loginYear;
  }

  public int getCurrentYear()
  {
    return this.currentYear;
  }

  public void setCurrentYear(int currentYear)
  {
    this.currentDS = this.currentDS.replaceAll(String.valueOf(this.currentYear), String.valueOf(currentYear));
    this.currentYear = currentYear;
  }
}