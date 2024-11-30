function buildCharts() {
    const mainDiv = document.getElementById("main-content");
    mainDiv.innerHTML = '';
    const script = document.createElement('script');
    script.src = '/js/charts/basic-charts.js';
    script.id = 'dynamicScript'; // changed
    script.onload = function () {
        console.log(`Script with URL ${script.src} has been loaded.`);
        lastLoadedScript = script.id; // changed
        insertBasicChart();
    };
    document.body.appendChild(script);
    const controlDiv = document.getElementById('pageNavigation');
    controlDiv.innerHTML = "";
    controlDiv.appendChild(createButtons());
}


function createButtons() {
    const buttonContainer = document.createElement("div");
    buttonContainer.className = "button-container";

    let buttons = [
        {text: "Basic Charts", value: "basic"},
        {text: "Fee Charts", value: "fees"},
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
        });
        // set our default
        if (button.value === "basic") a.classList.add("selected");
        buttonContainer.appendChild(a);
    });
    return buttonContainer;
}
