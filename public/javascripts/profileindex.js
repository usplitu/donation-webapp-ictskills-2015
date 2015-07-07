$('.ui.checkbox').checkbox();
$('.ui.dropdown').dropdown();

//go back 1 page in history list when cancel button clicked
function goBack() {
  window.history.go(-1);
  return true;
}

$('.ui.form').form({
  age : {
    identifier : 'age',
    rules : [ {
      type : 'not[0]',
      prompt : 'You cannot select a zero age'
    } ]
  }
});
