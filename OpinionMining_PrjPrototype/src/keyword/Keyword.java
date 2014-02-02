package keyword;

public class Keyword {

    public static float DEFAULT_RELEVANCE_VALUE = (float) -1.0;

    private String text;
    private float relevance;

    public Keyword() {}

    public Keyword(String text) {
        this.text = text;
        this.relevance  = DEFAULT_RELEVANCE_VALUE;

    }

    public Keyword(String text, float relevance) {
        this.text = text;
        this.relevance = relevance;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getRelevance() {
        return relevance;
    }

    public void setRelevance(float relevance) {
        this.relevance = relevance;
    }

    public String toString() {
        return "(Text: " + this.getText() + ", Relevance: " + this.getRelevance() + ")";
    }
}
