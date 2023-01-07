package model;

import java.time.LocalDate;

/**
 * Annuncio bean.
 */
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
  private Double recensione;

  /**
   * Empty constructor.
   */
  public Annuncio() {
    super();
  }

  /**
   * Constructor.
   *
   * @param abilitazioneRichiesta represents the qualification needed to accept the job {"Nessuna" =
   *                              Commissione}
   * @param autore                represents Annuncio author email
   * @param titolo                represents Annuncio title
   * @param descrizione           represents Annuncio description
   * @param indirizzo             represents Annuncio useful place address
   */
  public Annuncio(String abilitazioneRichiesta, String autore, String titolo, String descrizione,
      String indirizzo) {
    super();
    this.abilitazioneRichiesta = abilitazioneRichiesta;
    this.autore = autore;
    this.titolo = titolo;
    this.descrizione = descrizione;
    this.indirizzo = indirizzo;
    this.dataPubblicazione = LocalDate.now();
    this.incaricato = "nessuno"; //default unassigned ad
    this.dataFine = dataPubblicazione.plusDays(30); //default expire date
    this.recensione = -1.0; //default not reviewed
  }

  /**
   * Id setter.
   *
   * @param id the annuncioId
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Id getter.
   *
   * @return the annuncioId
   */
  public Long getId() {
    return id;
  }

  /**
   * DataPubblicazione setter.
   *
   * @param dataPubblicazione the dataPubblicazione to set
   */
  public void setDataPubblicazione(LocalDate dataPubblicazione) {
    this.dataPubblicazione = dataPubblicazione;
  }

  /**
   * Qualification getter.
   *
   * @return the qualification needed
   */
  public String getAbilitazioneRichiesta() {
    return abilitazioneRichiesta;
  }

  /**
   * Qualification setter.
   *
   * @param abilitazioneRichiesta is the qualification to set
   */
  public void setAbilitazioneRichiesta(String abilitazioneRichiesta) {
    this.abilitazioneRichiesta = abilitazioneRichiesta;
  }

  /**
   * Author's email getter.
   *
   * @return author's email
   */
  public String getAutore() {
    return autore;
  }

  /**
   * Author's email setter.
   *
   * @param email is the author email to set
   */
  public void setAutore(String email) {
    this.autore = email;
  }

  /**
   * Title getter.
   *
   * @return Annuncio title
   */
  public String getTitolo() {
    return titolo;
  }

  /**
   * Title setter.
   *
   * @param titolo is the title to set
   */
  public void setTitolo(String titolo) {
    this.titolo = titolo;
  }

  /**
   * Description getter.
   *
   * @return Annuncio description
   */
  public String getDescrizione() {
    return descrizione;
  }

  /**
   * Description setter.
   *
   * @param descrizione is the description to set
   */
  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  /**
   * Address getter.
   *
   * @return Annuncio useful address
   */
  public String getIndirizzo() {
    return indirizzo;
  }

  /**
   * Address setter.
   *
   * @param indirizzo is the addresss to set
   */
  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }

  /**
   * DataPubblicazione getter.
   *
   * @return the publication date
   */
  public LocalDate getDataPubblicazione() {
    return dataPubblicazione;
  }

  /**
   * User email getter.
   *
   * @return the email of the User who accepted the job
   */
  public String getIncaricato() {
    return incaricato;
  }

  /**
   * User email setter.
   *
   * @param email is the appointee User email to set
   */
  public void setIncaricato(String email) {
    this.incaricato = email;
  }

  /**
   * Expire date getter.
   *
   * @return the date on which the ad expire OR has been completed
   */
  public LocalDate getDataFine() {
    return dataFine;
  }

  /**
   * Expire date setter.
   *
   * @param dataFine is the date of removal from the available ads list to set
   */
  public void setDataFine(LocalDate dataFine) {
    this.dataFine = dataFine;
  }

  /**
   * Review rating getter.
   * 
   * @return the review rating assigned
   */
  public Double getRecensione() {
	  return recensione;
  }

  /**
   * Review rating setter.
   * 
   * @param recensione is the review rating to set
   */
  public void setRecensione(Double recensione) {
	  this.recensione = recensione;
  }

@Override
  public String toString() {
    return "Annuncio [id=" + id + ", abilitazioneRichiesta=" + abilitazioneRichiesta
        + ", autore=" + autore + ", titolo=" + titolo + ", descrizione=" + descrizione
        + ", indirizzo=" + indirizzo + ", dataPubblicazione=" + dataPubblicazione + ", incaricato="
        + incaricato + ", dataFine=" + dataFine + ", recensione=" + recensione +"]";
  }

}
