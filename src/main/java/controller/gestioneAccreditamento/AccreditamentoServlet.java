package controller.gestioneAccreditamento;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Accreditamento;

/**
 * Servlet implementation class AccreditamentoServlet
 */
@WebServlet("/AccreditamentoServlet")
public class AccreditamentoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccreditamentoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    GestioneAccreditamentoService serviceA = new GestioneAccreditamentoServiceImpl();
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/user/accreditamenti.jsp");
		 
		 List<Accreditamento> lista = serviceA.getAllUnexamined();
		 
		 request.setAttribute("accreditamenti", lista);
         request.setAttribute("link", "accreditamenti");
	     requestDispatcher.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
