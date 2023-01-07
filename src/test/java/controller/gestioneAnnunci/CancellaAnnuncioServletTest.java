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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Annuncio;
import model.Utente;

/**
 * @author miche
 *
 */
class CancellaAnnuncioServletTest {

	//Mock creation
		HttpServletRequest requestMock = mock(HttpServletRequest.class);
		HttpServletResponse responseMock = mock(HttpServletResponse.class);
		HttpSession sessionMock = mock(HttpSession.class);
		Utente utenteMock= mock(Utente.class);
		Annuncio annuncioMock= mock(Annuncio.class);
		CancellaAnnuncioServlet servletMock = mock(CancellaAnnuncioServlet.class);
		RequestDispatcher dispatcherMock = mock(RequestDispatcher.class);
		GestioneAnnunciService serviceMock= mock(GestioneAnnunciServiceImpl.class);

		/*
		 * Before each test a "Bacheca Annunci" session is simulated (1).
		 */
		@BeforeEach
		public void setUp() {
			servletMock = new CancellaAnnuncioServlet();
			when(requestMock.getSession(true)).thenReturn(sessionMock);
			when(sessionMock.getAttribute("user")).thenReturn(utenteMock);
			when(requestMock.getRequestDispatcher("AreaPersonale")).thenReturn(dispatcherMock);
		}
		/*
		 * After each test, the ad entry is eliminated.
		 */
		@AfterEach
		void tearDown() {
			GestioneAnnunciServiceImpl service = new GestioneAnnunciServiceImpl();
			Annuncio annuncio= new Annuncio("nessuna", "e.testa7@studenti.unisa.it" , "tghrthr" , "tebrth" , "etrrtgert");
			annuncio.setId((long) 2);
			//service.insertAnnuncio(annuncio);
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
		

		// Autore e utente non corrispondono test
 
		@Test public void autoreUtenteNonCorrispondono() throws ServletException,
		IOException { 
			when(requestMock.getParameter("annuncio")).thenReturn("2");
			when(serviceMock.findAnnuncioById((long) 2)).thenReturn(annuncioMock);
			when(annuncioMock.getAutore()).thenReturn("e.testa7@studenti.unisa.it");
			when(utenteMock.getMail()).thenReturn("alefaster25@gmail.com");
			
			servletMock.doPost(requestMock, responseMock);
 
			assertEquals(false, CancellaAnnuncioServlet.autoreOk(utenteMock, annuncioMock));
			
			verify(requestMock).setAttribute("error","Non puoi rimuovere un annuncio di cui non sei l'autore");
			verify(dispatcherMock).forward(requestMock, responseMock); 
			}

		
		// Autore e utente non corrispondono test
		@Test
		public void autoreNonOkTest() {
			when(annuncioMock.getAutore()).thenReturn("e.testa7@studenti.unisa.it");
			when(utenteMock.getMail()).thenReturn("alefaster25@gmail.com");
			
			assertEquals(false, CancellaAnnuncioServlet.autoreOk(utenteMock, annuncioMock));
		}
		
		// Autore e utente non corrispondono test
		@Test
		public void autoreOkTest() {
			when(annuncioMock.getAutore()).thenReturn("e.testa7@studenti.unisa.it");
			when(utenteMock.getMail()).thenReturn("e.testa7@studenti.unisa.it");
					
			assertEquals(true, CancellaAnnuncioServlet.autoreOk(utenteMock, annuncioMock));
		}
		
		/*
		 * // Cancellazione effettuata
		 * 
		 * @Test public void cancellazioneEffettuata() throws ServletException,
		 * IOException { when(serviceMock.findAnnuncioById((long)
		 * 2)).thenReturn(annuncioMock);
		 * when(annuncioMock.getAutore()).thenReturn("e.testa7@studenti.unisa.it");
		 * when(utenteMock.getMail()).thenReturn("e.testa7@studenti.unisa.it");
		 * 
		 * servletMock.doPost(requestMock, responseMock);
		 * 
		 * verify(requestMock).setAttribute("success", "Annuncio rimosso con successo");
		 * verify(dispatcherMock).forward(requestMock, responseMock); }
		 */

}
