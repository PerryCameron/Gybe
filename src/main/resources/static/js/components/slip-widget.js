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
        this.subleaseOwnerId = null; // Tracks the ID of the slip's owner if subleasing

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

        this.checkSublease().then(() => {
            this.renderGraphic();
            this.renderWidget(); // Initial render
        });
    }

    async checkSublease() {
        // Only check for subleasing if all fields in slip are null
        if (!this.slip.slipId && !this.slip.slipNum && !this.slip.subleasedTo) {
            try {
                const response = await fetch(`/api/check-sublease?msId=${this.msId}`);
                if (!response.ok) {
                    throw new Error("Failed to check sublease status");
                }

                const data = await response.json(); // Expecting { slipDTO: { slipNum: string | null, ownerId: string | null } }
                const subleaseInfo = data.slipDTO;

                if (subleaseInfo.slipNum && subleaseInfo.altText) {
                    // Update slip info for a subleasing membership
                    this.slip.slipNum = subleaseInfo.slipNum;
                    this.subleaseOwnerId = subleaseInfo.altText; // Use altText for the original owner's ID
                } else {
                    // Reset sublease information if no valid data is returned
                    this.subleaseOwnerId = null;
                }
                console.log("Sublease info", data);
            } catch (error) {
                console.error("Error checking sublease status:", error);
            }
        }
    }

    renderGraphic() {
        const currentSlipName = this.slip.slipNum || "none";

        // Update the slip label
        this.slipLabelDiv.innerHTML = `
        <span class="slip-label">Slip: </span>
        <span class="slip-name">${currentSlipName}</span>
    `;

        // Avoid re-fetching the image if slipNum hasn't changed
        if (this.graphicDiv.dataset.currentSlipName === currentSlipName) {
            // Update only the label if the slip name hasn't changed
            const existingLabel = this.graphicDiv.querySelector(".slip-label");
            if (existingLabel) {
                existingLabel.innerHTML = this.slipLabelDiv.innerHTML;
            }
            return;
        }

        // Clear the graphic and add the updated label
        this.graphicDiv.innerHTML = "";
        this.graphicDiv.appendChild(this.slipLabelDiv);

        // Fetch and display the slip image if slipNum is available
        if (this.slip.slipNum !== "none") {
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

                    // Ensure only one image is added
                    const existingImg = this.graphicDiv.querySelector("img");
                    if (!existingImg) {
                        this.graphicDiv.appendChild(img);
                    }

                    this.graphicDiv.dataset.currentSlipName = currentSlipName; // Cache the current slip name
                })
                .catch((error) => {
                    console.error("There was a problem with the fetch operation:", error);
                });
        }
    }
    
    renderWidget() {
        this.widgetDiv.innerHTML = ""; // Clear the widget div

        // If subleaseOwnerId is set, display "Subleased from <id>" message
        if (this.subleaseOwnerId) {
            const subleaseMessage = document.createElement("div");
            subleaseMessage.classList.add("sublease-message");
            subleaseMessage.textContent = `Subleased from Membership ${this.subleaseOwnerId}`;
            this.widgetDiv.appendChild(subleaseMessage);
            return;
        }

        // If subleasedTo is filled, display only the "Release Sub" button
        if (this.slip.subleasedTo !== null) {
            const releaseButton = document.createElement("button");
            releaseButton.textContent = "Release Sub";
            releaseButton.classList.add("release-sub-button");

            releaseButton.addEventListener("click", () => {
                console.log("Releasing sublease...");
                this.slip.subleasedTo = null; // Clear the sublease
            });

            this.widgetDiv.appendChild(releaseButton);
            return;
        }

        // Default controls for when there is no sublease
        if (!this.slip.slipNum) {
            return; // If slipNum is null, don't render anything
        }

        const radioContainer = document.createElement("div");
        radioContainer.classList.add("radio-container");

        radioContainer.innerHTML = `
            <label><input type="radio" name="action" value="sublease"> Sublease Slip</label><br>
            <label><input type="radio" name="action" value="reassign"> Reassign Slip</label>
        `;

        this.widgetDiv.appendChild(radioContainer);

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

        inputContainer.appendChild(textField);
        inputContainer.appendChild(submitButton);
        this.widgetDiv.appendChild(inputContainer);
    }

    getElement() {
        return this.container;
    }
}