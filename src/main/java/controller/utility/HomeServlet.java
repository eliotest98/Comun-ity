package controller.utility;

import controller.gestione.annunci.GestioneAnnunciService;
import controller.gestione.annunci.GestioneAnnunciServiceImpl;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Annuncio;
import model.Utente;

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
   * @throws IOException      //
   * @throws ServletException //
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */

  GestioneAnnunciService serviceA = new GestioneAnnunciServiceImpl();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession(true);

    if (session.getAttribute("user") != null) {

      Utente user = (Utente) session.getAttribute("user");

      List<Annuncio> lista = serviceA.getAllByAppointeeNotDone(user.getMail());


      RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/user/main.jsp");
      request.setAttribute("annunci", lista);
      request.setAttribute("link", "dashboard");
      requestDispatcher.forward(request, response);

    } else {
      response.sendRedirect("/Comun-ity/guest/login.jsp");
    }
  }

  /**
   * doPost method implementation.
   *
   * @throws IOException      //
   * @throws ServletException //
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }

}
