package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TextUtils {

    public static String arrayToStringLF(List<String> list) {
        return "{" + String.join(", ", list) + "}";
    }

    public static List<String> quebrarStringPorPadrao(String str, String pattern) {
        return Pattern.compile(pattern)
                .splitAsStream(str)
                .collect(Collectors.toList());
    }

    public static List<String> removeWhiteSpacesArray(List<String> list) {
        List<String> newList = new ArrayList<>();
        list.forEach(s -> newList.add(s.replaceAll("\\s+","")));
        return newList;
    }

    public static String removeColchetes(String str) {
        return str.replaceAll("[{}]","");
    }

    public static String removerParenteses(String str) {
        return str.replaceAll("[()]", "");
    }

    public static String removerDelta(String str) {
        return str.replaceAll("δ", "");
    }

}
