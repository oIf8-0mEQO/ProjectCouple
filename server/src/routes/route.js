/**
 * @auhor Henry Mao
 * @since 5/4/16.
 */
"use strict";
/**
 * A route is an abstract class that defines a way to handle a type of
 * message received on the server side sent upstream from the client.
 */
class Router {
	/**
	 * @param id The id of the route
	 */
	constructor(id) {
		this.id = id;
	}

	/**
	 * Handles when the route is called by the client
	 * @param messageId ID of the message
	 * @param from The ID of the user who sent this message
	 * @param data A JSON representing the data the client sent.
	 */
	receive(messageId, from, data) {
		throw "Receive not implemented in: " + this.id;
	}
}

module.exports = Router;