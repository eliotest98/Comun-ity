package model;

import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.client.model.Filters;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import controller.utility.*;

public class UtenteDAO {
	

	//Connessione al database
	MongoDatabase database=  DbConnection.connectToDb();
	
	
	//Inserisce un utente nel database
	public void createUtente(Utente utente) {
		
		try {
		database.getCollection("utente").insertOne(docForDb(utente));
		System.out.println("Utente aggiunto al database con successo");
		
		}catch(MongoException e) {
			System.out.println("Errore durante l'inserimento dell'utente" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	//Esegue l'eliminazione di un utente nel database
	public void deleteUtente(String mail) {
		try {
			
		database.getCollection("utente").deleteOne(Filters.eq("mail", mail));
		System.out.println("Utente eliminato!");
		}catch(MongoException e) {
			System.out.println("Errore durante l'eliminazione dell'utente" + e.getMessage());
		}
	}
	
	
	//Trova un utente specifico nel database per id
		public Document findUtenteByMail(String mail) {
			
			Document doc=null;
			
			try {
				
				doc= database.getCollection("utente").find(Filters.eq("mail" , mail)).first();
			}catch(MongoException e) {
				System.out.println("Errore durante la ricerca dell'utente" + e.getMessage());
			}
			
			return doc;
			
		}
	
	
	//Crea un documento per mongoDB
	public static Document docForDb(Utente utente) {
		
		Document doc= new Document("_id", new ObjectId())
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
