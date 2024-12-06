function buildRosters(data) {
    globalRosterData = data.roster;
    const mainDiv = document.getElementById("main-content");
    const controlDiv = document.getElementById('pageNavigation');
    controlDiv.innerHTML = "";
    controlDiv.appendChild(createRecordDiv());
    controlDiv.appendChild(createSearchBarDiv());
    controlDiv.appendChild(createYearSelectDiv());
    controlDiv.appendChild(createButtons());
    const membershipTabPane = document.createElement("div");
    membershipTabPane.id = "roster-tab-div";
    mainDiv.innerHTML = ""; // may make sense to create an array of divs, to maintain state even when not on tab, hmm?
    mainDiv.appendChild(membershipTabPane);
    const rosterTabPane = new TabPane(membershipTabPane, "horizontal");

    // Attach the TabPane object to the div
    membershipTabPane.tabPaneInstance = rosterTabPane;
    // how can I get this TabPane object later?
    rosterTabPane.addTab("roster", "Roster", createTable(rosterTabPane), false);
}

function createTable(rosterTabPane) {
    const table = document.createElement("table");
    table.className = "styled-table";
    const thead = document.createElement("thead");
    thead.className = "header-link";
    thead.innerHTML = `
      <tr>
        <th id="first-header" class="header" scope="col">ID<span id="arrow-membershipId"></span></th>
        <th class="header" style="width: 12%" scope="col">Join Date<span id="arrow-joinDate"></span></th>
        <th class="header" scope="col">Type<span id="arrow-memType"></span></th>
        <th class="header" scope="col">Slip<span id="arrow-slip"></span></th>
        <th class="header" scope="col">First Name<span id="arrow-firstName"></span></th>
        <th class="header" scope="col">Last Name<span id="arrow-lastName"></span></th>
        <th class="header" scope="col">City<span id="arrow-city"></span></th>
      </tr>
    `;

    // Attach event listeners instead of inline onclick
    thead.querySelectorAll("th").forEach((th, index) => {
        const sortColumns = ["membershipId", "joinDate", "memType", "slip", "firstName", "lastName", "city"];
        th.addEventListener("click", () => sortTable(sortColumns[index]));
    });
    table.appendChild(thead);
    const tbody = document.createElement("tbody");
    globalRosterData.membershipListDTOS.forEach((membership) => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
        <td>${membership.membershipId}</td>
        <td>${membership.joinDate}</td>
        <td>${membership.memType}</td>
        <td>${membership.slip || ""}</td>
        <td>${membership.firstName}</td>
        <td>${membership.lastName}</td>
        <td>${membership.city}</td>
      `;
        tr.addEventListener('click', () => {
            // membership-rest.js
            addNewTab(membership.msId, `Mem ${membership.membershipId}`, createMembershipContent(membership), true);
        });
        tbody.appendChild(tr);
    });
    table.appendChild(tbody);
    return table;
}

function addNewTab(tabId, label, content, closable) {
    const membershipTabPane = document.getElementById("roster-tab-div");
    if (membershipTabPane?.tabPaneInstance) {
        const tabPane = membershipTabPane.tabPaneInstance; // Get the TabPane instance
        if (tabPane.tabExists(tabId)) {
            tabPane.switchToTab(tabId); // Use the TabPane instance's method
        } else {
            tabPane.addTab(tabId, label, content, closable); // Add the new tab
        }
    }
}

function replaceTable() {
    // const rosterDiv = document.querySelector('div[data-tab-content="roster"]');
    const rosterDiv = document.getElementById("tab-content-roster");
    const tabsDiv = document.getElementById("content-roster");
    rosterDiv.innerHTML = "";
    rosterDiv.appendChild(createTable());
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

    // Populate the select element with years from the current year down to 1970
    const currentYear = new Date().getFullYear();
    for (let year = currentYear; year >= 1970; year--) {
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