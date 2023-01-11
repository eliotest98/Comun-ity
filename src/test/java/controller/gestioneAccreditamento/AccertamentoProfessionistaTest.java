package controller.gestioneAccreditamento;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import controller.gestioneUtenza.GestioneUtenzaService;
import controller.gestioneUtenza.GestioneUtenzaServiceImpl;
import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Accreditamento;
import model.AccreditamentoDAO;
import model.Utente;
import model.UtenteDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;




/**
 * Unit testing class for "InserimentoCertificazioneServlet".
 * This class has been written following BLACK BOX
 * testing methodologies.
 */
public class AccertamentoProfessionistaTest {

  //Mock creation
  HttpServletRequest requestMock = mock(HttpServletRequest.class);
  HttpServletResponse responseMock = mock(HttpServletResponse.class);
  HttpSession sessionMock = mock(HttpSession.class);
  AccertamentoProfessionistaServlet servletMock = mock(AccertamentoProfessionistaServlet.class);
  GestioneAccreditamentoService accrServiceMock = mock(GestioneAccreditamentoServiceImpl.class);
  GestioneUtenzaService userServiceMock = mock(GestioneUtenzaServiceImpl.class);
  Utente utenteMock = mock(Utente.class);
  Accreditamento accrMock = mock(Accreditamento.class);
  RequestDispatcher dispatcherMock = mock(RequestDispatcher.class);


  /**
   * Before each test a the session is simulated.
   */
  @BeforeEach
  public void setUp() {
    servletMock = new AccertamentoProfessionistaServlet();
    when(requestMock.getSession(true)).thenReturn(sessionMock);
    
    Utente user = new Utente(
        "cittadino", "nessuna", "Testa", "Testa", 18,
        "test@test.com", "test", "M", "3340000000", "test", LocalDate.now());
    UtenteDAO utenteDao = new UtenteDAO();
    utenteDao.saveUtente(user);
    
    Accreditamento test = new Accreditamento(
        "test@test.com", "idraulico", "aHR0cHM6Ly9naXRodWIuY29tL2VsaW90ZXN0OTgvQ29tdW4taXR5");
    AccreditamentoDAO accrDao = new AccreditamentoDAO();
    accrDao.saveAccreditamento(test);
    when(requestMock.getRequestDispatcher("AccreditamentoServlet"))
        .thenReturn(dispatcherMock);
  }

  /*
   * After each test, both the accreditation request
   * and the user entries are eliminated.
   */
  @AfterEach
  void tearDown() {
    
    AccreditamentoDAO accrDao = new AccreditamentoDAO();
    accrDao.deleteAccreditamento("test@test.com");
    UtenteDAO utenteDao = new UtenteDAO();
    utenteDao.deleteUtente("test@test.com");
  }

  //User not logged.
  @Test
  public void userNotLoggedTest() throws ServletException, IOException {
    when(sessionMock.getAttribute("user")).thenReturn(null);
    servletMock.doGet(requestMock, responseMock);
    verify(responseMock).sendRedirect("/Comun-ity/guest/login.jsp");
  }

  //User isn't an Admin.
  @Test
  public void notAnAdminTest() throws Exception {
    Utente user = new Utente();
    user.setRuolo("cittadino");
    when(sessionMock.getAttribute("user")).thenReturn(user);
    servletMock.doGet(requestMock, responseMock);
    verify(responseMock).sendRedirect("/Comun-ity/guest/login.jsp");
  }

  //User is an Admin.
  @Test
  public void adminTest() throws Exception {
    Utente user = new Utente();
    user.setRuolo("admin");
    when(sessionMock.getAttribute("user")).thenReturn(user);
    when(requestMock.getRequestDispatcher("/WEB-INF/user/accreditamenti.jsp"))
        .thenReturn(dispatcherMock);
    
    servletMock.doGet(requestMock, responseMock);

    verify(responseMock).sendRedirect("/WEB-INF/user/accreditamenti.jsp");
    verify(dispatcherMock).forward(requestMock, responseMock);
  }

  //Accreditation Request declined
  @Test
  public void declinedReqTest() throws Exception {
    when(requestMock.getParameter("accettato")).thenReturn("false");
    when(requestMock.getParameter("emailAccreditato")).thenReturn("test@test.com");
    
    servletMock.doPost(requestMock, responseMock);
    verify(requestMock).setAttribute("success", "La richiesta e' stata declinata");
    verify(dispatcherMock).forward(requestMock, responseMock);
  }
  
  //Accreditation Request accepted
  @Test
  public void acceptedReqTest() throws Exception {
    when(requestMock.getParameter("accettato")).thenReturn("true");
    when(requestMock.getParameter("emailAccreditato")).thenReturn("test@test.com");
    
    servletMock.doPost(requestMock, responseMock);
    verify(requestMock).setAttribute("success", "La richiesta e' stata accettata");
    verify(dispatcherMock).forward(requestMock, responseMock);
  }


}
