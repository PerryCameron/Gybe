function insertFeeChart() {
    fetch('/api/fee_chart_data')
        .then(response => response.json())
        .then(data => {
            // console.log("Fetched data:", data); // Log the fetched data
            buildFeeCharts(data);
        })
        .catch(error => {
            console.error('Error fetching gybe chart data:', error);
        });
}

function buildFeeCharts(data) {
    const mainDiv = document.getElementById("main-content");
    mainDiv.innerHTML = '';
    mainDiv.appendChild(addDuesChart(data));
}

function extractDues(data) {
    const fees = data.fees; // data.fees is the correct name

    // Group fees by description
    const groupedFees = {
        Regular: [],
        Family: [],
        Social: [],
        "Lake Associate": []
    };

    fees.forEach((fee) => {
        if (groupedFees.hasOwnProperty(fee.description)) {
            groupedFees[fee.description].push({
                year: fee.feeYear,
                value: parseFloat(fee.fieldValue)
            });
        }
    });

    // Sort each group by year
    Object.keys(groupedFees).forEach((key) => {
        groupedFees[key].sort((a, b) => a.year - b.year);
    });

    // Extract the years and values
    const years = [...new Set(fees.map((fee) => fee.feeYear))].sort((a, b) => a - b); // Unique sorted years
    const regular = groupedFees.Regular.map((fee) => fee.value);
    const family = groupedFees.Family.map((fee) => fee.value);
    const lakeAssociate = groupedFees["Lake Associate"].map((fee) => fee.value);
    const social = groupedFees.Social.map((fee) => fee.value);

    return { regular, years, family, lakeAssociate, social };
}

function addDuesChart(data) {
    const duesChartCanvas = document.createElement('canvas');
    const {regular, years, family, lakeAssociate, social} = extractDues(data);
    console.log("regular", regular); // returning undefined
    console.log("social", social); // returning undefined
    new Chart(duesChartCanvas, {
        type: "line",
        data: {
            labels: years,
            datasets: [
                {
                    label: "Family",
                    data: family,
                    borderWidth: 1,
                    backgroundColor: "#2A2B2E", //black
                },
                {
                    label: "Regular",
                    data: regular,
                    borderWidth: 1,
                    backgroundColor: "#4B9CD3", // Blue
                },
                {
                    label: "Social",
                    data: social,
                    borderWidth: 1,
                    backgroundColor: "#F9A825", // Yellow
                },
                {
                    label: "Lake Associate",
                    data: lakeAssociate,
                    borderWidth: 1,
                    backgroundColor: "#C62828", // Red
                },
            ],
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                title: {
                    display: true,
                    text: `Fees over the years`, // Add your description here
                    font: {
                        size: 18, // You can adjust the font size and other properties
                    },
                    padding: {
                        top: 10,
                        bottom: 30, // Adjust padding as needed
                    },
                },
                legend: {
                    display: true,
                    position: "top",
                },
            },
        },
    });
    return duesChartCanvas;
}

