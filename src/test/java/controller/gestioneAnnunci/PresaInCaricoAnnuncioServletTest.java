/**
 * 
 */
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Annuncio;
import model.AnnuncioDAO;
import model.Utente;
import org.mockito.Mockito;

import controller.utility.MailSender;

/**
 * @author miche
 *
 */
class PresaInCaricoAnnuncioServletTest {

	//Creazione dei mock
	HttpServletRequest requestMock = mock(HttpServletRequest.class);
	HttpServletResponse responseMock = mock(HttpServletResponse.class);
	HttpSession sessionMock = mock(HttpSession.class);
	RequestDispatcher dispatcherMock = mock(RequestDispatcher.class);
	Utente utenteMock = mock(Utente.class);
	Annuncio annuncioMock = mock(Annuncio.class);
	MailSender mailSenderMock = mock(MailSender.class);
	PresaInCaricoAnnuncioServlet presaInCaricoMock= mock(PresaInCaricoAnnuncioServlet.class);
	GestioneAnnunciService serviceMock = mock(GestioneAnnunciServiceImpl.class);


	//Prima di ogni test creo una nuova istanza della servlet e una sessione
	@BeforeEach
	public void setUp() {
		presaInCaricoMock= new PresaInCaricoAnnuncioServlet();
		when(requestMock.getSession(true)).thenReturn(sessionMock);
		when(requestMock.getParameter("from")).thenReturn("comunity.unisa@gmail.com");
		Annuncio test = new Annuncio("nessuna", "biagiusMagno@gmail.com", "Test", "This is only for test purpose", "Via Vulture 1 Rapolla Potenza 85027");
		AnnuncioDAO annuncioDAO = new AnnuncioDAO();
		annuncioDAO.saveAnnuncio(test);
		when(requestMock.getParameter("annuncio")).thenReturn(test.getId().toString());
		
	}

	//Dopo ogni test rendo l'annuncio di nuovo disponibile
	@AfterEach
	public void after() {
		AnnuncioDAO annuncioDAO= new AnnuncioDAO();
		annuncioDAO.deleteAnnuncio(annuncioDAO.getLastId());
	}


	//TC_CT_8: Presa in carico di una commissione
	@Test
	public void annuncioAccettatoCorrettamente() throws ServletException, IOException {
		when(sessionMock.getAttribute("user")).thenReturn(utenteMock);
		when(utenteMock.getMail()).thenReturn("eliotesting@gmail.com");
		when(requestMock.getRequestDispatcher("ListaAnnunciServlet")).thenReturn(dispatcherMock);
		presaInCaricoMock.doPost(requestMock, responseMock);
		verify(dispatcherMock).forward(requestMock, responseMock);
		verify(requestMock).setAttribute("success", "Annuncio accettato con successo");
	}

	//Test che verifica la mancata presa in carico dell'annuncio
	@Test
	public void annuncioNonAccettatoCorrettamente() throws ServletException, IOException {
		when(sessionMock.getAttribute("user")).thenReturn(utenteMock);
		when(utenteMock.getMail()).thenReturn(null);
		when(serviceMock.acceptAnnuncio(999L, null)).thenReturn(false);
		when(requestMock.getRequestDispatcher("ListaAnnunciServlet")).thenReturn(dispatcherMock);
		presaInCaricoMock.doPost(requestMock, responseMock);
		verify(dispatcherMock).forward(requestMock, responseMock);
		verify(requestMock).setAttribute("errore", "C'è stato un problema con il tuo annuncio");
	}

	@Test
	public void mailSenderError() throws ServletException, IOException {
		when(sessionMock.getAttribute("user")).thenReturn(utenteMock);
		when(requestMock.getParameter("from")).thenReturn("test@test.com");
		when(utenteMock.getMail()).thenReturn("eliotesting@gmail.com");
		when(requestMock.getRequestDispatcher("ListaAnnunciServlet")).thenReturn(dispatcherMock);
		presaInCaricoMock.doPost(requestMock, responseMock);
		verify(dispatcherMock).forward(requestMock, responseMock);
		verify(requestMock).setAttribute("success", "Annuncio accettato con successo");
	}
	
	//Test che verifica se l'utente non è loggato
	@Test
	public void utenteNonLoggatoCorrettamente() throws ServletException, IOException {
		when(sessionMock.getAttribute("user")).thenReturn(null);
		presaInCaricoMock.doGet(requestMock, responseMock);
		verify(responseMock).sendRedirect("/Comun-ity/guest/login.jsp");
	}

	//Test che verifica se l'utente è loggato
	@Test
	public void utenteLoggatoCorrettamente() throws Exception {
		when(sessionMock.getAttribute("user")).thenReturn(new Utente());
		presaInCaricoMock.doGet(requestMock, responseMock);
		verify(responseMock).sendRedirect("ListaCommissionServlet");
	}




}
