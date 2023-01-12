package controller.gestione.annunci;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Utente;

/**
 * Servlet implementation class ListaAnnunciServlet.
 */
@WebServlet("/ListaAnnunciServlet")
public class ListaAnnunciServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   *
   *@see HttpServlet#HttpServlet()
   */
  public ListaAnnunciServlet() {
    super();
  }

  /**
   * doGet method implementation.
   *
   * @throws IOException //
   * @throws ServletException //
   *@see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession(true);

    Utente user = (Utente) session.getAttribute("user");

    if (user != null) {

      RequestDispatcher requestDispatcher =
          request.getRequestDispatcher("/WEB-INF/user/bachecaAnnunci.jsp");

      request.setAttribute("link", "bacheca");
      requestDispatcher.forward(request, response);
    } else {
      response.sendRedirect("/Comun-ity/guest/login.jsp");
    }
  }

  /**
   * doPost method implementation.
   *
   * @throws IOException //
   * @throws ServletException //
   *@see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }

}
