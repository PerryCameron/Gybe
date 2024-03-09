document.addEventListener("DOMContentLoaded", function () {
  var topNav = document.getElementById("top-nav");

  var listSelectDiv = document.createElement("div");
  listSelectDiv.id = "list-select-div";
  listSelectDiv.style.minHeight = "50px"; // Set a minimum height
  listSelectDiv.style.border = "1px solid #ccc"; // Add a border for visibility
  topNav.appendChild(listSelectDiv);
});
