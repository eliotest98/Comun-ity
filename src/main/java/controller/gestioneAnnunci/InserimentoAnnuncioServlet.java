package controller.gestioneAnnunci;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Annuncio;
import model.Utente;

@WebServlet("/InserimentoAnnuncioServlet")
public class InserimentoAnnuncioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	GestioneAnnunciService service = new GestioneAnnunciServiceImpl();

	private final String addressRegex ="^([a-zA-Z\\u0080-\\u024F]+(?:. |-| |'))*[a-zA-Z\\u0080-\\u024F]*$";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(true);

		Utente user = (Utente) session.getAttribute("user");

		if(user == null) {
			resp.sendRedirect("/Comun-ity/guest/login.jsp"); 
		}else {
			resp.sendRedirect("ListaAnnunci");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession(true);
				
		Utente user = (Utente) session.getAttribute("user");
		
		String flag = req.getParameter("professionista");

		String autore = user.getMail();
		String abilitazioneRichiesta = flag == null ? "nessuna" :req.getParameter("professione");
		String tipologia = 	flag == null ? "commissione" : "lavoro";
		String titolo = req.getParameter("titolo");
		String descrizione = req.getParameter("descrizione");		
		String indirizzo = req.getParameter("indirizzo");
		
		System.out.println(abilitazioneRichiesta);


		if(enablingOK(tipologia, abilitazioneRichiesta)) {

			if(titleOK(titolo)) {

				if(descriptionOK(descrizione)) {

					if(patternMatches(indirizzo, addressRegex)) {

						if(service.insertAnnuncio(new Annuncio(abilitazioneRichiesta, autore, titolo, descrizione, indirizzo))) {
							
							RequestDispatcher requestDispatcher = req.getRequestDispatcher("ListaAnnunciServlet");
							req.setAttribute("success", "Annuncio inserito");
							requestDispatcher.forward(req, resp);
							
						}else {
							RequestDispatcher requestDispatcher = req.getRequestDispatcher("ListaAnnunciServlet");
							req.setAttribute("error", "Annuncio non inserito");

							requestDispatcher.forward(req, resp);
						}

					}else {
						RequestDispatcher requestDispatcher = req.getRequestDispatcher("ListaAnnunciServlet");
						req.setAttribute("error", "Indirizzo non valido");

						requestDispatcher.forward(req, resp);
					}
				}else {
					RequestDispatcher requestDispatcher = req.getRequestDispatcher("ListaAnnunciServlet");
					req.setAttribute("error", "Descrizione non valida");

					requestDispatcher.forward(req, resp);
				}
			}else {
				RequestDispatcher requestDispatcher = req.getRequestDispatcher("ListaAnnunciServlet");
				req.setAttribute("error", "Titolo non valido");

				requestDispatcher.forward(req, resp);
			}
		}else {
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("ListaAnnunciServlet");
			req.setAttribute("error", "Non può esserci un'abilitazione richiesta in una commissione");

			requestDispatcher.forward(req, resp);
		}
	}

	private static boolean patternMatches(String string, String regexPattern) {
		return Pattern.compile(regexPattern)
				.matcher(string)
				.matches();
	}

	private static boolean titleOK(String titolo) {
		boolean res= true;

		if(titolo.length()<1 || titolo.length()>30) {
			res=false;
		}

		return res;
	}


	private static boolean descriptionOK(String descrizione) {
		boolean res= true;

		if(descrizione.length()<1 || descrizione.length()>280) {
			res=false;
		}

		return res;
	}

	private static boolean enablingOK(String tipologia ,String abilitazione) {
		boolean res= true;

		if(tipologia.equals("commissione") && !abilitazione.equals("nessuna")) {
			res= false;
		}

		return res;
	}


}
