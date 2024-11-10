function populatePersonBox(membershipJson, tabPane) {
    let primaryTab;
    membershipJson.people.forEach(person => {

        const contentNode = document.createElement("div");
        contentNode.classList.add("content-node-person");
        const personFieldPane = new EditableFieldsPane(person);
        const personExtraTabPaneDiv = document.createElement("div");
        personExtraTabPaneDiv.classList.add("person-extra-tabpane-div");
        contentNode.appendChild(personFieldPane);
        contentNode.appendChild(personExtraTabPaneDiv);

        if (person.memberType === 1) {
            tabPane.addTab(person.pId, "Primary", contentNode, false);
            primaryTab = person.pId;
        } else if (person.memberType === 2) {
            tabPane.addTab(person.pId, "Secondary", contentNode, false);
        } else {
            tabPane.addTab(person.pId, "Dependent", contentNode, false);
        }
        const extraTabPane = new TabPane(personExtraTabPaneDiv, "horizontal");
        extraTabPane.tabsContainer.classList.add("person-accessories-button-div");
        extraTabPane.container.id = "person-accessories-tab-pane";
        extraTabPane.tabsContainer.id = "person-accessories-tab-container";
        extraTabPane.addTab(`email-${person.pId}`, "Email", setEmailTable(person), false);
        extraTabPane.addTab(`phone-${person.pId}`, "Phone", setPhoneTable(person), false);
        extraTabPane.addTab(`awards-${person.pId}`, "Awards", fakeContent(person, "awards"), false);
        extraTabPane.addTab(`properties-${person.pId}`, "Properties", fakeContent(person, "properties"), false);
        extraTabPane.addTab(`position-${person.pId}`, "Position", setPositionTable(person), false);
        extraTabPane.switchToTab(`email-${person.pId}`);
    });
    tabPane.switchToTab(primaryTab);
    return tabPane;
}

function setPositionTable(person) {
    const positionDiv = document.createElement("div");
    const positionTable = new PositionTable(positionDiv, person);
    positionTable.mainContainer.classList.add("small-table-container");// this is getting data and processing it
    return positionDiv; // but no table rendered in this div??
}

// function setAwardsTable(person) {
//     const awardDiv = document.createElement("div");
//     const awardTable = new Table(awardDiv, person);
//     awardTable.mainContainer.classList.add("small-table-container");// this is getting data and processing it
//     return awardDiv; // but no table rendered in this div??
// }

function setEmailTable(person) {
    const emailDiv = document.createElement("div");
    const emailTable = new EmailTable(emailDiv, person);
    emailTable.mainContainer.classList.add("small-table-container");// this is getting data and processing it
    return emailDiv; // but no table rendered in this div??
}

function setPhoneTable(person) {
    const phoneDiv = document.createElement("div");
    const phoneTable = new PhoneTable(phoneDiv, person);
    phoneTable.mainContainer.classList.add("small-table-container");// this is getting data and processing it
    return phoneDiv; // but no table rendered in this div??
}

function fakeContent(person, type) {
    const fakeDiv = document.createElement("div");
    fakeDiv.classList.add("fake-div-testing");
    const fakeP = document.createElement("p");
    fakeDiv.appendChild(fakeP);
    fakeP.innerText = `This is a filler for ${type}`;
    let email = person.emails;
    if (type === "email")
        console.log("Emails: ", email);
    return fakeDiv;
}