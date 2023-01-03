package controller.gestioneAnnunci;

import java.io.IOException;
import java.util.List;

import model.Annuncio;

public interface GestioneAnnunciService {
	

	/**
     * Inserts an ad into the database.
     * @param annuncio is the ad Object
     */
	public void insertAnnuncio(Annuncio annuncio);
	
	/**
     * Removes an ad from the database.
     * @param id is the ad identifier
     * @return true if the ad has been eliminated correctly.
     */
	public boolean removeAnnuncio(Long id);
	
	/**
     * Removes all the available ads published by a User from the database.
     * @param autore is the ad author email
     * @return true if all the available ads for the given user have been eliminated.
     */
	public boolean removeAllAvailableByUser(String autore);
	
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
}
