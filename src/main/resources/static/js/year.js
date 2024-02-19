document.addEventListener("DOMContentLoaded", () => {
  const startYear = 1970;
  const endYear = new Date().getFullYear(); // This will get the current year
  populateYearSelect(startYear, endYear);
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
  document.getElementById("sidenav-yearselect").addEventListener("change", function () {
    setParameter("year", this.value);
    location.reload();
  });
  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);
  const yearParam = urlParams.get("year");
  const selectedYear = yearParam ? yearParam : endYear.toString(); // Use the current year if no year parameter is found
  yearSelect1.value = selectedYear;
  yearSelect2.value = selectedYear;
}

function setParameter(paramName, paramValue) {
  let url = new URL(window.location.href);
  let params = new URLSearchParams(url.search);
  // Set the parameter
  params.set(paramName, paramValue);
  // Construct the new URL
  url.search = params.toString();
  window.history.replaceState({}, "", url);
}
