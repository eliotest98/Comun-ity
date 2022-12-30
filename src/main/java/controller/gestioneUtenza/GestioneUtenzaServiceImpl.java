package controller.gestioneUtenza;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import model.Utente;
import model.UtenteDAO;

public class GestioneUtenzaServiceImpl implements GestioneUtenzaService{

	/**
     * @exclude
     * */
    private UtenteDAO utenteDao;
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
    * Check the credential before login.
    * @param email
    * @param password
    * @return true or false
    * @throws IOException
    * @throws ExecutionException
    * @throws InterruptedException
    */

    @Override
    public boolean loginAccount(String email, String password)
          throws IOException, ExecutionException, InterruptedException {
      
    	//TODO
    	return false;
    }

	@Override
	public boolean registerAccount(Utente utente)
			throws IOException, IllegalArgumentException, ExecutionException, InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}
	
	 /**
     * check if the credential entered are correct.
     * @param email of the user
     * @param password of the user
     * @return true if the user credentials correspond.
     */
	@Override
	public boolean checkCredentials(String email, String password) {

		Utente utente = getAccountByEmail(email);
		
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

        return !utente.getMail().isEmpty();
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
        
        return utente.getMail().isEmpty() ? null : utente;
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

}
