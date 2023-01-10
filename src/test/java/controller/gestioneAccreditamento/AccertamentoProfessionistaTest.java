package controller.gestioneAccreditamento;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.gestioneUtenza.GestioneUtenzaService;
import controller.gestioneUtenza.GestioneUtenzaServiceImpl;
import model.Accreditamento;
import model.AccreditamentoDAO;
import model.Utente;

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
    when(sessionMock.getAttribute("user")).thenReturn(utenteMock);
    when(requestMock.getRequestDispatcher(" ")).thenReturn(dispatcherMock); //Aggiungere JSP
  }

  /*
   * After each test, the accreditation request entry is eliminated.
   */
  @AfterEach
  void tearDown() {
    AccreditamentoDAO accrDao = new AccreditamentoDAO();
    accrDao.deleteAccreditamento("test@test.com");
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
    verify(responseMock).sendRedirect("/Comun-ity/guest/login.jsp"); //Aggiungere JSP
  }
  
  //User is an Admin.
  @Test
  public void adminTest() throws Exception {
    Utente user = new Utente();
    user.setRuolo("admin");
    when(sessionMock.getAttribute("user")).thenReturn(user);
    servletMock.doGet(requestMock, responseMock);
    verify(responseMock).sendRedirect(" "); //Aggiungere JSP
  }
  
  
}
