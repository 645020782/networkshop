package com.utstar.common.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;



import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.ZParams;

public class RedisClusterUtil {
	private static JedisSentinelPool pool = null;
	/* private static JedisPool readPool = null; */
	private static Properties property;
	// private static Jedis jedis;
	private volatile static RedisClusterUtil singleton;
	
	public Vector<String> vpool = new Vector<String>();

	public static RedisClusterUtil getInstance() {
		if (singleton == null) {
			synchronized (RedisConfig.class) {
				if (singleton == null) {
					singleton = new RedisClusterUtil();
				}
			}
		}
		return singleton;
	}
	

	private  RedisClusterUtil() {
		init();
	}
	
	/*static {
		init();
	}
*/
	private static void init() {
		property = new Properties();
		try {
			property.load(RedisClusterUtil.class.getResourceAsStream("/redis.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		JedisPoolConfig config = new JedisPoolConfig();
		// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
		// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
		config.setMaxTotal(Integer.parseInt(property.getProperty("jedisMaxTotal")));
		// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
		config.setMaxIdle(Integer.parseInt(property.getProperty("jedisMaxIdle")));
		// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
		config.setMaxWaitMillis(Integer.parseInt(property.getProperty("jedisMaxWaitMillis")));
		// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
		config.setTestOnBorrow(false);
		config.setBlockWhenExhausted(true);
		config.setMinEvictableIdleTimeMillis(1800000);
		config.setSoftMinEvictableIdleTimeMillis(1800000);
		config.setTimeBetweenEvictionRunsMillis(-1);
		String sentinelStr = property.getProperty("sentinels");
		String[] sentinelArray = sentinelStr.split(",");
		Set<String> sentinels = new HashSet<String>();
		for (String sentinel : sentinelArray)
			sentinels.add(sentinel);
		pool = new JedisSentinelPool("master168", sentinels, config);
		System.out.println("Establishing the jedis connection pool !");

		/*
		 * String masterIp = writePool.getCurrentHostMaster().getHost(); int
		 * masterPort = writePool.getCurrentHostMaster().getPort(); readPool =
		 * new JedisPool(config, "10.80.248.123", 6379);
		 */

	}

	public  int getSelectId() {
		Integer selectId = RedisConfig.getInstance().getSelectId();
		return selectId;
	}

	public  String getOneDayHot() {
		return property.getProperty("oneDayHot");
	}

	public  String getOneWeekHot() {
		return property.getProperty("oneWeekHot");
	}

	public  String getOneMonthHot() {
		return property.getProperty("oneMonthHot");
	}

	/*
	 * public static JedisPool getPool() { return readPool; }
	 */
	public JedisSentinelPool getPool() {
		return pool;
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */

	public void returnBrokenResource(JedisSentinelPool mpool, Jedis jedis, String invoker) {
		if (jedis != null && mpool != null) {
			mpool.returnBrokenResource(jedis);
			//System.out.println("return OUT_CALL():"+jedis.toString()+"@"+invoker);
			vpool.remove("OUT_CALL():"+jedis.toString()+"@"+invoker);
		}
	}

	public  void returnResource(JedisSentinelPool pool2, Jedis jedis, String invoker) {
		if (jedis != null && pool2 != null) {
			pool2.returnResource(jedis);
			//System.out.println("return OUT_CALL():"+jedis.toString()+"@"+invoker);
			vpool.remove("OUT_CALL():"+jedis.toString()+"@"+invoker);
			
		}
	}

	public  Properties getProperty() {
		return property;
	}

	public  Jedis getJedis(String redisName) {
		String ip = property.getProperty(redisName);
		Integer ipPort = Integer.valueOf(property.getProperty(redisName + "Port"));
		Jedis jedis = new Jedis(ip, ipPort);
		return jedis;
	}
	
	
	public Jedis getResource(JedisSentinelPool pool2, String invoker){
		Jedis jedis = pool2.getResource();
		//System.out.println("OUT_CALL():"+jedis.toString()+"@"+invoker);
		vpool.addElement("OUT_CALL():"+jedis.toString()+"@"+invoker);
		return jedis;
	}

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @param index
	 *            数据库
	 * @return
	 */
	public  String get(String key, int index) {
		String value = null;

		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("get():" + jedis.toString());
			jedis.select(index);
			value = jedis.get(key);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			vpool.remove("get():"+jedis.toString());
			Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("get():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("get()中无法归还jedis连接", e);
				}
		}
		return value;
	}
	
	public byte[] get(byte[] bytes, int index) {
		byte[] value = null;

		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("get():" + jedis.toString());
			jedis.select(index);
			value = jedis.get(bytes);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			vpool.remove("get():"+jedis.toString());
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("get():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("get()中无法归还jedis连接", e);
				}
		}
		return value;
	}

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @param index
	 *            数据库
	 * @return
	 */
	public  List<String> lrange(final String key, long start, long end, int index) {
		List<String> value = new ArrayList<String>();
		////JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			////pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("lrange():"+jedis.toString());
			jedis.select(index);
			value = jedis.lrange(key, start, end);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("lrange():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("lrange():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("lrange()中无法归还jedis连接", e);
				}
		}

		return value;
	}

	public boolean exists(final String key, int index) {
		boolean isExist = false;
		////JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			////pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("exists():"+jedis.toString());
			jedis.select(index);
			isExist = jedis.exists(key);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("exists():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("exists():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("exists()中无法归还jedis连接", e);
				}
		}
		return isExist;

	}
	
