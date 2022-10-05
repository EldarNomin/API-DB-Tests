package utils;

public class StringUtils {
    public String getLastNum(String text) {
        char[] ch = text.toCharArray();
        int index = 0;
        String num = "";

        for (int i = ch.length - 1; i > 0; i--) {
            if (ch[i] >= '0' && ch[i] <= '9') {
                index = i;
            } else {
                break;
            }
        }
        num = text.substring(index, text.length());
        return num;
    }
}