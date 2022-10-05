package forms;

import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class TestForm extends Form {

    private static final String START_TIME_UI_TEXT_REPLACEMENT = "Start time: ";
    private static final String END_TIME_UI_TEXT_REPLACEMENT = "End time: ";
    private static final String IMAGE_HREF_REPLACEMENT = "data:image/png;base64,";
    private final String testFieldsXPath = "//div[contains(@class,'col-md-4')]//div[contains(@class,'panel-default')][1]" +
            "//div[contains(@class,'list-group-item')]";
    private final ILabel lblProjectName = getElementFactory().getLabel(By.xpath(testFieldsXPath + "[1]" +
            "/p[contains(@class,'list-group-item-text')]"), "Test's project name");
    private final ILabel lblName = getElementFactory().getLabel(By.xpath(testFieldsXPath + "[2]/p[contains" +
            "(@class,'list-group-item-text')]"), "Test name");
    private final ILabel lblMethodName = getElementFactory().getLabel(By.xpath(testFieldsXPath +
            "[3]/p[contains(@class,'list-group-item-text')]"), "Test's method name");

    private final ILabel lblStartTime = getElementFactory().getLabel(By.xpath(testFieldsXPath +
            "[5]/p[contains(@class,'list-group-item-text')][1]"), "Test's start time");
    private final ILabel lblEndTime = getElementFactory().getLabel(By.xpath(testFieldsXPath +
            "[5]/p[contains(@class,'list-group-item-text')][2]"), "Test's end time");
    private final ILabel lblEnvironment = getElementFactory().getLabel(By.xpath(testFieldsXPath +
            "[6]/p[contains(@class,'list-group-item-text')]"), "Test's environment");
    private final ILabel lblBrowser = getElementFactory().getLabel(By.xpath(testFieldsXPath +
            "[7]/p[contains(@class,'list-group-item-text')]"), "Test's browser");
    private final ILink lnkScreenshot = getElementFactory().getLink(By.xpath("//div[contains(@class," +
            "'col-md-8')]//table[@class='table']//td/a[1]"), "Screenshot link");

    public TestForm() {
        super(By.xpath("//div[contains(@class,'container')]/div[contains(@class,'col-md')]"), "Test form");
    }

    public String getProjectName() {
        return lblProjectName.getText();
    }

    public String getTestName() {
        return lblName.getText();
    }

    public String getMethodName() {
        return lblMethodName.getText();
    }

    public String getStartTime() {
        return lblStartTime.getText().replace(START_TIME_UI_TEXT_REPLACEMENT, "");
    }

    public String getEndTime() {
        return lblEndTime.getText().replace(END_TIME_UI_TEXT_REPLACEMENT, "");
    }

    public String getEnv() {
        return lblEnvironment.getText();
    }

    public String getBrowserName() {
        return lblBrowser.getText();
    }

    public String getScreenshotLink() {
        return lnkScreenshot.getHref().replace(IMAGE_HREF_REPLACEMENT, "");
    }
}