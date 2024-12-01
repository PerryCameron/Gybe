function insertFeeChart() {
    fetch('/api/fee_chart_data')
        .then(response => response.json())
        .then(data => {
            let organized = organizeDataByDescription(data.fees);
            console.log("organized", organized);
            buildFeeChart(organized);
        })
        .catch(error => {
            console.error('Error fetching gybe chart data:', error);
        });
}

function buildFeeChart(data) {
    const mainDiv = document.getElementById("main-content");
    mainDiv.innerHTML = '';
    // const ctx = document.getElementById("myChart").getContext("2d");
    const duesChartCanvas = document.createElement('canvas');
    const feesChart = new Chart(duesChartCanvas, {
        type: "line",
        data: {
            labels: [],
            datasets: [],
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                title: {
                    display: true,
                    text: "Fees Over the Years"
                },
                legend: {
                    display: true,
                    position: "top"
                }
            },
            scales: {
                x: {
                    title: {
                        display: true,
                        text: "Year"
                    }
                },
                y: {
                    title: {
                        display: true,
                        text: "Fee (USD)"
                    }
                }
            }
        }
    });
    mainDiv.appendChild(duesChartCanvas);
    mainDiv.appendChild(createDropdownMenus(data, feesChart));
}

// function createButtons(groupedData, chart) {
//     const buttonContainer = document.createElement('div');
//     Object.keys(groupedData).forEach((description) => {
//         const button = document.createElement("button");
//         button.textContent = description || "No Description"; // Handle empty descriptions
//         button.dataset.description = description;
//         button.addEventListener("click", () => {
//             toggleDataset(chart, description, groupedData[description]);
//         });
//         buttonContainer.appendChild(button);
//     });
//     return buttonContainer;
// }
function createDropdownMenus(groupedData, chart) {
    const buttonContainer = document.createElement('div');

    // Group data by fieldName
    const categories = {};
    for (const [description, data] of Object.entries(groupedData)) {
        const fieldName = data[0]?.fieldName || "Uncategorized";
        if (!categories[fieldName]) {
            categories[fieldName] = [];
        }
        categories[fieldName].push({ description, data });
    }

    // Create dropdowns for each category
    Object.keys(categories).forEach((category) => {
        const dropdown = document.createElement("div");
        dropdown.classList.add("dropdown");

        const dropdownLabel = document.createElement("button");
        dropdownLabel.textContent = category;
        dropdownLabel.classList.add("dropdown-button");
        dropdownLabel.addEventListener("click", () => {
            dropdownContent.classList.toggle("show");
        });

        const dropdownContent = document.createElement("div");
        dropdownContent.classList.add("dropdown-content");

        categories[category].forEach(({ description, data }) => {
            const button = document.createElement("button");
            button.textContent = description || "No Description";
            button.addEventListener("click", () => {
                toggleDataset(chart, description, data);
            });
            dropdownContent.appendChild(button);
        });

        dropdown.appendChild(dropdownLabel);
        dropdown.appendChild(dropdownContent);
        buttonContainer.appendChild(dropdown);
    });
    return buttonContainer;
}

function toggleDataset(chart, description, data) {
    const datasetIndex = chart.data.datasets.findIndex((dataset) => dataset.label === description);
    if (datasetIndex >= 0) {
        // Dataset already exists, remove it
        chart.data.datasets.splice(datasetIndex, 1);
    } else {
        // Add new dataset
        const years = data.map((entry) => entry.year);
        const values = data.map((entry) => entry.value);
        // Update chart labels (global years)
        chart.data.labels = [...new Set([...chart.data.labels, ...years])].sort((a, b) => a - b);
        // Align dataset values with chart labels
        const alignedValues = chart.data.labels.map((labelYear) => {
            const index = years.indexOf(labelYear);
            return index !== -1 ? values[index] : null; // Use `null` for missing data
        });
        chart.data.datasets.push({
            label: description,
            data: alignedValues,
            borderWidth: 2,
            borderColor: getRandomColor(),
            fill: false
        });
    }
    chart.update();
}

function getRandomColor() {
    const letters = "0123456789ABCDEF";
    let color = "#";
    for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}

function organizeDataByDescription(data) {
    const groupedData = {};
    data.forEach(({ description, fieldName, fieldValue, feeYear }) => {
        if (!groupedData[description]) {
            groupedData[description] = [];
        }
        groupedData[description].push({
            fieldName,
            value: parseFloat(fieldValue),
            year: feeYear
        });
    });
    return groupedData;
}

