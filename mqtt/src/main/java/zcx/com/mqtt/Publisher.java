package zcx.com.mqtt;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;

import java.net.URISyntaxException;

public class Publisher {


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
            System.out.println("=======================================================================================");
            connection.connect();
//            publishNode(connection);
//            publishGetDevice(connection);
            publishDeviceUpdate(connection);
//            publishDeviceCloudUpdate(connection);
//            publishGetDevice(connection);
//            publishGetDeviceState(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //修改设备状态
    private static void publishDeviceUpdate(BlockingConnection connection) throws Exception {
        //actual修改云端状态
        String json = "{\"twin\":{\"temperature-enable\":{\"actual\":{\"value\":\"ON\"}}}}";
        connection.publish("$hw/events/device/sensor-test/twin/update", json.getBytes(), QoS.AT_LEAST_ONCE, false);
    }

    //将设备状态同步到云端
    private static void publishDeviceCloudUpdate(BlockingConnection connection) throws Exception {
//        Gson gson = new Gson();
//        String json="{\"twin\":{\"temperature-enable\":{\"actual\":{\"value\":\"ddd\"},\"metadata\":{\"type\":\"string\"}}}}";
        String json="{\"twin\":{}}";
        connection.publish("$hw/events/device/sensor-test/twin/cloud_updated", json.getBytes(), QoS.AT_LEAST_ONCE, false);
    }

    //获取设备状态
    private static void publishGetDevice(BlockingConnection connection) throws Exception {
        connection.publish("$hw/events/device/sensor-test/twin/get", "{}".getBytes(), QoS.AT_LEAST_ONCE, false);
    }

    //
    private static void publishGetDeviceState(BlockingConnection connection) throws Exception {
        connection.publish("$hw/events/device/sensor-test/state/update", "{}".getBytes(), QoS.AT_LEAST_ONCE, false);
    }

    //获取节点上到设备信息
    private static void publishNode(BlockingConnection connection) throws Exception {
        connection.publish("$hw/events/node/edge/membership/get", "{}".getBytes(), QoS.AT_LEAST_ONCE, false);
    }

}
