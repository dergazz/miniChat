package entity;

public class Parser {

    public static String parseOne(String string, int lengthOfCommand) {
        String newString = string.substring(lengthOfCommand).trim();
        int space = newString.indexOf(' ');
        if (space > 0) {
            return null;
        }
        return newString.substring(space + 1);
    }

    public static Pair<String, String> parsePair(String string, int lengthOfCommand) {
        String newString = string.substring(lengthOfCommand).trim();
        int space = newString.indexOf(' ');
        if (space <=0) {
            return new Pair<String, String>(null, null);
        }
        String key = newString.substring(0, space);
        String value = newString.substring(space +1);
        return new Pair<String, String>(key, value);
    }

}
