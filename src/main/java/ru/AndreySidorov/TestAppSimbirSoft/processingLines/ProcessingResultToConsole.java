package ru.AndreySidorov.TestAppSimbirSoft.processingLines;

import java.util.Map;

public class ProcessingResultToConsole implements IProcessingResult {
    @Override
    public void process(Map<String, Integer> result) {
        for(Map.Entry<String, Integer> value : result.entrySet()){
            System.out.println(value.getKey() + ":" + value.getValue());
        }
    }
}
