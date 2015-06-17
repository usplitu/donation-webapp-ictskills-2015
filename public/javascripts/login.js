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
        }
      ]
    }
  })
;

