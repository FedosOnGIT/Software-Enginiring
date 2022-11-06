package refactoring.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import refactoring.dto.ProductDto;
import refactoring.repository.Database;
import refactoring.utils.HTMLProcessor;

import java.io.IOException;
import java.util.List;

/**
 * @author akirakozov
 */
@RequiredArgsConstructor
public class QueryServlet extends HttpServlet {
    private final Database database;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String command = request.getParameter("command");

        switch (command) {
            case "max" -> {
                List<ProductDto> products = database.getMax();
                HTMLProcessor.processProducts(response, products, "<h1>Product with max price: </h1>");
            }
            case "min" -> {
                List<ProductDto> products = database.getMin();
                HTMLProcessor.processProducts(response, products, "<h1>Product with min price: </h1>");
            }
            case "sum" -> {
                List<Integer> prices = database.getSum();
                HTMLProcessor.processValues(response, prices, "Summary price: ");
            }
            case "count" -> {
                List<Integer> counts = database.getCount();
                HTMLProcessor.processValues(response, counts, "Number of products: ");
            }
            default -> {
                try {
                    response.getWriter().println("Unknown command: " + command);
                    response.setContentType("text/html");
                    response.setStatus(HttpServletResponse.SC_OK);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}