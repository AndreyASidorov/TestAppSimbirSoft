package ru.AndreySidorov.TestAppSimbirSoft;

import ru.AndreySidorov.TestAppSimbirSoft.logger.ILogger;
import ru.AndreySidorov.TestAppSimbirSoft.logger.LoggerToFile;
import ru.AndreySidorov.TestAppSimbirSoft.processingLines.*;

import java.util.Arrays;

public class App {

    public static void main(String[] args){
        if(args.length == 0){
            System.out.println("You should set url as first argument");
            System.exit(0);
        }
        System.setProperty("file.encoding","UTF-8");

        ILogger loggerToFile = new LoggerToFile("log.txt");
        IProcessingResult processingResultToDatabase = new ProcessingResultToDatabase("simbirSoftTest", loggerToFile, args[0]);
        IProcessingResult processingResultToConsole = new ProcessingResultToConsole();
        IProcessingLine processingLineFindWords = new ProcessingLineFindWords(Arrays.asList(
                processingResultToDatabase,
                processingResultToConsole
        ));
        Downloader downloader = new Downloader(loggerToFile,processingLineFindWords);
        downloader.downloadAndProcess(args[0]);
    }
}
