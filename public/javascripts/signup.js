$('.ui.checkbox').checkbox();
$('.ui.dropdown').dropdown();

// hide input fields for latitude/longitude. They will be filled by the user
// clicking the 'Get Latitude/Longitude'
// button. They will only be empty if the button has not been pressed before the
// 'Signup' button so can use as a test
// here to see if the User did that. Also, they will contain '?' if Google
// Geolocate cannot find the lat/lng so
// cannot proceed to create a User with an invalid geolocation.
// They will appear when a successful geolocation has been found so will be
// feedback to the User as they will notice this more if it hasn't been on
// screen before.
// Note: Only need to check Lat field as superfluous to check both

$('#latlng').hide();

$('.ui.form')
    .form(
        {
          firstName : {
            identifier : 'firstName',
            rules : [ {
              type : 'empty',
              prompt : 'Please enter your first name'
            } ]
          },
          lastName : {
            identifier : 'lastName',
            rules : [ {
              type : 'empty',
              prompt : 'Please enter your last name'
            } ]
          },
          lat : {
            identifier : 'lat',
            rules : [
                {
                  type : 'not[?]',
                  prompt : 'Latitude/Longitude invalid; please enter valid state/zip + click Get latitude/longitude'
                },
                {
                  type : 'empty',
                  prompt : 'Please click on the Get Latitude/Longitude button before hitting Signup'
                } ]
          },
          addr1 : {
            identifier : 'addr1',
            rules : [ {
              type : 'empty',
              prompt : 'Please enter address line 1'
            } ]
          },
          addr2 : {
            identifier : 'addr2',
            rules : [ {
              type : 'empty',
              prompt : 'Please enter address line 2'
            } ]
          },
          city : {
            identifier : 'city',
            rules : [ {
              type : 'empty',
              prompt : 'Please enter a city'
            } ]
          },
          zip : {
            identifier : 'zip',
            rules : [ {
              type : 'empty',
              prompt : 'Please enter a zip code'
            }, {
              type : 'length[5]',
              prompt : 'Your zip must be at least 5 characters long'
            } ]
          },
          state : {
            identifier : 'state',
            rules : [ {
              type : 'empty',
              prompt : 'Please select a state'
            } ]
          },
          age : {
            identifier : 'age',
            rules : [ {
              type : 'not[0]',
              prompt : 'You cannot select a zero age'
            }, {
              type : 'integer',
              prompt : 'Please enter a valid age'
            } ]
          },
          email : {
            identifier : 'email',
            rules : [
                {
                  type : 'contains[@]',
                  prompt : 'Your email address must contain an @  - address not valid'
                }, {
                  type : 'empty',
                  prompt : 'Please enter a valid email'
                } ]
          },
          password : {
            identifier : 'password',
            rules : [ {
              type : 'empty',
              prompt : 'Please enter your password'
            }, {
              type : 'length[6]',
              prompt : 'Your password must be at least 6 characters'
            } ]
          }
        });