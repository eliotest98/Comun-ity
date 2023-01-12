package controller.gestione.reputazione;

import model.Annuncio;
import model.AnnuncioDAO;
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
  private final AnnuncioDAO annuncioDao = new AnnuncioDAO();

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
