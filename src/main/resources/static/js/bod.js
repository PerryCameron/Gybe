const officers = boardOfDirectors.filter((member) => member.officer === true);
const chairs = boardOfDirectors.filter((member) => member.chair === true);
const assistants = boardOfDirectors.filter((member) => member.assistantChar === true);
const board1 = boardOfDirectors.filter((member) => member.boardYear === year);
const board2 = boardOfDirectors.filter((member) => member.boardYear === year + 1);
const board3 = boardOfDirectors.filter((member) => member.boardYear === year + 2);
officers.sort((a, b) => a.order - b.order);
chairs.sort((a, b) => a.order - b.order);
assistants.sort((a, b) => a.order - b.order);

board1.sort((a, b) => a.lastName.localeCompare(b.lastName));
board2.sort((a, b) => a.lastName.localeCompare(b.lastName));
board3.sort((a, b) => a.lastName.localeCompare(b.lastName));


let count = boardOfDirectors.length;
// Get the label element by its ID
let recordsLabel = document.getElementById("numb-of-records");
// Update the text content of the label with the count
recordsLabel.textContent = "Board Count: " + count;

const mainDiv = document.getElementById("main");
const officerDiv = document.createElement("div");
const chairDiv = document.createElement("div");
const boardDiv = document.createElement("div");
chairDiv.classList.add("table-row"); // Add the class name to chairDiv
chairDiv.classList.add("table-container");
boardDiv.classList.add("table-container");
officerDiv.classList.add("table-container");

officerDiv.append(createOfficerTable());
mainDiv.append(officerDiv);

mainDiv.append(chairDiv);
chairDiv.append(createChairTable());
if (assistants.length !== 0)
chairDiv.append(createAssistentChairTable());
mainDiv.append(createBoardTable());

function createOfficerTable() {
  const table = document.createElement("table");
  table.classList.add("officer-table");
  // Create a table header with the title "Officers"
  const headerRow = document.createElement("tr");
  const headerCell = document.createElement("th");
  headerCell.colSpan = 2; // Span across two columns
  headerCell.textContent = "Officers";
  headerCell.style.color = "blue"; // Set the text color to blue
  headerRow.appendChild(headerCell);
  table.appendChild(headerRow);

  // Populate the table with rows for each officer
  officers.forEach((officer) => {
    const row = document.createElement("tr");

    const positionCell = document.createElement("td");
    positionCell.textContent = `${officer.position}`;
    positionCell.style.color = "grey"; // Set the text color to grey

    const nameCell = document.createElement("td");
    nameCell.textContent = `${officer.firstName} ${officer.lastName}`;

    row.appendChild(positionCell); // Add the position cell first
    row.appendChild(nameCell); // Add the name cell second
    table.appendChild(row);
  });
  return table;
}

function createChairTable() {
  const table = document.createElement("table");
  table.classList.add("officer-table");
  // Create a table header with the title "Officers"
  const headerRow = document.createElement("tr");
  const headerCell = document.createElement("th");
  headerCell.colSpan = 2; // Span across two columns
  headerCell.textContent = "Committee Chairs";
  headerCell.style.color = "blue"; // Set the text color to blue
  headerRow.appendChild(headerCell);
  table.appendChild(headerRow);

  chairs.forEach((chair) => {
    const row = document.createElement("tr");
    const positionCell = document.createElement("td");
    positionCell.textContent = `${chair.position}`;
    positionCell.style.color = "grey"; // Set the text color to grey
    const nameCell = document.createElement("td");
    nameCell.textContent = `${chair.firstName} ${chair.lastName}`;
    row.appendChild(positionCell); // Add the position cell first
    row.appendChild(nameCell); // Add the name cell second
    table.appendChild(row);
  });
  return table;
}

function createAssistentChairTable() {
  const table = document.createElement("table");
  table.classList.add("officer-table");
  // Create a table header with the title "Officers"
  const headerRow = document.createElement("tr");
  const headerCell = document.createElement("th");
  headerCell.colSpan = 2; // Span across two columns
  headerCell.textContent = "Assistent Chairs";
  headerCell.style.color = "blue"; // Set the text color to blue
  headerRow.appendChild(headerCell);
  table.appendChild(headerRow);

  assistants.forEach((chair) => {
    const row = document.createElement("tr");
    const positionCell = document.createElement("td");
    positionCell.textContent = `${chair.position}`;
    positionCell.style.color = "grey"; // Set the text color to grey
    const nameCell = document.createElement("td");
    nameCell.textContent = `${chair.firstName} ${chair.lastName}`;
    row.appendChild(positionCell); // Add the position cell first
    row.appendChild(nameCell); // Add the name cell second
    table.appendChild(row);
  });
  return table;
}

function createBoardTable() {
  var table = document.createElement("table");
  table.classList.add("board-table");

  var thead = document.createElement("thead");
  var titleRow = document.createElement("tr");
  var titleCell = document.createElement("th");
  titleCell.setAttribute("colspan", "3");
  titleCell.classList.add("table-title");
  titleCell.textContent = "Board Of Directors";
  titleRow.appendChild(titleCell);
  thead.appendChild(titleRow);
  table.appendChild(thead);

  var headerRow = document.createElement("tr");
  var headers = [year, year + 1, year + 2]; // Column headers based on years
  headers.forEach(function (year) {
    var th = document.createElement("th");
    th.classList.add("left-align");
    th.textContent = year;
    headerRow.appendChild(th);
  });
  thead.appendChild(headerRow);
  table.appendChild(thead);

  // Create the table body
  var tbody = document.createElement("tbody");
  var maxRows = Math.max(board1.length, board2.length, board3.length); // Find the longest column

  for (var i = 0; i < maxRows; i++) {
    var row = document.createElement("tr");
    [board1, board2, board3].forEach(function (board, index) {
      var td = document.createElement("td");
      if (board[i]) {
        td.textContent = board[i].firstName + " " + board[i].lastName;
      } else {
        td.textContent = ""; // Empty cell if no member
      }
      row.appendChild(td);
    });
    tbody.appendChild(row);
  }
  table.appendChild(tbody);

  return table;
}
