import mapper.ToModelMapper;
import model.LineModel;
import mongoManager.MongoManager;
import mysqlManager.MySQLManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        List<LineModel> lineModelsIo = Collections.emptyList();
        try {
            File file = new File("src/main/resources/big_file.txt");
            lineModelsIo = parseFile(file);
        } catch (Exception e) {
            System.out.println("error catched");
            e.printStackTrace();
        }

        MongoManager.createConnection();
        MongoManager.setDB();
        MongoManager.setCollection();
        long beforeMongo = System.currentTimeMillis() / 1000L;
        MongoManager.saveLines(lineModelsIo);
        long afterMongo = System.currentTimeMillis() / 1000L;
        long mongoDif = afterMongo - beforeMongo;
        System.out.println("time of saving to mongo = " + (mongoDif) + "ms ... or   " + mongoDif / 1000L / 60L + "min");
        //MongoManager.clearCollection();
        MongoManager.closeConnection();

        long beforeMysql = System.currentTimeMillis() / 1000L;
        MySQLManager.doGood(lineModelsIo);
        long afterMysql = System.currentTimeMillis() / 1000L;
        long mysqlDif = afterMysql - beforeMysql;
        System.out.println("time of saving to mySQL = " + (mysqlDif) + "ms ... or   " + mysqlDif / 1000 / 60 + "min");


    }

    private static List<LineModel> parseFile(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        List<LineModel> result = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            result.add(ToModelMapper.toModel(line.split(";")));
        }
        return result;
    }

}
