import org.junit.jupiter.api.Test;
import refactoring.servlet.AddProductServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class AddProductServletTest extends AbstractIntegrationTest {

    @Test
    public void doGetTest() throws IOException, SQLException {
        var request = mock(HttpServletRequest.class);
        var response = mock(HttpServletResponse.class);

        when(request.getParameter(eq("price"))).thenReturn("300");
        when(request.getParameter(eq("name"))).thenReturn("fisting");

        StringWriter stringWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        var servlet = new AddProductServlet();
        servlet.doGet(request, response);

        verify(response, times(1)).setContentType(eq("text/html"));
        verify(response, times(1)).setStatus(eq(HttpServletResponse.SC_OK));
        verify(response, times(1)).getWriter();

        assertEquals("OK\n", stringWriter.toString());

        List<Map.Entry<String, Integer>> products = new ArrayList<>();
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM PRODUCT");

            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                products.add(Map.entry(name, price));
            }
            rs.close();
            statement.close();
        }
        assertEquals(1, products.size());
        assertEquals("fisting", products.get(0).getKey());
        assertEquals(300, products.get(0).getValue());
    }
}