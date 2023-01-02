package controller.gestioneAnnunci;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Annuncio;

@WebServlet("/ListaCommissioniServlet")
public class ListaCommissioniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	GestioneAnnunciService service = new GestioneAnnunciServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("");					//Aggiungere redirect
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
			
				resp.getWriter().write("<div class=\"col\">\n"
						+ "				<div class=\"card center\">\n"
						+ "					<div class=\"additional\">\n"
						+ "					<div class=\"user-card\">\n"
						+ "						<img class='center' src=\"${pageContext.request.contextPath}/images/to-do-list.png\" width=\"100%\">\n"
						+ "					</div>\n"
						+ "					<div class=\"more-info\">\n"
						+ "						<h3 class=\"text-center\">"+annuncio.getTitolo()+"</h3>\n"
						+ "						<div class=\"row justify-content-center\">\n"
						+ "							<button class=\"btn btn-primary\" id=\"bottone\">Accetta</button>\n"
						+ "						</div>\n"
						+ "					</div>\n"
						+ "					</div>\n"
						+ "					<div class=\"general\">\n"
						+ "					<h3 class=\"text-center\">"+annuncio.getTitolo()+"</h3>\n"
						+ "					<hr>\n"
						+ "					<p>Autore: "+annuncio.getAutore()+"<br>\n"
						+ "						Descrizione: "+annuncio.getDescrizione()+"<br>\n"
						+ "						Indirizzo: "+annuncio.getIndirizzo()+"<br>\n"
						+ "						Data Fine: "+annuncio.getDataFine()+"3\n"
						+ "					</p>\n"
						+ "					<span class=\"more\">Muovi il mouse per accettare</span>\n"
						+ "					</div>\n"
						+ "				</div>\n"
						+ "			</div>");
				}
			}
	}

}
