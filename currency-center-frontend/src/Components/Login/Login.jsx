import { useState } from "react";
import { Link } from "react-router-dom";
const Login = ({ onLogin }) => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const loginProcess = async (e) => {
        e.preventDefault();

        const response = await fetch("http://localhost:8080/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ username, password })
        });

        if (response.status === 201) {
            const data = await response.json();
            const token = data.token;
            onLogin(token);
        } else {
            alert("Błędne dane logowania");
        }
    };

    return (
        <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
            <div className="card p-4 shadow" style={{ width: "100%", maxWidth: "400px" }}>
                <h3 className="text-center mb-4">Log in</h3>
                <form onSubmit={loginProcess}>
                    <div className="mb-3">
                        <label htmlFor="username" className="form-label">Username</label>
                        <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} className="form-control" id="username" placeholder="Enter username" required />
                    </div>

                    <div className="mb-3">
                        <label htmlFor="password" className="form-label">Password</label>
                        <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} className="form-control" id="password" placeholder="Enter password" required />
                    </div>

                    <button type="submit" className="btn btn-primary w-100">Log in</button>
                </form>
                <Link to="/register" className="btn btn-link mt-3 w-100 text-center">
                    Don't have an account? Create a new one
                </Link>
            </div>

        </div>
    );
};

export default Login;
