/**
 * @auhor Henry Mao
 * @since 5/4/16.
 */
"use strict";

/**
 * Represents a single user
 */
class User {
	constructor(deviceId, id, name, email) {
		this.deviceId = deviceId;
		this.id = id;
		this.name = name;
		this.email = email;
	}
}

module.exports = User;