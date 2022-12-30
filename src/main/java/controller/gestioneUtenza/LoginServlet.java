package controller.gestioneUtenza;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Utente;
import model.UtenteDAO;

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
    
    private static final String PASS_FORGOT = "pass_forgot";
    private static final String LOGIN = "login";
    UtenteDAO utenteDao = new UtenteDAO();
	GestioneUtenzaService service = new GestioneUtenzaServiceImpl(utenteDao);
		
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
				
		HttpSession session = request.getSession(true);
		
		Utente user = (Utente) session.getAttribute("user");
		
		
		if(user == null) {
			response.sendRedirect("/Comun-ity/guest/login.jsp"); //l'utente non � loggato
		}else {
			if(service.IsAdmin(user)) {
				//TODO l'utente � gi� loggato ed � admin
			}else {
				//TODO l'utente � gi� loggato e non � admin
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		String action = request.getParameter("action");
		System.out.println("action: " + action);
		
		String email = request.getParameter("mailUser");
		String password = request.getParameter("passwordUser");
		
		if(PASS_FORGOT.equals(action)) {
			//TODO reimpostare la password
		}else if(LOGIN.equals(action)){
			
			//TODO
			//Verifica credenziali
			
			//Errate -> ritorno
			
			//Corrette
			//Corrette e cittadino/professionista
			//Corrette e admin -> Dashboard admin
			
		}
		
			
			
	}
			
	}


