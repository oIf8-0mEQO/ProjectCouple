/**
 * @auhor Henry Mao
 * @since 5/4/16.
 */
"use strict";
var _ = require("lodash");

/**
 * Global app related variables and general functions
 */
class App {
	constructor() {
		// An associative map of all users connected
		this.gcmUsers = {};
	}

	/**
	 * Adds a user to the map of GCM users
	 * @param {User} user
	 */
	addUser(user) {
		this.gcmUsers[user.deviceId] = user;
	}

	/**
	 * Gets a user by device Id
	 * @param deviceId The GCM device id
	 * @returns {User} The user instance, or null/undefined if invalid
	 */
	getUserByDevice(deviceId) {
		return this.gcmUsers[deviceId];
	}

	/**
	 * Gets a user by the user's email
	 * @param email The email of the user
	 * @returns {User} The user instance, or null if invalid
	 */
	getUserByEmail(email) {
		var result = null;

		_.forOwn(this.gcmUsers, (value, key) => {
			if (value.email === email)
				result = value;
		});

		return result;
	}
}

module.exports = new App();