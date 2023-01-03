package model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import controller.utility.*;

public class UtenteDAO {


	//Connessione al database
	static MongoDatabase database = DbConnection.connectToDb();


	//Inserisce un utente nel database
	public void saveUtente(Utente utente) {

		try {
			database.getCollection("utente").insertOne(docForDb(utente));
			System.out.println("Utente aggiunto al database con successo.");
		}catch(MongoWriteException e) {
			System.out.println("Errore durante l'inserimento dell'utente\n" + e.getMessage());
			e.printStackTrace();
		}
	}


	//Esegue l'eliminazione di un utente nel database
	public boolean deleteUtente(String mail) {

		try {
			database.getCollection("utente").deleteOne(Filters.eq("mail", mail));

			System.out.println("Utente eliminato!");
			return true;

		}catch(MongoException e) {
			System.out.println("Errore durante l'eliminazione dell'utente" + e.getMessage());
			return false;
		}
	}

	//Esegue l'update dei dati di un utente nel database
	public boolean updateUtente(Utente utente) {
		try {
			database.getCollection("utente").replaceOne(Filters.eq("mail", utente.getMail()), docForDb(utente));
			System.out.println("Dati dell'utente " + utente.getMail() + " aggiornati.");
			return true;
		}catch(MongoException e) {
			System.out.println("Errore durante l'update dell'utente" + e.getMessage());
			return false;
		}
	}
	
	//Trova un utente specifico nel database per id
	public Utente findUtenteByMail(String mail) {

		Document doc = database.getCollection("utente").find(Filters.eq("mail", mail)).first();

		if(doc == null) {
			System.out.println("Utente non trovato!");
			return null;
		}else {
			System.out.println("Utente trovato!");
			Utente user = docToUtente(doc);
			return user;
		}
	}

	//Restitituisce una lista di admin
	public List<Utente> getAllAdmins(){

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


	//Restituisce tutti gli utenti del sistema
	public List<Utente> getAllUsers(){

		List<Utente> lista = new ArrayList<>();

		try {
			MongoCollection<Document> collection = database.getCollection("utente");

			MongoCursor<Document> cursor = collection.find().iterator();
			if(!cursor.hasNext()) {
				return null;
			}
			while (cursor.hasNext()) {
				lista.add(UtenteDAO.docToUtente(cursor.next()));
			}

		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lista;

	}
	
	public List<Utente> getAllBlacklistedUsers(){
		
		List<Utente> list = new ArrayList<>();
		List<Document> documents = new ArrayList<>();
		
		database.getCollection("utente").find(Filters.eq("blacklist", true)).into(documents);
		for(Document d : documents) {
			list.add(docToUtente(d));
		}
		
		return list;
		
	}

	//Assegna una valutazione ad un utente
	public void assignRating(Utente utente, Double recensione) {
		
		Utente utente1 = findUtenteByMail(utente.getMail());
		
		if(utente1==null) {
			System.out.println("L'utente a cui si vuole assegnare una valutazione non esiste");
		}else {
			utente1.getRecensioni().add(recensione);
			List<Double> recensioni= utente1.getRecensioni();

			database.getCollection("utente").updateOne(Filters.eq("mail", utente.getMail()), Updates.set("recensioni", recensioni));

			System.out.println("valutazione assegnata");
		}

	}



	//LASCIARE ALLA FINE
	//Crea un documento per mongoDB
	private static Document docForDb(Utente utente) {
		
		//Check if it already exists
		Document doc = database.getCollection("utente").find(Filters.eq("mail", utente.getMail())).first();
		
		//If it doesn't, create a new document
		if(doc == null) {
			
			doc= new Document("_id", new ObjectId())
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
			.append("recensioni", utente.getRecensioni())
			.append("blacklist", utente.getBlacklist())
			.append("durataBL", utente.getDurataBL());
		
		}else {
			doc.replace("ruolo", utente.getRuolo());
			doc.replace("abilitazione", utente.getAbilitazione());
			doc.replace("nome", utente.getNome());
			doc.replace("cognome", utente.getCognome());
			doc.replace("eta", utente.getEta());
			doc.replace("mail", utente.getMail());
			doc.replace("password", utente.getPassword());
			doc.replace("sesso", utente.getSesso());
			doc.replace("numeroTelefono", utente.getNumeroTelefono());
			doc.replace("indirizzo", utente.getIndirizzo());
			doc.replace("dataNascita", utente.getDataNascita());
			doc.replace("recensioni", utente.getRecensioni());
			doc.replace("blacklist", utente.getBlacklist());
			doc.replace("durataBL", utente.getDurataBL());
			
		}
		
		return doc;

	}
	
	public List<Utente> searchUtente(String mail){

			List<Utente> lista = new ArrayList<>();

			MongoCollection<Document> collection = database.getCollection("utente");

			BasicDBObject regexQuery = new BasicDBObject();
			regexQuery.put("mail", new BasicDBObject("$regex", mail).append("$options", "i"));

			MongoCursor<Document> cursor = collection.find(regexQuery).iterator();

			while (cursor.hasNext()) {
	        	lista.add(UtenteDAO.docToUtente(cursor.next()));
	        }
	        return lista;
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

		utente.setRecensioni((ArrayList<Double>) doc.getList("recensioni", Double.class));
		utente.setBlacklist(doc.getBoolean("blacklist"));
		utente.setDurataBL(doc.getString("durataBL"));

		return utente;
	}
}
