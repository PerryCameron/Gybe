function buildDock(data) {
    const canvas = document.getElementById("slipChart");
    const ctx = canvas.getContext("2d");
    const slipPopup = [];
    const slipOwners = data.slipOwners;
    const slipStructure = data.slipStructure;

    const dockPlacement = {
        a: { x: 140, y: 40, dock: "A" },
        b: { x: 430, y: 40, dock: "B" },
        c: { x: 720, y: 40, dock: "C" },
        d: { x: 1010, y: 40, dock: "D" },
        f: { x: 140, y: 610, dock: "F" },
    };

    function addDock(dockPosition, thisDock) {
        dockTopCap(dockPosition["x"], dockPosition["y"]);
        thisDock
            .sort((a, b) => a.dockSection - b.dockSection)
            .forEach((section) => {
                dockPosition["y"] += 50;
                dockSection(dockPosition["x"], dockPosition["y"], section);
            });
        dockBottomCap(dockPosition);
    }

    function isRightOnlySection(section) {
        return section["slip3"] === "none" && section["slip4"] === "none";
    }

    function dockSection(x, y, section) {
        ctx.beginPath();
        ctx.moveTo(x, y);
        if (isRightOnlySection(section)) ctx.lineTo(x, y - 50);
        else {
            ctx.lineTo(x, y - 20);
            ctx.lineTo(x - 110, y - 20);
            ctx.lineTo(x - 110, y - 50);
            ctx.lineTo(x, y - 50);
        }
        ctx.moveTo(x + 40, y - 50);
        ctx.lineTo(x + 150, y - 50);
        ctx.lineTo(x + 150, y - 20);
        ctx.lineTo(x + 40, y - 20);
        ctx.lineTo(x + 40, y);
        ctx.fillStyle = "#edeff2";
        if (isRightOnlySection(section)) ctx.fillRect(x, y - 50, 150, 30);
        else ctx.fillRect(x - 110, y - 50, 260, 30);
        ctx.fillRect(x, y - 20, 40, 20);
        ctx.strokeStyle = "black";
        ctx.fillStyle = "black";
        ctx.font = "11px Arial";
        if (section["slip4"] != "none") fillMembershipDetails(section["slip4"], x - 20, y - 39, true);
        if (section["slip3"] != "none") fillMembershipDetails(section["slip3"], x - 20, y - 24, true);
        if (section["slip2"] != "none") fillMembershipDetails(section["slip2"], x + 45, y - 39, false);
        if (section["slip1"] != "none") fillMembershipDetails(section["slip1"], x + 45, y - 24, false);
        ctx.stroke();
    }

    function fillMembershipDetails(slip, x, y, isLeft) {
        let textBounds;
        let slipDetails;
        let owner = getSlipOwnerBySlipNumber(slip);
        if (owner) {
            if (owner.ownerFirstName === null) {
                if (owner.altText != null) ctx.fillText(owner.slipNumber + " " + owner.altText, x, y);
            } else if (owner.subleaserFirstName != null) {
                ctx.fillStyle = "#075be3";
                let slipInfo = setDockString(owner, isLeft, true);
                let width = calculateTextOffset(isLeft, slipInfo);
                ctx.fillText(slipInfo, x - width, y);
                textBounds = calculateTextBounds(slipInfo, x - width, y);
                ctx.fillStyle = "black";
            } else {
                let slipInfo = setDockString(owner, isLeft, false);
                let width = calculateTextOffset(isLeft, slipInfo);
                textBounds = calculateTextBounds(slipInfo, x - width, y);
                ctx.fillText(slipInfo, x - width, y);
            }
            if (textBounds) {
                slipDetails = {
                    slipNumber: slip,
                    textBounds: textBounds,
                };
                slipPopup.push(slipDetails);
            }
        }
    }

    function calculateTextOffset(isLeft, slipInfo) {
        let width = 0;
        if (isLeft) {
            let metrics = ctx.measureText(slipInfo);
            width = metrics.width;
            width -= 15;
        }
        return width;
    }

    function setDockString(owner, isLeft, isSubleaser) {
        if (isSubleaser) {
            if (isLeft)
                return owner.subleaserLastName + " " + abbreviateFirstName(owner.subleaserFirstName) + " " + owner.slipNumber;
            else return owner.slipNumber + " " + owner.subleaserLastName + " " + abbreviateFirstName(owner.subleaserFirstName);
        } else if (isLeft)
            return owner.ownerLastName + " " + abbreviateFirstName(owner.ownerFirstName) + " " + owner.slipNumber;
        else return owner.slipNumber + " " + owner.ownerLastName + " " + abbreviateFirstName(owner.ownerFirstName);
    }

    function abbreviateFirstName(name) {
        if (name && name.length > 0) {
            return name[0] + ".";
        }
        return "";
    }

    function dockTopCap(x, y) {
        ctx.beginPath();
        ctx.moveTo(x, y);
        ctx.lineTo(x, y - 20);
        ctx.lineTo(x + 40, y - 20);
        ctx.lineTo(x + 40, y);
        ctx.fillStyle = "#edeff2";
        ctx.fillRect(x, y - 20, 40, 20);
        ctx.strokeStyle = "black";
        ctx.stroke();
    }

    function dockBottomCap(dock) {
        let x = dock["x"];
        let y = dock["y"];
        ctx.beginPath();
        ctx.moveTo(x, y);
        ctx.lineTo(x, y + 25);
        ctx.lineTo(x + 40, y + 25);
        ctx.lineTo(x + 40, y);
        ctx.fillStyle = "#edeff2";
        ctx.fillRect(x, y, 40, 25);
        ctx.strokeStyle = "black";
        ctx.font = "16px Arial";
        ctx.fillStyle = "black";
        ctx.fillText(dock["dock"], x + 13, y + 10);
        ctx.stroke();
    }

    function calculateTextBounds(text, x, y) {
        return { x: x, y: y, width: ctx.measureText(text).width, height: parseInt(ctx.font) };
    }

    for (const key in dockPlacement) {
        if (dockPlacement.hasOwnProperty(key)) {
            addDock(dockPlacement[key], setDock(dockPlacement[key].dock));
        }
    }
    addLegend();

    function addLegend() {
        let currentDate = new Date();
        let monthNames = [
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December",
        ];
        let month = monthNames[currentDate.getMonth()];
        let day = currentDate.getDate();
        let year = currentDate.getFullYear();
        let dateString = `${month} ${day}, ${year}`; // Construct the date string

        ctx.font = "24px Arial";
        ctx.fillStyle = "black";
        ctx.fillText("Dock Assignments", 550, 750);
        ctx.fillStyle = "grey"; // Set the color for the date
        ctx.font = "12px Arial";
        ctx.fillText("(Live Update Generated: " + `${dateString}` + ")", 545, 770);
    }

    function setDock(dock) {
        return slipStructure.filter((slip) => slip.dock === dock);
    }

    function getSlipOwnerBySlipNumber(slipNumber) {
        return slipOwners.find((owner) => owner.slipNumber === slipNumber);
    }

    canvas.addEventListener("mousemove", function (event) {
        let rect = canvas.getBoundingClientRect(); // Get the bounding rectangle of the canvas
        let mouseX = event.clientX - rect.left; // Adjust mouse X position relative to the canvas
        let mouseY = event.clientY - rect.top; // Adjust mouse Y position relative to the canvas
        let isOverSlip = false;

        for (let i = 0; i < slipPopup.length; i++) {
            let slip = slipPopup[i];
            let bounds = slip.textBounds;

            // Adjust bounds if necessary
            let adjustedBounds = {
                x: bounds.x,
                y: bounds.y - bounds.height, // Adjust for baseline to top-left corner
                width: bounds.width,
                height: bounds.height,
            };

            if (
                mouseX >= adjustedBounds.x &&
                mouseX <= adjustedBounds.x + adjustedBounds.width &&
                mouseY >= adjustedBounds.y &&
                mouseY <= adjustedBounds.y + adjustedBounds.height
            ) {
                const tooltip = document.getElementById("tooltip");
                tooltip.style.display = "block";
                tooltip.style.left = event.clientX + 10 + "px"; // Add offset to prevent tooltip from blocking the mouse pointer
                tooltip.style.top = event.clientY + 10 + "px";
                tooltip.innerHTML = getTextContent(slip.slipNumber);
                isOverSlip = true;
                break;
            }
        }

        if (!isOverSlip) {
            const tooltip = document.getElementById("tooltip");
            tooltip.style.display = "none";
        }
    });

    function getTextContent(slipNumber) {
        let owner = getSlipOwnerBySlipNumber(slipNumber);
        let popText =
            "<strong>Slip:</strong> " +
            slipNumber +
            "<BR><BR>" +
            "<strong>Owner:</strong> <BR>" +
            owner.ownerId +
            "  " +
            owner.ownerFirstName +
            " " +
            owner.ownerLastName;
        if (owner.subleaserFirstName != null)
            popText +=
                "<BR><BR><strong>Subleased by:</strong><BR>" +
                owner.subleaserId +
                " " +
                owner.subleaserFirstName +
                " " +
                owner.subleaserLastName;

        return popText;
    }
}
