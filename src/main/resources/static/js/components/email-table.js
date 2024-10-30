class EmailTable {
    constructor(container, person) {
        this.tableContainer = container;
        this.headers = {
            0: { email: "Email", widget: "text" },
            1: { primaryUse: "Primary", widget: "radio" },
            2: { emailListed: "Listed", widget: "check" },
        };
        this.person = person || [];// Table data for each email
        console.log("email-table", person);

        // this.tableContainer = document.createElement("div"); // Main container for the table and buttons
        this.tableContainer.style.display = "flex"; // changed
        this.tableContainer.style.alignItems = "flex-start"; // Align items to the top // changed
        this.tableContainer.style.gap = "5px"; // Space between table and buttons // changed

        this.table = document.createElement("table"); // The email table itself
        this.selectedRow = null; // To track the highlighted row

        this.renderTable();
        this.renderButtons();
    }

    renderTable() {
        this.table.innerHTML = ""; // Clear previous content

        // Create the header row
        const headerRow = document.createElement("tr");
        Object.values(this.headers).forEach((header) => {
            console.log("parsing email headers", header);
            const th = document.createElement("th");
            th.textContent = header.email || header.primaryUse || header.emailListed;
            headerRow.appendChild(th);
        });
        this.table.appendChild(headerRow);

        // Create data rows
        this.person.emails.forEach((rowData, index) => {
            console.log("parsing data", rowData)
            const row = this.createDataRow(rowData, index);
            this.table.appendChild(row);
        });

        // Append the table to the main container if it's not already there
        if (!this.tableContainer.contains(this.table)) {
            // changed
            this.tableContainer.appendChild(this.table); // changed
        }

        // Only append the tableContainer once to the body
        if (!document.body.contains(this.tableContainer)) {
            // changed
            document.body.appendChild(this.tableContainer); // changed
        }
    }

    createDataRow(rowData, index) {
        console.log("Creating data row");
        const row = document.createElement("tr");

        // Email column
        const emailCell = document.createElement("td");
        const emailText = document.createElement("span");

        // Display placeholder if email is empty
        emailText.textContent = rowData.email || "Click to add email";
        emailText.classList.add(rowData.email ? "filled-email" : "placeholder");
        // emailText.textContent = rowData.email;
        emailText.addEventListener("click", () => this.convertToTextInput(emailText, rowData, "email"));
        emailCell.appendChild(emailText);
        row.appendChild(emailCell);

        // Primary Use column as a radio button
        const primaryUseCell = document.createElement("td");
        const radio = document.createElement("input");
        radio.type = "radio";
        radio.name = "primaryUse";
        radio.classList.add("small-table-radio");
        radio.checked = rowData.primaryUse === 1;
        radio.addEventListener("click", () => this.updatePrimaryUse(index));
        primaryUseCell.appendChild(radio);
        row.appendChild(primaryUseCell);

        // Email Listed column as a checkbox
        const listedCell = document.createElement("td");
        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.classList.add("small-table-check-box");
        checkbox.checked = rowData.emailListed === 1;
        checkbox.addEventListener("change", () => (rowData.emailListed = checkbox.checked ? 1 : 0));
        listedCell.appendChild(checkbox);
        row.appendChild(listedCell);

        // Highlight row on click for deletion
        row.addEventListener("click", () => this.highlightRow(row, index));
        return row;
    }

    convertToTextInput(emailText, rowData, field) {
        const input = document.createElement("input");
        input.type = "text";
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
        this.person.forEach((item, index) => {
            item.primaryUse = index === selectedIndex ? 1 : 0;
        });
        this.renderTable(); // Re-render to update radio selection
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
        this.buttonContainer.style.display = "flex";
        this.buttonContainer.style.flexDirection = "column";
        this.buttonContainer.style.gap = "5px";

        // Add and Delete buttons
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
        this.tableContainer.appendChild(this.buttonContainer); // changed
    }

    addRow() {

        console.log("pid: " + this.person.pId);
        const newEmail = {
            "emailId": 0,
            "pId": this.person.pId,             // replace with actual person ID if needed
            "primaryUse": false,
            "email": "",
            "emailListed": true
        };

        // Retrieve the CSRF token from the meta tags
        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
        const csrfHeaderName = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");

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
                console.log("data id is " + data.id);
                this.person.emails.push(newEmail); // Add new email row to data array
                this.renderTable(); // Re-render the table to display the new row
            })
            .catch(error => console.error('Error adding new email:', error));
    }


    deleteRow() {
        if (this.selectedRowIndex != null) {
            this.person.emails.splice(this.selectedRowIndex, 1); // Remove from data
            this.selectedRow.remove(); // Remove from DOM
            this.selectedRow = null; // Reset selected row
        }
    }
}

function getCsrfToken() {
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
    const csrfHeaderName = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");
    return { token: csrfToken, headerName: csrfHeaderName };
}