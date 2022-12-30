package model;

import java.time.LocalDate;
import java.time.ZoneId;

import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.client.model.Filters;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import controller.utility.*;

public class UtenteDAO {
	

	//Connessione al database
	MongoDatabase database = DbConnection.connectToDb();
	
	
	//Inserisce un utente nel database
	public void saveUtente(Utente utente) {
		
		try {
		database.getCollection("utente").insertOne(docForDb(utente));
		System.out.println("Utente aggiunto al database con successo");
		
		}catch(MongoException e) {
			System.out.println("Errore durante l'inserimento dell'utente" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	//Esegue l'eliminazione di un utente nel database
	public void deleteUtente(Utente utente) {
		try {
			

		database.getCollection("utente").deleteOne(Filters.eq("mail", utente.getMail()));

		System.out.println("Utente eliminato!");
		}catch(MongoException e) {
			System.out.println("Errore durante l'eliminazione dell'utente" + e.getMessage());
		}
	}
	
	
	//Trova un utente specifico nel database per id
	public Utente findUtenteByMail(String mail) {
		
		Document doc = null;
		
		try {
			doc= database.getCollection("utente").find(Filters.eq("mail", mail)).first();
		}catch(MongoException e) {
			System.out.println("Errore durante la ricerca dell'utente" + e.getMessage());
		}
		
		Utente utente = docToUtente(doc);
		return utente;
	}
			
	
	//Crea un documento per mongoDB
	private static Document docForDb(Utente utente) {
		
		Document doc= new Document("_id", new ObjectId())
									.append("ruolo", utente.getRuolo())
									.append("abilitazione", utente.getAbilitazione())
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
  
	//Crea un istanza di Utentre da un documento mongoDB
	private static Utente docToUtente(Document doc) {
		
		Utente utente = new Utente(
				doc.getString("ruolo"),
				doc.getString("abilitazione"),
				doc.getString("nome"),
				doc.getString("cognome"),
				doc.getInteger("eta"),
				doc.getString("mail"),
				doc.getString("password"),
				doc.getString("sesso"),
				doc.getString("numeroTelefono"),
				doc.getString("indirizzo"),
				(LocalDate)doc.getDate("dataNascita").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		
		return utente;
	}

}
