class HorizontalBox {
    constructor(boxId) {
        // Create the main container
        this.container = document.createElement("div");
        this.container.classList.add("horizontal-container");
        this.container.id = boxId;

        // Append to the document or any specified parent
        document.body.appendChild(this.container);
    }

    // Method to add a note to the container
    add(noteContent) {
        if (noteContent instanceof HTMLElement) {
            this.container.appendChild(noteContent);
        } else {
            console.error("Invalid element: Only HTML elements can be added to HorizontalBox.");
        }
    }

    getElement() {
        return this.container;
    }
}