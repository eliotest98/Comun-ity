package controller.gestioneAnnunci;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Annuncio;
import model.Utente;

@WebServlet("/CancellaAnnuncioServlet")
public class CancellaAnnuncioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	GestioneAnnunciService service = new GestioneAnnunciServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession(true);
		
		Utente user = (Utente) session.getAttribute("user");
		
		if(user == null) {
			resp.sendRedirect("/Comun-ity/guest/login.jsp"); 
		}else {
			resp.sendRedirect("");	//jsp cancellazione annuncio
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				
		HttpSession session = req.getSession(true);
		Utente user = (Utente) session.getAttribute("user");
		
		Long id= Long.valueOf((String) req.getAttribute("annuncioId")); //annuncioId place holder (non so come si chiama il parametro nella jsp ma serve l'id dell'annuncio sul quale � stato premuto rimuovi)
		
		Annuncio annuncio= service.findAnnuncioById(id);
		
		if(autoreOK(user, annuncio)) {
			service.removeAnnuncio(annuncio.getId());
			
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("  "); //jsp cancellazione annuncio
			req.setAttribute("message", "annuncio rimosso con successo");
			
			requestDispatcher.forward(req, resp);
		}else {
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("  "); //jsp cancellazione annuncio
			req.setAttribute("message", "non puoi rimuovere un annuncio di cui non sei l'autore");
			
			requestDispatcher.forward(req, resp);
		}
		
	}
	
	public static boolean autoreOK(Utente user, Annuncio annuncio) {
		boolean res= true;
		
		if(!annuncio.getAutore().equals(user.getMail())) {
			res= false;
		}
		
		return res;
	}

}
