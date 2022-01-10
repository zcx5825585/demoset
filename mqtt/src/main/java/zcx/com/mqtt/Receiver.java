package zcx.com.mqtt;

import com.google.gson.Gson;
import org.fusesource.mqtt.client.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Receiver {
    public static void main(String[] args) {
        System.out.println(Md5Utils.shortHash("test"));
       oneDevice("2EC04DDC227A");
    }

    public static void oneDevice(String name){
        MQTT mqtt = new MQTT();
        try {
//            mqtt.setHost("smartvaccines.ahlyun.com", 8090);
            mqtt.setHost("127.0.0.1", 8089);
            mqtt.setUserName(name);
            mqtt.setPassword(Md5Utils.shortHash(name));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println(mqtt.getVersion());
        BlockingConnection connection = mqtt.blockingConnection();
        try {
            connection.connect();
            Topic[] topics1 = {
                    new Topic("aohui/vending/device/control/"+name, QoS.EXACTLY_ONCE)
            };
            System.out.println("aohui/vac/box/control/"+name);
            byte[] qoses = connection.subscribe(topics1);
            new Thread(() -> {
                while (true) {
                    try {
                        Message message = null;
                        message = connection.receive();
                        byte[] payload = message.getPayload();
                        message.ack();
                        System.out.println("connection receive");
                        System.out.println(new String(payload));
                        String text=MqttEncodeUtil.decode(new String(payload));
                        Gson gson=new Gson();
                        Map map=gson.fromJson(text, Map.class);
                        Map<String,Object> result=new HashMap<>();
                        result.put("sn",map.get("sn"));
                        result.put("function",map.get("function"));
                        result.put("deviceCode",name);
                        result.put("params",map.get("params"));
                        result.put("result","200");
                        String json = MqttEncodeUtil.encode(gson.toJson(result));
                        connection.publish("aohui/vac/box/control", json.getBytes(), QoS.AT_LEAST_ONCE, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

//            new Thread(() -> {
//                while (true) {
//                    try {
//                        Message message = null;
//                        message = connection2.receive();
//                        byte[] payload = message.getPayload();
//                        message.ack();
//                        System.out.println("connection2 receive");
//                        System.out.println(new String(payload));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
