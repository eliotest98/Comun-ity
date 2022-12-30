package model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.client.model.Filters;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
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
		}catch(MongoWriteException e) {
			System.out.println("Errore durante l'inserimento dell'utente\n" + e.getMessage());
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
			doc = database.getCollection("utente").find(Filters.eq("mail", mail)).first();
		}catch(MongoException e) {
			System.out.println("Errore durante la ricerca dell'utente" + e.getMessage());
		}
		
		if(doc == null)
			return null;
		else {
			Utente utente = docToUtente(doc);
			return utente;
		}
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
	
	public List<Utente> allAdmins(){
				
		Document doc = null;
		
		List<Utente> lista = new ArrayList<>();
		
		try {
			MongoCollection<Document> collection = database.getCollection("utente");

			MongoCursor<Document> cursor = collection.find(Filters.eq("ruolo", "admin")).iterator();
	        while (cursor.hasNext()) {
	        	lista.add(UtenteDAO.docToUtente(cursor.next()));
	        }
			
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
				
	}

}
