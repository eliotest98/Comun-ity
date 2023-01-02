package controller.gestioneUtenza;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import controller.gestioneAccreditamento.GestioneAccreditamentoService;
import controller.gestioneAccreditamento.GestioneAccreditamentoServiceImpl;
import model.Accreditamento;
import model.Utente;
import model.UtenteDAO;

/**
 * Servlet implementation class RegistrazioneServlet
 */
@WebServlet("/RegistrazioneServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
maxFileSize = 2048 * 2048 * 5, 
maxRequestSize = 2048 * 2048 * 5 * 5)
public class RegistrazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrazioneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	GestioneUtenzaService service = new GestioneUtenzaServiceImpl();
	GestioneAccreditamentoService serviceAccre = new GestioneAccreditamentoServiceImpl();
	
	private final String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
	private final String phoneRegex = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$";
	private final String addressRegex ="^([a-zA-Z\\u0080-\\u024F]+(?:. |-| |'))*[a-zA-Z\\u0080-\\u024F]*$";
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		
		if(session.getAttribute("user") != null) {
			response.sendRedirect("HomeServlet");
		}else {
			response.sendRedirect("/Comun-ity/guest/registrazione.jsp"); 
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String telefono = request.getParameter("telefono");
		String data = request.getParameter("data");
		String sesso = request.getParameter("sesso");
		String indirizzo = request.getParameter("indirizzo");
		String flag = request.getParameter("professionista");
				
		LocalDate dob = LocalDate.parse(data);  
		
		if(isValidEmailAddress(email)) {
			
			if(patternMatches(password,passwordRegex)) {
				
				Date current = new Date();
				
				ZoneId defaultZoneId = ZoneId.systemDefault();
		        Date insert = Date.from(dob.atStartOfDay(defaultZoneId).toInstant());

				
				if(current.after(insert)) {
				
				if(patternMatches(telefono,phoneRegex)) {
					
					if(sesso.charAt(0) == 'M' || sesso.charAt(0) == 'F') {
						
						if(patternMatches(indirizzo,addressRegex)) {
							
							Utente user = new Utente("cittadino", "nessuna", nome, cognome, calculateAge(dob), email, password, sesso, telefono, indirizzo, dob);
							
							Accreditamento accreditamento = null;
							
							if(flag != null) {
								
								String professione = request.getParameter("professione");
								
								if(professione.length() < 3) {
									
									RequestDispatcher requestDispatcher = request.getRequestDispatcher("/guest/registrazione.jsp");
									request.setAttribute("message", "Inserisci una professione");
									
									requestDispatcher.forward(request, response);
									
								}else {
									 	String path = getServletContext().getRealPath("/temp");
								        
								        File directory = new File(String.valueOf(path));

								        if (!directory.exists()) {
								                directory.mkdir();      
								        }
								        
								        Part part = request.getPart("file");
								        								        
								        String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
								        part.write(path + fileName);
								        
								        String base = encodeFileToBase64Binary(path + fileName);
								        
								        accreditamento = new Accreditamento(email, professione, base);   
								}
								
							}
							
							Boolean res = false;
					        
					        try {
								res = service.registerAccount(user);
							} catch (IllegalArgumentException | IOException | ExecutionException
									| InterruptedException e) {
								e.printStackTrace();
							}
					        
					        if(res) {
					        	
					        	if(flag != null) {
					        		
					        		if(serviceAccre.saveAccreditamento(accreditamento)) {
					        			
					        			HttpSession session = request.getSession(true);
					        			
					        			session.setAttribute("user", user);
					        			session.setAttribute("admin", false);
					        			
					        			response.sendRedirect("HomeServlet");
					        			
					        		}else {
					        			
					        			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/guest/registrazione.jsp");
										request.setAttribute("message", "Accreditamento non riuscito");
										
										requestDispatcher.forward(request, response);
					        			
					        		}
					        		
					        	}else {
					        		
					        		HttpSession session = request.getSession(true);
				        			
				        			session.setAttribute("user", user);
				        			session.setAttribute("admin", false);
				        			
					        		response.sendRedirect("HomeServlet");
					        	}
					        	
					        }else {
					        	
					        	RequestDispatcher requestDispatcher = request.getRequestDispatcher("/guest/registrazione.jsp");
								request.setAttribute("message", "Errore nella creazione dell'utente");
								
								requestDispatcher.forward(request, response);
					        }
							
						}else {
						
							RequestDispatcher requestDispatcher = request.getRequestDispatcher("/guest/registrazione.jsp");
							request.setAttribute("message", "Il campo indirizzo è incompleto");
							
							requestDispatcher.forward(request, response);
						}
					}else {
						RequestDispatcher requestDispatcher = request.getRequestDispatcher("/guest/registrazione.jsp");
						request.setAttribute("message", "Devi compilare il campo del sesso");
						
						requestDispatcher.forward(request, response);
					}
					
				}else {
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("/guest/registrazione.jsp");
					request.setAttribute("message", "Il numero di telefono non è corretto");
					
					requestDispatcher.forward(request, response);
				}
				
				}else {
					
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("/guest/registrazione.jsp");
					request.setAttribute("message", "Non puoi essere nato nel futuro");
					
					requestDispatcher.forward(request, response);
				}
				
			}else {
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/guest/registrazione.jsp");
				request.setAttribute("message", "La password non è valida. Deve essere lunga da 8 a 20 caratteri, avere almeno un carattere speciale, un numero, una lettera maiuscola e minuscola");
				
				requestDispatcher.forward(request, response);
			}
			
		}else {
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/guest/registrazione.jsp");
			request.setAttribute("message", "L'email non è valida");
			
			requestDispatcher.forward(request, response);
		}
	}
	
	
	
	private static String encodeFileToBase64Binary(String path) throws IOException {
		
		byte[] byteData = Files.readAllBytes(Paths.get(path));
		return Base64.getEncoder().encodeToString(byteData);
	}
	
	private static int calculateAge(LocalDate dob){  
		
	LocalDate curDate = LocalDate.now();  
	if ((dob != null) && (curDate != null))
		return Period.between(dob, curDate).getYears();  
	else    
		return 0;  
	}  
	
	private static boolean patternMatches(String string, String regexPattern) {
	    return Pattern.compile(regexPattern)
	      .matcher(string)
	      .matches();
	}
	
	private static boolean isValidEmailAddress(String email) {
		   boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (AddressException ex) {
		      result = false;
		   }
		   return result;
		}

}
