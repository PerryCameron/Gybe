function buildBOD(data) {


  data.theme.url = data.theme.url.replace(/\\/g, "");

  let officers;
  let chairs;
  let assistants;
  let board1;
  let board2;
  let board3;

  // Call the function to create the sidenav content
  createSidenavContent();

  const mainDiv = document.getElementById("main-content");
  mainDiv.innerHTML = '';

  fillPositions();
  sortPositions();
  buildTables();

  function fillPositions() {
    officers = data.boardOfDirectors.filter((member) => member.officer === true);
    chairs = data.boardOfDirectors.filter((member) => member.chair === true);
    assistants = data.boardOfDirectors.filter((member) => member.assistantChar === true);
    board1 = data.boardOfDirectors.filter((member) => member.boardYear === data.year);
    board2 = data.boardOfDirectors.filter((member) => member.boardYear === data.year + 1);
    board3 = data.boardOfDirectors.filter((member) => member.boardYear === data.year + 2);
  }

  function sortPositions() {
    officers.sort((a, b) => a.order - b.order);
    chairs.sort((a, b) => a.order - b.order);
    assistants.sort((a, b) => a.order - b.order);
    board1.sort((a, b) => a.lastName.localeCompare(b.lastName));
    board2.sort((a, b) => a.lastName.localeCompare(b.lastName));
    board3.sort((a, b) => a.lastName.localeCompare(b.lastName));
  }

  function buildTables() {
    let count = data.boardOfDirectors.length;
// Get the label element by its ID
    let controlsDiv = document.getElementById("pageNavigation");
    let recordsLabel = document.getElementById("numb-of-records");
// Update the text content of the label with the count
    recordsLabel.textContent = "Board Count: " + count;
    const officerDiv = document.createElement("div");
    const chairDiv = document.createElement("div");
    const boardDiv = document.createElement("div");
    chairDiv.classList.add("table-row"); // Add the class name to chairDiv
    chairDiv.classList.add("table-container");
    boardDiv.classList.add("table-container");
    officerDiv.classList.add("table-container");
    officerDiv.classList.add("officer-container");
    officerDiv.append(createOfficerTable());
    const officerImage = document.createElement("img");
    officerImage.src = data.theme.url + "/" + data.theme.stickerName;
    officerImage.alt = "Officer Image";
    officerImage.classList.add("officer-image");
    officerDiv.appendChild(officerImage);
    mainDiv.append(officerDiv);
    mainDiv.append(chairDiv);
    chairDiv.append(createChairTable());
    if (assistants.length !== 0) chairDiv.append(createAssistantChairTable());
    boardDiv.appendChild(createBoardTable());
    mainDiv.append(boardDiv);
    document.querySelectorAll("th").forEach(function (header) {
      header.style.color = data.theme.yearColor;
    });
  }

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

  function createAssistantChairTable() {
    const table = document.createElement("table");
    table.classList.add("officer-table");
    // Create a table header with the title "Officers"
    const headerRow = document.createElement("tr");
    const headerCell = document.createElement("th");
    headerCell.colSpan = 2; // Span across two columns
    headerCell.textContent = "Assistant Chairs";
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
    const table = document.createElement("table");
    table.classList.add("board-table");
    const thead = document.createElement("thead");
    const titleRow = document.createElement("tr");
    const titleCell = document.createElement("th");
    titleCell.setAttribute("colspan", "3");
    titleCell.classList.add("table-title");
    titleCell.textContent = "Board Of Directors";
    titleRow.appendChild(titleCell);
    thead.appendChild(titleRow);
    table.appendChild(thead);

    const headerRow = document.createElement("tr");
    let headers = [data.year, data.year + 1, data.year + 2]; // Column headers based on years
    headers.forEach(function (year) {
      var th = document.createElement("th");
      th.classList.add("left-align");
      th.textContent = year;
      headerRow.appendChild(th);
    });
    thead.appendChild(headerRow);
    table.appendChild(thead);

    // Create the table body
    const tbody = document.createElement("tbody");
    let maxRows = Math.max(board1.length, board2.length, board3.length); // Find the longest column

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

  function createSidenavContent() {
    const startYear = 1970;
    const endYear = new Date().getFullYear(); // This will get the current year
    // Get the control div
    const controlDiv = document.getElementById('pageNavigation');
    controlDiv.innerHTML = "";
    // Create the record content div
    const recordContentDiv = document.createElement('div');
    recordContentDiv.id = 'record-content-div';

    // Create the record label
    const recordLabel = document.createElement('label');
    recordLabel.id = 'numb-of-records';
    recordLabel.textContent = 'Records: placeholder';

    // Append the record label to the record content div
    recordContentDiv.appendChild(recordLabel);

    // Create the year container div
    const yearContainerDiv = document.createElement('div');
    yearContainerDiv.id = 'sidenav-year-container';

    // Create the year select element
    const yearSelect = document.createElement('select');
    yearSelect.id = 'sidenav-yearselect';
    yearSelect.className = 'sidenav-control';
    yearSelect.name = 'year';

    // Add an event listener to handle year selection change
    yearSelect.addEventListener('change', function () {
      const selectedYear = this.value;
      fetchBoardOfDirectors(selectedYear);
    });

    // Append the year select to the year container div
    yearContainerDiv.appendChild(yearSelect);

    for (let i = endYear; i >= startYear; i--) {
      let option1 = new Option(i, i);
      // let option2 = new Option(i, i);
      yearSelect.add(option1);
      // yearSelect2.add(option2);
    }

    // Append all the created elements to the sidenav div
    controlDiv.appendChild(recordContentDiv);
    controlDiv.appendChild(yearContainerDiv);
  }

// Fetch the board of directors data for the selected year
  function fetchBoardOfDirectors(selectedYear) {
    console.log("Rebuilding the data");
    // Example AJAX request to fetch the updated data with a query parameter for year
    fetch('/rb_bod?year=' + selectedYear)
        .then(response => response.json())
        .then(newData => {
          // Update the boardOfDirectors, theme, and year variables
          data.boardOfDirectors = newData.leadership;
          data.theme = newData.theme;
          data.year = newData.year;
          // Rebuild the page with the new data
          buildBoardOfDirectorsPage();
        })
        .catch(error => console.error('Error fetching data:', error));
  }

// Function to dynamically build the Board of Directors page
  function buildBoardOfDirectorsPage() {
    mainDiv.innerHTML = "";
    fillPositions();
    sortPositions();
    buildTables();
  }
}