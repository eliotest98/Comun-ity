package controller.gestioneAnnunci;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Annuncio;

public class ListaCommissioniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	GestioneAnnunciService service = new GestioneAnnunciServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("");					//Aggiungere redirect
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ArrayList<Annuncio> commissioni;
		commissioni=(ArrayList<Annuncio>) service.getAvailableErrands();
		if(commissioni==null) {
			//COMMISSIONI NON DISPONIBILI
		}
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("");  //Aggiungere pagina mancante
		req.setAttribute("Commissioni", commissioni);
		requestDispatcher.forward(req, resp);
		doGet(req,resp);
	}

}
