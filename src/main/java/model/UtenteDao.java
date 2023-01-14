package model;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import controller.utility.DbConnection;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Class UtenteDAO for database queries.
 */
public class UtenteDao {

  //static db connection.
  static MongoDatabase database = DbConnection.connectToDb();


  /**
   * Stores a new user into the database.
   *
   * @param utente is the user Object to store.
   */
  public void saveUtente(Utente utente) {

    try {
      database.getCollection("utente").insertOne(docForDb(utente));
      System.out.println("Utente aggiunto al database con successo.");
    } catch (MongoWriteException e) {
      System.out.println("Errore durante l'inserimento dell'utente\n" + e.getMessage());
      e.printStackTrace();
    }
  }


  /**
   * Delete the user with the given email from the db.
   *
   * @param mail is the email of the user to remove.
   * @return true if the user entry has been deleted correctly.
   */
  public boolean deleteUtente(String mail) {

    try {
      database.getCollection("utente").deleteOne(Filters.eq("mail", mail));

      System.out.println("Utente eliminato!");
      return true;

    } catch (MongoException e) {
      System.out.println("Errore durante l'eliminazione dell'utente" + e.getMessage());
      return false;
    }
  }

  /**
   * Updates user datas in the db.
   *
   * @param utente is the user Object already stored in the db to update.
   * @return true if the user datas have been updated correctly.
   */
  public boolean updateUtente(Utente utente) {
    try {
      database.getCollection("utente")
          .replaceOne(Filters.eq("mail", utente.getMail()), docForDb(utente));
      System.out.println("Dati dell'utente " + utente.getMail() + " aggiornati.");
      return true;
    } catch (MongoException e) {
      System.out.println("Errore durante l'update dell'utente" + e.getMessage());
      return false;
    }
  }

  /**
   * Find the account given its mail.
   *
   * @param mail refering to the account to search for.
   * @return the user Object if it exists.
   */
  public Utente findUtenteByMail(String mail) {

    Document doc = database.getCollection("utente").find(Filters.eq("mail", mail)).first();

    if (doc == null) {
      System.out.println("Utente non trovato!");
      return null;
    } else {
      System.out.println("Utente trovato!");
      return docToUtente(doc);
    }
  }

  /**
   * Retrieve all the Users from the db.
   *
   * @return a List of Utente that contains all the users.
   */
  public List<Utente> findAllUsers() {

    List<Utente> users = new ArrayList<>();
    List<Document> documents = new ArrayList<>();

    database.getCollection("utente").find().into(documents);
    if (!documents.isEmpty()) {
      users = documents.stream().map(UtenteDao::docToUtente).collect(Collectors.toList());
    }

    return users;
  }

  /**
   * Retrieve all the Admins from the db.
   *
   * @return a List of Utente that contains all the users who are admin.
   */
  public List<Utente> findAllAdmins() {

    List<Utente> admins = new ArrayList<>();
    List<Document> documents = new ArrayList<>();

    database.getCollection("utente").find(Filters.eq("ruolo", "admin")).into(documents);
    if (!documents.isEmpty()) {
      admins = documents.stream().map(UtenteDao::docToUtente).collect(Collectors.toList());
    }

    return admins;
  }

  /**
   * Retrieve all the blacklisted users from the db.
   *
   * @return a List of Utente that contains all the users who are blacklisted.
   */
  public List<Utente> findAllBlacklistedUsers() {

    List<Utente> users = findAllUsers();
    List<Utente> blacklisted = new ArrayList<>();

    if (!users.isEmpty()) {
      blacklisted =
          users.stream().filter(Utente::getBlacklist).collect(Collectors.toList());
    }

    String s = blacklisted.isEmpty() ? "Blacklist vuota" : "Trovato/i utente/i blacklistato/i";
    System.out.println(s);

    return blacklisted;
  }

  /**
   * Assign a rating to a user.
   *
   * @param utente     is the user to assign the rating to.
   * @param recensione is the rating to assign.
   */
  public boolean assignRating(Utente utente, Double recensione) {

    Utente utente1 = findUtenteByMail(utente.getMail());

    if (utente1 == null) {
      System.out.println("L'utente a cui si vuole assegnare una valutazione non esiste.");
      return false;
    } else {
      utente1.getRecensioni().add(recensione);
      List<Double> recensioni = utente1.getRecensioni();

      database.getCollection("utente")
          .updateOne(Filters.eq("mail", utente.getMail()), Updates.set("recensioni", recensioni));

      System.out.println("valutazione assegnata");
      return true;
    }

  }

  /**
   * Implements a research filter based on the email. As the Admin types in, the filter updates the
   * users' list each time a charachter is typed.
   *
   * @param mail is the typed part of the email to base the research on.
   * @return List of Utente that contains users with the matching email characters.
   */
  public List<Utente> searchUser(String mail) {

    List<Utente> lista = new ArrayList<>();

    MongoCollection<Document> collection = database.getCollection("utente");

    BasicDBObject regexQuery = new BasicDBObject();
    regexQuery.put("mail", new BasicDBObject("$regex", mail).append("$options", "i"));

    for (Document document : collection.find(regexQuery)) {
      lista.add(UtenteDao.docToUtente(document));
    }
    return lista;
  }

  /**
   * Converts an Utente Object into a Document for MongoDB methods usage.
   *
   * @param utente is the utente Object to convert.
   * @return the newly created Document.
   */
  private static Document docForDb(Utente utente) {

    //Check if it already exists
    Document doc =
        database.getCollection("utente").find(Filters.eq("mail", utente.getMail())).first();

    //If it doesn't, create a new document
    if (doc == null) {

      doc = new Document("_id", new ObjectId()).append("ruolo", utente.getRuolo())
          .append("abilitazione", utente.getAbilitazione()).append("nome", utente.getNome())
          .append("cognome", utente.getCognome()).append("eta", utente.getEta())
          .append("mail", utente.getMail()).append("password", utente.getPassword())
          .append("sesso", utente.getSesso()).append("numeroTelefono", utente.getNumeroTelefono())
          .append("indirizzo", utente.getIndirizzo()).append("dataNascita", utente.getDataNascita())
          .append("recensioni", utente.getRecensioni()).append("blacklist", utente.getBlacklist())
          .append("durataBL", utente.getDurataBl());

    } else {
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
      doc.replace("durataBL", utente.getDurataBl());

    }

    return doc;
  }

  /**
   * Converts a Document into an Utente Object.
   *
   * @param doc is the Document Object to convert.
   * @return the newly created Utente.
   */
  private static Utente docToUtente(Document doc) {

    Utente utente =
        new Utente(doc.getString("ruolo"),
            doc.getString("abilitazione"),
            doc.getString("nome"),
            doc.getString("cognome"),
            doc.getInteger("eta"),
            doc.getString("mail"),
            doc.getString("password"),
            doc.getString("sesso"),
            doc.getString("numeroTelefono"),
            doc.getString("indirizzo"),
            doc.getDate("dataNascita")
              .toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

    utente.setRecensioni((ArrayList<Double>) doc.getList("recensioni", Double.class));
    utente.setBlacklist(doc.getBoolean("blacklist"));
    utente.setDurataBl(doc.getString("durataBL"));

    return utente;
  }
}
