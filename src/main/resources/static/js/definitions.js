const personFields = {
    firstName: "First Name",
    lastName: "Last Name",
    nickName: "Nick Name",
    occupation: "Occupation",
    business: "Business",
    birthday: "Birthday",
};

// editable-fields
// Initialize the edit flag


// global for rosters
let rosterTabPane;
// let lastLoadedScript = null;
// let globalRosterData;
// let lastSortedColumn = 'membershipId';
// let sortDirection = "asc";
// global for search
// let timeout = null;

// Retrieve the CSRF token from the meta tags
const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
const csrfHeaderName = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");

