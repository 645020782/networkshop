package com.utstar.networkshop.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * 
 * <p>Title: JdbcTest</p>
 * <p>Description:jdbc测试程序 </p>
 * <p>Company: www.itcast.com</p> 
 * @author	传智.燕青
 * @date	2014-12-17上午8:45:10
 * @version 1.0
 */
public class JdbcTest {
	
	//定义sql语句，根据名称查询用户表的记录
	private static String sql = "SELECT * FROM user WHERE name =  ?";
	
	public static void main(String[] args) throws SQLException {
		
		//数据库连接
		Connection connection = null;
		//预编译statement
		//好处：防止 sql注入，提高数据的性能
		PreparedStatement preparedStatement = null;
		//结果集
		ResultSet resultSet = null;
		
		try {
			
			//加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");
			
			//连接数据库
			connection = DriverManager.getConnection("jdbc:mysql://192.168.1.168:3306/test", "root", "oss");
			
			//构造preparedStatement
			preparedStatement = connection.prepareStatement(sql);
			//向preparedStatement中占位符的位置设置参数
			preparedStatement.setString(1, "lisi");
			
			//发起数据库查询请求，得到结果集
			resultSet = preparedStatement.executeQuery();
			//遍历查询结果
			
			while(resultSet.next()){
				int id = resultSet.getInt("iduser");
				String username = resultSet.getString("name");
				System.out.println(id+"   "+username);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//释放资源
			if(resultSet!=null){
				resultSet.close();
			}
			if(preparedStatement!=null){
				preparedStatement.close();
			}
			if(connection!=null){
				connection.close();
			}
		}
		
		
	}

}
