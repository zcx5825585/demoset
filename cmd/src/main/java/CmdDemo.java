
public class CmdDemo {
    public static void main(String[] args) {
        String result = CmdUtil.executeLinuxCmd("ipconfig","GBK");
        System.out.println(result);
    }
}
