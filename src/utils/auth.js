function validateVoterID(voterID) {
    // Assuming voter IDs are alphanumeric and of a specific length
    const voterIDPattern = /^[a-zA-Z0-9]{5,10}$/;
    return voterIDPattern.test(voterID);
}

function validateAdminCredentials(id, password) {
    const adminID = "Ad21";
    const adminPassword = "vote21";
    return id === adminID && password === adminPassword;
}

export { validateVoterID, validateAdminCredentials };