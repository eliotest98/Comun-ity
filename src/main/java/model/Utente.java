package model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Accreditamento bean.
 */
public class Utente {

  /**
   * Attributes.
   */
  private String ruolo;
  private String abilitazione;
  private String nome;
  private String cognome;
  private Integer eta;
  private String mail;
  private String password;
  private String sesso;
  private String numeroTelefono;
  private String indirizzo;
  private LocalDate dataNascita;
  private ArrayList<Double> recensioni;
  private Boolean blacklist;
  private String durataBl;


  /**
   * Empty constructor.
   */
  public Utente() {
    super();
  }

  /**
   * Constructor.
   *
   * @param ruolo          represents User role {cittadino, professionista, admin}
   * @param abilitazione   represents User qualification {"nessuna" = cittadino}
   * @param nome           represents User first name
   * @param cognome        represents User last name
   * @param eta            represents User age
   * @param mail           represents User email address
   * @param password       represents User account password
   * @param sesso          represents User BIOLOGICAL sex
   * @param numeroTelefono represents User phone number
   * @param indirizzo      represents User home address
   * @param dataNascita    represents User birthdate
   */

  public Utente(String ruolo, String abilitazione, String nome, String cognome, Integer eta,
      String mail, String password, String sesso, String numeroTelefono, String indirizzo,
      LocalDate dataNascita) {
    super();
    this.ruolo = ruolo;
    this.abilitazione = abilitazione;
    this.nome = nome;
    this.cognome = cognome;
    this.eta = eta;
    this.mail = mail;
    this.password = password;
    this.sesso = sesso;
    this.numeroTelefono = numeroTelefono;
    this.indirizzo = indirizzo;
    this.dataNascita = dataNascita;
    this.recensioni = new ArrayList<>();
    this.blacklist = false;
    this.durataBl = "permanente"; //default value
  }

  /**
   * User role getter.
   *
   * @return User role
   */
  public String getRuolo() {
    return ruolo;
  }


  /**
   * User role setter.
   *
   * @param ruolo is the User role to set
   */
  public void setRuolo(String ruolo) {
    this.ruolo = ruolo;
  }

  /**
   * User qualification getter.
   *
   * @return the User qualification
   */
  public String getAbilitazione() {
    return abilitazione;
  }

  /**
   * User qualification setter.
   *
   * @param abilitazione ise the User qualification to set
   */
  public void setAbilitazione(String abilitazione) {
    this.abilitazione = abilitazione;
  }

  /**
   * Name getter.
   *
   * @return User first name
   */
  public String getNome() {
    return nome;
  }


  /**
   * Name setter.
   *
   * @param nome is the User first name to set
   */
  public void setNome(String nome) {
    this.nome = nome;
  }


  /**
   * Surname getter.
   *
   * @return User last name
   */
  public String getCognome() {
    return cognome;
  }


  /**
   * Surname setter.
   *
   * @param cognome is the User last name to set
   */
  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  /**
   * Age getter.
   *
   * @return User age
   */
  public Integer getEta() {
    return eta;
  }

  /**
   * Age setter.
   *
   * @param eta is the User age to set
   */
  public void setEta(Integer eta) {
    this.eta = eta;
  }

  /**
   * Email getter.
   *
   * @return User email
   */
  public String getMail() {
    return mail;
  }


  /**
   * Email setter.
   *
   * @param mail is the User email address to set
   */
  public void setMail(String mail) {
    this.mail = mail;
  }


  /**
   * Password getter.
   *
   * @return User account password
   */
  public String getPassword() {
    return password;
  }


  /**
   * Password setter.
   *
   * @param password is the User account password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }


  /**
   * Sex getter.
   *
   * @return User biological sex
   */
  public String getSesso() {
    return sesso;
  }


  /**
   * Sex setter.
   *
   * @param sesso is the User biological sex to set
   */
  public void setSesso(String sesso) {
    this.sesso = sesso;
  }


  /**
   * Phone number getter.
   *
   * @return User phone number
   */
  public String getNumeroTelefono() {
    return numeroTelefono;
  }


  /**
   * Phone number setter.
   *
   * @param numeroTelefono is the User phone number to set
   */
  public void setNumeroTelefono(String numeroTelefono) {
    this.numeroTelefono = numeroTelefono;
  }


  /**
   * Addres getter.
   *
   * @return User home address
   */
  public String getIndirizzo() {
    return indirizzo;
  }


  /**
   * Address setter.
   *
   * @param indirizzo is the User home address to set
   */
  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }


  /**
   * Birthdate getter.
   *
   * @return User birthdate
   */
  public LocalDate getDataNascita() {
    return dataNascita;
  }


  /**
   * Birthdate setter.
   *
   * @param dataNascita is the User birthdate to set
   */
  public void setDataNascita(LocalDate dataNascita) {
    this.dataNascita = dataNascita;
  }

  /**
   * Reviews getter.
   *
   * @return list of reviews
   */
  public ArrayList<Double> getRecensioni() {
    return recensioni;
  }

  /**
   * Reviews setter.
   *
   * @param recensioni is the review list to set
   */
  public void setRecensioni(ArrayList<Double> recensioni) {
    this.recensioni = recensioni;
  }

  /**
   * Review adder.
   *
   * @param recensione is the review to add to the list of reviews
   */
  public void addRecensione(double recensione) {
    recensioni.add(recensione);
  }

  /**
   * Reputation getter.
   *
   * @return reputation
   */
  public double getReputazione() {

    if (recensioni.isEmpty()) {
      return 0.0;
    }

    double sum = 0.0;
    for (double recensione : recensioni) {
      sum += recensione;
    }

    return sum / recensioni.size();
  }



  /**
   * Blacklist getter.
   *
   * @return true if the user is blacklisted.
   */
  public Boolean getBlacklist() {
    return blacklist;
  }

  /**
   * Blacklist setter.
   *
   * @param blacklist is the Boolean value to set whether the user is blacklisted.
   */
  public void setBlacklist(Boolean blacklist) {
    this.blacklist = blacklist;
  }

  /**
   * Blacklist duration getter.
   *
   * @return a string that tells the duration of the blacklist {default: "permanente"}
   */
  public String getDurataBl() {
    return durataBl;
  }

  /**
   * Blacklist permanation setter.
   *
   * @param durataBl is the duration of the blacklist for the user.
   */
  public void setDurataBl(String durataBl) {
    this.durataBl = durataBl;
  }

  @Override
  public String toString() {
    return "Utente [ruolo=" + ruolo + ", abilitazione=" + abilitazione
        + ", nome=" + nome + ", cognome=" + cognome
        + ", eta=" + eta + ", mail=" + mail
        + ", password=" + password + ", sesso="
        + sesso + ", numeroTelefono=" + numeroTelefono
        + ", indirizzo=" + indirizzo + ", dataNascita="
        + dataNascita + ", reputazione=" + getReputazione()
        + ", blacklist=" + blacklist + ", durataBL=" + durataBl + "]";
  }

}
