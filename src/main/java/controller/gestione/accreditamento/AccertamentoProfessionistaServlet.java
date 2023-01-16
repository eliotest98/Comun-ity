package controller.gestione.accreditamento;

import controller.gestione.accreditamento.service.GestioneAccreditamentoService;
import controller.gestione.accreditamento.service.GestioneAccreditamentoServiceImpl;
import controller.gestione.utenza.service.GestioneUtenzaService;
import controller.gestione.utenza.service.GestioneUtenzaServiceImpl;
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
 * Servlet implementation class AccertamentoProfessionista.
 */
@WebServlet("/AccertamentoProfessionistaServlet")
public class AccertamentoProfessionistaServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   *
   *@see HttpServlet#HttpServlet()
   */
  public AccertamentoProfessionistaServlet() {
    super();
  }

  GestioneUtenzaService service = new GestioneUtenzaServiceImpl();
  GestioneAccreditamentoService serviceA = new GestioneAccreditamentoServiceImpl();

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

    if (user != null) {

      if (!service.isAdmin(user)) {
        response.sendRedirect("/Comun-ity/guest/login.jsp");
      } else {
        RequestDispatcher requestDispatcher = 
            request.getRequestDispatcher("/WEB-INF/user/accreditamenti.jsp");
        
        response.sendRedirect("/WEB-INF/user/accreditamenti.jsp");
        requestDispatcher.forward(request, response);
      }

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

    boolean accettato = Boolean.parseBoolean(request.getParameter("accettato"));

    String richiedente = request.getParameter("emailAccreditato");

    if (accettato) {

      serviceA.approveRequest(richiedente);

      RequestDispatcher requestDispatcher = request.getRequestDispatcher("AccreditamentoServlet");
      request.setAttribute("success", "La richiesta e' stata accettata");
      
      requestDispatcher.forward(request, response);
      
    } else {
      
      serviceA.declineRequest(richiedente);

      RequestDispatcher requestDispatcher = request.getRequestDispatcher("AccreditamentoServlet");
      request.setAttribute("success", "La richiesta e' stata declinata");

      requestDispatcher.forward(request, response);
    }

  }

}
