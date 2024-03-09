document.addEventListener("DOMContentLoaded", function () {
  var sidenav = document.querySelector(".sidenav");

  var logoDiv = document.createElement("div");
  logoDiv.id = "logo";
  sidenav.appendChild(logoDiv);

  var logoImg = document.createElement("img");
  logoImg.src = "images/ecsc_logo_small.png";
  logoImg.alt = "logo";
  logoDiv.appendChild(logoImg);
});
