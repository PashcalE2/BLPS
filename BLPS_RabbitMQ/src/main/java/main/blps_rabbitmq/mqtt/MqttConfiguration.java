package main.blps_rabbitmq.mqtt;


import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@Slf4j
public class MqttConfiguration {
    @Bean
    public MqttConnectOptions mqttConnectOptions(MqttProperties mqttProperties) {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setConnectionTimeout(30);
        options.setKeepAliveInterval(30);
        options.setAutomaticReconnect(true);
//        options.setServerURIs(new String[]{mqttProperties.getHost()});
        options.setUserName(mqttProperties.getUsername());
        options.setPassword(mqttProperties.getPassword().toCharArray());
        return options;
    }

    @Bean
    public IMqttAsyncClient iMqttAsyncClientClient(MqttConnectOptions mqttConnectOptions, MqttProperties mqttProperties) throws MqttException, UnknownHostException {
        IMqttAsyncClient  client = new MqttAsyncClient(mqttProperties.getHost(),
                mqttProperties.getClientId() + getLocalHostIP());

        client.setCallback(new DefaultCallback());

        IMqttToken mqttToken = client.connect(mqttConnectOptions);

        mqttToken.waitForCompletion();

        return client;
    }

    /**
     * Tune class
     */
    static class DefaultCallback implements MqttCallback{

        @Override
        public void connectionLost(Throwable cause) {
            log.warn("MQTT connection");
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            log.debug("messageArrived {} {}",topic,message);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
            log.debug("deliveryComplete {}",token);
        }
    }

    public static String getLocalHostIP() throws UnknownHostException{
        return InetAddress.getLocalHost().getHostAddress();
    }
}