/**
 * The bootstrap script for the app's server
 *
 * @author Henry Mao
 * @date 5/2/16.
 */
"use strict";

var GCM = require("./gcm");
var RegistrationRoute = require("./routes/RegistrationRoute");

// An associative map of all users connected
var connectedUsers = {};

GCM.registerRoute(new RegistrationRoute());