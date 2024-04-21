document.addEventListener("DOMContentLoaded", function () {
  const sidenav = document.querySelector(".sidenav");
  sidenav.appendChild(createLogo());
  sidenav.appendChild(createBoatInfo());
});

function createLogo() {
  const logoDiv = document.createElement("div");
  logoDiv.id = "logo";
  const logoImg = document.createElement("img");
  logoImg.src = "images/ecsc_logo_small.png";
  logoImg.alt = "logo";
  logoDiv.appendChild(logoImg);
  return logoDiv;
}

function createBoatInfo() {
  const container = document.createElement("div");
  container.classList.add("side-container");
  boat.boatSettingsDTOS.forEach((setting) => {
    if (setting.visible) {
      // Check if there's a matching key in the normalized boatDTO object
      if (boat.boatDTO[setting.pojoName] !== undefined) {
        container.appendChild(createRow(setting.name, boat.boatDTO[setting.pojoName]));
      } else {
        console.log(`${setting.name}: No matching field in boatDTO`);
      }
    }
  });
  return container;
}

function createRow(labelText, dataText) {
  const row = document.createElement("div");
  const label = document.createElement("div");
  const data = document.createElement("div");
  row.classList.add("row");
  label.classList.add("label");
  data.classList.add("data");
  label.innerText = labelText;
  data.innerText = dataText;
  row.appendChild(label);
  row.appendChild(data);
  return row;
}
