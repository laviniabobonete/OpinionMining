package results;

import data.CharacteristicsRetrieval;
import data.DataRetrieval;
import keyword.Keyword;
import keyword.KeywordsRetrieval;
import sentiment.Score;
import sentiment.SentimentAnalysis;
import utils.TokenSplitter;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewExtraction {

    private String fileName;

    private String data;
    private Map<Keyword, Score> keywords;
    private Map<String, List<Keyword>> results;

    public ReviewExtraction(String fileName) {
        this.fileName = fileName;

        keywords = new HashMap<Keyword, Score>();
        results = new HashMap<String, List<Keyword>>();
    }

    public void extractResults() {

        loadData();
        extractKeywords();

        List<String> characteristics = getCharacteristics();
        for(String characteristic: characteristics) {
            results.put(characteristic, new ArrayList<Keyword>());
        }

        for(Keyword key: keywords.keySet()) {
            for(String characteristic: characteristics) {
                if(TokenSplitter.split(key.getText().toLowerCase()).contains(characteristic)) {
                    List<Keyword> keys = results.get(characteristic);
                    keys.add(key);
                    results.put(characteristic, keys);
                }
            }
        }
    }

    public void printResults() {
        String filePath = fileName.replaceFirst("reviews", "results");

        int positives = 0;
        int negatives = 0;

        try{

            RandomAccessFile file = new RandomAccessFile(filePath, "rw");

            file.write("--------------------------- Keywords and Scores --------------------------\n".getBytes());
            for (Map.Entry<Keyword, Score> entry : keywords.entrySet()) {
                Keyword key = entry.getKey();
                Score score = entry.getValue();

                if(Score.POSITIVE.equals(score)) {
                    positives ++;
                }
                if(Score.NEGATIVE.equals(score)) {
                    negatives ++;
                }


                file.write((key.getText() + " : " + score + "\n").getBytes());
            }

            file.write("--------------------------- Review results --------------------------\n".getBytes());
            for(String characteristic: results.keySet()) {
                List<Keyword> keys = results.get(characteristic);
                if(!keys.isEmpty()) {
                    file.write((characteristic + "\n").getBytes());

                    for(Keyword key: keys) {
                        file.write(("\t" + key.getText() + " : " + keywords.get(key) + "\n").getBytes());
                    }
                }
            }

            file.write("--------------------------- Overall --------------------------\n".getBytes());
            Score finalScore = positives > negatives ? Score.POSITIVE : Score.NEGATIVE;
            file.write(finalScore.name().getBytes());

            file.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadData() {
        this.data = DataRetrieval.getData(fileName);
    }

    private List<String> getCharacteristics() {
        return CharacteristicsRetrieval.getCharacteristics();
    }

    private void extractKeywords() {
        SentimentAnalysis sm = new SentimentAnalysis();

        List<Keyword> keywordsList =  KeywordsRetrieval.getKeywords(data);
        for(Keyword key: keywordsList) {
            keywords.put(key, sm.getScoreFor(key));
        }
    }
}
