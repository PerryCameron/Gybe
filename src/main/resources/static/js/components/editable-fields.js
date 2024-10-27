class EditableFieldsPane extends HTMLElement {
    constructor(fieldLabels, personData) {
        super();
        this.personData = personData;
        // this.fieldLabels = fieldLabels;

        // Add the editable-fields-pane class for styling
        this.classList.add("editable-fields-pane");

        // Create a div to hold all fields, add a mouseleave listener to handle updates
        this.fieldsContainer = document.createElement("div");
        this.fieldsContainer.classList.add("fields-container");
        // this.fieldsContainer.addEventListener("mouseleave", () => this.updateData());
        this.fieldsContainer.addEventListener("mouseleave", () => console.log("mouse left"));

        // Create fields for each property and add them to the container
        this.fields = {};
        Object.keys(fieldLabels).forEach((field) => {
            const fieldDiv = this.createFieldDiv(field);
            this.fieldsContainer.appendChild(fieldDiv);
        });

        // Append the container to the main element
        this.appendChild(this.fieldsContainer);
    }

    // Create a div for each field, initially displaying the text
    createFieldDiv(field) {
        const fieldDiv = document.createElement("div");
        fieldDiv.classList.add("field");

        const label = document.createElement("label");
        label.textContent = field.charAt(0).toUpperCase() + field.slice(1) + ": ";

        const valueSpan = document.createElement("span");
        valueSpan.classList.add("field-text");
        valueSpan.textContent = this.personData[field] || "";

        // Convert text to editable input on click
        valueSpan.addEventListener("click", () => this.toggleEditable(valueSpan, field));

        fieldDiv.appendChild(label);
        fieldDiv.appendChild(valueSpan);

        // Store a reference to the field for easy access later
        this.fields[field] = valueSpan;

        return fieldDiv;
    }

    // Toggle between text and input
    toggleEditable(spanElement, field) {
        // If span is already an input, exit
        if (spanElement.tagName === "INPUT") return;

        // Replace span with an input element
        const input = document.createElement("input");
        input.type = "text";
        input.value = spanElement.textContent;
        input.classList.add("field-input");

        input.addEventListener("blur", () => this.saveInput(input, field));
        spanElement.replaceWith(input);
        input.focus();
    }

    // Save the input back to span on blur
    saveInput(input, field) {
        const span = document.createElement("span");
        span.classList.add("field-text");
        span.textContent = input.value;

        // Update personData and replace input with span
        this.personData[field] = input.value;
        input.replaceWith(span);

        // Add click event to make the span editable again
        span.addEventListener("click", () => this.toggleEditable(span, field));

        // Update the stored reference
        this.fields[field] = span;
    }

    // Send AJAX update to the server
    // async updateData() {
    //     try {
    //         const response = await fetch("/api/update-person", {
    //             method: "POST",
    //             headers: { "Content-Type": "application/json" },
    //             body: JSON.stringify({ personData: this.personData })
    //         });

    //         if (response.ok) {
    //             // On success, switch all fields back to plain text
    //             Object.keys(this.fields).forEach(field => {
    //                 const span = this.fields[field];
    //                 if (span.tagName === "INPUT") {
    //                     this.saveInput(span, field);
    //                 }
    //             });
    //             console.log("Data successfully updated!");
    //         } else {
    //             console.error("Failed to update data.");
    //         }
    //     } catch (error) {
    //         console.error("Error sending update:", error);
    //     }
    // }
}

// Register the custom element
customElements.define("editable-fields-pane", EditableFieldsPane);

