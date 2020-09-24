import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WordCount {
    public static void main(String[] args) {
        long readStartTime = System.currentTimeMillis();
        List<Question> savedQuestionsList = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream("C:\\Users\\DELL\\Desktop\\questions");
             InputStreamReader isr = new InputStreamReader(fis, "utf-8");
             BufferedReader reader = new BufferedReader(isr)) {
            while (reader.ready()) {
                String line = reader.readLine();
                if (line != null && !"".equals(line)) {
                    savedQuestionsList.add(new Question(line));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long readEndTime = System.currentTimeMillis();
        float readExcTime = (float) (readEndTime - readStartTime) / 1000;
        System.out.println("执行时间为：" + readExcTime + "s");


        long startTime = System.currentTimeMillis();
        Map<String, Long> map = savedQuestionsList.parallelStream()
                .map(Question::getTitle)//转为名称列表
                .flatMap(title -> Arrays.stream(title.split("")))//拆分为字符
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));//分组计数
        map.entrySet().stream().sorted((c1, c2) -> c2.getValue().compareTo(c1.getValue())).forEach(e -> {//排序
            System.out.println(e.getKey() + "  " + e.getValue());
        });

        long endTime = System.currentTimeMillis();

        float excTime = (float) (endTime - startTime) / 1000;

        System.out.println("执行时间为：" + excTime + "s");
    }

}
