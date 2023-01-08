package controller.gestioneReputazione;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.gestioneAnnunci.GestioneAnnunciService;
import controller.gestioneAnnunci.GestioneAnnunciServiceImpl;
import controller.gestioneUtenza.GestioneUtenzaService;
import controller.gestioneUtenza.GestioneUtenzaServiceImpl;
import model.Annuncio;
import model.Utente;

/**
 * Servlet implementation class AssegnaValutazioneServlet.
 */
@WebServlet("/AssegnaValutazioneServlet")
public class AssegnaValutazioneServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   *
   *@see HttpServlet#HttpServlet()
   */
  public AssegnaValutazioneServlet() {
    super();
  }

  GestioneReputazioneService service = new GestioneReputazioneServiceImpl();
  GestioneUtenzaService serviceU= new GestioneUtenzaServiceImpl();
  GestioneAnnunciService serviceA= new GestioneAnnunciServiceImpl();

  /**
   * doGet method implementation.
   *
   * @throws IOException //
   * @throws ServletException //
   *@see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession(true);
    Utente utente = (Utente) session.getAttribute("user");

    if (utente == null) {
      response.sendRedirect("/Comun-ity/guest/login.jsp");
    } else {
      response.sendRedirect("AreaPersonale"); 
    }

  }

  /**
   * doPost method implementation.
   *
   * @throws IOException //
   * @throws ServletException //
   *@see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

	  System.out.println(request.getParameter("valutazione") + " " + request.getParameter("annuncioId"));
	  
    Double valutazione = Double.valueOf(request.getParameter("valutazione"));
    Long id = Long.valueOf(request.getParameter("annuncioId"));
    
    Annuncio annuncio = serviceA.findAnnuncioById(id);
    
    String utenteMail = annuncio.getIncaricato();
    
    Utente utente = new Utente();
	try {
		utente = serviceU.getAccountByEmail(utenteMail);
	} catch (InterruptedException | ExecutionException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	if(!annuncio.getDataFine().equals(annuncio.getDataPubblicazione().plusDays(30))) {
		if(utente!=null) {
			service.assignRating(annuncio, utente, valutazione);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("AreaPersonale");
			request.setAttribute("success", "Valutazione assegnata con successo");
			requestDispatcher.forward(request, response);
		}else {

			RequestDispatcher requestDispatcher = request.getRequestDispatcher("AreaPersonale");
			request.setAttribute("errore", "Assegnazione della valutazione fallita");
			requestDispatcher.forward(request, response);
		}

	}else {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("AreaPersonale");
		request.setAttribute("errore", "Annuncio non completato");
		requestDispatcher.forward(request, response);
	}
  }

}
