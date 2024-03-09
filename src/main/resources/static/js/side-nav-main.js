document.addEventListener("DOMContentLoaded", function () {
  var sidenav = document.querySelector(".sidenav");

  var logoDiv = document.createElement("div");
  logoDiv.id = "logo";
  sidenav.appendChild(logoDiv);

  var logoImg = document.createElement("img");
  logoImg.src = "images/ecsc_logo_small.png";
  logoImg.alt = "logo";
  logoDiv.appendChild(logoImg);

  var buttonContainer = document.createElement("div");
  buttonContainer.className = "button-container";
  sidenav.appendChild(buttonContainer);

  var currentYear = new Date().getFullYear(); // Get the current year

  var links = [
    { href: `lists?year=${currentYear}&rb=active`, text: "Rosters", id: "option1", target: "_blank" },
    { href: "bod", text: "Board of Directors", id: "option2", target: "_blank" },
    { href: "form-request-summary", text: "Form Requests", target: "_blank" },
  ];

  links.forEach(function (link) {
    var a = document.createElement("a");
    a.href = link.href;
    a.textContent = link.text;
    if (link.id) a.id = link.id;
    if (link.target) a.target = link.target;
    buttonContainer.appendChild(a);
  });
});
