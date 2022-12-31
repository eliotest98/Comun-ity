package controller.gestioneUtenza;

import java.util.List;

import model.Annuncio;

public interface GestioneAnnunciService {
	
	public List<Annuncio> getJobs();
	public List<Annuncio> getJobsAvailable();

}
