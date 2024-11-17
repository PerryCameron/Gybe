class HistoryTable {
    constructor(container, membershipData) {
        this.mainContainer = container;
        this.membership = membershipData || {};
        this.membership.membership_ids = Array.isArray(this.membership.membership_ids) ? this.membership.membership_ids : [];
        this.modifiedRows = new Set();
        this.selectedRow = null;
        this.tableContainer = document.createElement("div");
        this.tableContainer.classList.add("medium-table");
        this.table = document.createElement("table");
        // Define table headers
        this.headers = {
            0: {fiscalYear: "Year", widget: "text"},
            1: {membershipId: "Mem ID", widget: "text"},
            2: {memType: "Mem Type", widget: "drop down"},
            3: {renew: "Renewed", widget: "check"},
        };
        this.buttonContainer = this.renderButtons()
        this.tableContainer.appendChild(this.table);
        this.renderButtons()
        this.mainContainer.appendChild(this.buttonContainer);
        this.mainContainer.appendChild(this.tableContainer);
        this.mainContainer.addEventListener('mouseleave', () => this.batchUpdate());
        // Render the initial table
        this.renderTable(true);
    }

    renderTable(sort) {
        console.log("rendering table");

        // Clear previous rows while keeping the container structure intact
        this.table.innerHTML = ""; // Clear existing table rows

        // Create the header row (if not already created)
        const headerRow = document.createElement("tr");
        Object.values(this.headers).forEach((header) => {
            const th = document.createElement("th");
            th.textContent = header.fiscalYear || header.membershipId || header.memType || header.renew;
            headerRow.appendChild(th);
        });

        // Check if the header already exists before appending it
        if (!this.table.querySelector("tr")) {
            this.table.appendChild(headerRow);
        }

        if(sort) {
            this.membership.membership_ids.sort((a, b) => b.fiscalYear - a.fiscalYear);
        }
        // Create and append data rows
        this.buildRows();
    }

    buildRows() {
        this.membership.membership_ids.forEach((rowData, index) => {
            const row = this.createDataRow(rowData, index);
            this.table.appendChild(row);
        });
    }

    renderButtons() {
        const buttonContainer = document.createElement("div");
        buttonContainer.classList.add("medium-table-button-container");

        const addButton = document.createElement("button");
        addButton.textContent = "Add";
        addButton.addEventListener("click", () => this.addRow());

        const deleteButton = document.createElement("button");
        deleteButton.textContent = "Delete";
        deleteButton.addEventListener("click", () => this.deleteRow());

        buttonContainer.appendChild(addButton);
        buttonContainer.appendChild(deleteButton);

        return buttonContainer;
    }

    createDataRow(rowData, index) {
        const row = document.createElement("tr");
        // Set data attributes for the row using rowData properties
        row.dataset.mId = rowData.mId;

        // Create the year cell with an editable field
        const yearCell = document.createElement("td");
        const yearText = this.createEditableTextField(
            rowData.fiscalYear,
            "Click to add year",
            "filled-year",
            rowData,
            "fiscalYear"
        );
        yearCell.appendChild(yearText);
        row.appendChild(yearCell);

        // Create the membership ID cell with an editable field
        const idCell = document.createElement("td");
        const memIdText = this.createEditableTextField(
            rowData.membershipId,
            "Click to add ID",
            "filled-id",
            rowData,
            "membershipId"
        );
        idCell.appendChild(memIdText);
        row.appendChild(idCell);
        row.appendChild(this.createHistoryTypeSelect(rowData));

        const listedCell = document.createElement("td");
        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.classList.add("listed-check-box");
        checkbox.checked = rowData.renew === 1;
        checkbox.addEventListener("change", () => {
            rowData.renew = checkbox.checked ? 1 : 0;
            this.modifiedRows.add(rowData.mId);
        });
        listedCell.appendChild(checkbox);
        row.appendChild(listedCell);
        // Highlight row on click for deletion
        row.addEventListener("click", () => this.highlightRow(row, index));
        return row;
    }

    createEditableTextField(cellText, placeholderText, cssClass, rowData, key) {
        const span = document.createElement("span");
        // Set initial content or placeholder
        span.textContent = rowData[key] || placeholderText;
        span.classList.add(rowData[key] ? cssClass : "placeholder");

        // Add click event to convert to text input
        span.addEventListener("click", () => {
            this.convertToTextInput(span, rowData, key);
            this.modifiedRows.add(rowData.mId); // Track modified rows by `mId`
        });

        return span;
    }

    createHistoryTypeSelect(rowData) {
        const historyTypeContainer = document.createElement("td");
        historyTypeContainer.classList.add("select-container");
        historyTypeContainer.id = "history-type-container-" + rowData.mId;

        const historyTypeSelect = document.createElement("select");
        historyTypeSelect.id = "history-type-select-" +rowData.mId;
        historyTypeSelect.className = "small-table-select";
        historyTypeSelect.name = "historyType";

        // Define the options mapping
        const options = {
            "RM": "Regular",
            "FM": "Family",
            "SO": "Social",
            "LA": "Lake Associate",
            "LM": "Life Member",
            "RF": "Race Fellow"
        };

        // Populate the select element with the defined options
        for (const [value, label] of Object.entries(options)) {
            const option = document.createElement("option");
            option.value = value;
            option.textContent = label;
            historyTypeSelect.appendChild(option);
        }

        // Set the selected option based on data.historyType
        if (rowData.memType) {
            historyTypeSelect.value = rowData.memType;
        }

        historyTypeSelect.addEventListener("change", () => {
            rowData.memType = historyTypeSelect.value;
            this.modifiedRows.add(rowData.mId);  // Now this refers to the correct context
        });

        historyTypeContainer.appendChild(historyTypeSelect);
        return historyTypeContainer;
    }

    convertToTextInput(historyText, rowData, field) {
        const input = document.createElement("input");
        input.type = "text";
        input.classList.add("history-text-open");
        input.value = rowData[field] || ""; // Start with empty if no history
        // Function to handle saving and switching back to text view
        const saveAndSwitchBack = () => {
            rowData[field] = input.value; // Update the data object
            historyText.textContent = input.value || "Click to add id"; // Set text or placeholder
            historyText.classList.toggle("placeholder", !input.value); // Add placeholder styling if empty
            historyText.style.display = "inline";
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
        historyText.style.display = "none";
        historyText.parentNode.appendChild(input);
        input.focus();
    }

    highlightRow(row, index) {
        if (this.selectedRow) this.selectedRow.classList.remove("highlight");
        this.selectedRow = row;
        row.classList.add("highlight");
        this.selectedRowIndex = index;
    }

    addRow() {
        const newHistory = {
            // "fiscalYear": this.membership.fiscalYear,
            "fiscalYear": 0,
            "lateRenew": 0,
            "mId": 0,
            "memType": "RM",
            "msId": this.membership.msId,
            "membershipId": 0,
            "renew": 1,
            "selected": 0,
        };
        // Send POST request to server to create a new history
        fetch('/api/insert-membershipId', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeaderName]: csrfToken // Include CSRF token in the request headers
            },
            body: JSON.stringify(newHistory)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json(); // Parse JSON response
            })
            .then(data => {
                // Assuming the response contains the new ID
                newHistory.mId = data.id; // Set the returned ID
                this.membership.membership_ids.unshift(newHistory); // Add new history row to data array
                this.renderTable(false); // Re-render the table to display the new row
            })
            .catch(error => console.error('Error adding new history:', error));
    }

    deleteRow() {
        if (this.selectedRowIndex != null) {
            const history = this.membership.membership_ids[this.selectedRowIndex]; // Ensure `person` is accessible here
            // Confirm deletion (optional)
            if (!confirm("Are you sure you want to delete this history?")) return;
            fetch('/api/delete-membershipId', {
                method: 'DELETE', // Use DELETE method for deletion
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeaderName]: csrfToken // Include CSRF token
                },
                body: JSON.stringify(history)
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(() => { // data is not used here
                    // Remove history from array and DOM
                    this.membership.membership_ids.splice(this.selectedRowIndex, 1); // Remove from data
                    if (this.selectedRow) { // Ensure row exists
                        this.selectedRow.remove(); // Remove from DOM
                    }
                    // Reset selected row variables
                    this.selectedRow = null; // Reset selected row
                    this.selectedRowIndex = null; // Reset selected row index
                })
                .catch(error => console.error('Error deleting history:', error)); // Adjust error message
        }
    }

    // Function to check for any open editable fields and close them
    closeOpenInputs() {
        // Select all elements with the 'history-text-open' class
        const openInputs = document.querySelectorAll('.history-text-open');
        // Loop through each open input and simulate a blur to trigger saving
        openInputs.forEach(input => {
            input.blur(); // This will trigger the blur event, which saves the data
        });
    }

    batchUpdate() {
            this.closeOpenInputs();
            const editedRows = [];
            // Loop through each history in the data model
            this.membership.membership_ids.forEach(memId => {
                // Check if this historyId is in the modified set
                if (this.modifiedRows.has(memId.mId)) {
                    // Add the modified memId data directly from the model
                    editedRows.push({
                        mId: memId.mId,
                        fiscalYear: memId.fiscalYear,
                        msId: memId.msId,
                        membershipId: memId.membershipId,
                        renew: memId.renew,
                        memType: memId.memType,
                        selected: memId.selected,
                        lateRenew: memId.lateRenew,
                    });
                }
            });
        // Only send the fetch request if there are modified rows
        if (editedRows.length > 0) {
            fetch('/api/update-membershipIds', {
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
                    console.log("Modified rows: ", this.modifiedRows);
                    this.modifiedRows.forEach(mId => {
                        console.log(`tr[data-m-id="${mId}"]`); // why is mId undefined here?
                        const historyRow = document.querySelector(`tr[data-m-id="${mId}"]`);
                        if (historyRow) {
                            historyRow.classList.add("updateSuccess");
                            // Revert to the original background color after .5 seconds
                            setTimeout(() => {
                                historyRow.classList.remove("updateSuccess");
                            }, 500);
                        }
                    });
                    this.modifiedRows.clear();
                })
                .catch(error => console.error('Error updating membershipIds:', error));
        }
    }
}