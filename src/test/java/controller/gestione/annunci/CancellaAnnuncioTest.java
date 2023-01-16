package controller.gestione.annunci;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import controller.gestione.annunci.service.GestioneAnnunciService;
import controller.gestione.annunci.service.GestioneAnnunciServiceImpl;
import java.io.IOException;
import java.util.List;
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
 * Unit testing class for "CancellaAnnuncioServlet".
 * This class has been written following BLACK BOX
 * testing methodologies.
 */
public class CancellaAnnuncioTest {

  //Mock creation
  HttpServletRequest requestMock = mock(HttpServletRequest.class);
  HttpServletResponse responseMock = mock(HttpServletResponse.class);
  HttpSession sessionMock = mock(HttpSession.class);
  Utente utenteMock = mock(Utente.class);
  Annuncio annuncioMock = mock(Annuncio.class);
  CancellaAnnuncioServlet servletMock = mock(CancellaAnnuncioServlet.class);
  RequestDispatcher dispatcherMock = mock(RequestDispatcher.class);
  AnnuncioDao daoMock = mock(AnnuncioDao.class);
  GestioneAnnunciService serviceMock = mock(GestioneAnnunciServiceImpl.class);


  /**
   * Before each test, the session is simulated
   * and an new 'annuncio' entry is created for test purpose.
   */
  @BeforeEach
  public void setUp() {
    servletMock = new CancellaAnnuncioServlet();
    when(requestMock.getSession(true)).thenReturn(sessionMock);
    when(sessionMock.getAttribute("user")).thenReturn(utenteMock);
    Annuncio test = new Annuncio("nessuna", "biagiusMagno@gmail.com", "Test",
        "This is only for test purpose", "Via Vulture 1 Rapolla Potenza 85027");
    AnnuncioDao annuncioDao = new AnnuncioDao();
    annuncioDao.saveAnnuncio(test);
    when(requestMock.getParameter("annuncio")).thenReturn(test.getId().toString());
    when(requestMock.getRequestDispatcher("AreaPersonale")).thenReturn(dispatcherMock);
  }

  /*
   * After each test, the ad entry is eliminated.
   */
  @AfterEach
  void tearDown() {
    AnnuncioDao annuncioDao = new AnnuncioDao();
    List<Annuncio> list = annuncioDao.findAllAvailableByAuthor("biagiusMagno@gmail.com");
    if (!list.isEmpty()) {
      annuncioDao.deleteAnnuncio(list.get(0).getId());
    }
  }

  //User not logged.
  @Test
  public void userNotLoggedTest() throws ServletException, IOException {
    when(requestMock.getSession(true)).thenReturn(sessionMock);
    when(sessionMock.getAttribute("user")).thenReturn(null);
    servletMock.doGet(requestMock, responseMock);
    verify(responseMock).sendRedirect("/Comun-ity/guest/login.jsp");
  }

  //User correctly logged.
  @Test
  public void userLoggedTest() throws Exception {
    when(requestMock.getSession(true)).thenReturn(sessionMock);
    when(sessionMock.getAttribute("user")).thenReturn(new Utente());
    servletMock.doGet(requestMock, responseMock);
    verify(responseMock).sendRedirect("AreaPersonale");
  }


  //User email not corresponding with the ad's author.
  @Test
  public void authorNotOkTest() throws ServletException, IOException {
    when(utenteMock.getMail()).thenReturn("alefaster25@gmail.com");

    servletMock.doPost(requestMock, responseMock);

    verify(requestMock).setAttribute("error",
        "Non puoi rimuovere un annuncio di cui non sei l'autore");
    verify(dispatcherMock).forward(requestMock, responseMock);
  }

  //Successfull ad removal.
  @Test
  public void successfullEliminationTest() throws ServletException, IOException {
    when(utenteMock.getMail()).thenReturn("biagiusMagno@gmail.com");

    servletMock.doPost(requestMock, responseMock);

    verify(requestMock).setAttribute("success", "Annuncio rimosso con successo");
    verify(dispatcherMock).forward(requestMock, responseMock);
  }

}
