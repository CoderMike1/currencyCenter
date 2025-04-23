import {useEffect, useState} from "react";
import CurrencyBar from "./CurrencyBar/CurrencyBar.jsx";
import NewTransactionForm from "./NewTransactionForm/NewTransactionForm.jsx";
import "./Dashboard.css"
import TransactionHistory from "./TransactionHistory/TransactionHistory.jsx";
const Dashboard = ({ onLogout }) => {
    const token = sessionStorage.getItem("token");
    const [currencyRates, setCurrencyRates] = useState([]);

    useEffect(() => {
         const fetchData = async () => {
             const resp = await fetch("http://127.0.0.1:8080/api/get",{
                 method:"GET",
                 headers:{"Authorization":"Bearer "+sessionStorage.getItem("token")}
             });
             const data = await resp.json();
             const new_data = data.sort(
                 (a,b) => a.code.localeCompare(b.code)
             )
             setCurrencyRates(new_data);

         }
         fetchData()


    }, []);



    return (
        <>
        <button className="logout-button" onClick={() => onLogout()}>Log out</button>
        <div className="container mt-5 text-center">
            <h2>Welcome to the Dashboard</h2>
            <CurrencyBar currencyRates={currencyRates} />
            <div className="transaction-section">
                <NewTransactionForm currencyRates={currencyRates} />
                <TransactionHistory />
            </div>

        </div>
        </>
    );
};

export default Dashboard;
