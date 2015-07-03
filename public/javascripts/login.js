// div container for AJAX response completely disappears
$('#errorBox').hide();

//this was necessary when I implemented AJAX as message appears when you previously had a bad login
//You want this message to disappear when you are entering login details again
//also hide the div container #errorBox
$('.ui.form').click(function(){
	$('#errorJson').html("");
	$('#errorBox').hide("slow");
});



$('.ui.form')
.form({
  email: {
    identifier  : 'email',
    rules: [
      {
        type   : 'contains[@]',
        prompt : 'Your email address must contain an @  - address not valid'
      },        
      {
        type   : 'empty',
        prompt : 'Please enter your email address'
      }
    ]
  },
  password: {
      identifier : 'password',
      rules: [
        {
          type   : 'empty',
          prompt : 'Please enter your password'
        },
        {
            type   : 'length[6]',
            prompt : 'Your password must be at least 6 characters'
        }
      ]
   }
 });




