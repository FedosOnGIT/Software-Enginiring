package refactoring.utils;

import jakarta.servlet.http.HttpServletResponse;
import refactoring.dto.ProductDto;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

public class HTMLProcessor {
    public static void endOfProcessing(HttpServletResponse response) {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private static <T> void process(HttpServletResponse response,
                                    List<T> values,
                                    String header,
                                    Function<T, String> toString) {
        try {
            response.getWriter().println("<html><body>");
            if (header != null) {
                response.getWriter().println(header);
            }
            for (var value : values) {
                response.getWriter().println(toString.apply(value));
            }
            response.getWriter().println("</body></html>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        endOfProcessing(response);
    }

    public static void processProducts(HttpServletResponse response, List<ProductDto> products, String header) {
        process(response, products, header, product -> product.getName() + "\t" + product.getPrice() + "</br>");
    }

    public static void processValues(HttpServletResponse response, List<Integer> values, String header) {
        process(response, values, header, value -> Integer.toString(value));
    }
}
