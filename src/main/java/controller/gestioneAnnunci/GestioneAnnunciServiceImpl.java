package controller.gestioneAnnunci;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Annuncio;
import model.AnnuncioDAO;

public class GestioneAnnunciServiceImpl implements GestioneAnnunciService{
	
	/**
     * this method inserts an ad into the database.
     * @param utente annuncio into the db.
     * @return void.
     * @throws IOException
     * @throws IllegalArgumentException
     */
	@Override
	public void insertAnnuncio(Annuncio annuncio) {
		annuncioDAO.saveAnnuncio(annuncio);
		
	}
	
	/**
     * this method removes an ad into the database.
     * @param utente annuncio into the db.
     * @return void.
     * @throws IOException
     * @throws IllegalArgumentException
     */
	@Override
	public void removeAnnuncio(Long annuncio) {
		annuncioDAO.deleteAnnuncio(annuncio);
		
	}

	private AnnuncioDAO annuncioDAO;

	/**
     * Empty Constructor.
     **/
	public GestioneAnnunciServiceImpl() {
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
