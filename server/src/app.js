/**
 * @auhor Henry Mao
 * @since 5/4/16.
 */
"use strict";

/**
 * Global app related variables and general functions
 */
class App {
	constructor() {
		// An associative map of all users connected
		this.gcmUsers = {};
	}

	/**
	 * Gets a user by the user's email
	 * @param email The email of the user
	 */
	getUserByEmail(email) {
		//TODO: Implement
	}
}

module.exports = new App();