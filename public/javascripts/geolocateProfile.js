/**
 * Get the latitude/longitude of the state and zip
 * code by using Google Geocoder, then pass back to 
 * screen, making lat and lng coordinates appear #latlng
 *  A User will then be amended with these
 */

function getGeolocation() {
  var myAddressQuery = document.getElementById("stateForGeo").value + " "
      + document.getElementById("zipForGeo").value;
  var geocoder = new google.maps.Geocoder();

  geocoder
      .geocode(
          {
            'address' : myAddressQuery
          },
          function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
              document.getElementById("latlnginfo").innerHTML = "Latitude/Longitude successfully found - please click Submit";
              document.getElementById("latitude").value = results[0].geometry.location
                  .lat();
              document.getElementById("longitude").value = results[0].geometry.location
                  .lng();
              $('#latlng').show("fast");
              /**
               * only show latitude/longitude if found as user cannot then click
               * submit and amend records invalidly
               */
            } else {
              document.getElementById("latlnginfo").innerHTML = "Error "
                  + status
                  + " in getting Latitude/Longitude - please enter a valid state/zip combination";
              document.getElementById("latitude").value = "?";
            }
          });
} // end of function getGeolocation

/**
 * if a user changes the state or zip, we need to reset the Lat and Lng elements
 * to ? and the instructions back to the original for them. This is needed as
 * what if a valid Lat and Lng were returned and the User subsequently changed
 * the zip or state? When they press 'Submit', a User record would be amended
 * with incorrect coordinates. This change will now force them to recalc the
 * coordinates.
 */
function resetLatLng() {
  // if either state/zip have been changed, ensure both have values
  $('#notificationBox').hide();

  if (document.getElementById("stateForGeo").value === null
      || document.getElementById("stateForGeo").value === ""
      || document.getElementById("zipForGeo").value === null
      || document.getElementById("zipForGeo").value === "") {
    document.getElementById("latlnginfo").innerHTML = "Both State and Zip code must be changed to be in sync - you've just changed one."
    document.getElementById("latitude").value = "?"; //expected for profileindex.js as test condition
    document.getElementById("longitude").value = "?"; //expected for profileindex.js as test condition
    $('#notificationBox').show();
    $('#latlng').hide();
    $('#notButton').hide();

  } else {
    document.getElementById("latitude").value = "?"; //expected for profileindex.js as test condition
    document.getElementById("longitude").value = "?"; //expected for profileindex.js as test condition
    document.getElementById("latlnginfo").innerHTML = "You've changed the state and zip. Please click the 'Get Latitude/Longitude' button below to get the location of your address before hitting 'Submit'."
    $('#notificationBox').show();
    $('#latlng').show();
    $('#notButton').show();
  }
} // end of function resetLatLng

/**
 * if a user changes the lat or lng, we need to reset them
 * to spaces and the instructions back to the original for them. This is 
 * needed as what if a valid Lat and Lng were returned and the user 
 * subsequently changed them? When they press 'Submit',
 * a User would be amended with incorrect coordinates.
 * This change will now force them to recalc the coordinates.  
 * Same as resetLatLng but different error message.
 * This is only due to the obstacle of not being able to check
 * lat and lng in profileindex.js if we make the fields disabled in html
 * and we need to check them in case of a Geocode error or a 
 * User hitting 'Submit' and ignoring the instructions to calculate
 * Lat/Lng first. 
 */
function userChangedCoords() {
  $('#latlng').hide();
  document.getElementById("latitude").value = "?";
  document.getElementById("latlnginfo").innerHTML = "You have modified the coordinates. Please click the 'Get Latitude/Longitude' button below to get the location of your address before hitting 'Signup'."
} //end of function userChangedCoords