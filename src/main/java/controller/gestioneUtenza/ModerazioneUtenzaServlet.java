package controller.gestioneUtenza;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.internal.ExpirableValue;

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
		String userIp = request.getRemoteAddr();
		
		System.out.println(action + " " + email);
		
		if(action.equalsIgnoreCase("ban")) {
			
			if(serviceUtenza.removeUtente(email) && serviceAnnunci.removeAllAvailableByUser(email)) {
				request.setAttribute("message", "L'utente: " + email + ", � stato rimosso correttamente dal sistema.");
				try (BufferedWriter writer = new BufferedWriter(new FileWriter("banned-IPs.txt", true))) {
					writer.write(userIp);
		            writer.newLine();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
			}else request.setAttribute("message", "L'operazione di rimozione dell'utente: " + email + ", non è andata a buon fine.");
			
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("ListaUtenti");
			requestDispatcher.forward(request, response);
			
		}else if(action.equalsIgnoreCase("timeout")) {
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String expirationDate = formatter.format(LocalDateTime.now().plusHours(24));
			
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("banned-IPs.txt", true))) {
				writer.write(userIp+" "+expirationDate);
	            writer.newLine();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			request.setAttribute("success", "L'utente: " + email + ", � stato sospeso dal sistema fino a: " + expirationDate + ".");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("ListaUtenti");
			requestDispatcher.forward(request, response);
		}
		
	}

}
