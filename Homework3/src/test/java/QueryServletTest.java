import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import refactoring.servlet.QueryServlet;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class QueryServletTest extends AbstractIntegrationTest {
    @SneakyThrows
    private StringWriter common(String command, Integer timesOfWriter) {
        fill(Map.of("fisting", 300,
                "dead inside", 993));
        var request = mock(HttpServletRequest.class);
        var response = mock(HttpServletResponse.class);

        when(request.getParameter(eq("command"))).thenReturn(command);

        StringWriter stringWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        var servlet = new QueryServlet(database);
        servlet.doGet(request, response);

        verify(response, times(1)).setContentType(eq("text/html"));
        verify(response, times(1)).setStatus(eq(HttpServletResponse.SC_OK));
        verify(response, times(timesOfWriter)).getWriter();
        return stringWriter;
    }

    @Test
    public void maxTest() {
        var stringWriter = common("max", 4);
        assertEquals("""
                <html><body>
                <h1>Product with max price: </h1>
                dead inside\t993</br>
                </body></html>
                """, stringWriter.toString());
    }

    @Test
    public void minTest() {
        var stringWriter = common("min", 4);
        assertEquals("""
                <html><body>
                <h1>Product with min price: </h1>
                fisting\t300</br>
                </body></html>
                """, stringWriter.toString());
    }

    @Test
    public void sumTest() {
        var stringWriter = common("sum", 4);
        assertEquals("""
                <html><body>
                Summary price:\040
                1293
                </body></html>
                """, stringWriter.toString());
    }

    @Test
    public void countTest() {
        var stringWriter = common("count", 4);
        assertEquals("""
                <html><body>
                Number of products:\040
                2
                </body></html>
                """, stringWriter.toString());
    }

    @Test
    public void unknownMethodTest() {
        var stringWriter = common("avg", 1);
        assertEquals("""
                Unknown command: avg
                """, stringWriter.toString());
    }
}
