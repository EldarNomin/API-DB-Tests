package database;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import database.models.TestTable;
import enums.DatabaseRequests;
import enums.Parameters;
import lombok.SneakyThrows;
import static utils.DatabaseUtils.*;

public class TestDAO implements IDAO<TestTable> {

    private static final ISettingsFile CONFIG_DATA = new JsonSettingsFile("config.json");

    @SneakyThrows
    public void insert(TestTable testTable) {
        executeQuery(String.format(DatabaseRequests.INSERT_TEST_QUERY, testTable.getName(), testTable.getMethodName(),
                testTable.getStartTime(), testTable.getEndTime()));
        getQuery(String.format(DatabaseRequests.SELECT_ADDED_TEST, testTable.getName(), testTable.getMethodName(),
                testTable.getStartTime(), testTable.getEndTime()));

        insertImageAttach(CONFIG_DATA.getValue(Parameters.SCREENSHOTS_PATH.getValue()).toString(), testTable.getId());
        insertLog(CONFIG_DATA.getValue(Parameters.LOG_PATH.getValue()).toString(), testTable.getId());
    }
}