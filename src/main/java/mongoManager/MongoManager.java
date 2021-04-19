package mongoManager;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.LineModel;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoManager {

    private static MongoClient client;
    private static MongoDatabase db;
    private static MongoCollection<Document> collection;

    public static void createConnection() {
        client = MongoClients.create();
    }

    public static void setDB() {
        db = client.getDatabase("test");
    }

    public static void setCollection() {
        collection = db.getCollection("testCollection");
    }


    public static void clearCollection() {
        collection.deleteMany(new BasicDBObject());
    }

    public static void saveLines(List<LineModel> lines) {
        List<Document> docs = new ArrayList<>();
        Gson gson = new Gson();
        for (LineModel line : lines) {
            docs.add(Document.parse(gson.toJson(line)));
        }
        collection.insertMany(docs);
    }

    public static void closeConnection() {
        client.close();
    }
}
