var dropdownInstances = {};

function CustomDropdown(options, dropdownId, name) {
  // Create the main container div
  let container = document.createElement("div");
  container.className = "custom-select";
  container.id = "dropdown-" + dropdownId;

  // Create the button
  let button = document.createElement("button");
  button.className = "select-button";
  button.setAttribute("role", "combobox");
  button.setAttribute("aria-labelledby", "select button");
  button.setAttribute("aria-haspopup", "listbox");
  button.setAttribute("aria-expanded", "false");
  button.setAttribute("aria-controls", dropdownId);

  // Create the span for selected value
  let selectedValueSpan = document.createElement("span");
  selectedValueSpan.className = "selected-value";
  selectedValueSpan.id = "selected-value-" + dropdownId; // Unique ID for selected value span
  selectedValueSpan.textContent = "None";

  // Create the arrow span
  let arrowSpan = document.createElement("span");
  arrowSpan.className = "arrow";

  // Append spans to the button
  button.appendChild(selectedValueSpan);
  button.appendChild(arrowSpan);

  // Create the dropdown list
  let dropdownList = document.createElement("ul");
  dropdownList.className = "select-dropdown";
  dropdownList.setAttribute("role", "listbox");
  dropdownList.id = dropdownId;

  // Add options to the dropdown list
  options.forEach((option, index) => {
    let listItem = document.createElement("li");
    listItem.setAttribute("role", "option");

    let uniqueOptionId = `${option.toLowerCase()}-${name}-${index}`; // Unique ID for each option

    let input = document.createElement("input");
    input.type = "radio";
    input.id = uniqueOptionId;
    input.name = name;

    let label = document.createElement("label");
    label.setAttribute("for", uniqueOptionId);
    label.innerHTML = option;

    listItem.appendChild(input);
    listItem.appendChild(label);
    dropdownList.appendChild(listItem);
  });

  button.addEventListener("click", () => {
    // add/remove active class on the container element
    container.classList.toggle("active");
    // update the aria-expanded attribute based on the current state
    button.setAttribute("aria-expanded", button.getAttribute("aria-expanded") === "true" ? "false" : "true");
  });

  dropdownList.querySelectorAll("li").forEach((option) => {
    function handler(e) {
      // Check if the event's target is a label
      if (e.target.tagName.toLowerCase() === "label") {
        selectedValueSpan.textContent = e.target.textContent;
        container.classList.remove("active");
        console.log("Dropdown changed to: ", selectedValueSpan.textContent, " - ", dropdownId);
      }
    }
    option.addEventListener("click", handler);
  });

  // Append button and dropdown list to the container
  container.appendChild(button);
  container.appendChild(dropdownList);

  // Update setSelectedValue function to account for unique IDs
  this.setSelectedValue = function (value) {
    const optionLabels = container.querySelectorAll("label");
    for (let label of optionLabels) {
      if (label.textContent === value) {
        const uniqueId = label.getAttribute("for");
        document.getElementById(uniqueId).checked = true; // Select the radio button with the unique ID
        selectedValueSpan.textContent = value;
        break;
      }
    }
  };

  // Method to get the selected value using getElementById
  this.getSelectedValue = function () {
    const selectedValueElement = document.getElementById("selected-value-" + dropdownId);
    return selectedValueElement ? selectedValueElement.textContent : "";
  };

  // Expose the container for external access
  dropdownInstances[dropdownId] = this;
  this.container = container;
}

function addOptions(option, index) {
  options.forEach((option, index) => {
    let listItem = document.createElement("li");
    listItem.setAttribute("role", "option");

    let uniqueOptionId = `${option.toLowerCase()}-${name}-${index}`; // Unique ID for each option

    let input = document.createElement("input");
    input.type = "radio";
    input.id = uniqueOptionId;
    input.name = name;

    let label = document.createElement("label");
    label.setAttribute("for", uniqueOptionId);
    label.innerHTML = option;

    listItem.appendChild(input);
    listItem.appendChild(label);
    dropdownList.appendChild(listItem);
  });
}
// how might we be able to replace options at a later time, to be clear I would like to remove the old option list, feed it a new one
// and update the dropdown with the new options array

// Example usage
// let options = ["Commodore", "Racing", "Grounds Keeper", "Harbormaster", "Membership", "Junior Sailing Chairman"];
// let dropdown1 = new CustomDropdown(options, "dropdown-id1", "phones1");
// let dropdown2 = new CustomDropdown(options, "dropdown-id2", "phones2");
// let dropdown3 = new CustomDropdown(options, "dropdown-id3", "phones3");
// let test1 = document.getElementById("test1");
// let test2 = document.getElementById("test2");
// let test3 = document.getElementById("test3");
// test1.appendChild(dropdown1.container); // Append the dropdown to the body
// test2.appendChild(dropdown2.container); // Append the dropdown to the body
// test3.appendChild(dropdown3.container); // Append the dropdown to the body

// let dropdownInstance1 = dropdownInstances["dropdown-id1"];
// let dropdownInstance2 = dropdownInstances["dropdown-id2"];
// let dropdownInstance3 = dropdownInstances["dropdown-id3"];
// dropdownInstance1.setSelectedValue("Racing");
// dropdownInstance2.setSelectedValue("Membership");
// dropdownInstance3.setSelectedValue("Grounds Keeper");

// console.log(dropdownInstance2.getSelectedValue());

// To update options later
// dropdown.updateOptions(["New Option 1", "New Option 2", "New Option 3"]);
