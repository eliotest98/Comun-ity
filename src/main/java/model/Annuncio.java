package model;

import java.time.LocalDate;

import org.bson.types.ObjectId;

public class Annuncio {
	
	/**
	 * Attributes.
	 */
	private ObjectId id;
	private Integer annuncioId;
	private String abilitazioneRichiesta;
	private Integer autore;
	private String titolo;
	private String descrizione;
	private String indirizzo;
	private LocalDate dataPubblicazione;
	private Utente incaricato;
	private LocalDate dataFine;
	
	/**
	 * Empty constructor.
	 */
	public Annuncio() {
		super();
	}
	
	/**
	 * Constructor.
	 *
	 * @param abilitazioneRichiesta represents the qualification needed to accept the job {"Nessuna" = Commissione}
	 * @param autore represents Annuncio author
	 * @param titolo represents Annuncio title
	 * @param descrizione represents Annuncio description
	 * @param indirizzo represents Annuncio useful place address
	 */
	public Annuncio(Integer annuncioId, String abilitazioneRichiesta, Integer autore, String titolo, String descrizione,
			String indirizzo) {
		super();
		this.annuncioId= annuncioId;
		this.abilitazioneRichiesta = abilitazioneRichiesta;
		this.autore = autore;
		this.titolo = titolo;
		this.descrizione = descrizione;
		this.indirizzo = indirizzo;
		this.dataPubblicazione = LocalDate.now();
		this.dataFine = dataPubblicazione.plusDays(30); //default expire date
	}
	
	

	/**
	 * @return the annuncioId
	 */
	public Integer getAnnuncioId() {
		return annuncioId;
	}

	/**
	 * @param annuncioId the annuncioId to set
	 */
	public void setAnnuncioId(Integer annuncioId) {
		this.annuncioId = annuncioId;
	}

	/**
	 * @param dataPubblicazione the dataPubblicazione to set
	 */
	public void setDataPubblicazione(LocalDate dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}

	/**
	 * @return Annuncio identifier
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * @param id is the Annuncio identifier to set
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * @return the qualification needed
	 */
	public String getAbilitazioneRichiesta() {
		return abilitazioneRichiesta;
	}

	/**
	 * @param abilitazioneRichiesta is the qualification to set
	 */
	public void setAbilitazioneRichiesta(String abilitazioneRichiesta) {
		this.abilitazioneRichiesta = abilitazioneRichiesta;
	}

	/**
	 * @return Annuncio author
	 */
	public Integer getAutore() {
		return autore;
	}

	/**
	 * @param autore is the author to set
	 */
	public void setAutore(Integer autore) {
		this.autore = autore;
	}

	/**
	 * @return Annuncio title
	 */
	public String getTitolo() {
		return titolo;
	}

	/**
	 * @param titolo is the title to set
	 */
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	/**
	 * @return Annuncio description
	 */
	public String getDescrizione() {
		return descrizione;
	}

	/**
	 * @param descrizione is the description to set
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	/**
	 * @return Annuncio useful address
	 */
	public String getIndirizzo() {
		return indirizzo;
	}

	/**
	 * @param indirizzo is the addresss to set
	 */
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	/**
	 * @return the publication date
	 */
	public LocalDate getDataPubblicazione() {
		return dataPubblicazione;
	}

	/**
	 * @return the User who accepted the job
	 */
	public Utente getIncaricato() {
		return incaricato;
	}

	/**
	 * @param incaricato is the appointee User to set
	 */
	public void setIncaricato(Utente incaricato) {
		this.incaricato = incaricato;
	}

	/**
	 * @return the date on which the ad expire OR has been accepted
	 */
	public LocalDate getDataFine() {
		return dataFine;
	}

	/**
	 * @param dataFine is the date of removal from the available job list to set
	 */
	public void setDataFine(LocalDate dataFine) {
		this.dataFine = dataFine;
	}

	@Override
	public String toString() {
		return "Annuncio [id=" + id + ", abilitazioneRichiesta=" + abilitazioneRichiesta + ", autore=" + autore
				+ ", titolo=" + titolo + ", descrizione=" + descrizione + ", indirizzo=" + indirizzo
				+ ", dataPubblicazione=" + dataPubblicazione + ", incaricato=" + incaricato + ", dataFine=" + dataFine
				+ "]";
	}
	
}
