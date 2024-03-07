let main = document.getElementById("main");

let canvas = document.getElementById("slipChart");
const ctx = canvas.getContext("2d");

// let dDock = slipStructure.filter((slip) => slip.dock === "D");

dockPlacement = {
  a: { x: 430, y: 40, dock: "A" },
  b: { x: 720, y: 40, dock: "B" },
  c: { x: 1010, y: 40, dock: "C" },
  d: { x: 140, y: 40, dock: "D" },
  f: { x: 140, y: 610, dock: "F" },
};

addDock(dockPlacement["d"], setDock("D"));
addDock(dockPlacement["a"], setDock("A"));
addDock(dockPlacement["b"], setDock("B"));
addDock(dockPlacement["c"], setDock("C"));
addDock(dockPlacement["f"], setDock("F"));

// dock is dockPlacement, this Dock is slipStructure
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
  // left side of dock
  ctx.beginPath();
  ctx.moveTo(x, y);
  if (isRightOnlySection(section)) ctx.lineTo(x, y - 50);
  else {
    ctx.lineTo(x, y - 20);
    ctx.lineTo(x - 110, y - 20);
    ctx.lineTo(x - 110, y - 50);
    ctx.lineTo(x, y - 50);
  }
  // right side of dock
  ctx.moveTo(x + 40, y - 50);
  ctx.lineTo(x + 150, y - 50);
  ctx.lineTo(x + 150, y - 20);
  ctx.lineTo(x + 40, y - 20);
  ctx.lineTo(x + 40, y);
  // the fill
  ctx.fillStyle = "#edeff2";
  if (isRightOnlySection(section)) ctx.fillRect(x, y - 50, 150, 30);
  else ctx.fillRect(x - 110, y - 50, 260, 30);
  ctx.fillRect(x, y - 20, 40, 20);
  // Set the stroke style and stroke the lines
  ctx.strokeStyle = "black";
  // Add the dock numbers and people
  ctx.fillStyle = "black";
  ctx.font = "11px Arial";
  if (section["slip4"] != "none") fillMembershipDetails(section["slip4"], x - 20, y - 39, true);
  if (section["slip3"] != "none") fillMembershipDetails(section["slip3"], x - 20, y - 24, true);
  if (section["slip2"] != "none") fillMembershipDetails(section["slip2"], x + 45, y - 39, false);
  if (section["slip1"] != "none") fillMembershipDetails(section["slip1"], x + 45, y - 24, false);
  ctx.stroke();
}

function fillMembershipDetails(slip, x, y, isLeft) {
  let owner = getSlipOwnerBySlipNumber(slip);
  if (owner) {
    // this is a club slip
    if (owner.ownerFirstName === null) {
      if (owner.altText != null) ctx.fillText(owner.slipNumber + " " + owner.altText, x, y);
      // this is a subleaser
    } else if (owner.subleaserFirstName != null) {
      ctx.fillStyle = "#075be3";
      let slipInfo = setDockString(owner, isLeft, true);
      let width = calculateTextOffset(isLeft, slipInfo);
      ctx.fillText(slipInfo, x - width, y);
      ctx.fillStyle = "black";
      // this is an owner
    } else {
      let slipInfo = setDockString(owner, isLeft, false);
      let width = calculateTextOffset(isLeft, slipInfo);
      ctx.fillText(slipInfo, x - width, y);
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

function thisIsASubleasedSlip(owner) {
  return owner.ownerFirstName === null;
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

function countDockSections(dockLetter) {
  const docks = slipStructure.filter((slip) => slip.dock === dockLetter);
  return docks.length;
}

function setDock(dock) {
  return slipStructure.filter((slip) => slip.dock === dock);
}

function getSlipOwnerBySlipNumber(slipNumber) {
  return slipOwners.find((owner) => owner.slipNumber === slipNumber);
}
