var gcm = require('node-gcm');

var SERVER_API_KEY = "AIzaSyCyMy2k4cpGNsqqvcilg2GyFjXQSAZ_qbE";

var message = new gcm.Message();
message.addData('key1', 'msg1');

// Set up the sender with you API key
var sender = new gcm.Sender(SERVER_API_KEY);

var regTokens = ['YOUR_REG_TOKEN_HERE'];

// Now the sender can be used to send messages
sender.send(message, {registrationTokens: regTokens}, function (err, response) {
	if (err) console.error(err);
	else    console.log(response);
});

// Send to a topic, with no retry this time
sender.sendNoRetry(message, {topic: '/topics/global'}, function (err, response) {
	if (err) console.error(err);
	else    console.log(response);
});