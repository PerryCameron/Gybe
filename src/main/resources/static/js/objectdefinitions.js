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