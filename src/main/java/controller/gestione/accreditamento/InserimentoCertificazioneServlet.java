package controller.gestione.accreditamento;

import controller.gestione.accreditamento.service.GestioneAccreditamentoService;
import controller.gestione.accreditamento.service.GestioneAccreditamentoServiceImpl;
import controller.gestione.utenza.service.GestioneUtenzaService;
import controller.gestione.utenza.service.GestioneUtenzaServiceImpl;
import controller.utils.MailSender;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Base64;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import model.Accreditamento;
import model.Utente;

/**
 * Servlet implementation class InserimentoCertificazione.
 */
@WebServlet("/InserimentoCertificazioneServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 16,
    maxRequestSize = 1024 * 1024 * 20)
public class InserimentoCertificazioneServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   *
   * @see HttpServlet#HttpServlet()
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

    Part part = request.getPart("allegato");

    Accreditamento accreditamento = serviceA.getByApplicant(utente);

    if (accreditamento == null) {

      if (user.getRuolo().equals("cittadino")) {

        if (abilitazioneOk(abilitazione)) {

          String path = getServletContext().getRealPath("/temp");

          File directory = new File(String.valueOf(path));

          if (!directory.exists()) {
            directory.mkdir();
          }

          String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
          part.write(path + fileName);

          String base = encodeFileToBase64Binary(path + fileName);

          Accreditamento newaccreditamento = new Accreditamento(utente, abilitazione, base);

          serviceA.saveAccreditamento(newaccreditamento);
          MailSender.notifyAccreditationReq(newaccreditamento);

          RequestDispatcher requestDispatcher = request.getRequestDispatcher("AreaPersonale");
          request.setAttribute("success",
              "Richiesta sottomessa con successo, verra' controllata il prima possibile");

          requestDispatcher.forward(request, response);


        } else {

          RequestDispatcher requestDispatcher = request.getRequestDispatcher("AreaPersonale");
          request.setAttribute("error", "Abilitazione non valida");

          requestDispatcher.forward(request, response);

        }

      } else {

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("AreaPersonale");
        request.setAttribute("error",
            "Solo un cittadino puo' sottomettere una richiesta di accreditamento");

        requestDispatcher.forward(request, response);

      }
    } else {

      RequestDispatcher requestDispatcher = request.getRequestDispatcher("AreaPersonale");
      request.setAttribute("error",
          "Si può sottomettere una sola richiesta di accreditamento alla volta. "
              + "Aspetta che la precedente venga validata");

      requestDispatcher.forward(request, response);
    }

  }

  /**
   * Checks if the qualification is valid.
   *
   * @param abilitazione is the qualification to check
   * @return true if valid
   */
  public static boolean abilitazioneOk(String abilitazione) {
    return abilitazione.length() >= 1 && abilitazione.length() <= 30;
  }

  /**
   * Encode a Binary file, generated from the file
   * at the given path, to a Base64 String.
   *
   * @param path is the path of the file to encode
   * @return A base64 String of the file
   */
  private static String encodeFileToBase64Binary(String path) throws IOException {

    File allegato = new File(path);
    if (!allegato.exists()) {
      allegato.createNewFile();
    }
    FileInputStream fis = new FileInputStream(allegato);
    byte[] byteData = new byte[(int) allegato.length()];

    fis.read(byteData);
    fis.close();

    return Base64.getEncoder().encodeToString(byteData);
  }
}
