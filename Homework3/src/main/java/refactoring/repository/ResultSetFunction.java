package refactoring.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetFunction<T> {
    T apply(ResultSet resultSet) throws SQLException;
}
