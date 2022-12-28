package model;

public class Annuncio {
	
	private long id;
	private String abilitazioneRichiesta;
	private Utente autore;
	private String titolo;
	private String descrizione;
	private String indirizzo;
	
	/**
	 * Empty constructor.
	 */
	public Annuncio() {
		super();
	}
	
	/**
	 * Constructor.
	 * @param id represents Annuncio identifier
	 * @param abilitazioneRichiesta represents the qualification needed to accept the job {"Nessuna" = Commissione}
	 * @param autore represents Annuncio author
	 * @param titolo represents Annuncio title
	 * @param descrizione represents Annuncio description
	 * @param indirizzo represents Annuncio useful place address
	 */
	public Annuncio(long id, String abilitazioneRichiesta, Utente autore, String titolo, String descrizione,
			String indirizzo) {
		super();
		this.id = id;
		this.abilitazioneRichiesta = abilitazioneRichiesta;
		this.autore = autore;
		this.titolo = titolo;
		this.descrizione = descrizione;
		this.indirizzo = indirizzo;
	}

	/**
	 * @return Annuncio identifier
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id is the Annuncio identifier to set
	 */
	public void setId(long id) {
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
	public Utente getAutore() {
		return autore;
	}

	/**
	 * @param autore is the author to set
	 */
	public void setAutore(Utente autore) {
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

	@Override
	public String toString() {
		return "Annuncio [id=" + id + ", abilitazioneRichiesta=" + abilitazioneRichiesta + ", autore=" + autore
				+ ", titolo=" + titolo + ", descrizione=" + descrizione + ", indirizzo=" + indirizzo + "]";
	}

	
}
