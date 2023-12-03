if (membershipData && membershipData.personDTOS) {
    membershipData.personDTOS.forEach(function(person) {
        console.log(person); // This logs each PersonDTO object
        // You can access properties of the person object here, e.g., person.name
    });
}
