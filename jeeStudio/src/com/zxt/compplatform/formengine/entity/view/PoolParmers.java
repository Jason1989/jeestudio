package com.zxt.compplatform.formengine.entity.view;

/**
 * 连接池配置
 * @author 007
 */
public class PoolParmers {

	/**
	 * 驱动名称
	 */
	private String driverClassName;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 链接地址
	 */
	private String url;
	
	
	/**
	 * 连接池中保留的最大连接数
	 */
	private int maxPoolSize=6;
	/**
	 * 连接池最小连接数
	 */
	private int minPoolSize=2;
	
	/**
	 * 初始化时获取10个连接，取值应在minPoolSize与maxPoolSize之间
	 */
	private int InitialPoolSize=2;
	/**
	 * 最大空闲时间,60秒内未使用则连接被丢弃
	 */
	private int maxIdleTime=20;
	
	
	/**
	 * 当连接池中的连接耗尽的时候c3p0一次同时获取的连接数	 */
	private int acquireIncrement=15;
	/**
	 * 当连接池用完时客户端调用getConnection()后等待获取新连接的时间，超时后将抛出SQLException,如设为0则无限期等待。单位毫秒。
	 */
	private int checkoutTimeout=1000;
	/**
	 * 每60秒检查所有连接池中的空闲连接
	 */
	private int idleConnectionTestPeriod=60;


	/**
	 * c3p0是异步操作的，缓慢的JDBC操作通过帮助进程完成。扩展这些操作可以有效的提升性能通过多线程实现多个操作同时被执行
	 */
	private int numHelperThreads=3;
	/**
	 * 连接关闭时默认将所有未提交的操作回滚
	 */
	private boolean autoCommitOnClose=false;
	/**
	 * 获取连接失败后该数据源将申明已断开并永久关闭
	 */
	private boolean breakAfterAcquireFailure=false;
	/**
	 * 数据源ID
	 */
	private String dataSourceID;
	
	public boolean isAutoCommitOnClose() {
		return autoCommitOnClose;
	}
	public void setAutoCommitOnClose(boolean autoCommitOnClose) {
		this.autoCommitOnClose = autoCommitOnClose;
	}
	public boolean isBreakAfterAcquireFailure() {
		return breakAfterAcquireFailure;
	}
	public void setBreakAfterAcquireFailure(boolean breakAfterAcquireFailure) {
		this.breakAfterAcquireFailure = breakAfterAcquireFailure;
	}
	public int getNumHelperThreads() {
		return numHelperThreads;
	}
	public void setNumHelperThreads(int numHelperThreads) {
		this.numHelperThreads = numHelperThreads;
	}
	public int getAcquireIncrement() {
		return acquireIncrement;
	}
	public void setAcquireIncrement(int acquireIncrement) {
		this.acquireIncrement = acquireIncrement;
	}
	public int getCheckoutTimeout() {
		return checkoutTimeout;
	}
	public void setCheckoutTimeout(int checkoutTimeout) {
		this.checkoutTimeout = checkoutTimeout;
	}
	public int getIdleConnectionTestPeriod() {
		return idleConnectionTestPeriod;
	}
	public void setIdleConnectionTestPeriod(int idleConnectionTestPeriod) {
		this.idleConnectionTestPeriod = idleConnectionTestPeriod;
	}
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getInitialPoolSize() {
		return InitialPoolSize;
	}
	public void setInitialPoolSize(int initialPoolSize) {
		InitialPoolSize = initialPoolSize;
	}
	public int getMaxPoolSize() {
		return maxPoolSize;
	}
	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}
	public int getMinPoolSize() {
		return minPoolSize;
	}
	public void setMinPoolSize(int minPoolSize) {
		this.minPoolSize = minPoolSize;
	}
	public int getMaxIdleTime() {
		return maxIdleTime;
	}
	public void setMaxIdleTime(int maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}
	public String getDataSourceID() {
		return dataSourceID;
	}
	public void setDataSourceID(String dataSourceID) {
		this.dataSourceID = dataSourceID;
	}
	
}
