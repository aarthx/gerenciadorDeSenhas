package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection
{

	public static Connection getConnection()
	{
		String url = "jdbc:mysql://avnadmin:AVNS_JGspMvhNlN6_UMHCzLA@mysql-1369263b-arthuraugusto-e96c.d.aivencloud.com:26176/GerenciaSenhas?ssl-mode=REQUIRED";
        	String user = "avnadmin";
        	String password = "AVNS_JGspMvhNlN6_UMHCzLA";

		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(url, user, password);
		} catch(SQLException | ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}

	}

} 