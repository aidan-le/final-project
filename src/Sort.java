import java.util.ArrayList;

public class Sort {
    private Sort() {}

    public static void sortScore(ArrayList<Word> words)
    {
        int n = words.size();
        for (int i = 1; i < n; ++i) {
            Word key = words.get(i);
            int j = i - 1;

            while (j >= 0 && words.get(j).getScore() < key.getScore()) {
                words.set(j + 1, words.get(j));
                j = j - 1;
            }
            words.set(j + 1, key);
        }
    }
}