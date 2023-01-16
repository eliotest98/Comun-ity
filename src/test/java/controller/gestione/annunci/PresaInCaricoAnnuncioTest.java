package controller.gestione.annunci;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import controller.gestione.annunci.service.GestioneAnnunciService;
import controller.gestione.annunci.service.GestioneAnnunciServiceImpl;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Annuncio;
import model.AnnuncioDao;
import model.Utente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit testing class for "InserimentoAnnuncio".
 * This class has been written following BLACK BOX
 * testing methodologies.
 */
public class PresaInCaricoAnnuncioTest {

  //Mock creation
  HttpServletRequest requestMock = mock(HttpServletRequest.class);
  HttpServletResponse responseMock = mock(HttpServletResponse.class);
  HttpSession sessionMock = mock(HttpSession.class);
  RequestDispatcher dispatcherMock = mock(RequestDispatcher.class);
  Utente utenteMock = mock(Utente.class);
  Annuncio annuncioMock = mock(Annuncio.class);
  PresaInCaricoAnnuncioServlet presaInCaricoMock = mock(PresaInCaricoAnnuncioServlet.class);
  GestioneAnnunciService serviceMock = mock(GestioneAnnunciServiceImpl.class);


  /**
   * Before each test, the session is simulated
   * and an new 'annuncio' entry is created for test purpose.
   */
  @BeforeEach
  public void setUp() {
    presaInCaricoMock = new PresaInCaricoAnnuncioServlet();
    when(requestMock.getSession(true)).thenReturn(sessionMock);
    Annuncio test = new Annuncio("nessuna", "biagiusMagno@gmail.com", "Test",
        "This is only for test purpose", "Via Vulture 1 Rapolla Potenza 85027");
    AnnuncioDao annuncioDao = new AnnuncioDao();
    annuncioDao.saveAnnuncio(test);
    when(requestMock.getParameter("annuncio")).thenReturn(test.getId().toString());

  }

  /*
   * After each test, the ad entry is eliminated.
   */
  @AfterEach
  public void tearDown() {
    AnnuncioDao annuncioDao = new AnnuncioDao();
    annuncioDao.deleteAnnuncio(annuncioDao.getLastId());
  }

  //User not logged.
  @Test
  public void userNotLoggedTest() throws ServletException, IOException {
    when(sessionMock.getAttribute("user")).thenReturn(null);
    presaInCaricoMock.doGet(requestMock, responseMock);
    verify(responseMock).sendRedirect("/Comun-ity/guest/login.jsp");
  }

  //User correctly logged.
  @Test
  public void utenteLoggatoCorrettamente() throws Exception {
    when(sessionMock.getAttribute("user")).thenReturn(new Utente());
    presaInCaricoMock.doGet(requestMock, responseMock);
    verify(responseMock).sendRedirect("ListaCommissionServlet");
  }

  //TC_CT_8: Ad successfully accepted.
  @Test
  public void adAcceptedOkTest() throws ServletException, IOException {
    when(sessionMock.getAttribute("user")).thenReturn(utenteMock);
    when(utenteMock.getMail()).thenReturn("eliotesting@gmail.com");
    when(requestMock.getRequestDispatcher("ListaAnnunciServlet")).thenReturn(dispatcherMock);
    presaInCaricoMock.doPost(requestMock, responseMock);
    verify(dispatcherMock).forward(requestMock, responseMock);
    verify(requestMock).setAttribute("success", "Annuncio accettato con successo");
  }

  //Ad not accepted correctly.
  @Test
  public void adAcceptedNotOkTest() throws ServletException, IOException {
    when(sessionMock.getAttribute("user")).thenReturn(utenteMock);
    when(utenteMock.getMail()).thenReturn(null);
    when(serviceMock.acceptAnnuncio(999L, null)).thenReturn(false);
    when(requestMock.getRequestDispatcher("ListaAnnunciServlet")).thenReturn(dispatcherMock);
    presaInCaricoMock.doPost(requestMock, responseMock);
    verify(dispatcherMock).forward(requestMock, responseMock);
    verify(requestMock).setAttribute("errore", "C'è stato un problema con il tuo annuncio");
  }

}
