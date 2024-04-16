document.addEventListener("DOMContentLoaded", function () {
  const password1 = document.getElementById("password1");
  const password2 = document.getElementById("password2");
  const resetPasswordButton = document.querySelector('form button[type="submit"]');
  const errorMessage = document.querySelector(".error");

  // Initially disable the button and hide the error message
  resetPasswordButton.disabled = true;
  errorMessage.style.display = "none";

  // Function to check if passwords match
  function checkPasswords() {
    if (password1.value && password2.value) {
      // Check if both fields have values
      if (password1.value === password2.value) {
        resetPasswordButton.disabled = false; // Enable the button
        errorMessage.style.display = "none"; // Hide error message
      } else {
        resetPasswordButton.disabled = true; // Disable the button
        errorMessage.style.display = "block"; // Show error message
      }
    } else {
      resetPasswordButton.disabled = true; // Disable the button if one of the fields is empty
      errorMessage.style.display = "none"; // Hide error message
    }
  }

  // Add event listeners to both password fields
  password1.addEventListener("input", checkPasswords);
  password2.addEventListener("input", checkPasswords);
});

document.addEventListener('DOMContentLoaded', function() {
  document.getElementById('updatePasswordForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the default form submission

    const csrfToken = document.querySelector('input[name="_csrf"]').value; // Fetch the CSRF token from a hidden input

    let data = {
      key: document.querySelector('input[name="key"]').value,
      status: document.querySelector('input[name="status"]').value,
      email: document.querySelector('input[name="email"]').value,
      password1: document.getElementById('password1').value,
      password2: document.getElementById('password2').value
    };

    fetch('/update_password', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'X-CSRF-TOKEN': csrfToken // Make sure the header name matches what Spring Security expects
      },
      body: JSON.stringify(data)
    })
        .then(response => {
          if (!response.ok) {
            throw new Error('Server responded with status ' + response.status);
          }
          return response.json();
        })
        .then(data => {
          console.log('Success:', data);
          alert('Success: ' + data.message);
        })
        .catch((error) => {
          console.error('Error:', error);
          alert('Error: ' + error.message);
        });
  });
});

