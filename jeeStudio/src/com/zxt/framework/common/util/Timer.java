package com.zxt.framework.common.util;

import java.util.Date;
import java.util.Stack;

public class Timer
{
  private static final boolean log = true;
  private static Timer instance = null;
  private long beginTime;
  private long endTime;
  private double distance;
  int count = 0;

  private Stack beginTimes = new Stack();

  public static Timer getInstance()
  {
    if (instance == null)
    {
      instance = new Timer();
    }
    return instance;
  }

  public void begin()
  {
    this.beginTime = new Date().getTime();
    this.beginTimes.push(Long.toString(this.beginTime));
  }

  public double end()
  {
    this.endTime = new Date().getTime();
    if (this.beginTimes.isEmpty())
    {
      return 0.0D;
    }
    this.beginTime = Long.parseLong((String)this.beginTimes.pop());
    this.distance = ((this.endTime - this.beginTime) / 1000.0D);
    return this.distance;
  }

  public void end(String str)
  {
    end();

    System.out.println(str + this.distance);
  }
}