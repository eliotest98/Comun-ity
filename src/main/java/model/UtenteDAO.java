package model;

import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.client.model.Filters;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import controller.utility.*;

public class UtenteDAO {
	
	//Connessione al database
	MongoDatabase connection=  DbConnection.connectToDb();
	
	
	//Inserisce un utente nel database
	public void createUtente(Utente utente) {
		
		try {
		connection.getCollection("utente").insertOne(docForDb(utente));
		System.out.println("Utente agiunto al database con successo");
		
		}catch(MongoException e) {
			System.out.println("Errore durante l'inserimento dell'utente" + e.getMessage());
		}
	}
	
	
	//Esegue l'eliminazione di un utente nel database
	public void deleteUtente(Utente utente) {
		connection.getCollection("utente").deleteOne(Filters.eq("_id", utente.getId()));
	}
	
	//Esegue l'aggionramento di un utente nel database
	public void updateUtente(Utente utente) {
		connection.getCollection("utente").updateOne(Filters.eq("_id", utente.getId()), docForDb(utente));
	}
	
	
	//Trova un utente specifico nel database
	public Document findUtente(ObjectId id) {
		
		Document doc=null;
		
		try {
			
			doc= connection.getCollection("utente").find(Filters.eq("_id" , id)).first();
		}catch(MongoException e) {
			System.out.println("Errore durante la ricerca dell'utente" + e.getMessage());
		}
		
		return doc;
		
	}
	
	//Crea un documento per mongoDB
	public static Document docForDb(Utente utente) {
		
		Document doc= new Document("id" , utente.getId())
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
