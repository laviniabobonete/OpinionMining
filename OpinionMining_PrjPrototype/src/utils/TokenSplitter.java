package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TokenSplitter {

    private static final String WORKING_DIR_PATH = System.getProperty("user.dir");
    private static final String BLACKLIST_FILE_PATH = WORKING_DIR_PATH + "\\resources\\blacklist.txt";

    private static List<String> blackList;

    /**
     * Split text into tokens, eliminating the stop words
     */
    public static List<String> split(String text) {

        List<String> tokens = new ArrayList<String>();

        StringTokenizer tok = new StringTokenizer(text, "'- ");
        while(tok.hasMoreTokens()) {
            String token = tok.nextToken();
                tokens.add(token);
        }

        return tokens;
    }

    /**
     * Load stop words list
     */
    private static void loadBlackList() {
        blackList = new ArrayList<String>();

        try {
            RandomAccessFile raf = new RandomAccessFile(BLACKLIST_FILE_PATH, "r");
            String line = raf.readLine();
            while (line != null) {
                blackList.add(line);
                line = raf.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
