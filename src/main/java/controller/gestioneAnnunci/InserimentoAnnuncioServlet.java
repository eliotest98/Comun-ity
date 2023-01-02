package controller.gestioneAnnunci;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Annuncio;

public class InserimentoAnnuncioServlet extends HttpServlet {

	GestioneAnnunciService service = new GestioneAnnunciServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("");       //INSERIRE PAGINA MANCANTE
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Annuncio annuncio=(Annuncio) req.getAttribute("annuncio");
		service.insertAnnuncio(annuncio);
		doGet(req, resp);
	}

}
