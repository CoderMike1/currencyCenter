
import "./TransactionHistory.css"
import {useEffect, useState} from "react";
const TransactionHistory = () => {

    const [history, setHistory] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const resp = await fetch("http://localhost:8080/api/transaction/get",{
                method:"GET",
                headers:{
                    "Authorization":"Bearer "+sessionStorage.getItem("token")
                }
            });
            if(resp.status === 200){
                const data = await resp.json();
                setHistory(data.sort((a,b) => a.id - b.id));
            }
            else{
                alert("error while fetching transaction history data.")
            }
        }
        fetchData();
    },[])

    return (
        <div className="container">
        <h2>Transaction History</h2>
        <table className="table table-bordered table-striped">
            <thead>
            <tr>
                <th>#</th>
                <th>Date</th>
                <th>Type</th>
                <th>Currency</th>
                <th>Amount</th>
                <th>Exchange Rate</th>
                <th>Exchanged Amount</th>
                <th>Created by</th>
            </tr>
            </thead>
            <tbody>

            {history.map((transaction) => (
                <tr key={transaction.id}>
                    <td>{transaction.id}</td>
                    <td>{transaction.date}</td>
                    <td>{transaction.type}</td>
                    <td>{transaction.currency}</td>
                    <td>{transaction.amount}</td>
                    <td>{transaction.exchange_rate} PLN</td>
                    <td>{transaction.exchanged_amount} PLN</td>
                    <td>{transaction.employee.username}</td>
                </tr>
            ))}
            </tbody>
        </table>
        </div>
    )

}

export default TransactionHistory