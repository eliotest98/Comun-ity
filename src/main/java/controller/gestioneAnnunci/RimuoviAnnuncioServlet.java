package controller.gestioneAnnunci;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
 * Servlet implementation class RimuoviAnnuncioServlet
 */
@WebServlet("/RimuoviAnnuncioServlet")
public class RimuoviAnnuncioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RimuoviAnnuncioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    GestioneUtenzaService serviceU= new GestioneUtenzaServiceImpl();
    GestioneAnnunciService serviceA = new GestioneAnnunciServiceImpl();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(true);
		Utente utente= (Utente) session.getAttribute("user");
		
		if(serviceU.IsAdmin(utente)) {
			response.sendRedirect(" "); //jsp rimozione annunci
		}else{
			response.sendRedirect("/Comun-ity/guest/login.jsp");
		}			
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		Utente utente= (Utente) session.getAttribute("user");
		
		ArrayList<Annuncio> commissioni= (ArrayList<Annuncio>) serviceA.getErrands();
		ArrayList<Annuncio> lavori= (ArrayList<Annuncio>) serviceA.getJobs();
		ArrayList<Annuncio> annunci= (ArrayList<Annuncio>) Stream.concat(commissioni.stream(), lavori.stream()).collect(Collectors.toList());
		
		Long id = Long.valueOf(request.getParameter(" ")); 
		
		if(!annunci.isEmpty()) {
			serviceA.removeAnnuncio(id);
		}else {
			response.getWriter().write("<h3>Non ci sono annunci<h3>");
		}
	}

	
	
	
	
}
