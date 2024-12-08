
function createMembershipContent(membership) {
    const contentDiv = document.createElement("div");
    contentDiv.classList.add("vbox");
    // Append header immediately

    // Fetch the membership data once and pass it to both createPersonDiv and createMembershipDiv
    fetchMembershipData(membership).then(membershipData => {
        if (membershipData) {
            // Pass the fetched data to both functions
            contentDiv.appendChild(createHeaderDiv(membershipData));
            contentDiv.appendChild(createPersonDiv(membershipData));
            contentDiv.appendChild(AddressTitlePane(membershipData));
            const hBox = new HorizontalBox("membership-hbox-" + membershipData.id);
            hBox.add(MembershipTitlePane(membershipData));
            hBox.add(StorageTitlePane(membershipData));
            contentDiv.appendChild(hBox.getElement());  // why can't I do this?
            // contentDiv.classList.add("membership-content");

        } else {
            console.error('Unable to fetch membership data');
        }
    }).catch(error => {
        console.error('Error fetching membership data:', error);
    });
    return contentDiv;
}

function AddressTitlePane(membershipData) {
    const titledPane = new TitledPane("Address", "membership-section");
    titledPane.classList.add("address-titled-pane");
    titledPane.contentElement.appendChild(addressBlock(membershipData));
    return titledPane;
}

function StorageTitlePane(membershipData) {
    const titledPane = new TitledPane("Storage", "membership-section");
    titledPane.classList.add("address-storage-titled-pane");
    titledPane.contentElement.appendChild(storageTabPane(membershipData));
    return titledPane;
}

function MembershipTitlePane(membershipData) {
    const titledPane = new TitledPane("Membership", "membership-section");
    titledPane.classList.add("history-invoice-titled-pane");
    titledPane.contentElement.appendChild(membershipTabPane(membershipData));
    return titledPane;
}

function addressBlock(membershipData) {
    const historyDiv = document.createElement("div");
    const historyTable = new AddressBlock(membershipData);
    historyDiv.appendChild(historyTable.getElement());
    return historyDiv; // but no table rendered in this div??
}

function storageTabPane(membershipData) {
    const div = document.createElement("div");
    const tabPane = new TabPane(div, "horizontal");
    const slipWidget = new SlipWidget(membershipData);
    tabPane.addTab("storage-" + membershipData.msId, "Slip", slipWidget.getElement(), false);
    tabPane.switchToTab("storage-" + membershipData.msId);
    return div;
}

function membershipTabPane(membershipData) {
    const div = document.createElement("div");
    const tabPane = new TabPane(div, "horizontal");
    tabPane.addTab("history-" + membershipData.msId, "History", setHistoryTable(membershipData), false);
    tabPane.addTab("invoice-list-" + membershipData.msId, "Invoices", fakeContent("This is the invoice list"), false);
    tabPane.addTab("properties-" + membershipData.msId, "Properties", fakeContent("This is properties"), false);
    tabPane.switchToTab("history-" + membershipData.msId);
    return div;
}

function setHistoryTable(membershipData) {
    const historyDiv = document.createElement("div");
    const historyTable = new HistoryTable(historyDiv, membershipData);
    historyTable.mainContainer.classList.add("medium-table-container");// this is getting data and processing it
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
    topDiv.appendChild(labeledTextDiv("Record Year: ", membership.fiscalYear));
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