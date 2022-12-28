package controller.gestioneUtenza;

import java.io.IOException;
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
@WebServlet("/LoginServlet")
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
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String action = request.getParameter("action");
				
		HttpSession session = request.getSession(true);
		
		Utente user = (Utente) session.getAttribute("user");
		
		if(user == null) {
			response.sendRedirect("/Comun-ity/login/login.jsp"); //l'utente non è loggato
		}else if((Boolean) session.getAttribute("admin") != null) {
			if((Boolean) session.getAttribute("admin")) {
				//TODO l'utente è già loggato ed è admin
			}else {
				//TODO l'utente è già loggato e non è admin
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
		
		if(PASS_FORGOT.equals(action)) {
			//TODO reimpostare la password
		}else if(LOGIN.equals(action)){
			//TODO login
		}
			
	}

}
