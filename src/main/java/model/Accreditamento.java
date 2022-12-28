package model;

import java.time.LocalDate;

import javax.swing.text.Document;

import org.bson.types.ObjectId;

public class Accreditamento {
	
	/**
	 * Attributes.
	 */
	private ObjectId id;
	private Utente cittadino;
	private String abilitazione;
	private Document allegato;
	private LocalDate dataSottomissione;
	private LocalDate dataVisione;
	private String stato;
	
	
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
	 * @param stato represents the state of the request {SOTTOMESSA, RIFIUTATA, ACCETTATA}
	 */
	public Accreditamento(Utente cittadino, String abilitazione, Document allegato,
			LocalDate dataVisione, String stato) {
		super();
		this.cittadino = cittadino;
		this.abilitazione = abilitazione;
		this.allegato = allegato;
		this.dataSottomissione = LocalDate.now();
		this.stato = stato;
	}

	/**
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * @param id is the id to set
	 */
	public void setId(ObjectId id) {
		this.id = id;
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
	 * @return application date
	 */
	public LocalDate getDataSottomissione() {
		return dataSottomissione;
	}

	/**
	 * @param dataSottomissione is the application date to set
	 */
	public void setDataSottomissione(LocalDate dataSottomissione) {
		this.dataSottomissione = dataSottomissione;
	}

	/**
	 * @return the examination date
	 */
	public LocalDate getDataVisione() {
		return dataVisione;
	}

	/**
	 * @param dataVisione is the examination date to set
	 */
	public void setDataVisione(LocalDate dataVisione) {
		this.dataVisione = dataVisione;
	}

	/**
	 * @return the request state
	 */
	public String getStato() {
		return stato;
	}

	/**
	 * @param stato is the state of the request to set
	 */
	public void setStato(String stato) {
		this.stato = stato;
	}

	@Override
	public String toString() {
		return "Accreditamento [cittadino=" + cittadino + ", abilitazione=" + abilitazione + ", allegato=" + allegato
				+ ", dataSottomissione=" + dataSottomissione + ", dataVisione=" + dataVisione + ", stato=" + stato
				+ "]";
	}
	
	
}
