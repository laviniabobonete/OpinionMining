package data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class CharacteristicsRetrieval {

    private static final String WORKING_DIR_PATH = System.getProperty("user.dir");
    private static final String FILE_PATH = WORKING_DIR_PATH + "\\resources\\characteristics.txt";

    /**
     * Get list of characteristics
     */
    public static List<String> getCharacteristics() {

        List<String> characteristics = new ArrayList<String>();

        try {
            RandomAccessFile raf = new RandomAccessFile(FILE_PATH, "r");
            String line = raf.readLine();
            while (line != null) {
                characteristics.add(line);
                line = raf.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return characteristics;
    }
}
