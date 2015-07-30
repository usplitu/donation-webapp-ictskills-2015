$('.ui.checkbox').checkbox();
$('.ui.dropdown').dropdown();

// hide latitude and longitude information unless user changes state or zip and
// we need to recalc
$('#notificationBox').hide();

// go back 1 page in history list when cancel button clicked
function goBack() {
  window.history.go(-1);
  return true;
}

// lat field will be set to ? if user changes state/zip and hits Submit and
// doesn't recalc
// Latitude and Longitude first
$('.ui.form')
    .form(
        {
          lat : {
            identifier : 'lat',
            rules : [ {
              type : 'not[?]',
              prompt : 'Latitude/Longitude invalid; ensure you have a valid state/zip + click Get latitude/longitude before Submit'
            } ]
          },
          age : {
            identifier : 'age',
            rules : [ {
              type : 'not[0]',
              prompt : 'You cannot select a zero age'
            } ]
          }
        });
