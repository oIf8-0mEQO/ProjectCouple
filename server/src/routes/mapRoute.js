/**
 * @auhor Henry Mao
 * @since 5/4/16.
 */
"use strict";

var Route = require("./route");
var App = require("../app");
var GCM = require("../gcm");

/**
 * A route that handles partner enter favorite location requests.
 *
 * Expects:
 * {
 *    location: {
 *      name: "<name of location>",
 *      time: <time>,
 *      lat: <latitude>,
 *      long: <longitude>,
 *    }
 *    partner: "<email of partner>"
 * }
 *
 * Will respond the client if the partnering succeeds, or fails.
 */
class MapRoute extends Route {
	constructor() {
		super("map");
	}

	receive(messageId, from, data) {
		var gcmUser = App.getUserByDevice(from);
		if (gcmUser) {
			console.log("User " + gcmUser + " attempting to notify " + data.partner);
			var partner = App.getUserByEmail(data.partner);

			if (partner) {
				// Send request to partner
				GCM.send(partner.id, {
					type: "map-notify",
					location: data.location,
					partner: gcmUser.email
				});
			} else {
				//TODO: Reject the request
				GCM.send(partner.id, {
					type: "map-reject",
					partner: gcmUser.email
				});
				console.error("Invalid partner: " + data.partner);
			}
		} else {
			console.error("Invalid data sent to route: " + this.id);
		}
	}
}

module.exports = MapRoute;