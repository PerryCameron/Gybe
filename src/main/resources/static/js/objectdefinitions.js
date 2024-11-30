/**
 * @typedef {Object} membershipJson
 * @property {number} mid
 * @property {number} fiscalYear
 * @property {number} msId
 * @property {number} membershipId
 * @property {number} renew
 * @property {string} memType
 * @property {number} selected
 * @property {number} lateRenew
 * @property {number} pId
 * @property {string} joinDate
 * @property {string} address
 * @property {string} city
 * @property {string} state
 * @property {string} zip
 * @property {slip} slip
 * @property {Array.<person>} people
 * @property {Array.<boat>} boats
 */

/**
 * @typedef {Object} slip
 * @property {number} slipId
 * @property {string} slipNum
 * @property {string|null} subleasedTo
 * @property {string|null} altText
 */

/**
 * @typedef {Object} person
 * @property {number} pId
 * @property {number} memberType
 * @property {string} firstName
 * @property {string} lastName
 * @property {string} birthday
 * @property {string} occupation
 * @property {string|null} business
 * @property {boolean} active
 * @property {string|null} nickName
 * @property {number|null} oldMsid
 * @property {Array.<email>} emails
 * @property {Array.<phone>} phones
 * @property {Array.<award>} awards
 * @property {Array.<officer>} officers
 */

/**
 * @typedef {Object} email
 * @property {number} emailId
 * @property {number} primaryUse
 * @property {string} email
 * @property {number} emailListed
 */

/**
 * @typedef {Object} phone
 * @property {number} phoneId
 * @property {string} phone
 * @property {string} phoneType
 * @property {number} phoneListed
 */

/**
 * @typedef {Object} award
 * @property {number} awardId
 * @property {string} awardYear
 * @property {string} awardType
 */

/**
 * @typedef {Object} officer
 * @property {number} officerId
 * @property {number} boardYear
 * @property {string} officerType
 * @property {number} fiscalYear
 */

/**
 * @typedef {Object} boat
 * @property {number} boatId
 * @property {string|null} manufacturer
 * @property {string|null} manufactureYear
 * @property {string|null} registrationNum
 * @property {string|null} model
 * @property {string|null} boatName
 * @property {string|null} sailNumber
 * @property {number} hasTrailer
 * @property {string|null} length
 * @property {string|null} weight
 * @property {string|null} keel
 * @property {string|null} phrf
 * @property {string|null} draft
 * @property {string|null} beam
 * @property {string|null} lwl
 * @property {number} aux
 */

/**
 * @typedef {Object} Membership
 * @property {number} msId
 * @property {number} pId
 * @property {string} slip
 * @property {string} fullName
 * @property {string} address
 * @property {string} city
 * @property {string} state
 * @property {string} zip
 * @property {string} memType
 * @property {string} joinDate
 * @property {number} membershipId
 * @property {number} selectedYear
 */

/**
 * @param {Object} data
 * @param {Membership[]} data.roster.membershipListDTOS
 */

/**
 * @typedef {Object} invoice
 * @property {number} invoiceId - The unique identifier for the invoice.
 * @property {number} msId - The membership ID associated with the invoice.
 * @property {number|null} fiscalYear - The fiscal year of the invoice.
 * @property {number|null} paid - The amount paid on the invoice.
 * @property {number|null} total - The total amount of the invoice.
 * @property {number|null} credit - The credit applied to the invoice.
 * @property {number|null} balance - The remaining balance on the invoice.
 * @property {number|null} batch - The batch number for the invoice.
 * @property {boolean|null} committed - Whether the invoice is committed (1 for true, 0 for false).
 * @property {boolean|null} closed - Whether the invoice is closed (1 for true, 0 for false).
 * @property {boolean|null} supplemental - Whether the invoice is supplemental (1 for true, 0 for false).
 * @property {number} maxCredit - The maximum credit available on the invoice.
 */

/**
 * @typedef {Object} MembershipId
 * @property {number} mid - The unique identifier for the membership.
 * @property {number} fiscalYear - The fiscal year associated with this membership.
 * @property {number|null} membershipId - The membership ID.
 * @property {boolean} renew - Indicates if the membership was renewed (1 for true, 0 for false).
 * @property {string|null} memType - The type of membership (e.g., "FM" for full member).
 * @property {boolean} selected - Indicates if this membership is selected (1 for true, 0 for false).
 * @property {boolean} lateRenew - Indicates if the membership was renewed late (1 for true, 0 for false).
 */

/**
 * Represents a single stat entry for a specific fiscal year.
 * @typedef {Object} Stat
 * @property {number} statId - Unique identifier for the stat.
 * @property {number} fiscalYear - The fiscal year of the stat.
 * @property {number} activeMemberships - Number of active memberships.
 * @property {number} nonRenewMemberships - Number of memberships not renewed.
 * @property {number} returnMemberships - Number of returning memberships.
 * @property {number} newMemberships - Number of new memberships.
 * @property {number} secondaryMembers - Number of secondary members.
 * @property {number} dependants - Number of dependants.
 * @property {number} numberOfBoats - Number of boats.
 * @property {number} family - Number of family memberships.
 * @property {number} regular - Number of regular memberships.
 * @property {number} social - Number of social memberships.
 * @property {number} lakeAssociates - Number of lake associate memberships.
 * @property {number} lifeMembers - Number of life members.
 * @property {number} raceFellows - Number of race fellows.
 * @property {number} student - Number of student memberships.
 * @property {number} deposits - Number of deposits.
 * @property {number} initiation - Number of initiation fees.
 */

/**
 * Represents the ages breakdown of members.
 * @typedef {Object} Ages
 * @property {number} zeroTen - Members aged 0–10.
 * @property {number} elevenTwenty - Members aged 11–20.
 * @property {number} twentyOneThirty - Members aged 21–30.
 * @property {number} thirtyOneForty - Members aged 31–40.
 * @property {number} fortyOneFifty - Members aged 41–50.
 * @property {number} fiftyOneSixty - Members aged 51–60.
 * @property {number} sixtyOneSeventy - Members aged 61–70.
 * @property {number} seventyOneEighty - Members aged 71–80.
 * @property {number} eightyOneNinety - Members aged 81–90.
 * @property {number} notReported - Members with unreported ages.
 * @property {number} total - Total number of members.
 */

/**
 * Represents the entire data structure for stats and ages.
 * @typedef {Object} Data
 * @property {Stat[]} stats - Array of stat entries for different fiscal years.
 * @property {Ages} ages - Breakdown of member ages.
 */