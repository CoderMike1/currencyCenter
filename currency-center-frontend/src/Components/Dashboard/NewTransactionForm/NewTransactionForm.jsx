
import "./NewTransactionForm.css"
import {useEffect, useState} from "react";
const NewTransactionForm = ({currencyRates}) => {

    const [type,setType] = useState("");
    const [amount, setAmount] = useState("");
    const [currency,setCurrency] = useState("");
    const [rateAmount, setRateAmount] = useState(null);
    const [result, setResult] = useState("");

    useEffect(() => {
        if(type && amount >0 && currency){
            let rate1;
            if(type === "BUY"){
                rate1 = currencyRates.find((rate) => rate.code === currency).buy_rate;
            }
            if(type === "SELL"){
                rate1 = currencyRates.find((rate) => rate.code === currency).sell_rate;
            }
            setRateAmount((amount*rate1).toFixed(2));
            setResult(rateAmount+" PLN");
        }
    },[type,rateAmount,currency]);


    const createTransaction = async (e) =>{
        e.preventDefault();
        console.log(type)
        const resp = await fetch("http://127.0.0.1:8080/api/transaction/add",{
            method:"POST",
            headers:{
                "Authorization":"Bearer "+sessionStorage.getItem("token"),
                "Content-type":"application/json"
            },
            body: JSON.stringify({type:type,amount:amount,currency:currency})
        });
        if(resp.status === 201){
            document.getElementById("add-form").reset();
            setResult(null);
            alert("pozytywnie dodano nowa transakcje...");
            location.reload()
        }
        else{
            alert("error while adding new transaction...")
        }
    }


    return (
        <div className="add-transaction-container">

        <h2>Add new transaction</h2>
        <form onSubmit={(e) => createTransaction(e)} id="add-form">
            <label>Transaction type:</label>
            <div className="radio-group">
                <label><input type="radio" name="type" value="BUY" onChange={(e) =>setType(e.target.value)} required/>BUY</label>
                <label><input type="radio" name="type" value="SELL" onChange={(e) =>setType(e.target.value)} required/>SELL</label>
            </div>
            <div className="amount-group">
            <label>Amount:</label>
            <input type="number" id="amount" name="amount" min="0.01" step="0.01" onChange={(e) => setAmount(e.target.value)} required/>
            </div>
            <label>Currency:</label>
            <div className="currency-group">
                <select id="currency" name="currency" onClick={(e) => setCurrency(e.target.value)} required>
                    <option value="">-- Choose currency --</option>
                    {currencyRates.map((rate) => (
                        <option key={rate.code} value={rate.code}>{rate.code}</option>
                    ))}
                </select>
            </div>

            <p>{result}</p>
            <button type="submit">Submit</button>

        </form>
        </div>
    )


}
export default NewTransactionForm