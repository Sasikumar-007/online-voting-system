import React, { useState } from 'react';

const VoterForm = ({ onVote }) => {
    const [voterId, setVoterId] = useState('');
    const [selectedCandidate, setSelectedCandidate] = useState('');

    const handleVote = (e) => {
        e.preventDefault();
        if (voterId && selectedCandidate) {
            onVote(voterId, selectedCandidate);
            setVoterId('');
            setSelectedCandidate('');
        } else {
            alert('Please enter your voter ID and select a candidate.');
        }
    };

    return (
        <form onSubmit={handleVote}>
            <h2>Voter Registration</h2>
            <div>
                <label htmlFor="voterId">Voter ID:</label>
                <input
                    type="text"
                    id="voterId"
                    value={voterId}
                    onChange={(e) => setVoterId(e.target.value)}
                    required
                />
            </div>
            <div>
                <label htmlFor="candidate">Select Candidate:</label>
                <select
                    id="candidate"
                    value={selectedCandidate}
                    onChange={(e) => setSelectedCandidate(e.target.value)}
                    required
                >
                    <option value="">--Select a Candidate--</option>
                    {/* Candidates will be populated here */}
                </select>
            </div>
            <button type="submit">Vote</button>
        </form>
    );
};

export default VoterForm;