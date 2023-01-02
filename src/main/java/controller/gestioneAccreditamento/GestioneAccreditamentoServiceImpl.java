package controller.gestioneAccreditamento;

import model.Accreditamento;
import model.AccreditamentoDAO;

public class GestioneAccreditamentoServiceImpl implements GestioneAccreditamentoService{

	/**
     * @exclude
     **/
	private AccreditamentoDAO accreditamentoDAO = new AccreditamentoDAO();
	
	/**
	 * Empty Constructor.
	 */
	public GestioneAccreditamentoServiceImpl() {
	}

	/**
	 * Saves a cartification approval request in the db.
	 * @param accreditamento is the Object that contains alle the datas within the request
     * @return true if the request is saved correctly.
     */
	@Override
	public Boolean saveAccreditamento(Accreditamento accreditamento) {
		return accreditamentoDAO.saveAccreditamento(accreditamento);
	}

}
