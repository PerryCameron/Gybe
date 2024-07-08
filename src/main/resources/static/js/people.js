const tabPane = document.getElementById("vertical-tab-pane");
const personContent = document.getElementById("person-info");
tabPane.classList.add("top-margin");
personContent.classList.add("content-top-margin");
const positions = extractPositions(boardPositions);
let bidirectionalMap = createBidirectionalMap(boardPositions);
const phonesToAdd = new ObservableArray();
const emailToAdd = new ObservableArray();
const positionsToAdd = new ObservableArray();

membershipData.personDTOS.forEach((person, index) => {
  const tab = document.createElement("div");
  tab.classList.add("tab");
  const content = document.createElement("div");
  content.classList.add("content");
  content.id = "content-" + person.pId;
  if (index === 0) tab.classList.add("active");
  tab.appendChild(createImageElement("images/" + setTabContent(person.memberType), "person-icon"));
  // tab.textContent = setTabContent(person.memberType);
  // add person, phones, email, etc..
  addTo(content, person);
  tabPane.appendChild(tab);
  personContent.appendChild(content);
});

function addTo(content, person) {
  // Clear existing content
  content.innerHTML = "";
  content.appendChild(addPerson(person));
  content.appendChild(addPhones(person));
  content.appendChild(addEmail(person));
  content.appendChild(addOfficer(person));
}

/////////////////////////////// PERSON ////////////////////////////////////////
/////////////////////////////// PERSON ////////////////////////////////////////
/////////////////////////////// PERSON ////////////////////////////////////////
/////////////////////////////// PERSON ////////////////////////////////////////
/////////////////////////////// PERSON ////////////////////////////////////////
/////////////////////////////// PERSON ////////////////////////////////////////

function addPerson(person) {
  const content = document.createElement("div");
  content.appendChild(addHeader(2, `${person.firstName} ${person.lastName}`, "setPersonEdit", person.pId, "edit.svg"));
  content.appendChild(addLineDiv("person-line", "Person ID: " + person.pId));
  content.appendChild(addLineDiv("person-line", "Nickname: " + person.nickName));
  content.appendChild(addLineDiv("person-line", "Occupation: " + person.occupation));
  content.appendChild(addLineDiv("person-line", "Business: " + person.business));
  content.appendChild(addLineDiv("person-line", "Birthday: " + person.birthday));
  content.appendChild(addLineDiv("person-line", "Age: " + calculateAge(person.birthday)));
  content.appendChild(createHorizontalLine("line-half"));
  return content;
}

function setPersonEdit(pId) {
  const content = document.getElementById("content-" + pId); // pId will be converted to a string here
  if (content) {
    let person = findPersonByPId(pId);
    content.innerHTML = "";
    content.appendChild(createLabeledText("First Name", person.firstName, `firstName-${pId}`));
    content.appendChild(createLabeledText("Last Name", person.lastName, `lastName-${pId}`));
    content.appendChild(createLabeledText("Nickname", person.nickName, `nickName-${pId}`));
    content.appendChild(createLabeledText("Occupation", person.occupation, `occupation-${pId}`));
    content.appendChild(createLabeledText("Business", person.business, `business-${pId}`));
    content.appendChild(createLabeledText("Birthday", person.birthday, `birthday-${pId}`));
    content.appendChild(setBottomButtons(pId, updatePerson, "person"));
  } else {
    console.error("Element not found: content-" + pId);
  }
}

////////      PHONE ////////////////////////////////////////
////////      PHONE ////////////////////////////////////////
////////      PHONE ////////////////////////////////////////
////////      PHONE ////////////////////////////////////////
////////      PHONE ////////////////////////////////////////
////////      PHONE ////////////////////////////////////////

function addPhones(person) {
  const content = document.createElement("div");
  content.appendChild(addHeader(4, "Phone", "setPhoneEdit", person.pId, "edit.svg"));
  person.phones.forEach((phone) => {
    content.appendChild(addLineDiv("person-line", phone.phone + " - " + getPhoneMapping(phone.phoneType)));
  });
  return content;
}

