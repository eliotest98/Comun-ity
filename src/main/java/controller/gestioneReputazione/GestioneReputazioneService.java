package controller.gestioneReputazione;

import model.Annuncio;
import model.Utente;

/**
 * Inteface implemented in GestioneReputazioneServiceImpl.
 */
public interface GestioneReputazioneService {

  /**
   * Review a completed ad and assign a rating to a user.
   *
   * @param annuncio   is the completed ad 
   * @param utente     is the user to assign the rating to.
   * @param recensione is the rating to assign.
   * @return true if the rating is assigned correctly for both Annuncio and Utente.
   */
   boolean assignRating(Annuncio annuncio, Utente utente, Double recensione);
}
