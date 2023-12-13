let timeout = null;

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

// This keeps the search bar filled after a page reload
document.addEventListener('DOMContentLoaded', function() {
    let params = new URLSearchParams(window.location.search);
    let searchTerms = [];
    for (let i = 1; params.has('param' + i); i++) {
        searchTerms.push(params.get('param' + i));
    }
    if (searchTerms.length > 0) {
        document.getElementById('search').value = searchTerms.join(' ');
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
        setParameter('year', this.value);
        location.reload();
    });
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const product = urlParams.get('year');
    yearSelect1.value = product;
    yearSelect2.value = product;
}

function setParameter(paramName, paramValue) {
    let url = new URL(window.location.href);
    let params = new URLSearchParams(url.search);
    // Set the parameter
    params.set(paramName, paramValue);
    // Construct the new URL
    url.search = params.toString();
    window.history.replaceState({}, '', url);
}

// handles the list links
function handleLinkClick(element) {
    let value = element.getAttribute('value');
    setParameter('rb', value);
    // select all elements in the sidenav
    document.querySelectorAll(".sidenav a").forEach(function (el) {
        el.classList.remove("selected");
    });
    // Add the 'selected' class to the clicked element
    element.classList.add("selected");
    location.reload();
    return false;
}

// This is for the search bar
function handleKeyUp() {
    // Clear the previous timeout if the user types something new before the timeout is reached
    clearTimeout(timeout);
    clearSearchParams();

    // Set a new timeout
    timeout = setTimeout(() => {
        // Get the value of the input
        const searchValue = document.getElementById("search").value.trim();
        // Check if searchValue is not an empty string
        if (searchValue !== "") {
            // Split the searchValue into an array
            let valuesArray = searchValue.split(" "); // Splitting the string into an array
            // Loop through the array and set each value as a parameter
            valuesArray.forEach((val, index) => {
                // Assuming setGetParameter is defined elsewhere
                setParameter('param' + (index + 1), val);
            });
        }
        location.reload();
    }, 1000); // 1000 milliseconds = 1 second
}

// this clears the params everytime there is a change
function clearSearchParams() {
    let url = new URL(window.location.href);
    let params = new URLSearchParams(url.search);
    // Assuming all your search params start with 'param'
    Array.from(params.keys()).forEach(key => {
        if (key.startsWith('param')) {
            params.delete(key);
        }
    });
    url.search = params.toString();
    window.history.replaceState({}, '', url);
}

function sortList(paramName, paramValue) {
    setParameter(paramName, paramValue);
    location.reload();
}

function redirectToUrl(element) {
    var url = element.getAttribute('data-url');
    if (url) {
        window.open(url, '_blank');
    }
}
