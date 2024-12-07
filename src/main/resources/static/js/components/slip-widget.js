class SlipWidget {
    constructor(membershipData) {
        this.slip = new Proxy(membershipData.slip, {
            set: (target, property, value) => {
                target[property] = value;
                this.renderGraphic(); // Update the graphic whenever the slip changes
                this.renderWidget(); // Re-render the widget whenever the slip changes
                return true;
            }
        });
        this.msId = membershipData.msId;
        this.selectedYear = membershipData.fiscalYear;
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
                this.subleaseInfo = data.slipDTO;
                if (this.subleaseInfo.slipNum && this.subleaseInfo.altText) {
                    // Update slip info for a subleasing membership
                    this.slip.slipNum = this.subleaseInfo.slipNum;
                    this.subleaseOwnerId = this.subleaseInfo.altText; // Use altText for the original owner's ID
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
        console.log("Rendering widget"); // Add this to `renderWidget`
        this.widgetDiv.innerHTML = ""; // Clear the widget div
        if (this.subleaseOwnerId) {
            this.renderSubleaseOwner();
            return;
        }
        if (this.slip.subleasedTo !== null) {
            this.renderSubleasedTo();
            return;
        }
        if (!this.slip.slipNum) {
            return; // If slipNum is null, don't render anything
        }
        this.renderDefaultControls();
    }

    renderSubleaseOwner() {
        const subleaseMessage = document.createElement("div");
        subleaseMessage.classList.add("sublease-message");
        const subleaseLink = document.createElement("a");
        subleaseLink.href = "#";
        subleaseLink.textContent = `Membership ${this.subleaseOwnerId}`;
        const membership = {
            msId: this.subleaseInfo.msId,
            selectedYear: this.selectedYear,
            membershipId: this.subleaseInfo.altText
        };
        subleaseLink.addEventListener("click", (event) => {
            event.preventDefault();
            addNewTab(`${membership.msId}`, `Mem ${membership.membershipId}`, createMembershipContent(membership), true);
        });
        subleaseMessage.textContent = "Subleased from ";
        subleaseMessage.appendChild(subleaseLink);
        this.widgetDiv.appendChild(subleaseMessage);
    }

    renderSubleasedTo() {
        console.log("Rendering subleasedTo"); // Add this to `renderSubleasedTo`
        // Remove existing sublease container if it exists
        const existingContainer = document.querySelector('.sublease-container');
        if (existingContainer) {
            existingContainer.remove();
        }

        const subleaseContainer = document.createElement("div");
        subleaseContainer.classList.add("sublease-container");
        getSubleaserId(this.slip.subleasedTo)
            .then(subleaserInfo => {
                if (subleaserInfo) {
                    const subleaseInfo = document.createElement("span");
                    const subleaseToLink = document.createElement("a");
                    subleaseToLink.href = "#";
                    subleaseToLink.textContent = `Membership ${subleaserInfo.membershipIdDTO.membershipId}`;
                    const membership = {
                        msId: subleaserInfo.membershipIdDTO.msId,
                        selectedYear: subleaserInfo.membershipIdDTO.fiscalYear,
                        membershipId: subleaserInfo.membershipIdDTO.membershipId
                    };
                    subleaseToLink.addEventListener("click", (event) => {
                        event.preventDefault();
                        addNewTab(`${membership.msId}`, `Mem ${membership.membershipId}`, createMembershipContent(membership), true);
                    });
                    subleaseInfo.textContent = "Subleased to ";
                    subleaseInfo.appendChild(subleaseToLink);
                    subleaseContainer.appendChild(subleaseInfo);
                    const releaseButton = document.createElement("button");
                    releaseButton.textContent = "Release Sub";
                    releaseButton.classList.add("release-sub-button");
                    releaseButton.addEventListener("click", () => {
                        console.log("Releasing sublease...");
                        this.slip.subleasedTo = null;
                        this.renderWidget();
                    });
                    subleaseContainer.appendChild(releaseButton);
                    this.widgetDiv.appendChild(subleaseContainer);
                } else {
                    console.warn("Subleaser information not available.");
                }
            })
            .catch(error => {
                console.error("Failed to fetch subleaser information:", error);
            });
    }

    renderDefaultControls() {
        const radioContainer = document.createElement("div");
        radioContainer.classList.add("radio-container");
        radioContainer.innerHTML = `
        <label><input type="radio" name="action" value="sublease" checked> Sublease Slip</label><br>
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

        // Add click event listener for validation
        submitButton.addEventListener("click", (event) => {
            event.preventDefault(); // Prevent default form submission

            const inputValue = textField.value.trim();

            // Check if the input is an integer
            if (!/^\d+$/.test(inputValue)) {
                alert("Please enter a valid integer for Membership ID.");
                return; // Stop further execution if validation fails
            }

            const selectedRadio = document.querySelector('input[name="action"]:checked');
            if (!selectedRadio) {
                alert("Please select an action.");
                return; // Stop if no radio button is selected
            }
            const changeType = selectedRadio.value;

            // Proceed with the endpoint call
            const membershipId = parseInt(inputValue, 10);
            console.log("Validated Membership ID:", membershipId);
            console.log("Selected Change Type:", changeType);
            changeSlip(membershipId, changeType, this.msId)
                .then(data => {
                    console.log("New Slip Data:", data.newSlipInfo);

                    // Temporarily disable rendering to batch updates
                    const oldRenderWidget = this.renderWidget;
                    this.renderWidget = () => {}; // No-op

                    // Update the proxy object with the new data
                    for (const key in data.newSlipInfo) {
                        if (data.newSlipInfo.hasOwnProperty(key)) {
                            this.slip[key] = data.newSlipInfo[key]; // Triggers Proxy `set`
                        }
                    }

                    // Restore renderWidget and call it once
                    this.renderWidget = oldRenderWidget;
                    this.renderWidget();
                })
                .catch(error => {
                    console.error("Error handling slip change:", error);
                    alert("An error occurred while processing your request.");
                });
        });
        inputContainer.appendChild(textField);
        inputContainer.appendChild(submitButton);
        this.widgetDiv.appendChild(inputContainer);
    }

    getElement() {
        return this.container;
    }
}

async function changeSlip(membershipId, changeType, ownerMsId) {
    try {
        const response = await fetch(`/api/slip-change?membershipId=${membershipId}&changeType=${changeType}&ownerMsId=${ownerMsId}`, {
            method: "GET",
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        return data; // Resolve the Promise with the data
    } catch (error) {
        console.error("Error:", error);
        throw error; // Rethrow the error to be handled by the caller
    }
}

async function getSubleaserId(msId) {
    try {
        const response = await fetch(`/api/get-membershipId?msId=${msId}`);
        if (!response.ok) {
            throw new Error(`Error fetching membership ID: ${response.statusText}`);
        }
        const data = await response.json();
        console.log("Sublease data", data);
        return data;
    } catch (error) {
        console.error("Failed to fetch membership ID:", error);
        return null; // Return null or handle the error as appropriate
    }
}