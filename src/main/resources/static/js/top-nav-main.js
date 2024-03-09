document.addEventListener("DOMContentLoaded", function () {
  var topNav = document.getElementById("top-nav");

  var yearSelectDiv = document.createElement("div");
  yearSelectDiv.className = "topnav-child-div";
  yearSelectDiv.id = "topnav-yearselect-div";
  topNav.appendChild(yearSelectDiv);

  var yearSelect = document.createElement("select");
  yearSelect.id = "topnav-yearselect";
  yearSelect.className = "topnav-control";
  yearSelect.name = "topnavyear";
  yearSelectDiv.appendChild(yearSelect);

  var listSelectDiv = document.createElement("div");
  listSelectDiv.id = "list-select-div";
  topNav.appendChild(listSelectDiv);

  var listSelect = document.createElement("select");
  listSelect.id = "topnav-select-field";
  listSelect.className = "topnav-control";
  listSelect.name = "topnavlists";
  listSelectDiv.appendChild(listSelect);

  var options = [
    { value: "all", text: "Rosters" },
    { value: "active", text: "People" },
    { value: "non-renew", text: "Slips" },
    { value: "new-members", text: "Board of Directors" },
    { value: "return-members", text: "Create New Membership" },
    { value: "slip", text: "Deposits" },
    { value: "new-members", text: "Boats" },
    { value: "return-members", text: "Notes" },
  ];

  options.forEach(function (option) {
    var opt = document.createElement("option");
    opt.value = option.value;
    opt.textContent = option.text;
    listSelect.appendChild(opt);
  });

  var searchDiv = document.createElement("div");
  searchDiv.className = "topnav-child-div";
  topNav.appendChild(searchDiv);

  var searchForm = document.createElement("form");
  searchDiv.appendChild(searchForm);

  var searchField = document.createElement("input");
  searchField.id = "topnav-search-field";
  searchField.placeholder = "Search";
  searchField.type = "text";
  searchField.className = "topnav-control";
  searchForm.appendChild(searchField);
});
