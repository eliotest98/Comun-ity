package controller.gestioneAccreditamento;

import controller.gestioneUtenza.GestioneUtenzaService;
import controller.gestioneUtenza.GestioneUtenzaServiceImpl;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Accreditamento;
import model.Utente;

/**
 * Servlet implementation class AccreditamentoServlet.
 */
@WebServlet("/AccreditamentoServlet")
public class AccreditamentoServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   *
   *@see HttpServlet#HttpServlet()
   */
  public AccreditamentoServlet() {
    super();
  }

  GestioneAccreditamentoService serviceA = new GestioneAccreditamentoServiceImpl();
  GestioneUtenzaService serviceU = new GestioneUtenzaServiceImpl();

  /**
   * doGet method implementation.
   *
   * @throws IOException      //
   * @throws ServletException //
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession(true);
    Utente user = (Utente) session.getAttribute("user");

    if (serviceU.isAdmin(user)) {
      RequestDispatcher requestDispatcher = 
          request.getRequestDispatcher("/WEB-INF/user/accreditamenti.jsp");

      List<Accreditamento> lista = serviceA.getAllUnexamined();

      request.setAttribute("accreditamenti", lista);
      request.setAttribute("link", "accreditamenti");
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
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }

}
