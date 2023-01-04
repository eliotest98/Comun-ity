package controller.gestioneAnnunci;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Annuncio;
import model.AnnuncioDAO;

public class GestioneAnnunciServiceImpl implements GestioneAnnunciService{

	/**
	 * @exclude
	 */
	private AnnuncioDAO annuncioDAO= new AnnuncioDAO();

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
	 * @return true if the ad has been eliminated correctly.
	 */
	@Override
	public boolean removeAnnuncio(Long id) {
		return annuncioDAO.deleteAnnuncio(id);

	}
	
	/**
	 * Update ad datas in the db.
	 * @param annuncio is the Ad Object already stored in the db to update.
	 * @return true if the ad datas have been updated correctly.
	 */
	@Override
	public boolean updateAnnuncio(Annuncio annuncio) {
		return annuncioDAO.updateAnnuncio(annuncio);
	}
	
	/**
	 * Get an ad with a specific id.
	 * @param id is the Ad id that you want to get.
	 * @return The Ad if it exists.
	 */
	@Override
	public Annuncio findAnnuncioById(Long id) {
		return annuncioDAO.findAnnuncioById(id);
	}

	/**
	 * Removes all the available ads published by a User from the database.
	 * @param autore is the ad author email
	 * @return true if all the available ads for the given user have been eliminated.
	 */
	@Override
	public boolean removeAllAvailableByUser(String autore) {

		List<Annuncio> availables = new ArrayList<Annuncio>();
		availables = annuncioDAO.findAllAvailableByUser(autore);

		//Check if the list is not empty
		if(!availables.isEmpty()) {
			for(Annuncio available : availables) {
				//Check if the ads are being removed correctly
				if(removeAnnuncio(available.getId())) {
					//If they are actually removed, they can be also removed from the list
					availables.remove(available);
				}
			}
		}

		if(availables.isEmpty()) {
			System.out.println("Annunci disponibili rimossi correttamente.");
			return true;
		}else {
			System.out.println("L'operazione di rimozione degli annunci non � andata a buon fine.");
			return false;
		}

	}

	/**
	 * Retrieves all the Errands from the db.
	 * @return a List of Annuncio that contains all the errands.
	 */
	@Override
	public List<Annuncio> getErrands() {
		List<Annuncio> commissioni= new ArrayList<Annuncio>();
		commissioni= annuncioDAO.findErrands();
		return commissioni;
	}

	/**
	 * Retrieves all the available Errands from the db.
	 * @return a List of Annuncio that contains all the available errands.
	 */
	@Override
	public List<Annuncio> getAvailableErrands() {
		List<Annuncio> commissioni = new ArrayList<Annuncio>();
		commissioni = annuncioDAO.findAvailableErrands();
		return commissioni;
	}

	/**
	 * Retrieve all the Jobs from the db.
	 * @return a List of Annuncio that contains all the jobs.
	 */
	@Override
	public List<Annuncio> getJobs(){

		List<Annuncio> lavori= new ArrayList<Annuncio>();

		lavori = annuncioDAO.findJobs();

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

		lavori= annuncioDAO.findAvailableJobs();

		if(lavori.isEmpty()) {
			System.out.println("Non ci sono lavori");
		}

		return lavori;
	}

	/**
     * Establish that the ad identified by the given id has been taken on by the specified user.
     * @param id is the ad identifier.
     * @param incaricato is the the email of the user that accepts the ad.
     * @return true if the ad has been accepted correctly.
     */
	@Override
	public boolean acceptAnnuncio(Long id, String mail) {
		
		Annuncio annuncio = annuncioDAO.findAnnuncioById(id);
		
		annuncio.setIncaricato(mail);
		annuncio.setDataFine(LocalDate.now());
		
		return annuncioDAO.updateAnnuncio(annuncio);
	}
}
