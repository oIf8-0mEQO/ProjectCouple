/**
 * @author Henry Mao
 * @date 5/2/16.
 */

//var Notification = require('./notification');
var GCM = require('node-gcm-ccs');

var PROJECT_ID = "794558589013";
var SERVER_API_KEY = "AIzaSyCyMy2k4cpGNsqqvcilg2GyFjXQSAZ_qbE";
var CATEGORY = "group50.coupletones";

var gcm = GCM(PROJECT_ID, SERVER_API_KEY);

// An associative map with
var connectedUsers = {};


// Log connection events
gcm.on('connected', () => console.log("Server connected to GCM CCS."));
gcm.on('disconnected', () => console.log("Server disconnected from GCM CCS."));
gcm.on('online', console.log);
gcm.on('error', console.log);

/**
 * Upstream called by client to the server when the client enters a location.
 */
gcm.on('message', (messageId, from, category, data) => {
	console.log('received message', messageId);
	console.log('received from', from);
	console.log('received data', data);
	console.log('received category', category);

	if (data.email) {
		console.log(category);
		gcm.send(
			from,
			{message: 'Hey, ' + data.email},
			{delivery_receipt_requested: true},
			(err, messageId, to) => {
				if (!err) {
					console.log('sent message to', to, 'with message_id =', messageId);
				} else {
					console.log('failed to send message: ' + err);
				}
			}
		);
	}
});

