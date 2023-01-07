package controller.gestioneAnnunci;

import java.util.List;
import model.Annuncio;

/**
 * Interface implemented in GestioneAnnuncioServiceImpl.
 */
public interface GestioneAnnunciService {


  /**
   * Inserts an ad into the database.
   *
   * @param annuncio is the ad Object.
   * @return true if the ad has been saved correctly.
   */
  boolean insertAnnuncio(Annuncio annuncio);

  /**
   * Removes an ad from the database.
   *
   * @param id is the ad identifier
   * @return true if the ad has been eliminated correctly.
   */
  boolean removeAnnuncio(Long id);

  /**
   * Update ad datas in the db.
   *
   * @param annuncio is the Ad Object already stored in the db to update.
   * @return true if the ad datas have been updated correctly.
   */
  boolean updateAnnuncio(Annuncio annuncio);

  /**
   * Get an ad with a specific id.
   *
   * @param id is the Ad id that you want to get.
   * @return The Ad if it exists.
   */
  public Annuncio findAnnuncioById(Long id);

  /**
   * Removes all the available ads published by a User from the database.
   *
   * @param autore is the ad author email
   * @return true if all the available ads for the given user have been eliminated.
   */
  boolean removeAllAvailableByUser(String autore);

  /**
   * Retrieves all the Errands from the db.
   *
   * @return a List of Annuncio that contains all the errands.
   */
  List<Annuncio> getErrands();

  /**
   * Retrieves all the available Errands from the db.
   *
   * @return a List of Annuncio that contains all the available errands.
   */
  List<Annuncio> getAvailableErrands();

  /**
   * Retrieve all the Jobs from the db.
   *
   * @return a List of Annuncio that contains all the jobs.
   */
  List<Annuncio> getJobs();

  /**
   * Retrieve all the available Jobs from the db.
   *
   * @return a List of Annuncio that contains all the available jobs.
   */
  List<Annuncio> getAvailableJobs();

  /**
   * Retrieve all the Ads published from the given author from the db.
   *
   * @param autore is the email of the ad's author.
   * @return a List of Annuncio that contains all the ads published from the given author.
   */
  List<Annuncio> getAllByAuthor(String autore);

  /**
   * Retrieve all the Ads accepted from the given appointee from the db.
   *
   * @param incaricato is the email of the ad's appointee.
   * @return a List of Annuncio that contains all the ads accepted from the given appointee.
   */
  List<Annuncio> getAllByAppointee(String incaricato);

  /**
   * Establish that the ad identified by the given id has been taken on by the specified user.
   *
   * @param id         is the ad identifier.
   * @param incaricato is the the email of the user that accepts the ad.
   * @return true if the ad has been accepted correctly.
   */
  boolean acceptAnnuncio(Long id, String incaricato);
  
  /**
   * Mark an ad as done.
   *
   * @param ad's id.
   * @return true if the ad is marked as done.
   */
  public boolean markAsDone(Long id);


}
