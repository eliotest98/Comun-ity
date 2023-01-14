package model;

import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import controller.utility.DbConnection;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Class AnnuncioDAO for database queries.
 */
public class AnnuncioDao {

  //Static db connection
  static MongoDatabase database = DbConnection.connectToDb();


  /**
   * Inserts an ad into the database.
   *
   * @param annuncio is the ad Object to store.
   * @return true if the ad has been saved correctly.
   */
  public boolean saveAnnuncio(Annuncio annuncio) {

    try {
      if (database.getCollection("annuncio").countDocuments() == 0) {
        //se non ci sono annunci l'id sar� 1
        annuncio.setId((long) 1);
      } else {
        annuncio.setId(getLastId() + 1);
      }
      database.getCollection("annuncio").insertOne(docForDb(annuncio));
      System.out.println("Annuncio aggiunto al database con successo");
      return true;

    } catch (MongoException e) {
      System.out.println("Errore durante l'inserimento dell'annuncio" + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Removes an ad from the database.
   *
   * @param id is the ad identifier.
   * @return true if the ad has been eliminated correctly.
   */
  public boolean deleteAnnuncio(Long id) {

    try {
      database.getCollection("annuncio").deleteOne(Filters.eq("id", id));
      System.out.println("Annuncio eliminato!");
      return true;
    } catch (MongoException e) {
      System.out.println("Errore durante l'eliminazione dell'annuncio" + e.getMessage());
      return false;
    }
  }

  /**
   * Update ad datas in the db.
   *
   * @param annuncio is the Ad Object already stored in the db to update.
   * @return true if the ad datas have been updated correctly.
   */
  public boolean updateAnnuncio(Annuncio annuncio) {

    try {
      database.getCollection("annuncio").replaceOne(Filters.eq("id", annuncio.getId()), docForDb(
          annuncio));
      System.out.println("Dati dell'annuncio " + annuncio.getId() + " aggiornati.");
      return true;
    } catch (MongoException e) {
      System.out.println("Errore durante l'update dell'annuncio" + e.getMessage());
      return false;
    }
  }

  /**
   * Get an ad with a specific id.
   *
   * @param id is the Ad id that you want to get.
   * @return The Ad if it exists.
   */
  public Annuncio findAnnuncioById(Long id) {

    Document doc = database.getCollection("annuncio").find(Filters.eq("id", id)).first();

    if (doc == null) {
      System.out.println("Annuncio non trovato!");
      return null;
    } else {
      System.out.println("Annuncio trovato!");
      return docToAnnuncio(doc);
    }
  }

  /**
   * Get the last ad id in the database.
   *
   * @return The last ad id.
   */
  public Long getLastId() {

    Document myDoc = database.getCollection("annuncio").find().sort(new Document("id",
        -1)).first();

    assert myDoc != null;
    Annuncio lastAnnuncio = docToAnnuncio(myDoc);

    return lastAnnuncio.getId();
  }

  /**
   * Retrieve all the Jobs from the db.
   *
   * @return a List of Annuncio that contains all the jobs.
   */
  public List<Annuncio> findJobs() {

    List<Annuncio> jobs = new ArrayList<>();
    List<Document> documents = new ArrayList<>();

    database.getCollection("annuncio").find(Filters.ne("abilitazioneRichiesta", "nessuna")).into(
        documents);
    if (!documents.isEmpty()) {
      jobs = documents.stream().map(AnnuncioDao::docToAnnuncio).collect(Collectors.toList());
    }

    return jobs;
  }

  /**
   * Retrieves all the Errands from the db.
   *
   * @return a List of Annuncio that contains all the errands.
   */
  public List<Annuncio> findErrands() {

    List<Annuncio> errands = new ArrayList<>();
    List<Document> documents = new ArrayList<>();

    database.getCollection("annuncio").find(Filters.eq("abilitazioneRichiesta", "nessuna")).into(
        documents);
    if (!documents.isEmpty()) {
      errands = documents.stream().map(AnnuncioDao::docToAnnuncio).collect(Collectors.toList());
    }

    return errands;
  }

  /**
   * Retrieve all the available Jobs from the db.
   *
   * @return a List of Annuncio that contains all the available jobs.
   */
  public List<Annuncio> findAvailableJobs() {

    List<Annuncio> jobs = findJobs();
    List<Annuncio> availables = new ArrayList<>();

    if (!jobs.isEmpty()) {
      availables = jobs.stream().filter(job -> job.getIncaricato().equals("nessuno")).collect(
          Collectors.toList());
    }

    String s = availables.isEmpty() 
        ?
        "Nessun lavoro disponibile trovato." :
        "Trovato/i lavoro/i disponibile/i.";
    System.out.println(s);

    return availables;

  }

  /**
   * Retrieves all the available Errands from the db.
   *
   * @return a List of Annuncio that contains all the available errands.
   */
  public List<Annuncio> findAvailableErrands() {

    List<Annuncio> errands = findErrands();
    List<Annuncio> availables = new ArrayList<>();

    if (!errands.isEmpty()) {
      availables = errands.stream().filter(errand -> errand.getIncaricato().equals("nessuno"))
          .collect(Collectors.toList());
    }

    String s = availables.isEmpty() 
        ?
        "Nessuna commissione disponibile trovata." :
        "Trovata/e commissione/i disponibile/i.";
    System.out.println(s);

    return availables;
  }

  /**
   * Retrieve all the Ads published from the given author from the db.
   *
   * @param autore is the email of the ad's author.
   * @return a List of Annuncio that contains all the ads published from the given author.
   */
  public List<Annuncio> findAllByAuthor(String autore) {

    List<Annuncio> allByAuthor = new ArrayList<>();
    List<Document> documents = new ArrayList<>();

    database.getCollection("annuncio").find(Filters.eq("autore", autore)).into(documents);
    if (!documents.isEmpty()) {
      allByAuthor = documents.stream().map(AnnuncioDao::docToAnnuncio).collect(Collectors.toList());
    }

    return allByAuthor;
  }

  /**
   * Retrieve all the available Ads published from the given author from the db.
   *
   * @param autore is the email of the ad's author.
   * @return a List of Annuncio that contains all the available ads published from the given author.
   */
  public List<Annuncio> findAllAvailableByAuthor(String autore) {

    List<Annuncio> allByAuthor = findAllByAuthor(autore);
    List<Annuncio> availables = new ArrayList<>();

    if (!allByAuthor.isEmpty()) {
      availables = allByAuthor.stream().filter(ad -> ad.getIncaricato().equals("nessuno")).collect(
          Collectors.toList());
    }

    String s = availables.isEmpty() 
        ?
        "Nessun annuncio disponibile trovato" :
        "Trovato/i annuncio/i disponibile/i";
    System.out.println(s + "per l'utente: " + autore + ".");

    return availables;
  }

  /**
   * Retrieve all the Ads accepted from the given appointee from the db.
   *
   * @param incaricato is the email of the ad's appointee.
   * @return a List of Annuncio that contains all the ads accepted from the given appointee.
   */
  public List<Annuncio> findAllByAppointee(String incaricato) {

    List<Annuncio> allByAppointee = new ArrayList<>();
    List<Document> documents = new ArrayList<>();

    database.getCollection("annuncio").find(Filters.eq("incaricato", incaricato)).into(documents);
    if (!documents.isEmpty()) {
      allByAppointee = documents.stream().map(AnnuncioDao::docToAnnuncio).collect(Collectors
          .toList());
    }

    return allByAppointee;
  }

  /**
   * Mark an ad as done.
   *
   * @param id is ad's id.
   * @return true if the ad is marked as done.
   */
  public boolean markAsDone(Long id) {
    boolean res = true;

    Annuncio annuncio = findAnnuncioById(id);

    if (annuncio != null) {
      annuncio.setTerminato(true);
      updateAnnuncio(annuncio);
    } else {
      res = false;
    }

    return res;
  }


  /**
   * Retrieve all the Ads, not marked as done, accepted from the given appointee from the db.
   *
   * @param incaricato is the email of the ad's appointee.
   * @return a List of Annuncio that contains all the ads, not marked as done, accepted from the
   *         given appointee.
   */
  public List<Annuncio> findAllByAppointeeNotDone(String incaricato) {

    List<Annuncio> allByAppointee = findAllByAppointee(incaricato);
    List<Annuncio> allByAppointeeNotDone = new ArrayList<>();

    for (Annuncio a : allByAppointee) {
      if (!a.isTerminato()) {
        allByAppointeeNotDone.add(a);
      }
    }

    if (allByAppointeeNotDone.isEmpty()) {
      System.out.println("Non ci sono annunci relativi all'utente non svolti");
    }

    return allByAppointeeNotDone;

  }

  /**
   * Converts an Annuncio Object into a Document for MongoDB methods usage.
   *
   * @param annuncio is the ad Object to convert.
   * @return the newly created Document.
   */
  private static Document docForDb(Annuncio annuncio) {

    //Check if it already exists
    Document doc = database.getCollection("annuncio").find(Filters.eq("id", annuncio.getId()))
        .first();

    //If it doesn't, create a new document
    if (doc == null) {
      doc = new Document("_id", new ObjectId()).append("id", annuncio.getId()).append(
          "abilitazioneRichiesta", annuncio.getAbilitazioneRichiesta()).append("autore", annuncio
              .getAutore()).append("titolo", annuncio.getTitolo()).append("descrizione", annuncio
                  .getDescrizione()).append("indirizzo", annuncio.getIndirizzo()).append(
                      "dataPubblicazione", annuncio.getDataPubblicazione()).append("incaricato",
                          annuncio.getIncaricato()).append("dataFine", annuncio.getDataFine())
          .append("recensione", annuncio.getRecensione()).append("terminato", annuncio
              .isTerminato());
    } else {
      doc.replace("id", annuncio.getId());
      doc.replace("abilitazioneRichiesta", annuncio.getAbilitazioneRichiesta());
      doc.replace("autore", annuncio.getAutore());
      doc.replace("titolo", annuncio.getTitolo());
      doc.replace("descrizione", annuncio.getDescrizione());
      doc.replace("indirizzo", annuncio.getIndirizzo());
      doc.replace("dataPubblicazione", annuncio.getDataPubblicazione());
      doc.replace("incaricato", annuncio.getIncaricato());
      doc.replace("dataFine", annuncio.getDataFine());
      doc.replace("recensione", annuncio.getRecensione());
      doc.replace("terminato", annuncio.isTerminato());
    }

    return doc;
  }

  /**
   * Converts a Document into an Annuncio Object.
   *
   * @param doc is the Document Object to convert.
   * @return the newly created Annuncio.
   */
  private static Annuncio docToAnnuncio(Document doc) {

    Annuncio annuncio = new Annuncio(doc.getString("abilitazioneRichiesta"), doc.getString(
        "autore"), doc.getString("titolo"), doc.getString("descrizione"), doc.getString(
            "indirizzo"));

    annuncio.setId(doc.getLong("id"));
    annuncio.setDataPubblicazione(doc.getDate("dataPubblicazione").toInstant().atZone(
        ZoneId.systemDefault()).toLocalDate());
    annuncio.setIncaricato(doc.getString("incaricato"));
    annuncio.setDataFine(doc.getDate("dataFine").toInstant().atZone(ZoneId
        .systemDefault()).toLocalDate());
    annuncio.setRecensione(doc.getDouble("recensione"));
    annuncio.setTerminato(doc.getBoolean("terminato"));

    return annuncio;
  }

}
