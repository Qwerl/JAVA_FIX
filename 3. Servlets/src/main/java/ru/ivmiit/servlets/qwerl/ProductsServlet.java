package ru.ivmiit.servlets.qwerl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.ivmiit.servlets.Box;

import static java.lang.Double.parseDouble;

/**
 * @author Askar
 */
public class ProductsServlet extends HttpServlet {

  private static final List<Box> boxes = new ArrayList<>();
  // language=HTML
  private static final String SIMPLE_HTML_FORM =
      "<html>\n" +
      "  <body>\n" +
      "    <form action=\"/products\" method=\"post\">\n" +
      "      Box weight: <input type=\"number\" step=\"any\" name=\"weight\"><br>\n" +
      "    </form>\n" +
      "  </body>\n" +
      "</html>\n";

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.getWriter()
        .append(SIMPLE_HTML_FORM)
        .append(printBoxes())
        .flush();
  }

  private String printBoxes() {
    return ProductsServlet.boxes.stream()
        .map(Box::getWeight)
        .map(Object::toString)
        .collect(Collectors.joining("</br>"));
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      double weight = parseDouble(request.getParameter("weight"));
      boxes.add(() -> weight);
      response.sendRedirect("/products");
    }
    catch (NumberFormatException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
  }

}