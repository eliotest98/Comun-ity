package model;

import java.time.LocalDate;

public class Utente {
	
	private long id;
	private String nome;
	private String cognome;
	private String mail;
	private String password;
	private String sesso;
	private String numeroTelefono;
	private String indirizzo;
	private LocalDate dataNascita;
	private String ruolo;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getNumeroTelefono() {
		return numeroTelefono;
	}
	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public LocalDate getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	
	public Utente(long id, String nome, String cognome, String mail, String password, String sesso,
			String numeroTelefono, String indirizzo, LocalDate dataNascita, String ruolo) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.mail = mail;
		this.password = password;
		this.sesso = sesso;
		this.numeroTelefono = numeroTelefono;
		this.indirizzo = indirizzo;
		this.dataNascita = dataNascita;
		this.ruolo = ruolo;
	}
	
	@Override
	public String toString() {
		return "Utente [nome=" + nome + ", cognome=" + cognome + ", mail=" + mail + ", password=" + password
				+ ", sesso=" + sesso + ", numeroTelefono=" + numeroTelefono + ", indirizzo=" + indirizzo
				+ ", dataNascita=" + dataNascita + ", ruolo=" + ruolo + "]";
	}
	
	
	

}
