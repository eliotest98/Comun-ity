package model;

import javax.swing.text.Document;

public class Accreditamento {
	
	Utente cittadino;
	Document allegato;
	boolean accettata;
	
	public Utente getCittadino() {
		return cittadino;
	}
	public void setCittadino(Utente cittadino) {
		this.cittadino = cittadino;
	}
	public Document getAllegato() {
		return allegato;
	}
	public void setAllegato(Document allegato) {
		this.allegato = allegato;
	}
	public boolean isAccettata() {
		return accettata;
	}
	public void setAccettata(boolean accettata) {
		this.accettata = accettata;
	}
	
	@Override
	public String toString() {
		return "Accreditamento [cittadino=" + cittadino + ", accettata=" + accettata + "]";
	}
	
	

}
