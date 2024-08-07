function buildDirectory() {
    const mainDiv = document.getElementById("main-content");
    const currentYear = new Date().getFullYear();
    mainDiv.innerHTML = '';

    // Create the button
    const button = document.createElement("button");
    button.textContent = "Make Directory";
    button.className = "center-button";

    // Append the button to the main content div
    mainDiv.appendChild(button);

    // Add event listener for button click
    button.addEventListener("click", function () {
        fetch("/api/directory-rest", {
            method: "GET",
        })
            .then(response => {
                if (response.ok) {
                    return response.blob();
                } else {
                    throw new Error("Network response was not ok.");
                }
            })
            .then(blob => {
                // Remove any previously created link elements
                const existingLink = document.getElementById('download-link');
                if (existingLink) {
                    existingLink.remove();
                }
                // Create a link element, use it to download the file
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.style.display = 'none';
                a.href = url;
                a.download = `${currentYear}_ECSC_directory.pdf`; // The name of the file with the current year
                a.id = 'download-link'; // Add an ID to the link element
                document.body.appendChild(a);
                a.click();
                window.URL.revokeObjectURL(url);
            })
            .catch(error => {
                console.error('Error fetching the email list:', error);
            });
    });
};
