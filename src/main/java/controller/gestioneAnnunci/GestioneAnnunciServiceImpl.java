package controller.gestioneAnnunci;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Annuncio;
import model.AnnuncioDAO;

public class GestioneAnnunciServiceImpl implements GestioneAnnunciService{
	
	/**
     * @exclude
     */
	private AnnuncioDAO annuncioDAO;

	/**
     * Empty Constructor.
     **/
	public GestioneAnnunciServiceImpl() {
	}
	
	/**
     * Inserts an ad into the database.
     * @param annuncio is the ad Object
     */
	@Override
	public void insertAnnuncio(Annuncio annuncio) {
		annuncioDAO.saveAnnuncio(annuncio);
		
	}
	
	/**
     * Removes an ad from the database.
     * @param id is the ad identifier
     */
	@Override
	public void removeAnnuncio(Long annuncio) {
		annuncioDAO.deleteAnnuncio(annuncio);
		
	}
	
	/**
	 * Retrieves all the Errands from the db.
     * @return a List of Annuncio that contains all the errands.
     */
	@Override
	public List<Annuncio> getErrands() {
		List<Annuncio> commissioni= new ArrayList<Annuncio>();
		commissioni= annuncioDAO.findCommissioni();
		return commissioni;
	}

	/**
	 * Retrieves all the available Errands from the db.
     * @return a List of Annuncio that contains all the available errands.
     */
	@Override
	public List<Annuncio> getAvailableErrands() {
		List<Annuncio> commissioni= new ArrayList<Annuncio>();
		commissioni= annuncioDAO.findCommissioni();
		return commissioni;
	}

	/**
	 * Retrieve all the Jobs from the db.
     * @return a List of Annuncio that contains all the jobs.
     */
	@Override
	public List<Annuncio> getJobs(){
		
		List<Annuncio> lavori= new ArrayList<Annuncio>();
		
		lavori= annuncioDAO.findLavori();
		
		if(lavori.isEmpty()) {
			System.out.println("Non ci sono lavori");
		}
		
		return lavori;
	}
	
	/**
	 * Retrieve all the available Jobs from the db.
     * @return a List of Annuncio that contains all the available jobs.
     */
	@Override
	public List<Annuncio> getAvailableJobs(){
		
		List<Annuncio> lavori= new ArrayList<Annuncio>();
		
		lavori= annuncioDAO.findLavoriDisponibili();
		
		if(lavori.isEmpty()) {
			System.out.println("Non ci sono lavori");
		}
		
		return lavori;
	}

}
