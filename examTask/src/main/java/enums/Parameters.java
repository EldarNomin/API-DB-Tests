package enums;

import lombok.Getter;

public enum Parameters {
    API_LOGIN("/apiLogin"),
    API_PASSWORD("/apiPassword"),
    DB_USER("/dbUser"),
    DB_PASSWORD("/dbPassword"),
    BASE_URI_API("/baseUriApi"),
    BASE_URI_WEB("/baseUriWeb"),
    BASE_URI_DB("/baseUriDB"),
    VARIANT("/variant"),
    TEST_NAME("/testName"),
    SCREENSHOTS_PATH("/screenshotPath"),
    LOG_PATH("/logPath"),
    PROJECT_NAME("/projectName");

    @Getter
    private final String value;

    Parameters(String value) {
        this.value = value;
    }
}