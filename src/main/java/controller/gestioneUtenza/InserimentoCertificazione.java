package controller.gestioneUtenza;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import javax.xml.datatype.DatatypeConstants;

import model.Utente;

/**
 * Servlet implementation class InserimentoCertificazione
 */
@WebServlet("/InserimentoCertificazione")
public class InserimentoCertificazione extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InserimentoCertificazione() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
	    Utente user = (Utente) session.getAttribute("user");
	    
	    if (user == null) {
	        response.sendRedirect("/Comun-ity/guest/login.jsp");
	      } else {
	        response.sendRedirect(" "); //jsp inserimento certificazione
	      }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(true);
	    Utente user = (Utente) session.getAttribute("user");
		GestioneUtenzaService service= new GestioneUtenzaServiceImpl();
	    
		String utente= request.getParameter("utente"); //mail dell'utente che sottomette la richiesta
		String abilitazione= request.getParameter("abilitazione"); //abilitazione che si suppone di avere
		String allegato= request.getParameter("allegato"); //file della certificazione
		
		if(user.getRuolo().equals("cittadino")) {
			
			if(abilitazioneOK(abilitazione)) {
				
				if(allegatoOK(allegato)) {
					
				}else {
					
				}
				
			}else {
				
			}
			
		}else {
			
		}
	}
	
	public static boolean abilitazioneOK(String abilitazione) {
		
		boolean res= true;
		
		if(abilitazione.length()<1 || abilitazione.length()>30) {
			res= false;
		}
		
		return res;
	}
	
	public static boolean allegatoOK(String allegato) {
		boolean res= true;
		byte[] data = DatatypeConverter.parseBase64Binary(allegato);
		
		if(data.length < 0 || data.length > 52428800) {
			res= false;
		}
		
		return res;
	}

}
