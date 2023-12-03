function newDiv(className, id) {
  let content = document.createElement("div");
  content.classList.add(className);
  content.id = id;

  // Add a method to the div for appending multiple children
  content.appendChildren = function (children) {
    children.forEach((child) => {
      this.appendChild(child);
    });
  };

  return content;
}

function newParagraph(text) {
  let paragraph = document.createElement("p");
  paragraph.textContent = text;
  return paragraph;
}

function newCheckbox(text, objectId, value) {
  let checkbox = document.createElement("input");
  checkbox.type = "checkbox";
  checkbox.id = `${text}-check-${objectId}`;
  checkbox.name = `${text}-check-${objectId}`;
  checkbox.value = value;
  return checkbox;
}

function newLabledCheckBox(objectId, text, value, label) {
  let cbContent = newDiv("checkbox-container-div", "checkbox-container-" + text + "-" + objectId);
  let checkbox = newCheckbox(text, objectId, value);
  let checkboxDiv = newDiv("checkbox", "checkbox-" + objectId);
  let checkboxlabelDiv = newDiv("checkbox-label", "checkbox-label-" + objectId);
  checkboxlabelDiv.appendChild(newLabel(label, "none", `${text}-check-${objectId}`));
  checkboxDiv.appendChild(checkbox);
  cbContent.appendChildren([checkboxDiv, checkboxlabelDiv]);
  return cbContent;
}

function newTextInput(text, value, id, placeholder, className) {
  let input = document.createElement("input");
  input.id = id;
  input.type = "text";
  input.placeholder = `${placeholder}`;
  input.className = className;
  input.name = `${text}`;
  input.value = value;
  return input;
}

function newLabel(text, classAttribute, forAttribute) {
  var label = document.createElement("label");
  label.setAttribute("for", forAttribute);
  label.textContent = `${text}`;
  label.classList.add(classAttribute);
  return label;
}

// function deleteElement(divId) {
//   let elementToDelete = document.getElementById(divId);
//   elementToDelete.style.border = "2px solid red";
//   setTimeout(() => {
//     elementToDelete.style.border = "";
//     elementToDelete.remove();
//   }, 500);
// }
function createImageElement(src, className) {
  var img = document.createElement("img");
  img.src = src;
  img.alt = "logo"; // Setting a default alt text, can be parameterized as well
  if (className) {
    img.className = className;
  }
  return img;
}

function clearElement(element) {
  while (element.firstChild) {
    element.removeChild(element.firstChild);
  }
}

class ObservableArray {
  constructor() {
    this._array = [];
  }

  add(item) {
    this._array.push(item);
    console.log("Item added:", item);
  }

  get items() {
    return this._array;
  }

  get size() {
    return this._array.length;
  }

  clear() {
    this._array = [];
    console.log("Array cleared");
  }

  clearByPid(pId) {
    this._array = this._array.filter((item) => item.pId !== pId);
    console.log(`Elements with pId ${pId} removed`);
  }

  forEach(callback) {
    this._array.forEach(callback);
  }
}
