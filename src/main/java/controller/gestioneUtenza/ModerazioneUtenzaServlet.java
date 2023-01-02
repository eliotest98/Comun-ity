package controller.gestioneUtenza;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ModerazioneUtenza")
public class ModerazioneUtenzaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	GestioneUtenzaService service = new GestioneUtenzaServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		String email = request.getParameter("mail");
		
		System.out.println(action + " " + email);
		
		if(action.equalsIgnoreCase("ban")) {
			
			if(service.removeUtente(email)) 
				request.setAttribute("success", "L'utente: " + email + ", è stato rimosso correttamente dal sistema");
			else 
				request.setAttribute("error", "L'operazione di rimozione dell'utente: " + email + ", non è andata a buon fine");
			
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("ListaUtenti");
			requestDispatcher.forward(request, response);
			
		}else if(action.equalsIgnoreCase("tiemout")) {
			//TODO timeout utente
		}
		
	}

}
