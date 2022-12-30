package model;

import java.time.LocalDate;
import java.time.ZoneId;
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
			
		database.getCollection("accreditamento").deleteOne(Filters.eq("richiedente" , accreditamento.getRichiedente()));
		System.out.println("Accreditamento eliminato!");
		}catch(MongoException e) {
			System.out.println("Errore durante l'eliminazione dell'utente" + e.getMessage());
		}
	}
	
	public Document findAccreditamento(Accreditamento accreditamento) {
		
		Document doc=null;
		
		try {
			
			doc= database.getCollection("accreditamento").find(Filters.eq("richiedente" , accreditamento.getRichiedente())).first();
		}catch(MongoException e) {
			System.out.println("Errore durante la ricerca dell'utente" + e.getMessage());
		}
		
		return doc;
		
	}
	
	//Crea un documento per mongoDB
		public static Document docForDb(Accreditamento accreditamento) {
			
			Document doc= new Document("_id", new ObjectId())
					.append("richiedente", accreditamento.getRichiedente())
					.append("abilitazione", accreditamento.getAbilitazione())
					.append("allegato", accreditamento.getAllegato())
					.append("dataSottomissione", accreditamento.getDataSottomissione())
					.append("dataVisione", accreditamento.getDataVisione())
					.append("stato", accreditamento.getStato());
			
			return doc;
			
		}
		
		public static Accreditamento docToAccreditamento(Document doc) {
			Accreditamento accreditamento = new Accreditamento(
					doc.getString("richiedente"),
					doc.getString("abilitazione"),
					doc.getString("allegato"),
					(LocalDate)doc.getDate("dataSottomissione").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), 
				    (LocalDate)doc.getDate("dataVisione").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), doc.getString("stato"));
			return accreditamento;
		}
}