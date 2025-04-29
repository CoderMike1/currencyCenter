import {useEffect, useState} from "react";
import "./CurrentBalance.css"
const CurrentBalance = ()=>{

    const [balanceList,setBalanceList] = useState([]);

    useEffect(()=>{

        const getBalance = async () =>{

            const resp = await fetch("http://127.0.0.1:8080/api/transaction/get-balance",{
                method:"GET",
                headers:{
                    "Authorization":"Bearer "+sessionStorage.getItem("token")
                }
            })
            if(resp.status === 201){
                const data = await resp.json()
                setBalanceList(data.results.sort((a,b)=>a.currency_code.localeCompare(b.currency_code)))
            }
            else{
                alert("error")
            }

        }
        getBalance();
    },[])

    return (
        <>
        <p>Current currency balance:</p>
            {balanceList.map((i)=> (
                <p className="currency-balance-p" key={i.id}>{i.amount} {i.currency_code}</p>
            ))}
        </>
    )
}

export default CurrentBalance;

