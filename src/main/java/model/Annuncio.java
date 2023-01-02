package model;

import java.time.LocalDate;


public class Annuncio {
	
	/**
	 * Attributes.
	 */
	private Long id;
	private String abilitazioneRichiesta;
	private String autore;
	private String titolo;
	private String descrizione;
	private String indirizzo;
	private LocalDate dataPubblicazione;
	private String incaricato;
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
	 * @param autore represents Annuncio author email
	 * @param titolo represents Annuncio title
	 * @param descrizione represents Annuncio description
	 * @param indirizzo represents Annuncio useful place address
	 */
	public Annuncio(String abilitazioneRichiesta, String autore, String titolo, String descrizione, String indirizzo) {
		super();
		this.abilitazioneRichiesta = abilitazioneRichiesta;
		this.autore = autore;
		this.titolo = titolo;
		this.descrizione = descrizione;
		this.indirizzo = indirizzo;
		this.dataPubblicazione = LocalDate.now();
		this.incaricato = "nessuno";
		this.dataFine = dataPubblicazione.plusDays(30); //default expire date
	}

	/**
	 * Full Constructor for Db operations.
	 */
	public Annuncio(Long id, String abilitazioneRichiesta, String autore, String titolo, String descrizione,
			String indirizzo, LocalDate dataPubblicazione, String incaricato, LocalDate dataFine) {
		super();
		this.id = id;
		this.abilitazioneRichiesta = abilitazioneRichiesta;
		this.autore = autore;
		this.titolo = titolo;
		this.descrizione = descrizione;
		this.indirizzo = indirizzo;
		this.dataPubblicazione = dataPubblicazione;
		this.incaricato = incaricato;
		this.dataFine = dataFine;
	}
	
	
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the annuncioId
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param dataPubblicazione the dataPubblicazione to set
	 */
	public void setDataPubblicazione(LocalDate dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
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
	 * @return author's email
	 */
	public String getAutore() {
		return autore;
	}

	/**
	 * @param email is the author email to set
	 */
	public void setAutore(String email) {
		this.autore = email;
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
	 * @return the email of the User who accepted the job
	 */
	public String getIncaricato() {
		return incaricato;
	}

	/**
	 * @param email is the appointee User email to set
	 */
	public void setIncaricato(String email) {
		this.incaricato = email;
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
