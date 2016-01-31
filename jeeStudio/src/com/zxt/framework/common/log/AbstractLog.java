package com.zxt.framework.common.log;

import java.io.Serializable;

public abstract class AbstractLog
  implements Serializable
{
  protected int logid;
  protected String username;
  protected String functionname;
  protected String datetime;
  protected String message;
  protected String remoteip;

  public String getDatetime()
  {
    return this.datetime;
  }

  public void setDatetime(String datetime) {
    this.datetime = datetime;
  }

  public String getFunctionname() {
    return this.functionname;
  }

  public void setFunctionname(String functionname) {
    this.functionname = functionname;
  }

  public int getLogid() {
    return this.logid;
  }

  public void setLogid(int logid) {
    this.logid = logid;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message)
  {
    if ((message != null) && (message.length() > 100)) {
      message = message.substring(0, 100);
    }
    this.message = message;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String user) {
    this.username = user;
  }

  public String getRemoteip() {
    return this.remoteip;
  }

  public void setRemoteip(String remoteip) {
    this.remoteip = remoteip;
  }
}