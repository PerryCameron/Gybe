const mainDiv = document.getElementById("main");
let lastSortedColumn = null;
let sortDirection = "asc"; // 'asc' for ascending, 'desc' for descending

function createTable() {
    const table = document.createElement("table");
    table.className = "styled-table";

    const thead = document.createElement("thead");
    thead.className = "header-link";
    thead.innerHTML = `
  <tr>
    <th class="header" scope="col" onclick="sortTable('membershipId')">ID<span id="arrow-membershipId"></span></th>
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
    rosters.membershipListDTOS.forEach((member) => {
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
            window.open(`/membership?msId=${member.msId}&selectedYear=${rosters.year}`, '_blank');
        });
        tbody.appendChild(tr);
    });
    table.appendChild(tbody);
    return table;
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

    rosters.membershipListDTOS.sort((a, b) => {
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

function replaceTable() {
    mainDiv.innerHTML = "";
    mainDiv.appendChild(createTable());
}

mainDiv.appendChild(createTable());
