let table = "<table class='styled-table'><thead class='header-link'>";
table += "<tr><th class='header'>Date</th><th>Primary Member</th><th>Link</th><th>Email</th></tr></thead>";

formRequestData.forEach((request) => {
  table += `<tr>
                <td>${request.req_date}</td>
                <td>${request.pri_mem}</td>
                <td><a href="${request.link}">Register Link</a></td>
                <td><a href="mailto:${request.mailed_to}">${request.mailed_to}</a></td>
              </tr>`;
});

table += "</table>";

document.getElementById("formRequestTable").innerHTML = table;
updateRecordCount(formRequestData);

function updateRecordCount(array) {
  // Retrieve the element by ID
  var recordLabel = document.getElementById("numb-of-records");

  // Update the content of the element
  if (recordLabel) {
    recordLabel.textContent = "Records: " + array.length;
  }
}
