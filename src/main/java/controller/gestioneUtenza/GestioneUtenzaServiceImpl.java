package controller.gestioneUtenza;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import model.Accreditamento;
import model.AccreditamentoDAO;
import model.Utente;
import model.UtenteDAO;

public class GestioneUtenzaServiceImpl implements GestioneUtenzaService{

	/**
     * @exclude
     * */
    private UtenteDAO utenteDao = new UtenteDAO();
    /**
     * Service constructor.
     * @param accountDao is required, because is the DAO that
     * all the service methods will call.
     **/
    public GestioneUtenzaServiceImpl(UtenteDAO utenteDao) {
        this.utenteDao = utenteDao;
    }

    /**
     * Empty Constructor.
     **/
    public GestioneUtenzaServiceImpl() {
    }

    /**
     * this function register a new user account.
     * @param utente user into the db.
     * @return true if the operation ends in success.
     * @throws IOException
     * @throws IllegalArgumentException
     */
    @Override
    public boolean registerAccount(Utente utente)
            throws IllegalArgumentException {

        try {
			if(!searchAccountByEmail(utente.getMail())) {
				utenteDao.saveUtente(utente);
				return true;
			}else {
				throw new IllegalArgumentException("email gi� presente"
			            + " nel DB, utilizza una nuova email");
			}
		} catch (InterruptedException | ExecutionException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return false;
    }
	
	 /**
     * check if the credential entered are correct.
     * @param email of the user
     * @param password of the user
     * @return true if the user credentials correspond.
	 * @throws IOException 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
     */
	@Override
	public boolean checkCredentials(String email, String password) throws InterruptedException, ExecutionException, IOException {

		Utente utente = getAccountByEmail(email);
		
		if(utente == null)
			return false;
		
		return utente.getPassword().equals(password);

	}

	/**
     * This service method checks if an account exists in the database.
     * @param email referring to the account to search for
     * @return true if the account exists, false if not
     */
    @Override
    public boolean searchAccountByEmail(String email)
            throws InterruptedException, ExecutionException, IOException {

        Utente utente = new Utente();
        utente = utenteDao.findUtenteByMail(email);
        
        if(utente == null)
        	return false;
        
        return true;
    }

    /**
     * This method return the account given its mail.
     * @param email referring to the account to search for
     * @return Utente if it exists, null if not
     */
	@Override
	public Utente getAccountByEmail(String email) throws InterruptedException, ExecutionException, IOException {
		
		Utente utente = new Utente();
		
        utente = utenteDao.findUtenteByMail(email);
                
        return utente == null ? null : utente;
	}

	@Override
	public boolean changePassword(String email, String password)
			throws IOException, ExecutionException, InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
     * @param utente is the registred user into the DataBase.
     * @return Return true if the following account is
     * a Comun-ity Admin.
     * @throws IllegalArgumentException
     */
	@Override
	public boolean IsAdmin(Utente utente) throws IllegalArgumentException {
		
		return utente.getRuolo().equals("admin");
	}
	
	public boolean isPro(Utente utente) {
		
		return utente.getRuolo().equals("professionista");
	}

	@Override
	public List<String> getAllAdminsEmails() {
		
		List<Utente> lista = utenteDao.allAdmins();
	    Iterator<Utente> it = lista.iterator();
	    
	    List<String> emails = new ArrayList<>();
		
		while(it.hasNext()){
			emails.add(it.next().getMail());
		}
		
		return emails;
	}

	@Override
	public List<Utente> getListaUtenti() {
		List<Utente> lista = utenteDao.listaUtenti();
		return lista;
	}

	@Override
	public void removeUtente(String utente) {
		utenteDao.deleteUtente(utente);	
	}
	
	//Assegna valutazione
	
	public void assignRating(Utente utente, Double recensione) {
		utenteDao.assegnaValutazione(utente, recensione);
	}
	

}
