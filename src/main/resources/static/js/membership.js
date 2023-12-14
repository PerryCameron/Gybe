document.getElementById("defaultOpen").click();
updateTitle("Mem " + membershipData.membershipId);

function openCity(evt, cityName) {
  // Declare all variables
  var i, tabcontent, tablinks;

  // Get all elements with class="tabcontent" and hide them
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }

  // Get all elements with class="tablinks" and remove the class "active"
  tablinks = document.getElementsByClassName("tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }

  // Show the current tab, and add an "active" class to the button that opened the tab
  document.getElementById(cityName).style.display = "block";
  evt.currentTarget.className += " active";
}

function findPersonByMemberType(memberType) {
  return membershipData.personDTOS.find((person) => person.memberType === memberType);
}

function findPersonByPid(pId) {
  return membershipData.personDTOS.find((person) => person.pId === pId);
}

const pIdArray = membershipData.personDTOS.map((person) => person.pId);

// Membership Info
document.getElementById("membership-id").textContent = membershipData.membershipId;
document.getElementById("year").textContent = membershipData.selectedYear;
document.getElementById("type").textContent = setMembershipType(membershipData.memType);
document.getElementById("join-date").textContent = membershipData.joinDate;

// Address Info
document.getElementById("address-line1").value = membershipData.address;
document.getElementById("address-line2").value = "";
document.getElementById("address-city").value = membershipData.city;
document.getElementById("address-state").value = membershipData.state;
document.getElementById("address-zip").value = membershipData.zip;

function setMembershipType(memberType) {
  switch (memberType) {
    case "RM":
      return "Regular";
    case "SO":
      return "Social";
    default:
      return "Family";
  }
}

const phoneTypeMappings = {
  C: "Cell",
  E: "Emergency",
  W: "Work",
  H: "Home",
  Cell: "C",
  Emergency: "E",
  Work: "W",
  Home: "H",
};

function updateTitle(newTitle) {
  document.title = newTitle;
}
