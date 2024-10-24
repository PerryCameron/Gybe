
function createMembershipContent(membership, year) {
    const contentDiv = document.createElement("div");
    contentDiv.classList.add("vbox");
    contentDiv.appendChild(createHeaderDiv(membership, year));
    return contentDiv;
}

function createHeaderDiv(membership, year) {
    // Add content dynamically here
    const topDiv = document.createElement("div");
    topDiv.classList.add("top-div");
    topDiv.appendChild(labeledTextDiv("Record Year: ", year));
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