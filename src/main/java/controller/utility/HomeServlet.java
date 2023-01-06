package controller.utility;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class HomeServlet.
 */
@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   *
   * @see HttpServlet#HttpServlet()
   */
  public HomeServlet() {
    super();
  }

  /**
   * doGet method implementation.
   *
   * @throws IOException //
   * @throws ServletException //
   *@see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession(true);

    if (session.getAttribute("user") != null) {

      RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/user/main.jsp");

      request.setAttribute("link", "dashboard");
      requestDispatcher.forward(request, response);

    } else {
      response.sendRedirect("/Comun-ity/guest/login.jsp");
    }
  }

  final String commissioni = "commissioni";
  final String lavori = "lavori";

  /**
   * doPost method implementation.
   *
   * @throws IOException //
   * @throws ServletException //
   *@see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }

}
