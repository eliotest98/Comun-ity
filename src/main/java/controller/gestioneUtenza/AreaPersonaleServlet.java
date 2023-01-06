package controller.gestioneUtenza;


import controller.gestioneAnnunci.GestioneAnnunciService;
import controller.gestioneAnnunci.GestioneAnnunciServiceImpl;
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
 * Servlet implementation class AreaPersonaleServlet.
 */
@WebServlet("/AreaPersonale")
public class AreaPersonaleServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   *
   *@see HttpServlet#HttpServlet()
   */
  public AreaPersonaleServlet() {
  }

  GestioneAnnunciService serviceAnnuncio = new GestioneAnnunciServiceImpl();

  /**
   * Default constructor.
   *
   *@see HttpServlet#HttpServlet()
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    HttpSession session = req.getSession(true);

    Utente user = (Utente) session.getAttribute("user");

    if (user != null) {
      List<Annuncio> lista = serviceAnnuncio.getAllByAuthor(user.getMail());
      RequestDispatcher requestDispatcher =
          req.getRequestDispatcher("/WEB-INF/user/areaPersonale.jsp");
      req.setAttribute("link", "areaPersonale");
      req.setAttribute("annunci", lista);
      requestDispatcher.forward(req, resp);
    } else {
      resp.sendRedirect("/Comun-ity/guest/login.jsp");
    }

  }

  /**
   * doGet method implementation.
   *
   *@see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doGet(req, resp);
  }

}
