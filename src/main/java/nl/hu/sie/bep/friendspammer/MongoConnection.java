package nl.hu.sie.bep.friendspammer;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoConnection {

  private MongoConnection() {
    throw new IllegalStateException("Utility class");
  }

  public static MongoCollection<Document> getCollection() {
    final Logger logger = LoggerFactory.getLogger(MongoSaver.class);
    MongoClientURI uri = new MongoClientURI("mongodb+srv://ijans:Jean1ne!@cluster0-of0yn.azure.mongodb.net/test?retryWrites=true");
    MongoCollection<Document> collection = null;

    try (MongoClient mongoClient = new MongoClient(uri)) {
      MongoDatabase db = mongoClient.getDatabase("friendspammer");
      collection = db.getCollection("email");

    } catch (MongoException mongoException) {
      logger.debug("XXXXXXXXXXXXXXXXXX ERROR WHILE SAVING TO MONGO XXXXXXXXXXXXXXXXXXXXXXXXXX");
      logger.error(mongoException.getMessage());
    }
    return collection;
  }
}
