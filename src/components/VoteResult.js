import React, { useState, useEffect } from 'react';
import { getVoteResults } from '../utils/auth';

const VoteResult = () => {
    const [results, setResults] = useState([]);
    const [isAdmin, setIsAdmin] = useState(false);

    useEffect(() => {
        const fetchResults = async () => {
            const data = await getVoteResults();
            setResults(data);
        };
        fetchResults();
    }, []);

    const handleAdminAccess = (id, password) => {
        if (id === 'Ad21' && password === 'vote21') {
            setIsAdmin(true);
        } else {
            alert('Invalid admin credentials');
        }
    };

    const downloadResults = () => {
        const blob = new Blob([JSON.stringify(results)], { type: 'application/json' });
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'vote_results.json';
        a.click();
        URL.revokeObjectURL(url);
    };

    return (
        <div>
            {isAdmin ? (
                <div>
                    <h2>Vote Results</h2>
                    <ul>
                        {results.map((candidate) => (
                            <li key={candidate.id}>
                                {candidate.name}: {candidate.votes} votes
                            </li>
                        ))}
                    </ul>
                    <button onClick={downloadResults}>Download Results</button>
                </div>
            ) : (
                <div>
                    <h2>Admin Login</h2>
                    <input type="text" placeholder="Admin ID" id="adminId" />
                    <input type="password" placeholder="Password" id="adminPassword" />
                    <button onClick={() => handleAdminAccess(document.getElementById('adminId').value, document.getElementById('adminPassword').value)}>Login</button>
                </div>
            )}
        </div>
    );
};

export default VoteResult;