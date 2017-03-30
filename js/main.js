(function() {
  var clientId = "js-client-" + Math.random();
  var topic    = "/6465465654654";

  var client = new Paho.MQTT.Client("broker.hivemq.com", 8000, clientId);

  // when using mosca:
  //  $ mosca -v --http-port 3000 --http-bundle --http-static ./ | bunyan
  //client = new Paho.MQTT.Client("localhost", 3000, clientId);

  // tested OK
  //client = new Paho.MQTT.Client("broker.mqttdashboard.com", 8000, clientId);
  //client = new Paho.MQTT.Client("test.mosquitto.org", 80, clientId);
  //client = new Paho.MQTT.Client("m2m.eclipse.org", 80, clientId);

  client.onConnectionLost = onConnectionLost;
  client.onMessageArrived = onMessageArrived;

  log("connecting...");
  client.connect({onSuccess: onConnect});

  function onConnect() {
    // Once a connection has been made, make a subscription and send a message.
    log("onConnect");
    client.subscribe(topic);
    var message = new Paho.MQTT.Message("Hello " + Math.random());
    message.destinationName = topic;
    client.send(message);
  }

  function onConnectionLost(responseObject) {
    if (responseObject.errorCode !== 0) {
      log("onConnectionLost: " + responseObject.errorMessage);
    }
  }

  function onMessageArrived(message) {
    var elm = document.createElement("li");
    elm.textContent = message.payloadString;
    document.getElementById("messages").appendChild(elm);
    console.log("onMessageArrived: " + message.payloadString);
  }

  function log(message) {
    var elm = document.createElement("li");
    elm.textContent = message;
    document.getElementById("log").appendChild(elm);
  }
})();
