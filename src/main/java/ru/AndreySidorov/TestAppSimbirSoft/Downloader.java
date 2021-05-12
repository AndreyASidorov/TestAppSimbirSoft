package ru.AndreySidorov.TestAppSimbirSoft;

import ru.AndreySidorov.TestAppSimbirSoft.logger.ILogger;
import ru.AndreySidorov.TestAppSimbirSoft.processingLines.IProcessingLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Downloader {
    private ILogger logger;
    private IProcessingLine processingLine;

    public Downloader(ILogger logger, IProcessingLine processingLine){
        this.logger = logger;
        this.processingLine = processingLine;
    }

    public void downloadAndProcess(String urlString){
        InputStream is = null;
        BufferedReader br;
        String line;
        try {
            URL url = new URL(urlString);
            is = url.openStream();
            br = new BufferedReader(new InputStreamReader(is));
            processingLine.startProcess();
            while ((line = br.readLine()) != null) {
                processingLine.process(line);
            }
            processingLine.endProcess();
            logger.logging("Success proceeded '"+urlString+"'");
        } catch (Exception e) {
            logger.logging("Error proceeded '"+urlString+"'");
            logger.logging(e.getMessage());
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                logger.logging(ioe.getMessage());
            }
        }
    };
}
