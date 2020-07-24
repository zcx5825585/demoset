
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JSchSession {

    private String user;
    private String password;
    private String address;
    private int port;
    private int connectTimeout = 60 * 1000;
    private JSch jsch;
    private Session session;

    public JSchSession(String user, String password, String address, int port,JSch jsch) {
        this.user = user;
        this.password = password;
        this.address = address;
        this.port = port;
        this.jsch=jsch;
    }

    public Session getSession() {
        return session;
    }

    public void connect() throws JSchException {
        this.session = this.jsch.getSession(this.user, this.address, this.port);
        session.setPassword(this.password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect(this.connectTimeout);
    }

    public void disconnect() {
        this.session.disconnect();
    }

    public String doExec(String command) throws Exception {
        return doExec(command, "utf-8");
    }

    public String doExec(String command, String charsetName) throws Exception {
        ChannelExec channel = (ChannelExec) this.session.openChannel("exec");
        channel.setCommand(command);
        channel.setOutputStream(System.out);
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
        channel.disconnect();

        return out.toString();
    }
}
