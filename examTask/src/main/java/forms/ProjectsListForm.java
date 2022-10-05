package forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import utils.StringUtils;

public class ProjectsListForm extends Form {

    private final StringUtils stringUtils = new StringUtils();
    private static final String FORM_LOC = "//div[@class='panel-heading']";
    private static final String BUTTON_LOC_PATTERN = "//a[text()='%s']";

    private final IButton buttonAddProject = getElementFactory().getButton(By.xpath("//button[@data-toggle='modal']"),
            "button add project");

    private final ILabel versionText = getElementFactory().
            getLabel(By.xpath("//p[contains(@class, 'footer-text')]//span"), "footer version text");

    public ProjectsListForm() {
        super(By.xpath(FORM_LOC), "locator");
    }

    public String getActualVariant() {
        return stringUtils.getLastNum(versionText.getText());
    }

    public void addProject() {
        buttonAddProject.clickAndWait();
    }

    private IButton getNeededProject(String projectName) {
        return getElementFactory().getButton(By.xpath(String.format(BUTTON_LOC_PATTERN, projectName)),
                "project " + projectName);
    }

    public boolean isProjectExist(String projectName) {
        return getElementFactory().getTextBox(By.xpath(String.format(BUTTON_LOC_PATTERN, projectName)),
                "project " + projectName).state().isDisplayed();
    }

    public void goToProject(String projectName) {
        getNeededProject(projectName).clickAndWait();
    }
}