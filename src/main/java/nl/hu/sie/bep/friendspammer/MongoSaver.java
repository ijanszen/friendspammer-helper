package nl.hu.sie.bep.friendspammer;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoSaver {


  private MongoSaver(){  }

  public static List<Email> getAllPreviouslySendEmails(){
    final Logger logger = LoggerFactory.getLogger(MongoSaver.class);
    List<Email> sendEmailList = new ArrayList<>();
    MongoConnection conn = MongoConnection.getInstance();
    conn.init();
    MongoCollection<Document> c = conn.getEmailCollection();

    Iterator<Document> it = c.find().iterator();
    
    while(it.hasNext())
    {
      Document e = it.next();
      logger.info("Document in de while loop {}", e);


      String to = (String) e.get("to");
      String from = (String)e.get("from");
      String subject = (String)e.get("subject");
      String text = (String) e.get("text");
      Boolean ashtml = (Boolean) e.get("asHtml");

      Email email = new Email(to, from, subject, text, ashtml);
      logger.info("Email obj in de while loop {}", email);
      sendEmailList.add(email);
      logger.info("sendEmailList in de while loop {}", sendEmailList);
    }
    conn.close();
    return sendEmailList;
  }

  static void saveEmail(String to, String from, String subject, String text, Boolean html) {
    final Logger logger = LoggerFactory.getLogger(MongoSaver.class);
    logger.info("about to make an instance");
    MongoConnection conn = MongoConnection.getInstance();
    logger.info("conn.init coming up");
      conn.init();
      MongoCollection<Document> c = conn.getEmailCollection();
      Document  doc = new Document ("to", to)
        .append("from", from)
        .append("subject", subject)
        .append("text", text)
        .append("asHtml", html);
      c.insertOne(doc);
      conn.close();
  }
}
