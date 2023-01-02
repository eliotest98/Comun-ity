package controller.gestioneReputazione;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Utente;

/**
 * Servlet implementation class AssegnaValutazioneServlet
 */
@WebServlet("/AssegnaValutazioneServlet")
public class AssegnaValutazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssegnaValutazioneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    GestioneReputazioneService service= new GestioneReputazioneServiceImpl();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(true);
		Utente utente= (Utente) session.getAttribute("user");
		
		if(utente == null) {
			response.sendRedirect("/Comun-ity/guest/login.jsp"); 
		}else {
			response.sendRedirect(" "); //jsp per assegnare la valutazione (da fare)
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		HttpSession session= request.getSession(true);
		Utente utente= (Utente) session.getAttribute("user");
		Double valutazione= Double.valueOf(request.getParameter(" ")); //Inserire nome parametro
		
		service.assignRating(utente, valutazione);
		response.sendRedirect("HomeServlet");
		
	}

}
