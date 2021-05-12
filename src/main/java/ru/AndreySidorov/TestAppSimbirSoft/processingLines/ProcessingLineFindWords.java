package ru.AndreySidorov.TestAppSimbirSoft.processingLines;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessingLineFindWords implements IProcessingLine {
    private List<IProcessingResult> processingResults;
    private Map<String, Integer> result;
    private String delimeters = " |,|\\.|!|\\?|\"|;|:|[|]|\\n|\\r|\\t|\\(|\\)";

    public ProcessingLineFindWords(List<IProcessingResult> processingResults) {
        this.processingResults = processingResults;
    }

    @Override
    public void startProcess() {
        result = new HashMap<>();
    }

    @Override
    public void process(String inputLine) {
        String[] words = inputLine.split(delimeters);
        for(String word : words){
            if(word.isEmpty()) {
                continue;
            }
            int countRepeat = 0;
            int indexBegin = 0;
            while(indexBegin >= 0) {
                countRepeat++;
                indexBegin = inputLine.indexOf(word, indexBegin);
                if(indexBegin >= 0) {
                    indexBegin++;
                }
            }
            Integer count = result.get(word);
            count = count == null ? 0 : count;
            count += countRepeat;
            result.put(word, count);
        }
    }

    @Override
    public void endProcess() {
        for(IProcessingResult processingResult : processingResults) {
            processingResult.process(result);
        }
    }

    public Map<String, Integer> getResult() {
        return result;
    }
}
