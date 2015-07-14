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
});