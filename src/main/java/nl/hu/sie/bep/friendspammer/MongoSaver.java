package nl.hu.sie.bep.friendspammer;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class MongoSaver {

  private MongoSaver() {
    throw new IllegalStateException("Utility class");
  }

  public List<Email> getAllPreviouslySendEmails(){
    List<Email> sendEmailList = new ArrayList<>();
    MongoCollection<Document> c = MongoConnection.getCollection();
    Iterator<Document> it = c.find().iterator();
    
    while(it.hasNext())
    {
      Document e = it.next();

      String to = (String) e.get("to");
      String from = (String)e.get("from");
      String subject = (String)e.get("subject");
      String text = (String) e.get("text");
      Boolean ashtml = (Boolean) e.get("asHtml");

      Email email = new Email(to, from, subject, text, ashtml);
      sendEmailList.add(email);
    }
    return sendEmailList;
  }

  static void saveEmail(String to, String from, String subject, String text, Boolean html) {
    MongoCollection<Document> c = MongoConnection.getCollection();
      Document  doc = new Document ("to", to)
        .append("from", from)
        .append("subject", subject)
        .append("text", text)
        .append("asHtml", html);
      c.insertOne(doc);
  }
}
