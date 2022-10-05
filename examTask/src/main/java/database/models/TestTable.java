package database.models;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import lombok.Data;
import enums.ColumnLabels;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class TestTable {
    private static final Logger log = AqualityServices.getLogger();
    private Integer id;
    private String name;
    private String testName;
    private Integer statusId;
    private String methodName;
    private Integer projectId;
    private Integer sessionId;
    private String startTime;
    private String endTime;
    private String env;
    private String browser;
    private Integer authorId;

    public TestTable() {}

    public TestTable(ResultSet resultSet) {

        try {
            id = resultSet.getInt(ColumnLabels.ID.getValue());
            name = resultSet.getString(ColumnLabels.NAME.getValue());
            statusId = resultSet.getInt(ColumnLabels.STATUS_ID.getValue());
            methodName = resultSet.getString(ColumnLabels.METHOD_NAME.getValue());
            projectId = resultSet.getInt(ColumnLabels.PROJECT_ID.getValue());
            sessionId = resultSet.getInt(ColumnLabels.SESSION_ID.getValue());
            startTime = resultSet.getString(ColumnLabels.START_TIME.getValue());
            endTime = resultSet.getString(ColumnLabels.END_TIME.getValue());
            env = resultSet.getString(ColumnLabels.ENV.getValue());
            browser = resultSet.getString(ColumnLabels.BROWSER.getValue());
            authorId = resultSet.getInt(ColumnLabels.AUTHOR_ID.getValue());
        } catch (SQLException e) {
            log.debug(e.getMessage());
        }
    }
}