function populatePersonBox(membershipJson) {
    console.log("Getting to populatePersonBox()");
    const tabPane = new TabPane(`person-tab-pane-${membershipJson.msId}`, "vertical");

    membershipJson.people.forEach(person => {
        const contentHtml = `<p>Content for ${person.firstName}</p><br><br><br><br><br><br><br><br><br><br>`;
        if(person.memberType === 1) {
            tabPane.addTab(person.pId, "Primary", contentHtml, false);
        } else if(person.memberType === 2) {
            tabPane.addTab(person.pId, "Secondary", contentHtml, false);
        } else {
            tabPane.addTab(person.pId, "Dependent", contentHtml, false);
        }
    });
    return tabPane;
}
