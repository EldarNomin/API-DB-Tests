package utils;

import java.util.Collections;
import java.util.List;

public class SortUtils {
    public static List<String> sortListReverseOrder(List<String> list) {
        Collections.sort(list, Collections.reverseOrder());
        return list;
    }
}