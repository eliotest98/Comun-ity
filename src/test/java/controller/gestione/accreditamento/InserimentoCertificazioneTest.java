package controller.gestione.accreditamento;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import controller.gestione.utenza.GestioneUtenzaService;
import controller.gestione.utenza.GestioneUtenzaServiceImpl;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.AccreditamentoDao;
import model.Utente;
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
  RequestDispatcher dispatcherMock = mock(RequestDispatcher.class);


  /**
   * Before each test a the session is simulated.
   */
  @BeforeEach
  public void setUp() {
    servletMock = new InserimentoCertificazioneServlet();
    when(requestMock.getSession(true)).thenReturn(sessionMock);
    when(sessionMock.getAttribute("user")).thenReturn(utenteMock);
    when(requestMock.getRequestDispatcher("AreaPersonale")).thenReturn(dispatcherMock);
  }

  /*
   * After each test, the accreditation request entry is eliminated.
   */
  @AfterEach
  void tearDown() {
    AccreditamentoDao accrDao = new AccreditamentoDao();
    accrDao.deleteAccreditamento("test@test.com");
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

  //User doesn't have a "cittadino" role.
  @Test
  public void wrongRoleTest() throws Exception {

    when(requestMock.getRequestDispatcher("/Comun-ity/guest/login.jsp")).thenReturn(dispatcherMock);

    when(utenteMock.getRuolo()).thenReturn("professionista");


    servletMock.doPost(requestMock, responseMock);
    verify(requestMock).setAttribute("error",
        "Solo un cittadino puo' sottomettere una richiesta di accreditamento");
    verify(dispatcherMock).forward(requestMock, responseMock);
  }

  //Field "Abilitazione" empty.
  @Test
  public void emptyQualificationTest() throws Exception {

    when(requestMock.getParameter("abilitazione")).thenReturn("");
    when(utenteMock.getRuolo()).thenReturn("cittadino");

    servletMock.doPost(requestMock, responseMock);
    verify(requestMock).setAttribute("error", "Abilitazione non valida");
    verify(dispatcherMock).forward(requestMock, responseMock);
  }

  //Field "Abilitazione" out of bound (30).
  @Test
  public void qualificationLengthTest() throws Exception {

    when(requestMock.getParameter("abilitazione"))
        .thenReturn("Lorem ipsum dolor sit amet, consect");
    when(utenteMock.getRuolo()).thenReturn("cittadino");

    servletMock.doPost(requestMock, responseMock);
    verify(requestMock).setAttribute("error", "Abilitazione non valida");
    verify(dispatcherMock).forward(requestMock, responseMock);
  }

  //Invalid attached file: empty.
  @Test
  public void notAttachedTest() throws Exception {

    when(requestMock.getParameter("abilitazione")).thenReturn("idraulico");
    when(requestMock.getParameter("allegato")).thenReturn("");
    when(utenteMock.getRuolo()).thenReturn("cittadino");

    servletMock.doPost(requestMock, responseMock);
    verify(requestMock).setAttribute("error",
        "Errore nell'inserimento dell'allegato, deve essere un pdf da massimo 50MB");
    verify(dispatcherMock).forward(requestMock, responseMock);
  }
  
  //Invalid attached file: too big.
  //  @Test
  //  public void attachedTooBigTest() throws Exception {
  //
  //    when(requestMock.getParameter("abilitazione")).thenReturn("idraulico");
  //    when(requestMock.getParameter("allegato")).thenReturn("--BASE 64 PDF > 50MB--");
  //    when(utenteMock.getRuolo()).thenReturn("cittadino");
  //
  //    servletMock.doPost(requestMock, responseMock);
  //    verify(requestMock).setAttribute("error",
  //        "Errore nell'inserimento dell'allegato, deve essere un pdf da massimo 50MB");
  //    verify(dispatcherMock).forward(requestMock, responseMock);
  //  }

  @Test
  public void accreditationReqOkTest() throws Exception {
  
    when(requestMock.getParameter("abilitazione")).thenReturn("idraulico");
    when(requestMock.getParameter("allegato"))
        .thenReturn("aHR0cHM6Ly9naXRodWIuY29tL2VsaW90ZXN0OTgvQ29tdW4taXR5");
    when(utenteMock.getRuolo()).thenReturn("cittadino");
  
    servletMock.doPost(requestMock, responseMock);
    verify(requestMock).setAttribute("success",
        "Richiesta sottomessa con successo, verra' controllata il prima possibile");
    verify(dispatcherMock).forward(requestMock, responseMock);
  }
  
}
