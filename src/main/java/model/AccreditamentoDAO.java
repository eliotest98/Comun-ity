package model;

import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import controller.utility.DbConnection;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Class AccreditamentoDAO for database queries.
 */
public class AccreditamentoDAO {

  //static db connection.
  static MongoDatabase database = DbConnection.connectToDb();


  /**
   * Stores a new accreditation request into the database.
   *
   * @param accreditamento is the accreditation Object to store.
   * @return true if the accreditation datas have been saved correctly.
   */
  public boolean saveAccreditamento(Accreditamento accreditamento) {

    try {
      database.getCollection("accreditamento").insertOne(docForDb(accreditamento));
      System.out.println("Accreditamento aggiunto al database con successo");
      return true;
    } catch (MongoException e) {
      System.out.println("Errore durante l'inserimento dell'accreditamento" + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Delete the accreditation request submitted from the given applicant, from the db.
   *
   * @param richiedente is the email of the applicant.
   * @return true if the accreditation entry has been deleted correctly.
   */
  public boolean deleteAccreditamento(String richiedente) {

    try {
      database.getCollection("accreditamento").deleteOne(Filters.eq("richiedente", richiedente));
      System.out.println("Accreditamento eliminato!");
      return true;
    } catch (MongoException e) {
      System.out.println("Errore durante l'eliminazione dell'utente" + e.getMessage());
      return false;
    }
  }

  /**
   * Updates a new accreditation request into the database.
   *
   * @param accreditamento is the accreditation Object already stored in the db to update.
   * @return true if the accreditation datas have been updated correctly.
   */
  public boolean updateAccreditamento(Accreditamento accreditamento) {
    try {
      database.getCollection("accreditamento")
          .replaceOne(Filters.eq("richiedente", accreditamento.getRichiedente()),
              docForDb(accreditamento));
      System.out.println(
          "Dati dell'accreditamento dell'utente "
              + accreditamento.getRichiedente()
              + " aggiornati.");
      return true;
    } catch (MongoException e) {
      System.out.println("Errore durante l'update dell'utente" + e.getMessage());
      return false;
    }
  }

  /**
   * Find the accreditation request given its applicant email.
   *
   * @param email is the applicant email refering to the accreditation request to search for.
   * @return the accreditation Object if it exists.
   */
  public Accreditamento findAccreditamentoBySubmitter(String email) {

    Document doc =
        database.getCollection("accreditamento").find(Filters.eq("richiedente", email)).first();

    if (doc == null) {
      System.out.println("Richiesta di accreditamento non trovata!");
      return null;
    } else {
      System.out.println("Richiesta di accreditamento trovata!");
      return docToAccreditamento(doc);
    }

  }
  
  /**
   * Retrieves all the submitted and yet to examine accreditation requests.
   * 
   * @return a List of Accreditamento that contains all the accreditation requests
   * pending to be accepted or declined. 
   */
  public List<Accreditamento> findAllUnexamined() {
	
	List<Accreditamento> submitted = new ArrayList<>();
	List<Document> documents = new ArrayList<>();

	database.getCollection("accreditamento").find(Filters.eq("stato", "sottomessa")).into(documents);
	if (!documents.isEmpty()) {
		submitted = documents.stream().map(AccreditamentoDAO::docToAccreditamento).collect(Collectors.toList());
	}

	return submitted;
  }

  /**
   * Converts an Accreditamento Object into a Document for MongoDB methods usage.
   *
   * @param accreditamento is the accreditation Object to convert.
   * @return the newly created Document.
   */
  private static Document docForDb(Accreditamento accreditamento) {

    //Check if it already exists
    Document doc = database.getCollection("accreditamento")
        .find(Filters.eq("richiedente", accreditamento.getRichiedente())).first();

    //If it doesn't, create a new document
    if (doc == null) {

      doc =
          new Document("_id", new ObjectId()).append("richiedente", accreditamento.getRichiedente())
              .append("abilitazione", accreditamento.getAbilitazione())
              .append("allegato", accreditamento.getAllegato())
              .append("dataSottomissione", accreditamento.getDataSottomissione())
              .append("dataVisione", accreditamento.getDataVisione())
              .append("stato", accreditamento.getStato());

    } else {
      doc.replace("richiedente", accreditamento.getRichiedente());
      doc.replace("abilitazione", accreditamento.getAbilitazione());
      doc.replace("allegato", accreditamento.getAllegato());
      doc.replace("dataSottomissione", accreditamento.getDataSottomissione());
      doc.replace("dataVisione", accreditamento.getDataVisione());
      doc.replace("stato", accreditamento.getStato());

    }

    return doc;

  }

  /**
   * Converts a Document into an Accreditamento Object.
   *
   * @param doc is the Document Object to convert.
   * @return the newly created Accreditamento.
   */
  private static Accreditamento docToAccreditamento(Document doc) {

    return new Accreditamento(doc.getString("richiedente"), doc.getString("abilitazione"),
        doc.getString("allegato"),
        (LocalDate) doc.getDate("dataSottomissione").toInstant().atZone(ZoneId.systemDefault())
            .toLocalDate(),
            doc.getDate("dataVisione") != null ? (LocalDate) doc.getDate("dataVisione").toInstant().atZone(ZoneId.systemDefault())
            .toLocalDate() : null
            , doc.getString("stato"));
  }
}
