$('.ui.checkbox').checkbox();
$('.ui.dropdown').dropdown();

$('.ui.form').form({
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
    rules : [ {
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
},

{
  onSuccess : function() {
    var coordinates = getGeolocation();
    var lat = coordinates.latitude;
    var lng = coordinates.longitude;
    alert('coordinates lat  ' + lat);
    alert('coordinates long  ' + lng);
    registerUser(lat, lng);
    return false;
  }

});

function getGeolocation() {
//If all fields are fine, get the latitude/longitude of the state and zip
  // code by using Google Geocoder
  // then pass to Accounts/register and add to User that is being created

  var myAddressQuery = document.getElementById("stateForGeo").value + " "
      + document.getElementById("zipForGeo").value;
  alert('myaddresquery' + myAddressQuery);
  var geocoder = new google.maps.Geocoder();
  
  geocoder.geocode( { 'address': myAddressQuery}, function(results, status) {
     if (status == google.maps.GeocoderStatus.OK) {
       var lat = results[0].geometry.location.lat();
       var lng = results[0].geometry.location.lng();
       alert('lat  ' + lat);
       alert('long  ' + lng);
       return {
         latitude:lat,
         longitude:lng
       }; //end of return          
     } else {
       alert("Geocode was not successful for the following reason: " + status);
     }
   });
 } //end of function getGeolocation


  function registerUser(lat, lng) {
    //var lat1 = "44.008620";
    //var lng1 = "-123.074341"; 
    var formData = $('.ui.form.segment input').serialize() + {
      lat : lat,
      lng : lng
    };
    // var formData = $('.ui.form.segment input').serialize();
    alert('about to send lat  ' + lat);
    alert('about to send long  ' + lng);
    $.ajax({
      url : "/register",
      type : 'POST',
      data : formData,
      // data: {formData, lat: lat, lng: lng },
      success : function() {
        console.log("user signed up with latitude: " + lat + " longitude: " + lng);
      }
    });//end of AJAX  

} //end of function registerUser
