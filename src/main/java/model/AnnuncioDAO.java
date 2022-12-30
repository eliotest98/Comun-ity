package model;

import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import controller.utility.DbConnection;

public class AnnuncioDAO {
	
	//Connessione al database
		MongoDatabase database=  DbConnection.connectToDb();
		
		
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
		public Document findAnnuncioById(Long id) {
			
			Document doc=null;
			
			try {
				
				doc= database.getCollection("utente").find(Filters.eq("id", id)).first();
			}catch(MongoException e) {
				System.out.println("Errore durante la ricerca dell'annuncio" + e.getMessage());
			}
			
			return doc;
			
		}
	
	//Crea un documento per mongoDB
		public static Document docForDb(Annuncio annuncio) {
			
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

}
