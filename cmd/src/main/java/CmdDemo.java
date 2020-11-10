import com.jcraft.jsch.JSch;

public class CmdDemo {
    public static void main(String[] args) {
        String result = CmdUtil.executeLinuxCmd("ipconfig", "GBK");
        System.out.println(result);
    }


    public static void main2(String[] args) throws Exception {
        JSch jsch = new JSch();
        JSchSession session = new JSchSession("root", "sa123", "192.168.8.91", 22, jsch);
        String result = session.doExec("docker images");
        System.out.println(result);
        session.disconnect();
    }
}
