package controller.gestioneReputazione;

import model.Utente;
import model.UtenteDAO;

/**
 * Implementation class of GestioneReputazioneService.
 */
public class GestioneReputazioneServiceImpl implements GestioneReputazioneService {

  /**
   * exclude.
   */
  private final UtenteDAO utenteDao = new UtenteDAO();

  /**
   * Empty Constructor.
   **/
  public GestioneReputazioneServiceImpl() {
  }

  /**
   * Assign a rating to a user.
   *
   * @param utente     is the user to assign the rating to.
   * @param recensione is the rating to assign.
   * @return true if the rating is assigned correctly.
   */
  public boolean assignRating(Utente utente, Double recensione) {
    return utenteDao.assignRating(utente, recensione);
  }
}
