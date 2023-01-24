package controller.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 * Singleton class for Database connection.
 */
public class DbConnection {


  /**
   * Method for Database connection.
   *
   * @return database connection
   */
  public static MongoDatabase connectToDb() {

    try {
      String uri = "mongodb+srv://andreaaceto:andreaaceto@gps.om8mqqx.mongodb.net/test";
      MongoClient mongoClient = MongoClients.create(uri);
      MongoDatabase database = mongoClient.getDatabase("Comunity");
      System.out.println("Connessione riuscita");
      return database;
    } catch (Exception e) {
      System.out.println("Connessione fallita");
      e.printStackTrace();
    }

    return null;
  }

}

