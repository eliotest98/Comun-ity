package controller.utility;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DbConnection {
	
	
	public static MongoDatabase connectToDb() {

		
		String uri = "mongodb+srv://alessandrofalcone:alessandrofalcone@cluster0.mongodb.net/test?retryWrites=true&w=majority";
		MongoClient mongoClient = MongoClients.create(uri);
		MongoDatabase database = mongoClient.getDatabase("GPS");
		
		return database;
		
	}

}
