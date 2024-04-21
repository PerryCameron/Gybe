document.addEventListener("DOMContentLoaded", function () {
  const sidenav = document.querySelector(".sidenav");
  sidenav.appendChild(createLogo());
  sidenav.appendChild(createBoatInfo());
  sidenav.appendChild(createTableErrorDiv());
  sidenav.appendChild(createTableLabel());
  sidenav.appendChild(createOwnerTable());
  sidenav.appendChild(createButtonsDiv());
});

function createLogo() {
  const logoDiv = document.createElement("div");
  logoDiv.id = "logo";
  const logoImg = document.createElement("img");
  logoImg.src = "images/ecsc_logo_small.png";
  logoImg.alt = "logo";
  logoDiv.appendChild(logoImg);
  return logoDiv;
}

function createBoatInfo() {
  const container = document.createElement("div");
  container.classList.add("side-container");
  boat.boatSettingsDTOS.forEach((setting) => {
    if (setting.visible) {
      // Check if there's a matching key in the normalized boatDTO object
      if (boat.boatDTO[setting.pojoName] !== undefined) {
        container.appendChild(createRow(setting.name, boat.boatDTO[setting.pojoName]));
      } else {
        console.log(`${setting.name}: No matching field in boatDTO`);
      }
    }
  });
  return container;
}

function createRow(labelText, dataText) {
  const row = document.createElement("div");
  const label = document.createElement("div");
  const data = document.createElement("div");
  row.classList.add("row");
  label.classList.add("label");
  data.classList.add("data");
  label.innerText = labelText;
  data.innerText = dataText;
  row.appendChild(label);
  row.appendChild(data);
  return row;
}

function createTableErrorDiv() {
  const div = document.createElement("div");
  div.classList.add("table-error-div");
  div.id = "error-div";
  return div;
}

function createTableLabel() {
  const div = document.createElement("div");
  div.classList.add("table-label");
  if (boat.owners.length > 1) div.innerText = "Owners";
  else div.innerHTML = "Owner";
  return div;
}

function createOwnerTable() {
  const container = document.createElement("div");
  container.classList.add("owner-table-container");
  // Create table and its elements
  const table = document.createElement("table");
  table.className = "styled-table";
  table.id = "owner-table";

  // Create thead element
  const thead = document.createElement("thead");
  thead.className = "header-link";
  table.appendChild(thead);

  // Create header row
  const headerRow = document.createElement("tr");
  thead.appendChild(headerRow);

  // List of columns
  const columns = ["MEM", "First Name", "Last Name"];

  // Create th elements for each column
  columns.forEach((colName) => {
    const th = document.createElement("th");
    th.className = "header";
    th.setAttribute("scope", "col");
    th.textContent = colName;
    headerRow.appendChild(th);
  });

  // Create tbody element
  const tbody = document.createElement("tbody");
  table.appendChild(tbody);

  // Assuming 'boat.owners' is an accessible array
  boat.owners.forEach((owner) => {
    const row = document.createElement("tr");
    const td1 = document.createElement("td");
    td1.textContent = owner.membershipId;
    const td2 = document.createElement("td");
    td2.textContent = owner.firstName;
    const td3 = document.createElement("td");
    td3.textContent = owner.lastName;
    row.appendChild(td1);
    row.appendChild(td2);
    row.appendChild(td3);
    tbody.appendChild(row);
  });

  addRowSelectionFeature(table);

  // Return the fully constructed table
  container.appendChild(table);
  return container;
}

function addRowSelectionFeature(table) {
  const rows = table.querySelectorAll("tbody tr"); // Only select rows within tbody

  table.addEventListener("click", function (event) {
    // Check if the clicked element is a row
    if (event.target.closest("tr")) {
      // Remove 'selected' class from all rows
      rows.forEach((row) => {
        row.classList.remove("selected");
      });
      // Add 'selected' class to the clicked row
      event.target.closest("tr").classList.add("selected");
      errorMessage("");
    }
  });
}

function createButtonsDiv() {
  // Create the container div
  const container = document.createElement("div");
  container.classList.add("owner-button-div");
  container.id = "owner-button-div";
  createAddDeleteButtons(container);
  // Append the container div to the body or another specific element in the DOM
  return container;
}

function createAddDeleteButtons(container) {
  // Create the 'Add' button
  const addButton = document.createElement("button");
  addButton.classList.add("boat-button");
  addButton.textContent = "Add";
  addButton.onclick = function () {
    let container = document.getElementById("owner-button-div");
    container.innerHTML = "";
    container.appendChild(createInputForm());
  };

  // Create the 'Delete' button
  const deleteButton = document.createElement("button");
  deleteButton.classList.add("boat-button");
  deleteButton.textContent = "Delete";
  deleteButton.onclick = function () {
    // Find the selected row
    const selectedRow = document.querySelector(".styled-table tr.selected");
    if (selectedRow) {
      // Assuming the first cell in each row contains the `membershipId`
      const membershipId = selectedRow.cells[0].textContent; // Adjust index as necessary
      // Find the matching owner object
      const owner = boat.owners.find((owner) => owner.membershipId.toString() === membershipId);
      if (owner) {
        console.log("Selected Owner:", owner);
      } else {
        console.log("No owner found for membershipId:", membershipId);
      }
    } else {
      errorMessage("You must select a row first");
    }
  };
  // Append buttons to the container div
  container.appendChild(addButton);
  container.appendChild(deleteButton);
}

function errorMessage(message) {
  let errorDiv = document.getElementById("error-div");
  errorDiv.innerText = message;
}

function createInputForm() {
  // Create the container div
  const container = document.createElement("div");
  container.className = "input-form-container"; // Optional: for styling

  // Create the text field
  const textField = document.createElement("input");
  textField.type = "text";
  textField.placeholder = "Mem ID";
  textField.className = "mem-id-textfield"; // Optional: for styling

  // Create the button
  const addButton = document.createElement("button");
  addButton.textContent = "Update";
  addButton.classList.add("boat-button");
  addButton.onclick = function () {
    if (textField.value === "") {
      errorMessage("You must enter a membership ID."); // Display error message
      return; // Exit the function to prevent further processing
    }
    // Check if the input is a valid integer
    else if (!Number.isInteger(Number(textField.value))) {
      errorMessage("The membership ID must be a valid number."); // Display error message
      return; // Exit the function to prevent further processing
    } else {
      errorMessage("");
      console.log("Value entered:", textField.value);
    }
  };

  const cancelButton = document.createElement("button");
  cancelButton.textContent = "Cancel";
  cancelButton.classList.add("boat-button");
  cancelButton.onclick = function () {
    let container = document.getElementById("owner-button-div");
    container.innerHTML = "";
    createAddDeleteButtons(container);
  };

  // Append the text field and button to the container
  container.appendChild(textField);
  container.appendChild(addButton);
  container.appendChild(cancelButton);
  return container;
}
