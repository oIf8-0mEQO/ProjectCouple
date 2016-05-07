/**
 * @auhor Henry Mao
 * @since 5/4/16.
 */
"use strict";

var Route = require("./route");
var App = require("../app");
var GCM = require("../gcm");

/**
 * A route that handles partner pairing registration. This is called when a client
 * attempts to pair with another partner.
 *
 * Expects:
 * {
 *    partner: "<email of partner>"
 *    accept: "<true or false>"
 * }
 *
 * Will respond the client if the partnering succeeds, or fails.
 */
class PartnerRoute extends Route {
	constructor() {
		super("partner-response");
	}

	receive(messageId, from, data) {
		var sender = App.getUserByDevice(from);
		if (sender) {
			var logMsg = "User " + sender.email + " responding to " + data.partner + " request.";
			var partner = App.getUserByEmail(data.partner);

			if (partner /*&& partner.email !== sender.email*/) {
				console.log(logMsg + " (" + data.requestAccept + ")");
				// Send request to partner
				GCM.send(partner.deviceId, {
					type: "partner-response",
					requestAccept: data.requestAccept,
					name: sender.name,
					partner: sender.email
				}, {});
			} else {
				console.log(logMsg + " (error)");
				// Reject the request
				GCM.send(sender.deviceId, {
					type: "partner-error",
					partner: sender.email,
					error: "Unable to find user."
				}, {});
				console.error("Invalid partner: " + data.partner);
			}
		} else {
			console.error("Invalid user sent to route: " + this.id);
		}
	}
}

module.exports = PartnerRoute;