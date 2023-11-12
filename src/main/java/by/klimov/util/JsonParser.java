package by.klimov.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import static by.klimov.util.StringLiteral.LEFT_BRACKET;
import static by.klimov.util.StringLiteral.RIGHT_BRACKET;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class JsonParser {

    public static Map<String, String> parseJson(String json) {
        json = json.trim();
        if (json.startsWith(LEFT_BRACKET) && json.endsWith(RIGHT_BRACKET)) {
            return Map.of("", json);
        }
        json = removeStarAndEndBraces(json);
        Map<String, String> map = new HashMap<>();
        String key = "";
        String value;
        int bracketCounter = 0;
        int braceCounter = 0;
        boolean isKey = true;
        int keyStart = json.indexOf("\"") + 1;
        for (int i = keyStart; i < json.length(); i++) {
            char c = json.charAt(i);
            if (isKey) {
                if (isEndKey(json, c, i)) {
                    isKey = false;
                    key = json.substring(keyStart, i).trim();
                    keyStart = i + 2;
                }
            } else {
                braceCounter = c == '{' ? braceCounter + 1: braceCounter;
                braceCounter = c == '}' ? braceCounter - 1: braceCounter;
                bracketCounter = c == '[' ? bracketCounter + 1: bracketCounter;
                bracketCounter = c == ']' ? bracketCounter + 1: bracketCounter;
                if (bracketCounter == 0 && braceCounter == 0) {
                    if (isEndValue(json, c, i)) {
                        isKey = true;
                        value = json.substring(keyStart, i).replace("\"", "").trim();
                        keyStart = i + 2;
                        map.put(key, value);
                    }
                    if (isEndJson(json, i)) {
                        value = json.substring(keyStart).replace("\"", "").trim();
                        map.put(key, value);
                    }
                }
            }
        }
        return map;
    }

    private static boolean isEndJson(String json, int i) {
        return i + 1 == json.length();
    }

    private static String removeStarAndEndBraces(String json) {
        return json.substring(1, json.length() - 1);
    }

    private static boolean isEndValue(String json, char c, int i) {
        return (c == ',' && ((i + 2) < json.length() && (json.charAt(i + 1) == '\"')) );
    }

    private static boolean isEndKey(String json, char c, int i) {
        return c == '\"' && ((i + 2) < json.length() && (json.charAt(i + 1) == ':'));
    }
}
