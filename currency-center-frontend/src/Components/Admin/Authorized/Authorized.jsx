import {useState,useEffect} from "react";

import "./Authorized.css"

const Authorized = () =>{
    const [listOfEmployees, setListOfEmployees] = useState([]);

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
                alert("Error")
            }
        }
        getEmployees();
    } ,[])


    return (
        <div className="admin-panel-container">
            <div className="left-panel">
                <h2>👥 Pracownicy</h2>
                <ul>
                    {listOfEmployees.map((employee) =>(
                        <li key={employee.id}>{employee.username}</li>
                    ))}
                    {/*tu dodac opcje wejscia w szczegoly kazdego pracownika i tam jakies ustawienia i statystyki*/}
                </ul>
            </div>

            <div className="center-panel">
                <h2>📊 Analizy</h2>
                <p>Tu będą wykresy i dane analityczne.</p>
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