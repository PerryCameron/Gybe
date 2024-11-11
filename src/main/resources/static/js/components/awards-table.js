class AwardsTable {
    constructor(container, person) {
        this.mainContainer = container;
        this.tableContainer = document.createElement("div");
        this.tableContainer.classList.add("table-container");
        this.headers = {
            0: {awardYear: "Year", widget: "text"},
            1: {awardType: "Award Type", widget: "radio"},
        };
        this.person = person || [];// Table data for each award
        this.person.awards = Array.isArray(this.person.awards) ? this.person.awards : [];
        this.modifiedRows = new Set();
        this.table = document.createElement("table"); // The award table itself
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
            th.textContent = header.awardYear || header.awardType;
            headerRow.appendChild(th);
        });
        this.table.addEventListener('mouseleave', () => this.batchUpdate());
        this.table.appendChild(headerRow);
        // Create data rows
        this.person.awards.forEach((rowData, index) => {
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
        row.dataset.awardId = rowData.awardId;
        row.dataset.pId = rowData.pId;
        // award column
        const awardCell = document.createElement("td");
        const awardYearText = document.createElement("span");
        // Display placeholder if award is empty
        awardYearText.textContent = rowData.awardYear || "Click to add year";
        awardYearText.classList.add(rowData.awardYear ? "filled-award" : "placeholder");
        // awardText.classList.add("award-text-field");
        awardYearText.addEventListener("click", () => {
            this.convertToTextInput(awardYearText, rowData, "awardYear");
            this.modifiedRows.add(rowData.awardId);
            console.log("Testing: ", rowData);  // this is not updating awardYear
        });
        awardCell.appendChild(awardYearText);
        row.appendChild(awardCell);
        row.appendChild(this.createAwardTypeSelect(rowData));
        // Highlight row on click for deletion
        row.addEventListener("click", () => this.highlightRow(row, index));
        return row;
    }

    createAwardTypeSelect(rowData) {
        console.log("data: ", rowData);
        const awardTypeContainer = document.createElement("td");
        awardTypeContainer.classList.add("select-container");
        awardTypeContainer.id = "award-type-container-" + rowData.awardId;

        const awardTypeSelect = document.createElement("select");
        awardTypeSelect.id = "award-type-select-" + rowData.awardId;
        awardTypeSelect.className = "small-table-select";
        awardTypeSelect.name = "awardType";

        // Define the options mapping
        const options = {
            "SA": "Sportsmanship Award",
            "SY": "Sailor of the year"
        };

        // Populate the select element with the defined options
        for (const [value, label] of Object.entries(options)) {
            const option = document.createElement("option");
            option.value = value;
            option.textContent = label;
            awardTypeSelect.appendChild(option);
        }

        // Set the selected option based on data.awardType
        if (rowData.awardType) {
            awardTypeSelect.value = rowData.awardType;
        }

        awardTypeSelect.addEventListener("change", () => {
            rowData.awardType = awardTypeSelect.value;
            this.modifiedRows.add(rowData.awardId);  // Now this refers to the correct context
        });

        awardTypeContainer.appendChild(awardTypeSelect);
        return awardTypeContainer;
    }

    convertToTextInput(awardText, rowData, field) {
        const input = document.createElement("input");
        input.type = "text";
        input.classList.add("award-text-open");
        input.value = rowData[field] || ""; // Start with empty if no award
        // Function to handle saving and switching back to text view
        const saveAndSwitchBack = () => {
            rowData[field] = input.value; // Update the data object
            awardText.textContent = input.value || "Click to add award"; // Set text or placeholder
            awardText.classList.toggle("placeholder", !input.value); // Add placeholder styling if empty
            awardText.style.display = "inline";
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
        awardText.style.display = "none";
        awardText.parentNode.appendChild(input);
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
        const newAward = {
            "awardId": 0,
            "pId": this.person.pId,
            "awardYear": 0,
            "awardType": "SA",
        };
        // Send POST request to server to create a new award
        fetch('/api/insert-award', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeaderName]: csrfToken // Include CSRF token in the request headers
            },
            body: JSON.stringify(newAward)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json(); // Parse JSON response
            })
            .then(data => {
                // Assuming the response contains the new ID
                newAward.awardId = data.id; // Set the returned ID
                this.person.awards.push(newAward); // Add new award row to data array
                this.renderTable(); // Re-render the table to display the new row
            })
            .catch(error => console.error('Error adding new award:', error));
    }

    deleteRow() {
        if (this.selectedRowIndex != null) {
            const award = this.person.awards[this.selectedRowIndex]; // Ensure `person` is accessible here
            // Confirm deletion (optional)
            if (!confirm("Are you sure you want to delete this award?")) return;
            fetch('/api/delete-award', {
                method: 'DELETE', // Use DELETE method for deletion
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeaderName]: csrfToken // Include CSRF token
                },
                body: JSON.stringify(award)
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(() => { // data is not used here
                    // Remove award from array and DOM
                    this.person.awards.splice(this.selectedRowIndex, 1); // Remove from data
                    if (this.selectedRow) { // Ensure row exists
                        this.selectedRow.remove(); // Remove from DOM
                    }
                    // Reset selected row variables
                    this.selectedRow = null; // Reset selected row
                    this.selectedRowIndex = null; // Reset selected row index
                })
                .catch(error => console.error('Error deleting award:', error)); // Adjust error message
        }
    }

    // Function to check for any open editable fields and close them
    closeOpenInputs() {
        // Select all elements with the 'award-text-open' class
        const openInputs = document.querySelectorAll('.award-text-open');
        // Loop through each open input and simulate a blur to trigger saving
        openInputs.forEach(input => {
            input.blur(); // This will trigger the blur event, which saves the data
        });
    }

    batchUpdate() {
        this.closeOpenInputs();
        const editedRows = [];
        // Loop through each award in the data model
        this.person.awards.forEach(award => {
            // Check if this awardId is in the modified set
            if (this.modifiedRows.has(award.awardId)) {
                // Add the modified award data directly from the model
                editedRows.push({
                    awardId: award.awardId,
                    pId: award.pId,
                    awardYear: award.awardYear,
                    awardType: award.awardType,
                });
            }
        });
        // Only send the fetch request if there are modified rows
        if (editedRows.length > 0) {
            fetch('/api/update-awards', {
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
                    this.modifiedRows.forEach(awardId => {
                        const awardRow = document.querySelector(`tr[data-award-id="${awardId}"]`);
                        if (awardRow) {
                            awardRow.style.backgroundColor = 'lightgreen'; // still not turning green
                            // Revert to the original background color after .5 seconds
                            setTimeout(() => {
                                awardRow.style.backgroundColor = '';
                            }, 500);
                        }
                    });
                    this.modifiedRows.clear();
                })
                .catch(error => console.error('Error updating awards:', error));
        }
    }
}