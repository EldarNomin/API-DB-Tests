package enums;

import lombok.Getter;

public enum Endpoints {
    GENERATE_TOKEN("token/get");

    @Getter
    private final String value;

    Endpoints(String value) {
        this.value = value;
    }
}