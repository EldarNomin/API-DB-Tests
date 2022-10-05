package database;

import java.sql.SQLException;
import java.util.List;

public interface IDAO<T> {

    void insert(T t) throws SQLException;
}