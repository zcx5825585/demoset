import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CmdDemo {
    public static void main2(String[] args) {
        String result = CmdUtil.executeLinuxCmd("ipconfig", "GBK");
        System.out.println(result);
    }



    public static void main(String[] args) throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession("root", "192.168.0.81", 22);
        session.setPassword("sa123");
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect(60 * 1000);

        ChannelExec channel = (ChannelExec) session.openChannel("exec");
        String command = "docker images";
        String charsetName = "utf-8";
        channel.setCommand(command);
//        channel.setOutputStream(System.out);
        channel.setErrStream(System.err);
        channel.connect();

        String line;
        InputStream stderr = channel.getInputStream();//原始执行结果 得到进程的标准输出信息流
        InputStreamReader isr = new InputStreamReader(stderr, charsetName);//转换为字符流 此时设置字符编码 默认为utf-8
        BufferedReader stdoutReader = new BufferedReader(isr);
        StringBuffer out = new StringBuffer();
        while ((line = stdoutReader.readLine()) != null) {
            out.append(line);
            out.append("\n");
        }
        System.out.println(out);
        channel.disconnect();
        session.disconnect();
    }
}
