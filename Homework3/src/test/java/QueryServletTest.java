import org.junit.jupiter.api.Test;
import refactoring.servlet.QueryServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class QueryServletTest extends AbstractIntegrationTest {
    private StringWriter common(String command) throws SQLException, IOException {
        fill(Map.of("fisting", 300,
                "dead inside", 993));
        var request = mock(HttpServletRequest.class);
        var response = mock(HttpServletResponse.class);

        when(request.getParameter(eq("command"))).thenReturn(command);

        StringWriter stringWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        var servlet = new QueryServlet();
        servlet.doGet(request, response);

        verify(response, times(1)).setContentType(eq("text/html"));
        verify(response, times(1)).setStatus(eq(HttpServletResponse.SC_OK));
        verify(response, times(4)).getWriter();
        return stringWriter;
    }

    @Test
    public void maxTest() throws SQLException, IOException {
        var stringWriter = common("max");
        assertEquals("""
                <html><body>
                <h1>Product with max price: </h1>
                dead inside\t993</br>
                </body></html>
                """, stringWriter.toString());
    }

    @Test
    public void minTest() throws SQLException, IOException {
        var stringWriter = common("min");
        assertEquals("""
                <html><body>
                <h1>Product with min price: </h1>
                fisting\t300</br>
                </body></html>
                """, stringWriter.toString());
    }

    @Test
    public void sumTest() throws SQLException, IOException {
        var stringWriter = common("sum");
        assertEquals("""
                <html><body>
                Summary price:\040
                1293
                </body></html>
                """, stringWriter.toString());
    }

    @Test
    public void countTest() throws SQLException, IOException {
        var stringWriter = common("count");
        assertEquals("""
                <html><body>
                Number of products:\040
                2
                </body></html>
                """, stringWriter.toString());
    }
}
