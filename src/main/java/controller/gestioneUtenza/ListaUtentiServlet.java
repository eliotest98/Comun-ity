package controller.gestioneUtenza;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Utente;

@WebServlet("/ListaUtenti")
public class ListaUtentiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	GestioneUtenzaService service = new GestioneUtenzaServiceImpl();
	 
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession(true);
		
		if(session.getAttribute("user") != null && (session.getAttribute("admin") != null && (Boolean) session.getAttribute("admin") == true)) {
			
			ArrayList<Utente> listaUtenti = new ArrayList<>();
			
			listaUtenti=(ArrayList<Utente>) service.getAllUsers();
						
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/user/listaUtenti.jsp");
			req.setAttribute("listaUtenti", listaUtenti);
			req.setAttribute("link", "utenti");
			requestDispatcher.forward(req, resp);
		}else {
			resp.sendRedirect("/Comun-ity/guest/login.jsp");
		}
}

	 
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
 
  public ListaUtentiServlet() {
		 super();
		// TODO Auto-generated constructor stub
	}
}