function setPhoneEdit(pId) {
  let person = findPersonByPId(pId);
  const content = createEditDiv(pId, "phone-error");
  content.appendChild(addHeader(2, "Phone", "newPhone", pId, "add-button.svg"));
  person.phones.forEach((phone) => content.appendChild(createPhoneEditRow(phone)));
  content.appendChild(setBottomButtons(pId, updatePhones, "phone"));
  addPhoneListeners(person.pId);
  person.phones.forEach(
    // performed after it is all attached to DOM
    (phone) => {
      let dropdownInstance = dropdownInstances[`phone-${phone.phoneId}`];
      let phoneTypeValue = getPhoneMapping(phone.phoneType);
      let container = document.getElementById("dropdown-phone-" + phone.phoneId);
      container.classList.add("phone-width");
      dropdownInstance.setSelectedValue(phoneTypeValue);
      document.getElementById(`phone-check-${phone.phoneId}`).checked = phone.phoneListed;
    }
  );
}

function createPhoneEditRow(phone) {
  let containerName = "phone-edit-container-" + phone.phoneId;
  let errorTopName = "phone-error-top-" + phone.phoneId;
  let errorDiv = newDiv("error-top", errorTopName);
  let content = newDiv("phone-edit-container", containerName);
  let subContent1 = newDiv("phone-sub1", "phone-sub1-" + phone.phoneId);
  let subContent2 = newDiv("phone-sub2", "phone-sub2-" + phone.phoneId);
  let options = ["Cell", "Home", "Emergency", "Work"];
  let dropdown = new CustomDropdown(options, `phone-${phone.phoneId}`, `name-${phone.phoneId}`);
  subContent1.appendChild(newTextInput("phone", phone.phone, "phone-" + phone.phoneId, "Phone", "phone-input"));
  subContent1.appendChild(newLabledCheckBox(phone.phoneId, "phone", phone.phoneListed, "Listed"));
  subContent1.appendChild(createDeleteLinkWithImage("deleteElement", containerName, "delete.svg", errorTopName));
  subContent2.appendChild(dropdown.container);
  content.appendChildren([errorDiv, subContent1, subContent2, createHorizontalLine("line-full")]);
  return content;
}

function newPhone(pId) {
  let phoneContainer = document.getElementById("content-" + pId);
  let button = document.getElementById("updateButton-phone-" + pId);
  let phone = {
    phoneId: pId + "-" + getRandomNumber(1, 100000), // new unique phone ID - remove when connected to back end
    pId: pId, // assuming the same pId as others
    phoneListed: true,
    phone: "", // new phone number
    phoneType: "C", // new phone type
    membershipTypes: null,
  };
  // phonesToAdd.push(phone); checkHere
  phonesToAdd.add(phone);
  insertNewObject(phoneContainer, createPhoneEditRow(phone));
  // sets dropdown to correct phoneType
  createPhoneListener(phone, button);
  let container = document.getElementById("dropdown-phone-" + phone.phoneId);
  container.classList.add("phone-width");
}

function addPhoneListeners(pId) {
  let person = findPersonByPId(pId);
  let button = document.getElementById("updateButton-phone-" + pId);
  person.phones.forEach((phone) => {
    createPhoneListener(phone, button);
  });
}

function createPhoneListener(phone, button) {
  let inputElement = document.getElementById("phone-" + phone.phoneId);
  let container = document.getElementById("phone-edit-container-" + phone.phoneId);
  if (inputElement) {
    // Create a single handler function
    let handlePhoneInputEvent = function (event) {
      // Check for Enter key press or blur event
      if (event.type === "blur" || (event.type === "keypress" && event.key === "Enter")) {
        let errorDiv = document.getElementById("phone-error-top-" + phone.phoneId);
        if (validatePhoneNumber(event.target.value)) {
          let number = formatNumber(event.target.value);
          inputElement.value = number;
          errorDiv.style.display = "none"; // Hide error message
          errorDiv.textContent = ""; // Clear error message text
          container.style.border = "";
          button.disabled = false;
        } else {
          errorDiv.innerHTML = "<p class='red-alert'>Invalid phone number</p>";
          errorDiv.style.display = "block"; // Show error message
          container.style.border = "2px solid red";
          button.disabled = true;
        }
      }
    };
    // Attach the same handler for both blur and keypress events
    inputElement.addEventListener("blur", handlePhoneInputEvent);
    inputElement.addEventListener("keypress", handlePhoneInputEvent);
  }
}

