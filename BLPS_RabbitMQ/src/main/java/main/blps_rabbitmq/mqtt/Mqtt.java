package main.blps_rabbitmq.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Mqtt {
    private static IMqttAsyncClient iMqttAsyncClient;

    @Autowired
    public void setIMqttAsyncClient(IMqttAsyncClient iMqttAsyncClient) {
        Mqtt.iMqttAsyncClient = iMqttAsyncClient;
    }

    public static void publish(String topic, MqttMessage message) throws MqttException {
        iMqttAsyncClient.publish(topic, message);
    }

    public static void publish(String topic, byte[] payload, int qos, boolean retained) throws MqttException {
        iMqttAsyncClient.publish(topic, payload, qos, retained);
    }

    public static void subscribe(String topic, int qos) throws MqttException {
        iMqttAsyncClient.subscribe(topic, qos);
    }

    public static void subscribe(String topic, int qos, IMqttMessageListener messageListener) throws MqttException {
        iMqttAsyncClient.subscribe(topic, qos, null, null, messageListener);
    }

    public static void subscribe(String[] topic, int[] qos, IMqttMessageListener[] messageListener) throws MqttException {
        iMqttAsyncClient.subscribe(topic, qos, null, null, messageListener);
    }
}
