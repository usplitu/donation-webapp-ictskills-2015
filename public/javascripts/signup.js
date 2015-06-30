$('.ui.checkbox').checkbox();
$('.ui.form')
.form({
  firstName: {
    identifier  : 'firstName',
    rules: [
      {
        type   : 'empty',
        prompt : 'Please enter your first name'
      }
    ]
  },
  lastName: {
      identifier : 'lastName',
      rules: [
        {
          type   : 'empty',
          prompt : 'Please enter your last name'
        }
      ]
    },
  email: {
      identifier : 'email',
      rules: [
        {
          type   : 'contains[@]',
          prompt : 'Your email address must contain an @  - address not valid'
        },      
        {
          type   : 'empty',
          prompt : 'Please enter a valid email'
        }
      ]
    },    
  password: {
      identifier : 'password',
      rules: [
        {
          type   : 'empty',
          prompt : 'Please enter your password'
        }
      ]
    }
  })
;