function updatePhones(pId) {
  let person = findPersonByPId(pId);
  let phonesToDelete = [];
  // adds any phones that were added
  phonesToAdd.forEach((phone) => {
    if (phone.pId === pId) person.phones.push(phone);
  });
  phonesToAdd.clearByPid(pId);
  // only remove phones from this person
  person.phones.forEach((phone) => {
    let phoneElement = document.getElementById("phone-edit-container-" + phone.phoneId);
    if (phoneElement) {
      // Delete phones marked for deletion here
      if (phoneElement.className === "phone-edit-container delete") {
        phonesToDelete.push(phone.phoneId);
      }
      // Update the phone data if the element exists
      let inputElement = document.getElementById("phone-" + phone.phoneId);
      let dropdownInstance = dropdownInstances[`phone-${phone.phoneId}`];
      let checkElement = document.getElementById("phone-check-" + phone.phoneId);
      phone.phone = inputElement.value;
      phone.phoneType = getPhoneMapping(dropdownInstance.getSelectedValue());
      phone.phoneListed = checkElement.checked;
    }
  });
  // Perform deletion after iteration
  phonesToDelete.forEach((phoneId) => {
    deleteItemById(phoneId, person.phones, "phoneId");
  });
  returnToPerson(pId);
}

////////      EMAIL ////////////////////////////////////////  EMAIL
////////      EMAIL ////////////////////////////////////////  EMAIL
////////      EMAIL ////////////////////////////////////////  EMAIL
////////      EMAIL ////////////////////////////////////////  EMAIL
////////      EMAIL ////////////////////////////////////////  EMAIL
////////      EMAIL ////////////////////////////////////////  EMAIL

function addEmail(person) {
  const content = document.createElement("div");
  content.classList.add("email-content");
  content.appendChild(createHorizontalLine("line-half"));
  content.appendChild(addHeader(4, "Email", "setEmailEdit", person.pId, "edit.svg"));
  person.email.forEach((email) => {
    content.innerHTML += `<div class="spaced-line"><a href="mailto:${email.email} ">${email.email} </a></div>`;
  });
  return content; // This associates phones with the specific person
}

function setEmailEdit(pId) {
  person = findPersonByPId(pId);
  const content = createEditDiv(pId, "email-error");
  content.appendChild(addHeader(2, "Email", "newEmail", pId, "add-button.svg"));
  person.email.forEach((email) => content.appendChild(createEmailEditRow(email)));
  content.appendChild(setBottomButtons(pId, updateEmail, "email"));
  addEmailListeners(pId);
  // populates them
  person.email.forEach((email) => {
    document.getElementById(`email-primary-check-${email.emailId}`).checked = email.primaryUse;
    document.getElementById(`email-listed-check-${email.emailId}`).checked = email.emailListed;
  });
}

function createEmailEditRow(email) {
  let containerName = "email-edit-container-" + email.emailId;
  let errorTopName = "email-error-top-" + email.emailId;
  let errorDiv = newDiv("error-top", errorTopName);
  let content = newDiv("email-edit-container", containerName);
  let inputContent = newDiv("email-input", "email-input-" + email.emailId);
  inputContent.appendChild(newTextInput("email", email.email, "email-" + email.emailId, "Email", "email-text-input"));
  inputContent.appendChild(createDeleteLinkWithImage("deleteElement", containerName, "delete.svg", errorTopName));
  content.appendChild(errorDiv);
  content.appendChild(inputContent);
  content.appendChild(newLabledCheckBox(email.emailId, "email-primary", email.primaryUse, "Primary Use"));
  content.appendChild(newLabledCheckBox(email.emailId, "email-listed", email.emailListed, "Listed"));
  content.appendChild(createHorizontalLine("line-full"));
  return content;
}

