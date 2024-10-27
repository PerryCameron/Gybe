

function populatePersonBox(membershipJson, tabPane) {

    membershipJson.people.forEach(person => {
        // console.log("person: ", person);
        // const contentNode = new EditableFieldsPane(personFields, person);

        const contentNode = document.createElement("div");
        contentNode.classList.add("content-node-person");
        const personFieldPane = new EditableFieldsPane(personFields, person);
        const personExtraTabPaneDiv = document.createElement("div");
        personExtraTabPaneDiv.classList.add("person-extra-tabpane-div");
        contentNode.appendChild(personFieldPane);
        contentNode.appendChild(personExtraTabPaneDiv);
        if(person.memberType === 1) {
            tabPane.addTab(person.pId, "Primary", contentNode, false);
        } else if(person.memberType === 2) {
            tabPane.addTab(person.pId, "Secondary", contentNode, false);
        } else {
            tabPane.addTab(person.pId, "Dependent", contentNode, false);
        }
        const extraTabPane = new TabPane(personExtraTabPaneDiv,"horizontal");
        extraTabPane.addTab(`email-${person.pId}`,"Email", fakeContent("email"),false);
        extraTabPane.addTab(`phone-${person.pId}`,"Phone", fakeContent("phone"),false);
        extraTabPane.addTab(`awards-${person.pId}`,"Awards", fakeContent("awards"),false);
        extraTabPane.addTab(`position-${person.pId}`,"Position", fakeContent("position"),false);
        extraTabPane.switchToTab(`phone-${person.pId}`);
    });
    return tabPane;
}

function fakeContent(type) {
    const fakeP = document.createElement("p");
    fakeP.innerText=`This is a filler for ${type}`;
    return fakeP;
}