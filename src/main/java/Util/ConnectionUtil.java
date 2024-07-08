package Util;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.postgresql.ds.PGPoolingDataSource;
import org.postgresql.jdbc2.optional.ConnectionPool;
import org.postgresql.jdbc2.optional.SimpleDataSource;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {


    private static final String url = "jdbc:postgresql://localhost:5432/project0";
    private static final String user = "user";
    private static final String password = "password";


    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Sql exception: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void startDatabase() {
        try {
            FileReader sqlScript = new FileReader("src/main/resources/database.sql");
            // run script
            ScriptRunner sr = new ScriptRunner(getConnection());
            sr.runScript(sqlScript);
            System.out.println("Database created.");
            sqlScript.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}