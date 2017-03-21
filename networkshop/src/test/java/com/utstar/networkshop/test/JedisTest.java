package com.utstar.networkshop.test;

import com.utstar.common.utils.RedisClusterUtil;

public class JedisTest {
	public static void main(String[] args){
		RedisClusterUtil.getInstance().set("hello", "test", 0, "");
		String s = RedisClusterUtil.getInstance().get("hello", 0);
		System.out.println(s);
	}
}
