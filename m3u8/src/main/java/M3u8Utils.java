import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class M3u8Utils {
    public static String mergeTsCmd = "ffmpeg -allowed_extensions ALL -protocol_whitelist \"file,http,crypto,tcp\" -i %s%s\\index.m3u8 -c copy %s%s\\output.ts";
    public static String converTsToMp3Cmd = "ffmpeg -i %s%s\\output.ts -f mp3 %s%s.mp3";

    private String localRoot;
    private String urlRoot;

    public M3u8Utils(String localRoot, String urlRoot) {
        this.localRoot = localRoot;
        this.urlRoot = urlRoot;
    }

    public void testFFmpeg(String key, String title) throws IOException {

        //合成ts文件
        CmdUtil.execute(String.format(mergeTsCmd, key, key));
        //转为mp3
        CmdUtil.execute(String.format(converTsToMp3Cmd, key, title));

    }

    public void downLoadM3u8(String key, String title, String path, String token) throws IOException {
        String dirPath = this.localRoot + key;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File mp3 = new File(localRoot + title + ".mp3");
        if (mp3.exists()) {
            if (mp3.length() < 1) {
                mp3.delete();
            } else {
                return;
            }
        }

        String filePath = dirPath + "/index.m3u8";
        File index = new File(filePath);
        if (index.exists()) {
            index.delete();
        }
        //下载m3u8
        DownloadUtil.download(urlRoot + path + "?token=" + token, filePath, 10);
        //读取文件
        List<String> content = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        StringBuilder newContent = new StringBuilder();
        boolean flag = true;
        for (int i = 0; i < content.size(); i++) {
            String line = content.get(i);
            if (line.contains("#EXT-X-KEY")) {//下载key文件
                StringReader keyReader = new StringReader(line);
                String keyUrl = keyReader.readByFlag("URI=\"", "\"");
                if (!keyUrl.startsWith("http")) {
                    keyUrl = urlRoot + keyUrl;
                }
                String keyPath = dirPath + keyUrl.substring(keyUrl.lastIndexOf("/"));
                if (!DownloadUtil.download(keyUrl, keyPath, 10)) {
                    System.out.println("跳过 " + key);
                    return;
                }
                ;
                newContent.append(line.replaceAll(keyUrl, keyPath) + "\n");
            } else if (line.contains("#EXTINF")) {//下载ts文件
                newContent.append(line + "\n");
                i++;//读取下一行
                String tsUrl = content.get(i);
                if (!tsUrl.startsWith("http")) {
                    tsUrl = urlRoot + tsUrl;
                }
                String tsPath = dirPath + tsUrl.substring(tsUrl.lastIndexOf("/"));
                if (!DownloadUtil.download(tsUrl, tsPath, 10)) {
                    System.out.println("跳过 " + key);
                    return;
                }
                ;
                newContent.append(tsPath + "\n");
            } else {
                newContent.append(line + "\n");
            }
        }
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.append(newContent.toString());
        }
        //合成ts文件
        CmdUtil.execute(String.format(mergeTsCmd, localRoot, key, localRoot, key));
        //转为mp3
        CmdUtil.execute(String.format(converTsToMp3Cmd, localRoot, key, localRoot, title));

    }


}
