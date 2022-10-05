package enums;

import lombok.Getter;

public enum JavaScriptsMethods {
    CLOSE("window.close();");

    @Getter
    private final String value;

    JavaScriptsMethods(String value) {
        this.value = value;
    }
}