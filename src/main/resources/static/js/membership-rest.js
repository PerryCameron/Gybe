
function createMembershipContent(membership) {
    const contentDiv = document.createElement("div");
    contentDiv.classList.add("vbox");
    // Append header immediately
    contentDiv.appendChild(createHeaderDiv(membership));
    // Fetch the membership data once and pass it to both createPersonDiv and createMembershipDiv
    fetchMembershipData(membership).then(membershipData => {
        if (membershipData) {
            // Pass the fetched data to both functions
            contentDiv.appendChild(createPersonDiv(membershipData));
            const hBox = new HorizontalBox("membership-hbox");
            hBox.add(MembershipTitlePane(membershipData));
            hBox.add(AddressAndStorageTitlePane(membershipData));
            contentDiv.appendChild(hBox.getElement());  // why can't I do this?

        } else {
            console.error('Unable to fetch membership data');
        }
    }).catch(error => {
        console.error('Error fetching membership data:', error);
    });
    return contentDiv;
}

function AddressAndStorageTitlePane(membershipData) {
    const titledPane = new TitledPane("Address and Storage", "membership-section");
    // Use membershipData if needed, for example:
    // titledPane.setContentId(`membership-pane-${membershipData.msId}`);
    titledPane.classList.add("address-storage-titled-pane");
    titledPane.contentElement.appendChild(addressAndStorageTabPane(membershipData));
    // Additional content population logic
    return titledPane;
}

function MembershipTitlePane(membershipData) {
    const titledPane = new TitledPane("Membership", "membership-section");
    // Use membershipData if needed, for example:
    // titledPane.setContentId(`membership-pane-${membershipData.msId}`);
    titledPane.classList.add("history-invoice-titled-pane");
    titledPane.contentElement.appendChild(membershipTabPane(membershipData));
    // Additional content population logic
    return titledPane;
}

function addressAndStorageTabPane(membershipData) {
    const div = document.createElement("div");
    const tabPane = new TabPane(div, "horizontal");
    tabPane.addTab("address-" + membershipData.pId, "Address", setAddressBlock(membershipData), false);
    tabPane.addTab("storage-" + membershipData.pId, "Storage", fakeContent("This is storage"), false);
    return div;
}

function membershipTabPane(membershipData) {
    const div = document.createElement("div");
    const tabPane = new TabPane(div, "horizontal");
    tabPane.addTab("history-" + membershipData.pId, "History", setHistoryTable(membershipData), false);
    tabPane.addTab("invoice-list-" + membershipData.pId, "Invoices", fakeContent("This is the invoice list"), false);
    tabPane.addTab("properties-" + membershipData.pId, "Properties", fakeContent("This is properties"), false);
    return div;
}

function setAddressBlock(membershipData) {
    const historyDiv = document.createElement("div");
    const historyTable = new AddressBlock(membershipData);
    historyDiv.appendChild(historyTable.getElement());
    return historyDiv; // but no table rendered in this div??
}

function setHistoryTable(membershipData) {
    const historyDiv = document.createElement("div");
    const historyTable = new HistoryTable(historyDiv, membershipData);
    historyTable.mainContainer.classList.add("small-table-container");// this is getting data and processing it
    return historyDiv; // but no table rendered in this div??
}

function createPersonDiv(membershipData) {
    const titledPane = new TitledPane("People", "membership-section");
    titledPane.setContentId(`person-tab-pane-${membershipData.msId}`);
    const tabPane = new TabPane(titledPane.getContentDiv(), "vertical");
    tabPane.contentContainer.classList.add("tab-content-style");
    populatePersonBox(membershipData, tabPane); // Populate with the fetched data
    return titledPane;
}

function createHeaderDiv(membership) {
    // Add content dynamically here
    const topDiv = document.createElement("div");
    topDiv.classList.add("top-div");
    topDiv.appendChild(labeledTextDiv("Record Year: ", membership.selectedYear));
    topDiv.appendChild(labeledTextDiv("Membership: ", membership.membershipId));
    topDiv.appendChild(labeledTextDiv("Type: ", membership.memType));
    topDiv.appendChild(labeledTextDiv("Join Date: ", membership.joinDate));
    return topDiv;
}

function labeledTextDiv(label, text) {
    const div = document.createElement("div");
    div.classList.add("labeled-text-div");
    const labelElement = document.createElement("p");
    const textElement = document.createElement("p");
    labelElement.innerText = (label);
    labelElement.classList.add("labelText");
    textElement.innerText = (text);
    textElement.classList.add("infoText");
    div.appendChild(labelElement);
    div.appendChild(textElement);
    return div;
}

async function fetchMembershipData(membership) {
    try {
        const response = await fetch(`/api/membership-rest?msId=${membership.msId}&year=${membership.selectedYear}`);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const data = await response.json();
        console.log('Membership Data:', data);
        return data; // Return the data so it can be used
    } catch (error) {
        console.error('Error fetching membership data:', error);
        return null;
    }
}