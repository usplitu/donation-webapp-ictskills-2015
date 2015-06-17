$('.ui.dropdown')
  .dropdown()
;

$('.ui.form')
.form({

  amountDonated: {
    identifier: 'amountDonated',
    rules: [{
        type: 'empty',
        prompt: 'Please select an amount to donate'
      }]
  }
});