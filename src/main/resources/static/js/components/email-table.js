class EmailTable {
    constructor(container, person) {
        this.mainContainer = container;
        this.tableContainer = document.createElement("div");
        this.tableContainer.classList.add("table-container");
        this.headers = {
            0: {email: "Email", widget: "text"},
            1: {primaryUse: "Primary", widget: "radio"},
            2: {emailListed: "Listed", widget: "check"},
        };
        this.person = person || [];// Table data for each email
        this.person.emails = Array.isArray(this.person.emails) ? this.person.emails : [];
        // I need to also make sure person.emails is not null but an empty set
        this.modifiedRows = new Set();
        this.table = document.createElement("table"); // The email table itself
        this.selectedRow = null; // To track the highlighted row
        this.mainContainer.appendChild(this.tableContainer);
        this.renderTable();
        this.renderButtons();
    }

    renderTable() {
        this.table.innerHTML = ""; // Clear previous content
        // Create the header row
        const headerRow = document.createElement("tr");
        Object.values(this.headers).forEach((header) => {
            const th = document.createElement("th");
            th.textContent = header.email || header.primaryUse || header.emailListed;
            headerRow.appendChild(th);
        });
        this.table.addEventListener('mouseleave', () => this.batchUpdate());
        this.table.appendChild(headerRow);
        // Create data rows
        this.person.emails.forEach((rowData, index) => {
            const row = this.createDataRow(rowData, index);
            this.table.appendChild(row);
        });
        // Append the table to the main container if it's not already there
        if (!this.tableContainer.contains(this.table)) {
            // changed
            this.tableContainer.appendChild(this.table); // changed
        }
        // Only append the tableContainer once to the body
        if (!document.body.contains(this.mainContainer)) {
            // changed
            document.body.appendChild(this.mainContainer); // changed
        }
    }

    createDataRow(rowData, index) {
        const row = document.createElement("tr");
        // Set data attributes for the row using rowData properties
        row.dataset.emailId = rowData.emailId;
        row.dataset.pId = rowData.pId;
        // Email column
        const emailCell = document.createElement("td");
        const emailText = document.createElement("span");
        // Display placeholder if email is empty
        emailText.textContent = rowData.email || "Click to add email";
        emailText.classList.add(rowData.email ? "filled-email" : "placeholder");
        // emailText.classList.add("email-text-field");
        emailText.addEventListener("click", () => {
            this.convertToTextInput(emailText, rowData, "email");
            this.modifiedRows.add(rowData.emailId);
        });
        emailCell.appendChild(emailText);
        row.appendChild(emailCell);
        // Primary Use column as a radio button
        const primaryUseCell = document.createElement("td");
        const radio = document.createElement("input");
        radio.type = "radio";
        radio.name = "primaryUse";
        radio.classList.add("primary-use-radio");
        radio.checked = rowData.primaryUse === 1;
        radio.addEventListener("click", () => {
            this.updatePrimaryUse(index);
        });
        primaryUseCell.appendChild(radio);
        row.appendChild(primaryUseCell);
        // Email Listed column as a checkbox
        const listedCell = document.createElement("td");
        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.classList.add("listed-check-box");
        checkbox.checked = rowData.emailListed === 1;
        checkbox.addEventListener("change", () => {
            rowData.emailListed = checkbox.checked ? 1 : 0;
            this.modifiedRows.add(rowData.emailId);
        });
        listedCell.appendChild(checkbox);
        row.appendChild(listedCell);
        // Highlight row on click for deletion
        row.addEventListener("click", () => this.highlightRow(row, index));
        return row;
    }

    convertToTextInput(emailText, rowData, field) {
        const input = document.createElement("input");
        input.type = "text";
        input.classList.add("email-text-open");
        input.value = rowData[field] || ""; // Start with empty if no email
        // Function to handle saving and switching back to text view
        const saveAndSwitchBack = () => {
            rowData[field] = input.value; // Update the data object
            emailText.textContent = input.value || "Click to add email"; // Set text or placeholder
            emailText.classList.toggle("placeholder", !input.value); // Add placeholder styling if empty
            emailText.style.display = "inline";
            input.remove();
        };
        // Attach the blur event
        const handleBlur = () => saveAndSwitchBack();
        input.addEventListener("blur", handleBlur);
        // Trigger save on Enter key and remove blur event
        input.addEventListener("keydown", (event) => {
            if (event.key === "Enter") {
                input.removeEventListener("blur", handleBlur); // Prevent duplicate call
                saveAndSwitchBack();
            }
        });
        emailText.style.display = "none";
        emailText.parentNode.appendChild(input);
        input.focus();
    }

    updatePrimaryUse(selectedIndex) {
        this.person.emails.forEach((item, index) => {
            item.primaryUse = index === selectedIndex ? 1 : 0;
            this.modifiedRows.add(item.emailId);
        });
    }

    highlightRow(row, index) {
        if (this.selectedRow) this.selectedRow.classList.remove("highlight");
        this.selectedRow = row;
        row.classList.add("highlight");
        this.selectedRowIndex = index;
    }

    renderButtons() {
        // Create the button container only once
        if (this.buttonContainer) return; // Avoid creating it multiple times // changed
        this.buttonContainer = document.createElement("div"); // changed
        this.buttonContainer.classList.add("tableButton-container");
        const addButton = document.createElement("button");
        addButton.textContent = "Add";
        addButton.addEventListener("click", () => this.addRow());
        const deleteButton = document.createElement("button");
        deleteButton.textContent = "Delete";
        deleteButton.addEventListener("click", () => this.deleteRow());
        // Append buttons to the button container
        this.buttonContainer.appendChild(addButton);
        this.buttonContainer.appendChild(deleteButton);
        // Append the button container to the right side of the table in the main container
        this.mainContainer.appendChild(this.buttonContainer); // changed
    }

    addRow() {
        const newEmail = {
            "emailId": 0,
            "pId": this.person.pId,             // replace with actual person ID if needed
            "primaryUse": false,
            "email": "",
            "emailListed": true
        };
        // Send POST request to server to create a new email
        fetch('/api/insert-email', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeaderName]: csrfToken // Include CSRF token in the request headers
            },
            body: JSON.stringify(newEmail)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json(); // Parse JSON response
            })
            .then(data => {
                // Assuming the response contains the new ID
                newEmail.emailId = data.id; // Set the returned ID
                this.person.emails.push(newEmail); // Add new email row to data array
                this.renderTable(); // Re-render the table to display the new row
            })
            .catch(error => console.error('Error adding new email:', error));
    }

    deleteRow() {
        if (this.selectedRowIndex != null) {
            const email = this.person.emails[this.selectedRowIndex]; // Ensure `person` is accessible here
            // Confirm deletion (optional)
            if (!confirm("Are you sure you want to delete this email?")) return;
            fetch('/api/delete-email', {
                method: 'DELETE', // Use DELETE method for deletion
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeaderName]: csrfToken // Include CSRF token
                },
                body: JSON.stringify(email)
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(() => { // data is not used here
                    // Remove email from array and DOM
                    this.person.emails.splice(this.selectedRowIndex, 1); // Remove from data
                    if (this.selectedRow) { // Ensure row exists
                        this.selectedRow.remove(); // Remove from DOM
                    }
                    // Reset selected row variables
                    this.selectedRow = null; // Reset selected row
                    this.selectedRowIndex = null; // Reset selected row index
                })
                .catch(error => console.error('Error deleting email:', error)); // Adjust error message
        }
    }

    // Function to check for any open editable fields and close them
    closeOpenInputs() {
        // Select all elements with the 'email-text-open' class
        const openInputs = document.querySelectorAll('.email-text-open');
        // Loop through each open input and simulate a blur to trigger saving
        openInputs.forEach(input => {
            input.blur(); // This will trigger the blur event, which saves the data
        });
    }

    batchUpdate() {
            this.closeOpenInputs();
            const modifiedRows = [];
            // Loop through each email in the data model
            this.person.emails.forEach(email => {
                // Check if this emailId is in the modified set
                if (this.modifiedRows.has(email.emailId)) {
                    // Add the modified email data directly from the model
                    modifiedRows.push({
                        emailId: email.emailId,
                        pId: email.pId,
                        primaryUse: email.primaryUse,
                        email: email.email,
                        emailListed: email.emailListed
                    });
                }
            });
            if(modifiedRows.length != 0)
            this.modifiedRows.clear();
        // Only send the fetch request if there are modified rows
        if (modifiedRows.length > 0) {
            fetch('/api/update-emails', {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeaderName]: csrfToken
                },
                body: JSON.stringify(modifiedRows) // Send modified rows in a single request
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    this.modifiedRows.clear();
                })
                .catch(error => console.error('Error updating emails:', error));
        }
    }
}