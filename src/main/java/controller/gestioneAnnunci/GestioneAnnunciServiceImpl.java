package controller.gestioneAnnunci;

import java.time.LocalDate;
import java.util.List;
import model.Annuncio;
import model.AnnuncioDAO;

/**
 * Implementation class of GestioneAnnunciService.
 */
public class GestioneAnnunciServiceImpl implements GestioneAnnunciService {

  /**
   * exclude.
   */
  private final AnnuncioDAO annuncioDAO = new AnnuncioDAO();

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
    return annuncioDAO.saveAnnuncio(annuncio);
  }

  /**
   * Removes an ad from the database.
   *
   * @param id is the ad identifier
   * @return true if the ad has been eliminated correctly.
   */
  @Override
  public boolean removeAnnuncio(Long id) {
    return annuncioDAO.deleteAnnuncio(id);

  }

  /**
   * Update ad datas in the db.
   *
   * @param annuncio is the Ad Object already stored in the db to update.
   * @return true if the ad datas have been updated correctly.
   */
  @Override
  public boolean updateAnnuncio(Annuncio annuncio) {
    return annuncioDAO.updateAnnuncio(annuncio);
  }

  /**
   * Get an ad with a specific id.
   *
   * @param id is the Ad id that you want to get.
   * @return The Ad if it exists.
   */
  @Override
  public Annuncio findAnnuncioById(Long id) {
    return annuncioDAO.findAnnuncioById(id);
  }

  /**
   * Removes all the available ads published by a User from the database.
   *
   * @param autore is the ad author email
   * @return true if all the available ads for the given user have been eliminated.
   */
  @Override
  public boolean removeAllAvailableByUser(String autore) {

    List<Annuncio> availables = annuncioDAO.findAllAvailableByAuthor(autore);

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
    return annuncioDAO.findErrands();
  }

  /**
   * Retrieves all the available Errands from the db.
   *
   * @return a List of Annuncio that contains all the available errands.
   */
  @Override
  public List<Annuncio> getAvailableErrands() {
    return annuncioDAO.findAvailableErrands();
  }

  /**
   * Retrieve all the Jobs from the db.
   *
   * @return a List of Annuncio that contains all the jobs.
   */
  @Override
  public List<Annuncio> getJobs() {
    return annuncioDAO.findJobs();
  }

  /**
   * Retrieve all the available Jobs from the db.
   *
   * @return a List of Annuncio that contains all the available jobs.
   */
  @Override
  public List<Annuncio> getAvailableJobs() {
    return annuncioDAO.findAvailableJobs();
  }

  /**
   * Retrieve all the Ads published from the given author from the db.
   *
   * @param autore is the email of the ad's author.
   * @return a List of Annuncio that contains all the ads published from the given author.
   */
  @Override
  public List<Annuncio> getAllByAuthor(String autore) {
    return annuncioDAO.findAllByAuthor(autore);
  }

  /**
   * Retrieve all the Ads accepted from the given appointee from the db.
   *
   * @param incaricato is the email of the ad's appointee.
   * @return a List of Annuncio that contains all the ads accepted from the given appointee.
   */
  @Override
  public List<Annuncio> getAllByAppointee(String incaricato) {
    return annuncioDAO.findAllByAppointee(incaricato);
  }

  /**
   * Establish that the ad identified by the given id has been taken on by the specified user.
   *
   * @param id   is the ad identifier.
   * @param mail is the the email of the user that accepts the ad.
   * @return true if the ad has been accepted correctly.
   */

  @Override
  public boolean acceptAnnuncio(Long id, String mail) {
    Annuncio annuncio = annuncioDAO.findAnnuncioById(id);
    if (mail == null || mail.isEmpty()) {
      return false;
    }
    annuncio.setIncaricato(mail);
    return annuncioDAO.updateAnnuncio(annuncio);
  }
  
  /**
   * Mark an ad as done.
   *
   * @param ad's id.
   * @return true if the ad is marked as done.
   */
  @Override
  public boolean markAsDone(Long id) {
	  return annuncioDAO.markAsDone(id);
  }

}
