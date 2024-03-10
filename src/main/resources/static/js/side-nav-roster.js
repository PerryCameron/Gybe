document.addEventListener("DOMContentLoaded", function () {
    const sidenav = document.querySelector(".sidenav");

    const logoDiv = document.createElement("div");
    logoDiv.id = "logo";
    sidenav.appendChild(logoDiv);

    const recordContentDiv = document.createElement("div");
    recordContentDiv.id = "record-content-div";
    const label = document.createElement("label");
    label.id = "numb-of-records";
    label.textContent = `Records: ${rosters.membershipListDTOS.length}`;
    recordContentDiv.appendChild(label);
    sidenav.appendChild(recordContentDiv);
    sidenav.appendChild(createSearchBarDiv());
    sidenav.appendChild(createYearSelectDiv());

    const logoImg = document.createElement("img");
    logoImg.src = "images/ecsc_logo_small.png";
    logoImg.alt = "logo";
    logoDiv.appendChild(logoImg);

    const buttonContainer = document.createElement("div");
    buttonContainer.className = "button-container";
    sidenav.appendChild(buttonContainer);

    // var currentYear = new Date().getFullYear(); // Get the current year

    let buttons = [
        { text: "All", value: "all" },
        { text: "Active", value: "active" },
        { text: "Non-Renew", value: "non_renew" },
        { text: "New Members", value: "new_members" },
        { text: "Return Members", value: "return_members" },
        { text: "Slip Wait-list", value: "slip_wait_list" },
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
            rosters.rosterType = this.value;
            fetchData(false);
        });

        buttonContainer.appendChild(a);
    });
});

function handleKeyUp(event) {
    if (event.key === "Enter") {
        fetchData(true);
    }
}

function handleBlur() {
    fetchData(true);
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
    input.addEventListener("blur", handleBlur);
    searchBarDiv.appendChild(input);
    return searchBarDiv;
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
    if (rosters.year) {
        yearSelect.value = rosters.year;
    }

    yearSelect.addEventListener("change", function () {
        rosters.year = this.value;
        fetchData(false);
    });

    yearContainer.appendChild(yearSelect);
    return yearContainer;
}

function fetchData(isSearch) {
    if (isSearch) {
        rosters.rosterType = "search"
        const searchText = document.getElementById("search");
        searchText.value = "";
    }
    const url = `/rb_roster?type=${rosters.rosterType}&year=${rosters.year}`;

    // Use the fetch API to send a GET request to the server
    fetch(url)
        .then(response => {
            if (response.ok) {
                return response.json(); // Parse the JSON response
            } else {
                throw new Error('Failed to fetch roster data');
            }
        })
        .then(rosterResponse => {
            console.log('Roster Response:', rosterResponse);
            // Handle the roster response data
        })
        .catch(error => {
            console.error('Error fetching roster data:', error);
        });
    rebuildTable();
}

function rebuildTable() {
    const mainDiv = document.getElementById("main");
    mainDiv.innerHTML = "";
    mainDiv.appendChild(createTable());
}
