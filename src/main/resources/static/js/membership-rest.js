
function createMembershipContent(membership) {
    const contentDiv = document.createElement("div");
    console.log(membership);
    contentDiv.classList.add("vbox");
    contentDiv.appendChild(createHeaderDiv(membership));
    // Fetch the membership data and append the person box once the data is available
    fetchMembershipData(membership).then(membershipJson => {
        if (membershipJson) {
            // Append the person box after the data has been fetched
            contentDiv.appendChild(createPersonBox(membershipJson));
            populatePersonBox(membershipJson);
        } else {
            console.error('No membership data returned');
        }
    }).catch(error => {
        console.error('Error fetching or appending membership data:', error);
    });
    return contentDiv;
}

function createPersonBox(membershipJson) {
    const personDiv = document.createElement("div");
    personDiv.classList.add("membership-section");
    personDiv.id = `person-tab-pane-${membershipJson.msId}`;
    return personDiv;
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
    }
}