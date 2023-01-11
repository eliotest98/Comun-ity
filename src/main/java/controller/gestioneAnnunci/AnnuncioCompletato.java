package controller.gestioneAnnunci;

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
 * Servlet implementation class AnnuncioCompletato.
 */
@WebServlet("/AnnuncioCompletato")
public class AnnuncioCompletato extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   *
   *@see HttpServlet#HttpServlet()
   */
  public AnnuncioCompletato() {
    super();
  }

  GestioneAnnunciService serviceA = new GestioneAnnunciServiceImpl();

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
    } else {
      response.sendRedirect("HomeServlet");
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

    Long id = (Long.parseLong(request.getParameter("annuncio")));

    if (serviceA.markAsDone(id)) {

      RequestDispatcher requestDispatcher = request.getRequestDispatcher("HomeServlet");
      request.setAttribute("success", "Annuncio marcato come completato con successo");
      requestDispatcher.forward(request, response);

    } else {
      RequestDispatcher requestDispatcher = request.getRequestDispatcher("HomeServlet");
      request.setAttribute("error", "Non è stato possibile marcare l'annuncio come completato");
      requestDispatcher.forward(request, response);
    }
  }

}
