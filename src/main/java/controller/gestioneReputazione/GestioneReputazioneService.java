package controller.gestioneReputazione;

import model.Utente;

public interface GestioneReputazioneService {

	public void assignRating(Utente utente, Double recensione);
}
