import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import results.ReviewExtraction;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main {

    private static final String WORKING_DIR_PATH = System.getProperty("user.dir") + "\\resources\\reviews";

    public static void main(String args[]) {

        List<String> fileNames = new ArrayList<String>();

        Collection<File> files = FileUtils.listFiles( new File(WORKING_DIR_PATH), new RegexFileFilter("^(.*?)"), DirectoryFileFilter.DIRECTORY);

        for (File file : files) {
            fileNames.add(file.getPath());
            for(String fileName : fileNames) {
                ReviewExtraction review = new ReviewExtraction(fileName);
                review.extractResults();
                review.printResults();
            }
        }
    }
}
