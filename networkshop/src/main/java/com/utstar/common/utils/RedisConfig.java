package com.utstar.common.utils;

import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author UTSC0368
 *
 */
public class RedisConfig {

	private static final String FILENAME = "redis.properties";
	private Properties props = null;
	
	private volatile static RedisConfig singleton;
	private int first_level_cache = 0;
	private int third_level_cache = 0;
	private int forth_level_cache = 0;
	private float topNFactor = 1;
	private int db_cache1;
	private int db_cache2;
	private int db_cache3;
	private int db_topN;
	private int db_cache4;
	private String master;
	private int selectId;
	private String HotSearchTopNHH24;
	private String HotSearchTopNHH24_0;
	private String HotSearchTopNHH24_1_2;
	//private String HotSearchTopNHH24_2;
	private String hots;
	private String hots_0;
	private String hots_1_2;
	//private String hots_2;
	private int similar_cache_all;
	private int similar_cache_one;
    private Integer mergedmediaId;
    private Integer slavePort;
    private Integer timeOut;

	private RedisConfig() {
		props = new Properties();
		try {
			this.props.load(this.getClass().getClassLoader().getResourceAsStream(FILENAME));	
			
			this.first_level_cache = Integer.parseInt(props.getProperty("first_level_cache_timeout"));
			this.forth_level_cache = Integer.parseInt(props.getProperty("forth_level_cache_timeout"));
			this.third_level_cache = Integer.parseInt(props.getProperty("third_level_cache_timeout"));
			this.db_cache1 = Integer.parseInt(props.getProperty("cache1"));
			this.db_cache2 = Integer.parseInt(props.getProperty("cache2"));
			this.db_cache3 = Integer.parseInt(props.getProperty("cache3"));
			this.db_cache4 = Integer.parseInt(props.getProperty("cache4"));
			this.db_topN = Integer.parseInt(props.getProperty("topN"));
			this.master = props.getProperty("master");
			this.selectId = Integer.valueOf(props.getProperty("selectId"));
			this.HotSearchTopNHH24 = props.getProperty("HotSearchTopNHH24");
			this.HotSearchTopNHH24_0 = props.getProperty("HotSearchTopNHH24_0");
			this.HotSearchTopNHH24_1_2 = props.getProperty("HotSearchTopNHH24_1_2");
			//this.HotSearchTopNHH24_2 = props.getProperty("HotSearchTopNHH24_2");
			this.hots = props.getProperty("hots");
			this.hots_0 = props.getProperty("hots_0");
			this.hots_1_2 = props.getProperty("hots_1_2");
			//this.hots_2 = props.getProperty("hots_2");
            this.mergedmediaId = Integer.parseInt(props.getProperty("mergedmediaId"));
            this.slavePort = Integer.parseInt(props.getProperty("slavePort"));
			this.similar_cache_one=Integer.parseInt(props.getProperty("similar_cache_one"));
			this.similar_cache_all=Integer.parseInt(props.getProperty("similar_cache_all"));
			this.timeOut=Integer.parseInt(props.getProperty("timeout"));
		} catch (Exception e) {
			Logger.getLogger(this.getClass()).error("发生异常", e);;
		}
	}

	public static RedisConfig getInstance() {
		if (singleton == null) {
			synchronized (RedisConfig.class) {
				if (singleton == null) {
					singleton = new RedisConfig();
				}
			}
		}
		return singleton;
	}

	public int getFirst_level_cache() {
		return first_level_cache;
	}

	public void setFirst_level_cache(int first_level_cache) {
		this.first_level_cache = first_level_cache;
	}

	public int getThird_level_cache() {
		return third_level_cache;
	}

	public void setThird_level_cache(int third_level_cache) {
		this.third_level_cache = third_level_cache;
	}

	public int getForth_level_cache() {
		return forth_level_cache;
	}

	public void setForth_level_cache(int forth_level_cache) {
		this.forth_level_cache = forth_level_cache;
	}

	public float getTopNFactor() {
		return topNFactor;
	}

	public void setTopNFactor(float topNFactor) {
		this.topNFactor = topNFactor;
	}

	public int getDb_cache1() {
		return db_cache1;
	}

	public void setDb_cache1(int db_cache1) {
		this.db_cache1 = db_cache1;
	}

	public int getDb_cache2() {
		return db_cache2;
	}

	public void setDb_cache2(int db_cache2) {
		this.db_cache2 = db_cache2;
	}

	public int getDb_cache3() {
		return db_cache3;
	}

	public void setDb_cache3(int db_cache3) {
		this.db_cache3 = db_cache3;
	}

	public int getDb_topN() {
		return db_topN;
	}

	public void setDb_topN(int db_topN) {
		this.db_topN = db_topN;
	}

	public int getDb_cache4() {
		return db_cache4;
	}

	public void setDb_cache4(int db_cache4) {
		this.db_cache4 = db_cache4;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public int getSelectId() {
		return selectId;
	}

	public void setSelectId(int selectId) {
		this.selectId = selectId;
	}

	public String getHotSearchTopNHH24() {
		return HotSearchTopNHH24;
	}

	public void setHotSearchTopNHH24(String hotSearchTopNHH24) {
		HotSearchTopNHH24 = hotSearchTopNHH24;
	}

	public String getHotSearchTopNHH24_0() {
		return HotSearchTopNHH24_0;
	}

	public void setHotSearchTopNHH24_0(String hotSearchTopNHH24_0) {
		HotSearchTopNHH24_0 = hotSearchTopNHH24_0;
	}

	public String getHotSearchTopNHH24_1_2() {
		return HotSearchTopNHH24_1_2;
	}

	public void setHotSearchTopNHH24_1_2(String hotSearchTopNHH24_1_2) {
		HotSearchTopNHH24_1_2 = hotSearchTopNHH24_1_2;
	}

	public String getHots() {
		return hots;
	}

	public void setHots(String hots) {
		this.hots = hots;
	}

	public String getHots_0() {
		return hots_0;
	}

	public void setHots_0(String hots_0) {
		this.hots_0 = hots_0;
	}

	public String getHots_1_2() {
		return hots_1_2;
	}

	public void setHots_1_2(String hots_1_2) {
		this.hots_1_2 = hots_1_2;
	}

	public Integer getMergedmediaId() {
		return mergedmediaId;
	}

	public void setMergedmediaId(Integer mergedmediaId) {
		this.mergedmediaId = mergedmediaId;
	}

	public Integer getSlavePort() {
		return slavePort;
	}

	public void setSlavePort(Integer slavePort) {
		this.slavePort = slavePort;
	}
	
	public int getSimilar_cache_all() {
		return similar_cache_all;
	}

	public void setSimilar_cache_all(int similar_cache_all) {
		this.similar_cache_all = similar_cache_all;
	}

	public int getSimilar_cache_one() {
		return similar_cache_one;
	}

	public void setSimilar_cache_one(int similar_cache_one) {
		this.similar_cache_one = similar_cache_one;
	}

	public Integer getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Integer timeOut) {
		this.timeOut = timeOut;
	}
	
}
