package model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import controller.utility.DbConnection;

public class AnnuncioDAO {

	//Connessione al database
	static MongoDatabase database = DbConnection.connectToDb();


	//Inserisce un annuncio nel database
	public void saveAnnuncio(Annuncio annuncio) {

		try {
			annuncio.setId(getLastId()+1);
			database.getCollection("annuncio").insertOne(docForDb(annuncio));
			System.out.println("Annuncio aggiunto al database con successo");

		}catch(MongoException e) {
			System.out.println("Errore durante l'inserimento dell'annuncio" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
     * Removes an ad from the database.
     * @param id is the ad identifier
     * @return true if the ad has been eliminated correctly.
     */
	public boolean deleteAnnuncio(Long id) {
		
		try {
			database.getCollection("annuncio").deleteOne(Filters.eq("id", id));
			System.out.println("Annuncio eliminato!");
			return true;
		}catch(MongoException e) {
			System.out.println("Errore durante l'eliminazione dell'annuncio" + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Update ad datas in the db.
	 * @param annuncio is the Ad Object already stored in the db to update.
	 * @return true if the ad datas have been updated correctly.
	 */
	public boolean updateAnnuncio(Annuncio annuncio) {
		
		try {
			database.getCollection("annuncio").replaceOne(Filters.eq("id", annuncio.getId()), docForDb(annuncio));
			System.out.println("Dati dell'annuncio " + annuncio.getId() + " aggiornati.");
			return true;
		}catch(MongoException e) {
			System.out.println("Errore durante l'update dell'annuncio" + e.getMessage());
			return false;
		}
	}

	//Trova un annuncio specifico nel database
	public Annuncio findAnnuncioById(Long id) {

		Document doc = database.getCollection("annuncio").find(Filters.eq("id", id)).first();
		
		if(doc == null) {
			System.out.println("Annuncio non trovato!");
			return null;
		}else {
			System.out.println("Annuncio trovato!");
			Annuncio annuncio = docToAnnuncio(doc);
			return annuncio;
		}
	}

	//this method returns the id of the last annuncio
	public Long getLastId() {
		
		Document myDoc = (Document)database.getCollection("annuncio").find().sort(new Document("id",-1)).first();
		
		Annuncio lastAnnuncio=docToAnnuncio(myDoc);
		
		return lastAnnuncio.getId();
	}

	//Restituisce una lista con tutti i lavori 
	public List<Annuncio> findJobs(){

		List<Annuncio> jobs = new ArrayList<>();
		List<Document> documents = new ArrayList<>();
		
		database.getCollection("annuncio").find(Filters.ne("abilitazioneRichiesta", "nessuna")).into(documents);
		if(!documents.isEmpty()) {
			jobs = documents.stream().map(AnnuncioDAO::docToAnnuncio).collect(Collectors.toList());
		}
		
		return jobs;
	}

	//Restituisce una lista con tutte le commissioni
	public List<Annuncio> findErrands(){

		List<Annuncio> errands = new ArrayList<>();
		List<Document> documents = new ArrayList<>();
		
		database.getCollection("annuncio").find(Filters.eq("abilitazioneRichiesta","nessuna")).into(documents);
		if(!documents.isEmpty()) {
			errands = documents.stream().map(AnnuncioDAO::docToAnnuncio).collect(Collectors.toList());
		}
		
		return errands;
	}

	//Restituisce una lista di tutti i lavori disponibili
	public List<Annuncio> findAvailableJobs(){

		List<Annuncio> jobs = findJobs();
		List<Annuncio> availables = new ArrayList<Annuncio>();

		if(!jobs.isEmpty()) {
			availables = jobs.stream().filter(job -> job.getIncaricato().equals("nessuno")).collect(Collectors.toList());
		}
		
		String s = availables.isEmpty() ? "Nessun lavoro disponibile trovato." : "Trovato/i lavoro/i disponibile/i.";
		System.out.println(s);
		
		return availables;
		
	}

	//Restituisce una lista di tutte le commissioni disponibili
	public List<Annuncio> findAvailableErrands(){

		List<Annuncio> errands = findErrands();
		List<Annuncio> availables = new ArrayList<Annuncio>();

		if(!errands.isEmpty()) {
			availables = errands.stream().filter(errand -> errand.getIncaricato().equals("nessuno")).collect(Collectors.toList());
		}
		
		String s = availables.isEmpty() ? "Nessuna commissione disponibile trovata." : "Trovata/e commissione/i disponibile/i.";
		System.out.println(s);
		
		return availables;
	}
	
	//Restituisce una lista di tutti gli annunci pubblicati da un utente
	public List<Annuncio> findAllByAuthor(String autore){
		
		List<Annuncio> allByAuthor = new ArrayList<Annuncio>();
		List<Document> documents = new ArrayList<>();
		
		database.getCollection("annuncio").find(Filters.eq("autore", autore)).into(documents);
		if(!documents.isEmpty()) {
			allByAuthor = documents.stream().map(AnnuncioDAO::docToAnnuncio).collect(Collectors.toList());
		}
		
		return allByAuthor;
	}

	//Restituisce una lista di annunci ancora disponibili per autore
	public List<Annuncio> findAllAvailableByAuthor(String autore){
		
		List<Annuncio> allByAuthor = findAllByAuthor(autore);
		List<Annuncio> availables = new ArrayList<Annuncio>();

		if(!allByAuthor.isEmpty()) {
			availables = allByAuthor.stream().filter(ad -> ad.getIncaricato().equals("nessuno")).collect(Collectors.toList());
		}
		
		String s = availables.isEmpty() ? "Nessun annuncio disponibile trovato" : "Trovato/i annuncio/i disponibile/i";
		System.out.println(s + "per l'utente: " + autore + ".");
		
		return availables;
	}
	
	public List<Annuncio> findAllByAppointee(String incaricato){

		List<Annuncio> allByAppointee = new ArrayList<Annuncio>();
		List<Document> documents = new ArrayList<>();

		database.getCollection("annuncio").find(Filters.eq("incaricato", incaricato)).into(documents);
		if(!documents.isEmpty()) {
			allByAppointee = documents.stream().map(AnnuncioDAO::docToAnnuncio).collect(Collectors.toList());
		}

		return allByAppointee;
	}

	//Crea un documento per mongoDB
	private static Document docForDb(Annuncio annuncio) {
		
		//Check if it already exists
		Document doc = database.getCollection("annuncio").find(Filters.eq("id", annuncio.getId())).first();
		
		//If it doesn't, create a new document
		if(doc == null) {
			doc = new Document("_id", new ObjectId())
					.append("id", annuncio.getId())
					.append("abilitazioneRichiesta", annuncio.getAbilitazioneRichiesta())
					.append("autore" , annuncio.getAutore())
					.append("titolo", annuncio.getTitolo())
					.append("descrizione" , annuncio.getDescrizione())
					.append("indirizzo", annuncio.getIndirizzo())
					.append("dataPubblicazione", annuncio.getDataPubblicazione())
					.append("incaricato", annuncio.getIncaricato())
					.append("dataFine", annuncio.getDataFine());
		} else {
			doc.replace("id", annuncio.getId());
			doc.replace("abilitazioneRichiesta", annuncio.getAbilitazioneRichiesta());
			doc.replace("autore" , annuncio.getAutore());
			doc.replace("titolo", annuncio.getTitolo());
			doc.replace("descrizione" , annuncio.getDescrizione());
			doc.replace("indirizzo", annuncio.getIndirizzo());
			doc.replace("dataPubblicazione", annuncio.getDataPubblicazione());
			doc.replace("incaricato", annuncio.getIncaricato());
			doc.replace("dataFine", annuncio.getDataFine());
		}

		return doc;
	}
	
	

	//Crea un istanza di Annuncio da un documento mongoDB
	private static Annuncio docToAnnuncio(Document doc) {

		Annuncio annuncio = new Annuncio(
				doc.getString("abilitazioneRichiesta"),
				doc.getString("autore"),
				doc.getString("titolo"),
				doc.getString("descrizione"),
				doc.getString("indirizzo"));

		annuncio.setId(doc.getLong("id"));
		annuncio.setDataPubblicazione((LocalDate)doc.getDate("dataPubblicazione").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		annuncio.setIncaricato(doc.getString("incaricato"));
		annuncio.setDataFine((LocalDate)doc.getDate("dataFine").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		
		return annuncio;
	}

}
