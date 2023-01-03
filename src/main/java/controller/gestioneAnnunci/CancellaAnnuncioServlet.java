package controller.gestioneAnnunci;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Utente;

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
			resp.sendRedirect(" ");	//jsp cancellazione annuncio
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doGet(req, resp);
		
		Long id = (Long.parseLong((String)req.getParameter("annuncio")));
		service.removeAnnuncio(id);
	}

}
