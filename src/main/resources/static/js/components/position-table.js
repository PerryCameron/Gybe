class PositionTable {
    constructor(container, person) {
        this.mainContainer = container;
        this.tableContainer = document.createElement("div");
        this.tableContainer.classList.add("table-container");
        this.headers = {
            0: {fiscalYear: "Year", widget: "text"},
            1: {officerType: "Position", widget: "radio"},
            2: {boardYear: "Board End", widget: "check"},
        };
        this.person = person || []; // if array doesn't exist make an empty set
        this.person.officers = Array.isArray(this.person.officers) ? this.person.officers : [];
        this.modifiedRows = new Set();
        this.table = document.createElement("table"); // The position table itself
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
            th.textContent = header.fiscalYear || header.officerType || header.boardYear;
            headerRow.appendChild(th);
        });
        this.table.addEventListener('mouseleave', () => this.batchUpdate());
        this.table.appendChild(headerRow);
        // Create data rows
        this.person.officers.forEach((rowData, index) => {
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
        row.dataset.officerId = rowData.officerId;
        // position column
        const positionCell = document.createElement("td");
        const fiscalYearText = document.createElement("span");
        // Display placeholder if position is empty
        fiscalYearText.textContent = rowData.fiscalYear || "Click to add year";
        fiscalYearText.classList.add(rowData.fiscalYear ? "filled-fiscalYear" : "placeholder");
        fiscalYearText.addEventListener("click", () => {
            this.convertToTextInput(fiscalYearText, rowData, "fiscalYear");
            this.modifiedRows.add(rowData.officerId);
        });
        positionCell.appendChild(fiscalYearText);
        row.appendChild(positionCell);
        row.appendChild(this.createOfficerTypeSelect(rowData));

        const boardEndCell = document.createElement("td");
        const BoardEndYearText = document.createElement("span");
        // Display placeholder if position is empty
        BoardEndYearText.textContent = rowData.boardYear || "Click to add year";
        BoardEndYearText.classList.add(rowData.boardYear ? "filled-BoardYear" : "placeholder");
        BoardEndYearText.addEventListener("click", () => {
            this.convertToTextInput(BoardEndYearText, rowData, "boardYear");
            this.modifiedRows.add(rowData.officerId);
        });
        boardEndCell.appendChild(BoardEndYearText);
        row.appendChild(boardEndCell);

        row.addEventListener("click", () => this.highlightRow(row, index));
        return row;
    }

    createOfficerTypeSelect(rowData) {
        console.log("data: ", rowData);
        const officerTypeContainer = document.createElement("td");
        officerTypeContainer.classList.add("select-container");
        officerTypeContainer.id = "phone-type-container-" + rowData.officerId;

        const officerTypeSelect = document.createElement("select");
        officerTypeSelect.id = "phone-type-select-" +rowData.officerId;
        officerTypeSelect.className = "phone-select";
        officerTypeSelect.name = "officerType";

        // Define the options mapping
        const options = {
            "CO": "Commodore",
            "VC": "Vice Commodore",
            "PC": "Past Commodore",
            "CB": "Chairman of the Board",
            "FM": "Facility Manager",
            "TR": "Treasurer",
            "SE": "Secretary",
            "HM": "Harbormaster",
            "AH": "Assistant Harbormaster",
            "MS": "Membership",
            "AM": "Assistant Membership",
            "PU": "Publicity",
            "AP": "Assistant Publicity",
            "RA": "Racing",
            "AR": "Assistant Racing",
            "SM": "Safety and Education",
            "AS": "Assistant S and E",
            "JP": "Junior Program",
            "AJ": "Assistant Junior Program",
            "SO": "Social",
            "SA": "Assistant Social",
            "SS": "Ships Store",
            "WA": "Winter Activities",
            "BM": "Board Member",
            "GC": "Grounds Keeper",
            "TE": "Technology",
            "AF": "Assistant Facilities",
            "SK": "Score Keeper",
            "AG": "Assistant Ground Keeper",
            "AT": "Assistant Treasurer",
        };

        // Populate the select element with the defined options
        for (const [value, label] of Object.entries(options)) {
            const option = document.createElement("option");
            option.value = value;
            option.textContent = label;
            officerTypeSelect.appendChild(option);
        }

        // Set the selected option based on data.positionType
        if (rowData.officerType) {
            officerTypeSelect.value = rowData.officerType;
        }

        officerTypeSelect.addEventListener("change", () => {
            rowData.officerType = officerTypeSelect.value;
            console.log("Phone Id is: " + rowData.officerId);
            this.modifiedRows.add(rowData.officerId);  // Now this refers to the correct context
            console.log("modified: ", this.modifiedRows);
        });

        officerTypeContainer.appendChild(officerTypeSelect);
        return officerTypeContainer;
    }

    convertToTextInput(officerText, rowData, field) {
        const input = document.createElement("input");
        input.type = "text";
        input.classList.add("phone-text-open");
        input.value = rowData[field] || ""; // Start with empty if no phone
        // Function to handle saving and switching back to text view
        const saveAndSwitchBack = () => {
            rowData[field] = input.value; // Update the data object
            officerText.textContent = input.value || "Click to add phone"; // Set text or placeholder
            officerText.classList.toggle("placeholder", !input.value); // Add placeholder styling if empty
            officerText.style.display = "inline";
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
        officerText.style.display = "none";
        officerText.parentNode.appendChild(input);
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
            "officerId": 0,
            "pId": this.person.pId,
            "phoneListed": true,
            "phone": "",
            "officerType": "cell",
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
                newPhone.officerId = data.id; // Set the returned ID
                this.person.officers.push(newPhone); // Add new phone row to data array
                this.renderTable(); // Re-render the table to display the new row
            })
            .catch(error => console.error('Error adding new phone:', error));
    }

    deleteRow() {
        if (this.selectedRowIndex != null) {
            const phone = this.person.officers[this.selectedRowIndex]; // Ensure `person` is accessible here
            // Confirm deletion (optional)
            if (!confirm("Are you sure you want to delete this position?")) return;
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
                    this.person.officers.splice(this.selectedRowIndex, 1); // Remove from data
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
            console.log("officers: ", this.person.officers);
            // Loop through each phone in the data model
            this.person.officers.forEach(position => {
                // Check if this officerId is in the modified set
                if (this.modifiedRows.has(position.officerId)) {
                    // Add the modified position data directly from the model
                    editedRows.push({
                        officerId: position.officerId,
                        boardYear: position.boardYear,
                        officerType: position.officerType,
                        fiscalYear: position.fiscalYear,
                    });
                }
            });
            if(editedRows.length != 0)
            this.modifiedRows.clear();
        // Only send the fetch request if there are modified rows
        if (editedRows.length > 0) {
            fetch('/api/update-officers', {
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
                .then(data => {
                    this.modifiedRows.clear();
                })
                .catch(error => console.error('Error updating officers:', error));
        }
    }
}