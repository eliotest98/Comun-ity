package controller.gestioneAnnunci;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.*;

/**
 * Servlet implementation class PresaInCaricoAnnuncioServlet
 */
@WebServlet("/PresaInCaricoAnnuncioServlet")
public class PresaInCaricoAnnuncioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	GestioneAnnunciService service = new GestioneAnnunciServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PresaInCaricoAnnuncioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			HttpSession session= request.getSession(true);
			Utente utente = (Utente) session.getAttribute("user");
			
			if(utente == null) {
				response.sendRedirect("/Comun-ity/guest/login.jsp"); 
			}else {
				request.getParameter("ListaCommissionServlet");
			}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		HttpSession session= request.getSession(true);
		Utente utente = (Utente) session.getAttribute("user");
		Long id = (Long.parseLong((String)request.getParameter("annuncio")));
		service.acceptAnnuncio(id, utente.getMail());
		
		response.sendRedirect("ListaAnnunciServlet");
		
	}

}
