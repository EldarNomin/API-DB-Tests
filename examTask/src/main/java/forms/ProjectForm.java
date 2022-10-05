package forms;

import database.models.TestTable;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import enums.ColumnNames;
import org.openqa.selenium.By;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProjectForm extends Form {
    private final ITextBox modalWindow = getElementFactory().getTextBox(By.xpath("//div[@class='messi-modal']"),
            "messi modal");

    private static final String FORM_LOC = "//div[@class='panel-heading']";
    private static final String TEST_XPATH = "//td//a[text()='%s']";
    private static final String TABLE_XPATH = "//table[@class='table']";

    public ProjectForm() {
        super(By.xpath(FORM_LOC), "locator");
    }

    public List<TestTable> getListWebTests() {
        List<ITextBox> columnNames = getTableColumnNames(getTable());
        List<ITextBox> rows = getTableRows(getTable());

        List<TestTable> testTables = new ArrayList<>();
        TestTable testTable = new TestTable();

        for (int i = 1; i < rows.size(); i++) {
            List<ITextBox> cols = rows.get(i).findChildElements(By.tagName("td"), ElementType.TEXTBOX);

            for (int j = 0; j < cols.size() - 1; j++) {

                if (Objects.equals(columnNames.get(j).getText(), ColumnNames.TEST_NAME.getValue())) {
                    testTable.setName(cols.get(j).getText());
                } else if (Objects.equals(columnNames.get(j).getText(), ColumnNames.TEST_METHOD.getValue())) {
                    testTable.setMethodName(cols.get(j).getText());
                } else if (Objects.equals(columnNames.get(j).getText(), ColumnNames.LATEST_TEST_START_TIME.getValue())) {
                    testTable.setStartTime(cols.get(j).getText());
                } else if (Objects.equals(columnNames.get(j).getText(), ColumnNames.LATEST_TEST_END_TIME.getValue())) {
                    testTable.setEndTime(cols.get(j).getText());
                }
            }
            testTables.add(testTable);
            testTable = new TestTable();
        }
        return testTables;
    }

    public List<String> getListWebTimes(List<TestTable> testTables) {
        List<String> listWebTimes = new ArrayList<>();
        for (TestTable testTable : testTables) {
            listWebTimes.add(testTable.getStartTime());
        }
        return listWebTimes;
    }

    private ITextBox getTable() {
        return getElementFactory().getTextBox(By.xpath(TABLE_XPATH), "table");
    }

    private List<ITextBox> getTableRows(ITextBox table) {
        return table.findChildElements(By.tagName("tr"), ElementType.TEXTBOX);
    }

    private List<ITextBox> getTableColumnNames(ITextBox table) {
        return table.findChildElements(By.tagName("th"), ElementType.TEXTBOX);
    }

    public ITextBox getTest(String testName) {
        return getElementFactory().getTextBox(By.xpath(String.format(TEST_XPATH, testName)), "test");
    }

    public void waitForNotDisplayedModalWindow() {
        modalWindow.state().waitForNotDisplayed();
    }
}