package controller.gestioneUtenza;

import java.util.ArrayList;
import java.util.List;

import model.Annuncio;
import model.AnnuncioDAO;

public class GestioneAnnunciServiceImpl implements GestioneAnnunciService{
	
	private AnnuncioDAO annuncioDAO;

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
