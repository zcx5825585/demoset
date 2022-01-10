import com.jcraft.jsch.JSch;

public class CmdDemo {
    public static void main2(String[] args) {
        String result = CmdUtil.executeLinuxCmd("ipconfig", "GBK");
        System.out.println(result);
    }


    public static void main(String[] args) throws Exception {
        JSch jsch = new JSch();
        JSchSession session = new JSchSession("root", "Usg220011.", "118.190.173.81", 22, jsch);
        String result = session.doExec("docker images");
        System.out.println(result);
        String result2 = session.doExec("docker ps");
        System.out.println(result2);
        session.disconnect();
    }
}
