// src/app.js

const voters = {};
const candidates = [
    { id: 1, name: "Candidate A", symbol: "symbolA.png", votes: 0 },
    { id: 2, name: "Candidate B", symbol: "symbolB.png", votes: 0 },
    { id: 3, name: "Candidate C", symbol: "symbolC.png", votes: 0 },
];

function registerVoter(voterId) {
    if (voters[voterId]) {
        alert("You have already voted.");
        return false;
    }
    voters[voterId] = true;
    return true;
}

function castVote(voterId, candidateId) {
    if (!registerVoter(voterId)) return;

    const candidate = candidates.find(c => c.id === candidateId);
    if (candidate) {
        candidate.votes++;
        displayVoteConfirmation(candidate);
    }
}

function displayVoteConfirmation(candidate) {
    const confirmationMessage = `You have voted for ${candidate.name} with symbol: ${candidate.symbol}`;
    alert(confirmationMessage);
}

function getVoteResults() {
    return candidates.map(candidate => ({
        name: candidate.name,
        votes: candidate.votes,
    }));
}

function downloadResults() {
    const results = getVoteResults();
    const csvContent = "data:text/csv;charset=utf-8," 
        + results.map(e => `${e.name},${e.votes}`).join("\n");

    const encodedUri = encodeURI(csvContent);
    const link = document.createElement("a");
    link.setAttribute("href", encodedUri);
    link.setAttribute("download", "vote_results.csv");
    document.body.appendChild(link);
    link.click();
}

document.getElementById("voteButton").addEventListener("click", () => {
    const voterId = document.getElementById("voterId").value;
    const candidateId = parseInt(document.querySelector('input[name="candidate"]:checked').value);
    castVote(voterId, candidateId);
});

document.getElementById("downloadResultsButton").addEventListener("click", downloadResults);