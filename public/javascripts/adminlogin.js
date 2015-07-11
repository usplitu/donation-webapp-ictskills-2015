// div container for AJAX response completely disappears
$('#notificationBox').hide();

// this was necessary when I implemented AJAX as message appears when you
// previously had a bad login
// You want this message to disappear when you are entering login details again
// also hide the div container #notificationBox
$('.ui.form').click(function() {
  $('#notification').html("");
  $('#notificationBox').hide("slow");
});

$('.ui.form').form({
  username : {
    identifier : 'username',
    rules : [ {
      type : 'empty',
      prompt : 'Please enter your username'
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
    submitForm();
    return false;
  }

});

function submitForm() {
  // Hit Administrator.authenticate. If login details don't match database, give
  // error message + redisplay screen
  // If details match database, 'correct' is returned in JSON object and next
  // screen displayed.
  var formData = $('.ui.form.segment input').serialize();
  $.ajax({
    type : 'POST',
    url : "/adminauthent",
    data : formData,
    success : function(response) {
      for ( var prop in response) {
        if (response.hasOwnProperty(prop)) {
          if (response[prop] === "correct") {
            console.log("correct log in " + response.inputdata);
            window.location.assign("/adminreport");

          } else {
            console.log("notification: " + response.inputdata);
            $('#notification').html(response.inputdata);
            $('#notificationBox').show("fast");
          }
        }
      }
    }
  });
}
