package com.utstar.common.web.aop;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.beans.factory.annotation.Autowired;

import com.danga.MemCached.MemCachedClient;
import com.utstar.common.utils.RedisClusterUtil;
import com.utstar.common.utils.SerializeUtil;

/**
 * 缓存Memcached中数据的切面对象
 * aroud
 * after
 * @author lx
 *
 */
public class CacheInterceptor {

	
	//时间 缓存时间
	public static final int TIMEOUT = 360000;//秒
	
	private int expiry = TIMEOUT;
	
	//配置环绕方法
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable{
		//去Memcached中看看有没有我们的数据  包名+ 类名 + 方法名 + 参数(多个)
		String cacheKey = getCacheKey(pjp);
		System.out.println(cacheKey);
		//如果Memcached 连接不上呢
		//返回值
		if(null == RedisClusterUtil.getInstance().get(cacheKey,0)){
			//回Service
			Object proceed = pjp.proceed();
			//先放到Memcached中一份
			RedisClusterUtil.getInstance().set(cacheKey, proceed, 0, "", expiry);
		}
		return SerializeUtil.unserialize(RedisClusterUtil.getInstance().get(cacheKey.getBytes(),0));
	}
	//后置由于数据库数据变更  清理get*
	public void doAfter(JoinPoint jp){
		//包名+ 类名 + 方法名 + 参数(多个)  生成Key
		//包名+ 类名 
		String packageName = jp.getTarget().getClass().getName();
		String key = "*"+packageName+"*";
		//包名+ 类名  开始的 都清理
		Set<byte[]> keySet =  RedisClusterUtil.getInstance().keys(key.getBytes(), 0);
		//
		//遍历
		for(byte[] entry : keySet){
			RedisClusterUtil.getInstance().del(entry, 0);
		}
	}
	
	
	
	
	
	//包名+ 类名 + 方法名 + 参数(多个)  生成Key
	public String getCacheKey(ProceedingJoinPoint pjp){
		//StringBuiter
		StringBuilder key = new StringBuilder();
		//包名+ 类名   cn.itcast.core.serice.product.ProductServiceImpl.productList
		String packageName = pjp.getTarget().getClass().getName();
		key.append(packageName);
		// 方法名
		String methodName = pjp.getSignature().getName();
		key.append(".").append(methodName);
		
		//参数(多个)
		Object[] args = pjp.getArgs();
		
		ObjectMapper  om = new ObjectMapper();
		om.setSerializationInclusion(Inclusion.NON_NULL);
		
		for(Object arg : args){
			
			//流
			StringWriter str = new StringWriter(); 
			
			//对象转Json  写的过程     Json是字符串流
			try {
				om.writeValue(str, arg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//参数
			key.append(".").append(str);
		}
		
		return key.toString();
	}
	public void setExpiry(int expiry) {
		this.expiry = expiry;
	}
	
	
}
