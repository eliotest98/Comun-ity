package controller.gestioneAnnunci;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Utente;

/**
 * Servlet implementation class AnnuncioCompletato
 */
@WebServlet("/AnnuncioCompletato")
public class AnnuncioCompletato extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnnuncioCompletato() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    GestioneAnnunciService serviceA = new GestioneAnnunciServiceImpl();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
	    Utente utente = (Utente) session.getAttribute("user");

	    if (utente == null) {
	      response.sendRedirect("/Comun-ity/guest/login.jsp");
	    } else {
	      response.sendRedirect("HomeServlet"); 
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Long id = (Long.parseLong(request.getParameter("annuncio")));
		
		if(serviceA.markAsDone(id)) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("HomeServlet");
		    request.setAttribute("success", "Annuncio marcato come completato con successo");
		    requestDispatcher.forward(request, response);
		}else {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("HomeServlet");
		    request.setAttribute("error", "Non è stato possibile marcare l'annuncio come completato");
		    requestDispatcher.forward(request, response);
		}
	}

}
