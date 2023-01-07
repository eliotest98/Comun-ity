package controller.gestioneAccreditamento;

import java.util.List;

import model.Accreditamento;
import model.AccreditamentoDAO;
import model.Utente;
import model.UtenteDAO;

/**
 * Implementation class of GestioneAccreditamentoService.
 */
public class GestioneAccreditamentoServiceImpl implements GestioneAccreditamentoService {

  /**
   * exclude.
   **/
  private AccreditamentoDAO accreditamentoDAO = new AccreditamentoDAO();
  private UtenteDAO utenteDAO = new UtenteDAO();

  /**
   * Empty Constructor.
   */
  public GestioneAccreditamentoServiceImpl() {
  }

  /**
   * Saves a cartification approval request in the db.
   *
   * @param accreditamento is the Object that contains alle the datas within the request
   * @return true if the request is saved correctly.
   */
  @Override
  public boolean saveAccreditamento(Accreditamento accreditamento) {
    return accreditamentoDAO.saveAccreditamento(accreditamento);
  }
  
  /**
   * Retrieves all the submitted and yet to examine accreditation requests.
   * 
   * @return a List of Accreditamento that contains all the accreditation requests
   * pending to be accepted or declined. 
   */
  @Override
  public List<Accreditamento> getAllUnexamined() {
	  return accreditamentoDAO.findAllUnexamined();
  }
  
  /**
   * Approves the user request of accreditation and elect him to Pro.
   * 
   * @param richiedente is the applicant email.
   * @return true if the request is approved correctly and the user role is updated to Pro.
   */
  @Override
  public boolean approveRequest(String richiedente) {
	  
	  Accreditamento accreditamento = accreditamentoDAO.findAccreditamentoBySubmitter(richiedente);
	  Utente utente = utenteDAO.findUtenteByMail(richiedente);
	  
	  accreditamento.setStato("accettata");
	  utente.setRuolo("professionista");
	  utente.setAbilitazione(accreditamento.getAbilitazione());

	  return (accreditamentoDAO.updateAccreditamento(accreditamento) && utenteDAO.updateUtente(utente));
  }
  
  /**
   * Declines the user request of accreditation.
   * 
   * @param richiedente is the applicant email.
   * @return true if the request is declined correctly.
   */
  @Override
  public boolean declineRequest(String richiedente) {
	  
	  Accreditamento accreditamento = accreditamentoDAO.findAccreditamentoBySubmitter(richiedente);
	  
	  accreditamento.setStato("rifiutata");
	  
	  return accreditamentoDAO.updateAccreditamento(accreditamento);
  }

}
