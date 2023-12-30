document.addEventListener("DOMContentLoaded", () => {
  const startYear = 2022;
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
}

let table = "<table class='styled-table'><thead class='header-link'>";
table +=
  "<tr><th>ID</th><th>Email Request Date</th><th>Primary Member</th><th>Link</th><th>Email</th><th>Email Attempts</th><th>Form Request Date</th><th>Form Attempts</th></tr></thead>";

// Your forEach loop and table generation code here
formSummaryData.forEach((request) => {
  table += `<tr>
                <td>${request.membershipId}</td>
                <td>${request.newestHashReqDate}</td>
                <td>${request.priMem}</td>
                <td><a href="${request.link}" target='_blank'>Register Link</a></td>
                <td><a href="mailto:${request.mailedTo}">${request.mailedTo}</a></td>
                <td>${request.numHashDuplicates}</td>
                <td>${request.newestFormReqDate}</td>
                <td>${request.numFormAttempts}</td>
              </tr>`;
});

table += "</table>";
document.getElementById("formRequestTable").innerHTML = table;

updateRecordCount(formSummaryData);

function updateRecordCount(array) {
  // Retrieve the element by ID
  var recordLabel = document.getElementById("numb-of-records");

  // Update the content of the element
  if (recordLabel) {
    recordLabel.textContent = "Records: " + array.length;
  }
}
