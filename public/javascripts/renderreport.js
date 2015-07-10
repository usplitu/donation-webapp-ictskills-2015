$('.ui.dropdown').dropdown();
$('.ui.form').form({
  candidateEmail : {
    identifier : 'candidateEmail',
    rules : [ {
      type : 'empty',
      prompt : 'Please choose a Candidate to filter Donations by'
    } ]
  },
  donorEmail : {
    identifier : 'donorEmail',
    rules : [ {
      type : 'empty',
      prompt : 'Please choose a Donor to filter Donations by'
    } ]
  },
  state : {
    identifier : 'state',
    rules : [ {
      type : 'empty',
      prompt : 'Please choose a State to filter Donations by'
    } ]
  }
});
