import results.ReviewExtraction;

public class Main {

    public static void main(String args[]) {

        ReviewExtraction nexusReview = new ReviewExtraction("nexus4.txt");
        nexusReview.extractResults();
        nexusReview.printResults();

        ReviewExtraction samsungReview = new ReviewExtraction("samsungS4.txt");
        samsungReview.extractResults();
        samsungReview.printResults();
    }
}
