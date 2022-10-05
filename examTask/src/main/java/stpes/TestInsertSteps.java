package stpes;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import database.TestDAO;
import database.models.TestTable;
import enums.ColumnLabels;
import enums.Parameters;

public class TestInsertSteps {

    private final static ISettingsFile TEST_DATA = new JsonSettingsFile("testData.json");

    public void insertTest(){
        TestDAO testDAO = new TestDAO();
        TestTable testTable = new TestTable();
        testTable.setName(ColumnLabels.NAME.getValue());
        testTable.setTestName(TEST_DATA.getValue(Parameters.TEST_NAME.getValue()).toString());
        testTable.setMethodName(ColumnLabels.METHOD_NAME.getValue());
        testTable.setBrowser(ColumnLabels.BROWSER.getValue());
        testDAO.insert(testTable);
    }
}