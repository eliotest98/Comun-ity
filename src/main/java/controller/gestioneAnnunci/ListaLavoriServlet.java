package controller.gestioneAnnunci;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
 * Servlet implementation class ListaLavoriServlet
 */
@WebServlet("/ListaLavoriServlet")
public class ListaLavoriServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListaLavoriServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	GestioneUtenzaService serviceUtenza = new GestioneUtenzaServiceImpl();
	GestioneAnnunciService serviceAnnuncio= new GestioneAnnunciServiceImpl();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		HttpSession session= request.getSession(true);
		Utente utente= (Utente) session.getAttribute("user");
		
		if(utente != null) {
			response.sendRedirect("ListaAnnunci");
		}else{
			response.sendRedirect("/Comun-ity/guest/login.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		
		
		HttpSession session= request.getSession(true);
		int cont,size;
		
		Utente utente = (Utente) session.getAttribute("user");
				
		if(serviceUtenza.isPro(utente) || serviceUtenza.IsAdmin(utente)) {
			
			List<Annuncio> lavoriDisponibili = serviceAnnuncio.getAvailableJobs();
			
			if(lavoriDisponibili.isEmpty()) {
				
				response.getWriter().write("<h3>Non ci sono lavori disponibili<h3>");
				
			}else {
						
				Iterator<Annuncio> it = lavoriDisponibili.iterator();
				size=lavoriDisponibili.size();
				cont=0;
				
				while(it.hasNext()) {
					
					Annuncio annuncio = (Annuncio) it.next();
					if((!annuncio.getAutore().equals(utente.getMail()))&&utente.getAbilitazione().equals(annuncio.getAbilitazioneRichiesta())) {
					Utente user = null;
					
					System.out.println(annuncio.getAutore());
					
					try {
						user = serviceUtenza.getAccountByEmail(annuncio.getAutore());
					} catch (InterruptedException | ExecutionException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
								
					response.getWriter().write("<div class=\"col\">\n"
							+ "				<div class=\"card center\">\n"
							+ "					<div class=\"additional\">\n"
							+ "					<div class=\"user-card\">\n"
							+ "						<img class='center' src=\"/Comun-ity/images/hammer.png\" width=\"80%\">\n"
							+ "					</div>\n"
							+ "					<div class=\"more-info\">\n"
							+ "						<h3 class=\"text-center\">"+annuncio.getTitolo()+"</h3>\n"
							+ "						<div class=\"row justify-content-center\">\n"
							+ "							<form action=\"PresaInCaricoAnnuncioServlet\" method=\"post\" style=\"width:auto;\">"
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
					else{
						
						cont++;
					}
					
					}
				if(cont==size) {
					
					response.getWriter().write("<h3>Non ci sono lavori disponibili<h3>");
					
				}
			}
			
		}else {
			response.getWriter().write("<h3>Non puoi accettare lavori<h3>");
		}
	}

}
