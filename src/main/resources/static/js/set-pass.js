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

document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('updatePasswordForm').addEventListener('submit', function (event) {
        event.preventDefault(); // Prevent the default form submission

        const csrfToken = document.querySelector('input[name="_csrf"]').value; // Fetch the CSRF token from a hidden input

        let data = {
            key: document.querySelector('input[name="key"]').value,
            status: document.querySelector('input[name="status"]').value,
            email: document.querySelector('input[name="email"]').value,
            password1: document.getElementById('password1').value,
            password2: document.getElementById('password2').value
        };
        // console.log('Data Origin:', data);

        console.log('Key:', data.key);
        console.log('Status:', data.status);
        console.log('Email:', data.email);
        console.log('Password1:', data.password1);
        console.log('Password2:', data.password2);

        fetch('/update_password', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': csrfToken // Correct handling of the CSRF token
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (!response.ok) {
                    // Instead of throwing directly here, parse and then decide
                    return response.json().then(errData => {
                        // Construct a more informative error message or use server-sent error directly
                        throw new Error(errData.message || 'Something went wrong with the request.');
                    }).catch(() => {
                        // This catches any errors in parsing JSON in case the response isn't JSON-formatted
                        throw new Error('Failed to parse error response.');  // getting this!!
                    });
                }
                return response.json();
            })
            .then(messagedata => {
                console.log('Success:', messagedata);  // temp
                console.log('Data message: ' + messagedata.message); // temp
                console.log('Data success: ' + messagedata.success); // temp
                if (messagedata.success === true)
                    createNewLogin(data);
                setNewMessage(messagedata.message);
            })
            .catch((error) => {  // still broken
                console.error('Error:', error);
                setNewMessage(error.message); // Make sure this is the error.message
            });


        function setNewMessage(message) {
            let p = document.getElementById("existingMessage");
            p.innerText = "";
            p.innerText = message;
        }

        function createNewLogin(data) {
            // Retrieve the CSRF token from the old form before removing it
            const csrfToken = document.querySelector('input[name="_csrf"]').value;
            console.log('Data in createNewLogin:', data); // the data prints here correctly so it is getting here
            const displayedName = document.getElementById('displayedName');
            const container = document.getElementById('formContainer'); // Ensure this container exists in your HTML
            if (!container) console.error('Container element not found');


            // Clear existing content
            container.innerHTML = '';
            displayedName.innerHTML = '';

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
            usernameInput.value = data.email; // this is undefined
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
            console.log("password1: " + data.password1) // this is undefined, how?
            console.log("Data inside needed part: " + data);  // no object found
            passwordInput.value = data.password1; // this is undefined
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
    })
});