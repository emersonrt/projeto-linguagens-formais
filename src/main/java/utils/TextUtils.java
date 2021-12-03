package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TextUtils {

    public static String arrayToStringLF(List<String> list) {
        return "{" + String.join(", ", list) + "}";
    }

    public static List<String> removeWhiteSpaces(List<String> list) {
        List<String> newList = new ArrayList<>();
        list.forEach(s -> newList.add(s.replaceAll("\\s+","")));
        return newList;
    }

}
