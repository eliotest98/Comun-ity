package controller.gestione.annunci;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.gestione.utenza.GestioneUtenzaService;
import controller.gestione.utenza.GestioneUtenzaServiceImpl;
import model.Utente;

/**
 * Servlet implementation class RimuoviAnnuncioServlet.
 */
@WebServlet("/RimuoviAnnuncioServlet")
public class RimuoviAnnuncioServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   *
   * @see HttpServlet#HttpServlet()
   */
  public RimuoviAnnuncioServlet() {
    super();
  }

  GestioneUtenzaService serviceU = new GestioneUtenzaServiceImpl();
  GestioneAnnunciService serviceA = new GestioneAnnunciServiceImpl();

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
    Utente utente = (Utente) session.getAttribute("user");

    if (utente != null) {
      if (serviceU.isAdmin(utente)) {
        response.sendRedirect("ArchivioServlet");
      } else {
        response.sendRedirect("AreaPersonale");
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

    Long id = (Long.parseLong(request.getParameter("annuncio")));

    HttpSession session = request.getSession(true);
    Utente utente = (Utente) session.getAttribute("user");

    if (serviceU.isAdmin(utente)) {

      if (serviceA.removeAnnuncio(id)) {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("ArchivioServlet");
        request.setAttribute("success", "Annuncio rimosso con successo");
        requestDispatcher.forward(request, response);
      } else {

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("ArchivioServlet");
        request.setAttribute("errore", "Rimozione dell'annuncio fallita");
        requestDispatcher.forward(request, response);
      }

    }

  }

}
