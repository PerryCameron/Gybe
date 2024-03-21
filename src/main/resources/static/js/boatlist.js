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
      <th class="header" scope="col" onclick="sortTable('lName')">Last Name<span id="arrow-lName"></th>
      <th class="header" scope="col" onclick="sortTable('fName')">First Name<span id="arrow-fName"></th>
      <th class="header" scope="col" onclick="sortTable('model')">Model<span id="arrow-model"></th>
      <th class="header" scope="col" onclick="sortTable('registrationNum')">Registration<span id="arrow-registrationNum"></th>
      <th class="header" scope="col" onclick="sortTable('boatName')">Boat Name<span id="arrow-boatName"></th>
      <th class="header" scope="col" onclick="sortTable('numberOfImages')">Images<span id="arrow-numberOfImages"></th>
    </tr>
  `;

  table.appendChild(thead);

  const tbody = document.createElement("tbody");
  boats.boatListDTOS.forEach((boat) => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
          <td>${boat.membershipId}</td>
          <td>${boat.lName}</td>
          <td>${boat.fName}</td>
          <td>${boat.model || ""}</td>
          <td>${boat.registrationNum || ""}</td>
          <td>${boat.boatName || ""}</td>
          <td>${boat.numberOfImages}</td>
        `;
    tr.addEventListener('click', () => {
      window.open(`/boat?boatId=${boat.boatId}`, '_blank');
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

  boats.boatListDTOS.sort((a, b) => {
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
