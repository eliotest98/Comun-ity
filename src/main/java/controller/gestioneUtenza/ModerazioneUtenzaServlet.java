package controller.gestioneUtenza;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.gestioneAnnunci.GestioneAnnunciService;
import controller.gestioneAnnunci.GestioneAnnunciServiceImpl;
import model.Utente;

@WebServlet("/ModerazioneUtenza")
public class ModerazioneUtenzaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	GestioneUtenzaService serviceUtenza = new GestioneUtenzaServiceImpl();
	GestioneAnnunciService serviceAnnunci = new GestioneAnnunciServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		String email = request.getParameter("mail");
		
		if(action.equalsIgnoreCase("ban")) {
			
			if(serviceUtenza.banUser(email) && serviceAnnunci.removeAllAvailableByUser(email)) {
				request.setAttribute("message", "L'utente: " + email + ", è stato bannato correttamente dal sistema.");
				
			}else request.setAttribute("message", "L'operazione di rimozione dell'utente: " + email + ", non è andata a buon fine.");
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("ListaUtenti");
			requestDispatcher.forward(request, response);
			
		}else if(action.equalsIgnoreCase("timeout")) {
			
			LocalDateTime duration = LocalDateTime.now().plusHours(24);
			
			if(serviceUtenza.timeoutUser(email, duration)) {
				request.setAttribute("success", "L'utente: " + email + ", è stato sospeso dal sistema fino a: " + duration + ".");
			}else request.setAttribute("success", "La sospensione non è andata a buon fine.");
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("ListaUtenti");
			requestDispatcher.forward(request, response);
		}
		
	}

}
