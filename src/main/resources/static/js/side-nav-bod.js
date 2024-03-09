// Create a function to dynamically generate the sidenav content
function createSidenavContent() {
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
    recordLabel.textContent = 'Records: 201';

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

    // Append the year select to the year container div
    yearContainerDiv.appendChild(yearSelect);

    // Append all the created elements to the sidenav div
    sidenav.appendChild(logoDiv);
    sidenav.appendChild(recordContentDiv);
    sidenav.appendChild(yearContainerDiv);
}

// Call the function to create the sidenav content
createSidenavContent();
