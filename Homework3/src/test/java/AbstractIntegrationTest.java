import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public abstract class AbstractIntegrationTest {
    @BeforeEach
    protected void init() throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)";
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.executeUpdate("DELETE FROM PRODUCT");
            stmt.close();
        }
    }

    protected void fill(Map<String, Integer> products) throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();
            for (var product : products.entrySet()) {
                String sql = "INSERT INTO PRODUCT " +
                        "(NAME, PRICE) VALUES (\"" + product.getKey() + "\"," + product.getValue() + ")";
                stmt.executeUpdate(sql);
            }
            stmt.close();
        }
    }
}
