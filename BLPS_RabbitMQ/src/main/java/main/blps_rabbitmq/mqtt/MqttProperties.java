package main.blps_rabbitmq.mqtt;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.springframework.stereotype.Component;

@Component
public class MqttProperties {
    private String host = "tcp://localhost:1883";

    private String username = "test";

    private String password = "test";

    private String[] topics = { "test" };

    private String clientId = MqttAsyncClient.generateClientId();

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getTopics() {
        return topics;
    }

    public void setTopics(String[] topics) {
        this.topics = topics;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

}
