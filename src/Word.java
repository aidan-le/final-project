import org.json.JSONObject;

public class Word {
    private String word;
    private int score;

    public Word(JSONObject jsonObject) {
        word = jsonObject.getString("word");
        try {
            score = jsonObject.getInt("score");
        } catch (Exception e) {
            score = 0;
        }
    }

    public String getWord() {
        return word;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", score=" + score +
                '}';
    }
}
