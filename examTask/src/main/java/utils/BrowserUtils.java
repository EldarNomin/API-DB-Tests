package utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import enums.Parameters;
import org.openqa.selenium.Cookie;
import static aquality.selenium.browser.AqualityServices.getBrowser;

public class BrowserUtils {
    private static final ISettingsFile AUTHORIZATION_DATA = new JsonSettingsFile("authorizationData.json");
    private static final ISettingsFile CONFIG_DATA = new JsonSettingsFile("config.json");

    public static void authorisation(){
        getBrowser().goTo(String.format(
                CONFIG_DATA.getValue(Parameters.BASE_URI_WEB.getValue()).toString(),
                AUTHORIZATION_DATA.getValue(Parameters.API_LOGIN.getValue()),
                AUTHORIZATION_DATA.getValue(Parameters.API_PASSWORD.getValue())));
    }

    public static void sendToken(String cookie){
        getBrowser().getDriver().manage().addCookie(new Cookie("Cookie token", cookie));
    }
}