let lastLoadedScript;
let lastChartScript;
// global for rosters
let globalRosterData;  // Declare global variable
let lastSortedColumn = 'membershipId';
let sortDirection = "asc"; // 'asc' for ascending, 'desc' for descending
// global for search
let timeout;

document.addEventListener("DOMContentLoaded", function () {
    const logoDiv = document.getElementById("logo");
    const logoImg = document.createElement("img");
    logoImg.src = "images/ecsc_logo_small.png";
    logoImg.alt = "logo";
    logoDiv.appendChild(logoImg);

    const sideTabPaneDiv = document.getElementById("links");
    const sideTabPane = new TabPane(sideTabPaneDiv, "horizontal");
    const mainNavigationDiv = document.createElement("div");
    const pageNavigationDiv = document.createElement("div");
    pageNavigationDiv.id = "pageNavigation";
    sideTabPane.addTab("main-navigation","Main Menu", mainNavigationDiv,false);
    sideTabPane.addTab("page-navigation","Page Options", pageNavigationDiv,false);
    sideTabPane.container.id = "main-menu-tab-pane";
    sideTabPane.tabsContainer.id = "main-menu_tab-container";
    sideTabPane.contentContainer.id = "main-menu-tab-content-container";
    let optionsButton = document.getElementById("tab-button-page-navigation");
    sideTabPane.switchToTab("main-navigation");

    const links = [
        {handler: charts, text: "Charts", target: "", tabLabel: "Chart Options", roles: ["ROLE_USER"]},
        {handler: roster, text: "Rosters", target: "", tabLabel: "Roster Options",roles: ["ROLE_MEMBERSHIP"]},
        {handler: bod, text: "Board of Directors", id: "option2", target: "", tabLabel: "BOD Options",roles: ["ROLE_USER"]},
        {handler: loadFormRequests, text: "Form Requests", target: "", tabLabel: "Options",roles: ["ROLE_MEMBERSHIP"]},
        {handler: slipChart, text: "Slips", target: "", tabLabel: "Slip Options",roles: ["ROLE_USER"]},
        {href: "boat_list", text: "Boats", target: "_blank", tabLabel: "Boat Options",roles: ["ROLE_MEMBERSHIP", "ROLE_HARBORMASTER"]},
        {handler: loadPublicityScript, text: "Publicity", target: "", tabLabel: "",roles: ["ROLE_PUBLICITY"]},
        {handler: loadDirectory, text: "Directory", target: "", tabLabel: "",roles: ["ROLE_MEMBERSHIP"]}
    ];

    let mainLink;

    links.forEach(function (link) {
        const hasRole = link.roles.some(role => userRoles.includes(role));
        if (hasRole) {
            const a = document.createElement("a");
            a.textContent = link.text;
            if (link.href) {
                a.href = link.href;
            } else {
                a.href = "#"; // Prevents page reload if no href is present
                a.addEventListener("click", (e) => {
                    e.preventDefault();
                    link.handler();
                });
            }
            if (link.id) a.id = link.id;
            if (link.target) a.target = link.target;
            a.addEventListener("click", function () {
                document.querySelectorAll('.sidenav a').forEach(el => el.classList.remove('selected'));
                a.classList.add('selected');
                optionsButton.innerText = link.tabLabel;
            });
            // mainNavigation.appendChild(a);
            mainNavigationDiv.appendChild(a);

            // Save reference to "Charts" link
            if (link.text === "Charts") {
                mainLink = a;
            }
        }
    });
    if (mainLink) {
        mainLink.click();
    }
});

function loadFormRequests() {
    if (lastLoadedScript) { // changed
        unloadScript(lastLoadedScript); // changed
    }
    fetch('/api/form-request-summary')
        .then(response => response.json())
        .then(data => {
            // Dynamically load slips.js and then call buildDock
            const script = document.createElement('script');
            script.src = '/js/form-request-summary-rest.js';
            script.id = 'dynamicScript'; // changed
            script.onload = function () {
                console.log(`Script with URL ${script.src} has been loaded.`);
                lastLoadedScript = script.id; // changed
                createFormRequest(data);
            };
            document.body.appendChild(script);
        })
        .catch(error => {
            console.error('Error fetching slip chart data:', error);
        });
}

