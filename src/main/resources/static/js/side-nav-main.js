let lastLoadedScript = null;

// <div className="sidenav">
//     <div id="logo"></div>
//     <div id="controls"></div>
//     <div id="links"></div>
// </div>

document.addEventListener("DOMContentLoaded", function () {
    const logoDiv = document.getElementById("logo");
    const logoImg = document.createElement("img");
    logoImg.src = "images/ecsc_logo_small.png";
    logoImg.alt = "logo";
    logoDiv.appendChild(logoImg);

    const buttonContainer = document.getElementById("links");

    const currentYear = new Date().getFullYear(); // Get the current year

    const links = [
        {href: "javascript:charts()", text: "Charts", target: "", roles: ["ROLE_USER"]},
        {href: "Rosters", text: "Rosters", id: "option1", target: "_blank", roles: ["ROLE_MEMBERSHIP"]},
        {href: "javascript:bod()", text: "Board of Directors", id: "option2", target: "", roles: ["ROLE_USER"]},
        {href: "javascript:loadFormRequests()", text: "Form Requests", target: "", roles: ["ROLE_MEMBERSHIP"]},
        {href: "javascript:slipChart()", text: "Slips", target: "", roles: ["ROLE_USER"]},
        {href: "boat_list", text: "Boats", target: "_blank", roles: ["ROLE_MEMBERSHIP", "ROLE_HARBORMASTER"]},
        {href: "javascript:loadPublicityScript()", text: "Publicity", target: "", roles: ["ROLE_PUBLICITY"]},
        {href: "javascript:loadDirectory()", text: "Directory", target: "", roles: ["ROLE_MEMBERSHIP"]},
    ];

    links.forEach(function (link) {
        const hasRole = link.roles.some(role => userRoles.includes(role));
        if (hasRole) {
            const a = document.createElement("a");
            a.href = link.href;
            a.textContent = link.text;
            if (link.id) a.id = link.id;
            if (link.target) a.target = link.target;
            a.addEventListener("click", function () {
                document.querySelectorAll('.sidenav a').forEach(el => el.classList.remove('selected'));
                a.classList.add('selected');
            });
            buttonContainer.appendChild(a);
        }
    });
    charts();
});



function loadFormRequests() {
    if (lastLoadedScript) { // changed
        console.log('Unloading script:', lastLoadedScript); // changed
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
        console.log('Unloading script:', lastLoadedScript); // changed
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
        console.log('Unloading script:', lastLoadedScript); // changed
        unloadScript(lastLoadedScript); // changed
    }
    fetch('/api/gybe_chart_data')
        .then(response => response.json())
        .then(data => {
            const script = document.createElement('script');
            script.src = '/js/gybe-chart-rest.js';
            script.id = 'dynamicScript'; // changed
            script.onload = function () {
                console.log(`Script with URL ${script.src} has been loaded.`);
                lastLoadedScript = script.id; // changed
                buildGybeChart(data);
            };
            document.body.appendChild(script);
        })
        .catch(error => {
            console.error('Error fetching gybe chart data:', error);
        });
}

function bod() {
    if (lastLoadedScript) { // changed
        console.log('Unloading script:', lastLoadedScript); // changed
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
    console.log("loading publicity script");
    if (lastLoadedScript) {
        console.log('Unloading script:', lastLoadedScript);
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
    console.log("loading publicity script");
    if (lastLoadedScript) {
        console.log('Unloading script:', lastLoadedScript);
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
