package controller.gestioneAnnunci;

import java.io.IOException;
import java.util.List;

import model.Annuncio;
import model.Utente;

public interface GestioneAnnunciService {
	

	/**
     * Inserts an ad into the database.
     * @param annuncio is the ad Object
     */
	void insertAnnuncio(Annuncio annuncio);
	
	/**
     * Removes an ad from the database.
     * @param id is the ad identifier
     * @return true if the ad has been eliminated correctly.
     */
	boolean removeAnnuncio(Long id);
	
	/**
	 * Update ad datas in the db.
	 * @param annuncio is the Ad Object already stored in the db to update.
	 * @return true if the ad datas have been updated correctly.
	 */
	boolean updateAnnuncio(Annuncio annuncio);
	
	/**
     * Removes all the available ads published by a User from the database.
     * @param autore is the ad author email
     * @return true if all the available ads for the given user have been eliminated.
     */
	boolean removeAllAvailableByUser(String autore);
	
	/**
	 * Retrieves all the Errands from the db.
     * @return a List of Annuncio that contains all the errands.
     */
	List<Annuncio> getErrands();
	
	/**
	 * Retrieves all the available Errands from the db.
     * @return a List of Annuncio that contains all the available errands.
     */
	List<Annuncio> getAvailableErrands();
	
	/**
	 * Retrieve all the Jobs from the db.
     * @return a List of Annuncio that contains all the jobs.
     */
	List<Annuncio> getJobs();
	
	/**
	 * Retrieve all the available Jobs from the db.
     * @return a List of Annuncio that contains all the available jobs.
     */
	List<Annuncio> getAvailableJobs();
	
	/**
     * Establish that the ad identified by the given id has been taken on by the specified user.
     * @param id is the ad identifier.
     * @param incaricato is the the email of the user that accepts the ad.
     * @return true if the ad has been accepted correctly.
     */
	boolean acceptAnnuncio(Long id, String incaricato);
}