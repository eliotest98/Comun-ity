package model;

import java.time.LocalDate;
import java.time.ZoneId;

import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import controller.utility.DbConnection;

public class AnnuncioDAO {

	//Connessione al database
	MongoDatabase database = DbConnection.connectToDb();


	//Inserisce un annuncio nel database
	public void saveAnnuncio(Annuncio annuncio) {

		try {
			database.getCollection("annuncio").insertOne(docForDb(annuncio));
			System.out.println("Annuncio aggiunto al database con successo");

		}catch(MongoException e) {
			System.out.println("Errore durante l'inserimento dell'annuncio" + e.getMessage());
			e.printStackTrace();
		}
	}


	//Esegue l'eliminazione di un annuncio nel database
	public void deleteAnnuncio(Annuncio annuncio) {
		try {

			database.getCollection("annuncio").deleteOne(Filters.eq("id", annuncio.getId()));
			System.out.println("Annuncio eliminato!");
		}catch(MongoException e) {
			System.out.println("Errore durante l'eliminazione dell'annuncio" + e.getMessage());
		}
	}

	//Trova un annuncio specifico nel database
	public Annuncio findAnnuncioById(Long id) {

		Document doc=null;

		try {

			doc= database.getCollection("utente").find(Filters.eq("id", id)).first();
		}catch(MongoException e) {
			System.out.println("Errore durante la ricerca dell'annuncio" + e.getMessage());
		}

		Annuncio annuncio = docToAnnuncio(doc);
		return annuncio;

	}

	//Crea un documento per mongoDB
	private static Document docForDb(Annuncio annuncio) {

		Document doc= new Document("_id", new ObjectId())
				.append("id", annuncio.getId())
				.append("abilitazioneRichiesta", annuncio.getAbilitazioneRichiesta())
				.append("autore" , annuncio.getAutore())
				.append("titolo", annuncio.getTitolo())
				.append("descrizione" , annuncio.getDescrizione())
				.append("indirizzo", annuncio.getIndirizzo())
				.append("dataPubblicazione", annuncio.getDataPubblicazione())
				.append("incaricato", annuncio.getIncaricato())
				.append("dataFine", annuncio.getDataFine());

		return doc;

	}
	
	//Crea un istanza di Annuncio da un documento mongoDB
	private static Annuncio docToAnnuncio(Document doc) {

		Annuncio annuncio = new Annuncio(
				doc.getLong("id"),
				doc.getString("abilitazioneRichiesta"),
				doc.getString("autore"),
				doc.getString("titolo"),
				doc.getString("descrizione"),
				doc.getString("indirizzo"),
				(LocalDate)doc.getDate("dataPubblicazione").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
				doc.getString("incaricato"),
				(LocalDate)doc.getDate("dataFine").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				
		return annuncio;
	}

}
