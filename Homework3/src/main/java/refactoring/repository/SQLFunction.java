package refactoring.repository;

import java.sql.SQLException;
import java.sql.Statement;

@FunctionalInterface
public interface SQLFunction<T> {
    T apply(Statement statement) throws SQLException;
}
