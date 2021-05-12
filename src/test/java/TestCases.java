
import org.junit.Assert;
import org.junit.Test;
import ru.AndreySidorov.TestAppSimbirSoft.Downloader;
import ru.AndreySidorov.TestAppSimbirSoft.logger.ILogger;
import ru.AndreySidorov.TestAppSimbirSoft.logger.LoggerToFile;
import ru.AndreySidorov.TestAppSimbirSoft.processingLines.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;

public class TestCases {

    @Test
    public void downloadAndParseTestSimbirSoftTest() {
        String url = "https://www.simbirsoft.com/";
        ProcessingLineFindWords processingLineFindWords = new ProcessingLineFindWords(Arrays.asList());
        Downloader downloader = new Downloader(new ILogger() {
            @Override
            public void logging(String loggingStr) {
                System.out.println(loggingStr);
            }
        }, processingLineFindWords);
        downloader.downloadAndProcess(url);
        Assert.assertTrue(processingLineFindWords.getResult().containsKey("ready"));
        Assert.assertTrue(processingLineFindWords.getResult().get("ready").equals(10));
    }

    @Test
    public void loggerToFileTest() {
        File loggerFile = new File("test.txt");
        loggerFile.delete();
        LoggerToFile loggerToFile = new LoggerToFile("test.txt");
        loggerToFile.logging("test string");
        loggerFile = new File("test.txt");
        try {
            Assert.assertTrue(loggerFile.exists());
            Assert.assertTrue(Files.readAllLines(Paths.get(loggerFile.getPath())).get(0).equals("test string"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void processingResultToDatabaseTest() {
        ILogger loggerToFile = new LoggerToFile("log.txt");
        IProcessingResult processingResultToDatabase = new ProcessingResultToDatabase("simbirSoftTest", loggerToFile, "https://www.simbirsoft.com/");
        processingResultToDatabase.process(new HashMap<String, Integer>() {{
            put("1", 1);
        }});

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:simbirSoftTest.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet result = statement.executeQuery("select * from statistics where id = (select max(id) from statistics)");
            result.next();
            Assert.assertTrue(result.getString("value").equals("{1=1}"));
            Assert.assertTrue(result.getString("url").equals("https://www.simbirsoft.com/"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
