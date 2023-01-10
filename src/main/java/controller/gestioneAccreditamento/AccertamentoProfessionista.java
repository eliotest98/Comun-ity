package controller.gestioneAccreditamento;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.gestioneUtenza.GestioneUtenzaService;
import controller.gestioneUtenza.GestioneUtenzaServiceImpl;
import model.Accreditamento;
import model.Utente;

/**
 * Servlet implementation class AccertamentoProfessionista
 */
@WebServlet("/AccertamentoProfessionista")
public class AccertamentoProfessionista extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public AccertamentoProfessionista() {
    super();
    // TODO Auto-generated constructor stub
  }

  GestioneUtenzaService service = new GestioneUtenzaServiceImpl();
  GestioneAccreditamentoService serviceA = new GestioneAccreditamentoServiceImpl();

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    HttpSession session = request.getSession(true);
    Utente user = (Utente) session.getAttribute("user");

    if (user != null) {

      if (!service.isAdmin(user)) {
        response.sendRedirect("/Comun-ity/guest/login.jsp");
      } else {
        response.sendRedirect(" "); //jsp verifica accreditamento
      }

    } else {
      response.sendRedirect("/Comun-ity/guest/login.jsp");
    }
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub

    boolean accettato = (boolean) request.getAttribute("accettato");
    Accreditamento accreditamento = (Accreditamento) request.getAttribute("accreditamento");

    if (accreditamento.getStato().equals("sottomessa")) {

      if (accettato) {
        serviceA.approveRequest(accreditamento.getRichiedente());

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("");   //jsp verifica accreditamento
        request.setAttribute("success", "La richiesta e' stata approvata");

        requestDispatcher.forward(request, response);
      } else {
        serviceA.declineRequest(accreditamento.getRichiedente());

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("");   //jsp verifica accreditamento
        request.setAttribute("success", "La richiesta e' stata declinata");

        requestDispatcher.forward(request, response);
      }
    } else {

      RequestDispatcher requestDispatcher = request.getRequestDispatcher("");   //jsp verifica accreditamento
      request.setAttribute("error", "La richiesta non e' in stato sottomessa");

      requestDispatcher.forward(request, response);

    }
  }

}
