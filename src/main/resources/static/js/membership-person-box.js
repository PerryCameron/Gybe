function populatePersonBox(membershipJson) {

    const tabPane = new TabPane(`person-tab-pane-${membershipJson.msId}`, "vertical");
    tabPane.setContentClass("tab-content-style");

    membershipJson.people.forEach(person => {
        console.log("person: ", person);
        const contentNode = new EditableFieldsPane(personFields, person);
        if(person.memberType === 1) {
            tabPane.addTab(person.pId, "Primary", contentNode, false);
        } else if(person.memberType === 2) {
            tabPane.addTab(person.pId, "Secondary", contentNode, false);
        } else {
            tabPane.addTab(person.pId, "Dependent", contentNode, false);
        }
    });
    return tabPane;
}

