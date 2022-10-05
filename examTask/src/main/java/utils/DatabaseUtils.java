package utils;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import database.models.TestTable;
import enums.DatabaseRequests;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static database.DatabaseConnection.getConnection;

public class DatabaseUtils {

    private static Connection connection = getConnection();
    private static Logger log = AqualityServices.getLogger();

    public DatabaseUtils() {}

    public static void executeQuery(String query) {
        getConnection();
       try (Statement statement = connection.createStatement()) {
           statement.executeUpdate(query);
       } catch (SQLException e){
           log.error(e.getMessage());
       }
    }

    public static void getQuery(String query) {
        getConnection();
        ResultSet resultSet;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(query);
            resultSet.next();
        } catch (SQLException e){
            log.error(e.getMessage());
        }
    }

    public List<TestTable> get(String request) {
        Connection connection = getConnection();
        List<TestTable> testTables = new ArrayList<>();
        ResultSet resultSet;
        try (PreparedStatement statement = connection.prepareStatement(request)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                testTables.add(new TestTable(resultSet));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return testTables;
    }

    public static void insertImageAttach(String contentPath, int testId) {
        getConnection();
        PreparedStatement statement;
        log.debug("Sending img attachment to database");
        try (FileInputStream fis = new FileInputStream(contentPath)) {
            statement = connection.prepareStatement(DatabaseRequests.INSERT_IMG_ATTACH_QUERY);
            statement.setBinaryStream(1, fis);
            statement.setString(2, "image/png");
            statement.setInt(3, testId);
            statement.executeLargeUpdate();
            statement.close();
            connection.commit();
        } catch (SQLException | FileNotFoundException e) {
            log.debug(e.getMessage(), "Attachment cannot be send");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static void insertLog(String contentPath, int testId) {
        getConnection();
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader reader = null;
        PreparedStatement statement;
        try {
            log.debug("Sending log attachment to database");
            fis = new FileInputStream(contentPath);
            isr = new InputStreamReader(fis, "UTF-8");
            reader = new BufferedReader(isr);
            statement = connection.prepareStatement(DatabaseRequests.INSERT_LOG_QUERY);
            statement.setCharacterStream(1, reader);
            statement.setBoolean(2, false);
            statement.setInt(3, testId);
            statement.executeUpdate();
            statement.close();
            connection.commit();
        } catch (SQLException | FileNotFoundException | UnsupportedEncodingException e) {
            log.debug(e.getMessage(), "Attachment cannot be send");
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                log.debug(e.getMessage());
            }
        }
    }
}