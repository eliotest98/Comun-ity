package controller.gestioneAccreditamento;

import model.Accreditamento;
import model.AccreditamentoDAO;

public class GestioneAccreditamentoServiceImpl implements GestioneAccreditamentoService{

	
	private AccreditamentoDAO accreditamentoDAO = new AccreditamentoDAO();
	
	@Override
	public Boolean saveAccreditamento(Accreditamento accreditamento) {
		return accreditamentoDAO.saveAccreditamento(accreditamento);
	}

}
