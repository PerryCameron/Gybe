function createFormRequest(data) {
    console.log('Data received:', data);

    if (!data || !data.formSummaryData) {
        console.error('Invalid data format:', data);
        return;
    }

    const mainDiv = document.getElementById("main-content");
    mainDiv.innerHTML = '';
    const formRequestDiv = document.createElement("div");
    mainDiv.appendChild(formRequestDiv);

    createSidenavContent();

    const startYear = 2023;
    const endYear = new Date().getFullYear();
    populateYearSelect(startYear, endYear);
    setSelectedYear();

    function populateYearSelect(startYear, endYear) {
        const yearSelect = document.getElementById("sidenav-year-container");
        for (let i = endYear; i >= startYear; i--) {
            let option = new Option(i, i);
            yearSelect.add(option);
        }
        yearSelect.addEventListener('change', function () {
            updatePage(this.value);
        });
    }

    function updatePage(selectedYear) {
        console.log(`Updating page for year: ${selectedYear}`);
        fetch(`/api/form-request-summary?year=${selectedYear}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok.');
                }
                return response.json();
            })
            .then(newData => {
                console.log('New data received:', newData);
                formRequestDiv.innerHTML = ''; // Clear the main content
                renderTable(newData); // Rebuild the table with new data
                updateRecordCount(newData.formSummaryData);
            })
            .catch(error => {
                console.error('Error fetching form request summaries:', error);
            });
    }

    function setSelectedYear() {
        const params = new URLSearchParams(window.location.search);
        const year = params.get('year');
        const yearSelect = document.getElementById("sidenav-year-container");

        if (year) {
            yearSelect.value = year;
        }
    }

    function renderTable(data) {
        console.log('Rendering table with data:', data);
        let table = "<table class='styled-table'><thead class='header-link'>";
        table += "<tr><th>ID</th><th>Email Request Date</th><th>Primary Member</th><th>Link</th><th>Email</th><th>Email Attempts</th><th>Form Request Date</th><th>Form Attempts</th></tr></thead><tbody>";

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

        table += "</tbody></table>";
        console.log('Generated table HTML:', table);
        formRequestDiv.innerHTML = table;

        // Check if the table has been correctly added to the DOM
        if (formRequestDiv.innerHTML.includes('<table')) {
            console.log('Table successfully added to the DOM.');
        } else {
            console.error('Failed to add the table to the DOM.');
        }
    }

    function updateRecordCount(array) {
        let recordLabel = document.getElementById("numb-of-records");

        if (recordLabel) {
            recordLabel.textContent = "Records: " + array.length;
        }
    }

    function createSidenavContent() {
        const controlDiv = document.getElementById('controls');
        controlDiv.innerHTML = "";

        const labelDiv = document.createElement("div");
        labelDiv.id = "record-content-div";

        const label = document.createElement("label");
        label.id = "numb-of-records";
        labelDiv.appendChild(label);

        const yearSelect = document.createElement("select");
        yearSelect.id = "sidenav-year-container";
        yearSelect.className = "sidenav-control";
        yearSelect.name = "year";

        controlDiv.appendChild(labelDiv);
        controlDiv.appendChild(yearSelect);
    }

    renderTable(data);
    updateRecordCount(data.formSummaryData);
}

