package controller.gestioneUtenza;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Utente;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
	GestioneUtenzaService service = new GestioneUtenzaServiceImpl();
		
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
						
		HttpSession session = request.getSession(true);
		
		Utente user = (Utente) session.getAttribute("user");
		
		if(user == null) {
			response.sendRedirect("/Comun-ity/guest/login.jsp"); //l'utente non � loggato
		}else {
			response.sendRedirect("HomeServlet");
		}
		
	}

	/**
	 * @throws IOException 
	 * @throws ServletException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String email = request.getParameter("mailUser");
		String password = request.getParameter("passwordUser");
		
		HttpSession session = request.getSession(true);
		
		try {
			if(service.checkCredentials(email, password)) {				
				
				Utente user = service.getAccountByEmail(email);
				
				session.setAttribute("user", user);
				
				if(service.IsAdmin(user))
					session.setAttribute("admin", true);
				else
					session.setAttribute("admin", false);
				
				response.sendRedirect("HomeServlet");
				
			}else {				
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/guest/login.jsp");
				request.setAttribute("message", "Credenziali non esistenti o errate, Riprova");
				
				requestDispatcher.forward(request, response);
			}
		} catch (IllegalArgumentException | InterruptedException | ExecutionException | IOException e) {
			e.printStackTrace();
		}
			
			
	}
			
	}


