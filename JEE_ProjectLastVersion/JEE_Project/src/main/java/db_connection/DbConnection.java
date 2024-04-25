package db_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/travel_booking_system";
    private static final String USER = "root";
    private static final String PASSWORD = "";


	public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
