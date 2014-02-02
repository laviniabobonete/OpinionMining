import results.ReviewExtraction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String WORKING_DIR_PATH = System.getProperty("user.dir") + "\\resources\\reviews";

    public static void main(String args[]) {

        List<String> fileNames = new ArrayList<String>();
        File[] files = new File(WORKING_DIR_PATH).listFiles();

        for (File file : files) {
            if (file.isFile()) {
                fileNames.add(file.getName());
            }
        }

        for(String fileName : fileNames) {
            ReviewExtraction review = new ReviewExtraction(fileName);
            review.extractResults();
            review.printResults();
        }
    }
}
