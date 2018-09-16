package orm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionDB {
	private static String Driver;
	private static String Url;
	private static String User;
	private static String PassWord;

	static {
		Properties pros = new Properties();
		try {
			InputStream in = new FileInputStream("./jdbc.properties");
			pros.load(in);
			Driver = pros.getProperty("Driver");
			Url = pros.getProperty("Url");
			User = pros.getProperty("User");
			PassWord = pros.getProperty("PassWord");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(Driver);
			conn = DriverManager.getConnection(Url, User, PassWord);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

//	public static void main(String[] args) {
//		Connection conn = ConnectionDB.getConnection();
//		System.out.println(conn);
//		if (conn != null) {
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}

}
