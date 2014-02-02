package com.sentiwordnet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class SentiWordNet {

    public static String FILE_PATH = "D:\\School\\Disertatie\\git\\OpinionMining\\SentiWordNet\\SentiWordNet_3.0.0_20130122.txt";
    private static Map<String, Double> dictionary;

    private static void createDictionary() {
        // Dictionary representation
        dictionary = new HashMap<String, Double>();

        // From String to list of doubles.
        HashMap<String, HashMap<Integer, Double>> tempDictionary = new HashMap<String, HashMap<Integer, Double>>();

        BufferedReader csv = null;
        try {
            csv = new BufferedReader(new FileReader(FILE_PATH));
            int lineNumber = 0;

            String line;
            while ((line = csv.readLine()) != null) {
                lineNumber++;

                // If it's a comment, skip this line.
                if (!line.trim().startsWith("#")) {
                    // We use tab separation
                    String[] data = line.split("\t");
                    String wordTypeMarker = data[0];

                    // Example line:
                    // POS ID PosS NegS SynsetTerm#sensenumber Desc
                    // a 00009618 0.5 0.25 spartan#4 austere#3 ascetical#2
                    // ascetic#2 practicing great self-denial;...etc

                    // Is it a valid line? Otherwise, through exception.
                    if (data.length != 6) {
                        throw new IllegalArgumentException(
                                "Incorrect tabulation format in file, line: "
                                        + lineNumber);
                    }

                    // Calculate synset score as score = PosS - NegS
                    Double synsetScore = Double.parseDouble(data[2])
                            - Double.parseDouble(data[3]);

                    // Get all Synset terms
                    String[] synTermsSplit = data[4].split(" ");

                    // Go through all terms of current synset.
                    for (String synTermSplit : synTermsSplit) {
                        // Get synterm and synterm rank
                        String[] synTermAndRank = synTermSplit.split("#");
                        String synTerm = synTermAndRank[0] + "#"
                                + wordTypeMarker;

                        int synTermRank = Integer.parseInt(synTermAndRank[1]);
                        // What we get here is a map of the type:
                        // term -> {score of synset#1, score of synset#2...}

                        // Add map to term if it doesn't have one
                        if (!tempDictionary.containsKey(synTerm)) {
                            tempDictionary.put(synTerm,
                                    new HashMap<Integer, Double>());
                        }

                        // Add synset link to synterm
                        tempDictionary.get(synTerm).put(synTermRank,
                                synsetScore);
                    }
                }
            }

            // Go through all the terms.
            for (Map.Entry<String, HashMap<Integer, Double>> entry : tempDictionary
                    .entrySet()) {
                String word = entry.getKey();
                Map<Integer, Double> synSetScoreMap = entry.getValue();

                // Calculate weighted average. Weigh the synsets according to
                // their rank.
                // Score= 1/2*first + 1/3*second + 1/4*third ..... etc.
                // Sum = 1/1 + 1/2 + 1/3 ...
                double score = 0.0;
                double sum = 0.0;
                for (Map.Entry<Integer, Double> setScore : synSetScoreMap
                        .entrySet()) {
                    score += setScore.getValue() / (double) setScore.getKey();
                    sum += 1.0 / (double) setScore.getKey();
                }
                score /= sum;

                dictionary.put(word, score);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (csv != null) {
                try {
                    csv.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static double getScoreFor(String word, String type) {

        if(dictionary == null) {
            createDictionary();
        }

        word = word.toLowerCase();

        Double value = dictionary.get(word + "#" + type);
        return value != null ? value : 0.0;
    }

    public static double getScoreFor(String word) {

        if(dictionary == null) {
            createDictionary();
        }

        word = word.toLowerCase();

        double avg = 0.0;
        double typeCount = 0;

        // adjective
        double a = getScoreFor(word, "a");
        boolean isAdjective = (a != 0.0 ? true : false);
        if(isAdjective) {
            avg += a;
            typeCount ++;
        }

        // noun
        double n = getScoreFor(word, "n");
        boolean isNoun = (n != 0.0 ? true : false);
        if(isNoun) {
            avg += n;
            typeCount ++;
        }

        // verb
        double v = getScoreFor(word, "v");
        boolean isVerb = (v != 0.0 ? true : false);
        if(isVerb) {
            avg += v;
            typeCount ++;
        }

        //adverb
        double r = getScoreFor(word, "r");
        boolean isAdverb = (r != 0.0 ? true : false);
        if(isAdverb) {
            avg += r;
            typeCount ++;
        }

        return (typeCount > 0.0) ? (avg/typeCount) : 0.0;

    }
}
