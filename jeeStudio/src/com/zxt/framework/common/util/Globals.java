package com.zxt.framework.common.util;

public final class Globals {
	public static String TEMPTABLE;
	public static String DATA_SOURCE = "datasource";
	public static boolean IS_UNIT_TEST;
	public static boolean IS_MULTIDB_CACHE;
	public static final String INFO = "I";
	public static final String WARN = "W";
	public static final String ERROR = "E";
	public static final String CRASH = "C";
	public static final int SYS_NEXT_YEAR = 1;
	public static final int SYS_CUR_YEAR = 0;
	public static final int SYS_LAST_YEAR = -1;
	public static int NEXT_YEAR;
	public static int ZERO_REJECT = 0;

	public static int GL_UNDO_NO_CTRL = 0;
	public static int BpoTimeLog;
	public static int DaoTimeLog;
	public static int BurlapServiceMode;
	public static int HttpServiceMode;
	public static boolean DEBUG = false;

	public static boolean DEBUG_PERFORMANCE = false;

	public static boolean DBMS_OUTPUT = false;

	public static int OUTPUT_SIZE = 100000;
	public static boolean ASYNCPROCESS_ON;
	public static boolean ASYNCPROCESS_CONN;
	public static boolean PASSWORDHASH;

	public static int loadCacheOnStartup;

	private static int loadParameterInt(String parameter, int defaultValue, boolean output) {
		int result = defaultValue;
		try {
			String zeroReject = SysConfig.getString(parameter);
			result = Integer.parseInt(zeroReject);
		} catch (Exception e) {
			if (output) {
				String msg = "SysConfig.properties没配置系统变量" + parameter + ";默认值" + defaultValue;
				System.out.println(msg);
			}
		}
		return result;
	}

	public static boolean isMultiDataSourceDeployMode() {
		return true;
	}

	static {
		String on = SysConfig.getString("globals.ASYNCPROCESS");
		if ((on.equalsIgnoreCase("true")) || (on.equalsIgnoreCase("1")) || (on.equalsIgnoreCase("on")))
			ASYNCPROCESS_ON = true;
		else {
			ASYNCPROCESS_ON = false;
		}
		on = SysConfig.getString("globals.ASYNCPROCESS_CONN");
		if ((on.equalsIgnoreCase("true")) || (on.equalsIgnoreCase("1")) || (on.equalsIgnoreCase("on")))
			ASYNCPROCESS_CONN = true;
		else {
			ASYNCPROCESS_CONN = false;
		}

		on = SysConfig.getString("globals.IS_MULTIDB_CACHE");
		if ((on.equalsIgnoreCase("true")) || (on.equalsIgnoreCase("1")) || (on.equalsIgnoreCase("on")))
			IS_MULTIDB_CACHE = true;
		else {
			IS_MULTIDB_CACHE = false;
		}
		on = SysConfig.getString("globals.IS_UNIT_TEST");
		if ((on.equalsIgnoreCase("true")) || (on.equalsIgnoreCase("1")) || (on.equalsIgnoreCase("on")))
			IS_UNIT_TEST = true;
		else {
			IS_UNIT_TEST = false;
		}

		TEMPTABLE = SysConfig.getString("dic.temptable");
		if (null != TEMPTABLE) {
			TEMPTABLE = TEMPTABLE.toUpperCase();
		}

		NEXT_YEAR = loadParameterInt("globals.NEXT_YEAR", 9, true);
		ZERO_REJECT = loadParameterInt("globals.ZERO_REJECT", 0, true);
		GL_UNDO_NO_CTRL = loadParameterInt("globals.GL_UNDO_NO_CTRL", 0, true);

		BpoTimeLog = loadParameterInt("logger.BpoTimeLog", 1000, true);
		DaoTimeLog = loadParameterInt("logger.DaoTimeLog", 500, true);
		BurlapServiceMode = loadParameterInt("ServiceMode.Burlap", 0, true);
		HttpServiceMode = loadParameterInt("ServiceMode.Http", 1, true);

		int iDebug = loadParameterInt("debug", 0, true);
		if (iDebug == 1)
			DEBUG = true;
		int iDebugPerformance = loadParameterInt("debug.performance", 0, true);
		if (iDebugPerformance == 1) {
			DEBUG_PERFORMANCE = true;
		}
		int iDBMS_OUTPUT = loadParameterInt("DBMS_OUTPUT", 0, false);
		if (iDBMS_OUTPUT == 1) {
			DBMS_OUTPUT = true;
		}
		loadCacheOnStartup = loadParameterInt("loadCacheOnStartup", 0, false);

		PASSWORDHASH = SysConfig.getString("globals.PASSWORDHASH").equalsIgnoreCase("1");

		loadCacheOnStartup = 0;
	}
}