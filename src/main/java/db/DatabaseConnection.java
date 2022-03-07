
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class DatabaseConnection {
    public static DatabaseConnection databaseConnection;
    Connection conn = null;

    private DatabaseConnection() {
    }

    public static DatabaseConnection getInstance() {
        System.out.println(" -------> getInstance: ");
        if (Objects.isNull(databaseConnection)) {
            System.out.println(" -------> Entra a isNull: ");
            databaseConnection = new DatabaseConnection();
        }

        System.out.println("databaseConnection: " + databaseConnection);
        return databaseConnection;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        System.out.println("---------------getConnection: ");
        if (this.conn == null) {
            String dbDriver = "com.mysql.cj.jdbc.Driver";
            String dbURL = "jdbc:mysql:// localhost:3306/";
            String dbName = "learners_academy_db";
            String dbUsername = "root";
            String dbPassword = "root";
            Class.forName(dbDriver);
            this.conn = DriverManager.getConnection(dbURL + dbName, dbUsername, dbPassword);
        }

        return this.conn;
    }
}
