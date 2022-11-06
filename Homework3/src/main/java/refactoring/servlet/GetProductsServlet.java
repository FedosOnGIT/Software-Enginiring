package refactoring.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import refactoring.dto.ProductDto;
import refactoring.repository.Database;
import refactoring.utils.HTMLProcessor;

import java.util.List;

/**
 * @author akirakozov
 */
@RequiredArgsConstructor
public class GetProductsServlet extends HttpServlet {
    private final Database database;
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<ProductDto> products = database.getAll();
        HTMLProcessor.processProducts(response, products, null);
    }
}