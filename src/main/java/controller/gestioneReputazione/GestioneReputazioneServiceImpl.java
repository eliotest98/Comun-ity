package controller.gestioneReputazione;

import model.Utente;
import model.UtenteDAO;

public class GestioneReputazioneServiceImpl implements GestioneReputazioneService{

	/**
     * @exclude
     * */
    private UtenteDAO utenteDao = new UtenteDAO();
	
		public void assignRating(Utente utente, Double recensione) {
			utenteDao.assegnaValutazione(utente, recensione);
		}
}
