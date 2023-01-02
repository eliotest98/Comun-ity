package controller.gestioneAnnunci;

import java.io.IOException;
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
import model.AnnuncioDAO;
import model.Utente;
import model.UtenteDAO;

/**
 * Servlet implementation class VisualizzazioneLavoriServlet
 */
@WebServlet("/VisualizzazioneLavoriServlet")
public class VisualizzazioneLavoriServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VisualizzazioneLavoriServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    UtenteDAO utenteDao = new UtenteDAO();
    AnnuncioDAO annuncioDAO = new AnnuncioDAO();
	GestioneUtenzaService serviceUtenza = new GestioneUtenzaServiceImpl(utenteDao);
	GestioneAnnunciService serviceAnnuncio= new GestioneAnnunciServiceImpl(annuncioDAO);

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session= request.getSession(true);
		Utente utente= (Utente) session.getAttribute("user");
		
		
		if(utente != null && serviceUtenza.isPro(utente)==true) {
			
			response.sendRedirect("jsp visualizzazaione lavori");		//va inserita la jsp (ancora da fare)
			
		}else{
			request.setAttribute("message", "Sembra che per la piattaforma tu non sia registrato come professionista, autenticati");
			response.sendRedirect("/Comun-ity/guest/login.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		HttpSession session= request.getSession(true);
		
		List<Annuncio> lavoriDisponibili= serviceAnnuncio.getJobsAvailable();
		request.setAttribute("lavoriDisponibili", lavoriDisponibili);
		
		RequestDispatcher disp = this.getServletContext().getRequestDispatcher("jsp visualizzazaione lavori");				//va inserita la jsp (ancora da fare)
		
		disp.forward(request, response);
	}

}
