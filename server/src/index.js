/**
 * The bootstrap script for the app's server
 *
 * @author Henry Mao
 * @date 5/2/16.
 */
"use strict";

var GCM = require("./gcm");
var RegistrationRoute = require("./routes/registrationRoute");
var MapRoute = require("./routes/mapRoute");

GCM.registerRoute(new RegistrationRoute());
GCM.registerRoute(new MapRoute());