function slipChart() {
    if (lastLoadedScript) { // changed
        unloadScript(lastLoadedScript); // changed
    }
    fetch('/api/slip_chart')
        .then(response => response.json())
        .then(data => {
            // Dynamically load slips.js and then call buildDock
            const script = document.createElement('script');
            script.src = '/js/slip-rest.js';
            script.id = 'dynamicScript'; // changed
            script.onload = function () {
                console.log(`Script with URL ${script.src} has been loaded.`);
                lastLoadedScript = script.id; // changed
                buildDock(data);
            };
            document.body.appendChild(script);
        })
        .catch(error => {
            console.error('Error fetching slip chart data:', error);
        });
}

function charts() {
    if (lastLoadedScript) { // changed
        unloadScript(lastLoadedScript); // changed
    }
    const script = document.createElement('script');
    script.src = '/js/chart-controller.js';
    script.id = 'dynamicScript'; // changed
    script.onload = function () {
        console.log(`Script with URL ${script.src} has been loaded.`);
        lastLoadedScript = script.id; // changed
        buildCharts(); //gybe-chart-rest.js
    };
    document.body.appendChild(script);
}

function bod() {
    if (lastLoadedScript) { // changed
        unloadScript(lastLoadedScript); // changed
    }
    fetch('/api/bod')
        .then(response => response.json())
        .then(data => {
            const script = document.createElement('script');
            script.src = '/js/bod-rest.js';
            script.id = 'dynamicScript'; // changed
            script.onload = function () {
                console.log(`Script with URL ${script.src} has been loaded.`);
                lastLoadedScript = script.id; // changed
                buildBOD(data)
            };
            document.body.appendChild(script);
        })
        .catch(error => {
            console.error('Error fetching gybe chart data:', error);
        });
}

function loadPublicityScript() {
    if (lastLoadedScript) {
        unloadScript(lastLoadedScript);
    }
    // Dynamically load publicity.js and then call a function if needed
    const script = document.createElement('script');
    script.src = '/js/publicity.js';
    script.id = 'dynamicScript';
    script.onload = function () {
        console.log(`Script with URL ${script.src} has been loaded.`);
        lastLoadedScript = script.id;
        // Call a function from publicity.js if needed
        if (typeof buildPublicity() === 'function') {
            buildPublicity();
        }
    };
    document.body.appendChild(script);
}

function loadDirectory() {
    if (lastLoadedScript) {
        unloadScript(lastLoadedScript);
    }
    // Dynamically load publicity.js and then call a function if needed
    const script = document.createElement('script');
    script.src = '/js/directory.js';
    script.id = 'dynamicScript';
    script.onload = function () {
        console.log(`Script with URL ${script.src} has been loaded.`);
        lastLoadedScript = script.id;
        // Call a function from publicity.js if needed
        if (typeof buildDirectory() === 'function') {
            buildDirectory();
        }
    };
    document.body.appendChild(script);
}

function roster() {
    if (lastLoadedScript) {
        unloadScript(lastLoadedScript);
    }
    fetch('/api/roster_data')
        .then(response => response.json())
        .then(data => {
            const script = document.createElement('script');
            script.src = '/js/rosters-rest.js';
            script.id = 'dynamicScript'; // changed
            script.onload = function () {
                console.log(`Script with URL ${script.src} has been loaded.`);
                lastLoadedScript = script.id; // changed
                buildRosters(data); // rosters-rest.js
            };
            document.body.appendChild(script);
        })
        .catch(error => {
            console.error('Error fetching gybe chart data:', error);
        });
}

function unloadScript(id) { // changed
    if (!id) {
        console.warn('No ID provided for unloading script.');
        return;
    }
    const script = document.getElementById(id); // changed
    if (script) { // changed
        script.parentNode.removeChild(script); // changed
        console.log(`Script with ID ${id} has been removed.`); // changed
    } else { // changed
        console.warn(`No script found with ID ${id}.`); // changed
    }
}
