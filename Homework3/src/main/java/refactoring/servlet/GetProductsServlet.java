package refactoring.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import refactoring.dto.ProductDto;
import refactoring.repository.Database;

import java.io.IOException;
import java.util.List;

/**
 * @author akirakozov
 */
@RequiredArgsConstructor
public class GetProductsServlet extends HttpServlet {
    private final Database database;
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.getWriter().println("<html><body>");
            List<ProductDto> products = database.getAll();
            for (var product : products) {
                response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
            }
            response.getWriter().println("</body></html>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}