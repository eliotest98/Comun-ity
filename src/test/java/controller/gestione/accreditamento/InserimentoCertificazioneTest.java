package controller.gestione.accreditamento;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import controller.gestione.accreditamento.service.GestioneAccreditamentoService;
import controller.gestione.accreditamento.service.GestioneAccreditamentoServiceImpl;
import controller.gestione.utenza.service.GestioneUtenzaService;
import controller.gestione.utenza.service.GestioneUtenzaServiceImpl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import model.Accreditamento;
import model.AccreditamentoDao;
import model.Utente;
import model.UtenteDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Unit testing class for "InserimentoCertificazioneServlet".
 * This class has been written following BLACK BOX
 * testing methodologies.
 */
public class InserimentoCertificazioneTest {

  //Mock creation
  HttpServletRequest requestMock = mock(HttpServletRequest.class);
  HttpServletResponse responseMock = mock(HttpServletResponse.class);
  HttpSession sessionMock = mock(HttpSession.class);
  InserimentoCertificazioneServlet servletMock = mock(InserimentoCertificazioneServlet.class);
  GestioneAccreditamentoService accrServiceMock = mock(GestioneAccreditamentoServiceImpl.class);
  GestioneUtenzaService userServiceMock = mock(GestioneUtenzaServiceImpl.class);
  Utente utenteMock = mock(Utente.class);
  Accreditamento accrMock = mock(Accreditamento.class);
  RequestDispatcher dispatcherMock = mock(RequestDispatcher.class);
  Part partMock = mock(Part.class);
  ServletContext ctxMock = mock(ServletContext.class);
  File fileMock = mock(File.class);


  /**
   * Before each test new entries for 'utente' e 'annuncio'
   * are created for testing purpose and the session is simulated.
   * 
   */
  @BeforeEach
  public void setUp() {
    servletMock = new InserimentoCertificazioneServlet() {

      private static final long serialVersionUID = 1L;

      public ServletContext getServletContext() {
        return ctxMock; // return the mock
      }
      
    };
    when(requestMock.getSession(true)).thenReturn(sessionMock);

    Utente user = new Utente("cittadino", "nessuna", "Testa", "Testa", 18, "test@test.com", "test",
        "M", "3340000000", "test", LocalDate.now());
    UtenteDao utenteDao = new UtenteDao();
    utenteDao.saveUtente(user);

    Accreditamento test = new Accreditamento("test2@test.com", "idraulico",
        "aHR0cHM6Ly9naXRodWIuY29tL2VsaW90ZXN0OTgvQ29tdW4taXR5");
    AccreditamentoDao accrDao = new AccreditamentoDao();
    accrDao.saveAccreditamento(test);
    
    when(sessionMock.getAttribute("user")).thenReturn(user);
    when(requestMock.getRequestDispatcher("AreaPersonale")).thenReturn(dispatcherMock);
  }

  /*
   * After each test, both the accreditation request
   * and the user entries are eliminated.
   */
  @AfterEach
  void tearDown() {

    AccreditamentoDao accrDao = new AccreditamentoDao();
    accrDao.deleteAccreditamento("test@test.com");
    accrDao.deleteAccreditamento("test2@test.com");
    UtenteDao utenteDao = new UtenteDao();
    utenteDao.deleteUtente("test@test.com");
    utenteDao.deleteUtente("test2@test.com");
    utenteDao.deleteUtente("test3@test.com");
    
    String path = servletMock.getServletContext().getRealPath("/temp");
    File directory = new File(String.valueOf(path));
    directory.delete();
  }

  //User not logged.
  @Test
  public void userNotLoggedTest() throws ServletException, IOException {
    when(sessionMock.getAttribute("user")).thenReturn(null);
    servletMock.doGet(requestMock, responseMock);
    verify(responseMock).sendRedirect("/Comun-ity/guest/login.jsp");
  }

  //User correctly logged.
  @Test
  public void userLoggedTest() throws Exception {
    when(sessionMock.getAttribute("user")).thenReturn(new Utente());
    servletMock.doGet(requestMock, responseMock);
    verify(responseMock).sendRedirect("AreaPersonale");
  }

  //User has already submitted an accreditation request.
  @Test
  public void secondReqTest() throws Exception {

    Utente user = new Utente("cittadino", "nessuna", "Testa", "Testa", 18, "test2@test.com", "test",
        "M", "3340000000", "test", LocalDate.now());
    UtenteDao utenteDao = new UtenteDao();
    utenteDao.saveUtente(user);
    when(sessionMock.getAttribute("user")).thenReturn(user);

    servletMock.doPost(requestMock, responseMock);
    verify(requestMock).setAttribute("error",
        "Si può sottomettere una sola richiesta di accreditamento alla volta. "
            + "Aspetta che la precedente venga validata");
    verify(dispatcherMock).forward(requestMock, responseMock);
  }

  //User doesn't have a "cittadino" role.
  @Test
  public void wrongRoleTest() throws Exception {

    Utente user = new Utente("professionista", "nessuna", "Testa", "Testa", 18, "test3@test.com",
        "test", "M", "3340000000", "test", LocalDate.now());
    UtenteDao utenteDao = new UtenteDao();
    utenteDao.saveUtente(user);
    when(sessionMock.getAttribute("user")).thenReturn(user);


    servletMock.doPost(requestMock, responseMock);
    verify(requestMock).setAttribute("error",
        "Solo un cittadino puo' sottomettere una richiesta di accreditamento");
    verify(dispatcherMock).forward(requestMock, responseMock);
  }

  //Field "Abilitazione" empty.
  @Test
  public void emptyQualificationTest() throws Exception {

    when(requestMock.getParameter("abilitazione")).thenReturn("");

    servletMock.doPost(requestMock, responseMock);
    verify(requestMock).setAttribute("error", "Abilitazione non valida");
    verify(dispatcherMock).forward(requestMock, responseMock);
  }

  //Field "Abilitazione" out of bound (30).
  @Test
  public void qualificationLengthTest() throws Exception {

    when(requestMock.getParameter("abilitazione")).thenReturn(
        "Lorem ipsum dolor sit amet, consect");

    servletMock.doPost(requestMock, responseMock);
    verify(requestMock).setAttribute("error", "Abilitazione non valida");
    verify(dispatcherMock).forward(requestMock, responseMock);
  }
  
  //Accreditation request successfully submitted.
  @Test
  public void accreditationReqOkTest() throws Exception {

    when(requestMock.getParameter("abilitazione")).thenReturn("idraulico");
    when(requestMock.getPart("allegato")).thenReturn(partMock);
    when(partMock.getSubmittedFileName()).thenReturn("certificationReq.pdf");
    
    servletMock.doPost(requestMock, responseMock);
    verify(requestMock).setAttribute("success",
        "Richiesta sottomessa con successo, verra' controllata il prima possibile");
    verify(dispatcherMock).forward(requestMock, responseMock);
    
    File f = new File("nullcertificationReq.pdf");
    if (f.delete()) {
      System.out.println(f.getName() + " deleted");  
    } else {  
      System.out.println("failed");  
    }  
  }
  
}
