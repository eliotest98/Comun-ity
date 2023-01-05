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
	Utente utenteMock= mock(Utente.class);
	PresaInCaricoAnnuncioServlet presaInCaricoMock= mock(PresaInCaricoAnnuncioServlet.class);
	
	
	//Prima di ogni test creo una nuova istanza della servlet e una sessione
	@BeforeEach
	  public void setUp() {
	    presaInCaricoMock= new PresaInCaricoAnnuncioServlet();
	    when(requestMock.getSession(true)).thenReturn(sessionMock);
	  }
	
	//Dopo ogni test rendo l'annuncio di nuovo disponibile
	@AfterEach
	public void after() {
		 AnnuncioDAO annuncioDAO= new AnnuncioDAO();
		 Annuncio annuncio= annuncioDAO.findAnnuncioById((long) 7);
		 annuncio.setIncaricato("nessuno");
		 annuncioDAO.updateAnnuncio(annuncio);
	}
	
	//Test che verifica la corretta presa in carico dell'annuncio
	@Test
	void utenteLoggatoCorrettamente() throws ServletException, IOException {
		 when(sessionMock.getAttribute("user")).thenReturn(utenteMock);
		 when(requestMock.getParameter("annuncio")).thenReturn("7");
		 when(utenteMock.getMail()).thenReturn("alefaster25@gmail.com");
		 when(requestMock.getRequestDispatcher("ListaAnnunciServlet")).thenReturn(dispatcherMock);
		 
		 presaInCaricoMock.doPost(requestMock, responseMock);
		 
		 verify(requestMock).setAttribute("success", "Annuncio accettato con successo");
		 
	}

	


}
