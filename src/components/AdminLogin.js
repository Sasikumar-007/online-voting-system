import React, { useState } from 'react';

const AdminLogin = ({ onLogin }) => {
    const [adminId, setAdminId] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleLogin = (e) => {
        e.preventDefault();
        if (adminId === 'Ad21' && password === 'vote21') {
            onLogin(true);
            setError('');
        } else {
            setError('Invalid ID or Password');
        }
    };

    return (
        <div className="admin-login">
            <h2>Admin Login</h2>
            <form onSubmit={handleLogin}>
                <div>
                    <label htmlFor="adminId">Admin ID:</label>
                    <input
                        type="text"
                        id="adminId"
                        value={adminId}
                        onChange={(e) => setAdminId(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="password">Password:</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Login</button>
                {error && <p className="error">{error}</p>}
            </form>
        </div>
    );
};

export default AdminLogin;