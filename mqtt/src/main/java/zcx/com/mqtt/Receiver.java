package zcx.com.mqtt;

import org.fusesource.mqtt.client.*;

import java.net.URISyntaxException;

public class Receiver {
    public static void main(String[] args) {
        MQTT mqtt = new MQTT();
        try {
            mqtt.setHost("192.168.0.83", 1883);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println(mqtt.getVersion());
        BlockingConnection connection = mqtt.blockingConnection();
        try {
            connection.connect();
            Topic[] topics = {
                    new Topic("$hw/events/device/sensor-test/twin/update/document", QoS.AT_LEAST_ONCE),
                    new Topic("$hw/events/device/sensor-test/twin/update/delta", QoS.AT_LEAST_ONCE),
                    new Topic("$hw/events/device//twin/update/result", QoS.AT_LEAST_ONCE),
                    new Topic("$hw/events/device/sensor-test/twin/get/result", QoS.AT_LEAST_ONCE),
                    new Topic("$hw/events/node/edge/membership/get/result", QoS.AT_LEAST_ONCE)
            };
            byte[] qoses = connection.subscribe(topics);
            while (true) {
                Message message = null;
                message = connection.receive();
                byte[] payload = message.getPayload();
                message.ack();
                System.out.println(new String(payload));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
