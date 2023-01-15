package controller.gestione.annunci;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.gestione.annunci.service.GestioneAnnunciService;
import controller.gestione.annunci.service.GestioneAnnunciServiceImpl;
import model.Annuncio;
import model.Utente;

/**
 * Servlet implementation class InserimentoAnnuncioServlet.
 */
@WebServlet("/InserimentoAnnuncioServlet")
public class InserimentoAnnuncioServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  GestioneAnnunciService service = new GestioneAnnunciServiceImpl();

  /**
   * doGet method implementation.
   *
   * @throws IOException      //
   * @throws ServletException //
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {

    HttpSession session = req.getSession(true);

    Utente user = (Utente) session.getAttribute("user");

    if (user == null) {
      resp.sendRedirect("/Comun-ity/guest/login.jsp");
    } else {
      resp.sendRedirect("ListaAnnunci");
    }
  }

  /**
   * doPost method implementation.
   *
   * @throws IOException      //
   * @throws ServletException //
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {

    HttpSession session = req.getSession(true);

    Utente user = (Utente) session.getAttribute("user");

    String flag = req.getParameter("professionista");

    String autore = user.getMail();
    String abilitazioneRichiesta = flag == null ? "nessuna" : req.getParameter("professione");
    String tipologia = flag == null ? "commissione" : "lavoro";
    String titolo = req.getParameter("titolo");
    String descrizione = req.getParameter("descrizione");
    String indirizzo = req.getParameter("indirizzo");


    if (enablingOk(tipologia, abilitazioneRichiesta)) {

      if (titleOk(titolo)) {

        if (descriptionOk(descrizione)) {

          if (addressOk(indirizzo)) {

            if (service.insertAnnuncio(
                new Annuncio(abilitazioneRichiesta, autore, titolo, descrizione, indirizzo))) {
              
              RequestDispatcher requestDispatcher = req.getRequestDispatcher("ListaAnnunciServlet");
              req.setAttribute("success", "Annuncio inserito");
              
              requestDispatcher.forward(req, resp);
            } else {
              RequestDispatcher requestDispatcher = req.getRequestDispatcher("ListaAnnunciServlet");
              req.setAttribute("error", "Annuncio non inserito");
              
              requestDispatcher.forward(req, resp);
            }
          } else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("ListaAnnunciServlet");
            req.setAttribute("error", "Indirizzo non valido");

            requestDispatcher.forward(req, resp);
          }
        } else {
          RequestDispatcher requestDispatcher = req.getRequestDispatcher("ListaAnnunciServlet");
          req.setAttribute("error", "Descrizione non valida");

          requestDispatcher.forward(req, resp);
        }
      } else {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("ListaAnnunciServlet");
        req.setAttribute("error", "Titolo non valido");

        requestDispatcher.forward(req, resp);
      }
    } else {
      RequestDispatcher requestDispatcher = req.getRequestDispatcher("ListaAnnunciServlet");
      req.setAttribute("error", "Controllare abilitazione richiesta rispetto alla tipologia");

      requestDispatcher.forward(req, resp);
    }
  }

  /**
   * Checks if address is valid.
   *
   * @param address to check
   * @return true if valid
   */
  public static boolean addressOk(String address) {
    return address.length() >= 1 && address.length() <= 100;
  }

  /**
   * Checks if title is valid.
   *
   * @param titolo to check
   * @return true if valid
   */
  public static boolean titleOk(String titolo) {
    return titolo.length() >= 1 && titolo.length() <= 30;
  }


  /**
   * Checks if descrizione is valid.
   *
   * @param descrizione to check
   * @return true if valid
   */
  public static boolean descriptionOk(String descrizione) {
    return descrizione.length() >= 1 && descrizione.length() <= 280;
  }

  /**
   * Checks id tipologia and abilitazione can be matched.
   *
   * @param tipologia    to check
   * @param abilitazione to check
   * @return true if match is possible
   */
  public static boolean enablingOk(String tipologia, String abilitazione) {
    return !tipologia.equals("lavoro") || !abilitazione.equals("nessuna");
  }


}
