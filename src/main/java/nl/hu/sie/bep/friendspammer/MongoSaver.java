package nl.hu.sie.bep.friendspammer;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoSaver {

  public static boolean saveEmail(String to, String from, String subject, String text, Boolean html) {

    MongoClientURI uri = new MongoClientURI("mongodb+srv://ijans:Jean1ne!@cluster0-of0yn.azure.mongodb.net/test?retryWrites=true");

    boolean success = true;

    try (MongoClient mongoClient = new MongoClient(uri)) {

      MongoDatabase database = mongoClient.getDatabase("friendspammer");

      MongoCollection<Document> c = database.getCollection("email");

      Document  doc = new Document ("to", to)
        .append("from", from)
        .append("subject", subject)
        .append("text", text)
        .append("asHtml", html);
      c.insertOne(doc);
    } catch (MongoException mongoException) {
      System.out.println("XXXXXXXXXXXXXXXXXX ERROR WHILE SAVING TO MONGO XXXXXXXXXXXXXXXXXXXXXXXXXX");
      mongoException.printStackTrace();
      success = false;
    }

    return success;

  }

}
