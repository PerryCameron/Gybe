document.addEventListener("DOMContentLoaded", function () {
  const sidenav = document.querySelector(".sidenav");

  const logoDiv = document.createElement("div");
  logoDiv.id = "logo";
  sidenav.appendChild(logoDiv);

  const logoImg = document.createElement("img");
  logoImg.src = "images/ecsc_logo_small.png";
  logoImg.alt = "logo";
  logoDiv.appendChild(logoImg);

  const buttonContainer = document.createElement("div");
  buttonContainer.className = "button-container";
  sidenav.appendChild(buttonContainer);

  const currentYear = new Date().getFullYear(); // Get the current year

  const links = [
    {href: `lists?year=${currentYear}&rb=active`, text: "Rosters", id: "option1", target: "_blank"},
    {href: "bod", text: "Board of Directors", id: "option2", target: "_blank"},
    {href: "form-request-summary", text: "Form Requests", target: "_blank"},
    {href: "slip_chart", text: "Slips", target: "_blank"},
  ];

  links.forEach(function (link) {
    const a = document.createElement("a");
    a.href = link.href;
    a.textContent = link.text;
    if (link.id) a.id = link.id;
    if (link.target) a.target = link.target;
    buttonContainer.appendChild(a);
  });
});
