class SlipWidget {
    constructor(membershipData) {
        this.slip = { ...membershipData.slip }; // Initialize slip as a plain object
        this.msId = membershipData.msId;
        this.membershipId = membershipData.membershipId;
        this.selectedYear = membershipData.fiscalYear;
        this.subleaseOwnerId = null; // Tracks the ID of the slip's owner if subleasing

        this.container = document.createElement("div");
        this.container.classList.add("slip-storage");
        this.container.id = `slip-widget-${this.membershipId}`; // Assign a unique ID

        this.graphicDiv = document.createElement("div");
        this.graphicDiv.classList.add("slip-graphic");

        this.slipLabelDiv = document.createElement("div");
        this.slipLabelDiv.classList.add("slip-label");

        this.widgetDiv = document.createElement("div");
        this.widgetDiv.classList.add("slip-widget");

        this.container.appendChild(this.graphicDiv);
        this.container.appendChild(this.widgetDiv);

        // Link instance to DOM element
        this.container.widgetInstance = this;
        this.rebuildSlipWidget()
    }

    rebuildSlipWidget() {
        console.log("rebuildSlipWidget()", this.slip);
        this.checkSublease().then(() => {
            this.renderGraphic();
            this.renderWidget(); // Initial render
        });
    }

    async checkSublease() {
        console.log("All slip fields are null, checking if " + this.membershipId + " is subleasing a slip");
        // Only check for subleasing if all fields in slip are null
        if (!this.slip.slipId && !this.slip.slipNum && !this.slip.subleasedTo) {
            console.log("This object renders true, so we will actually check for the sublease now")
            checkForSublease(this.msId).then(data => {
                this.subleaseInfo = data.slipDTO;
                if (this.subleaseInfo.slipNum && this.subleaseInfo.altText) {
                    // Update slip info for a subleasing membership
                    this.slip.slipNum = this.subleaseInfo.slipNum;
                    this.subleaseOwnerId = this.subleaseInfo.altText; // Use altText for the original owner's ID
                    this.rebuildSlipWidget();
                } else {
                    // Reset sublease information if no valid data is returned
                    this.subleaseOwnerId = null;
                }
                console.log("Sublease info", data);
            });
        }
    }

    renderGraphic() {
        console.log("Rendering graphic");
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
        console.log("subleasedTo type:", typeof this.slip.subleasedTo);
        console.log("subleasedTo value:", this.slip.subleasedTo);
        if (this.subleaseOwnerId) {
            console.log("Rendering Subleased Owner for " + this.membershipId);
            this.renderSubleaseOwner();
            this.subleaseOwnerId = null;
            return;
        }
        if (this.slip.subleasedTo !== null) {
            console.log("Rendering Subleased To for " + this.membershipId);
            console.log("The value of subleased to is: " + this.slip.subleasedTo);
            this.renderSubleasedTo();  // why is this being called?
            return;
        }
        if (!this.slip.slipNum) {
            console.log("No slip number found, rendering nothing for " + this.membershipId);
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
        // Remove existing sublease container if it exists
        const existingContainer = document.querySelector('.sublease-container');
        if (existingContainer) {
            existingContainer.remove();
        }
        const subleaseContainer = document.createElement("div");
        subleaseContainer.classList.add("sublease-container");
        // fetch the current membership ID of the membership who is subleasing
        console.log("Getting Membership Id for the current membership subleasing the slip " + this.membershipId);
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
                    // launch tab of the membership who is subleasing if user clicks on it
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
                        releaseSublease(this.msId).then(() => {
                            // console.log("Release Sub", membership.membershipId);
                            this.slip.subleasedTo = null;
                            this.rebuildSlipWidget();
                            this.refreshWidgetById(membership.membershipId);
                        }).catch(error => {
                            console.error("Failed to release subleaser:", error);
                        })
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

    updateSlipObject(data) {
        // Temporarily disable rendering to batch updates
        console.log("Updating slip object for membership " + this.membershipId);
        // this fixes it to work correctly
        if(data.newSlipInfo.subleasedTo === 0) data.newSlipInfo.subleasedTo = null;
            this.slip = { ...this.slip, ...data.newSlipInfo };
        console.log("Slip object for membership " + this.membershipId + " is now updated, rendering widget");
        this.rebuildSlipWidget();
    }

    renderDefaultControls() {
        console.log("Rendering default controls for " + this.membershipId);
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
            changeSlip(membershipId, changeType, this.msId)
                .then(data => {
                    console.log("Changing slip new data= " + this.membershipId, data.newSlipInfo);
                    this.updateSlipObject(data)
                    // to refresh any open tabs that may have their data changed.
                    console.log("We successfully updated the membership with the controls")
                    this.refreshWidgetById(membershipId);
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

    async refresh() {
        getSlipInfo(this.msId).then(data => {
            this.updateSlipObject(data);
        })
    }

    refreshWidgetById(membershipId) {
        console.log("Refreshing...widget for the membership without controls: membershipId=" + membershipId);
        const widgetElement = document.getElementById(`slip-widget-${membershipId}`);
        if (widgetElement && widgetElement.widgetInstance) {
            widgetElement.widgetInstance.refresh();
        } else {
            console.error(`No widget instance found for ID: slip-widget-${membershipId}`);
        }
    }

    getElement() {
        return this.container;
    }
}

async function checkForSublease(msId) {
    console.log("checkForSublease() " + msId);
    try {
        const response = await fetch(`/api/check-sublease?msId=${msId}`);
        if (!response.ok) {
            throw new Error("Failed to check sublease status");
        }
        const data = await response.json(); // Expecting { slipDTO: { slipNum: string | null, ownerId: string | null } }
        return data;
    } catch (error) {
        console.error("Error checking sublease status:", error);
    }
}

async function getSlipInfo(ownerMsId) {
    try {
        const response = await fetch(`/api/get-slip-info?ownerMsId=${ownerMsId}`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        return data;
    } catch (error) {
        console.error("Error refreshing SlipWidget:", error);
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
        console.log("Getting membership ID for current subleaser=" + msId, data);
        return data;
    } catch (error) {
        console.error("Failed to fetch membership ID:", error);
        return null; // Return null or handle the error as appropriate
    }
}

async function releaseSublease(ownerMsId) {
    try {
        const response = await fetch(`/api/slip-sub-release?ownerMsId=${ownerMsId}`, {
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