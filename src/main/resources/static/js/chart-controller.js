function buildCharts() {
    const controlDiv = document.getElementById('pageNavigation');
    controlDiv.innerHTML = "";
    controlDiv.appendChild(createButtons());
}

function createButtons() {
    const buttonContainer = document.createElement("div");
    buttonContainer.className = "button-container";
    // Example user roles (replace with actual user roles from your app)
    const userRoles = ["ROLE_USER"];
    let buttons = [
        { handler: basicCharts, text: "Basic Charts", value: "basic", roles: ["ROLE_USER"] },
        { handler: feeCharts, text: "Fee Charts", value: "fees", roles: ["ROLE_USER"] },
    ];
    let chartLink;
    buttons.forEach(function (button) {
        // Check if the user has any of the required roles for this button
        const hasRole = button.roles.some(role => userRoles.includes(role));
        if (hasRole) {
            const a = document.createElement("a");
            a.textContent = button.text;

            if (button.href) {
                a.href = button.href;
            } else {
                a.href = "#";
                a.addEventListener("click", (e) => {
                    e.preventDefault();
                    button.handler(); // Call the button's handler function
                });
            }
            if (button.id) a.id = button.id;
            if (button.target) a.target = button.target;
            // Add click listener to highlight the selected button
            a.addEventListener("click", function () {
                // Remove the 'selected' class from all links within this container
                buttonContainer.querySelectorAll("a").forEach(function (el) {
                    el.classList.remove("selected");
                });
                this.classList.add("selected"); // Highlight the clicked link
            });
            // Set default selection
            if (button.value === "basic") {
                a.classList.add("selected");
                    chartLink = a;
            }
            buttonContainer.appendChild(a);
        }
    });
    if (chartLink) {
        chartLink.click();
    }
    return buttonContainer;
}

function basicCharts() {
    if (lastChartScript) { // changed
        unloadScript(lastChartScript); // changed
    }
    const mainDiv = document.getElementById("main-content");
    mainDiv.innerHTML = '';
    const script = document.createElement('script');
    script.src = '/js/charts/basic-charts.js';
    script.id = 'dynamicChartScript'; // changed
    script.onload = function () {
        console.log(`Script with URL ${script.src} has been loaded.`);
        lastChartScript = script.id; // changed
        insertBasicChart();
    };
    document.body.appendChild(script);
}

function feeCharts() {
    if (lastChartScript) { // changed
        unloadScript(lastChartScript); // changed
    }
    const script = document.createElement('script');
    script.src = '/js/charts/fee-charts.js';
    script.id = 'dynamicChartScript'; // changed
    script.onload = function () {
        console.log(`Script with URL ${script.src} has been loaded.`);
        lastChartScript = script.id; // changed
        insertFeeChart();
    };
    document.body.appendChild(script);
}


