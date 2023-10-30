import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadUtil {
    public static boolean download(String url, String filePath, int time) throws IOException {
        int count = 0;
        while (count < time) {
            count++;
            if (download(url, filePath)) {
                return true;
            }
            System.out.println(url + ":下载重试" + count + "次");
        }
        return false;
    }

    public static boolean download(String url, String filePath) throws IOException {
        URL realUrl = new URL(url);
        File file = new File(filePath);
        if (file.exists() && file.length() > 1) {
            return true;
        }
        // 定义BufferedReader输入流来读取URL的响应.
        BufferedInputStream bufferedInputStream = null;
        try {
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(1000*60);
            connection.setReadTimeout(1000*60*3);
            connection.connect();
            bufferedInputStream = new BufferedInputStream(connection.getInputStream());
        } catch (Exception e) {
            System.out.println(url + ":下载失败");
            return false;
        }
        if (bufferedInputStream != null && !file.exists()) {
            file.createNewFile();
        } else {
            return true;
        }
        FileOutputStream fos = new FileOutputStream(file);
        int count = 0;
        byte[] b = new byte[100];

        while ((count = bufferedInputStream.read(b)) != -1) {
            fos.write(b, 0, count);
        }
        bufferedInputStream.close();
        fos.close();

        System.out.println(url + ":下载完成");
        return true;
    }
}
