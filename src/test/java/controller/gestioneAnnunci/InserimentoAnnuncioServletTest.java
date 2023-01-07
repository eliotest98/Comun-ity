package controller.gestioneAnnunci;

import static org.junit.jupiter.api.Assertions.*;
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

import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import controller.utility.DbConnection;
import model.Utente;

/*
 * 
 * 
 * Unit testing class for "InserimentoAnnuncioServlet".
 * This class has been written following BLACK BOX
 * testing methodologies.
 * 
 * 
 */
class InserimentoAnnuncioServletTest {


	//Mock creation
	HttpServletRequest requestMock = mock(HttpServletRequest.class);
	HttpServletResponse responseMock = mock(HttpServletResponse.class);
	HttpSession sessionMock = mock(HttpSession.class);
	Utente utenteMock= mock(Utente.class);
	InserimentoAnnuncioServlet servletMock = mock(InserimentoAnnuncioServlet.class);
	RequestDispatcher dispatcherMock = mock(RequestDispatcher.class);
	GestioneAnnunciService serviceMock= mock(GestioneAnnunciServiceImpl.class);

	/*
	 * Before each test a "Bacheca Annunci" session is simulated (1).
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
	
	// Test che verifica se l'utente non è loggato
		@Test
		public void utenteNonLoggatoCorrettamente() throws ServletException, IOException {
			when(requestMock.getSession(true)).thenReturn(sessionMock);
			when(sessionMock.getAttribute("user")).thenReturn(null);
			servletMock.doGet(requestMock, responseMock);
			verify(responseMock).sendRedirect("/Comun-ity/guest/login.jsp");
		}

		// Test che verifica se l'utente è loggato
		@Test
		public void utenteLoggatoCorrettamente() throws Exception {
			when(requestMock.getSession(true)).thenReturn(sessionMock);
			when(sessionMock.getAttribute("user")).thenReturn(new Utente());
			servletMock.doGet(requestMock, responseMock);
			verify(responseMock).sendRedirect("ListaAnnunci");
		}

	// Test case TC_CT_7.1.1: empty field 'abilitazioneRichiesta' for 'tipologia' = "lavoro".
	@Test
	void testRequiredQualificationNotSet() throws ServletException, IOException {
		when(requestMock.getParameter("professionista")).thenReturn(" ");
		when(requestMock.getParameter("professione")).thenReturn("nessuna");
		
		servletMock.doPost(requestMock, responseMock);
		
		verify(requestMock).setAttribute("error", "Controllare abilitazione richiesta rispetto alla tipologia");
		verify(dispatcherMock).forward(requestMock, responseMock);

	}

	/*
	 * // Test case TC_CT_7.1.2: field 'abilitazioneRichiesta' setted for
	 * 'tipologia' = "commissione" but not required.
	 * 
	 * @Test void testNonRequiredQualificationSet() throws ServletException,
	 * IOException {
	 * when(requestMock.getParameter("professionista")).thenReturn(null);
	 * when(requestMock.getParameter("professione")).thenReturn("idraulico");
	 * 
	 * servletMock.doPost(requestMock, responseMock);
	 * 
	 * verify(requestMock).setAttribute("error",
	 * "Controllare abilitazione richiesta rispetto alla tipologia");
	 * verify(dispatcherMock).forward(requestMock, responseMock); }
	 */

	// Test case TC_CT_7.1.3: empty field 'titolo'.
	@Test
	void testEmptyTitle() throws ServletException, IOException {
		when(requestMock.getParameter("professionista")).thenReturn(null);
		when(requestMock.getParameter("titolo")).thenReturn("");
		
		servletMock.doPost(requestMock, responseMock);
		
		verify(requestMock).setAttribute("error", "Titolo non valido");
		verify(dispatcherMock).forward(requestMock, responseMock);
	}
	
