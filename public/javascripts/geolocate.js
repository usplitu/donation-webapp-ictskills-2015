//Get the latitude/longitude of the state and zip
// code by using Google Geocoder
// then pass back to screen, making lat and lng coordinates appear #latlng
// They will then be passed to Accounts/register and added to User that is being created
// Only lat is checked in signup.html as having ? for a geocode error - superfluous to check both lat and lng


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
              document.getElementById("latlnginfo").innerHTML = "Latitude/Longitude successfully found - please click Signup";
              document.getElementById("latitude").value = results[0].geometry.location
                  .lat();
              document.getElementById("longitude").value = results[0].geometry.location
                  .lng();
              $('#latlng').show("fast");
              // only show latitude/longitude if found as user cannot then click
              // signup and create invalid User
            } else {
              document.getElementById("latlnginfo").innerHTML = "Error "
                  + status
                  + " in getting Latitude/Longitude - please enter a valid state/zip combination";
              document.getElementById("latitude").value = "?";
            }
          });
} // end of function getGeolocation

// if a user changes the state or zip, we need to reset the Lat and Lng elements
// to spaces and the
// instructions back to the original for them. This is needed as what if a valid
// Lat and Lng were returned
// and the user subsequently changed the zip or state? When they press 'Signup',
// a User would be created with
// incorrect coordinates. This change will now force them to recalc the
// coordinates.
function resetLatLng() {
  $('#latlng').hide();
  document.getElementById("latitude").value = "";
  document.getElementById("latlnginfo").innerHTML = "You've edited state/zip. Please click the 'Get Latitude/Longitude' button below to get the location of your address before hitting 'Signup'."
} //end of function resetLatLng

//if a user changes the lat or lng, we need to reset them
//to spaces and the
//instructions back to the original for them. This is needed as what if a valid
//Lat and Lng were returned
//and the user subsequently changed them? When they press 'Signup',
//a User would be created with
//incorrect coordinates. This change will now force them to recalc the
//coordinates. Same as resetLatLng but different error message
function userChangedCoords() {
$('#latlng').hide();
document.getElementById("latitude").value = "";
document.getElementById("latlnginfo").innerHTML = "You have modified the coordinates. Please click the 'Get Latitude/Longitude' button below to get the location of your address before hitting 'Signup'."
} //end of function userChangedCoords

