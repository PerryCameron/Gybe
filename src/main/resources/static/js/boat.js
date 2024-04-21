const mainDiv = document.getElementById("main");
const defaultPhotoIndex = boat.photosDTOS.findIndex((photo) => photo.default === true);
let currentIndex = defaultPhotoIndex === -1 ? 0 : defaultPhotoIndex; // Start at default photo or the first one
let img = document.createElement("img");

mainDiv.appendChild(createBoatButtonControl());
mainDiv.appendChild(createBoatAttributes());

function createBoatAttributes() {
  const container = document.createElement("div");
  container.classList.add("photo-container");
  let defaultPhoto = boat.photosDTOS.find((photo) => photo.default === true);

  if (defaultPhoto) {
    img.src = "/boat_images/" + defaultPhoto.filename;
    img.alt = "Default Boat Photo";
    container.appendChild(img);
  } else {
    console.log("No default photo found.");
  }
  return container;
}

function createBoatButtonControl() {
  const container = document.createElement("div");
  container.classList.add("button-control-container-div");
  const addButton = createImageButton("add-button.svg", "Add");
  const leftButton = createImageButton("fi-rr-arrow-small-left.svg", "Previous");
  const rightButton = createImageButton("fi-rr-arrow-small-right.svg", "Next");
  const defaultButton = createImageButton("fi-rr-check.svg", "Default");
  const deleteButton = createImageButton("delete.svg", "Delete");
  // Append the button to the container
  leftButton.onclick = function () {
    currentIndex = (currentIndex - 1 + boat.photosDTOS.length) % boat.photosDTOS.length;
    updatePhoto(currentIndex);
  };
  rightButton.onclick = function () {
    currentIndex = (currentIndex + 1) % boat.photosDTOS.length;
    updatePhoto(currentIndex);
  };
  container.appendChild(leftButton);
  container.appendChild(rightButton);
  container.appendChild(defaultButton);
  container.appendChild(addButton);
  container.appendChild(deleteButton);
  return container;
}

function createImageButton(image, alt) {
  const button = document.createElement("button");
  button.classList.add("boat-control");
  const img = document.createElement("img");
  img.src = "images/" + image; // Path to your image
  img.alt = alt;
  img.classList.add("boat-control-img");
  button.appendChild(img);
  return button;
}

// Function to update displayed photo
function updatePhoto(index) {
  const photo = boat.photosDTOS[index];
  img.src = "/boat_images/" + photo.filename;
  img.alt = "Boat Photo " + (index + 1);
  console.log("/boat_images/" + photo.filename);
}
