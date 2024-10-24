/**
 * @typedef {Object} Membership
 * @property {number} msId
 * @property {number} pId
 * @property {string} fullName
 * @property {string} address
 * @property {string} city
 * @property {string} state
 * @property {string} zip
 * @property {string} [slip]
 * @property {string} memType
 * @property {string} joinDate
 * @property {number} membershipId
 */

/**
 * @param {Object} data
 * @param {Membership[]} data.roster.membershipListDTOS
 */

function buildRosters(data) {
    globalRosterData = data.roster;
    const mainDiv = document.getElementById("main-content");
    const controlDiv = document.getElementById('pageNavigation');
    controlDiv.innerHTML = "";
    controlDiv.appendChild(createRecordDiv());
    controlDiv.appendChild(createSearchBarDiv());
    controlDiv.appendChild(createYearSelectDiv());
    controlDiv.appendChild(createButtons());
    const tabsDiv = document.createElement("div");
    tabsDiv.classList.add("tabs")
    tabsDiv.id = "roster-tab-div";
    mainDiv.innerHTML = "";
    mainDiv.appendChild(tabsDiv);
    addTab("Roster", createTable());
}

function createTable() {
    const table = document.createElement("table");
    table.className = "styled-table";
    const thead = document.createElement("thead");
    thead.className = "header-link";
    thead.innerHTML = `
  <tr>
    <th id="first-header" class="header" scope="col" onclick="sortTable('membershipId')">ID<span id="arrow-membershipId"></span></th>
    <th class="header" style="width: 12%" scope="col" onclick="sortTable('joinDate')">Join Date<span id="arrow-joinDate"></th>
    <th class="header" scope="col" onclick="sortTable('memType')">Type<span id="arrow-memType"></th>
    <th class="header" scope="col" onclick="sortTable('slip')">Slip<span id="arrow-slip"></th>
    <th class="header" scope="col" onclick="sortTable('firstName')">First Name<span id="arrow-firstName"></th>
    <th class="header" scope="col" onclick="sortTable('lastName')">Last Name<span id="arrow-lastName"></th>
    <th class="header" scope="col" onclick="sortTable('city')">City<span id="arrow-city"></th>
  </tr>
`;
    table.appendChild(thead);
    const tbody = document.createElement("tbody");
    globalRosterData.membershipListDTOS.forEach((member) => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
        <td>${member.membershipId}</td>
        <td>${member.joinDate}</td>
        <td>${member.memType}</td>
        <td>${member.slip || ""}</td>
        <td>${member.firstName}</td>
        <td>${member.lastName}</td>
        <td>${member.city}</td>
      `;
        tr.addEventListener('click', () => {
            // window.open(`/membership?msId=${member.msId}&selectedYear=${globalRosterData.year}`, '_blank');
            launchMembershipTab(member);
        });
        tbody.appendChild(tr);
    });
    table.appendChild(tbody);
    return table;
}

function launchMembershipTab(membership) {
    // Normalize the id to ensure consistency
    const tabId = `Tab${membership.membershipId}`;  // Use dash instead of space for id
    // Check if a tab with the same msId already exists
    if (document.getElementById(tabId)) {
        console.log(`Tab${membership.membershipId} already exists.`);
        // Switch to the existing tab if exists
        document.getElementById(tabId).click();
        return;
    }
    // Add the new tab using the corrected id
    addTab(membership, createMembershipContent(membership, globalRosterData.year));  // Pass tabId to keep track of the unique identifier
}

function addTab(membership, content) {
    const mainDiv = document.getElementById("main-content");
    const tabsDiv = document.getElementById("roster-tab-div");
    const contentDiv = document.createElement("div");
    const button = document.createElement("button");
    if(membership === "Roster") {
        button.id = `tab-roster`;
        contentDiv.id = `content-roster`;
        button.textContent = `Roster`;
        button.setAttribute('onclick', `showTab(event, 'content-roster')`);
    } else {
        contentDiv.id = `content-${membership.msId}`;
        button.id = `tab-${membership.msId}`;
        button.textContent = `Mem ${membership.membershipId}`
        button.setAttribute('onclick', `showTab(event, 'content-${membership.msId}')`);
        const closeButton = document.createElement("span");
        closeButton.innerHTML = "&times;";  // HTML for '×' symbol
        closeButton.classList.add("close-tab");
        closeButton.onclick = function (event) {
            // Prevent tab from switching when closing
            event.stopPropagation();
            closeTab(membership.msId);
        };
        button.appendChild(closeButton);
    }
    contentDiv.classList.add("tab-content");
    button.classList.add("tab");
    contentDiv.appendChild(content);
    tabsDiv.appendChild(button);
    mainDiv.appendChild(contentDiv);
    button.click();
}

function replaceTable() {
    const tabsDiv = document.getElementById("content-roster");
    tabsDiv.innerHTML = "";
    tabsDiv.appendChild(createTable());
}

function closeTab(tabId) {
    console.log("Closing tab: " + tabId);
    // Remove the tab button
    const tabButton = document.getElementById(`tab-${tabId}`);
    if (tabButton) {
        tabButton.remove();
    }

    // Remove the tab content
    const tabContent = document.getElementById(`content-${tabId}`);
    if (tabContent) {
        tabContent.remove();
    }

    // Optional: Activate another tab when one is closed
    const remainingTabs = document.querySelectorAll('.tab');
    if (remainingTabs.length > 0) {
        remainingTabs[0].click();  // Automatically switch to the first remaining tab
    }
}

function showTab(event, tabId) {
    // Hide all tab content
    const tabContent = document.querySelectorAll('.tab-content');
    tabContent.forEach(content => content.style.display = 'none');

    // Remove active class from all tabs
    const tabs = document.querySelectorAll('.tab');
    tabs.forEach(tab => tab.classList.remove('active'));

    // Show the clicked tab content
    document.getElementById(tabId).style.display = 'block';

    // Set the clicked tab as active
    event.currentTarget.classList.add('active');
}

function sortTable(column) {
    // Clear all arrows
    document.querySelectorAll(".header span").forEach((span) => {
        span.textContent = "";
    });
    if (column === lastSortedColumn) {
        // Toggle the sort direction
        sortDirection = sortDirection === "asc" ? "desc" : "asc";
    } else {
        // If a new column is clicked, start with ascending sort
        sortDirection = "asc";
    }
    globalRosterData.membershipListDTOS.sort((a, b) => {
        let comparison = 0;
        if (a[column] < b[column]) {
            comparison = -1;
        } else if (a[column] > b[column]) {
            comparison = 1;
        }
        return sortDirection === "asc" ? comparison : -comparison;
    });
    replaceTable();
    lastSortedColumn = column; // Update the last sorted column
    // Set the arrow for the sorted column
    const arrow = document.getElementById(`arrow-${column}`);
    if (arrow) {
        arrow.textContent = sortDirection === "asc" ? "↑" : "↓";
    }
}

// updating the side nav
function createButtons() {
    const buttonContainer = document.createElement("div");
    buttonContainer.className = "button-container";

    let buttons = [
        {text: "All", value: "all"},
        {text: "Active", value: "active"},
        {text: "Non-Renew", value: "non_renew"},
        {text: "New Members", value: "new_members"},
        {text: "Return Members", value: "return_members"},
        {text: "Slip Wait-list", value: "slip_wait_list"},
    ];

    buttons.forEach(function (button) {
        const a = document.createElement("a");
        a.textContent = button.text;
        if (button.value) a.value = button.value;

        a.addEventListener("click", function () {
            // Remove highlighted class from all links
            document.querySelectorAll("a").forEach(function (el) {
                el.classList.remove("selected");
            });
            // Add highlighted class to the clicked link
            this.classList.add("selected");
            globalRosterData.rosterType = this.value;
            fetchData();
        });

        if (a.value === globalRosterData.rosterType) a.classList.add("selected");

        buttonContainer.appendChild(a);
    });
    return buttonContainer;
}

function fetchData(searchParams = []) {
    let url = "";
    if (searchParams.length > 0) {
        url = `/rb_roster?type=search&year=${globalRosterData.year}`;
        searchParams.forEach((param, index) => {
            url += `&param${index + 1}=${encodeURIComponent(param)}`;
        });
    } else {
        url = `/rb_roster?type=${globalRosterData.rosterType}&year=${globalRosterData.year}`;
    }

    // Use the fetch API to send a GET request to the server
    fetch(url)
        .then(response => response.json())
        .then(data => {
            // Update the rosters.membershipListDTOS array with the new data
            globalRosterData.membershipListDTOS = data.membershipListDTOS;
            // Rebuild the table with the updated data
            replaceTable();
            updateRecordDiv();
        })
        .catch(error => {
            console.error('Error fetching roster data:', error);
        });
}

function createRecordDiv() {
    const recordContentDiv = document.createElement("div");
    recordContentDiv.id = "record-content-div";
    const label = document.createElement("label");
    label.id = "numb-of-records";
    label.textContent = `Records: ${globalRosterData.membershipListDTOS.length}`;
    recordContentDiv.appendChild(label);
    return recordContentDiv;
}

function updateRecordDiv() {
    const label = document.getElementById("numb-of-records")
    label.textContent = `Records: ${globalRosterData.membershipListDTOS.length}`;
}

function createYearSelectDiv() {
    const yearContainer = document.createElement("div");
    yearContainer.id = "sidenav-year-container";

    const yearSelect = document.createElement("select");
    yearSelect.id = "sidenav-yearselect";
    yearSelect.className = "sidenav-control";
    yearSelect.name = "year";

    // Populate the select element with years from 1970 to the present year
    const currentYear = new Date().getFullYear();
    for (let year = 1970; year <= currentYear; year++) {
        const option = document.createElement("option");
        option.value = year;
        option.textContent = year;
        yearSelect.appendChild(option);
    }

    // Set the selected year to the value in rosters.year
    if (globalRosterData.year) {
        yearSelect.value = globalRosterData.year;
    }

    yearSelect.addEventListener("change", function () {
        globalRosterData.year = this.value;
        fetchData();
    });

    yearContainer.appendChild(yearSelect);
    return yearContainer;
}

function createSearchBarDiv() {
    const searchBarDiv = document.createElement("div");
    searchBarDiv.id = "search-bar-div";
    const input = document.createElement("input");
    input.id = "search";
    input.placeholder = "Search";
    input.type = "text";
    input.className = "sidenav-control";
    input.addEventListener("keyup", handleKeyUp);
    searchBarDiv.appendChild(input);
    return searchBarDiv;
}

function handleKeyUp() {
    // Clear the previous timeout if the user types something new before the timeout is reached
    clearTimeout(timeout);

    // Set a new timeout
    timeout = setTimeout(() => {
        // Get the value of the input
        const searchValue = document.getElementById("search").value.trim();
        // Check if searchValue is not an empty string
        if (searchValue !== "") {
            // Split the searchValue into an array
            let valuesArray = searchValue.split(" "); // Splitting the string into an array
            // Fetch data with the search parameters
            fetchData(valuesArray);
        } else {
            // If the search field is empty, fetch data without search parameters
            fetchData();
        }
    }, 1000); // 1000 milliseconds = 1 second
}