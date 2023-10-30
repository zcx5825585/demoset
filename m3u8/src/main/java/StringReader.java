import java.util.ArrayList;
import java.util.List;

public class StringReader {
    public String content;
    public int lastIndex;

    public StringReader(String content) {
        this.content = content;
        this.lastIndex = 0;
    }

    public List<String> readAllByFlag(String startFlag, String endFlag) {
        List<String> stringList = new ArrayList<>();
        while (content.indexOf(startFlag, lastIndex) > 0) {
            int start = content.indexOf(startFlag, lastIndex) + startFlag.length();
            int end = content.indexOf(endFlag, start);
            String string = content.substring(start, end);
            this.lastIndex = start;
            stringList.add(string);
        }
        return stringList;
    }

    public String readByFlag(String startFlag, String endFlag) {
        int start = content.indexOf(startFlag, lastIndex) + startFlag.length();
        int end = content.indexOf(endFlag, start);
        String string = content.substring(start, end);
        this.lastIndex = start;
        return string;
    }

}
