package ru.AndreySidorov.TestAppSimbirSoft.processingLines;

import ru.AndreySidorov.TestAppSimbirSoft.logger.ILogger;

import java.sql.*;
import java.util.Map;

public class ProcessingResultToDatabase implements IProcessingResult {
    private String databaseName;
    private ILogger logger;
    private String url;

    public ProcessingResultToDatabase(String databaseName, ILogger logger, String url) {
        this.databaseName = databaseName;
        this.logger = logger;
        this.url = url;
    }

    @Override
    public void process(Map<String, Integer> result) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = null;
            try
            {
                connection = DriverManager.getConnection("jdbc:sqlite:"+databaseName+".db");
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);
                statement.executeUpdate("create table if not exists statistics (id integer primary key autoincrement, value string, url string)");
                PreparedStatement insertNewValue = connection.prepareStatement("insert into statistics (value, url) values(?, ?)");
                insertNewValue.setString(1, result.toString());
                insertNewValue.setString(2, url);
                insertNewValue.executeUpdate();
            }
            catch(SQLException e)
            {
                logger.logging(e.getMessage());
            }
            finally
            {
                try
                {
                    if(connection != null)
                        connection.close();
                }
                catch(SQLException e)
                {
                    logger.logging(e.getMessage());
                }
            }
        } catch (ClassNotFoundException e) {
            logger.logging(e.getMessage());
        }
    }
}
