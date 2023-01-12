package controller.gestione.annunci;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.gestione.utenza.GestioneUtenzaService;
import controller.gestione.utenza.GestioneUtenzaServiceImpl;
import model.Annuncio;
import model.Utente;

/**
 * Servlet implementation class ArchivioServlet.
 */
@WebServlet("/ArchivioServlet")
public class ArchivioServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   *
   *@see HttpServlet#HttpServlet()
   */
  public ArchivioServlet() {
    super();
  }

  GestioneAnnunciService serviceAnnuncio = new GestioneAnnunciServiceImpl();
  GestioneUtenzaService serviceUtenza = new GestioneUtenzaServiceImpl();

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

    Utente user = (Utente) session.getAttribute("user");

    if (user != null && serviceUtenza.isAdmin(user)) {

      RequestDispatcher requestDispatcher =
          request.getRequestDispatcher("/WEB-INF/user/archivio.jsp");
      request.setAttribute("link", "archivio");
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

    HttpSession session = request.getSession(true);

    Utente user = (Utente) session.getAttribute("user");

    if (serviceUtenza.isAdmin(user)) {

      List<Annuncio> lista = null;

      String action = request.getParameter("action");
      Iterator<Annuncio> it;
      Annuncio annuncio;
      
      if (action == null) {
        action = "";
      }

      switch (action) {
        case commissioni:

          lista = serviceAnnuncio.getErrands();

          it = lista.iterator();

          while (it.hasNext()) {

            annuncio = (Annuncio) it.next();
            
            Utente utente = null;
            
            try {
              utente = serviceUtenza.getAccountByEmail(annuncio.getAutore());
            } catch (InterruptedException | ExecutionException | IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }

            response.getWriter().write("<div class=\"col\">\n"
                    + "<div class=\"card center\">\n"
                    + "<div class=\"additional\">\n"
                    + "<div class=\"user-card\">\n"
                    + "<img class='center'src=\"/Comun-ity/images/to-do-list.png\"width=\"100%\">\n"
                    + "</div>\n"
                    + "<div class=\"more-info\">\n"
                    + "<h3 class=\"text-center\">" + annuncio.getTitolo() + "</h3>\n"
                    + "<div class=\"row justify-content-center\">\n"
                    + "<form action=\"RimuoviAnnuncioServlet\" "
                    + "method=\"post\" style=\"width:auto;\">"
                    + "<input type=\"hidden\"  name=\"annuncio\" "
                    + "id=\"annuncio\" value=" + annuncio.getId() + ">"
                    + "<button type=\"submit\" class=\"btn btn-danger\" "
                    + "id=\"bottone\">Elimina</button>\n"
                    + "</form>"
                    + "</div>\n"
                    + "</div>\n"
                    + "</div>\n"
                    + "<div class=\"general\">\n"
                    + "<h3 class=\"text-center\">" + annuncio.getTitolo() + "</h3>\n"
                    + "<hr>\n"
                    + "<p>Autore: " + annuncio.getAutore() + "<br>\n"
                    + "Descrizione: " + annuncio.getDescrizione() + "<br>\n"
                    + "Indirizzo: " + annuncio.getIndirizzo() + "<br>\n"
                    + "Data Fine: " + annuncio.getDataFine() + "<br>\n"
                    + "Valutazione Utente: <span class=\"heading\" "
                    + "id=\"val\">" + utente.getReputazione() + "</span>\n"
                    + "<i class='bx bxs-star star1'></i>\n"
                    + "<i class='bx bxs-star star2'></i>\n"
                    + "<i class='bx bxs-star star3'></i>\n"
                    + "<i class='bx bxs-star star4'></i>\n"
                    + "<i class='bx bxs-star star5'></i></p>"
                    + "</p>\n"
                    + "<span class=\"more\">Muovi il mouse per rimuovere</span>\n"
                    + "</div>\n"
                    + "</div>\n"
                    + "</div>");
          }
          break;


        case lavori:

          lista = serviceAnnuncio.getJobs();

          it = lista.iterator();

          while (it.hasNext()) {

            annuncio = (Annuncio) it.next();

            response.getWriter().write(
                "<div class=\"col\">\n"
                    + "<div class=\"card center\">\n"
                    + "<div class=\"additional\">\n"
                    + "<div class=\"user-card\">\n"
                    + "<img class='center' src=\"/Comun-ity/images/hammer.png\" width=\"80%\">\n"
                    + "</div>\n"
                    + "<div class=\"more-info\">\n"
                    + "<h3 class=\"text-center\">" + annuncio.getTitolo() + "</h3>\n"
                    + "<div class=\"row justify-content-center\">\n"
                    + "<form action=\"RimuoviAnnuncioServlet\" method=\"post\" style=\"width:auto;\">"
                    + "<input type=\"hidden\"  name=\"annuncio\" id=\"annuncio\" value="
                    + annuncio.getId() + ">"
                    + "<button type=\"submit\"class=\"btn btn-danger\" "
                    + "id=\"bottone\">Elimina</button>\n"
                    + "</form>"
                    + "</div>\n" + "</div>\n" + "</div>\n"
                    + "<div class=\"general\">\n"
                    + "<h3 class=\"text-center\">" + annuncio.getTitolo() + "</h3>\n"
                    + "<hr>\n" + "<p>Autore: " + annuncio.getAutore() + "<br>\n"
                    + "Descrizione: " + annuncio.getDescrizione() + "<br>\n"
                    + "Indirizzo: " + annuncio.getIndirizzo() + "<br>\n"
                    + "Data Fine: " + annuncio.getDataFine() + "<br>\n"
                    + "Valutazione Utente: <span class=\"heading\" id=\"val\">"
                    + user.getReputazione() + "</span>\n"
                    + "<i class='bx bxs-star' id=\"star1\"></i>\n"
                    + "<i class='bx bxs-star' id=\"star2\"></i>\n"
                    + "<i class='bx bxs-star' id=\"star3\"></i>\n"
                    + "<i class='bx bxs-star' id=\"star4\"></i>\n"
                    + "<i class='bx bxs-star' id=\"star5\"></i></p>"
                    + "</p>\n" + "<span class=\"more\">Muovi il mouse per rimuovere</span>\n"
                    + "</div>\n" + "</div>\n" + "</div>");
          }
          break;
        default:
          RequestDispatcher requestDispatcher = 
              request.getRequestDispatcher("/WEB-INF/user/archivio.jsp");
          requestDispatcher.forward(request, response);
          break;
      }

    }
  }

}
