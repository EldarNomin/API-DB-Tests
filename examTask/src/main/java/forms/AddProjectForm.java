package forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class AddProjectForm extends Form {

    private static final String ALERT_LOC = "//div[contains(@class,'alert-success')]";
    private static final String FORM_LOC = "//form[@id='addProjectForm']";

    private final ITextBox inputProjectName = getElementFactory().getTextBox(By.xpath("//input[@placeholder]"),
            "input for project name");

    private final IButton saveButton = getElementFactory().getButton(By.xpath("//button[@type='submit']"),
            "save project button");

    public AddProjectForm() {
        super(By.xpath(FORM_LOC), "locator");
    }

    public void inputProjectName(String projectName) {
        inputProjectName.clearAndType(projectName);
    }

    public void saveProject() {
        saveButton.clickAndWait();
    }

    public boolean isAlertSuccessDisplayed() {
        return getAlertSuccess().state().waitForDisplayed();
    }

    private ITextBox getAlertSuccess() {
        return getElementFactory().getTextBox(By.xpath(ALERT_LOC),
                "alert success");
    }
}