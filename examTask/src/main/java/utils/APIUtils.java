package utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import enums.ColumnLabels;
import enums.Endpoints;
import enums.Parameters;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;

public class APIUtils {
    private final static ISettingsFile TEST_DATA = new JsonSettingsFile("testData.json");

    public static String getToken() {
        return given().
                queryParam(ColumnLabels.VARIANT.getValue(), TEST_DATA.getValue(Parameters.VARIANT.getValue())).
                when().post(Endpoints.GENERATE_TOKEN.getValue()).
                then().statusCode(HTTP_OK).
                extract().asString();
    }
}