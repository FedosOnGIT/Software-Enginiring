import org.junit.jupiter.api.Test;
import refactoring.servlet.GetProductsServlet;

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

public class GetProductsServletTest extends AbstractIntegrationTest {
    @Test
    public void doGetTest() throws IOException, SQLException {
        fill(Map.of("fisting", 300));
        var request = mock(HttpServletRequest.class);
        var response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        var servlet = new GetProductsServlet();
        servlet.doGet(request, response);

        verify(response, times(1)).setContentType(eq("text/html"));
        verify(response, times(1)).setStatus(eq(HttpServletResponse.SC_OK));
        verify(response, times(3)).getWriter();

        assertEquals("""
                <html><body>
                fisting\t300</br>
                </body></html>
                """, stringWriter.toString());
    }
}
