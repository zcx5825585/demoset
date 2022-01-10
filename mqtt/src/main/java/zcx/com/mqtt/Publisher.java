package zcx.com.mqtt;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;

import java.net.URISyntaxException;

public class Publisher {


    public static void main(String[] args) {
        MQTT mqtt = new MQTT();
        try {
            String name="testDevice";
            mqtt.setHost("127.0.0.1", 8089);
//            mqtt.setHost("cloud.ahlyun.com", 8089);
//            mqtt.setHost("118.190.173.81", 8089);
//            mqtt.setHost("192.168.0.215", 8089);
//            mqtt.setHost("127.0.0.1", 8089);
            mqtt.setUserName(name);
            mqtt.setPassword(Md5Utils.shortHash(name));
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
//            while (true) {
                String json = MqttEncodeUtil.encode("{\n" +
                        "\"sn\": \"2021_11_50af0755da114e5691d82b181f00a522\",\n" +
                        "\"deviceCode\": \"testdevice0001\",\n" +
                        "    \"function\": \"deviceconfig\",\n" +
                        "    \"params\": [\n" +
                        "        {\n" +
                        "            \"partsCatalogId\": 1242,\n" +
                        "            \"partsModelConfig\": {\n" +
                        "                \"enable\": \"0\" //是否启用 1启用 2禁用\n" +
                        "            }\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"result\": \"500\"\n" +
                        "}\n");
                connection.publish("aohui/vending/device/control", json.getBytes(), QoS.AT_MOST_ONCE, false);
//                Thread.sleep(1000 * 30);
//            }
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
//        connection.publish("$hw/events/device/sensor-test/twin/update2", json.getBytes(), QoS.AT_LEAST_ONCE, false);

    }

    //将设备状态同步到云端
    private static void publishDeviceCloudUpdate(BlockingConnection connection) throws Exception {
//        Gson gson = new Gson();
//        String json="{\"twin\":{\"temperature-enable\":{\"actual\":{\"value\":\"ddd\"},\"metadata\":{\"type\":\"string\"}}}}";
        String json = "{\"twin\":{}}";
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
