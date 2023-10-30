import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CmdUtil {


    public static String executeSilent(String cmd) {
        return execute(cmd, "utf-8", true);
    }

    public static String execute(String cmd) {
        return execute(cmd, "utf-8", false);
    }

    public static String execute(String cmd, String charsetName, boolean silent) {
        if (!silent)
            System.out.println("执行命令[ " + cmd + "]");
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            InputStream stdin = process.getInputStream();//原始执行结果 得到进程的标准输出信息流
            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(stdin, charsetName));
            StringBuffer out = new StringBuffer();
            new Thread(() -> {
                try {
                    String line;
                    while ((line = stdoutReader.readLine()) != null) {
                        if (!silent)
                            System.out.println(line);
                        out.append(line);
                        out.append("\n");
                    }
                } catch (Exception ignored) {
                }
            }).start();
            InputStream stderr = process.getErrorStream();//原始错误结果 得到进程的标准输出信息流
            BufferedReader stdoutReaderErr = new BufferedReader(new InputStreamReader(stderr, charsetName));
            new Thread(() -> {
                try {
                    String line;
                    while ((line = stdoutReaderErr.readLine()) != null) {
                        if (!silent)
                            System.out.println(line);
                        out.append(line);
                        out.append("\n");
                    }
                } catch (Exception ignored) {
                }
            }).start();
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            process.destroy();
            if (!silent)
                System.out.println();
            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
