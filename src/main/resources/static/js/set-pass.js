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

document.getElementById('submitBtn').addEventListener('click', function() {
  const csrfToken = document.getElementById('csrfToken').value;
  let data = {
    _csrf: csrfToken,
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
      'CSRF-Token': csrfToken // Ensure CSRF token header is set if needed
    },
    body: JSON.stringify(data)
  })
      .then(response => response.json())
      .then(data => {
        if (!response.ok) {
          throw new Error(data.message); // Use the server's error message
        }
        console.log('Success:', data);
        alert('Success: ' + data.message); // Show success message
      })
      .catch((error) => {
        console.error('Error:', error);
        alert('Failure: ' + error.message); // Show error message
      });
});
