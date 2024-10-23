// tabContent.js
function createMembershipContent(membership) {
    const contentDiv = document.createElement("div");

    // Add content dynamically here
    const nameElement = document.createElement("h2");
    nameElement.textContent = `Welcome, ${membership.firstName}!`;

    const detailsElement = document.createElement("p");
    detailsElement.textContent = `Membership ID: ${membership.membershipId}`;

    contentDiv.appendChild(nameElement);
    contentDiv.appendChild(detailsElement);

    return contentDiv;
}