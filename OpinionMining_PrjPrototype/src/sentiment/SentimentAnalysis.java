package sentiment;

import com.sentiwordnet.SentiWordNet;
import keyword.Keyword;
import utils.TokenSplitter;

import java.util.List;

public class SentimentAnalysis {

    /**
     * Computes the score for a given Keyword
     */
    public double getScoreValueFor(Keyword keyword) {

        double score = 0.0;    // returned keyword score

        List<String> tokens = TokenSplitter.split(keyword.getText());
        for(String token: tokens) {
            double localScore = SentiWordNet.getScoreFor(token);
            score += localScore;
            //System.out.print("   " + token + ":" + localScore);
        }
        return score;
    }

    /**
     * Classifies the given keyword into: POSITIVE, NEGATIVE, OBJECTIVE
     */
    public Score getScoreFor(Keyword keyword) {

        double score = getScoreValueFor(keyword);
        //System.out.print("   Score:" + score + "   ");

        if(score < 0.15 && score > -0.15) {
            return Score.OBJECTIVE;
        }

        if(score < 0) {
            return Score.NEGATIVE;
        }

        return Score.POSITIVE;
    }
}
