package controller.gestioneReputazione;

import model.Utente;

public interface GestioneReputazioneService {

	/**
	 * Assign a rating to a user.
	 * @param utente is the user to assign the rating to
	 * @param recensione is the the rating
	 */
	public void assignRating(Utente utente, Double recensione);
}
