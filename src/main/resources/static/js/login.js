function showEmailField() {
  // Get the form element
  let form = document.getElementById("loginForm");

  // Store the CSRF token value
  let csrfToken = document.querySelector('input[name="_csrf"]').value;

  // Clear the form
  form.innerHTML = "";
  form.action = '/useradd';

  let outterDiv = document.createElement("div");
  outterDiv.classList.add("form-group");
  let buttonDiv = document.createElement("div");
  buttonDiv.classList.add("form-group", "submit-button-div");

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

  // Add the CSRF token field back to the form
  let csrfInput = document.createElement("input");
  csrfInput.type = "hidden";
  csrfInput.name = "_csrf";
  csrfInput.value = csrfToken;
  form.appendChild(csrfInput);
}


function addEmailField() {
  let emailDiv = document.createElement("div");
  emailDiv.classList.add("subscript-div");
  let emailInput = document.createElement("input");
  emailInput.type = "email";
  emailInput.name = "email";
  emailInput.id = "email";
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
