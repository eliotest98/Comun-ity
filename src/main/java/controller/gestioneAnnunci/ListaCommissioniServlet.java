package controller.gestioneAnnunci;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

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

@WebServlet("/ListaCommissioniServlet")
public class ListaCommissioniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	GestioneAnnunciService service = new GestioneAnnunciServiceImpl();
	GestioneUtenzaService serviceUtenza = new GestioneUtenzaServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession(true);
		Utente utente= (Utente) session.getAttribute("user");
		
		if(utente != null) {
			resp.sendRedirect("ListaAnnunci");
		}else{
			resp.sendRedirect("/Comun-ity/guest/login.jsp");
		}				//Aggiungere redirect
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ArrayList<Annuncio> commissioni;
		
		service.getAvailableErrands();
		
		if(service.getAvailableErrands() == null) {
			
			resp.getWriter().write("<h3>Non ci sono commissioni disponibili</h3>");
			
		}else {
						
			commissioni = (ArrayList<Annuncio>) service.getAvailableErrands();
		
			Iterator it = commissioni.iterator();
			
			while(it.hasNext()) {
				
				Annuncio annuncio = (Annuncio) it.next();
				Utente user = null;
				
				try {
					user = serviceUtenza.getAccountByEmail(annuncio.getAutore());
				} catch (InterruptedException | ExecutionException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				resp.getWriter().write("<div class=\"col\">\n"
						+ "				<div class=\"card center\">\n"
						+ "					<div class=\"additional\">\n"
						+ "					<div class=\"user-card\">\n"
						+ "						<img class='center' src=\"/Comun-ity/images/to-do-list.png\" width=\"100%\">\n"
						+ "					</div>\n"
						+ "					<div class=\"more-info\">\n"
						+ "						<h3 class=\"text-center\">"+annuncio.getTitolo()+"</h3>\n"
						+ "						<div class=\"row justify-content-center\">\n"
						+ "							<form action=\"PresaInCaricoAnnuncioServlet\" method=\"post\">"
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
			}
	}

}
