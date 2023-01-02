package controller.gestioneUtenza;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UtenteDAO;

public class ModerazioneUtenzaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	GestioneUtenzaService service = new GestioneUtenzaServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mail=(String) req.getAttribute("utente");
		service.removeUtente(mail);
	}

}
