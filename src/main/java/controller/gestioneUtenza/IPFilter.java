package controller.gestioneUtenza;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class IPFilter
 */
@WebFilter("/*")
public class IPFilter implements Filter {

	private Set<String> bannedIps = new HashSet<>();
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * Default constructor. 
     */
    public IPFilter() {
        // TODO Auto-generated constructor stub
    }
    
    /**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// Read the list of banned IPs from the text file
		try (BufferedReader reader = new BufferedReader(new FileReader("banned-IPs.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
            	//Check if the line contains also a date (Timeout)
            	if(line.contains(" ")) {
            		String[] parts = line.split(" ");
            		String ip = parts[0];
            		String expirationDateString = parts[1];
            		LocalDateTime expirationDate = LocalDateTime.parse(expirationDateString, formatter);

            		// Check if the expiration date has passed
            		if (expirationDate.isAfter(LocalDateTime.now())) {
            			bannedIps.add(ip);
            		}
            	//If not it's a permanent ban
            	}else bannedIps.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// Get the user's IP address
        String userIp = ((HttpServletRequest) request).getRemoteAddr();

        // Check if the user's IP is on the banned list
        if (bannedIps.contains(userIp)) {
            // Redirect the user
            ((HttpServletResponse) response).sendRedirect(" "); //PAGINA BANNATO
        } else {
            // Allow the request to continue
            chain.doFilter(request, response);
        }
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// Remove expired entries from the banned IPs text file
	    try (BufferedReader reader = new BufferedReader(new FileReader("banned-IPs.txt"));
	         BufferedWriter writer = new BufferedWriter(new FileWriter("banned-IPs-temp.txt"))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	        	//Check if the line contains also a date (Timeout)
	        	if(line.contains(" ")) {
            		String[] parts = line.split(" ");
            		String ip = parts[0];
            		String expirationDateString = parts[1];
            		LocalDateTime expirationDate = LocalDateTime.parse(expirationDateString, formatter);

            		// Check if the expiration date hasn't passed
            		if (expirationDate.isAfter(LocalDateTime.now())) {
            			writer.write(line);
            			writer.newLine();
            		}
            	//If not it just writes it because it's a permanent ban
	        	}else {
	        		writer.write(line);
        			writer.newLine();
	        	}
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // Replace the banned IPs text file with the updated version
	    try {
	        Files.deleteIfExists(Paths.get("banned-IPs.txt"));
	        Files.move(Paths.get("banned-IPs-temp.txt"), Paths.get("banned-IPs.txt"));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
