function buildPublicity() {
    const mainDiv = document.getElementById("main-content");
    mainDiv.innerHTML = '';

    // Create the button
    const button = document.createElement("button");
    button.textContent = "Make Email List";
    button.className = "center-button";

    // Append the button to the main content div
    mainDiv.appendChild(button);

    // Add event listener for button click
    button.addEventListener("click", function () {
        fetch("/api/publicity", {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        })
            .then(response => {
                if (response.ok) {
                    return response.blob();
                } else {
                    throw new Error("Network response was not ok.");
                }
            })
            .then(blob => {
                // Create a link element, use it to download the file
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.style.display = 'none';
                a.href = url;
                a.download = 'Email_List.xlsx'; // The name of the file
                document.body.appendChild(a);
                a.click();
                window.URL.revokeObjectURL(url);
            })
            .catch(error => {
                console.error('Error fetching the email list:', error);
            });
    });
};



// this is publicity.js
// create a button that centers in the screen about 100px from the top
// call the button make email list

// on button click use AJAX to an endpoint like this

// @GetMapping("/publicity")
//     @PreAuthorize("hasRole('ROLE_PUBLICITY')")
// public String getDirectory(Model model) {
//     xlsService.createEmailList();  // this creates an email list in xlsx format and places it in the home dir
//     finalContrller.downloadFile("Email_List.xlsx"); // this takes the newly created file and sends it to the user to be downloaded
//     return "download/Email_List.xlsx";
// }