package controller.gestioneAnnunci;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Annuncio;
import model.AnnuncioDAO;
import model.UtenteDAO;

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

	public GestioneAnnunciServiceImpl() {
	    }

	@Override
	public List<Annuncio> getCommissioni() {
		List<Annuncio> commissioni= new ArrayList<Annuncio>();
		commissioni= annuncioDAO.findCommissioni();
		return commissioni;
	}

	@Override
	public List<Annuncio> getCommissioniDisponibili() {
		List<Annuncio> commissioni= new ArrayList<Annuncio>();
		commissioni= annuncioDAO.findCommissioni();
		return commissioni;
	}

	public GestioneAnnunciServiceImpl(AnnuncioDAO annuncioDAO) {
		super();
		this.annuncioDAO = annuncioDAO;
	}
	
	//Restituisce una lista di lavori
	public List<Annuncio> getJobs(){
		
		List<Annuncio> lavori= new ArrayList<Annuncio>();
		
		lavori= annuncioDAO.findLavori();
		
		if(lavori.isEmpty()) {
			System.out.println("Non ci sono lavori");
		}
		
		return lavori;
	}
	
	//Restituisce una lista di lavori disponibili
	public List<Annuncio> getJobsAvailable(){
		
		List<Annuncio> lavori= new ArrayList<Annuncio>();
		
		lavori= annuncioDAO.findLavoriDisponibili();
		
		if(lavori.isEmpty()) {
			System.out.println("Non ci sono lavori");
		}
		
		return lavori;
	}

}
