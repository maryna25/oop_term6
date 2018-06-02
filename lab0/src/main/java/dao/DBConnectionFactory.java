package dao;

import log.AppLogger;
import org.apache.tomcat.jdbc.pool.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnectionFactory {

    private static DBConnectionFactory connectionFactory = null;
    private DataSource ds;

    // Initialize DB connection pool
    private DBConnectionFactory() {
        ds = new DataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/mysql");
        ds.setUsername("root");
//        ds.setPassword("");
        ds.setInitialSize(5);
        ds.setMaxActive(10);
        ds.setMaxIdle(5);
        ds.setMinIdle(2);
    }

    // Get DB connection
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static DBConnectionFactory getInstance() {
        if (connectionFactory == null) {
            connectionFactory = new DBConnectionFactory();
        }
        return connectionFactory;
    }

    // Close connection
    public static void closeConnection(Statement statement, Connection connection) {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            AppLogger.getLogger().error("Error closing connection", e);
        }
    }
}
