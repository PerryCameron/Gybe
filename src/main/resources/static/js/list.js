// Call the function with your desired years as arguments
document.addEventListener("DOMContentLoaded", () => {
    const startYear = 1970;
    const endYear = new Date().getFullYear(); // This will get the current year
    populateYearSelect(startYear, endYear);
});

document.addEventListener('DOMContentLoaded', () => {
    // Get the 'rb' parameter from the URL
    const urlParams = new URLSearchParams(window.location.search);
    const rbValue = urlParams.get('rb');

    // Find the link with the corresponding 'value' attribute and add 'selected' class
    if (rbValue) {
        const links = document.querySelectorAll('.sidenav a');
        links.forEach(link => {
            if (link.getAttribute('value') === rbValue) {
                link.classList.add('selected');
            }
        });
    }
});

function populateYearSelect(startYear, endYear) {
    const yearSelect1 = document.getElementById("sidenav-yearselect");
    const yearSelect2 = document.getElementById("topnav-yearselect");
    for (let i = endYear; i >= startYear; i--) {
        let option1 = new Option(i, i);
        let option2 = new Option(i, i);
        yearSelect1.add(option1);
        yearSelect2.add(option2);
    }
    document.getElementById('sidenav-yearselect').addEventListener('change', function () {
        setGetParameter('year', this.value);
    });
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const product = urlParams.get('year');
    yearSelect1.value = product;
    yearSelect2.value = product;
}

function setGetParameter(paramName, paramValue) {
    let url = window.location.href;
    let hash = location.hash;
    url = url.replace(hash, '');
    if (url.indexOf("?") >= 0) {
        let params = url.substring(url.indexOf("?") + 1).split("&");
        let paramFound = false;
        params.forEach(function (param, index) {
            let p = param.split("=");
            if (p[0] == paramName) {
                params[index] = paramName + "=" + paramValue;
                paramFound = true;
            }
        });
        if (!paramFound) params.push(paramName + "=" + paramValue);
        url = url.substring(0, url.indexOf("?") + 1) + params.join("&");
    } else
        url += "?" + paramName + "=" + paramValue;
    window.location.href = url + hash;
}

function handleLinkClick(element) {
    let value = element.getAttribute('value');
    setGetParameter('rb', value);
    // select all elements in the sidenav
    document.querySelectorAll(".sidenav a").forEach(function (el) {
        el.classList.remove("selected");
    });
    // Add the 'selected' class to the clicked element
    clickedElement.classList.add("selected");
    return false;
}

