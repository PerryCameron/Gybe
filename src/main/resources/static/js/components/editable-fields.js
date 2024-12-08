class EditableFieldsPane extends HTMLElement {
    constructor(personData) {
        super();
        this.personData = personData;
        this.personIsEdited = false;
        this.classList.add("editable-fields-pane");
        this.id = `person-info-${this.personData.pId}`;
        // Create a div to hold all fields, add a mouseleave listener to handle updates
        this.fieldsContainer = document.createElement("div");
        this.fieldsContainer.classList.add("fields-container");
        this.fieldsContainer.id = "fields-" + this.personData.pId;
        // Create fields for each property and add them to the container
        this.fields = {};
        Object.keys(personFields).forEach((field) => {
            const fieldDiv = this.createFieldDiv(field);
            this.fieldsContainer.appendChild(fieldDiv);
        });
        this.fieldsContainer.appendChild(this.createAgeDiv());
        this.addEventListener('mouseleave', () => {
            if(this.personIsEdited) {
                this.update();
                this.personIsEdited = false;
            }
        });
        this.appendChild(this.fieldsContainer);
    }

    calculateAge(birthDateString) {
        if(birthDateString === null) return "";
        const birthDate = new Date(birthDateString);
        const today = new Date();
        let age = today.getFullYear() - birthDate.getFullYear();
        const monthDifference = today.getMonth() - birthDate.getMonth();
        const dayDifference = today.getDate() - birthDate.getDate();
        // Adjust age if the birthdate hasn't occurred yet this year
        if (monthDifference < 0 || (monthDifference === 0 && dayDifference < 0)) {
            age--;
        }
        return age;
    }

    createAgeDiv() {
        const fieldDiv = document.createElement("div");
        fieldDiv.classList.add("field");
        fieldDiv.id = "age-div";
        const label = document.createElement("label");
        label.textContent = "Age:";
        const valueSpan = document.createElement("span");
        valueSpan.textContent = this.calculateAge(this.personData.birthday) || "";
        valueSpan.classList.add("field-text");
        fieldDiv.appendChild(label);
        fieldDiv.appendChild(valueSpan);
        return fieldDiv;
    }

    // Create a div for each field, initially displaying the text
    createFieldDiv(field) {
        const fieldDiv = document.createElement("div");
        fieldDiv.classList.add("field");
        // fieldDiv.id =  field + "-" + this.id
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
        this.personIsEdited = true;

        const input = document.createElement("input");
        input.type = "text";
        input.value = spanElement.textContent;
        input.classList.add("field-input");
        input.classList.add("person-text-open");
        // Function to handle saving and replacing input with span
        const saveAndReplace = () => this.saveInput(input, field);
        // Attach blur and keydown events
        input.addEventListener("blur", saveAndReplace);
        input.addEventListener("keydown", (event) => {
            if (event.key === "Enter") {
                event.preventDefault(); // Prevent default Enter behavior if needed
                input.removeEventListener("blur", saveAndReplace); // Avoid duplicate call
                saveAndReplace();
            }
        });
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

    closeOpenInputs() {
        // Select all elements with the 'email-text-open' class
        const openInputs = document.querySelectorAll('.person-text-open');
        // Loop through each open input and simulate a blur to trigger saving
        openInputs.forEach(input => {
            input.blur(); // This will trigger the blur event, which saves the data
        });
    }

    update() {
        this.closeOpenInputs();
        // Gather modified values from UI fields
        const personData = {
            pId: this.personData.pId,
            msId: this.personData.msId,
            memberType: this.personData.memberType,
            firstName: this.personData.firstName,
            lastName: this.personData.lastName,
            nickName: this.personData.nickName,
            occupation: this.personData.occupation,
            business: this.personData.business,
            birthday: this.personData.birthday,
            active: this.personData.active,
            oldMsid: this.personData.oldMsid
        };
        console.log("Sent person data", personData);
        // Send POST request to update the person fields
        fetch('/api/update-person', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeaderName]: csrfToken // CSRF token from global variable
            },
            body: JSON.stringify(personData)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json(); // Optional: Parse JSON response
            })
            .then(() => {
                const personRow = document.getElementById(`fields-${this.personData.pId}`);
                console.log(personRow);
                if (personRow) {
                    personRow.classList.add("updateSuccess");
                    // Revert to the original background color after .5 seconds
                    setTimeout(() => {
                        personRow.classList.remove("updateSuccess");
                    }, 500);
                }
            })
            .catch(error => console.error('Error updating person:', error)); // Handle error
    }
}

// Register the custom element
customElements.define("editable-fields-pane", EditableFieldsPane);

