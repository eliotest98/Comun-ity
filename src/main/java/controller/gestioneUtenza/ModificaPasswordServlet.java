package controller.gestioneUtenza;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

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
		doGet(request, response);
		
		HttpSession session = request.getSession(true);
		Utente user = (Utente) session.getAttribute("user");
		
		String vecchiaPassword= request.getParameter("oldPass");
		String nuovaPassword= request.getParameter("newPass");
		
		try {
			if(service.checkCredentials(user.getMail(), vecchiaPassword)) {
				if(!vecchiaPassword.equals(nuovaPassword)) {
				try {
					service.changePassword(user.getMail(), nuovaPassword);
					response.getWriter().write("<h3>Password aggiornata con successo<h3>");
				} catch (IOException | ExecutionException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}else {
				response.getWriter().write("<h3>La password vecchia non corrisponde<h3>");
			}
		} catch (IllegalArgumentException | InterruptedException | ExecutionException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
