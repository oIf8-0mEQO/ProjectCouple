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
			console.log("User " + gcmUser + " attempting to partner with " + data.partner);
			var partner = App.getUserByEmail(data.partner);

			if (partner) {
				// Send request to partner
				GCM.send(partner.id, {
					type: "partner-request",
					partner: gcmUser.email
				});
			} else {
				//TODO: Reject the request
				GCM.send(partner.id, {
					type: "partner-reject",
					partner: gcmUser.email
				});
				console.error("Invalid partner: " + data.partner);
			}
		} else {
			console.error("Invalid data sent to route: " + this.id);
		}
	}
}

module.exports = PartnerRoute;