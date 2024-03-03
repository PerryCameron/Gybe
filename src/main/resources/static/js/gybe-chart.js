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
  return { regular, years, family, lakeAssociate, lifeMember, social };
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

const { regular, years, family, lakeAssociate, lifeMember, social } = extractRegular(stats);

const ctx = document.getElementById("membership-chart");

const stackedBar = new Chart(ctx, {
  type: "bar",
  data: {
    labels: years,
    datasets: [
      {
        label: "Family",
        data: family,
        borderWidth: 1,
        backgroundColor: "#2A2B2E",
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

const age = document.getElementById("age-chart");

const doughnutChart = new Chart(age, {
  type: "pie",
  data: {
    labels: ["0-10", "11-20", "21-30", "31-40", "41-50", "51-60", "61-70", "71-80", "81-90", "Not Reported"],
    datasets: [
      {
        label: "My First Dataset",
        data: agesArray,
        backgroundColor: [
          "#4B9CD3",
          "#C62828",
          "#FFCD56",
          "#7A306C",
          "#9966FF",
          "#FF9F40",
          "#2A2B2E",
          "#EC1A1A",
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
        text: "Age Distribution 2024", // Add your description here
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
