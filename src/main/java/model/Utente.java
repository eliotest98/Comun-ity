package model;

import java.time.LocalDate;

public class Utente {
	
	/**
	 * Attributes.
	 */
	private long id;
	private String ruolo;
	private String nome;
	private String cognome;
	private Integer eta;
	private String mail;
	private String password;
	private String sesso;
	private String numeroTelefono;
	private String indirizzo;
	private LocalDate dataNascita;
	
	
	/**
	 * Empty constructor.
	 */
	public Utente() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param id represents User identifier
	 * @param ruolo represents User role {cittadino, professionista, admin}
	 * @param nome represents User first name
	 * @param cognome represents User last name
	 * @param eta represents User age
	 * @param mail represents User email address
	 * @param password represents User account password
	 * @param sesso represents User BIOLOGICAL sex 
	 * @param numeroTelefono represents User phone number
	 * @param indirizzo represents User home address
	 * @param dataNascita represents User birth date
	 */
	public Utente(long id, String ruolo, String nome, String cognome, Integer eta, String mail, String password, String sesso,
			String numeroTelefono, String indirizzo, LocalDate dataNascita) {
		super();
		this.id = id;
		this.ruolo = ruolo;
		this.nome = nome;
		this.cognome = cognome;
		this.eta = eta;
		this.mail = mail;
		this.password = password;
		this.sesso = sesso;
		this.numeroTelefono = numeroTelefono;
		this.indirizzo = indirizzo;
		this.dataNascita = dataNascita;
	}


	/**
	 * @return User identfier
	 */
	public long getId() {
		return id;
	}


	/**
	 * @param id is the User identifier to set
	 */
	public void setId(long id) {
		this.id = id;
	}


	/**
	 * @return User role
	 */
	public String getRuolo() {
		return ruolo;
	}


	/**
	 * @param ruolo is the User role to set
	 */
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}


	/**
	 * @return User first name
	 */
	public String getNome() {
		return nome;
	}


	/**
	 * @param nome is the User first name to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}


	/**
	 * @return User last name
	 */
	public String getCognome() {
		return cognome;
	}


	/**
	 * @param cognome is the User last name to set
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	/**
	 * @return User age
	 */
	public Integer getEta() {
		return eta;
	}

	/**
	 * @param eta is the User age to set
	 */
	public void setEta(Integer eta) {
		this.eta = eta;
	}

	/**
	 * @return User email
	 */
	public String getMail() {
		return mail;
	}


	/**
	 * @param mail is the User email address to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}


	/**
	 * @return User account password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * @param password is the User account password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @return User biological sex
	 */
	public String getSesso() {
		return sesso;
	}


	/**
	 * @param sesso is the User biological sex to set
	 */
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}


	/**
	 * @return User phone number
	 */
	public String getNumeroTelefono() {
		return numeroTelefono;
	}


	/**
	 * @param numeroTelefono is the User phone number to set
	 */
	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}


	/**
	 * @return User home address
	 */
	public String getIndirizzo() {
		return indirizzo;
	}


	/**
	 * @param indirizzo is the User home address to set
	 */
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}


	/**
	 * @return User birth date
	 */
	public LocalDate getDataNascita() {
		return dataNascita;
	}


	/**
	 * @param dataNascita is the User birth date to set
	 */
	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}


	@Override
	public String toString() {
		return "Utente [id=" + id + ", ruolo=" + ruolo + ", nome=" + nome + ", cognome=" + cognome + ", mail=" + mail
				+ ", password=" + password + ", sesso=" + sesso + ", numeroTelefono=" + numeroTelefono + ", indirizzo="
				+ indirizzo + ", dataNascita=" + dataNascita + "]";
	}
	

}
