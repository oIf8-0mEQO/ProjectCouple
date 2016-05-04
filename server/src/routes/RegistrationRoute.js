/**
 * @auhor Henry Mao
 * @since 5/4/16.
 */
"use strict";

var Route = require("./route");
var GCM = require("./gcm");

class RegistrationRoute extends Route {
	constructor() {
		super("registration");
	}

	receive(data) {
		if (data.email) {
			console.log(category);
			GCM.send(
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
	}
}

module.exports = RegistrationRoute;