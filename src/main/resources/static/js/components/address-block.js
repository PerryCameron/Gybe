class AddressBlock {
    constructor(membershipData) {
        // Store the initial membership data
        this.membershipData = membershipData;
        this.addressData = {
            msId: this.membershipData.msId,
            pId: this.membershipData.pId,
            joinDate: this.membershipData.joinDate,
            address: this.membershipData.address,
            city: this.membershipData.city,
            state: this.membershipData.state,
            zip: this.membershipData.zip
        }

        // Create the address block container
        this.container = document.createElement("div");
        this.container.classList.add("address-block");

        // Build the address form
        this.buildAddressBox();

        // Event listener to detect when the mouse leaves the address block
        this.container.addEventListener("mouseleave", () => this.checkForChangesAndUpdate());
    }

    // Build the address box UI
    buildAddressBox() {
        // Street field (multiline)
        this.streetField = document.createElement("textarea");
        this.streetField.classList.add("street-field");
        // this.streetField.rows = 2;
        this.streetField.value = this.addressData.address || "";
        this.container.appendChild(this.streetField);

        // City, State, and Zip fields (inline)
        this.cityField = this.createTextInput("city-field", this.addressData.city || "", "City");
        this.stateField = this.createTextInput("state-field", this.addressData.state || "", "State");
        this.zipField = this.createTextInput("zip-field", this.addressData.zip || "", "Zip");

        this.container.appendChild(this.streetField);
        this.container.appendChild(this.cityField);
        this.container.appendChild(this.stateField);
        this.container.appendChild(this.zipField);
    }

    // Helper method to create text input fields
    createTextInput(className, value, placeholder) {
        const input = document.createElement("input");
        input.type = "text";
        input.classList.add(className);
        input.value = value;
        input.placeholder = placeholder;
        return input;
    }

    checkForChangesAndUpdate() {
        const newAddressData = {
            msId: this.membershipData.msId,
            pId: this.membershipData.pId,
            joinDate: this.membershipData.joinDate,
            memType: this.membershipData.memType,
            address: this.streetField.value,
            city: this.cityField.value,
            state: this.stateField.value,
            zip: this.zipField.value
        };

        // Determine which fields have changed
        const changedFields = Object.keys(newAddressData).filter(
            key => newAddressData[key] !== this.addressData[key]
        );

        if (changedFields.length > 0) {
            // Update the stored address data
            this.addressData = { ...newAddressData };

            // Send AJAX update to /api/update-address
            fetch('/api/update-address', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeaderName]: csrfToken
                },
                body: JSON.stringify(newAddressData)
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! Status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    // Highlight only the fields that were updated
                    changedFields.forEach(field => {
                        const fieldElement = this.getFieldElement(field);
                        if (fieldElement) {
                            fieldElement.classList.add("updateSuccess");
                            setTimeout(() => fieldElement.classList.remove("updateSuccess"), 500);
                        }
                    });
                })
                .catch(error => console.error("Error updating address:", error));
        }
    }

// Helper function to map field names to DOM elements
    getFieldElement(fieldName) {
        switch (fieldName) {
            case 'address':
                return this.streetField;
            case 'city':
                return this.cityField;
            case 'state':
                return this.stateField;
            case 'zip':
                return this.zipField;
            default:
                return null; // No corresponding field
        }
    }

    // Method to append the address block to a target element
    appendTo(target) {
        target.appendChild(this.container);
    }

    getElement() {
        return this.container;
    }
}