	public boolean exists(byte[] key, int index) {
		boolean isExist = false;
		////JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			////pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("exists():"+jedis.toString());
			jedis.select(index);
			isExist = jedis.exists(key);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("exists():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("exists():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("exists()中无法归还jedis连接", e);
				}
		}
		return isExist;
	}

	public long rpush(final String key, String value, int index) {
		long num = 0;
		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("rpush():"+jedis.toString());
			jedis.select(index);
			num = jedis.rpush(key, value);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("rpush():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("rpush():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("rpush()中无法归还jedis连接", e);
				}
		}
		/*
		 * return Integer reply, specifically, the number of elements inside the
		 * list after the push operation.
		 */
		return num;

	}

	public long expire(final String key, int timeout, int index) {
		long num = 0;
		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("expire():"+jedis.toString());
			jedis.select(index);
			num = jedis.expire(key, timeout);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("expire():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("expire():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("expire()中无法归还jedis连接", e);
				}
		}
		/*
		 * @return Integer reply, specifically: 1: the timeout was set. 0: the
		 * timeout was not set since the key already has an associated timeout
		 * (this may happen only in Redis versions &lt; 2.1.3, Redis &gt;= 2.1.3
		 * will happily update the timeout), or the key does not exist.
		 */
		return num;
	}

	public long llen(final String key, int index) {
		long num = 0;
		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("llen():"+jedis.toString());
			jedis.select(index);
			num = jedis.llen(key);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("llen():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("llen():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("llen()中无法归还jedis连接", e);
				}
		}
		return num;
	}

	public String setex(final String key, final int seconds, final String value, int index) {
		String str = null;
		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("setex():"+jedis.toString());
			jedis.select(index);
			str = jedis.setex(key, seconds, value);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("setex():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("setex():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("setex()中无法归还jedis连接", e);
				}
		}
		return str;
	}

	public Map<String, String> hgetAll(final String key, int index) {
		Map<String, String> map = new HashMap<String, String>();
		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("hgetAll():"+jedis.toString());
			jedis.select(index);
			map = jedis.hgetAll(key);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("hgetAll():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("hgetAll():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("hgetAll()中无法归还jedis连接", e);
				}
		}
		return map;
	}

	public long hsetnx(final String key, final String field, final String value, int index) {
		long isExists = 0;
		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("hsetnx():"+jedis.toString());
			jedis.select(index);
			isExists = jedis.hsetnx(key, field, value);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("hsetnx():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("hsetnx():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("hsetnx()中无法归还jedis连接", e);
				}
		}
		return isExists;

	}

	public void set(String key, String value, int index, String invoker) {

		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("set():"+jedis.toString()+"@"+invoker);
			jedis.select(index);
			jedis.set(key, value);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("set():"+jedis.toString()+"@"+invoker);
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("set():"+jedis.toString()+"@"+invoker);
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("set()中无法归还jedis连接", e);
				}
		}
	}
	
	public void set(String key, Map<String, Serializable> value, int index, String invoker, Integer expiry) {

		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("set():"+jedis.toString()+"@"+invoker);
			jedis.select(index);
			jedis.set(key.getBytes(),SerializeUtil.serialize(value),"NX".getBytes(),"EX".getBytes(),expiry);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("set():"+jedis.toString()+"@"+invoker);
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("set():"+jedis.toString()+"@"+invoker);
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("set()中无法归还jedis连接", e);
				}
		}
	}
	
	public void set(String key, Object value, int index, String invoker, Integer expiry) {

		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("set():"+jedis.toString()+"@"+invoker);
			jedis.select(index);
			jedis.set(key.getBytes(),SerializeUtil.serialize(value),"NX".getBytes(),"EX".getBytes(),expiry);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("set():"+jedis.toString()+"@"+invoker);
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("set():"+jedis.toString()+"@"+invoker);
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("set()中无法归还jedis连接", e);
				}
		}
	}

	/**
	 * 查key
	 * 
	 * @param keyStr
	 * @return
	 */
	public Set<String> keys(String key, int index) {

		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		Set<String> keys = new HashSet<String>();
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("keys():"+jedis.toString());
			
			jedis.select(index);
			keys = jedis.keys(key);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("keys():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("keys():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("keys()中无法归还jedis连接", e);
				}
		}
		return keys;
	}

	public Set<byte[]> keys(byte[] key, int index) {

		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		Set<byte[]> keys = new HashSet<byte[]>();
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("keys():"+jedis.toString());
			
			jedis.select(index);
			keys = jedis.keys(key);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("keys():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("keys():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("keys()中无法归还jedis连接", e);
				}
		}
		return keys;
	}

	
	public Set<String> smembers(String key, int index) {
		Set<String> members = new HashSet<String>();
		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("smembers():"+jedis.toString());
			jedis.select(index);
			members = jedis.smembers(key);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("smembers():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("smembers():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("smembers()中无法归还jedis连接", e);
				}
		}
		return members;
	}

	public boolean sismember(String key, String member, int index) {
		boolean ismember = false;
		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("sismember():"+jedis.toString());
			jedis.select(index);
			ismember = jedis.sismember(key, member);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("sismember():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("sismember():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("sismember()中无法归还jedis连接", e);
				}
		}
		return ismember;
	}

	public long smove(String srcKey, String destKey, String member, int index) {
		long retVal = 0;
		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("smove():"+jedis.toString());
			jedis.select(index);
			retVal = jedis.smove(srcKey, destKey, member);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("smove():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + srcKey + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("smove():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("smove()中无法归还jedis连接", e);
				}
		}
		return retVal;
	}

	public long del(String key, int index) {
		long retVal = 0;
		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("del():"+jedis.toString());
			jedis.select(index);
			retVal = jedis.del(key);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("del():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("del():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("del()中无法归还jedis连接", e);
				}
		}
		return retVal;
	}
	
	public long del(byte[] key, int index) {
		long retVal = 0;
		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("del():"+jedis.toString());
			jedis.select(index);
			retVal = jedis.del(key);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("del():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("del():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("del()中无法归还jedis连接", e);
				}
		}
		return retVal;
	}

	public long hset(final String key, final String field, final String value, int index) {
		long isExists = 0;
		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("hset():"+jedis.toString());
			jedis.select(index);
			isExists = jedis.hset(key, field, value);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("hset():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("hset():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("hset()中无法归还jedis连接", e);
				}
		}
		return isExists;

	}

	public long lpush(final String key, String value, int index) {
		long retVal = 0;
		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("lpush():"+jedis.toString());
			jedis.select(index);
			retVal = jedis.lpush(key, value);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("lpush():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("lpush():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("lpush()中无法归还jedis连接", e);
				}
		}
		return retVal;
	}

	public String hget(String key, String field, int index) {
		String value = null;
		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("hget():"+jedis.toString());
			jedis.select(index);
			value = jedis.hget(key, field);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("hget():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("hget():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("hget()中无法归还jedis连接", e);
				}
		}
		return value;
	}

	public long zadd(String key, double score, String member, int index) {
		long retVal = 0;
		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("zadd():"+jedis.toString());
			jedis.select(index);
			retVal = jedis.zadd(key, score, member);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("zadd():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("zadd():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("zadd()中无法归还jedis连接", e);
				}
		}
		return retVal;
	}

	public Set<String> zrevrangeByScore(String key, String max, String min, int index) {
		Set<String> replySet = null;
		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("zrevrangeByScore():"+jedis.toString());
			jedis.select(index);
			replySet = jedis.zrevrangeByScore(key, max, min);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("zrevrangeByScore():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("zrevrangeByScore():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("zrevrangeByScore()中无法归还jedis连接", e);
				}
		}
		return replySet;
	}

	public long zunionstore(String dstkey, ZParams params, int index, String... sets) {
		long retVal = 0;
		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("zunionstore():"+jedis.toString());
			jedis.select(index);
			retVal = jedis.zunionstore(dstkey, params, sets);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("zunionstore():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + dstkey + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("zunionstore():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("zunionstore()中无法归还jedis连接", e);
				}
		}
		return retVal;
	}

	public long zunionstore(String dstkey, String[] sets, int index) {
		long retVal = 0;
		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("zunionstore():"+jedis.toString());
			jedis.select(index);
			retVal = jedis.zunionstore(dstkey, sets);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("zunionstore():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + dstkey + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("zunionstore():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("zunionstore()中无法归还jedis连接", e);
				}
		}
		return retVal;
	}
	
	public String lindex(String key,int pos,int index) {
		String resStr = null;
		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("lindex():"+jedis.toString());
			jedis.select(index);
			resStr = jedis.lindex(key, pos);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("lindex():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("lindex():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("lindex()中无法归还jedis连接", e);
				}
		}
		return resStr;
	}

	public long lrem(String key, int count, String value, int index) {
		long retVal = 0;
		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("lrem():"+jedis.toString());
			jedis.select(index);
			retVal = jedis.lrem(key, count, value);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("lrem():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("lrem():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("lrem()中无法归还jedis连接", e);
				}
		}
		return retVal;
	}

	public long sadd(String key, String member, int index) {
		long retVal = 0;
		//JedisSentinelPool pool = null;
		Jedis jedis = null;
		try {
			//pool = getPool();
			jedis = pool.getResource();
			vpool.addElement("sadd():"+jedis.toString());
			jedis.select(index);
			retVal = jedis.sadd(key, member);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("sadd():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("sadd():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("sadd()中无法归还jedis连接", e);
				}
		}
		return retVal;
		
	}
	
	public long incr(String key,  int index){
		long retVal = 0;
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			vpool.addElement("sadd():"+jedis.toString());
			jedis.select(index);
			retVal = jedis.incr(key);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("sadd():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("sadd():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("sadd()中无法归还jedis连接", e);
				}
		}
		return retVal;
		
	}
	
	public long incrby(String key, long increment, int index){
		long retVal = 0;
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			vpool.addElement("sadd():"+jedis.toString());
			jedis.select(index);
			retVal = jedis.incrBy(key, increment);
		} catch (Exception e) {
			// 释放redis对象
				pool.returnBrokenResource(jedis);
				vpool.remove("sadd():"+jedis.toString());
				Logger.getLogger(RedisClusterUtil.class).error("通过key【" + key + "】从库【" + index + "】操作数据发生异常", e);
		} finally {
			// 返还到连接池
				try {
					pool.returnResource(jedis);
					vpool.remove("sadd():"+jedis.toString());
				} catch (Exception e) {
					Logger.getLogger(RedisClusterUtil.class).error("sadd()中无法归还jedis连接", e);
				}
		}
		return retVal;
		
	}
	
	public  int getIdleNum(){
		return pool.getNumIdle();
	}
	
	public  int getActiveNum() {
		return pool.getNumActive();
	}

}
