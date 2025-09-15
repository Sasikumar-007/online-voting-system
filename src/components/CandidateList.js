import React from 'react';

const CandidateList = ({ candidates, onVote }) => {
    return (
        <div>
            <h2>Candidate List</h2>
            <ul>
                {candidates.map((candidate) => (
                    <li key={candidate.id}>
                        <img src={candidate.symbol} alt={`${candidate.name} symbol`} />
                        <span>{candidate.name}</span>
                        <button onClick={() => onVote(candidate.id)}>Vote</button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default CandidateList;