function newEmail(pId) {
  let emailContainer = document.getElementById("content-" + pId);
  let button = document.getElementById("updateButton-email-" + pId);
  let email = {
    emailId: pId + "-" + getRandomNumber(1, 100000),
    pId: pId,
    primaryUse: false,
    email: "",
    emailListed: true,
  };
  emailToAdd.add(email);
  insertNewObject(emailContainer, createEmailEditRow(email));
  createEmailListener(email, button);
}

function addEmailListeners(pId) {
  let person = findPersonByPId(pId);
  let button = document.getElementById("updateButton-email-" + pId);
  person.email.forEach((email) => {
    createEmailListener(email, button);
  });
}

function createEmailListener(email, button) {
  let inputElement = document.getElementById("email-" + email.emailId);
  let container = document.getElementById("email-edit-container-" + email.emailId);
  if (inputElement) {
    // Create a single handler function
    let handleEmailInputEvent = function (event) {
      // Check for Enter key press or blur event
      if (event.type === "blur" || (event.type === "keypress" && event.key === "Enter")) {
        let errorDiv = document.getElementById("email-error-top-" + email.emailId);
        if (validateEmail(event.target.value)) {
          email.email = event.target.value;
          errorDiv.style.display = "none"; // Hide error message
          errorDiv.textContent = ""; // Clear error message text
          container.style.border = "";
          button.disabled = false;
        } else {
          errorDiv.innerHTML = "<p class='red-alert'>Invalid Email</p>";
          errorDiv.style.display = "block"; // Show error message
          container.style.border = "2px solid red";
          button.disabled = true;
        }
      }
    };
    // Attach the same handler for both blur and keypress events
    inputElement.addEventListener("blur", handleEmailInputEvent);
    inputElement.addEventListener("keypress", handleEmailInputEvent);
  }
}

function updateEmail(pId) {
  let person = findPersonByPId(pId);
  let emailToDelete = [];
  // adds any emails that were added
  emailToAdd.forEach((email) => {
    if (email.pId === pId) person.email.push(email);
  });
  emailToAdd.clearByPid(pId);
  // only remove email from this person
  person.email.forEach((email) => {
    let emailElement = document.getElementById("email-edit-container-" + email.emailId);
    if (emailElement) {
      // Update the email data if the element exists
      if (emailElement.className === "email-edit-container delete") {
        emailToDelete.push(email.emailId);
      }
      let inputElement = document.getElementById("email-" + email.emailId);
      let primaryElement = document.getElementById("email-primary-check-" + email.emailId);
      let listedElement = document.getElementById("email-listed-check-" + email.emailId);
      email.email = inputElement.value;
      email.primaryUse = primaryElement.checked;
      email.emailListed = listedElement.checked;
    } else {
      // Mark the email for deletion if its element doesn't exist
      emailToDelete.push(email.emailId);
    }
  });
  // Perform deletion after iteration
  emailToDelete.forEach((emailId) => {
    deleteItemById(emailId, person.email, "emailId");
  });
  returnToPerson(pId);
}

/////////////////////////////// OFFICER ////////////////////////////////////////
/////////////////////////////// OFFICER ////////////////////////////////////////
/////////////////////////////// OFFICER ////////////////////////////////////////
/////////////////////////////// OFFICER ////////////////////////////////////////
/////////////////////////////// OFFICER ////////////////////////////////////////
/////////////////////////////// OFFICER ////////////////////////////////////////

