/**
* Copyright 2010 zxt Co. Ltd.
* All right reserved.
* 
*/
package com.zxt.framework.common.exceptions;
/**
 * Title: ExceptionFormatter
 * Description:  
 * Create DateTime: 2010-9-10
 * @author xxl
 * @since v1.0
 * 
 */

public class ExceptionFormatter
{
  private String filter = "gov.mof";
  private int traceDepth = 3;
  private Exception _e;
  private StackTraceElement _stElement;
  private StackTraceElement[] _stElements;
  private String _className;
  private String _fileName;
  private String _methodName;
  private int _lineNumber;

  public ExceptionFormatter(Exception e)
  {
    this._e = e;
    this._stElements = getStackElements();
    this._stElement = this._stElements[0];
    if (this._stElement == null)
      return;
    this._fileName = this._stElement.getFileName();
    this._className = this._stElement.getClassName();
    this._methodName = this._stElement.getMethodName();
    this._lineNumber = this._stElement.getLineNumber();
  }

  public String getErrorMessage()
  {
    return this._e.getMessage();
  }

  public String getClassName()
  {
    return this._className;
  }

  public String getFileName()
  {
    return this._fileName;
  }

  public String getMethodName()
  {
    return this._methodName;
  }

  public int getLineNumber()
  {
    return this._lineNumber;
  }

  public String getStackMessage()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("¥ÌŒÛ‘≠“Ú£∫").append(this._e.getMessage()).append("\n");
    StackTraceElement stElement = null;
    for (int i = 0; i < this._stElements.length; ++i)
    {
      stElement = this._stElements[i];
      if (stElement == null)
      {
        break;
      }

      sb.append(" ‘⁄ ").append(stElement).append("\n");
    }

    return sb.toString();
  }

  public String toString()
  {
    return getStackMessage();
  }

  private StackTraceElement[] getStackElements()
  {
    StackTraceElement[] stElements = this._e.getStackTrace();
    StackTraceElement[] returnStElements = new StackTraceElement[this.traceDepth];
    StackTraceElement stElement = null;
    int i = 0; for (int j = 0; (i < stElements.length) && (j < this.traceDepth); ++i)
    {
      stElement = stElements[i];

      if (!stElement.getClassName().startsWith(this.filter))
        continue;
      returnStElements[j] = stElement;
      ++j;
    }

    return returnStElements;
  }
}