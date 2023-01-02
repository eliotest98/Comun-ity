package controller.gestioneAccreditamento;

import model.Accreditamento;

public interface GestioneAccreditamentoService {
	
	/**
	 * Saves a cartification approval request in the db.
	 * @param accreditamento is the Object that contains alle the datas within the request
     * @return true if the request is saved correctly.
     */
	Boolean saveAccreditamento(Accreditamento accreditamento);

}
