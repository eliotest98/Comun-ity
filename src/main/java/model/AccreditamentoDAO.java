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
	static MongoDatabase database = DbConnection.connectToDb();


	//Inserisce un utente nel database
	public boolean saveAccreditamento(Accreditamento accreditamento) {

		try {
			database.getCollection("accreditamento").insertOne(docForDb(accreditamento));
			System.out.println("Accreditamento aggiunto al database con successo");
			return true;

		}catch(MongoException e) {
			System.out.println("Errore durante l'inserimento dell'accreditamento" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteAccreditamento(String richiedente) {
		try {
			database.getCollection("accreditamento").deleteOne(Filters.eq("richiedente" , richiedente));
			System.out.println("Accreditamento eliminato!");
			return true;
		}catch(MongoException e) {
			System.out.println("Errore durante l'eliminazione dell'utente" + e.getMessage());
			return false;
		}
	}

	public boolean updateAccreditamento(Accreditamento accreditamento) {
		try {
			database.getCollection("accreditamento").replaceOne(Filters.eq("richiedente", accreditamento.getRichiedente()), docForDb(accreditamento));
			System.out.println("Dati dell'accreditamento dell'utente " + accreditamento.getRichiedente() + " aggiornati.");
			return true;
		}catch(MongoException e) {
			System.out.println("Errore durante l'update dell'utente" + e.getMessage());
			return false;
		}
	}

	public Accreditamento findAccreditamentoBySubmitter(String email) {

		Document doc = database.getCollection("accreditamento").find(Filters.eq("richiedente" , email)).first();;

		if(doc == null) {
			System.out.println("Richiesta di accreditamento non trovata!");
			return null;
		}else {
			System.out.println("Richiesta di accreditamento trovata!");
			Accreditamento accr= docToAccreditamento(doc);
			return accr;
		}

	}

	//Crea un documento per mongoDB
	private static Document docForDb(Accreditamento accreditamento) {

		//Check if it already exists
		Document doc = database.getCollection("accreditamento").find(Filters.eq("richiedente", accreditamento.getRichiedente())).first();

		//If it doesn't, create a new document
		if(doc == null) {

			doc= new Document("_id", new ObjectId())
					.append("richiedente", accreditamento.getRichiedente())
					.append("abilitazione", accreditamento.getAbilitazione())
					.append("allegato", accreditamento.getAllegato())
					.append("dataSottomissione", accreditamento.getDataSottomissione())
					.append("dataVisione", accreditamento.getDataVisione())
					.append("stato", accreditamento.getStato());

		}else {
			doc.replace("richiedente", accreditamento.getRichiedente());
			doc.replace("abilitazione", accreditamento.getAbilitazione());
			doc.replace("allegato", accreditamento.getAllegato());
			doc.replace("dataSottomissione", accreditamento.getDataSottomissione());
			doc.replace("dataVisione", accreditamento.getDataVisione());
			doc.replace("stato", accreditamento.getStato());

		}

		return doc;

	}

	//Crea un istanza di Accreditamento da un documento mongoDB
	private static Accreditamento docToAccreditamento(Document doc) {

		Accreditamento accreditamento = new Accreditamento(
				doc.getString("richiedente"),
				doc.getString("abilitazione"),
				doc.getString("allegato"),
				(LocalDate)doc.getDate("dataSottomissione").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), 
				(LocalDate)doc.getDate("dataVisione").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
				doc.getString("stato"));
		return accreditamento;
	}
}