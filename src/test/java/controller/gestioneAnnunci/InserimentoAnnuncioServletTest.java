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

	//MongoDB connection
	MongoDatabase database = DbConnection.connectToDb();

	//Mock creation
	HttpServletRequest requestMock = mock(HttpServletRequest.class);
	HttpServletResponse responseMock = mock(HttpServletResponse.class);
	HttpSession sessionMock = mock(HttpSession.class);
	Utente utenteMock= mock(Utente.class);
	InserimentoAnnuncioServlet servletMock = mock(InserimentoAnnuncioServlet.class);
	RequestDispatcher dispatcherMock = mock(RequestDispatcher.class);

	/*
	 * Before each test a "Bacheca Annunci" session is simulated (1).
	 */
	@BeforeEach
	public void setUp() {
		when(requestMock.getSession()).thenReturn(sessionMock);
		when(sessionMock.getAttribute("user")).thenReturn(utenteMock);
	}
	/*
	 * After each test, the ad entry is eliminated.
	 */
	@AfterEach
	void tearDown() {
		try {
			database.getCollection("annuncio").deleteOne(Filters.eq("autore", "InserimentoAnnuncioServlet@test.com"));
			System.out.println("Annuncio eliminato!");
		}catch(MongoException e) {
			System.out.println("Errore durante l'eliminazione dell'annuncio" + e.getMessage());
		}
	}

	// Test case TC_CT_7.1.1: empty field 'abilitazioneRichiesta' for 'tipologia' = "lavoro".
	@Test
	void testRequiredQualificationNotSet() {
		when(requestMock.getParameter("professionista")).thenReturn("professionista");
		when(requestMock.getParameter("professione")).thenReturn("nessuna");
		InserimentoAnnuncioServlet test = new InserimentoAnnuncioServlet();
		IllegalArgumentException e =
				assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
		assertEquals("Il campo 'Professione richiesta' non può essere vuoto per un Lavoro.", e.getMessage());
	}

	// Test case TC_CT_7.1.2: field 'abilitazioneRichiesta' setted for 'tipologia' = "commissione" but not required.
	@Test
	void testNonRequiredQualificationSet() {
		when(requestMock.getParameter("professionista")).thenReturn("");
		when(requestMock.getParameter("professione")).thenReturn("idraulico");
		InserimentoAnnuncioServlet test = new InserimentoAnnuncioServlet();
		IllegalArgumentException e =
				assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
		assertEquals("Il campo 'Professione richiesta' non può essere settato per una commissione.", e.getMessage());
	}

	// Test case TC_CT_7.1.3: empty field 'titolo'.
	@Test
	void testEmptyTitle() {
		when(requestMock.getParameter("professionista")).thenReturn("");
		when(requestMock.getParameter("titolo")).thenReturn("");
		InserimentoAnnuncioServlet test = new InserimentoAnnuncioServlet();
		IllegalArgumentException e =
				assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
		assertEquals("Il campo 'Titolo' non può essere vuoto.", e.getMessage());
	}
	
	// Test case TC_CT_7.1.4: field 'titolo' out of bound (30 chars).
	@Test
	void testTitleLength() {
		when(requestMock.getParameter("professionista")).thenReturn("");
		when(requestMock.getParameter("titolo")).thenReturn("Lorem ipsum dolor sit amet, con");
		InserimentoAnnuncioServlet test = new InserimentoAnnuncioServlet();
		IllegalArgumentException e =
				assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
		assertEquals("Il campo 'Titolo' supera la lunghezza consentita.", e.getMessage());
	}
	
	// Test case TC_CT_7.1.5: field 'descrizione' empty.
	@Test
	void testDescriptionEmpty() {
		when(requestMock.getParameter("professionista")).thenReturn("");
		when(requestMock.getParameter("titolo")).thenReturn("Titolo");
		when(requestMock.getParameter("descrizione")).thenReturn("");
		InserimentoAnnuncioServlet test = new InserimentoAnnuncioServlet();
		IllegalArgumentException e =
				assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
		assertEquals("Il campo 'Descrizione' non può essere vuoto.", e.getMessage());
	}
	
	// Test case TC_CT_7.1.6: field 'descrizione' out of bound (280 chars).
	@Test
	void testDescriptionLength() {
		when(requestMock.getParameter("professionista")).thenReturn("");
		when(requestMock.getParameter("titolo")).thenReturn("Titolo");
		when(requestMock.getParameter("descrizione")).thenReturn("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. "
				+ "Aenean commodo ligula eget dolor. "
				+ "Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. "
				+ "Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat mass");
		InserimentoAnnuncioServlet test = new InserimentoAnnuncioServlet();
		IllegalArgumentException e =
				assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
		assertEquals("Il campo 'Descrizione' supera la lunghezza consentita.", e.getMessage());
	}
	
	// Test case TC_CT_7.1.7: field 'indirizzo' empty.
	@Test
	void testAddressEmpty() {
		when(requestMock.getParameter("professionista")).thenReturn("");
		when(requestMock.getParameter("titolo")).thenReturn("--Titolo--");
		when(requestMock.getParameter("descrizione")).thenReturn("--Descrizione--");
		when(requestMock.getParameter("indirizzo")).thenReturn("");
		InserimentoAnnuncioServlet test = new InserimentoAnnuncioServlet();
		IllegalArgumentException e =
				assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
		assertEquals("Il campo 'Indirizzo' non può essere vuoto.", e.getMessage());
	}
	
	// Test case TC_CT_7.1.8: field 'indirizzo' out of bound (100 chars).
	@Test
	void testAddressLength() {
		when(requestMock.getParameter("professionista")).thenReturn("");
		when(requestMock.getParameter("titolo")).thenReturn("--Titolo--");
		when(requestMock.getParameter("descrizione")).thenReturn("--Descrizione--");
		when(requestMock.getParameter("indirizzo")).thenReturn("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. "
				+ "Aenean commodo ligula eget dolor. Aenean ma");
		InserimentoAnnuncioServlet test = new InserimentoAnnuncioServlet();
		IllegalArgumentException e =
				assertThrows(IllegalArgumentException.class, () -> test.doPost(requestMock, responseMock));
		assertEquals("Il campo 'Indirizzo' supera la lunghezza consentita.", e.getMessage());
	}
	
	// Test case TC_CT_7.1.9: Successful new insert
	@Test
	void testInsertSuccess() throws ServletException, IOException {
		when(requestMock.getParameter("professionista")).thenReturn("");
		when(requestMock.getParameter("titolo")).thenReturn("Titolo");
		when(requestMock.getParameter("descrizione")).thenReturn("Descrizione");
		when(requestMock.getParameter("indirizzo")).thenReturn("Indirizzo");
		
		when(requestMock.getRequestDispatcher("ListaAnnunciServlet")).thenReturn(dispatcherMock);
		InserimentoAnnuncioServlet test = new InserimentoAnnuncioServlet();
		test.doPost(requestMock, responseMock);
		verify(requestMock).setAttribute("success", "Annuncio inserito con successo");
		 
	}
}
