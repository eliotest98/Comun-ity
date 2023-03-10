package controller.gestione.reputazione.service;

import model.Annuncio;
import model.AnnuncioDao;
import model.Utente;
import model.UtenteDao;

/**
 * Implementation class of GestioneReputazioneService.
 */
public class GestioneReputazioneServiceImpl implements GestioneReputazioneService {

  /**
   * exclude.
   */
  private final UtenteDao utenteDao = new UtenteDao();
  private final AnnuncioDao annuncioDao = new AnnuncioDao();

  /**
   * Empty Constructor.
   **/
  public GestioneReputazioneServiceImpl() {
  }

  /**
   * Review a completed ad and assign a rating to a user.
   *
   * @param annuncio   is the completed ad
   * @param utente     is the user to assign the rating to.
   * @param recensione is the rating to assign.
   * @return true if the rating is assigned correctly for both Annuncio and Utente.
   */
  @Override
  public boolean assignRating(Annuncio annuncio, Utente utente, Double recensione) {

    annuncio.setRecensione(recensione);

    return (utenteDao.assignRating(utente, recensione) && annuncioDao.updateAnnuncio(annuncio));
  }
}
