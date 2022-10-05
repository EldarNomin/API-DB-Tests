package enums;

import lombok.Getter;

public enum ProjectsNames {
    NEXAGE("Nexage");

    @Getter
    private final String value;

    ProjectsNames(String value) {
        this.value = value;
    }
}