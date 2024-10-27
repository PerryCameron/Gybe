

function populatePersonBox(membershipJson, tabPane) {

    // const tabPane = new TabPane(`person-tab-pane-${membershipJson.msId}`, "vertical");
    // tabPane.setContentClass("tab-content-style");

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
        // const extraTabPane = new TabPane("person-extra-tabpane-div","horizontal");
        // extraTabPane.addTab(`email-${person.pId}`,fakeContent(),false);
        // extraTabPane.addTab(`phone-${person.pId}`,fakeContent(),false);
        // extraTabPane.addTab(`awards-${person.pId}`,fakeContent(),false);
        // extraTabPane.addTab(`position-${person.pId}`,fakeContent(),false);
        // extraTabPane.switchToTab(`phone-${person.pId}`);
    });
    return tabPane;
}

function fakeContent() {
    const fakeP = document.createElement("p");
    p.innerText="yes";
    return fakeP;
}