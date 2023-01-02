package controller.gestioneAnnunci;

import java.util.List;

import model.Annuncio;

public interface GestioneAnnunciService {
	

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
