import com.sentiwordnet.SentiWordNet;

public class Main {

    public static void main(String args[]) {

        String word = "google";

        System.out.println(SentiWordNet.getScoreFor(word, "a"));
        System.out.println(SentiWordNet.getScoreFor(word, "n"));
        System.out.println(SentiWordNet.getScoreFor(word, "v"));
        System.out.println(SentiWordNet.getScoreFor(word, "r"));
        System.out.println(SentiWordNet.getScoreFor(word));
    }

}