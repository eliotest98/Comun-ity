package model;


import java.time.LocalDate;

/**
 * Accreditamento bean.
 */
public class Accreditamento {
  /**
   * Attributes.
   */
  private String richiedente;
  private String abilitazione;
  private String allegato;
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
   * Constructor for new issue.
   *
   * @param utente       is the email of the User who submitted the request
   * @param abilitazione is the qualification the User wants to demonstrate he has
   * @param allegato     is the documentation issued
   */
  public Accreditamento(String utente, String abilitazione, String allegato) {
    super();
    this.richiedente = utente;
    this.abilitazione = abilitazione;
    this.allegato = allegato;
    this.dataSottomissione = LocalDate.now();
    this.stato = "sottomessa";
  }

  /**
   * Constructor.
   *
   * @param utente            is the email of the User who submitted the request
   * @param abilitazione      is the qualification the User wants to demonstrate he has
   * @param allegato          is the documentation issued
   * @param dataSottomissione represents the date of submission
   * @param dataVisione       represents the date of examination
   * @param stato             represents the state of the request {SOTTOMESSA, RIFIUTATA,
   *                          ACCETTATA}
   */
  public Accreditamento(String utente, String abilitazione, String allegato,
      LocalDate dataSottomissione, LocalDate dataVisione, String stato) {
    super();
    this.richiedente = utente;
    this.abilitazione = abilitazione;
    this.allegato = allegato;
    this.dataSottomissione = dataSottomissione;
    this.dataVisione = dataVisione;
    this.stato = stato;
  }

  /**
   * Email getter.
   *
   * @return applicant email
   */
  public String getRichiedente() {
    return richiedente;
  }

  /**
   * Email setter.
   *
   * @param email is the applicant email to set
   */
  public void setRichiedente(String email) {
    this.richiedente = email;
  }

  /**
   * Qualification getter.
   *
   * @return the applicant qualification
   */
  public String getAbilitazione() {
    return abilitazione;
  }

  /**
   * Qualification setter.
   *
   * @param abilitazione is the qualification to set
   */
  public void setAbilitazione(String abilitazione) {
    this.abilitazione = abilitazione;
  }

  /**
   * Attachment getter.
   *
   * @return applicant documentation
   */
  public String getAllegato() {
    return allegato;
  }

  /**
   * Attachment setter.
   *
   * @param allegato is the document to set
   */
  public void setAllegato(String allegato) {
    this.allegato = allegato;
  }

  /**
   * Date getter.
   *
   * @return application date
   */
  public LocalDate getDataSottomissione() {
    return dataSottomissione;
  }

  /**
   * Date setter.
   *
   * @param dataSottomissione is the application date to set
   */
  public void setDataSottomissione(LocalDate dataSottomissione) {
    this.dataSottomissione = dataSottomissione;
  }

  /**
   * DataVisione getter.
   *
   * @return the examination date
   */
  public LocalDate getDataVisione() {
    return dataVisione;
  }

  /**
   * DataVisione setter.
   *
   * @param dataVisione is the examination date to set
   */
  public void setDataVisione(LocalDate dataVisione) {
    this.dataVisione = dataVisione;
  }

  /**
   * State getter.
   *
   * @return the request state
   */
  public String getStato() {
    return stato;
  }
  
  /**
   * State setter.
   *
   * @param stato is the state of the request to set
   */
  public void setStato(String stato) {
    this.stato = stato;
  }

  @Override
  public String toString() {
    return "Accreditamento [richiedente=" + richiedente + ", abilitazione=" + abilitazione
        + ", allegato=" + allegato + ", dataSottomissione=" + dataSottomissione
        + ", dataVisione=" + dataVisione + ", stato=" + stato + "]";
  }


}
