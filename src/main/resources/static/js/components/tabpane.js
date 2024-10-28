class TabPane {
    constructor(container, layout = "horizontal") {
        // Store the container where the tab pane will be created
        this.container = container;

        // Initialize the tab container and content container elements
        this.tabsContainer = document.createElement("div");
        this.tabsContainer.classList.add("tab-buttons");
        // Save the layout as an instance property
        this.layout = layout;

        this.contentContainer = document.createElement("div");
        this.contentContainer.classList.add("tab-content");

        // Apply vertical class if specified
        if (layout === "vertical") {
            this.container.style.display = "flex"; // Parent container is a flex row
            this.tabsContainer.style.display = "flex";
            this.container.style.flexDirection = "row";
            this.tabsContainer.style.flexDirection = "column";
        }
        this.container.appendChild(this.tabsContainer);
        this.container.appendChild(this.contentContainer);
        // Store references to tabs and contents
        this.tabs = [];
        this.contents = {};
    }

    tabExists(id) {
        // Use querySelector to find a button with the matching data-tab attribute
        const tabButton = this.tabsContainer.querySelector(`button[data-tab="${id}"]`);
        // Return true if found, false otherwise
        return tabButton !== null;
    }

    // Method to add a new tab and corresponding content
    addTab(tabId, tabLabel, content, closable) {
        // Create and append the new tab button
        const tabButton = document.createElement("button");
        tabButton.setAttribute("data-tab", tabId);
        this.tabLabel = tabLabel;
        tabButton.textContent = this.tabLabel;
        tabButton.addEventListener("click", () => this.switchToTab(tabId));
        tabButton.id = `tab-button-${tabId}`;
        this.tabsContainer.appendChild(tabButton);

        // Create and append the content for the new tab
        const tabContent = document.createElement("div");
        tabContent.setAttribute("data-tab-content", tabId);
        tabContent.appendChild(content);
        tabContent.style.display = "none"; // Hide content initially
        tabContent.classList.add("attributes-tab-content");
        tabContent.id = `tab-content-${tabId}`;
        this.contentContainer.appendChild(tabContent);

        // Store the tab and content elements
        this.tabs.push(tabButton);
        this.contents[tabId] = tabContent;

        if (this.layout === "vertical") {
            tabButton.classList.add("vertical");
        } else {
            tabButton.classList.add("horizontal");
        }

            if (closable) {
                const closeButton = document.createElement("span");
                closeButton.innerHTML = "&times;";
                closeButton.classList.add("close-tab");
                closeButton.onclick = (event) => {
                    event.stopPropagation(); // Prevent switching tabs on close
                    this.closeTab(tabButton, tabContent); // Correct reference to closeTab
                };
                tabButton.appendChild(closeButton);
            }

        // Activate the first tab by default if this is the first tab added
        this.switchToTab(tabId);
    }

    closeTab(tabButton, tabContent) {
        console.log("Closing tab: " + tabButton.getAttribute("data-tab"));

        // Remove the tab button
        const tabIndex = this.tabs.indexOf(tabButton);
        if (tabIndex > -1) {
            this.tabs.splice(tabIndex, 1);
            tabButton.remove();
        }

        // Remove the tab content
        delete this.contents[tabButton.getAttribute("data-tab")];
        tabContent.remove();

        // Activate the next available tab if any remain
        if (this.tabs.length > 0) {
            const nextTabId = this.tabs[0].getAttribute("data-tab");
            this.switchToTab(nextTabId);
        }
    }

    // Method to switch to a specific tab
    switchToTab(tabId) {
        // Deactivate all tabs and hide all content
        this.tabs.forEach((tab) => tab.classList.remove("active"));
        Object.values(this.contents).forEach((content) => (content.style.display = "none"));

        // Activate the selected tab and show its content
        const activeTab = this.tabs.find((tab) => tab.getAttribute("data-tab") == tabId);
        const activeContent = this.contents[tabId];
        if (activeTab && activeContent) {
            activeTab.classList.add("active");
            activeContent.style.display = "flex";
        }
    }
}   // show me css which would make tabs take 100% of space
