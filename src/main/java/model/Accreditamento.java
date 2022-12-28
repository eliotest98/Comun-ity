package model;

import javax.swing.text.Document;

public class Accreditamento {
	
	/**
	 * Attributes.
	 */
	private Utente cittadino;
	private String abilitazione;
	private Document allegato;
	private boolean accettata;
	
	/**
	 * Empty constructor.
	 */
	public Accreditamento() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param cittadino is the reference to the User who submitted the request
	 * @param abilitazione is the qualification the User wants to demonstrate he has
	 * @param allegato is the documentation issued
	 * @param accettata represents the state of the request {false = not accepted, true = accepted}
	 */
	public Accreditamento(Utente cittadino, String abilitazione, Document allegato, boolean accettata) {
		super();
		this.cittadino = cittadino;
		this.abilitazione = abilitazione;
		this.allegato = allegato;
		this.accettata = accettata;
	}

	/**
	 * @return applicant reference
	 */
	public Utente getCittadino() {
		return cittadino;
	}

	/**
	 * @param cittadino is the applicant reference to set
	 */
	public void setCittadino(Utente cittadino) {
		this.cittadino = cittadino;
	}

	/**
	 * @return the applicant qualification
	 */
	public String getAbilitazione() {
		return abilitazione;
	}

	/**
	 * @param abilitazione is the qualification to set
	 */
	public void setAbilitazione(String abilitazione) {
		this.abilitazione = abilitazione;
	}

	/**
	 * @return applicant documentation
	 */
	public Document getAllegato() {
		return allegato;
	}

	/**
	 * @param allegato is the document to set
	 */
	public void setAllegato(Document allegato) {
		this.allegato = allegato;
	}

	/**
	 * @return request state
	 */
	public boolean isAccettata() {
		return accettata;
	}

	/**
	 * @param accettata is the request state to set
	 */
	public void setAccettata(boolean accettata) {
		this.accettata = accettata;
	}
	
	
	
}
