package controller.gestioneAnnunci;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Utente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * 
 * 
 * Unit testing class for "InserimentoAnnuncioServlet".
 * This class has been written following BLACK BOX
 * testing methodologies.
 * 
 * 
 */
public class InserimentoAnnuncioServletTest {

  //Mock creation
  HttpServletRequest requestMock = mock(HttpServletRequest.class);
  HttpServletResponse responseMock = mock(HttpServletResponse.class);
  HttpSession sessionMock = mock(HttpSession.class);
  Utente utenteMock = mock(Utente.class);
  InserimentoAnnuncioServlet servletMock = mock(InserimentoAnnuncioServlet.class);
  RequestDispatcher dispatcherMock = mock(RequestDispatcher.class);
  GestioneAnnunciService serviceMock = mock(GestioneAnnunciServiceImpl.class);

  /*
   * Before each test a "Bacheca Annunci" session is simulated.
   */
  @BeforeEach
  public void setUp() {
    servletMock = new InserimentoAnnuncioServlet();
    when(requestMock.getSession(true)).thenReturn(sessionMock);
    when(sessionMock.getAttribute("user")).thenReturn(utenteMock);
    when(requestMock.getRequestDispatcher("ListaAnnunciServlet")).thenReturn(dispatcherMock);
  }

  /*
   * After each test, the ad entry is eliminated.
   */
  @AfterEach
  void tearDown() {
    GestioneAnnunciServiceImpl service = new GestioneAnnunciServiceImpl();
    service.removeAllAvailableByUser("testServlet@biagio.com");
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
    verify(responseMock).sendRedirect("ListaAnnunci");
  }

  // Test case TC_CT_7.1.1: empty field 'abilitazioneRichiesta' for 'tipologia' = "lavoro".
  @Test
  void requiredQualificationNotSetTest() throws ServletException, IOException {
    when(requestMock.getParameter("professionista")).thenReturn("1");
    when(requestMock.getParameter("professione")).thenReturn("nessuna");

    servletMock.doPost(requestMock, responseMock);

    verify(requestMock).setAttribute("error",
        "Controllare abilitazione richiesta rispetto alla tipologia");
    verify(dispatcherMock).forward(requestMock, responseMock);

  }

  // Test case TC_CT_7.1.3: empty field 'titolo'.
  @Test
  void emptyTitleTest() throws ServletException, IOException {
    when(requestMock.getParameter("professionista")).thenReturn(null);
    when(requestMock.getParameter("titolo")).thenReturn("");

    servletMock.doPost(requestMock, responseMock);

    verify(requestMock).setAttribute("error", "Titolo non valido");
    verify(dispatcherMock).forward(requestMock, responseMock);
  }

  // Test case TC_CT_7.1.4: field 'titolo' out of bound (30 chars).
  @Test
  void titleLengthTest() throws ServletException, IOException {
    when(requestMock.getParameter("professionista")).thenReturn(null);
    when(requestMock.getParameter("titolo")).thenReturn("Lorem ipsum dolor sit amet, consect");

    servletMock.doPost(requestMock, responseMock);

    verify(requestMock).setAttribute("error", "Titolo non valido");
    verify(dispatcherMock).forward(requestMock, responseMock);
  }

  // Test case TC_CT_7.1.5: field 'descrizione' empty.
  @Test
  void descriptionEmptyTest() throws ServletException, IOException {
    when(requestMock.getParameter("professionista")).thenReturn(null);
    when(requestMock.getParameter("titolo")).thenReturn("Titolo");
    when(requestMock.getParameter("descrizione")).thenReturn("");

    servletMock.doPost(requestMock, responseMock);

    verify(requestMock).setAttribute("error", "Descrizione non valida");
    verify(dispatcherMock).forward(requestMock, responseMock);
  }

  // Test case TC_CT_7.1.6: field 'descrizione' out of bound (280 chars).
  @Test
  void descriptionLengthTest() throws ServletException, IOException {
    when(requestMock.getParameter("professionista")).thenReturn(null);
    when(requestMock.getParameter("titolo")).thenReturn("Titolo");
    when(requestMock.getParameter("descrizione")).thenReturn("Lorem ipsum dolor sit amet, "
        + "consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. "
        + "Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. "
        + "Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. "
        + "Nulla consequat mass");

    servletMock.doPost(requestMock, responseMock);

    verify(requestMock).setAttribute("error", "Descrizione non valida");
    verify(dispatcherMock).forward(requestMock, responseMock);
  }

  // Test case TC_CT_7.1.7: field 'indirizzo' empty.
  @Test
  void addressEmptyTest() throws ServletException, IOException {
    when(requestMock.getParameter("professionista")).thenReturn(null);
    when(requestMock.getParameter("titolo")).thenReturn("Titolo");
    when(requestMock.getParameter("descrizione")).thenReturn("Descrizione");
    when(requestMock.getParameter("indirizzo")).thenReturn("");

    servletMock.doPost(requestMock, responseMock);

    verify(requestMock).setAttribute("error", "Indirizzo non valido");
    verify(dispatcherMock).forward(requestMock, responseMock);
  }

  // Test case TC_CT_7.1.8: field 'indirizzo' out of bound (100 chars).
  @Test
  void addressLengthTest() throws ServletException, IOException {
    when(requestMock.getParameter("professionista")).thenReturn(null);
    when(requestMock.getParameter("titolo")).thenReturn("Titolo");
    when(requestMock.getParameter("descrizione")).thenReturn("Descrizione");
    when(requestMock.getParameter("indirizzo")).thenReturn("Lorem ipsum dolor sit amet, "
        + "consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean ma");

    servletMock.doPost(requestMock, responseMock);

    verify(requestMock).setAttribute("error", "Indirizzo non valido");
    verify(dispatcherMock).forward(requestMock, responseMock);
  }

  // Test case TC_CT_7.1.9: Successful new Errand insert
  @Test
  void errandInsertSuccessTest() throws ServletException, IOException {
    when(requestMock.getParameter("professionista")).thenReturn(null);
    when(utenteMock.getMail()).thenReturn("testServlet@biagio.com");
    when(requestMock.getParameter("titolo")).thenReturn("Titolo");
    when(requestMock.getParameter("descrizione")).thenReturn("Descrizione");
    when(requestMock.getParameter("indirizzo")).thenReturn("Via del Testing 404 Test Test 00000");

    servletMock.doPost(requestMock, responseMock);

    verify(requestMock).setAttribute("success", "Annuncio inserito");
    verify(dispatcherMock).forward(requestMock, responseMock);

  }

  // Test case TC_CT_7.1.9: Successful new Job insert
  @Test
  void jobInsertSuccessTest() throws ServletException, IOException {
    when(requestMock.getParameter("professionista")).thenReturn("1");
    when(requestMock.getParameter("professione")).thenReturn("idraulico");
    when(utenteMock.getMail()).thenReturn("testServlet@biagio.com");
    when(requestMock.getParameter("titolo")).thenReturn("Titolo");
    when(requestMock.getParameter("descrizione")).thenReturn("Descrizione");
    when(requestMock.getParameter("indirizzo")).thenReturn("Via del Testing 404 Test Test 00000");

    servletMock.doPost(requestMock, responseMock);

    verify(requestMock).setAttribute("success", "Annuncio inserito");
    verify(dispatcherMock).forward(requestMock, responseMock);

  }
}
