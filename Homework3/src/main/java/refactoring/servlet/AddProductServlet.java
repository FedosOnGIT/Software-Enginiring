package refactoring.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import refactoring.dto.ProductDto;
import refactoring.repository.Database;

import java.io.IOException;

/**
 * @author akirakozov
 */
@RequiredArgsConstructor
public class AddProductServlet extends HttpServlet {
    private final Database database;
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        Integer price = Integer.parseInt(request.getParameter("price"));

        database.insert(ProductDto.builder().name(name).price(price).build());

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("OK");
    }
}