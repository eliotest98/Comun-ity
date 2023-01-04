package controller.gestioneUtenza;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.gestioneAnnunci.GestioneAnnunciService;
import controller.gestioneAnnunci.GestioneAnnunciServiceImpl;
import model.Utente;

@WebServlet("/AreaPersonale")
public class AreaPersonaleServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AreaPersonaleServlet() {
		// TODO Auto-generated constructor stub
	}
	
	GestioneAnnunciService serviceAnnuncio = new GestioneAnnunciServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub		
		
		HttpSession session = req.getSession(true);
		
		Utente user = (Utente) session.getAttribute("user");
		
		if(user != null) {
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/user/areaPersonale.jsp");
			req.setAttribute("link", "areaPersonale");
			requestDispatcher.forward(req, resp);
		}else {
			resp.sendRedirect("/Comun-ity/guest/login.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
	
}
