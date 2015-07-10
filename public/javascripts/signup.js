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
});
