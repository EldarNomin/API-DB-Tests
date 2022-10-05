import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import enums.Parameters;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import static database.DatabaseConnection.closeConnection;

public abstract class BaseTest {

    protected final Logger log = AqualityServices.getLogger();
    protected final static ISettingsFile TEST_DATA = new JsonSettingsFile("testData.json");
    protected final static ISettingsFile CONFIG_DATA = new JsonSettingsFile("config.json");

    @BeforeMethod
    protected void beforeMethod() {
        getBrowser().maximize();
    }

    @BeforeSuite
    protected void beforeSuite() {
        RestAssured.requestSpecification = new RequestSpecBuilder().
                setBaseUri(CONFIG_DATA.getValue(Parameters.BASE_URI_API.getValue()).toString()).
                setContentType(ContentType.JSON).
                setAccept(ContentType.JSON).
                log(LogDetail.ALL).
                build();
    }

    @AfterMethod
    public void afterMethod() {
        if (AqualityServices.isBrowserStarted()) {
            AqualityServices.getBrowser().quit();
            closeConnection();
        }
    }

    protected Browser getBrowser() {
        return AqualityServices.getBrowser();
    }
}