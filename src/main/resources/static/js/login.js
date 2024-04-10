function buildEmailView() {
    const form = document.getElementById("loginForm");
    // Store the CSRF token value
    const csrfToken = document.querySelector('input[name="_csrf"]').value;
    // Clear the form
    form.innerHTML = "";
    form.action = '/upsert_user';
    const div = document.createElement("div");
    div.classList.add("form-group");
    div.appendChild(addSubScript());
    div.appendChild(addEmailFieldAndButton());
    form.appendChild(div);
    // Add the CSRF token field back to the form
    const csrfInput = document.createElement("input");
    csrfInput.type = "hidden";
    csrfInput.name = "_csrf";
    csrfInput.value = csrfToken;
    form.appendChild(csrfInput);
}

function addEmailFieldAndButton() {
    const div = document.createElement("div");
    div.classList.add("email-div");
    div.appendChild(addEmailField());
    div.appendChild(addButton());
    return div;
}

function addButton() {
    const div = document.createElement("div");
    div.classList.add("form-group", "submit-button-div");
    const submitButton = document.createElement("button");
    submitButton.type = "submit";
    submitButton.textContent = "Submit";
    div.appendChild(submitButton);
    return div;
}

function addEmailField() {
    const div = document.createElement("div");
    div.classList.add("email-div");
    const input = document.createElement("input");
    input.type = "email";
    input.name = "email";
    input.placeholder = "Email";
    input.required = true;
    div.appendChild(input);
    return div;
}

function addSubScript() {
    let div = document.createElement("div");
    div.classList.add("subscript-div");
    const label = document.createElement("label");
    // label.htmlFor = "main-input";
    label.className = "subscript";
    label.textContent =
        "Enter email used for account or to register for first time use any email listed in your membership";
    div.appendChild(label);
    return div;
}
