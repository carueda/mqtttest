package mqtttest;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Main {
  
  public static void main(String[] args) throws MqttException {
    
    String broker       = "tcp://broker.hivemq.com:1883";
    //String broker       = "tcp://localhost:1883";
    //String broker       = "tcp://broker.mqttdashboard.com:1883";
    //String broker       = "tcp://m2m.eclipse.org:1883";
    //String broker       = "tcp://test.mosquitto.org:1883";
    //String broker       = "tcp://iot.eclipse.org:1883";
    
    String clientId  = "java-client";
    int qos          = 2;
    String topic     = "/6465465654654";
    
    MemoryPersistence persistence = new MemoryPersistence();
    
    MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
    MqttConnectOptions connOpts = new MqttConnectOptions();
    connOpts.setCleanSession(true);
    System.out.println("connecting to broker: "+broker);
    sampleClient.connect(connOpts);
    for (int i = 1; i <= 5; i++) {
      String content   = "Message(" +i+ ") " +Math.random()+ " from " + clientId;
      System.out.println("  publishing message: " +content);
      MqttMessage message = new MqttMessage(content.getBytes());
      message.setQos(qos);
      sampleClient.publish(topic, message);
    }
    sampleClient.disconnect();
    System.out.println("disconnected");
  }
}
