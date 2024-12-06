class SlipWidget {
    constructor(MembershipData) {
        this.slip = new Proxy(MembershipData.slip, {
            set: (target, property, value) => {
                target[property] = value;
                this.renderGraphic(); // Update the graphic whenever the slip changes
                this.renderWidget(); // Re-render the widget whenever the slip changes
                return true;
            }
        });

        this.msId = MembershipData.msId;
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

        // this.renderGraphic();
        // this.renderWidget(); // Initial render
        this.checkSublease().then(() => {
            this.renderGraphic();
            this.renderWidget(); // Initial render
        });
    }

    renderGraphic() {
        const currentSlipName = this.slip.slipNum || "none";

        // Update the slip label and sublease information without clearing the image
        this.slipLabelDiv.innerHTML = `
        <span class="slip-label">Slip: </span>
        <span class="slip-name">${currentSlipName}</span>
    `;

        if (this.slip.subleasedTo) {
            this.slipLabelDiv.innerHTML += `
            <br><br>
            <span class="sublease-label">Sub: </span>
            <span class="sublease-name">${this.slip.subleasedTo}</span>
        `;
        }

        // Avoid re-fetching the image if slipNum hasn't changed
        if (this.graphicDiv.dataset.currentSlipName === currentSlipName) {
            return;
        }

        // Clear the image only if the slipNum changes
        this.graphicDiv.innerHTML = "";
        this.graphicDiv.appendChild(this.slipLabelDiv);

        const imageUrl = `/api/images/${currentSlipName}_icon.png`;

        const img = document.createElement("img");
        img.alt = `Slip: ${currentSlipName}`;

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
                this.graphicDiv.dataset.currentSlipName = currentSlipName; // Cache the current slip name
            })
            .catch((error) => {
                console.error("There was a problem with the fetch operation:", error);
                // Optional: Handle the error, e.g., show a placeholder image
            });
    }

    renderWidget() {
        this.widgetDiv.innerHTML = ""; // Clear the widget div

        // Check if slipNum is null
        if (!this.slip.slipNum) {
            return; // If slipNum is null, don't render anything
        }

        // If subleasedTo is filled, display only the "Release Sub" button
        if (this.slip.subleasedTo !== null) {
            const releaseButton = document.createElement("button");
            releaseButton.textContent = "Release Sub";
            releaseButton.classList.add("release-sub-button");

            // Add an event listener for the button (optional functionality)
            releaseButton.addEventListener("click", () => {
                console.log("Releasing sublease...");
                this.slip.subleasedTo = null; // Clear the sublease
            });

            this.widgetDiv.appendChild(releaseButton);
            return;
        }

        // Create a container for radio buttons when subleasedTo is null
        const radioContainer = document.createElement("div");
        radioContainer.classList.add("radio-container");

        radioContainer.innerHTML = `
        <label><input type="radio" name="action" value="sublease"> Sublease Slip</label><br>
        <label><input type="radio" name="action" value="reassign"> Reassign Slip</label>
    `;

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

    async checkSublease() {
        // Only check for subleasing if all fields in slip are null
        if (!this.slip.slipId && !this.slip.slipNum && !this.slip.subleasedTo) {
            try {
                const response = await fetch(`/api/check-sublease?msId=${this.msId}`);
                if (!response.ok) {
                    throw new Error("Failed to check sublease status");
                }

                const data = await response.json(); // Expecting { slipDTO: { slipNum: string | null, ownerId: number | null } }
                const subleaseInfo = data.slipDTO;

                if (subleaseInfo.slipNum && subleaseInfo.ownerId) {
                    this.slip.slipNum = subleaseInfo.slipNum;
                    this.subleaseOwnerId = subleaseInfo.ownerId; // Store owner ID for rendering
                } else {
                    this.subleaseOwnerId = null;
                }
                console.log("sublease info", data);
            } catch (error) {
                console.error("Error checking sublease status:", error);
            }
        }
    }

    getElement() {
        return this.container;
    }
} // checkSublease now returns slipDTO object if the person has no slip or is subleasing
// if they are subleasing all elements of slipDTO are filled with slipDTO.slipNum being the subleased slip number
// and altText