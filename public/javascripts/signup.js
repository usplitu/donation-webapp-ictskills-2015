$('.ui.checkbox').checkbox();
$('.ui.dropdown').dropdown();

// had to create input fields for latitude/longitude so the form serialize command would send them
//to the Accounts/register method. But hide them on screen in a div when it loads as don't want the user to see them
$('#latlng').hide();

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
    getGeolocation();      
    return false;
  }

});

function getGeolocation() {
//If all fields are fine, get the latitude/longitude of the state and zip
  // code by using Google Geocoder
  // then pass to Accounts/register and add to User that is being created

  var myAddressQuery = document.getElementById("stateForGeo").value + " "
      + document.getElementById("zipForGeo").value;  
  var geocoder = new google.maps.Geocoder();
  
  geocoder.geocode( { 'address': myAddressQuery}, function(results, status) {
     if (status == google.maps.GeocoderStatus.OK) {
       document.getElementById("latitude").value = results[0].geometry.location.lat();
       document.getElementById("longitude").value = results[0].geometry.location.lng();
       //var lat = results[0].geometry.location.lat();
       //var lng = results[0].geometry.location.lng();
       //document.getElementById("latitude").value = lat;       
       //document.getElementById("longitude").value = lng;      
       registerUser(); 
     } else {
       alert("Geocode was not successful for the following reason: " + status);
     }
   });
 } //end of function getGeolocation


  function registerUser() {    
    var formData = $('.ui.form.segment input').serialize();
    
    $.ajax({
      url : "/register",
      type : 'POST',
      data : formData,     
      success : function() {
        window.location.assign("/login");
        console.log("user signed up");
      }
    });//end of AJAX  

} //end of function registerUser