function addOfficer(person) {
  const content = document.createElement("div");
  content.classList.add("position-content");
  content.appendChild(createHorizontalLine("line-half"));
  content.appendChild(addHeader(4, "Officer", "setOfficerEdit", person.pId, "edit.svg"));
  person.officers.forEach((officer) => {
    let position = getPositionByIdentifier(officer.officerType, boardPositions);
    htmlEmailContent = `<p>${officer.fiscalYear} -  ${position}</p>`;
    content.innerHTML += htmlEmailContent; // Append, don't overwrite
  });
  return content;
}

function setOfficerEdit(pId) {
  const content = createEditDiv(pId, "club-error");
  let person = findPersonByPId(pId);
  content.appendChild(addHeader(2, "Club Position", "newOfficer", pId, "add-button.svg"));
  person.officer.forEach((officer) => {
    content.appendChild(createOfficerEditRow(officer, pId));
  });
  // performed after it is all attached to DOM
  person.officer.forEach((officer) => {
    let positionInstance = dropdownInstances[`officer-${officer.officerId}`];
    let endYearInstance = dropdownInstances[`board-end-year-${officer.officerId}`];
    positionInstance.setSelectedValue(bidirectionalMap.getPositionByIdentifier(officer.officerType));
    endYearInstance.setSelectedValue(officer.boardYear);
    let positionContainer = document.getElementById("dropdown-officer-" + officer.officerId);
    positionContainer.classList.add("officer-width");
    let endYearContainer = document.getElementById("dropdown-board-end-year-" + officer.officerId);
    endYearContainer.classList.add("board-end-year-width");
  });
  content.appendChild(setBottomButtons(pId, updateOfficer, "officer"));
}

function createOfficerEditRow(officer, pId) {
  let containerName = "officer-edit-container-" + officer.officerId;
  let content = newDiv("officer-edit-container", containerName);
  let errorTopName = "officer-error-top-" + officer.officerId;
  let errorDiv = newDiv("error-top", errorTopName);
  let subContent1 = newDiv("officer-sub1", "officerSub1-" + pId);
  let subContent2 = newDiv("officer-sub2", "officerSub2-" + pId);
  content.appendChildren([errorDiv, subContent1, subContent2, createHorizontalLine("line-full")]);
  let dropdown1 = new CustomDropdown(positions, `officer-${officer.officerId}`, `name-${officer.officerId}`);
  let textInput = newTextInput(
    "fiscalYear",
    officer.fiscalYear,
    "fiscalYear-" + officer.officerId,
    "Fiscal Year",
    "fiscalYear-input"
  );
  let dropdown2 = dropDownYear(officer, parseInt(officer.fiscalYear));
  subContent1.appendChildren([textInput, dropdown1.container]);
  subContent1.appendChild(createDeleteLinkWithImage("deleteElement", containerName, "delete.svg", errorTopName));
  subContent2.appendChildren([newParagraph("Board Term Expiration"), dropdown2.container]);
  textInput.addEventListener("change", function (event) {
    createOfficerListener(officer, subContent2, event);
  });
  return content;
}

function newOfficer(pId) {
  let positionContainer = document.getElementById("content-" + pId);
  let currentYear = new Date().getFullYear();
  let boardYear = currentYear + 3;
  let position = {
    officerId: pId + "-" + getRandomNumber(1, 100000),
    pId: pId,
    boardYear: String(boardYear),
    officerType: "Board Member",
    fiscalYear: String(currentYear),
  };
  positionsToAdd.add(position);
  insertNewObject(positionContainer, createOfficerEditRow(position, pId));
  let dropdownContainer = document.getElementById("dropdown-officer-" + position.officerId);
  dropdownContainer.classList.add("officer-width");
}

