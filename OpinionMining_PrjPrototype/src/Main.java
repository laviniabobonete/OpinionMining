import data.DataRetrieval;
import keyword.Keyword;
import keyword.KeywordsRetrieval;
import sentiment.SentimentAnalysis;
import utils.TokenSplitter;

import javax.xml.crypto.Data;

public class Main {

    public static void main(String args[]) {
        //System.out.println(KeywordsRetrieval.getKeywords(DataRetrieval.getData()));
        //System.out.println(TokenSplitter.getPath());
        //System.out.println(TokenSplitter.split("ana has apples and are red i so black"));

        SentimentAnalysis sm = new SentimentAnalysis();
        for(Keyword key: KeywordsRetrieval.getKeywords(DataRetrieval.getData())){
            System.out.println("[" + key.getText() + "]" + " : " + sm.getScoreFor(key));
        }
    }
}
