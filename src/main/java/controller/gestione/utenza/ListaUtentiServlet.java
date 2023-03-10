package controller.gestione.utenza;

import controller.gestione.utenza.service.GestioneUtenzaService;
import controller.gestione.utenza.service.GestioneUtenzaServiceImpl;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Utente;

/**
 * Servlet implementation class ListaUtentiServlet.
 */
@WebServlet("/ListaUtenti")
@MultipartConfig
public class ListaUtentiServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   *
   * @see HttpServlet#HttpServlet()
   */
  public ListaUtentiServlet() {
    super();
  }

  GestioneUtenzaService service = new GestioneUtenzaServiceImpl();

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

    if (session.getAttribute("user") != null && (session.getAttribute(
        "admin") != null && (Boolean) session.getAttribute("admin"))) {

      RequestDispatcher requestDispatcher =
          req.getRequestDispatcher("/WEB-INF/user/listaUtenti.jsp");
      req.setAttribute("link", "utenti");
      requestDispatcher.forward(req, resp);
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

    HttpSession session = req.getSession(true);

    if (session.getAttribute("user") != null && (session.getAttribute(
        "admin") != null && (Boolean) session.getAttribute("admin"))) {

      ArrayList<Utente> listaUtenti;

      String search = req.getParameter("search");

      if (search.equals("")) {
        listaUtenti = (ArrayList<Utente>) service.getAllUsers();
      } else {
        listaUtenti = (ArrayList<Utente>) service.searchUser(search);
      }

      if (listaUtenti.isEmpty()) {

        resp.getWriter().write("<h1>Non ci sono utenti</h1>");

      } else {

        for (Utente user : listaUtenti) {

          resp.setContentType("application/x; charset=UTF-8");
          resp.getWriter().write("<div class=\"col\">\n"
              + "<div class=\"card center\">\n"
              + "<div class=\"additional\">\n"
              + "<div class=\"user-card\">\n"
              + "<img class='center' src=\"./images/user.png\">\n"
              + "</div>\n"
              + "<div class=\"more-info\">\n"
              + "<h3 class=\"text-center\">" + user.getNome() + " "
              + user.getCognome() + "</h3>\n"
              + "<div class=\"row justify-content-center\">\n"
              + "<form action=\"ModerazioneUtenza\" method=\"POST\">\n"
              + "<button type=\"submit\" class=\"button\" name=\"mail\" "
              + "id=\"timeout\" value=" + user.getMail() + " "
              + "onclick=\"timeoutUtente(this.value)\" data-bs-toggle=\"tooltip\" "
              + "data-bs-placement=\"top\" data-bs-title=\"Sospensione momentanea\">\n"
              + "<i class='bx bxs-time'></i>\n"
              + "</button>\n"
              + "<input type=\"hidden\" value=\"timeout\" name=\"action\">\n"
              + "</form>\n"
              + "<form action=\"ModerazioneUtenza\" method=\"POST\">\n"
              + "<button type=\"submit\" class=\"button\" id=\"ban\" "
              + "name=\"mail\" value=" + user.getMail() + " "
              + "onclick=\"banUtente(this.value)\" data-bs-toggle=\"tooltip\" "
              + "data-bs-placement=\"top\" data-bs-title=\"Ban utente\">\n"
              + "<i class='bx bxs-x-circle'></i>\n"
              + "</button>\n"
              + "<input type=\"hidden\" value=\"ban\" name=\"action\">\n"
              + "</form>\n"
              + "</div>\n"
              + "</div>\n"
              + "</div>\n"
              + "<div class=\"general\">\n"
              + "<h3 class=\"text-center\">" + user.getNome() + " " + user.getCognome() + "</h3>\n"
              + "<hr>\n"
              + "<p>Ruolo: " + user.getRuolo() + "<br>\n"
              + "Eta': " + user.getEta() + "<br>\n"
              + "Sesso: " + user.getSesso() + "<br>\n"
              + "Indirizzo: " + user.getIndirizzo() + "<br>\n"
              + "Email: " + user.getMail() + "\n"
              + "</p>\n"
              + "<span class=\"more\">Muovi il mouse per eseguire azioni</span>\n"
              + "</div>\n"
              + "</div>\n"
              + "</div>");
        }
      }


    } else {
      resp.sendRedirect("/Comun-ity/guest/login.jsp");
    }
  }
}