	// Test case TC_CT_7.1.4: field 'titolo' out of bound (30 chars).
	@Test
	void testTitleLength() throws ServletException, IOException {
		when(requestMock.getParameter("professionista")).thenReturn(null);
		when(requestMock.getParameter("titolo")).thenReturn("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		servletMock.doPost(requestMock, responseMock);
		
		verify(requestMock).setAttribute("error", "Titolo non valido");
		verify(dispatcherMock).forward(requestMock, responseMock);
	}
	
	// Test case TC_CT_7.1.5: field 'descrizione' empty.
	@Test
	void testDescriptionEmpty() throws ServletException, IOException {
		when(requestMock.getParameter("professionista")).thenReturn(null);
		when(requestMock.getParameter("titolo")).thenReturn("afbetbeb");
		when(requestMock.getParameter("descrizione")).thenReturn("");
		
		servletMock.doPost(requestMock, responseMock);
		
		verify(requestMock).setAttribute("error", "Descrizione non valida");
		verify(dispatcherMock).forward(requestMock, responseMock);
	}
	
	// Test case TC_CT_7.1.6: field 'descrizione' out of bound (280 chars).
	@Test
	void testDescriptionLength() throws ServletException, IOException {
		when(requestMock.getParameter("professionista")).thenReturn(null);
		when(requestMock.getParameter("titolo")).thenReturn("afbetbeb");
		when(requestMock.getParameter("descrizione")).thenReturn("wefwf4w4fefqergeqrgqegrqerfeqrvqegrgeqgetgeqvqeveqgeqrgeqtveqtvqegteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		
		servletMock.doPost(requestMock, responseMock);
		
		verify(requestMock).setAttribute("error", "Descrizione non valida");
		verify(dispatcherMock).forward(requestMock, responseMock);
	}
	
	// Test case TC_CT_7.1.7: field 'indirizzo' empty.
	@Test
	void testAddressEmpty() throws ServletException, IOException {
		when(requestMock.getParameter("professionista")).thenReturn(null);
		when(requestMock.getParameter("titolo")).thenReturn("afbetbeb");
		when(requestMock.getParameter("descrizione")).thenReturn("afbetbebwetbwrtbwrbthwrhbwrtbwr");
		when(requestMock.getParameter("indirizzo")).thenReturn("");
		
		servletMock.doPost(requestMock, responseMock);
		
		verify(requestMock).setAttribute("error", "Indirizzo non valido");
		verify(dispatcherMock).forward(requestMock, responseMock);
	}
	
	// Test case TC_CT_7.1.8: field 'indirizzo' out of bound (100 chars).
	@Test
	void testAddressLength() throws ServletException, IOException {
		when(requestMock.getParameter("professionista")).thenReturn(null);
		when(requestMock.getParameter("titolo")).thenReturn("afbetbeb");
		when(requestMock.getParameter("descrizione")).thenReturn("afbetbebwetbwrtbwrbthwrhbwrtbwr");
		when(requestMock.getParameter("indirizzo")).thenReturn("\"wefwf4w4fefqergeqrgqegrqerfeqrvqegrgeqgetgeqvqeveqgeqrgeqtveqtvqegteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee\"");
		
		servletMock.doPost(requestMock, responseMock);
		
		verify(requestMock).setAttribute("error", "Indirizzo non valido");
		verify(dispatcherMock).forward(requestMock, responseMock);
	}
	
	// Test case TC_CT_7.1.9: Successful new insert
	@Test
	void testInsertSuccess() throws ServletException, IOException {
		when(requestMock.getParameter("professionista")).thenReturn(null);
		when(utenteMock.getMail()).thenReturn("testServlet@biagio.com");
		when(requestMock.getParameter("titolo")).thenReturn("afbetbeb");
		when(requestMock.getParameter("descrizione")).thenReturn("afbetbebwetbwrtbwrbthwrhbwrtbwr");
		when(requestMock.getParameter("indirizzo")).thenReturn("afbetbebwetbwrtbwrbthwrhbwrtr");
		
		servletMock.doPost(requestMock, responseMock);
		
		verify(requestMock).setAttribute("success", "Annuncio inserito");
		verify(dispatcherMock).forward(requestMock, responseMock);
		 
	}
}
