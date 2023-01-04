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
	public boolean insertAnnuncio(Annuncio annuncio) {
		if(annuncioDAO.saveAnnuncio(annuncio))
			return true;
		return false;

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
		availables = annuncioDAO.findAllAvailableByAuthor(autore);

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
		return annuncioDAO.findErrands();
	}

	/**
	 * Retrieves all the available Errands from the db.
	 * @return a List of Annuncio that contains all the available errands.
	 */
	@Override
	public List<Annuncio> getAvailableErrands() {
		return annuncioDAO.findAvailableErrands();
	}

	/**
	 * Retrieve all the Jobs from the db.
	 * @return a List of Annuncio that contains all the jobs.
	 */
	@Override
	public List<Annuncio> getJobs(){
		return annuncioDAO.findJobs();
	}

	/**
	 * Retrieve all the available Jobs from the db.
	 * @return a List of Annuncio that contains all the available jobs.
	 */
	@Override
	public List<Annuncio> getAvailableJobs(){
		return annuncioDAO.findAvailableJobs();
	}
	
	/**
	 * Retrieve all the Ads published from the given author from the db.
	 * @param autore is the email of the ad's author. 
     * @return a List of Annuncio that contains all the ads published from the given author.
     */
	@Override
	public List<Annuncio> getAllByAuthor(String autore){
		return annuncioDAO.findAllByAuthor(autore);
	}
	
	/**
	 * Retrieve all the Ads accepted from the given appointee from the db.
	 * @param incaricato is the email of the ad's appointee. 
     * @return a List of Annuncio that contains all the ads accepted from the given appointee.
     */
	@Override
	public List<Annuncio> getAllByAppointee(String incaricato){
		return annuncioDAO.findAllByAppointee(incaricato);
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