function createOfficerListener(officer, content, event) {
  const result = validateYear(event.target.value);
  let container = document.getElementById("officer-edit-container-" + officer.officerId);
  let errorDiv = document.getElementById("officer-error-top-" + officer.officerId);
  let button = document.getElementById("updateButton-officer-" + officer.pId);

  if (result.valid) {
    // Additional actions for a valid year
    startYear = result.year;
    clearElement(content);
    content.appendChildren([
      newParagraph("Board Term Expiration"),
      dropDownYear(officer, parseInt(result.year)).container,
    ]);
    let endYearContainer = document.getElementById("dropdown-board-end-year-" + officer.officerId);
    endYearContainer.classList.add("board-end-year-width");
    errorDiv.style.display = "none"; // Hide error message
    errorDiv.textContent = ""; // Clear error message text
    container.style.border = "";
    button.disabled = false;
  } else {
    errorDiv.innerHTML =
      "<p class='red-alert'>Invalid year: Please enter a year between 1970 and the current year.</p>";
    errorDiv.style.display = "block"; // Show error message
    container.style.border = "2px solid red";
    button.disabled = true;
  }
}

function updateOfficer(pId) {
  let person = findPersonByPId(pId);
  let officerToDelete = [];
  // adds any positions that were marked for add
  positionsToAdd.forEach((officer) => {
    if (officer.pId === pId) person.officer.push(officer);
  });
  // only remove positions from this person marked for delete
  positionsToAdd.clearByPid(pId);

  person.officer.forEach((officer) => {
    let officerElement = document.getElementById("officer-edit-container-" + officer.officerId);
    if (officerElement) {
      if (officerElement.className === "officer-edit-container delete") {
        officerToDelete.push(officer.officerId);
      }
      // Update the position data if the element exists
      let inputElement = document.getElementById("fiscalYear-" + officer.officerId);
      let dropdownPositionInstance = dropdownInstances[`officer-${officer.officerId}`];
      let dropdownBoardYearInstance = dropdownInstances[`board-end-year-${officer.officerId}`];
      officer.fiscalYear = inputElement.value;
      officer.officerType = bidirectionalMap.getIdentifierByPosition(dropdownPositionInstance.getSelectedValue());
      officer.boardYear = dropdownBoardYearInstance.getSelectedValue();
    }
  });
  // Perform deletion after iteration
  officerToDelete.forEach((officerId) => {
    deleteItemById(officerId, person.officer, "officerId");
  });
  returnToPerson(pId);
}

////////////////////////////////////// END //////////////////////////////
////////////////////////////////////// END //////////////////////////////
////////////////////////////////////// END //////////////////////////////
////////////////////////////////////// END //////////////////////////////
////////////////////////////////////// END //////////////////////////////

function deleteElement(divId, topErrorDiv) {
  let container = document.getElementById(divId);
  container.classList.add("delete");
  let errorDiv = document.getElementById(topErrorDiv);
  errorDiv.innerHTML = "<p class='red-alert'>Set to Delete</p>";
  errorDiv.style.display = "block"; // Show error message
  container.style.border = "2px solid red";
}

function createDeleteLinkWithImage(onClickFunctionName, objectId, image, errorTopName) {
  var link = document.createElement("a");
  link.className = "edit-link";
  link.setAttribute("onclick", `${onClickFunctionName}('${objectId}', '${errorTopName}')`);
  var img = document.createElement("img");
  img.src = `images/${image}`;
  img.alt = "edit";
  img.className = "edit-icon";
  link.appendChild(img);
  return link;
}

function createEditLinkWithImage(onClickFunctionName, pId, image) {
  var link = document.createElement("a");
  link.className = "edit-link";
  // link.setAttribute("data-person-id", pId);
  link.setAttribute("onclick", `${onClickFunctionName}(${pId})`);
  var img = document.createElement("img");
  img.src = `images/${image}`;
  img.alt = "edit";
  img.className = "edit-icon";
  link.appendChild(img);
  return link;
}

function createHorizontalLine(className) {
  const hr = document.createElement("hr");
  hr.classList.add(className);
  return hr;
}

function createLabeledText(labelText, value, fieldName) {
  const content = document.createElement("div");
  content.classList.add("person-edit-div");
  content.appendChild(newLabel(labelText, "field-label", fieldName));
  content.appendChild(newTextInput(fieldName, value, fieldName, labelText, "short-input"));
  return content;
}

