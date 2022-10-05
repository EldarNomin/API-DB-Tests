package enums;

import lombok.Getter;

public enum ColumnLabels {
    ID("id"),
    NAME("name"),
    STATUS_ID("status_id"),
    METHOD_NAME("method_name"),
    PROJECT_ID("project_id"),
    SESSION_ID("session_id"),
    START_TIME("start_time"),
    END_TIME("end_time"),
    ENV("env"),
    BROWSER("browser"),
    AUTHOR_ID("author_id"),
    VARIANT("variant");

    @Getter
    private final String value;

    ColumnLabels(String value) {
        this.value = value;
    }

    public String getValueJson() {
        return "/" + value;
    }
}