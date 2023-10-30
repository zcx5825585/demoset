import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class M3u8App {
    public static void main(String[] args) throws Exception {
        String localRoot = "";
        String urlRoot = "";
        String category = "";
        if (args != null && args.length > 0) {
            for (String arg : args) {
                if (arg.contains("=")) {
                    String[] arr = arg.split("=");
                    if (arr != null && arr.length == 2) {
                        String label = arr[0];
                        String value = arr[1];
                        if ("--localRoot".equals(label)) {
                            localRoot = value;
                        } else if ("--urlRoot".equals(label)) {
                            urlRoot = value;
                        } else if ("--category".equals(label)) {
                            category = value;
                        }
                    }
                } else if ("--nmd".equals(arg)) {

                    mergeAndDelete = false;
                }
            }
        }

        File indexCache = new File(localRoot + "\\cache\\" + category);
        List<String> keyList = new ArrayList<>();

        if (indexCache.exists()) {
            keyList = Files.readAllLines(Paths.get(localRoot + "\\cache\\" + category), StandardCharsets.UTF_8);
        } else {
            List<String> pageList = new ArrayList<>();
            pageList.add("");
            for (int i = 0; i < pageList.size(); i++) {
                String html = HttpUtils.sendGetAndTry(urlRoot + "/category/" + category + pageList.get(i));
                StringReader htmlString = new StringReader(html);
                List<String> list = htmlString.readAllByFlag("<div class=\"card-body\"><a href=\"/movie/", "\"><h2>");
                keyList.addAll(list);
                if (i == 0) {
                    pageList.addAll(htmlString.readAllByFlag("<li class=\"no\"><a href=\"", "\">"));
                }
            }
            try (FileWriter fileWriter = new FileWriter(indexCache)) {
                for (String key : keyList) {
                    fileWriter.append(key + "\n");
                }
            }
        }
        System.out.println("获取目录成功");

        M3u8Utils m3u8Utils = new M3u8Utils(localRoot, urlRoot);
        for (String key : keyList) {

            try {
                String html = HttpUtils.sendGet(urlRoot + "/share/" + key);
                StringReader htmlString = new StringReader(html);
                String title = htmlString.readByFlag("<title>", "</title>").replaceAll(" ", "");
                String token = htmlString.readByFlag("var token = \"", "\"");
                String path = htmlString.readByFlag("var m3u8 = '", "';");
                m3u8Utils.downLoadM3u8(key, title, path, token);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}