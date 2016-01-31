package com.zxt.compplatform.formengine.util;

import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zxt.compplatform.formengine.entity.view.PoolParmers;

/**
 * 该类是连接池的管理类
 * 
 * @author 何舸维
 * 
 */
public class DynamicPoolFactory {
	/**
	 * 
	 */
	private static Map hashMap = null;
	private static DynamicPoolFactory dataSourcePoolFactory;
	private static final Logger log = Logger
			.getLogger(DynamicPoolFactory.class);

	private DynamicPoolFactory() {
	}

	public static DynamicPoolFactory getInstance() {
		if (null == dataSourcePoolFactory) {
			hashMap = new HashMap();
			dataSourcePoolFactory = new DynamicPoolFactory();
		}
		return dataSourcePoolFactory;
	}

	/**
	 * 绑定连接池
	 * 
	 * @param key
	 *            连接池的名称必须唯一
	 * @param dataSourcePool
	 *            对应的连接池
	 */
	// public void bind(String key,DynamicDataSourcePool dataSourcePool){
	// if(IsBePool(key))getDynamicDataSourcePool(key).destroy();
	// hashMap.put(key, dataSourcePool);
	// }
	/**
	 * 重新绑定连接池
	 * 
	 * @param key
	 *            连接池的名称必须唯一
	 * @param dataSourcePool
	 *            对应的连接池
	 */
	// public void rebind(String key,DynamicDataSourcePool dataSourcePool){
	// if(IsBePool(key))getDynamicDataSourcePool(key).destroy();
	// hashMap.put(key, dataSourcePool);
	// }
	/**
	 * 删除动态数据连接池中名称为key的连接池
	 * 
	 * @param key
	 */
	// public void unbind(String key){
	// if(IsBePool(key))getDynamicDataSourcePool(key).destroy();
	// hashMap.remove(key);
	// }
	/**
	 * 查找动态数据连接池中是否存在名称为key的连接池
	 * 
	 * @param key
	 * @return
	 */
	public boolean IsBePool(String key) {
		return hashMap.containsKey(key);
	}

	/**
	 * 根据key返回key对应的连接池
	 * 
	 * @param key
	 * @return
	 */
	public void getDynamicDataSourcePool(String key) {
		// if(!IsBePool(key))return null;
		// return (DynamicDataSourcePool)hashMap.get(key);

	}

	/**
	 * 初始化一个连接池
	 * 
	 * @param userName
	 *            数据库用户名
	 * @param pass
	 *            数据库密码
	 * @param url
	 *            连接的url
	 * @param driverClass
	 *            数据驱动
	 */
	public static ComboPooledDataSource dynamicDataSourcePool(
			PoolParmers connectionPoolingEntity) {

		ComboPooledDataSource pool = null;
		try {
			pool = new ComboPooledDataSource();// 创建对象
			/**
			 * 基本参数设置
			 */
			pool.setDriverClass(connectionPoolingEntity.getDriverClassName());// 设置驱动
			pool.setJdbcUrl(connectionPoolingEntity.getUrl()); // 设置连接的url
			pool.setUser(connectionPoolingEntity.getUserName());// 设置数据库用户名
			pool.setPassword(connectionPoolingEntity.getPassword());// 设置数据库密码
			/**
			 * 性能参数设置
			 */
			pool.setMaxPoolSize(connectionPoolingEntity.getMaxPoolSize());// 最大连接数
																			// -5
			pool.setMinPoolSize(connectionPoolingEntity.getMinPoolSize());// 最小连接数
																			// -1
			pool.setInitialPoolSize(connectionPoolingEntity
					.getInitialPoolSize());// 初始化连接数 -1
			pool.setMaxIdleTime(connectionPoolingEntity.getMaxIdleTime());// 最大空闲时间
																			// -20

			/**
			 * 
			 * 
			 * pool.setAcquireIncrement(connectionPoolingEntity.getAcquireIncrement());//当连接池中的连接耗尽的时候c3p0一次同时获取的连接数
			 * pool.setAutoCommitOnClose(connectionPoolingEntity.isAutoCommitOnClose());//连接关闭时默认将所有未提交的操作回滚
			 * pool.setBreakAfterAcquireFailure(connectionPoolingEntity.isBreakAfterAcquireFailure());//获取连接失败后该数据源将申明已断开并永久关闭
			 * pool.setCheckoutTimeout(connectionPoolingEntity.getCheckoutTimeout());//当连接池用完时客户端调用getConnection()后等待获取新连接的时间，超时后将抛出SQLException,如设为0则无限期等待。单位毫秒。
			 * pool.setIdleConnectionTestPeriod(connectionPoolingEntity.getIdleConnectionTestPeriod());//每60秒检查所有连接池中的空闲连接
			 * pool.setNumHelperThreads(connectionPoolingEntity.getNumHelperThreads());//c3p0是异步操作的，缓慢的JDBC操作通过帮助进程完成。扩展这些操作可以有效的提升性能通过多线程实现多个操作同时被执行
			 */

			log.info("数据库连接池初始化成功");
		} catch (PropertyVetoException e) {
			log.error("动态连接池 构造失败。。");
		}
		return pool;
	}

	/**
	 * 创建多数数据源的连接池
	 * 
	 * @return
	 */
	public static Map createPoolsUtil(List poolParmersList) {
		PoolParmers temParmers = null;

		Map map = new HashMap();
		ComboPooledDataSource pool;
		if (poolParmersList != null) {
			for (int i = 0; i < poolParmersList.size(); i++) {
				try {
					temParmers = (PoolParmers) poolParmersList.get(i);
					pool = dynamicDataSourcePool(temParmers);
					map.put(temParmers.getDataSourceID(), pool);
				} catch (Exception e) {
					// TODO: handle exception
					log.error("该条数据源连接池 创建失败。。");
				}
			}
		}
		return map;
	}

}