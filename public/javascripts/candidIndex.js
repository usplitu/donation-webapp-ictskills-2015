$('.ui.dropdown').dropdown();

/**
 * div container for AJAX response completely disappears on page load
 * Used for giving an error if a duplicate candidate signed up
 */
$('#notificationBox').hide();

/**
 * this was necessary when I implemented AJAX as message appears when you
 * previously had an error on registering a candidate.
 * You want this message to disappear if you are entering candidate details
 * again. Also hide the div container #notificationBox. 
 */

$('.ui.form').click(function() {
  $('#notification').html("");
  $('#notificationBox').hide("slow");
});

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
  titleOfOffice : {
    identifier : 'titleOfOffice',
    rules : [ {
      type : 'empty',
      prompt : 'Please select an Office'
    } ]
  },
  donationTarget : {
    identifier : 'donationTarget',
    rules : [ {
      type : 'integer',
      prompt : 'Please select a valid Donation Target amount'
    }, {
      type : 'not[0]',
      prompt : 'You cannot select a zero target amount'
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
    submitForm();
    return false;
  }

});

function submitForm() {
  /**
   * Hit CandidateController.candidRegister. If admin tries to register a 
   * pre-existing candidate, give error message + redisplay screen.
   * If not a duplicate candidate, 'correct' is returned in JSON object and next
   * screen displayed. 
   */

  var formData = $('.ui.form.segment input').serialize();
  $.ajax({
    type : 'POST',
    url : "/candidregister",
    data : formData,
    success : function(response) {
      for ( var prop in response) {
        if (response.hasOwnProperty(prop)) {
          if (response[prop] === "correct") {
            console.log("correct registration " + response.inputdata);
            window.location.assign("/adminreport");

          } else {
            console.log("notification: " + response.inputdata);
            $('#notification').html(response.inputdata);
            $('#notification').css('color', 'red');
            $('#notificationBox').show("fast");
          }
        }
      }
    }
  });
}
