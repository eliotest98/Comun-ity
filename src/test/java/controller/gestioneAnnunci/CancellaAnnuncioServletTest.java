/**
 * 
 */
package controller.gestioneAnnunci;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import model.Annuncio;
import model.AnnuncioDAO;
import model.Utente;


class CancellaAnnuncioServletTest {

	//Mock creation
	HttpServletRequest requestMock = mock(HttpServletRequest.class);
	HttpServletResponse responseMock = mock(HttpServletResponse.class);
	HttpSession sessionMock = mock(HttpSession.class);
	Utente utenteMock= mock(Utente.class);
	Annuncio annuncioMock= mock(Annuncio.class);
	CancellaAnnuncioServlet servletMock = mock(CancellaAnnuncioServlet.class);
	RequestDispatcher dispatcherMock = mock(RequestDispatcher.class);
	AnnuncioDAO daoMock = mock(AnnuncioDAO.class);
	GestioneAnnunciService serviceMock= mock(GestioneAnnunciServiceImpl.class);

	/*
	 * Before each test a "Bacheca Annunci" session is simulated (1).
	 */
	@BeforeEach
	public void setUp() {
		servletMock = new CancellaAnnuncioServlet();
		when(requestMock.getSession(true)).thenReturn(sessionMock);
		when(sessionMock.getAttribute("user")).thenReturn(utenteMock);
		Annuncio test = new Annuncio("nessuna", "biagiusMagno@gmail.com", "Test", "This is only for test purpose", "Via Vulture 1 Rapolla Potenza 85027");
		AnnuncioDAO annuncioDAO = new AnnuncioDAO();
		annuncioDAO.saveAnnuncio(test);
		when(requestMock.getParameter("annuncio")).thenReturn(test.getId().toString());
		when(requestMock.getRequestDispatcher("AreaPersonale")).thenReturn(dispatcherMock);
	}
	/*
	 * After each test, the ad entry is eliminated.
	 */
	@AfterEach
	void tearDown() {
		AnnuncioDAO annuncioDAO = new AnnuncioDAO();
		List<Annuncio> list = annuncioDAO.findAllAvailableByAuthor("biagiusMagno@gmail.com");
		if(!list.isEmpty()) {
			annuncioDAO.deleteAnnuncio(list.get(0).getId());
		}
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
		verify(responseMock).sendRedirect("AreaPersonale");
	}


	//User email not corresponding with the ad's author.

	@Test public void authorNotOKTest() throws ServletException,
	IOException { 
		when(utenteMock.getMail()).thenReturn("alefaster25@gmail.com");

		servletMock.doPost(requestMock, responseMock);

		verify(requestMock).setAttribute("error","Non puoi rimuovere un annuncio di cui non sei l'autore");
		verify(dispatcherMock).forward(requestMock, responseMock); 
	}

	// Successfull ad removal.
	@Test
	public void successfullEliminationTest() throws ServletException, IOException {
		when(utenteMock.getMail()).thenReturn("biagiusMagno@gmail.com");

		servletMock.doPost(requestMock, responseMock);
		
		verify(requestMock).setAttribute("success", "Annuncio rimosso con successo");
		verify(dispatcherMock).forward(requestMock, responseMock);
	}

	// Unsuccessfull ad removal.
	@Test
	public void unSuccessfullEliminationTest() throws ServletException, IOException {
		when(utenteMock.getMail()).thenReturn("biagiusMagno@gmail.com");
		when(annuncioMock.getId()).thenReturn(9999L);
		servletMock.doPost(requestMock, responseMock);

		verify(requestMock).setAttribute("error", "Errore nella rimozione");
		verify(dispatcherMock).forward(requestMock, responseMock);
	}

	

}
