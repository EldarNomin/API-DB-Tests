import database.models.TestTable;
import enums.DatabaseRequests;
import enums.Parameters;
import enums.ProjectsNames;
import forms.ProjectForm;
import forms.ProjectsListForm;
import forms.TestForm;
import org.assertj.core.api.SoftAssertions;
import org.testng.Assert;
import org.testng.annotations.Test;
import stpes.AddProjectSteps;
import stpes.TestInsertSteps;
import utils.*;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import net.bytebuddy.utility.RandomString;

public class ExamTask extends BaseTest {
    private final ProjectsListForm projectsListForm = new ProjectsListForm();
    private final ProjectForm projectForm = new ProjectForm();
    private final AddProjectSteps addProjectSteps = new AddProjectSteps();
    private final TestInsertSteps testInsertSteps = new TestInsertSteps();
    private final DatabaseUtils databaseUtils = new DatabaseUtils();
    private final TestTable testTable = new TestTable();
    private final TestForm testForm = new TestForm();
    private final static int RANDOM_STRING_LENGTH = 10;

    @Test
    public void UIDBTests() {
        log.info("Получение токена запросом к API согласно номеру варианта");
        String token = APIUtils.getToken();
        Assert.assertFalse(token.isEmpty(), "Токен не сгенерирован");

        log.info("Переход на сайт с авторизацией");
        BrowserUtils.authorisation();
        Assert.assertTrue(projectsListForm.state().waitForDisplayed(), "Страница проекта не открыта");
        log.info("Передача токена с помощью cookie");
        BrowserUtils.sendToken(token);
        log.info("Обновление страницы");
        getBrowser().refresh();
        Assert.assertEquals(projectsListForm.getActualVariant(),
                TEST_DATA.getValue(Parameters.VARIANT.getValue()), "Номер варианта не совпадает");

        log.info("Переход на страницу проекта 'Nexage' ");
        projectsListForm.goToProject(ProjectsNames.NEXAGE.getValue());
        getBrowser().waitForPageToLoad();
        projectForm.waitForNotDisplayedModalWindow();
        log.info("Получение тестов со страницы проекта");
        List<TestTable> testsListFromWeb = projectForm.getListWebTests();
        Assert.assertEquals(projectForm.getListWebTimes(testsListFromWeb), SortUtils.sortListReverseOrder(
                        projectForm.getListWebTimes(testsListFromWeb)),
                "Тесты, находящиеся на первой странице, не отсортированы по убыванию даты");

        log.info("Получение тестов с БД");
        List<TestTable> testsListFromDB = databaseUtils.get(String.format(DatabaseRequests.SQL_GET_TESTS, ProjectsNames.NEXAGE, testsListFromWeb.size()));
        Assert.assertEquals(testsListFromWeb.stream().map(TestTable::getName).collect(Collectors.toList()),
                testsListFromDB.stream().map(TestTable::getName).collect(Collectors.toList()),
                "Тесты, находящиеся на первой странице, не соответствуют тем, которые вернул запрос к БД");

        log.info("Возврат на предыдущую страницу");
        getBrowser().goBack();
        String randomProjectName = RandomString.make(RANDOM_STRING_LENGTH);
        addProjectSteps.addProject(randomProjectName);
        log.info("Обновление страницы");
        getBrowser().refresh();
        Assert.assertTrue(projectsListForm.isProjectExist(randomProjectName),
                "После обновления страницы проект не отображается в списке");

        log.info("Переход на страницу созданного проекта");
        projectsListForm.goToProject(TEST_DATA.getValue(Parameters.PROJECT_NAME.getValue()).toString());
        log.info("Добавление теста через БД(вместе с логом и скриншотом текущей страницы).");
        byte[] screenshot = getBrowser().getScreenshot();
        FileUtils.savePngScreenshot(screenshot);
        testInsertSteps.insertTest();
        Assert.assertTrue(projectForm.getTest(randomProjectName).state().waitForDisplayed(), "Тест не отображается");

        log.info("Переход на страницу созданного теста");
        projectForm.getTest(randomProjectName).state().waitForDisplayed();
        SoftAssertions testDataFromWeb = new SoftAssertions();
        testDataFromWeb.assertThat(testForm.getProjectName()).as(randomProjectName).isEqualTo(testForm.getProjectName(),
                "Название проекта не соответствует отправленному");
        testDataFromWeb.assertThat(testForm.getTestName()).as(testTable.getTestName()).isEqualTo(testForm.getTestName(),
                "Названия теста не соответствует отправленному");
        testDataFromWeb.assertThat(testForm.getMethodName()).as(testTable.getMethodName()).isEqualTo(testForm.getMethodName(),
                "Название метода не соответствует отправленному");
        testDataFromWeb.assertThat(testForm.getStartTime()).as(testTable.getStartTime()).isEqualTo(testForm.getStartTime(),
                "Время начала теста не совпадает");
        testDataFromWeb.assertThat(testForm.getEndTime()).as(testTable.getEndTime()).isEqualTo(testForm.getEndTime(),
                "Время окончания теста не совпадает");
        testDataFromWeb.assertThat(testForm.getEnv()).as(testTable.getEnv()).isEqualTo(testForm.getEnv(),
                "Тестовое окружение не соответствует отправленному");
        testDataFromWeb.assertThat(testForm.getBrowserName()).as(testTable.getBrowser()).isEqualTo(testForm.getBrowserName(),
                "Название браузера не соответствует отправленному");
        testDataFromWeb.assertThat(testForm.getScreenshotLink()).as(Base64.getEncoder().encodeToString(screenshot))
                .isEqualTo(testForm.getScreenshotLink(), "Скриншоты не совпадают");
        testDataFromWeb.assertAll();
    }
}