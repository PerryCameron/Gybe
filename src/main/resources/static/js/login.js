function showEmailField() {
  // Get the form element
  let form = document.getElementById("loginForm");

  // Clear the form
  form.innerHTML = "";

  let outterDiv = document.createElement("div");
  outterDiv.classList.add("form-group"); // Add the form-group class to outterDiv
  let buttonDiv = document.createElement("div");

  buttonDiv.classList.add("form-group", "submit-button-div"); // Add the submit-button-div class to buttonDiv for consistent styling

  // Create a new input field for the email

  outterDiv.appendChild(addEmailField());
  outterDiv.appendChild(buttonDiv);

  // Create a new submit button
  let submitButton = document.createElement("button");
  submitButton.type = "submit";
  submitButton.textContent = "Submit";
  buttonDiv.appendChild(submitButton);

  // Append the new fields to the form
  form.appendChild(outterDiv);
}

function addEmailField() {
  let emailDiv = document.createElement("div");
  emailDiv.classList.add("subscript-div");
  let emailInput = document.createElement("input");
  emailInput.type = "email";
  emailInput.name = "email";
  emailInput.placeholder = "Email";
  emailInput.required = true;
  emailDiv.appendChild(emailInput);
  var subscript = document.createElement("label");
  subscript.htmlFor = "main-input";
  subscript.className = "subscript";
  subscript.textContent =
    "Enter email used for account or to register for first time use any email listed in your membership";
  emailDiv.appendChild(subscript);
  return emailDiv;
}
