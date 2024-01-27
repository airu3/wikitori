package model;
import java.util.ArrayList;
import java.util.List;

public class ShiritoriModel {
    private List<String> wordHistory = new ArrayList<>();
    private String cpuWord = "";
    private boolean isWork = false;

    public String playShiritori(String userMsg) {
        List<String> words = new ArrayList<>();
        List<String> links = new ArrayList<>();

        String[] changes = strChange(userMsg, -1);

        fetchWordsFromWikipedia(changes[0], words, links);
        fetchWordsFromWikipedia(changes[1], words, links);

        if (words.isEmpty()) {
            return "負けました";
        }

        int random = (int) (Math.random() * words.size());
        cpuWord = words.get(random);
        String wikiLink = links.get(random);

        if (strChange(cpuWord, -1)[0].equals("ん")) {
            do {
                words.remove(cpuWord);
                random = (int) (Math.random() * words.size());
                cpuWord = words.get(random);
                wikiLink = links.get(random);
            } while (strChange(cpuWord, -1)[0].equals("ん"));
        }

        return cpuWord + "," + wikiLink;
    }

}
