package controller.gestioneAnnunci;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
		
		if(utente != null && serviceUtenza.isPro(utente)==true) {
			
			response.sendRedirect("jsp lista lavori");		//va inserita la jsp (ancora da fare)
			
		}else{
			request.setAttribute("message", "Sembra che per la piattaforma tu non sia registrato come professionista, autenticati");
			response.sendRedirect("/Comun-ity/guest/login.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	final String OLD_FORMAT = "dd/MM/yyyy";
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		
		HttpSession session= request.getSession(true);
		
		Utente utente = (Utente) session.getAttribute("user");
				
		if(serviceUtenza.isPro(utente)) {
			
			List<Annuncio> lavoriDisponibili = serviceAnnuncio.getAvailableJobs();
			
			Iterator it = lavoriDisponibili.iterator();
			
			while(it.hasNext()) {
				
				Annuncio annuncio = (Annuncio) it.next();
				
				SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
								
				response.getWriter().write("<div class=\"col\">\n"
						+ "				<div class=\"card center\">\n"
						+ "					<div class=\"additional\">\n"
						+ "					<div class=\"user-card\">\n"
						+ "						<img class='center' src=\"${pageContext.request.contextPath}/images/hammer.png\" width=\"100%\">\n"
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
			
		}else {
			
			response.getWriter().write("<h3>Non ci sono lavori disponibili<h3>");
		}
	}

}