function setBottomButtons(pId, clickHandler, type) {
  const buttonDiv = document.createElement("div");
  buttonDiv.classList.add("button-div");
  let updateButton = createButton(pId, `updateButton-${type}`, "Update", "button-submit");
  let cancelButton = createButton(pId, `cancelButton-${type}`, "Cancel", "button-cancel");
  buttonDiv.appendChild(updateButton);
  buttonDiv.appendChild(cancelButton);
  updateButton.addEventListener("click", function () {
    clickHandler(pId);
  });
  cancelButton.addEventListener("click", function () {
    returnToPerson(pId);
  });
  return buttonDiv;
}

function createButton(pId, buttonId, text, classnm) {
  const button = document.createElement("button");
  button.className = `${classnm}`;
  button.setAttribute("role", "button");
  button.type = "button";
  button.id = `${buttonId}-${pId}`;
  button.textContent = `${text}`;
  return button;
}

////////      UPDATE ////////////////////////////////////////
////////      UPDATE ////////////////////////////////////////
////////      UPDATE ////////////////////////////////////////
////////      UPDATE ////////////////////////////////////////
////////      UPDATE ////////////////////////////////////////
////////      UPDATE ////////////////////////////////////////
////////      UPDATE ////////////////////////////////////////

function updatePerson(pId) {
  let person = findPersonByPId(pId);
  person.firstName = document.getElementById(`firstName-${pId}`).value;
  person.lastName = document.getElementById(`lastName-${pId}`).value;
  person.nickName = document.getElementById(`nickName-${pId}`).value;
  person.occupation = document.getElementById(`occupation-${pId}`).value;
  person.business = document.getElementById(`business-${pId}`).value;
  person.birthday = document.getElementById(`birthday-${pId}`).value;
  returnToPerson(pId);
}

////////      DELETE ////////////////////////////////////////
////////      DELETE ////////////////////////////////////////
////////      DELETE ////////////////////////////////////////
////////      DELETE ////////////////////////////////////////
////////      DELETE ////////////////////////////////////////
////////      DELETE ////////////////////////////////////////
////////      DELETE ////////////////////////////////////////

const tabs = document.querySelectorAll(".tabs-container .tab");
const contents = document.querySelectorAll(".tabs-container .content");
const removeActiveClass = () => {
  tabs.forEach((t) => t.classList.remove("active"));
  contents.forEach((c) => c.classList.remove("active"));
};

const setActiveContentOnLoad = () => {
  tabs.forEach((tab, index) => {
    if (tab.classList.contains("active")) {
      contents[index].classList.add("active");
    }
  });
};

tabs.forEach((tab, index) => {
  tab.addEventListener("click", () => {
    removeActiveClass();
    contents[index].classList.add("active");
    tab.classList.add("active");
  });
});

function setTabContent(memberType) {
  switch (memberType) {
    case 1:
      return "person.svg"; // primary
    case 2:
      return "person2.svg"; // secondary
    default:
      return "dependant.svg"; // dependant
  }
}

function dropDownYear(officer, startYear) {
  let nextThreeYears = [String(startYear), String(startYear + 1), String(startYear + 2), "None"];
  let dropdown = new CustomDropdown(
    nextThreeYears,
    `board-end-year-${officer.officerId}`,
    `board-${officer.officerId}`
  );
  return dropdown;
}

function calculateAge(birthdate) {
  const birthDate = new Date(birthdate);
  const today = new Date();
  let age = today.getFullYear() - birthDate.getFullYear();
  // Adjust the age if the person hasn't had their birthday yet this year
  const monthDiff = today.getMonth() - birthDate.getMonth();
  if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
    age--;
  }
  return age;
}

function findPersonByPId(pId) {
  // Assuming membershipData.personDTOS is an array of person objects
  return membershipData.personDTOS.find((person) => person.pId === pId);
}

