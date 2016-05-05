/**
 * @auhor Henry Mao
 * @since 5/4/16.
 */
"use strict";

/**
 * Represents a single user
 */
class User {
	constructor(deviceId, email) {
		this.deviceId = deviceId;
		this.email = email;
	}
}

module.exports = User;