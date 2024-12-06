const personFields = {
    firstName: "First Name",
    lastName: "Last Name",
    nickName: "Nick Name",
    occupation: "Occupation",
    business: "Business",
    birthday: "Birthday",
};

// Retrieve the CSRF token from the meta tags
const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
const csrfHeaderName = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");

