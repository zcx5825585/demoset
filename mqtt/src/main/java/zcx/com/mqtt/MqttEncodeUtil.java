package zcx.com.mqtt;


public class MqttEncodeUtil {

    public static String encode(String message) {
        String md5 = Md5Utils.shortHash(message);
        message = message + md5;
        return message;
    }

    public static String decode(String message) {
        if (message.length() <= 16) {
            System.out.println("消息长度错误");
        }
        String md5 = message.substring(message.length() - 16);
        message = message.substring(0, message.length() - 16);
        if (!Md5Utils.shortHash(message).equals(md5)) {
            System.out.println("md5不匹配");
        }
        return message;
    }
}
