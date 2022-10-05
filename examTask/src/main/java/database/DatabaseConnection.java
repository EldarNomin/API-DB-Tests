package database;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import enums.Parameters;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final ISettingsFile CONFIG_DATA = new JsonSettingsFile("config.json");
    private static final ISettingsFile AUTHORIZATION_DATA = new JsonSettingsFile("authorization.json");
    private static Logger log = AqualityServices.getLogger();
    private static Connection connection = DatabaseConnection.getConnection();

    private DatabaseConnection(){}

    public static void setDatabaseConnection(){
        try {
            connection = DriverManager.getConnection(CONFIG_DATA.getValue(Parameters.BASE_URI_DB.getValue()).toString(),
                    AUTHORIZATION_DATA.getValue(Parameters.DB_USER.getValue()).toString(),
                    AUTHORIZATION_DATA.getValue(Parameters.DB_PASSWORD.getValue()).toString());
            if (connection != null) {
                log.info("Connected to the database!");
            } else {
                log.info("Failed to make connection!");
            }
        } catch (SQLException e) {
            log.error(String.format("SQL state: %s\n%s", e.getSQLState(), e.getMessage()));
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            setDatabaseConnection();
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}