package controller.gestione.annunci;

import controller.gestione.annunci.service.GestioneAnnunciService;
import controller.gestione.annunci.service.GestioneAnnunciServiceImpl;
import controller.gestione.utenza.service.GestioneUtenzaService;
import controller.gestione.utenza.service.GestioneUtenzaServiceImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Annuncio;
import model.Utente;

/**
 * Servlet implementation class ListaCommissioniServlet.
 */
@WebServlet("/ListaCommissioniServlet")
public class ListaCommissioniServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  GestioneAnnunciService service = new GestioneAnnunciServiceImpl();
  GestioneUtenzaService serviceUtenza = new GestioneUtenzaServiceImpl();

  /**
   * Default constructor.
   *
   *@see HttpServlet#HttpServlet()
   */
  public ListaCommissioniServlet() {
    super();
  }

  /**
   * doGet method implementation.
   *
   * @throws IOException //
   * @throws ServletException //
   *@see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    HttpSession session = req.getSession(true);
    Utente utente = (Utente) session.getAttribute("user");

    if (utente != null) {
      resp.sendRedirect("ListaAnnunci");
    } else {
      resp.sendRedirect("/Comun-ity/guest/login.jsp");
    }
  }

  /**
   * doPost method implementation.
   *
   * @throws IOException //
   * @throws ServletException //
   *@see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    ArrayList<Annuncio> commissioni;
    int cont;
    int size;

    HttpSession session = req.getSession(true);
    service.getAvailableErrands();
    Utente utente = (Utente) session.getAttribute("user");

    if (service.getAvailableErrands() == null) {

      resp.getWriter().write("<h3>Non ci sono commissioni disponibili</h3>");

    } else {

      commissioni = (ArrayList<Annuncio>) service.getAvailableErrands();
      size = commissioni.size();
      cont = 0;

      for (Annuncio annuncio : commissioni) {

        Utente user = null;

        if (!annuncio.getAutore().equals(utente.getMail())) {
          try {
            user = serviceUtenza.getAccountByEmail(annuncio.getAutore());
          } catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
          }

          assert user != null;
          resp.getWriter().write("<div class=\"col\">\n"
                  + "<div class=\"card center\">\n"
                  + "<div class=\"additional\">\n"
                  + "<div class=\"user-card\">\n"
                  + "<img class='center' src=\"/Comun-ity/images/to-do-list.png\" width=\"100%\">\n"
                  + "</div>\n"
                  + "<div class=\"more-info\">\n"
                  + "<h3 class=\"text-center\">" + annuncio.getTitolo() + "</h3>\n"
                  + "<div class=\"row justify-content-center\">\n"
                  + "<form action=\"PresaInCaricoAnnuncioServlet\" "
                  + "method=\"post\" style=\"width:auto;\">"
                  + "<input type=\"hidden\"  name=\"annuncio\" "
                  + "id=\"annuncio\" value=" + annuncio.getId() + ">"
                  + "<button type=\"submit\" class=\"btn btn-primary\" "
                  + "id=\"bottone\">Accetta</button>\n"
                  + "</form>"
                  + "</div>\n"
                  + "</div>\n"
                  + "</div>\n"
                  + "<div class=\"general\">\n"
                  + "<h3 class=\"text-center\">" + annuncio.getTitolo() + "</h3>\n"
                  + "<hr>\n"
                  + "<p>Autore: "
                  + user.getNome() + " " + user.getCognome()
                  + "<br>\n"
                  + "Descrizione: " + annuncio.getDescrizione() + "<br>\n"
                  + "Indirizzo: " + annuncio.getIndirizzo() + "<br>\n"
                  + "Data Fine: " + annuncio.getDataFine() + "<br>\n"
                  + "Valutazione Utente: <span class=\"heading\" "
                  + "id=\"val\">" + user.getReputazione() + "</span>\n"
                  + "<i class='bx bxs-star' id=\"star1\"></i>\n"
                  + "<i class='bx bxs-star' id=\"star2\"></i>\n"
                  + "<i class='bx bxs-star' id=\"star3\"></i>\n"
                  + "<i class='bx bxs-star' id=\"star4\"></i>\n"
                  + "<i class='bx bxs-star' id=\"star5\"></i></p>"
                  + "</p>\n"
                  + "<span class=\"more\">Muovi il mouse per accettare</span>\n"
                  + "</div>\n"
                  + "</div>\n"
                  + "</div>");
        } else {

          cont++;

        }
      }
      if (cont == size) {

        resp.getWriter().write("<h3>Non ci sono commissioni disponibili<h3>");

      }
    }
  }
}
