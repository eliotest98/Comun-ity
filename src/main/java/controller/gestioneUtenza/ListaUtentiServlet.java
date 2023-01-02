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
import model.UtenteDAO;

@WebServlet("/ListaUtenti")
public class ListaUtentiServlet extends HttpServlet {

	 UtenteDAO utenteDao = new UtenteDAO();
	 GestioneUtenzaService service = new GestioneUtenzaServiceImpl(utenteDao);
	 
	 public ListaUtentiServlet() {
		 super();
		// TODO Auto-generated constructor stub
	}
	 
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession(true);
		
		ArrayList<Utente> listaUtenti = new ArrayList<>();
		
		listaUtenti = (ArrayList<Utente>) service.getListaUtenti();
		
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/user/listaUtenti.jsp");			//Inserire pagina non ancora creata per il Dispatcher
		req.setAttribute("listaUtenti", listaUtenti);
		requestDispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}

}
