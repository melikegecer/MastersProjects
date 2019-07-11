package stringsattached;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SADBConnector {

    private static SADBConnector dbconnector;
    private static Connection connection;
    private static String dbUrl;
    private static String dbUserName;
    private static String dbPassword;

    private SADBConnector() {
        try {
            Properties props = new Properties();
            FileInputStream in = new FileInputStream("dbconnector.properties");
            props.load(in);
            in.close();

            String driver = props.getProperty("jdbc.driver");
            if (driver != null) {
                Class.forName(driver);
            }

            dbUrl = props.getProperty("jdbc.url");
            dbUserName = props.getProperty("jdbc.username");
            dbPassword = props.getProperty("jdbc.password");

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException: " + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SADBConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SADBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static SADBConnector getInstance() {
        if (dbconnector == null) {
            dbconnector = new SADBConnector();
        }
        return dbconnector;
    }

    static Connection getConnection() {
        return connection;
    }

}
