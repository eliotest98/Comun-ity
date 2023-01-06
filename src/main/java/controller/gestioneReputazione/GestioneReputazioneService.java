package controller.gestioneReputazione;

import model.Utente;

/**
 * Inteface implemented in GestioneReputazioneServiceImpl.
 */
public interface GestioneReputazioneService {

  /**
   * Assign a rating to a user.
   *
   * @param utente     is the user to assign the rating to.
   * @param recensione is the rating to assign.
   */
  public void assignRating(Utente utente, Double recensione);
}
