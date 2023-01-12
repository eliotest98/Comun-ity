package controller.gestione.annunci;

import java.util.List;
import model.Annuncio;
import model.AnnuncioDao;

/**
 * Implementation class of GestioneAnnunciService.
 */
public class GestioneAnnunciServiceImpl implements GestioneAnnunciService {

  /**
   * exclude.
   */
  private final AnnuncioDao annuncioDao = new AnnuncioDao();

  /**
   * Empty Constructor.
   **/
  public GestioneAnnunciServiceImpl() {
  }

  /**
   * Inserts an ad into the database.
   *
   * @param annuncio is the ad Object
   */
  @Override
  public boolean insertAnnuncio(Annuncio annuncio) {
    return annuncioDao.saveAnnuncio(annuncio);
  }

  /**
   * Removes an ad from the database.
   *
   * @param id is the ad identifier
   * @return true if the ad has been eliminated correctly.
   */
  @Override
  public boolean removeAnnuncio(Long id) {
    return annuncioDao.deleteAnnuncio(id);

  }

  /**
   * Update ad datas in the db.
   *
   * @param annuncio is the Ad Object already stored in the db to update.
   * @return true if the ad datas have been updated correctly.
   */
  @Override
  public boolean updateAnnuncio(Annuncio annuncio) {
    return annuncioDao.updateAnnuncio(annuncio);
  }

  /**
   * Get an ad with a specific id.
   *
   * @param id is the Ad id that you want to get.
   * @return The Ad if it exists.
   */
  @Override
  public Annuncio findAnnuncioById(Long id) {
    return annuncioDao.findAnnuncioById(id);
  }

  /**
   * Removes all the available ads published by a User from the database.
   *
   * @param autore is the ad author email
   * @return true if all the available ads for the given user have been eliminated.
   */
  @Override
  public boolean removeAllAvailableByUser(String autore) {

    List<Annuncio> availables = annuncioDao.findAllAvailableByAuthor(autore);

    //Check if the list is not empty
    if (!availables.isEmpty()) {
      availables.removeIf(available -> removeAnnuncio(available.getId()));
    }

    if (availables.isEmpty()) {
      System.out.println("Annunci disponibili rimossi correttamente.");
      return true;
    } else {
      System.out.println("L'operazione di rimozione degli annunci non è andata a buon fine.");
      return false;
    }

  }

  /**
   * Retrieves all the Errands from the db.
   *
   * @return a List of Annuncio that contains all the errands.
   */
  @Override
  public List<Annuncio> getErrands() {
    return annuncioDao.findErrands();
  }

  /**
   * Retrieves all the available Errands from the db.
   *
   * @return a List of Annuncio that contains all the available errands.
   */
  @Override
  public List<Annuncio> getAvailableErrands() {
    return annuncioDao.findAvailableErrands();
  }

  /**
   * Retrieve all the Jobs from the db.
   *
   * @return a List of Annuncio that contains all the jobs.
   */
  @Override
  public List<Annuncio> getJobs() {
    return annuncioDao.findJobs();
  }

  /**
   * Retrieve all the available Jobs from the db.
   *
   * @return a List of Annuncio that contains all the available jobs.
   */
  @Override
  public List<Annuncio> getAvailableJobs() {
    return annuncioDao.findAvailableJobs();
  }

  /**
   * Retrieve all the Ads published from the given author from the db.
   *
   * @param autore is the email of the ad's author.
   * @return a List of Annuncio that contains all the ads published from the given author.
   */
  @Override
  public List<Annuncio> getAllByAuthor(String autore) {
    return annuncioDao.findAllByAuthor(autore);
  }

  /**
   * Retrieve all the Ads accepted from the given appointee from the db.
   *
   * @param incaricato is the email of the ad's appointee.
   * @return a List of Annuncio that contains all the ads accepted from the given appointee.
   */
  @Override
  public List<Annuncio> getAllByAppointee(String incaricato) {
    return annuncioDao.findAllByAppointee(incaricato);
  }

  /**
   * Establish that the ad identified by the given id has been taken on by the specified user.
   *
   * @param id   is the ad identifier.
   * @param mail is the email of the user that accepts the ad.
   * @return true if the ad has been accepted correctly.
   */

  @Override
  public boolean acceptAnnuncio(Long id, String mail) {
    Annuncio annuncio = annuncioDao.findAnnuncioById(id);
    if (mail == null || mail.isEmpty()) {
      return false;
    }
    annuncio.setIncaricato(mail);
    return annuncioDao.updateAnnuncio(annuncio);
  }
  
  /**
   * Mark an ad as done.
   *
   * @param id is the ad's id.
   * @return true if the ad is marked as done.
   */
  @Override
  public boolean markAsDone(Long id) {
    return annuncioDao.markAsDone(id);
  }
  
  /**
   * Retrieve all the Ads, not marked as done, accepted from the given appointee from the db.
   *
   * @param incaricato is the email of the ad's appointee.
   * @return a List of Annuncio that contains all
   the ads, not marked as done, accepted from the given appointee.
   */
  @Override
  public List<Annuncio> getAllByAppointeeNotDone(String incaricato) {
    return annuncioDao.findAllByAppointeeNotDone(incaricato);
  }

}
