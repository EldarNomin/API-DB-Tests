package stpes;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import enums.JavaScriptsMethods;
import forms.AddProjectForm;
import forms.ProjectsListForm;
import org.testng.Assert;
import static aquality.selenium.browser.AqualityServices.getBrowser;

public class AddProjectSteps {
    private final Logger log = AqualityServices.getLogger();
    private final ProjectsListForm projectsListForm = new ProjectsListForm();
    private final AddProjectForm addProjectForm = new AddProjectForm();

    public void addProject(String projName) {
        log.info("Нажатие на +Add");
        projectsListForm.addProject();
        getBrowser().tabs().switchToLastTab();
        log.info("Ввод название проекта");
        addProjectForm.inputProjectName(projName);
        log.info("Сохранение проекта");
        addProjectForm.saveProject();
        Assert.assertTrue(addProjectForm.isAlertSuccessDisplayed(),
                "Сообщение об успешном сохранении не появилось");
        log.info("Закрытие окна добавления проекта с помощью JavaScript метода");
        getBrowser().executeScript(JavaScriptsMethods.CLOSE.getValue());
        getBrowser().refresh();
        Assert.assertFalse(addProjectForm.state().isDisplayed(), "Окно добавления проекта не закрыто");
        getBrowser().tabs().switchToLastTab();
    }
}