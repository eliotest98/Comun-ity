package model;

import javax.management.Query;

import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.client.model.Filters;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import controller.utility.*;

public class AccreditamentoDAO {
	
	//Connessione al database
	MongoDatabase database=  DbConnection.connectToDb();
	
	
	//Inserisce un utente nel database
	public void createAccreditamento(Accreditamento accreditamento) {
		
		try {
		database.getCollection("accreditamento").insertOne(docForDb(accreditamento));
		System.out.println("Accreditamento aggiunto al database con successo");
		
		}catch(MongoException e) {
			System.out.println("Errore durante l'inserimento dell'accreditamento" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void deleteAccreditamento(Accreditamento accreditamento) {
		try {
			
		database.getCollection("accreditamento").deleteOne(Filters.eq("accreditamentoId", accreditamento.getAccreditamentoId()));
		System.out.println("Accreditamento eliminato!");
		}catch(MongoException e) {
			System.out.println("Errore durante l'eliminazione dell'utente" + e.getMessage());
		}
	}
	
	public Document findAccreditamento(Accreditamento accreditamento) {
		
		Document doc=null;
		
		try {
			
			doc= database.getCollection("accreditamento").find(Filters.eq("accreditamentoId" , accreditamento.getAccreditamentoId())).first();
		}catch(MongoException e) {
			System.out.println("Errore durante la ricerca dell'utente" + e.getMessage());
		}
		
		return doc;
		
	}
	
	//Crea un documento per mongoDB
		public static Document docForDb(Accreditamento accreditamento) {
			
			Document doc= new Document("_id", new ObjectId())
					.append("accreditamentoId", accreditamento.getAccreditamentoId())
					.append("richiedente", accreditamento.getRichiedente())
					.append("abilitazione", accreditamento.getAbilitazione())
					.append("allegato", accreditamento.getAllegato())
					.append("dataSottomissione", accreditamento.getDataSottomissione())
					.append("dataVisione", accreditamento.getDataVisione())
					.append("stato", accreditamento.getStato());
			
			return doc;
			
		}
}