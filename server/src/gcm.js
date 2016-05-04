/**
 * Initializes the GCM instance
 *
 * @auhor Henry Mao
 * @since 5/4/16.
 */
"use strict";

var GCM = require('node-gcm-ccs');

var PROJECT_ID = "794558589013";
var SERVER_API_KEY = "AIzaSyCyMy2k4cpGNsqqvcilg2GyFjXQSAZ_qbE";
var CATEGORY = "group50.coupletones";

// The GCM instance
var gcm = GCM(PROJECT_ID, SERVER_API_KEY);

// A map of routes registered
var routes = {};

/**
 * Registers route to have GCM reroute messages based on the message type.
 */
gcm.registerRoute = (route)=> {
	routes[route.id] = route;
};

// Log connection events
gcm.on('connected', () => console.log("Server connected to GCM CCS."));
gcm.on('disconnected', () => console.log("Server disconnected from GCM CCS."));
gcm.on('online', () => console.log("Server is online."));
gcm.on('error', () => console.error("Server errored."));

/**
 * Upstream called by client to the server when the client enters a location.
 *
 * Data is expected (at minimum) to have a "type" field, which determines the type of message
 * sent by the client. The type will pick the correct route to route to.
 */
gcm.on('message', (messageId, from, category, data) => {
	// Only handle messages sent by the app
	if (category === CATEGORY) {
		// Find GCM instance
		var route = routes[data.type];

		if (route) {
			route.receive(messageId, from, data);
		} else {
			console.error("Received invalid message: " + data + " \n    from " + from)
		}
	}
});

module.exports = gcm;