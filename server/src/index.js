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

var deviceId = "cMAW30NTz94:APA91bH0VstK2GUfr-SKIp9GebzNaY51p27OG8qwtbrxfumRT_iaVKi3-PZnVxx0Ilt6vqQpSXUHUCgW35UT5yfGlu_x6Std8BgBz0_T36l5qqPChr-rS2Q2IMwZbKns4BGGDHs1V8CD";
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