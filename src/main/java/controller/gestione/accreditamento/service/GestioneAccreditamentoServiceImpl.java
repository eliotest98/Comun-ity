package controller.gestione.accreditamento.service;

import java.time.LocalDate;
import java.util.List;
import model.Accreditamento;
import model.AccreditamentoDao;
import model.Utente;
import model.UtenteDao;

/**
 * Implementation class of GestioneAccreditamentoService.
 */
public class GestioneAccreditamentoServiceImpl implements GestioneAccreditamentoService {

  /**
   * exclude.
   **/
  private AccreditamentoDao accreditamentoDao = new AccreditamentoDao();
  private UtenteDao utenteDao = new UtenteDao();

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
    return accreditamentoDao.saveAccreditamento(accreditamento);
  }
  
  /**
   * Retrieves all the submitted and yet to examine accreditation requests.
   *
   * @return a List of Accreditamento that contains all the accreditation requests
   pending to be accepted or declined. 
   */
  @Override
  public List<Accreditamento> getAllUnexamined() {
    return accreditamentoDao.findAllUnexamined();
  }
  
  /**
   * Find the accreditation request pending for examination
   * given its applicant email.
   *
   * @param richiedente is the applicant email refering to the accreditation request to search for.
   * @return the accreditation Object if it exists.
   */
  @Override
  public Accreditamento getByApplicant(String richiedente) {
    return accreditamentoDao.findAccreditamentoBySubmitter(richiedente);
  }
  
  /**
   * Approves the user request of accreditation and elect him to Pro.
   *
   * @param richiedente is the applicant email.
   * @return true if the request is approved correctly and the user role is updated to Pro.
   */
  @Override
  public boolean approveRequest(String richiedente) {

    Accreditamento accreditamento = accreditamentoDao.findAccreditamentoBySubmitter(richiedente);
    Utente utente = utenteDao.findUtenteByMail(richiedente);
    
    accreditamento.setDataVisione(LocalDate.now());
    accreditamento.setStato("accettata");
    utente.setRuolo("professionista");
    utente.setAbilitazione(accreditamento.getAbilitazione());

    return (accreditamentoDao.updateAccreditamento(accreditamento) && utenteDao.updateUtente(
        utente));
  }
  
  /**
   * Declines the user request of accreditation.
   *
   * @param richiedente is the applicant email.
   * @return true if the request is declined correctly.
   */
  @Override
  public boolean declineRequest(String richiedente) {

    Accreditamento accreditamento = accreditamentoDao.findAccreditamentoBySubmitter(richiedente);

    accreditamento.setDataVisione(LocalDate.now());
    accreditamento.setStato("rifiutata");

    return accreditamentoDao.updateAccreditamento(accreditamento);
  }

}
