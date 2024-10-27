class TitledPane extends HTMLElement {
    constructor(title, optionClass = "membership-section") {
        super(); // Call the HTMLElement constructor

        // Add the titled-pane class to the custom element
        this.classList.add("titled-pane");
        this.classList.add(optionClass);

        // Title element
        this.titleElement = document.createElement("div");
        this.titleElement.classList.add("title");
        this.titleElement.textContent = title;

        // Content element (initially empty)
        this.contentElement = document.createElement("div");
        this.contentElement.classList.add("content");

        // Append title and content to the main element (this)
        this.appendChild(this.titleElement);
        this.appendChild(this.contentElement);
    }

    // Method to set or update content
    setContent(contentNode) {
        // Clear any existing content
        this.contentElement.innerHTML = "";
        // Append the new content node
        this.contentElement.appendChild(contentNode);
    }

    setContentId(contentId) {
        this.contentElement.id = contentId;
    }

    addClass(paneClass) {
        this.contentElement.classList.add(paneClass);
    }
}

// Register the custom element with the browser
customElements.define("titled-pane", TitledPane);


