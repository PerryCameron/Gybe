function buildGybeChart(data) {
  const stats = data.stats;
  const ages = data.ages;
  const mainDiv = document.getElementById("main-content");
  mainDiv.innerHTML = '';

  const controlDiv = document.getElementById('pageNavigation');
  controlDiv.innerHTML = "";

  // Create top chart container
  const topChartDiv = document.createElement('div');
  topChartDiv.className = 'charts top-chart';
  const membershipChartCanvas = document.createElement('canvas');
  topChartDiv.appendChild(membershipChartCanvas);
  mainDiv.appendChild(topChartDiv);

  // Create bottom charts container
  const bottomChartsDiv = document.createElement('div');
  bottomChartsDiv.className = 'charts bottom-charts';
  const ageChartCanvas = document.createElement('canvas');
  const membershipTypeChartCanvas = document.createElement('canvas');
  bottomChartsDiv.appendChild(ageChartCanvas);
  bottomChartsDiv.appendChild(membershipTypeChartCanvas);
  mainDiv.appendChild(bottomChartsDiv);


  const currentYear = new Date().getFullYear();
  const {regular, years, family, lakeAssociate, lifeMember, social} = extractRegular(stats);


  function extractRegular(stats) {
    // Sort the stats array by fiscalYear
    stats.sort((a, b) => a.fiscalYear - b.fiscalYear);
    // Extract the "regular" fields into a new array
    let regular = stats.map((stat) => stat.regular);
    let family = stats.map((stat) => stat.family);
    let lifeMember = stats.map((stat) => stat.lifeMembers);
    let lakeAssociate = stats.map((stat) => stat.lakeAssociates);
    let social = stats.map((stat) => stat.social);
    let years = stats.map((stat) => stat.fiscalYear);
    return {regular, years, family, lakeAssociate, lifeMember, social};
  }

  let agesArray = [
    ages.zeroTen,
    ages.elevenTwenty,
    ages.twentyOneThirty,
    ages.thirtyOneForty,
    ages.fortyOneFifty,
    ages.fiftyOneSixty,
    ages.sixtyOneSeventy,
    ages.seventyOneEighty,
    ages.eightyOneNinety,
    ages.notReported,
  ];

  function getCurrentStats() {
    // Get the current year
    const currentYear = new Date().getFullYear();
    // Find the stats for the current year
    const currentYearStats = stats.find((stat) => stat.fiscalYear === currentYear);
    if (currentYearStats) {
      // Extract the relevant properties
      const relevantStats = [
        currentYearStats.family,
        currentYearStats.regular,
        currentYearStats.social,
        currentYearStats.lakeAssociates,
        currentYearStats.lifeMembers,
      ];
      return relevantStats;
    }
    return []; // Return an empty array if no stats are found for the current year
  }

  const stackedBar = new Chart(membershipChartCanvas, {
    type: "bar",
    data: {
      labels: years,
      datasets: [
        {
          label: "Family",
          data: family,
          borderWidth: 1,
          backgroundColor: "#2A2B2E", //black
        },
        {
          label: "Regular",
          data: regular,
          borderWidth: 1,
          backgroundColor: "#4B9CD3", // Blue
        },
        {
          label: "Social",
          data: social,
          borderWidth: 1,
          backgroundColor: "#F9A825", // Yellow
        },
        {
          label: "Lake Associate",
          data: lakeAssociate,
          borderWidth: 1,
          backgroundColor: "#C62828", // Red
        },
        {
          label: "Life Membership",
          data: lifeMember,
          borderWidth: 1,
          backgroundColor: "#2E7D32", // Green
        },
      ],
    },
    options: {
      responsive: true,
      maintainAspectRatio: false, // Disable aspect ratio
      plugins: {
        title: {
          display: true,
          text: "Membership types by year", // Add your description here
          font: {
            size: 18, // You can adjust the font size and other properties
          },
          padding: {
            top: 10,
            bottom: 30, // Adjust padding as needed
          },
        },
      },
      scales: {
        x: {
          stacked: true,
        },
        y: {
          stacked: true,
        },
      },
    },
  });

  const doughnutChart = new Chart(ageChartCanvas, {
    type: "pie",
    data: {
      labels: ["0-10", "11-20", "21-30", "31-40", "41-50", "51-60", "61-70", "71-80", "81-90", "Not Reported"],
      datasets: [
        {
          label: "People",
          data: agesArray,
          backgroundColor: [
            "#4B9CD3",
            "#C62828",
            "rgb(255, 205, 86)",
            "#7A306C",
            "rgb(153, 102, 255)",
            "rgb(255, 159, 64)",
            "#2A2B2E",
            "rgb(255, 99, 132)",
            "#FFE45E",
            "#2E7D32",
          ],
          hoverOffset: 4,
        },
      ],
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        title: {
          display: true,
          text: `Age Distribution ${currentYear}`, // Add your description here
          font: {
            size: 18, // You can adjust the font size and other properties
          },
          padding: {
            top: 10,
            bottom: 30, // Adjust padding as needed
          },
        },
        legend: {
          display: true,
          position: "top",
        },
      },
    },
  });

  const membershipTypeChart = new Chart(membershipTypeChartCanvas, {
    type: "pie",
    data: {
      labels: ["Family", "Regular", "Social", "Lake Associate", "Life Membership"],
      datasets: [
        {
          label: "Memberships",
          data: getCurrentStats(),
          backgroundColor: ["#2A2B2E", "#4B9CD3", "#F9A825", "#C62828", "#2E7D32"],
          hoverOffset: 4,
        },
      ],
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        title: {
          display: true,
          text: `Membership Types ${currentYear}`, // Add your description here
          font: {
            size: 18, // You can adjust the font size and other properties
          },
          padding: {
            top: 10,
            bottom: 30, // Adjust padding as needed
          },
        },
        legend: {
          display: true,
          position: "top",
        },
      },
    },
  });
}