package controller.gestioneAnnunci;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.gestioneUtenza.GestioneUtenzaService;
import controller.gestioneUtenza.GestioneUtenzaServiceImpl;
import model.Annuncio;
import model.Utente;

/**
 * Servlet implementation class ArchivioServlet
 */
@WebServlet("/ArchivioServlet")
public class ArchivioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArchivioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	GestioneAnnunciService serviceAnnuncio = new GestioneAnnunciServiceImpl();
	GestioneUtenzaService serviceUtenza = new GestioneUtenzaServiceImpl();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		
		Utente user = (Utente) session.getAttribute("user");
		
		if(user != null && serviceUtenza.IsAdmin(user)) {
									
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/user/archivio.jsp");
				request.setAttribute("link", "archivio");
				requestDispatcher.forward(request, response);
			
		}else {
			response.sendRedirect("/Comun-ity/guest/login.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	final String commissioni = "commissioni";
	final String lavori = "lavori";
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		
		Utente user = (Utente) session.getAttribute("user");
		
		if(serviceUtenza.IsAdmin(user)) {
			
			List<Annuncio> lista = null;
			
			String action = request.getParameter("action");
			Iterator<Annuncio> it;
			Annuncio annuncio;
			
			switch(action) {
				case commissioni: 
					
					lista = serviceAnnuncio.getErrands(); 
					
					it = lista.iterator();
					
					while(it.hasNext()) {
					
						annuncio = (Annuncio) it.next();
						
						response.getWriter().write("<div class=\"col\">\n"
								+ "				<div class=\"card center\">\n"
								+ "					<div class=\"additional\">\n"
								+ "					<div class=\"user-card\">\n"
								+ "						<img class='center' src=\"/Comun-ity/images/to-do-list.png\" width=\"100%\">\n"
								+ "					</div>\n"
								+ "					<div class=\"more-info\">\n"
								+ "						<h3 class=\"text-center\">"+annuncio.getTitolo()+"</h3>\n"
								+ "						<div class=\"row justify-content-center\">\n"
								+ "							<form action=\"\" method=\"post\" style=\"width:auto;\">"
								+ "								<input type=\"hidden\"  name=\"annuncio\" id=\"annuncio\" value="+annuncio.getId()+">" 
								+ "									<button type=\"submit\" class=\"btn btn-primary\" id=\"bottone\">Accetta</button>\n"
								+ "							</form>"
								+ "						</div>\n"
								+ "					</div>\n"
								+ "					</div>\n"
								+ "					<div class=\"general\">\n"
								+ "					<h3 class=\"text-center\">"+annuncio.getTitolo()+"</h3>\n"
								+ "					<hr>\n"
								+ "					<p>Autore: "+annuncio.getAutore()+"<br>\n"
								+ "						Descrizione: "+annuncio.getDescrizione()+"<br>\n"
								+ "						Indirizzo: "+annuncio.getIndirizzo()+"<br>\n"
								+ "						Data Fine: "+annuncio.getDataFine()+"<br>\n"
								+ "						Valutazione Utente: <span class=\"heading\" id=\"val\">"+user.getReputazione()+"</span>\n"
								+ "								<i class='bx bxs-star' id=\"star1\"></i>\n"
								+ "								<i class='bx bxs-star' id=\"star2\"></i>\n"
								+ "								<i class='bx bxs-star' id=\"star3\"></i>\n"
								+ "								<i class='bx bxs-star' id=\"star4\"></i>\n"
								+ "								<i class='bx bxs-star' id=\"star5\"></i></p>"					
								+ "					</p>\n"
								+ "					<span class=\"more\">Muovi il mouse per accettare</span>\n"
								+ "					</div>\n"
								+ "				</div>\n"
								+ "			</div>");
					}
					break;
					
					
				case lavori: 
					
					lista = serviceAnnuncio.getJobs(); 
					
					it = lista.iterator();
					
					while(it.hasNext()) {
					
						annuncio = (Annuncio) it.next();
						
						response.getWriter().write("<div class=\"col\">\n"
								+ "				<div class=\"card center\">\n"
								+ "					<div class=\"additional\">\n"
								+ "					<div class=\"user-card\">\n"
								+ "						<img class='center' src=\"/Comun-ity/images/hammer.png\" width=\"80%\">\n"
								+ "					</div>\n"
								+ "					<div class=\"more-info\">\n"
								+ "						<h3 class=\"text-center\">"+annuncio.getTitolo()+"</h3>\n"
								+ "						<div class=\"row justify-content-center\">\n"
								+ "							<form action=\"\" method=\"post\" style=\"width:auto;\">"
								+ "								<input type=\"hidden\"  name=\"annuncio\" id=\"annuncio\" value="+annuncio.getId()+">"
								+ "									<button type=\"submit\" class=\"btn btn-primary\" id=\"bottone\">Accetta</button>\n"
								+ "							</form>"
								+ "						</div>\n"
								+ "					</div>\n"
								+ "					</div>\n"
								+ "					<div class=\"general\">\n"
								+ "					<h3 class=\"text-center\">"+annuncio.getTitolo()+"</h3>\n"
								+ "					<hr>\n"
								+ "					<p>Autore: "+annuncio.getAutore()+"<br>\n"
								+ "						Descrizione: "+annuncio.getDescrizione()+"<br>\n"
								+ "						Indirizzo: "+annuncio.getIndirizzo()+"<br>\n"
								+ "						Data Fine: "+annuncio.getDataFine()+"<br>\n"
								+ "						Valutazione Utente: <span class=\"heading\" id=\"val\">"+user.getReputazione()+"</span>\n"
								+ "								<i class='bx bxs-star' id=\"star1\"></i>\n"
								+ "								<i class='bx bxs-star' id=\"star2\"></i>\n"
								+ "								<i class='bx bxs-star' id=\"star3\"></i>\n"
								+ "								<i class='bx bxs-star' id=\"star4\"></i>\n"
								+ "								<i class='bx bxs-star' id=\"star5\"></i></p>"					
								+ "					</p>\n"
								+ "					<span class=\"more\">Muovi il mouse per accettare</span>\n"
								+ "					</div>\n"
								+ "				</div>\n"
								+ "			</div>");
					}
					break;
			}
			
		}
	}

}