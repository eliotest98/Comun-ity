package controller.gestioneAnnunci;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.utility.MailSender;
import model.Annuncio;
import model.Utente;

/**
 * Servlet implementation class PresaInCaricoAnnuncioServlet.
 */
@WebServlet("/PresaInCaricoAnnuncioServlet")
public class PresaInCaricoAnnuncioServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  GestioneAnnunciService service = new GestioneAnnunciServiceImpl();

  /**
   * Default constructor.
   *
   *@see HttpServlet#HttpServlet()
   */
  public PresaInCaricoAnnuncioServlet() {
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
    Utente utente = (Utente) session.getAttribute("user");

    if (utente == null) {
      response.sendRedirect("/Comun-ity/guest/login.jsp");
      System.out.println("Utente non loggato");
    } else {
      response.sendRedirect("ListaCommissionServlet");
      System.out.println("Utente loggato");
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

    HttpSession session = request.getSession(true);
    Utente utente = (Utente) session.getAttribute("user");
    Long id = (Long.parseLong(request.getParameter("annuncio")));
    Annuncio annuncio= service.findAnnuncioById(id);

    if (service.acceptAnnuncio(id, utente.getMail())) {

      RequestDispatcher requestDispatcher = request.getRequestDispatcher("ListaAnnunciServlet");
      request.setAttribute("link", "bacheca");
      request.setAttribute("success", "Annuncio accettato con successo");
      
      MailSender.sendMail(utente, annuncio);
      
      requestDispatcher.forward(request, response);

    } else {

      RequestDispatcher requestDispatcher = request.getRequestDispatcher("ListaAnnunciServlet");
      request.setAttribute("link", "bacheca");
      request.setAttribute("errore", "C'è stato un problema con il tuo annuncio");
      requestDispatcher.forward(request, response);
    }

  }

}
