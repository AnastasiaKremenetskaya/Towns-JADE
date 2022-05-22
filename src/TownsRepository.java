import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class TownsRepository {
    private static final ArrayList<String> towns = new ArrayList<>();

    private static void init() {
        try (
                BufferedReader br = new BufferedReader(new FileReader("doc/towns.txt"))) {
            while (br.ready()) {
                towns.add(br.readLine());
            }
        } catch (Throwable ignored) {
        }
        Collections.shuffle(towns);
    }

    public static ArrayList<String> getTowns() {
        if (towns.size() == 0) {
            init();
        }
        return towns;
    }

    public static void remove(String town) {
        towns.remove(town);
    }
    
    public static String getAnswer(String senderAgentName, String messageText) {
        String answer = "";
        Character lastChar = getLastChar(messageText);

        if (towns.size() > 0) {
            for (int i = 0; i < towns.size(); i++) {
                String town = towns.get(i);
                Character firstChar = getFirstChar(town);
                if (firstChar.equals(lastChar)) {

                    System.out.println(senderAgentName + ": " + town);

                    towns.remove(town);
                    answer = town;
                    break;
                }
            }
        } else {
            answer = "End";
        }

        return answer;
    }

    public static Character getFirstChar(String town) {
        return town.charAt(0);
    }

    public static Character getLastChar(String town) {
        int value = town.length() - 1;
        char lastChar = town.toUpperCase().charAt(value);
        if (town.toUpperCase().charAt(value) == 'Й') {
            return 'И';
        }
        if (lastChar == 'Ь' || lastChar == 'Ы' || lastChar == 'Ъ') {
            value--;
        }
        return town.toUpperCase().charAt(value);
    }
}
