let timeout = null;
document.addEventListener("DOMContentLoaded", function () {
  const sidenav = document.querySelector(".sidenav");
  sidenav.appendChild(createLogo());
  sidenav.appendChild(createRecordDiv());
  sidenav.appendChild(createSearchBarDiv());
  sidenav.appendChild(createButtons());
});

function createButtons() {
  const buttonContainer = document.createElement("div");
  buttonContainer.className = "button-container";

  const buttons = [
    { text: "Active Sailboats", value: "active_sailboats" },
    { text: "Active Paddlecraft", value: "active_paddlecraft" },
    { text: "All Sailboats", value: "all_sailboats" },
    { text: "All Paddlecraft", value: "all_paddlecraft" },
    { text: "All Watercraft", value: "all_watercraft" },
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
      boats.boatListType = this.value;
      fetchData(false);
    });
    if (a.value === boats.boatListType) a.classList.add("selected");
    buttonContainer.appendChild(a);
  });
  return buttonContainer;
}

function createRecordDiv() {
  const recordContentDiv = document.createElement("div");
  recordContentDiv.id = "record-content-div";
  const label = document.createElement("label");
  label.id = "numb-of-records";
  label.textContent = `Records: ${boats.boatListDTOS.length}`;
  recordContentDiv.appendChild(label);
  return recordContentDiv;
}

function createSearchBarDiv() {
  const searchBarDiv = document.createElement("div");
  searchBarDiv.id = "search-bar-div";
  const input = document.createElement("input");
  input.id = "search";
  input.placeholder = "Search";
  input.type = "text";
  input.className = "sidenav-control";
  input.addEventListener("keyup", handleKeyUp);
  searchBarDiv.appendChild(input);
  return searchBarDiv;
}

function handleKeyUp() {
  // Clear the previous timeout if the user types something new before the timeout is reached
  clearTimeout(timeout);

  // Set a new timeout
  timeout = setTimeout(() => {
    // Get the value of the input
    const searchValue = document.getElementById("search").value.trim();
    // Check if searchValue is not an empty string
    if (searchValue !== "") {
      // Split the searchValue into an array
      let valuesArray = searchValue.split(" "); // Splitting the string into an array
      // Fetch data with the search parameters
      fetchData(valuesArray);
    } else {
      // If the search field is empty, fetch data without search parameters
      fetchData();
    }
  }, 1000); // 1000 milliseconds = 1 second
}

function createLogo() {
  const logoDiv = document.createElement("div");
  logoDiv.id = "logo";
  const logoImg = document.createElement("img");
  logoImg.src = "images/ecsc_logo_small.png";
  logoImg.alt = "logo";
  logoDiv.appendChild(logoImg);
  return logoDiv;
}

function fetchData(searchParams = []) {
  let url = "";
  if (searchParams.length > 0) {
    url = `/rb_boats?type=search`;
    searchParams.forEach((param, index) => {
      url += `&param${index + 1}=${encodeURIComponent(param)}`;
    });
  } else {
    url = `/rb_boats?type=${boats.boatListType}`;
  }

  console.log(url);
  // Use the fetch API to send a GET request to the server
  // fetch(url)
  //   .then((response) => response.json())
  //   .then((data) => {
  //     // Update the rosters.membershipListDTOS array with the new data
  //     rosters.membershipListDTOS = data.membershipListDTOS;
  //     // Rebuild the table with the updated data
  //     replaceTable();
  //     updateRecordDiv();
  //   })
  //   .catch((error) => {
  //     console.error("Error fetching roster data:", error);
  //   });
}

function updateRecordDiv() {
  const label = document.getElementById("numb-of-records");
  label.textContent = `Records: ${boats.boatListDTOS.length}`;
}
