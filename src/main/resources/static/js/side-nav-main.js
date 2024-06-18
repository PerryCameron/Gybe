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
    {href: "Rosters", text: "Rosters", id: "option1", target: "_blank", roles: ["ROLE_MEMBERSHIP"]},
    {href: "bod", text: "Board of Directors", id: "option2", target: "_blank", roles: ["ROLE_USER"]},
    {href: "form-request-summary", text: "Form Requests", target: "_blank", roles: ["ROLE_MEMBERSHIP"]},
    {href: "javascript:slipChart()", text: "Slips", target: "", roles: ["ROLE_USER"]},
    {href: "boat_list", text: "Boats", target: "_blank", roles: ["ROLE_MEMBERSHIP","ROLE_HARBORMASTER"]},
    {href: "publicity", text: "Publicity", target: "_blank", roles: ["ROLE_PUBLICITY"]},
  ];

  links.forEach(function (link) {
    const hasRole = link.roles.some(role => userRoles.includes(role));
    if (hasRole) {
      const a = document.createElement("a");
      // if href starts with function- method is after, so link to the function
      a.href = link.href;
      a.textContent = link.text;
      if (link.id) a.id = link.id;
      if (link.target) a.target = link.target;
      buttonContainer.appendChild(a);
    }
  });
});

function slipChart() {
  const mainDiv = document.getElementById("main-content");
  mainDiv.innerHTML = '<canvas id="slipChart" width="1200" height="1000"></canvas>';

  fetch('/api/slip_chart')
      .then(response => response.json())
      .then(data => {
        // Dynamically load slips.js and then call buildDock
        const script = document.createElement('script');
        script.src = '/js/slip-rest.js';
        script.onload = function() {
          buildDock(data);
        };
        document.body.appendChild(script);
      })
      .catch(error => {
        console.error('Error fetching slip chart data:', error);
      });
}
