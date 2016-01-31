package com.zxt.framework.common.dao;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.zxt.framework.common.exceptions.AppRuntimeException;


public class MultiDataSource implements DataSource
{
  private static Logger log = LogManager.getLogger(MultiDataSource.class);
  
  private static String dsDefault;
  private static ThreadLocal contextHolder = new InheritableThreadLocal();
  private static Map dataSources;
  private static Map dsAlias;

  public void setDsDefault(String dsDefaultNew)
  {
    dsDefault = dsDefaultNew;
  }

  public Connection getConnection()
    throws SQLException
  {
    DataSource targetDS = getDataSourceByYear();
    return targetDS.getConnection();
  }

  public DataSource getDataSourceByYear()
  {
    UserDataSource user = getUserDataSource();

    DataSource ds = getDataSourceByYear(user.getLoginDS());
    if (ds != null)
      return ds;
    //log
    return getDefaultDataSource();
  }

  public DataSource getDataSourceByYear(String year)
  {
    return (DataSource)dataSources.get(year);
  }

  public DataSource getDefaultDataSource()
  {
    if ((dsDefault == null) || !(dsDefault instanceof String)) {
      throw new AppRuntimeException("�޷���ȡ��ݿ�l��");
    }
    return getDataSourceByYear(dsDefault);
  }

  public static String getCurrentDSName()
  {
    String currentDS = getUserDataSource().getCurrentDS();

    return currentDS;
  }

  public static void setLoginDS(String ds)
  {
    UserDataSource user = getUserDataSource();
    String newds = findDS(ds);
    if (newds != null) {
      user.setLoginDS(newds);
      contextHolder.set(user);
    }
  }

  public static void setCurrentDS(String ds)
  {
    UserDataSource user = getUserDataSource();

    String newds = findDS(ds);
    if (newds == null)
      return;
    user.setCurrentDS(newds);
    contextHolder.set(user);
  }

  public static void changeYear(int year)
  {
    UserDataSource user = getUserDataSource();

    String newyear = user.getCurrentDS().replaceAll(String.valueOf(user.getCurrentYear()), String.valueOf(year));

    if (matchYear(newyear) == null) {
      //log
      throw new AppRuntimeException(year + "�����ݿ�l���޷���ȡ,������datasource.xml");
    }

    log.debug("�л���ȣ�" + newyear);
    user.setCurrentYear(year);
    contextHolder.set(user);
    //SecureUtil.setDataSource(user);
  }

  public static void resetYear()
  {
    UserDataSource user = getUserDataSource();
    user.reset();
    contextHolder.set(user);
   // SecureUtil.setDataSource(user);

    log.debug("�ָ���ȣ�" + user.getCurrentDS());
  }

  public static int findCurrentYear()
  {
    return getUserDataSource().getCurrentYear();
  }

  public static int findLoginYear()
  {
    return getUserDataSource().getLoginYear();
  }

  public static String findDBUser(int year)
  {
    UserDataSource user = getUserDataSource();
    String newyear = user.getCurrentDS().replaceAll(String.valueOf(user.getCurrentYear()), String.valueOf(year));

    newyear = matchYear(newyear);
    if (newyear == null) {
      //log
      throw new AppRuntimeException(year + "�����ݿ�l���޷���ȡ,������datasource.xml");
    }

    String[] s = newyear.split(";");

    if (s.length < 5) {
    	//log
      throw new AppRuntimeException(year + "�����ݿ�l���޷���ȡ�û�,������datasource.xml");
    }
    return s[4];
  }

  public static String findDS(String year)
  {
    if (year == null) {
      return null;
    }
    if (dsAlias != null) {
      String newyear = (String)dsAlias.get(year);
      if (newyear != null) {
        year = newyear;
      }
    }
    if (matchYear(year) == null) {
      return null;
    }
    return year;
  }

  public static UserDataSource getUserDataSource()
  {
    UserDataSource user = (UserDataSource)contextHolder.get();
    if (user == null)
    {
      //user = SecureUtil.getDataSource();
      if ((user == null) || (user.getLoginDS() == null))
      {
        user = new UserDataSource();
        user.setLoginDS(dsDefault);
      }
      contextHolder.set(user);
    }
    return user;
  }

  public static void cleanupUserContext()
  {
    contextHolder.set(null);
  }

  public static Map getDatasources()
  {
    Assert.notNull(dataSources, "���Դδ��ʼ����������dataSource.xml!");
    return dataSources;
  }

  private static String matchYear(String year)
  {
    year = year.substring(0, year.lastIndexOf(";"));
    for (Iterator iter = dataSources.keySet().iterator(); iter.hasNext(); ) {
      String strYear = (String)iter.next();
      String strYearBlock = strYear.substring(0, strYear.lastIndexOf(";"));
      if (strYearBlock.equals(year)) {
        return strYear;
      }
    }
    return null;
  }

  public void afterPropertiesSet()
    throws Exception
  {
    Assert.notNull(dsDefault, "��������ȱʡ���Դ");
  }

  public void setDataSources(Map dataSources)
  {
    dataSources = dataSources;
  }

  public Map getDataSources()
  {
    return dataSources;
  }

  public Object[] getDataSourceNames()
  {
    return dataSources.keySet().toArray();
  }

  public void setDsAlias(Map dsAlias) {
    dsAlias = dsAlias;
  }

  public Connection getConnection(String username, String password)
    throws SQLException
  {
    throw new AppRuntimeException("��֧�ֵķ���");
  }

  public PrintWriter getLogWriter()
    throws SQLException
  {
    throw new AppRuntimeException("��֧�ֵķ���");
  }

  public void setLogWriter(PrintWriter out)
    throws SQLException
  {
  }

  public void setLoginTimeout(int seconds)
    throws SQLException
  {
  }

  public int getLoginTimeout()
    throws SQLException
  {
    return 0;
  }

  public static void setCurrentDS(int year, int gov, String ds) {
  }

  public static void setGovs(Map govlist) {
  }

  public static int getGovid() {
    return 0;
  }
  public static int getAcctyear() {
    return 0;
  }
  public static String getYearGov() {
    return null;
  }
  public static String findDS(int year, int gov) {
    return null;
  }

public boolean isWrapperFor(Class arg0) throws SQLException {
	// TODO Auto-generated method stub
	return false;
}

public Object unwrap(Class arg0) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}
}