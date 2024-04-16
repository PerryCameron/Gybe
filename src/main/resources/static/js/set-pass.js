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
    console.log('Data Origin:', data);

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
            // If the response is not ok, parse it as JSON still to get the error message
            return response.json().then(errData => {
              // Throw an error that includes the JSON data
              throw new Error(errData.message || 'Something went wrong');
            });          }
          return response.json();
        })
        .then(data => {
          console.log('Success:', data);
          createNewLogin(data);
        })
        .catch((error) => {
          console.error('Error:', error);
          setNewMessage(error);
        });
  });
});

function createNewLogin(data) {
  // Retrieve the CSRF token from the old form before removing it
  const csrfToken = document.querySelector('input[name="_csrf"]').value;
  console.log('Data received:', data);

  const container = document.getElementById('formContainer'); // Ensure this container exists in your HTML
  if (!container) {
    console.error('Container element not found');
    return;
  }

  // Clear existing content
  container.innerHTML = '';

  // Create the form
  const form = document.createElement('form');
  form.id = 'loginForm';
  form.action = '/login';
  form.method = 'post';

  // Username input
  const usernameDiv = document.createElement('div');
  usernameDiv.className = 'form-group';
  const usernameInput = document.createElement('input');
  usernameInput.type = 'text';
  usernameInput.id = 'username';
  usernameInput.name = 'username';
  usernameInput.placeholder = 'Username';
  usernameInput.autocomplete = 'username';
  usernameInput.value = data.email;
  usernameDiv.appendChild(usernameInput);

  // Password input
  const passwordDiv = document.createElement('div');
  passwordDiv.className = 'form-group';
  const passwordInput = document.createElement('input');
  passwordInput.type = 'password';
  passwordInput.id = 'password';
  passwordInput.name = 'password';
  passwordInput.placeholder = 'Password';
  passwordInput.autocomplete = 'current-password';
  passwordInput.value = data.password1;
  passwordDiv.appendChild(passwordInput);

  // CSRF Token
  const csrfInput = document.createElement('input');
  csrfInput.type = 'hidden';
  csrfInput.name = '_csrf';
  csrfInput.value = csrfToken;

  // Submit button
  const buttonDiv = document.createElement('div');
  buttonDiv.className = 'form-group submit-button-div';
  const submitButton = document.createElement('button');
  submitButton.type = 'submit';
  submitButton.textContent = 'Login';
  buttonDiv.appendChild(submitButton);

  // Error message div
  const errorDiv = document.createElement('div');
  errorDiv.className = 'error';
  errorDiv.textContent = 'Invalid username or password.';
  errorDiv.style.display = 'none'; // Initially hidden

  // Append all to form
  form.appendChild(usernameDiv);
  form.appendChild(passwordDiv);
  form.appendChild(csrfInput);
  form.appendChild(buttonDiv);
  form.appendChild(errorDiv);

  // Append form to container
  container.appendChild(form);
}

function setNewMessage(status, error) {
    let p = document.getElementById("existingMessage");
    p.innerText = "";
    p.innerText = error.message;
}
