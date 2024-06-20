function createFormRequest(data) {
    const mainDiv = document.getElementById("main-content");
    mainDiv.innerHTML = '';
    const formRequestDiv = document.createElement("div");
    mainDiv.appendChild(formRequestDiv);

    const yearSelect = document.createElement("select");
    // yearSelect.id = "sidenav-yearselect";
    yearSelect.className = "sidenav-control";
    yearSelect.name = "year";

    // <div id="formRequestTable"></div>
    const startYear = 2023;
    const endYear = new Date().getFullYear(); // This will get the current year
    populateYearSelect(startYear, endYear);
    setSelectedYear();

    function populateYearSelect(startYear, endYear) {
        for (let i = endYear; i >= startYear; i--) {
            let option1 = new Option(i, i);
            yearSelect.add(option1);
        }
        yearSelect.addEventListener('change', function () {
            updatePage(this.value);
        });
    }

    function updatePage(selectedYear) {
        // Get the current URL
        let currentUrl = new URL(window.location.href);

        // Check if the URL already has a 'year' query parameter
        if (currentUrl.searchParams.has('year')) {
            // Update the 'year' parameter
            currentUrl.searchParams.set('year', selectedYear);
        } else {
            // Add the 'year' parameter
            currentUrl.searchParams.append('year', selectedYear);
        }

        // Update the browser's URL without reloading the page
        // window.history.pushState({}, '', currentUrl);

        // Update the browser's URL
        window.location.href = currentUrl;

        // Add your logic here to refresh or update the content of the page
        // based on the selected year
    }

    function setSelectedYear() {
        // Get the current year from the URL parameter 'year'
        const params = new URLSearchParams(window.location.search);
        const year = params.get('year');

        if (year) {
            yearSelect.value = year;
        }
    }

    let table = "<table class='styled-table'><thead class='header-link'>";
    table +=
        "<tr><th>ID</th><th>Email Request Date</th><th>Primary Member</th><th>Link</th><th>Email</th><th>Email Attempts</th><th>Form Request Date</th><th>Form Attempts</th></tr></thead>";

// Your forEach loop and table generation code here
    data.formSummaryData.forEach((request) => {
        table += `<tr>
                <td>${request.membershipId}</td>
                <td>${request.newestHashReqDate}</td>
                <td>${request.priMem}</td>
                <td><a href="${request.link}" target='_blank'>Register Link</a></td>
                <td><a href="mailto:${request.mailedTo}">${request.mailedTo}</a></td>
                <td>${request.numHashDuplicates}</td>
                <td>${request.newestFormReqDate}</td>
                <td>${request.numFormAttempts}</td>
              </tr>`;
    });

    table += "</table>";
    formRequestDiv.innerHTML = table;
    updateRecordCount(data.formSummaryData);

    function updateRecordCount(array) {
        // Retrieve the element by ID
        let recordLabel = document.getElementById("numb-of-records");

        // Update the content of the element
        if (recordLabel) {
            recordLabel.textContent = "Records: " + array.length;
        }
    }
}