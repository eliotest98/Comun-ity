package model;

public class Annuncio {
	
	private long id;
	private Utente autore;
	private String titolo;
	private String descrizione;
	private String indirizzo;
	private boolean abilitazioneRichiesta;
	
	public Utente getAutore() {
		return autore;
	}
	public void setAutore(Utente autore) {
		this.autore = autore;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public boolean isAbilitazioneRichiesta() {
		return abilitazioneRichiesta;
	}
	public void setAbilitazioneRichiesta(boolean abilitazioneRichiesta) {
		this.abilitazioneRichiesta = abilitazioneRichiesta;
	}
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	
	public Annuncio(long id, Utente autore, String titolo, String descrizione, String indirizzo,
			boolean abilitazioneRichiesta) {
		super();
		this.id = id;
		this.autore = autore;
		this.titolo = titolo;
		this.descrizione = descrizione;
		this.indirizzo = indirizzo;
		this.abilitazioneRichiesta = abilitazioneRichiesta;
	}
	
	
	@Override
	public String toString() {
		return "Annuncio [autore=" + autore + ", titolo=" + titolo + ", descrizione=" + descrizione + ", indirizzo="
				+ indirizzo + ", abilitazioneRichiesta=" + abilitazioneRichiesta + "]";
	}
	
	

}
