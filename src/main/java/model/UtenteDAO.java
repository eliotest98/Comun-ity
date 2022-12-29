package model;

import javax.management.Query;

import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.client.model.Filters;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import controller.utility.*;

public class UtenteDAO {
	
	//Connessione al database
	MongoDatabase database=  DbConnection.connectToDb();
	
	
	//Inserisce un utente nel database
	public void createUtente(Utente utente) {
		
		try {
		database.getCollection("utente").insertOne(docForDb(utente));
		System.out.println("Utente agiunto al database con successo");
		
		}catch(MongoException e) {
			System.out.println("Errore durante l'inserimento dell'utente" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	//Esegue l'eliminazione di un utente nel database
	public void deleteUtente(Utente utente) {
		try {
			
		database.getCollection("utente").deleteOne(Filters.eq("utenteId", utente.getUtenteId()));
		System.out.println("Utente eliminato!");
		}catch(MongoException e) {
			System.out.println("Errore durante l'eliminazione dell'utente" + e.getMessage());
		}
	}
	
	/*
	
	//Esegue l'aggionramento di un utente nel database
	public void updateUtente(Utente utenteNuovo , Utente utenteVecchio) {
		try {
		
			Document documento= findUtente(utenteVecchio.getUtenteId());
			
			Update update
			
		}catch(MongoException e) {
			System.out.println("Errore durante l'aggiornamento dell'utente");
		}
	}
	
	*/
	
	//Trova un utente specifico nel database
	public Document findUtente(Integer id) {
		
		Document doc=null;
		
		try {
			
			doc= database.getCollection("utente").find(Filters.eq("utenteId" , id)).first();
		}catch(MongoException e) {
			System.out.println("Errore durante la ricerca dell'utente" + e.getMessage());
		}
		
		return doc;
		
	}
	
	//Crea un documento per mongoDB
	public static Document docForDb(Utente utente) {
		
		Document doc= new Document("_id", new ObjectId())
									.append("utenteId", utente.getUtenteId())
									.append("ruolo", utente.getRuolo())
									.append("nome", utente.getNome())
									.append("cognome", utente.getCognome())
									.append("eta", utente.getEta())
									.append("mail", utente.getMail())
									.append("password", utente.getPassword())
									.append("sesso", utente.getSesso())
									.append("numeroTelefono", utente.getNumeroTelefono())
									.append("indirizzo", utente.getIndirizzo())
									.append("dataNascita", utente.getDataNascita())
									.append("recensioni", utente.getRecensioni());
		
		return doc;
		
	}

}
