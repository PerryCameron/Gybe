function populatePersonBox(membershipJson) {
    console.log("Getting to populatePersonBox()");
    const tabPane = new TabPane(`person-tab-pane-${membershipJson.msId}`, "vertical");

    membershipJson.people.forEach(person => {
        const contentNode = tempContent(person)
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

function tempContent(person) {
    const div = document.createElement("div");
    div.innerHTML = `<p>Content for ${person.firstName}</p><br><br><br><br><br><br><br><br><br><br>`
    return div;
}
