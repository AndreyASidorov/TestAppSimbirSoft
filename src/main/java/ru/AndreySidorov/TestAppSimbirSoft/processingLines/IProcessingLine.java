package ru.AndreySidorov.TestAppSimbirSoft.processingLines;

public interface IProcessingLine {
    void startProcess();
    void process(String inputLine);
    void endProcess();
}
