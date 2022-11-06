package refactoring.repository;

import refactoring.dto.ProductDto;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
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

    public List<ProductDto> executeQuery(String query, Function<ResultSet, List<ProductDto>> processing) {
        return execute(statement -> statement.executeQuery(query), processing);
    }

    public int executeUpdate(String query) {
        return execute(statement -> statement.executeUpdate(query), Function.identity());
    }

    public void init() {
        String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)";
        executeUpdate(sql);
    }

    public int insert(ProductDto product) {
        String sql = "INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + product.getName() + "\"," + product.getPrice() + ")";
        return executeUpdate(sql);
    }

    public List<ProductDto> get(String query) {
        return executeQuery(query, resultSet -> {
            try {
                List<ProductDto> products = new ArrayList<>();
                while (resultSet.next()) {
                    products.add(ProductDto
                            .builder()
                            .name(resultSet.getString("name"))
                            .price(resultSet.getInt("price"))
                            .build());
                }
                return products;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<ProductDto> getAll() {
        return get("SELECT * FROM PRODUCT");
    }

    public List<ProductDto> getMax() {
        return get("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
    }

    public List<ProductDto> getMin() {
        return get("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
    }

    public List<ProductDto> getSum() {
        return get("SELECT SUM(price) FROM PRODUCT");
    }

    public List<ProductDto> getCount() {
        return get("SELECT COUNT(*) FROM PRODUCT");
    }
}
