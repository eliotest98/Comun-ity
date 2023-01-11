package controller.gestioneAccreditamento;

import controller.gestioneUtenza.GestioneUtenzaService;
import controller.gestioneUtenza.GestioneUtenzaServiceImpl;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import model.Accreditamento;
import model.Utente;

/**
 * Servlet implementation class InserimentoCertificazione.
 */
@WebServlet("/InserimentoCertificazioneServlet")
public class InserimentoCertificazioneServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   *
   *@see HttpServlet#HttpServlet()
   */
  public InserimentoCertificazioneServlet() {
    super();
  }

  GestioneAccreditamentoService serviceA = new GestioneAccreditamentoServiceImpl();
  GestioneUtenzaService serviceU = new GestioneUtenzaServiceImpl();

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

    if (user == null) {
      response.sendRedirect("/Comun-ity/guest/login.jsp");
    } else {
      response.sendRedirect("AreaPersonale");
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

    HttpSession session = request.getSession(true);
    Utente user = (Utente) session.getAttribute("user");

    String utente = user.getMail();
    String abilitazione = request.getParameter("abilitazione");
    String allegato = request.getParameter("allegato");

    if (user.getRuolo().equals("cittadino")) {

      if (abilitazioneOk(abilitazione)) {

        if (allegatoOk(allegato)) {

          Accreditamento accreditamento = new Accreditamento(utente, abilitazione, allegato);

          serviceA.saveAccreditamento(accreditamento);

          RequestDispatcher requestDispatcher = request.getRequestDispatcher("AreaPersonale");
          request.setAttribute("success",
              "Richiesta sottomessa con successo, verra' controllata il prima possibile");

          requestDispatcher.forward(request, response);

        } else {

          RequestDispatcher requestDispatcher = request.getRequestDispatcher("AreaPersonale");
          request.setAttribute("error",
              "Errore nell'inserimento dell'allegato, deve essere un pdf da massimo 50MB");

          requestDispatcher.forward(request, response);

        }

      } else {

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("AreaPersonale");
        request.setAttribute("error", "Abilitazione non valida");

        requestDispatcher.forward(request, response);

      }

    } else {

      RequestDispatcher requestDispatcher = request.getRequestDispatcher(
          "/Comun-ity/guest/login.jsp");
      request.setAttribute("error",
          "Solo un cittadino puo' sottomettere una richiesta di accreditamento");

      requestDispatcher.forward(request, response);

    }
  }

  /**
   * Checks if abilitazione is valid.
   *
   * @param abilitazione is the qualification to check
   * @return true if valid
   */
  public static boolean abilitazioneOk(String abilitazione) {
    return abilitazione.length() >= 1 && abilitazione.length() <= 30;
  }

  /**
   * Decodes base64 file to binary and checks the size.
   *
   * @param allegato is the attached file to decode
   * @return true if the file's size is correct
   */
  public static boolean allegatoOk(String allegato) {

    byte[] data = DatatypeConverter.parseBase64Binary(allegato);

    return data.length >= 1 && data.length <= 26214400;

  }
}
