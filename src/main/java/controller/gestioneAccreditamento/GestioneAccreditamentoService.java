package controller.gestioneAccreditamento;

import java.util.List;
import model.Accreditamento;

/**
 * Inteface implemented in GestioneAccreditamentoServiceImpl.
 */
public interface GestioneAccreditamentoService {
  
  /**
   * Saves a cartification approval request in the db.
   *
   * @param accreditamento is the Object that contains alle the datas within the reques.
   * @return true if the request is saved correctly.
   */
  boolean saveAccreditamento(Accreditamento accreditamento);
  
  /**
   * Retrieves all the submitted and yet to examine accreditation requests.
   *
   * @return a List of Accreditamento that contains all the accreditation requests
   pending to be accepted or declined. 
   */
  List<Accreditamento> getAllUnexamined();
  
  /**
   * Find the accreditation request pending for examination
   * given its applicant email.
   *
   * @param richiedente is the applicant email refering to the accreditation request to search for.
   * @return the accreditation Object if it exists.
   */
  Accreditamento getByApplicant(String richiedente);
  
  /**
   * Approves the user request of accreditation and elect him to Pro.
   *
   * @param richiedente is the applicant email.
   * @return true if the request is approved correctly and the user role is updated to Pro.
   */
  boolean approveRequest(String richiedente);
  
  /**
   * Declines the user request of accreditation.
   *
   * @param richiedente is the applicant email.
   * @return true if the request is declined correctly.
   */
  boolean declineRequest(String richiedente);

}
