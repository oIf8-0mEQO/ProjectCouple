/**
 * @author Henry Mao
 * @date 5/2/16.
 */

//var Notification = require('./notification');
var GCM = require('node-gcm-ccs');

var PROJECT_ID = "794558589013";
var SERVER_API_KEY = "AIzaSyCyMy2k4cpGNsqqvcilg2GyFjXQSAZ_qbE";

var gcm = GCM(PROJECT_ID, SERVER_API_KEY);

// Log connection events
gcm.on('connected', console.log);
gcm.on('disconnected', console.log);
gcm.on('online', console.log);
gcm.on('error', console.log);

/**
 * Upstream called by client to the server when the client enters a location.
 */
gcm.on('message', (messageId, from, category, data) => {
	console.log('received message', messageId);
	console.log('received from', from);
	console.log('received data', data);
});

var deviceId = "eQxKfJvITU4:APA91bGg6kxFf5cldPy-5gkjZTn1mZECk82wPoPnZJPvs7y3fT52J8fogs8Lt8wFN9VLk3CAYwzl8EPf8Fu3fDU7M6k3xijm_hfg7NpchenKLzk2Am6o9DwBqx-S7NB804Cdk0mGPGEL";
gcm.send(deviceId,
	{message: 'hello world'},
	{delivery_receipt_requested: true},
	(err, messageId, to) => {
		if (!err) {
			console.log('sent message to', to, 'with message_id =', messageId);
		} else {
			console.log('failed to send message: ' + err);
		}
	}
);

/*
 var n = new Notification(SERVER_API_KEY);

 var message = new gcm.Message();
 message.addData('key1', 'msg1');

 n.send([], message);
 */