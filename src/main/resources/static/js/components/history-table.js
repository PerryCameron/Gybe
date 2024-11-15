class HistoryTable {
    constructor(container, membershipData) {
        this.mainContainer = container;
        this.tableContainer = document.createElement("div");
        this.tableContainer.classList.add("table-container");
        this.headers = {
            0: {fiscalYear: "Year", widget: "text"},
            1: {membershipId: "Mem ID", widget: "text"},
            2: {memType: "Mem Type", widget: "drop down"},
            3: {renew: "Renewed", widget: "check"},
        };
        this.membership = membershipData || [];// Table data for each phone
        this.membership.membership_ids = Array.isArray(this.membership.membership_ids) ? this.membership.membership_ids : [];
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
            th.textContent = header.fiscalYear || header.membershipId || header.memType || header.renew;
            headerRow.appendChild(th);
        });
        this.table.addEventListener('mouseleave', () => this.batchUpdate());
        this.table.appendChild(headerRow);
        // sort rows descending
        this.membership.membership_ids.sort((a, b) => b.fiscalYear - a.fiscalYear);
        // Create data rows
        this.membership.membership_ids.forEach((rowData, index) => {
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
        row.dataset.mid = rowData.mid;

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
            this.modifiedRows.add(rowData.mid);
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
            this.modifiedRows.add(rowData.mid); // Track modified rows by `mid`
        });

        return span;
    }

    createHistoryTypeSelect(rowData) {
        console.log("history data: ", rowData);
        const historyTypeContainer = document.createElement("td");
        historyTypeContainer.classList.add("select-container");
        historyTypeContainer.id = "history-type-container-" + rowData.mid;

        const historyTypeSelect = document.createElement("select");
        historyTypeSelect.id = "history-type-select-" +rowData.mid;
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
            rowData.historyType = historyTypeSelect.value;
            this.modifiedRows.add(rowData.mid);  // Now this refers to the correct context
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
        const newHistory = {
            "mid": this.membership.mid,
            "fiscalYear": 0,
            "membershipId": 0,
            "renew": false,
            "memType": "RM",
            "lateRenew": false
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
                newHistory.mid = data.id; // Set the returned ID
                this.membership.membership_ids.push(newHistory); // Add new history row to data array
                this.renderTable(); // Re-render the table to display the new row
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
                if (this.modifiedRows.has(memId.mid)) {
                    // Add the modified memId data directly from the model
                    editedRows.push({
                        mid: memId.mid,
                        fiscalYear: memId.fiscalYear,
                        membershipId: memId.membershipId,
                        renew: memId.renew,
                        memType: memId.memType,
                        lateRenew: false,
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
                    this.modifiedRows.forEach(historyId => {
                        const historyRow = document.querySelector(`tr[data-history-id="${historyId.mid}"]`);
                        if (historyRow) {
                            historyRow.style.backgroundColor = 'lightgreen'; // still not turning green
                            // Revert to the original background color after .5 seconds
                            setTimeout(() => {
                                historyRow.style.backgroundColor = '';
                            }, 500);
                        }
                    });
                    this.modifiedRows.clear();
                })
                .catch(error => console.error('Error updating membershipIds:', error));
        }
    }
}