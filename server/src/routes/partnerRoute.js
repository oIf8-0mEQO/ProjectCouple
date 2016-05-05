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
 * }
 *
 * Will respond the client if the partnering succeeds, or fails.
 */
class PartnerRoute extends Route {
	constructor() {
		super("partner");
	}

	receive(messageId, from, data) {
		var gcmUser = App.getUserByDevice(from);
		if (gcmUser) {
			var logMsg = "User " + gcmUser.email + " attempting to partner with " + data.partner;
			var partner = App.getUserByEmail(data.partner);

			//TODO: Check to reject self-partner
			if (partner) {
				console.log(logMsg + " (accept)");
				// Send request to partner
				GCM.send(partner.deviceId, {
					type: "partner-request",
					partner: gcmUser.email
				}, {});
			} else {
				console.log(logMsg + " (reject)");
				//TODO: Reject the request
				GCM.send(gcmUser.deviceId, {
					type: "partner-reject",
					partner: gcmUser.email,
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