package ru.AndreySidorov.TestAppSimbirSoft.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LoggerToFile implements ILogger{
    private String fileName;

    public LoggerToFile(String fileName){
        this.fileName = fileName;
    }

    @Override
    public void logging(String loggingStr) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(
                    new FileWriter(fileName, true));
            writer.write(loggingStr+"\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
