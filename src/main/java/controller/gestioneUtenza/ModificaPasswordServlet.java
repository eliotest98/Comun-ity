package controller.gestioneUtenza;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Utente;

/**
 * Servlet implementation class ModificaPasswordServlet
 */
@WebServlet("/ModificaPasswordServlet")
public class ModificaPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificaPasswordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    GestioneUtenzaService service = new GestioneUtenzaServiceImpl();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	private final String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		Utente user = (Utente) session.getAttribute("user");
		
		if(user==null) {
			response.sendRedirect("/Comun-ity/guest/login.jsp");
		}else {
			response.sendRedirect("/Comun-ity/AreaPersonale");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(true);
		Utente user = (Utente) session.getAttribute("user");
		
		String vecchiaPassword= request.getParameter("oldPass");
		String nuovaPassword= request.getParameter("newPass");
		
		try {
			if(service.checkCredentials(user.getMail(), vecchiaPassword)) {
				
				if(!vecchiaPassword.equals(nuovaPassword)) {
					
					if(patternMatches(nuovaPassword,passwordRegex)) {
						
						if(service.changePassword(user.getMail(), nuovaPassword)) {
							RequestDispatcher requestDispatcher = request.getRequestDispatcher("AreaPersonale");
							request.setAttribute("success", "Password aggiornata con successo");
							requestDispatcher.forward(request, response);
						}else {
							RequestDispatcher requestDispatcher = request.getRequestDispatcher("AreaPersonale");
							request.setAttribute("error", "Errore nell'aggiornamento password");
							requestDispatcher.forward(request, response);
						}
					}else {
						RequestDispatcher requestDispatcher = request.getRequestDispatcher("AreaPersonale");
						request.setAttribute("error", "Il formato della nuova password non è valido");
						requestDispatcher.forward(request, response);
					}
				}else {
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("AreaPersonale");
					request.setAttribute("error", "Le password sono uguali");
					requestDispatcher.forward(request, response);
				}
			}else {
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("AreaPersonale");
				request.setAttribute("error", "La vecchia password è errata");
				requestDispatcher.forward(request, response);
			}
		} catch (IllegalArgumentException | InterruptedException | ExecutionException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static boolean patternMatches(String string, String regexPattern) {
	    return Pattern.compile(regexPattern)
	      .matcher(string)
	      .matches();
	}

}
