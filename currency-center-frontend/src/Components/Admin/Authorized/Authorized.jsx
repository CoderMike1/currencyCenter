import {useState,useEffect} from "react";

import "./Authorized.css"

const Authorized = () =>{
    const [listOfEmployees, setListOfEmployees] = useState([]);
    const [stats,setStats] = useState([]);
    const [financialSummary,setFinancialSummary] = useState([]);
    useEffect(() => {
        const getEmployees = async () =>{
            const resp = await fetch("http://localhost:8080/admin/get-employees", {
                method: "GET",
                headers: {
                    "Authorization":"Bearer "+sessionStorage.getItem("token")
                }
            })
            if(resp.status === 200){
                const data = await resp.json();
                setListOfEmployees(data)
            }
            else{
                const data = await resp.json();
                alert("Error: "+data.message)
            }
        }

        const getStats = async () => {
            const resp = await fetch("http://localhost:8080/admin/data/transaction-breakdown", {
                method:"GET",
                headers: {
                    "Authorization":"Bearer "+sessionStorage.getItem("token")
                }
            }
            )
            if(resp.status === 200){
                const data = await resp.json();
                setStats(data)
            }
            else{
                const data = await resp.json();
                alert("Error: "+data.message)
            }
        }

        const getFinancialSummary = async () =>{
            const resp = await fetch("http://localhost:8080/admin/data/financial-summary", {
                    method:"GET",
                    headers: {
                        "Authorization":"Bearer "+sessionStorage.getItem("token")
                    }
                }
            )
            if(resp.status === 200){
                const data = await resp.json();
                setFinancialSummary(data)
            }
            else{
                const data = await resp.json();
                alert("Error: "+data)
            }
        }
        getEmployees();
        getStats();
        getFinancialSummary();
    } ,[])


    return (
        <div className="admin-panel-container">
            <div className="left-panel">
                <h2>👥 Pracownicy</h2>
                <ul>
                    {listOfEmployees.map((employee) =>(
                        <li key={employee.id} className="left-panel-employee-li">{employee.username}</li>
                    ))}
                    {/*tu dodac opcje wejscia w szczegoly kazdego pracownika i tam jakies ustawienia i statystyki*/}
                </ul>
            </div>

            <div className="center-panel">
                <h2>📊 Analizy</h2>
                <div className="containers">
                    <div className="time-stats stats-container">
                        <h4 className="time-stats stats-h4">Liczba transakcji:</h4>
                        <div className="time-stats stats-grid">
                            <div className="time-stats stat-box" style={{ backgroundColor: "#e0f2fe" }}>
                                <div className="time-stats stat-title">Dzisiaj</div>
                                <div className="time-stats stat-value">{stats.this_day}</div>
                            </div>
                            <div className="time-stats stat-box" style={{ backgroundColor: "#dcfce7" }}>
                                <div className="time-stats stat-title">Ten tydzień</div>
                                <div className="time-stats stat-value">{stats.this_week}</div>
                            </div>
                            <div className="time-stats stat-box" style={{ backgroundColor: "#fef9c3" }}>
                                <div className="time-stats stat-title">Ten miesiąc</div>
                                <div className="time-stats stat-value">{stats.this_month}</div>
                            </div>
                            <div className="time-stats stat-box" style={{ backgroundColor: "#ede9fe" }}>
                                <div className="time-stats stat-title">Ten rok</div>
                                <div className="time-stats stat-value">{stats.this_year}</div>
                            </div>
                        </div>
                    </div>

                    <div className="financial-summary stats-container">
                        <h4 className="financial-summary stats-h4">Podsumowanie transakcji:</h4>
                        <div className="financial-summary stats-grid">
                            <div className="financial-summary stat-box" style={{ backgroundColor: "#e0f2fe" }}>
                                <div className="financial-summary stat-title">Łączna suma transakcji</div>
                                <div className="financial-summary stat-value">{financialSummary.fullExchangedAmount} PLN</div>
                            </div>
                            <div className="financial-summary stat-box" style={{ backgroundColor: "#dcfce7" }}>
                                <div className="financial-summary stat-title">Łączna suma transakcji zakupu waluty</div>
                                <div className="financial-summary stat-value">{financialSummary.fullBUYExchangedAmount} PLN</div>
                            </div>
                            <div className="financial-summary stat-box" style={{ backgroundColor: "#fef9c3" }}>
                                <div className="financial-summary stat-title">Łączna suma transakcji sprzedaży waluty</div>
                                <div className="financial-summary stat-value">{financialSummary.fullSELLExchangedAmount} PLN</div>
                            </div>
                            <div className="financial-summary stat-box" style={{ backgroundColor: "#ede9fe" }}>
                                <div className="financial-summary stat-title">Średnia wartość transakcji</div>
                                <div className="financial-summary stat-value">{financialSummary.averageExchangeAmount} PLN</div>
                            </div>
                        </div>
                    </div>
                </div>




            </div>


            <div className="right-panel">
                <h2>⚙️ Akcje</h2>
                <button>Pobierz raport</button>
                <button>Eksport do CSV</button>
            </div>
        </div>
    );
}

export default Authorized;