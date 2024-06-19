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
        {href: "form-request-summary", text: "Form Requests", target: "_blank", roles: ["ROLE_MEMBERSHIP"]},
        {href: "javascript:slipChart()", text: "Slips", target: "", roles: ["ROLE_USER"]},
        {href: "boat_list", text: "Boats", target: "_blank", roles: ["ROLE_MEMBERSHIP", "ROLE_HARBORMASTER"]},
        {href: "publicity", text: "Publicity", target: "_blank", roles: ["ROLE_PUBLICITY"]},
    ];

    links.forEach(function (link) {
        const hasRole = link.roles.some(role => userRoles.includes(role));
        if (hasRole) {
            const a = document.createElement("a");
            a.href = link.href;
            a.textContent = link.text;
            if (link.id) a.id = link.id;
            if (link.target) a.target = link.target;
            buttonContainer.appendChild(a);
        }
    });
    charts();
});

// this is used through a button
function slipChart() {
    if (lastLoadedScript) { // changed
        console.log('Unloading script:', lastLoadedScript); // changed
        unloadScript(lastLoadedScript); // changed
    }

    const mainDiv = document.getElementById("main-content");
    mainDiv.innerHTML = '<canvas id="slipChart" width="1200" height="1000"></canvas>';

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
