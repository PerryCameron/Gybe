function createSidenavContent() {
    const startYear = 1970;
    const endYear = new Date().getFullYear(); // This will get the current year
    // Get the sidenav div
    const sidenav = document.getElementById('sidenav');

    // Create the logo div
    const logoDiv = document.createElement('div');
    logoDiv.id = 'logo';

    // Create the logo image
    const logoImg = document.createElement('img');
    logoImg.src = 'images/ecsc_logo_small.png';
    logoImg.alt = 'logo';

    // Append the logo image to the logo div
    logoDiv.appendChild(logoImg);

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
    sidenav.appendChild(logoDiv);
    sidenav.appendChild(recordContentDiv);
    sidenav.appendChild(yearContainerDiv);
}

// Fetch the board of directors data for the selected year
function fetchBoardOfDirectors(selectedYear) {
    console.log("Rebuilding the data");
    // Example AJAX request to fetch the updated data with a query parameter for year
    fetch('/rb_bod?year=' + selectedYear)
        .then(response => response.json())
        .then(data => {
            // Update the boardOfDirectors, theme, and year variables
            boardOfDirectors = data.leadership;
            theme = data.theme;
            year = data.year;
            // Rebuild the page with the new data
            buildBoardOfDirectorsPage();
        })
        .catch(error => console.error('Error fetching data:', error));
}

// Function to dynamically build the Board of Directors page
function buildBoardOfDirectorsPage() {
    const mainDiv = document.getElementById("main");
    mainDiv.innerHTML = "";
    fillPositions();
    sortPositions();
    buildTables();
}

// Call the function to create the sidenav content
createSidenavContent();
