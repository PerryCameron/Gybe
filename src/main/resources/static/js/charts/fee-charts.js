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
    const treeViewContainer = document.createElement("div");
    treeViewContainer.id = "treeview-container";
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
    const buttonContainer = document.getElementById("chart-button-container");
    treeViewContainer.appendChild(createTreeView(data, feesChart));
    buttonContainer.appendChild(treeViewContainer);
    // mainDiv.appendChild(treeViewContainer);
}

function createTreeView(data, chart) {
    const treeview = document.createElement("ul");
    treeview.id = "treeview";

    // Group data by fieldName
    const categories = {};
    for (const [description, items] of Object.entries(data)) {
        const fieldName = items[0]?.fieldName || "Uncategorized";
        if (!categories[fieldName]) {
            categories[fieldName] = [];
        }
        categories[fieldName].push({ description, items });
    }

    // Build the tree structure
    Object.keys(categories).forEach((category) => {
        const categoryNode = document.createElement("li");
        const toggleSpan = document.createElement("span");
        toggleSpan.textContent = "+";
        toggleSpan.classList.add("toggle");
        toggleSpan.addEventListener("click", () => {
            categoryNode.classList.toggle("open");
            toggleSpan.textContent = categoryNode.classList.contains("open") ? "-" : "+";
        });
        const categoryLabel = document.createElement("span");
        categoryLabel.textContent = category;
        categoryLabel.style.fontWeight = "bold";
        const sublist = document.createElement("ul");
        categories[category].forEach(({ description, items }) => {
            const typeNode = document.createElement("li");
            typeNode.textContent = description || "No Description";
            typeNode.addEventListener("click", () => {
                toggleDataset(chart, description, items);
                if (typeNode.classList.contains("selected-chart")) {
                    typeNode.classList.remove("selected-chart");
                } else {
                    typeNode.classList.add("selected-chart");
                }
            });
            sublist.appendChild(typeNode);
        });
        categoryNode.appendChild(toggleSpan);
        categoryNode.appendChild(categoryLabel);
        categoryNode.appendChild(sublist);
        treeview.appendChild(categoryNode);
    });
    return treeview;
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
