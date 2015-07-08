$('.ui.dropdown').dropdown();
$('.ui.form').form({
  candidateEmail : {
    identifier : 'candidateEmail',
    rules : [ {
      type : 'empty',
      prompt : 'Please choose a Candidate to filter Donations by'
    } ]
  }
});
