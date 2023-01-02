package controller.gestioneUtenza;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Utente;

public class ListaUtentiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	GestioneUtenzaService service = new GestioneUtenzaServiceImpl();
	 
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ArrayList<Utente> listaUtenti;
		listaUtenti=(ArrayList<Utente>) req.getAttribute("listaUtenti");
		
		if(listaUtenti==null) {
			resp.sendRedirect("");					//Inserire pagina per lista vuota
		}
		resp.sendRedirect("");					//Inserire pagina non ancora creata
}

	 
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession(true);
		
		ArrayList<Utente> listaUtenti= new ArrayList<>();
		
		listaUtenti=(ArrayList<Utente>) service.getAllUsers();
		
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("");			//Inserire pagina non ancora creata per il Dispatcher
		req.setAttribute("listaUtenti", listaUtenti);
		requestDispatcher.forward(req, resp);
	}
 
  public ListaUtentiServlet() {
		 super();
		// TODO Auto-generated constructor stub
	}
}
