package controller.gestioneAnnunci;

import java.io.IOException;
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
 * Servlet implementation class CancellaAnnuncioServlet.
 */
@WebServlet("/CancellaAnnuncioServlet")
public class CancellaAnnuncioServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  GestioneAnnunciService service = new GestioneAnnunciServiceImpl();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    HttpSession session = req.getSession(true);

    Utente user = (Utente) session.getAttribute("user");

    if (user == null) {
      resp.sendRedirect("/Comun-ity/guest/login.jsp");
    } else {
      resp.sendRedirect("AreaPersonale");  //jsp cancellazione annuncio
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    HttpSession session = req.getSession(true);
    Utente user = (Utente) session.getAttribute("user");


    System.out.println(req.getParameter("annuncio"));

    Long id = Long.valueOf((String) req.getParameter("annuncio"));

    Annuncio annuncio = service.findAnnuncioById(id);

    if (autoreOk(user, annuncio)) {
      if (service.removeAnnuncio(annuncio.getId())) {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("AreaPersonale");
        req.setAttribute("success", "Annuncio rimosso con successo");
        requestDispatcher.forward(req, resp);
      } else {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("AreaPersonale");
        req.setAttribute("error", "Errore nella rimozione");
        requestDispatcher.forward(req, resp);
      }

    } else {
      RequestDispatcher requestDispatcher = req.getRequestDispatcher("AreaPersonale");
      req.setAttribute("error", "Non puoi rimuovere un annuncio di cui non sei l'autore");

      requestDispatcher.forward(req, resp);
    }

  }

  /**
   * Job author validation method.
   *
   * @param user     user
   * @param annuncio job
   * @return res value
   */
  public static boolean autoreOk(Utente user, Annuncio annuncio) {

    return annuncio.getAutore().equals(user.getMail());
  }

}
