public class Runner {
    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        String startWord = dictionary.getRandomWord();
        String endWord = dictionary.getRandomWord();

        Gui gui = new Gui(startWord, endWord);
        gui.init();
    }
}
