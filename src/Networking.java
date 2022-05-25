import java.util.ArrayList;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class Networking {
    private static int MAX_WORDS = 20;
    private static String[] queries = {"ml", "sl", "sp"};

    public static ArrayList<Word> getRelatedWords(String givenWord, String topicWord) {
        ArrayList<Word> words = new ArrayList<>();

        for (String query : queries) {
            final String url = "https://api.datamuse.com/words?"+query+"="+givenWord+"&topics="+topicWord;
            String response = makeAPICall(url);

            if (response == null) {
                return null;
            }

            words.addAll(parseJSON(response));
        }

        Sort.sortScore(words);
        for (int i = 0; i < words.size(); i++) {
            Word thisWord = words.get(i);
            for (int j = i + 1; j < words.size(); j++) {
                if (words.get(j).getWord().equals(thisWord.getWord())) {
                    words.remove(j);
                    j--;
                }
            }
        }
        for (int i = MAX_WORDS; i < words.size(); i++) {
            words.remove(i);
            i--;
        }
        return words;
    }

    private static String makeAPICall(String url) {
        try {
            URI myUri = URI.create(url);
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static ArrayList<Word> parseJSON(String json) {
        JSONArray jsonArray = new JSONArray(json);
        ArrayList<Word> linkedList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            linkedList.add(new Word(jsonObject));
        }

        return linkedList;
    }
}
