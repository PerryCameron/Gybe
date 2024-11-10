class PhoneTable {
    constructor(container, person) {
        this.mainContainer = container;
        this.tableContainer = document.createElement("div");
        this.tableContainer.classList.add("table-container");
        this.headers = {
            0: {phone: "Phone", widget: "text"},
            1: {phoneType: "Type", widget: "radio"},
            2: {phoneListed: "Listed", widget: "check"},
        };
        this.person = person || [];// Table data for each phone
        this.person.phones = Array.isArray(this.person.phones) ? this.person.phones : [];
        this.modifiedRows = new Set();
        this.table = document.createElement("table"); // The phone table itself
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
            th.textContent = header.phone || header.phoneType || header.phoneListed;
            headerRow.appendChild(th);
        });
        this.table.addEventListener('mouseleave', () => this.batchUpdate());
        this.table.appendChild(headerRow);
        // Create data rows
        this.person.phones.forEach((rowData, index) => {
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
        row.dataset.phoneId = rowData.phoneId;
        row.dataset.pId = rowData.pId;
        // phone column
        const phoneCell = document.createElement("td");
        const phoneText = document.createElement("span");
        // Display placeholder if phone is empty
        phoneText.textContent = rowData.phone || "Click to add phone";
        phoneText.classList.add(rowData.phone ? "filled-phone" : "placeholder");
        // phoneText.classList.add("phone-text-field");
        phoneText.addEventListener("click", () => {
            this.convertToTextInput(phoneText, rowData, "phone");
            this.modifiedRows.add(rowData.phoneId);
        });
        phoneCell.appendChild(phoneText);
        row.appendChild(phoneCell);
        row.appendChild(this.createPhoneTypeSelect(rowData));
        // phone Listed column as a checkbox
        const listedCell = document.createElement("td");
        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.classList.add("listed-check-box");
        checkbox.checked = rowData.phoneListed === 1;
        checkbox.addEventListener("change", () => {
            rowData.phoneListed = checkbox.checked ? 1 : 0;
            this.modifiedRows.add(rowData.phoneId);
        });
        listedCell.appendChild(checkbox);
        row.appendChild(listedCell);
        // Highlight row on click for deletion
        row.addEventListener("click", () => this.highlightRow(row, index));
        return row;
    }

    createPhoneTypeSelect(rowData) {
        console.log("data: ", rowData);
        const phoneTypeContainer = document.createElement("td");
        phoneTypeContainer.classList.add("select-container");
        phoneTypeContainer.id = "phone-type-container-" + rowData.phoneId;

        const phoneTypeSelect = document.createElement("select");
        phoneTypeSelect.id = "phone-type-select-" +rowData.phoneId;
        phoneTypeSelect.className = "phone-select";
        phoneTypeSelect.name = "phoneType";

        // Define the options mapping
        const options = {
            "C": "Cell",
            "H": "Home",
            "E": "Emergency"
        };

        // Populate the select element with the defined options
        for (const [value, label] of Object.entries(options)) {
            const option = document.createElement("option");
            option.value = value;
            option.textContent = label;
            phoneTypeSelect.appendChild(option);
        }

        // Set the selected option based on data.phoneType
        if (rowData.phoneType) {
            phoneTypeSelect.value = rowData.phoneType;
        }

        phoneTypeSelect.addEventListener("change", () => {
            rowData.phoneType = phoneTypeSelect.value;
            this.modifiedRows.add(rowData.phoneId);  // Now this refers to the correct context
        });

        phoneTypeContainer.appendChild(phoneTypeSelect);
        return phoneTypeContainer;
    }

    convertToTextInput(phoneText, rowData, field) {
        const input = document.createElement("input");
        input.type = "text";
        input.classList.add("phone-text-open");
        input.value = rowData[field] || ""; // Start with empty if no phone
        // Function to handle saving and switching back to text view
        const saveAndSwitchBack = () => {
            rowData[field] = input.value; // Update the data object
            phoneText.textContent = input.value || "Click to add phone"; // Set text or placeholder
            phoneText.classList.toggle("placeholder", !input.value); // Add placeholder styling if empty
            phoneText.style.display = "inline";
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
        phoneText.style.display = "none";
        phoneText.parentNode.appendChild(input);
        input.focus();
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
        this.buttonContainer.classList.add("tableButton-container"); // TODO
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
        const newPhone = {
            "phoneId": 0,
            "pId": this.person.pId,
            "phoneListed": true,
            "phone": "",
            "phoneType": "cell",
        };
        // Send POST request to server to create a new phone
        fetch('/api/insert-phone', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeaderName]: csrfToken // Include CSRF token in the request headers
            },
            body: JSON.stringify(newPhone)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json(); // Parse JSON response
            })
            .then(data => {
                // Assuming the response contains the new ID
                newPhone.phoneId = data.id; // Set the returned ID
                this.person.phones.push(newPhone); // Add new phone row to data array
                this.renderTable(); // Re-render the table to display the new row
            })
            .catch(error => console.error('Error adding new phone:', error));
    }

    deleteRow() {
        if (this.selectedRowIndex != null) {
            const phone = this.person.phones[this.selectedRowIndex]; // Ensure `person` is accessible here
            // Confirm deletion (optional)
            if (!confirm("Are you sure you want to delete this phone?")) return;
            fetch('/api/delete-phone', {
                method: 'DELETE', // Use DELETE method for deletion
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeaderName]: csrfToken // Include CSRF token
                },
                body: JSON.stringify(phone)
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(() => { // data is not used here
                    // Remove phone from array and DOM
                    this.person.phones.splice(this.selectedRowIndex, 1); // Remove from data
                    if (this.selectedRow) { // Ensure row exists
                        this.selectedRow.remove(); // Remove from DOM
                    }
                    // Reset selected row variables
                    this.selectedRow = null; // Reset selected row
                    this.selectedRowIndex = null; // Reset selected row index
                })
                .catch(error => console.error('Error deleting phone:', error)); // Adjust error message
        }
    }

    // Function to check for any open editable fields and close them
    closeOpenInputs() {
        // Select all elements with the 'phone-text-open' class
        const openInputs = document.querySelectorAll('.phone-text-open');
        // Loop through each open input and simulate a blur to trigger saving
        openInputs.forEach(input => {
            input.blur(); // This will trigger the blur event, which saves the data
        });
    }

    batchUpdate() {
            this.closeOpenInputs();
            const editedRows = [];
            // Loop through each phone in the data model
            this.person.phones.forEach(phone => {
                // Check if this phoneId is in the modified set
                if (this.modifiedRows.has(phone.phoneId)) {
                    // Add the modified phone data directly from the model
                    editedRows.push({
                        phoneId: phone.phoneId,
                        pId: phone.pId,
                        phoneListed: phone.phoneListed,
                        phone: phone.phone,
                        phoneType: phone.phoneType,
                    });
                }
            });
        // Only send the fetch request if there are modified rows
        if (editedRows.length > 0) {
            fetch('/api/update-phones', {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeaderName]: csrfToken
                },
                body: JSON.stringify(editedRows) // Send modified rows in a single request
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(() => {
                    this.modifiedRows.forEach(phoneId => {
                        const phoneRow = document.querySelector(`tr[data-phone-id="${phoneId}"]`);
                        if (phoneRow) {
                            phoneRow.style.backgroundColor = 'lightgreen'; // still not turning green
                            // Revert to the original background color after .5 seconds
                            setTimeout(() => {
                                phoneRow.style.backgroundColor = '';
                            }, 500);
                        }
                    });
                    this.modifiedRows.clear();
                })
                .catch(error => console.error('Error updating phones:', error));
        }
    }
}