class SlipWidget {
    constructor(slip) {
        this.slip = new Proxy(slip, {
            set: (target, property, value) => {
                target[property] = value;
                this.renderWidget(); // Re-render the widget whenever the slip object changes
                return true;
            }
        });

        this.container = document.createElement("div");
        this.container.classList.add("slip-storage");

        this.graphicDiv = document.createElement("div");
        this.graphicDiv.classList.add("slip-graphic");

        this.slipLabelDiv = document.createElement("div");
        this.slipLabelDiv.classList.add("slip-label");

        this.widgetDiv = document.createElement("div");
        this.widgetDiv.classList.add("slip-widget");

        this.container.appendChild(this.graphicDiv);
        this.container.appendChild(this.widgetDiv);

        this.renderGraphic();
        this.renderWidget(); // Initial render
    }

    renderGraphic() {
        this.graphicDiv.innerHTML = ""; // Clear the graphic div

        // Update slip label
        this.slipLabelDiv.innerHTML = `
            <span class="slip-label">Slip: </span>
            <span class="slip-name">${this.slip.slipNum || "none"}</span>
        `;
        this.graphicDiv.appendChild(this.slipLabelDiv);

        // Fetch and display the slip image
        const slipName = this.slip.slipNum || "none";
        const imageUrl = `/api/images/${slipName}_icon.png`;

        const img = document.createElement("img");
        img.alt = `Slip: ${slipName}`;

        fetch(imageUrl)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.blob();
            })
            .then((blob) => {
                const imageObjectUrl = URL.createObjectURL(blob);
                img.src = imageObjectUrl;
                this.graphicDiv.appendChild(img);
            })
            .catch((error) => {
                console.error("There was a problem with the fetch operation:", error);
                img.src = "/path/to/placeholder.png";
                this.graphicDiv.appendChild(img);
            });
    }

    renderWidget() {
        this.widgetDiv.innerHTML = ""; // Clear the widget div

        // Check if slipNum is null
        if (!this.slip.slipNum) {
            return; // If slipNum is null, don't render anything
        }

        // Create a container for radio buttons
        const radioContainer = document.createElement("div");
        radioContainer.classList.add("radio-container");

        // Add radio buttons based on conditions
        if (this.slip.subleasedTo !== null) {
            radioContainer.innerHTML = `
                <label><input type="radio" name="action" value="sublease"> Sublease Slip</label><br>
                <label><input type="radio" name="action" value="reassign"> Reassign Slip</label><br>
                <label><input type="radio" name="action" value="release"> Release Sublease</label>
            `;
        } else {
            radioContainer.innerHTML = `
                <label><input type="radio" name="action" value="sublease"> Sublease Slip</label><br>
                <label><input type="radio" name="action" value="reassign"> Reassign Slip</label>
            `;
        }

        // Add the radioContainer to the widget
        this.widgetDiv.appendChild(radioContainer);

        // Add a text field and a submit button
        const inputContainer = document.createElement("div");
        inputContainer.classList.add("input-container");
        inputContainer.style.display = "flex";
        inputContainer.style.gap = "5px";

        const textField = document.createElement("input");
        textField.type = "text";
        textField.placeholder = "Membership ID";
        textField.style.flex = "1";

        const submitButton = document.createElement("button");
        submitButton.textContent = "Submit";

        // Append the text field and button to the container
        inputContainer.appendChild(textField);
        inputContainer.appendChild(submitButton);

        // Append the input container to the widget
        this.widgetDiv.appendChild(inputContainer);
    }

    getElement() {
        return this.container;
    }
}