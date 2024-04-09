document.addEventListener("DOMContentLoaded", function () {
  const password1 = document.getElementById("password1");
  const password2 = document.getElementById("password2");
  const resetPasswordButton = document.querySelector('button[type="button"]');
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
