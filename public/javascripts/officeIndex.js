/**
 * div container for AJAX response completely disappears on page load
 * Used for giving an error if a duplicate office registered
 */
$('#notificationBox').hide();

/**
 * this was necessary when I implemented AJAX as message appears when you
 * previously had an error on registering an office.
 * You want this message to disappear if you are entering office details
 * again. Also hide the div container #notificationBox
 */
$('.ui.form').click(function() {
  $('#notification').html("");
  $('#notificationBox').hide("slow");
});

$('.ui.form').form({
  officeTitle : {
    identifier : 'officeTitle',
    rules : [ {
      type : 'empty',
      prompt : 'Please enter an Office title'
    } ]
  },
  officeDescript : {
    identifier : 'officeDescript',
    rules : [ {
      type : 'empty',
      prompt : 'Please enter an Office description'
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
   * Hit OfficeController.officeRegister. If admin tries to register a pre-existing office, give
   * error message + redisplay screen. 
   * If not a duplicate office, 'correct' is returned in JSON object and next
   * screen displayed.
   */
  var formData = $('.ui.form.segment input').serialize();
  $.ajax({
    type : 'POST',
    url : "/officeregister",
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
