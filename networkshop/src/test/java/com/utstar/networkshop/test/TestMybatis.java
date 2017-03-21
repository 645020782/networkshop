package com.utstar.networkshop.test;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.utstar.networkshop.dao.BrandDao;
import com.utstar.networkshop.domain.TestVo;

public class TestMybatis {
	/*private SqlSessionFactory sqlSessionFactory=null;
	@Before
	public void init(){
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
		sqlSessionFactory = (SqlSessionFactory) applicationContext.getBean("sqlSessionFactory");
	}
	@Test
	public void test1(){
		SqlSession session = sqlSessionFactory.openSession();
		TestVo t1 = session.selectOne("findtestbyid","1");
		System.out.println(t1);
	}*/
}
