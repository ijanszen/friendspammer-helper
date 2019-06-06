package nl.hu.sie.bep.friendspammer;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoConnection {

  private MongoClient mongo = null;
  private static MongoConnection instance = new MongoConnection();
  final Logger logger = LoggerFactory.getLogger(MongoConnection.class);

  private MongoConnection() {}

  public MongoClient getMongo() {
    if (mongo == null) {
      logger.debug("Starting Mongo");
      MongoClientOptions.Builder options = MongoClientOptions.builder()
        .connectionsPerHost(4)
        .maxConnectionIdleTime((60 * 1_000))
        .maxConnectionLifeTime((120 * 1_000));

      MongoClientURI uri = new MongoClientURI("mongodb+srv://ijans:Jean1ne!@cluster0-of0yn.azure.mongodb.net/test?retryWrites=true", options);



      try {
        logger.info("About to connect to MongoDB @ {}", uri);
        mongo = new MongoClient(uri);
      } catch (MongoException ex ) {
        logger.error("An error occoured when connecting to MongoDB", ex);
      }
    }
    return mongo;
  }

  public MongoCollection<Document>  getEmailCollection() {
    MongoCollection<Document> collection = null;
    MongoDatabase db = mongo.getDatabase("friendspammer");
    collection = db.getCollection("email");
    return collection;
  }

  public void init() {
    logger.debug("init");
    getMongo();
  }

  public void close() {
    logger.info("Closing MongoDB connection");
    if (mongo != null) {
      try {
        mongo.close();
        logger.debug("Nulling the connection dependency objects");
        mongo = null;

      } catch (Exception e) {
        logger.error("An error occurred when closing the MongoDB connection\n%s, see {}", e.getMessage());
      }
    } else {
      logger.warn("mongo object was null, wouldn't close connection");
    }
  }

  public static MongoConnection getInstance() {
    return instance;
  }
}
