package controller.utility;

import controller.gestioneUtenza.GestioneUtenzaService;
import controller.gestioneUtenza.GestioneUtenzaServiceImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class IndexServlet.
 */
@WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   *
   * @see HttpServlet#HttpServlet()
   */
  public IndexServlet() {
    super();
  }

  GestioneUtenzaService service = new GestioneUtenzaServiceImpl();

  /**
   * doGet method implementation.
   *
   * @throws IOException //
   * @throws ServletException //
   *@see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.sendRedirect("/Comun-ity/index.jsp");

  }

  /**
   * doPost method implementation.
   *
   * @throws IOException //
   * @throws ServletException //
   *@see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);

  }

}
