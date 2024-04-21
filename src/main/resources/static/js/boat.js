const mainDiv = document.getElementById("main");

mainDiv.appendChild(createBoatAttributes());

function createBoatAttributes() {
  const container = document.createElement("div");
  let defaultPhoto = boat.photosDTOS.find((photo) => photo.default === true);

  if (defaultPhoto) {
    let img = document.createElement("img");
    img.src = "/boat_images/" + defaultPhoto.filename;
    img.alt = "Default Boat Photo";
    container.appendChild(img);
  } else {
    console.log("No default photo found.");
  }
  return container;
}
