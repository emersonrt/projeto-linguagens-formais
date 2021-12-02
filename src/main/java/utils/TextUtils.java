package utils;

import java.util.List;
import java.util.stream.Collectors;

public class TextUtils {

    public static String arrayToStringLF(List<Character> list) {
        return "{" + list.stream().map(Object::toString)
                .collect(Collectors.joining(", ")) + "}";
    }

}
