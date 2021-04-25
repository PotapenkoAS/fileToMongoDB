import mapper.ToModelMapper;
import model.LineModel;
import mongoManager.MongoManager;
import mysqlManager.MySQLManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<LineModel> lineModelsIo = Collections.emptyList();
        try {
            lineModelsIo = parseFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        MongoManager.createConnection();
        MongoManager.setDB();
        MongoManager.setCollection();
        long beforeMongo = System.currentTimeMillis() / 1000L;
        MongoManager.saveLines(lineModelsIo);
        long afterMongo = System.currentTimeMillis() / 1000L;
        long mongoDif = afterMongo - beforeMongo;
        System.out.println("time of saving to mongo = " + (mongoDif) + " s ... or   " + mongoDif / 60F + " min");
        MongoManager.closeConnection();

        long beforeMysql = System.currentTimeMillis() / 1000L;
        MySQLManager.doGood(lineModelsIo);
        long afterMysql = System.currentTimeMillis() / 1000L;
        long mysqlDif = afterMysql - beforeMysql;
        System.out.println("time of saving to mySQL = " + (mysqlDif) + " s ... or   " + mysqlDif / 60F + " min");

    }

    private static List<LineModel> parseFile() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/test3.txt"), StandardCharsets.UTF_8));
        List<LineModel> result = new ArrayList<>();
        for (String line; (line = reader.readLine()) != null; ) {
            result.add(ToModelMapper.toModel(line.split(";")));
        }
        return result;
    }

}
