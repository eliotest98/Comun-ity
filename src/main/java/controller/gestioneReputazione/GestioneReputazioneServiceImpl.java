package controller.gestioneReputazione;

import model.Utente;
import model.UtenteDAO;

public class GestioneReputazioneServiceImpl implements GestioneReputazioneService{

	/**
     * @exclude
     */
    private UtenteDAO utenteDao = new UtenteDAO();
    
    /**
     * Empty Constructor.
     **/
    public GestioneReputazioneServiceImpl() {
	}
	
    /**
	 * Assign a rating to a user.
	 * @param utente is the user to assign the rating to
	 * @param recensione is the the rating
	 */
    public void assignRating(Utente utente, Double recensione) {
    	
    	utenteDao.assignRating(utente, recensione);
    }
}
