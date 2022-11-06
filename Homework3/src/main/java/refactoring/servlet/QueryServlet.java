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
public class QueryServlet extends HttpServlet {
    private final Database database;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        switch (command) {
            case "max" -> {
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h1>Product with max price: </h1>");
                List<ProductDto> products = database.getMax();
                for (var product : products) {
                    response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
                }
                response.getWriter().println("</body></html>");
            }
            case "min" -> {
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h1>Product with min price: </h1>");
                List<ProductDto> products = database.getMin();
                for (var product : products) {
                    response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
                }
                response.getWriter().println("</body></html>");
            }
            case "sum" -> {
                response.getWriter().println("<html><body>");
                response.getWriter().println("Summary price: ");
                List<Integer> prices = database.getSum();
                for (var price : prices) {
                    response.getWriter().println(price);
                }
                response.getWriter().println("</body></html>");
            }
            case "count" -> {
                response.getWriter().println("<html><body>");
                response.getWriter().println("Number of products: ");
                List<Integer> counts = database.getCount();
                for (var count : counts) {
                    response.getWriter().println(count);
                }
                response.getWriter().println("</body></html>");
            }
            default -> response.getWriter().println("Unknown command: " + command);
        }
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}