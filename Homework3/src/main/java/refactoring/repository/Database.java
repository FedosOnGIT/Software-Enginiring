package refactoring.repository;

import refactoring.dto.ProductDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Database {
    private final String url;

    public Database(String url) {
        this.url = url;
    }

    public <T, R> R execute(SQLFunction<T> execution, Function<T, R> processing) {
        try (Connection c = DriverManager.getConnection(url)) {
            Statement statement = c.createStatement();
            R result = processing.apply(execution.apply(statement));
            statement.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <R> List<R> executeQuery(String query, Function<ResultSet, List<R>> processing) {
        return execute(statement -> statement.executeQuery(query), processing);
    }

    public void executeUpdate(String query) {
        execute(statement -> statement.executeUpdate(query), Function.identity());
    }

    public void init() {
        String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)";
        executeUpdate(sql);
    }

    public void insert(ProductDto product) {
        String sql = "INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + product.getName() + "\"," + product.getPrice() + ")";
        executeUpdate(sql);
    }

    private <T> List<T> get(String query, ResultSetFunction<T> processor) {
        return executeQuery(query, resultSet -> {
            try {
                List<T> values = new ArrayList<>();
                while (resultSet.next()) {
                    values.add(processor.apply(resultSet));
                }
                return values;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private List<ProductDto> getProducts(String query) {
        return get(query,
                resultSet -> ProductDto
                        .builder()
                        .name(resultSet.getString("name"))
                        .price(resultSet.getInt("price"))
                        .build()
        );
    }

    private List<Integer> getValues(String query) {
        return get(query,
                resultSet -> resultSet.getInt(1));
    }


    public List<ProductDto> getAll() {
        return getProducts("SELECT * FROM PRODUCT");
    }

    public List<ProductDto> getMax() {
        return getProducts("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
    }

    public List<ProductDto> getMin() {
        return getProducts("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
    }

    public List<Integer> getSum() {
        return getValues("SELECT SUM(price) FROM PRODUCT");
    }

    public List<Integer> getCount() {
        return getValues("SELECT COUNT(*) FROM PRODUCT");
    }
}
