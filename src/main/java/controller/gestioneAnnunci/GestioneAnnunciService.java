package controller.gestioneAnnunci;

import java.util.List;

import model.Annuncio;
import model.Utente;

public interface GestioneAnnunciService {
	
	public List<Annuncio> getJobs();
	public List<Annuncio> getJobsAvailable();
	

}
