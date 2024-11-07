package com.DB;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConn {
	private static Connection conn;
	public static Connection getConn() {
		
		try {
			if(conn == null) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "blog", "1234");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
}
