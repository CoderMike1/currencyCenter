import './App.css';
import Dashboard from "./Components/Dashboard/Dashboard.jsx";
import { useEffect, useState } from "react";
import Login from "./Components/Login/Login.jsx";
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Register from "./Components/Register/Register.jsx";
import MenuBar from "./Components/MenuBar/MenuBar.jsx";
import Settings from "./Components/Settings/Settings.jsx";

function App() {
    const [token, setToken] = useState('');
    const [settingsON, setSettingsON] = useState(false);

    useEffect(() => {
        const savedToken = sessionStorage.getItem("token");
        if (savedToken) {
            setToken(savedToken);
        }
    }, []);

    const handleLogin = (token) => {
        sessionStorage.setItem("token", token);
        setToken(token);
    };

    const handleLogout = () => {
        sessionStorage.removeItem("token");
        setToken("");
    };

    return (
        <BrowserRouter>
            <MenuBar onLogout={handleLogout} isLoggedIn={token !== ""} settingsON={settingsON} setSettingsON={setSettingsON} />
            <Routes>
                <Route
                    path="/login"
                    element={token ? <Navigate to="/" /> : <Login onLogin={handleLogin} />}
                />
                <Route
                    path="/"
                    element={token ? <Dashboard  /> : <Navigate to="/login" />}
                />
                <Route
                    path="/register"
                    element={token ? <Navigate to="/"/> : <Register onLogin={handleLogin} />}
                />
                <Route
                    path="/settings"
                    element={token ? <Settings /> : <Navigate to="login"/>}
                    />
                <Route path="*" element={<Navigate to={token ? "/" : "/login"} />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
