'use strict';

module.exports.http = (event, context, callback) => {
  var output = {};
  var request  = require('request');
  var URL_NAME = process.env.URL;

  console.log("Requesting " + URL_NAME);

  var requestObj = {
    "uri": URL_NAME,
    "time": true,
    "timeout": 10000, // milliseconds
  };

  request(requestObj, function (error, response, body) {
    // Debug only - goes to CloudWatch logs.
    //console.log('error:', error);
    //console.log('statusCode:', response && response.statusCode);

    // Create the stats for this request.
    if (error) {
      output = {
        "HTTPError": error.code,
        "statusCode": 0,
        "durationMS": 0,
      };
    } else {
      output = {
        "statusCode": response.statusCode,
        "durationMS": response.timingPhases.total
      };
    }

    console.log(URL_NAME + " : " + JSON.stringify(output));
    console.log("Final results:");
    console.log(JSON.stringify(output));
    callback(null, output);
  });

};
