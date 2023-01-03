package model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	MongoDatabase database = DbConnection.connectToDb();


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

	//Esegue l'eliminazione di un annuncio nel database
	public void deleteAnnuncio(Long id) {
		try {

			database.getCollection("annuncio").deleteOne(Filters.eq("id", id));
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
		
		if(doc == null)
			return null;
		else {
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

		List<Annuncio> lavori = new ArrayList<Annuncio>();
		
		try {

			MongoCursor<Document> cursor = database.getCollection("annuncio").find(Filters.ne("abilitazioneRichiesta", "nessuna")).iterator(); 
			
			if(!cursor.hasNext()) {
				return null;
			}
			
			while (cursor.hasNext()) {
				lavori.add(docToAnnuncio(cursor.next()));
			}


		}catch(MongoException e) {
			System.out.println("Errore durante la ricerca dei lavori disponibili");
		}

		return lavori;
	}

	//Restituisce una lista con tutte le commissioni
	public List<Annuncio> findErrands(){

		List<Annuncio> commissioni = new ArrayList<Annuncio>();

		try {
			MongoCursor<Document> cursor = database.getCollection("annuncio").find(Filters.eq("abilitazioneRichiesta", "nessuna")).iterator(); 
			if(!cursor.hasNext()) {
				return null;
			}
			while (cursor.hasNext()) {
				commissioni.add(docToAnnuncio(cursor.next()));
			}


		}catch(MongoException e) {
			System.out.println("Errore durante la ricerca delle commissioni disponibili");
		}

		return commissioni;
	}

	//Restituisce una lista di tutti i lavori disponibili
	public List<Annuncio> findAvailableJobs(){

		List<Annuncio> lavori;
		List<Annuncio> lavoriDisponibili = new ArrayList<Annuncio>();

		lavori= findJobs();
		
		if(lavori == null)
			return null;
		
		Iterator<Annuncio> it= lavori.iterator();
		while(it.hasNext()) {
			Annuncio annuncio=it.next();
			if(annuncio.getIncaricato().equals("nessuno")) {
				lavoriDisponibili.add(annuncio);
			}
		}

		if(lavoriDisponibili.isEmpty()) {
			System.out.println("Non ci sono lavori disponibili");
		}

		return lavoriDisponibili;

	}

	//Restituisce una lista di tutte le commissioni disponibili
	public List<Annuncio> findAvailableErrands(){

		List<Annuncio> commissioni;
		List<Annuncio> commissioniDisponibili = new ArrayList<Annuncio>();
		
		commissioni=this.findErrands();
		
		if(commissioni == null)
			return null;
		
		Iterator<Annuncio> it= commissioni.iterator();
		Annuncio commissione;
		while(it.hasNext()) {
			commissione=it.next();
			if(commissione.getIncaricato().equals("nessuno")) {
				commissioniDisponibili.add(commissione);
			}
		}

		if(commissioniDisponibili.isEmpty()) {
			return null;
		}

		return commissioniDisponibili;

	}
	
	//Restituisce una lista di tutti gli annunci pubblicati da un utente
	public List<Annuncio> findAllByUser(String autore){
		
		List<Annuncio> allAdsByUser = new ArrayList<Annuncio>();

		try {

			MongoCursor<Document> cursor = database.getCollection("annuncio").find(Filters.eq("autore", autore)).iterator(); 
			if(!cursor.hasNext()) {
				return null;
			}
			while (cursor.hasNext()) {
				allAdsByUser.add(docToAnnuncio(cursor.next()));
			}


		}catch(MongoException e) {
			System.out.println("Errore durante la ricerca degli annunci pubblicati da: " + autore + ".");
		}

		return allAdsByUser;
	}

	//Restituisce una lista di annunci ancora disponibili per autore
	public List<Annuncio> findAllAvailableByUser(String autore){
		
		List<Annuncio> allAdsByUser = new ArrayList<Annuncio>();
		List<Annuncio> availables = new ArrayList<Annuncio>();
		
		allAdsByUser = findAllByUser(autore);

		Iterator<Annuncio> it = allAdsByUser.iterator();
		while(it.hasNext()) {
			if(it.next().getIncaricato().equals("nessuno")) {
				availables.add(it.next());
			}
		}

		if(availables.isEmpty()) {
			System.out.println("Non ci sono annunci disponibili pubblicati dall'utente: " + autore + ".");
		}

		return availables;
		
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
