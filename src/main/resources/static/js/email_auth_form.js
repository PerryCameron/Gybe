document.getElementById('form-email-submit').addEventListener('submit', function(event) {
    event.preventDefault();

    var emailInput = document.getElementById('email');
    var emailHelpDiv = document.getElementById('email-help-div');
    var email = emailInput.value;
    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (emailRegex.test(email)) {
        emailHelpDiv.textContent = 'Email looks good!';
        emailHelpDiv.style.color = 'black';
        this.submit(); // Submit the form
    } else {
        emailHelpDiv.textContent = 'Please enter a valid email address.';
        emailHelpDiv.style.color = 'red';
    }
});


