/**
 * The bootstrap script for the app's server
 *
 * @author Henry Mao
 * @date 5/2/16.
 */
"use strict";

var GCM = require("./gcm");
var RegistrationRoute = require("./routes/registrationRoute");
var PartnerRoute = require("./routes/partnerRoute");
var PartnerResponseRoute = require("./routes/partnerResponseRoute");
var MapRoute = require("./routes/mapRoute");

GCM.registerRoute(new RegistrationRoute());
GCM.registerRoute(new PartnerRoute());
GCM.registerRoute(new PartnerResponseRoute());
GCM.registerRoute(new MapRoute());