class AddressBlock {
    constructor(membershipData) {
        // Store the initial membership data
        this.membershipData = membershipData;
        this.addressData = { ...membershipData }; // Copy of initial data to track changes

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
        this.streetField.rows = 2;
        this.streetField.value = this.addressData.address || "";
        this.container.appendChild(this.streetField);

        // City, State, and Zip fields (inline)
        this.cityField = this.createTextInput("city-field", this.addressData.city || "", "City");
        this.stateField = this.createTextInput("state-field", this.addressData.state || "", "State");
        this.zipField = this.createTextInput("zip-field", this.addressData.zip || "", "Zip");

        const cityContainer = document.createElement("div");
        const stateZipContainer = document.createElement("div");
        stateZipContainer.classList.add("city-state-zip-container");
        cityContainer.classList.add("city-container");
        cityContainer.append(this.cityField);
        stateZipContainer.append(this.stateField, this.zipField);

        this.container.appendChild(cityContainer);
        this.container.appendChild(stateZipContainer);

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

    // Method to check for changes and send an AJAX update if needed
    checkForChangesAndUpdate() {
        const newAddressData = {
            address: this.streetField.value,
            city: this.cityField.value,
            state: this.stateField.value,
            zip: this.zipField.value
        };

        // Compare each field to see if there are changes
        const hasChanges = Object.keys(newAddressData).some(
            key => newAddressData[key] !== this.addressData[key]
        );

        if (hasChanges) {
            // Update the stored address data
            this.addressData = { ...newAddressData };

            // Send AJAX update to /api/update-address
            fetch('/api/update-address', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newAddressData)
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! Status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => console.log("Address updated successfully:", data))
                .catch(error => console.error("Error updating address:", error));
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