function findPhoneByPhoneId(phoneId) {
  phoneId = Number(phoneId);
  for (let person of membershipData.personDTOS) {
    const foundPhone = person.phones.find((phone) => phone.phoneId === phoneId);
    if (foundPhone) {
      return foundPhone;
    }
  }
  return null;
}

function validatePhoneNumber(phoneNumber) {
  let regex = /^(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]?\d{3}[\s.-]?\d{4}$/; // Modify as needed
  return regex.test(phoneNumber);
}

function validateEmail(email) {
  const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  return emailRegex.test(email);
}

function validateYear(inputString) {
  const year = Number(inputString);
  const currentYear = new Date().getFullYear();

  if (!isNaN(year) && year >= 1970 && year <= currentYear) {
    return { valid: true, year: year };
  } else {
    return { valid: false, year: null };
  }
}

function formatNumber(phoneNumber) {
  // Extract digits from the phone number
  let digits = phoneNumber.replace(/\D/g, "");
  // Format and return the number as xxx-xxx-xxxx
  return digits.replace(/(\d{3})(\d{3})(\d{4})/, "$1-$2-$3");
}

function deleteItemById(itemId, items, idProperty) {
  const index = items.findIndex((item) => item[idProperty] === itemId);
  if (index !== -1) {
    items.splice(index, 1);
  }
}

function getPositionByIdentifier(identifier, boardPositions) {
  const positionObj = boardPositions.find((position) => position.identifier === identifier);
  return positionObj ? positionObj.position : null;
}

function extractPositions(boardPositions) {
  return boardPositions.map((positionObj) => positionObj.position);
}

function createBidirectionalMap(boardPositions) {
  let positionToIdentifier = {};
  let identifierToPosition = {};

  boardPositions.forEach((item) => {
    positionToIdentifier[item.position] = item.identifier;
    identifierToPosition[item.identifier] = item.position;
  });

  return {
    getPositionByIdentifier: function (identifier) {
      return identifierToPosition[identifier];
    },
    getIdentifierByPosition: function (position) {
      return positionToIdentifier[position];
    },
  };
}

function getRandomNumber(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

function setEmailChecks(person) {
  person.email.forEach((email) => {
    document.getElementById("check-primary_use-" + email.emailId).checked = email.primaryUse;
    document.getElementById("check-email_listed-" + email.emailId).checked = email.emailListed;
  });
}

function getPhoneMapping(type) {
  return phoneTypeMappings[type] || null;
}

function returnToPerson(pId) {
  let person = findPersonByPId(pId);
  const content = document.getElementById("content-" + pId);
  addTo(content, person);
}

function addHeader(headerSize, title, onClickFunctionName, pId, image) {
  const content = document.createElement("div");
  content.classList.add("header");
  content.appendChild(createTitle(headerSize, title));
  content.appendChild(createEditLinkWithImage(onClickFunctionName, pId, image));
  return content;
}

function createTitle(headerSize, titleText) {
  let titleElement = document.createElement(`h${headerSize}`);
  titleElement.textContent = titleText;
  return titleElement;
}

function addLineDiv(className, innerContent) {
  const content = document.createElement("div");
  content.classList.add(className);
  const paragraph = document.createElement("p");
  content.appendChild(paragraph);
  paragraph.textContent = innerContent;
  return content;
}

function createEditDiv(pId, errorId) {
  const content = document.getElementById("content-" + pId);
  content.classList.add("edit-div");
  content.innerHTML = `<div id="${errorId}" class="error-div"></div>`;
  return content;
}

function insertNewObject(container, newElement) {
  if (container.children.length >= 2) {
    const thirdChild = container.children[2];
    container.insertBefore(newElement, thirdChild);
  } else {
    container.appendChild(newElement);
  }
  let infoDiv = newElement.querySelector("div.error-top");
  infoDiv.innerHTML = "<p class='blue-alert'>New</p>";
  infoDiv.style.display = "block"; // Show error message
  newElement.style.border = "2px solid blue";
}

// Call this function when the page loads
setActiveContentOnLoad();
