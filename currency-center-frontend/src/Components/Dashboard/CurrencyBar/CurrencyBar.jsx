import {useRef, useState} from "react";
import "./CurrencyBar.css"
const CurrencyBar = ({currencyRates}) => {
    const scrollRef = useRef(null);

    const [selectedCurrency, setSelectedCurrency] = useState(null);
    const [selectedCurrencyBuyRate, setSelectedCurrencyBuyRate] = useState(null);
    const [selectedCurrencySellRate, setSelectedCurrencySellRate] = useState(null);
    const scroll = (offset) => {
        if (scrollRef.current) {
            scrollRef.current.scrollLeft += offset;
        }
    };

    const updateRate = async (e) =>{
        e.preventDefault();
        const resp = await fetch("http://127.0.0.1:8080/api/update/"+selectedCurrency.code,{
            method:"PUT",
            headers:{
                "Authorization":"Bearer "+sessionStorage.getItem("token"),
                "Content-type":"application/json"
            },
            body:JSON.stringify({buy_rate:selectedCurrencyBuyRate,sell_rate:selectedCurrencySellRate})
        })
        if(resp.status === 200){
            alert("sucessfully updated "+selectedCurrency.code+"'s rates")
            closeModal();
            location.reload();
        }
        else{
            alert("error while updating rates...")
            console.log(resp.body)
        }


    }


    const closeModal = () =>{
        setSelectedCurrency(null);
        setSelectedCurrencyBuyRate(null);
        setSelectedCurrencySellRate(null)
    };

    return (
        <div className="position-relative bg-light p-2 shadow-sm">
            <h5 className="mb-2 text-center">Currency Exchange Rates</h5>
            <button onClick={() => scroll(-200)} className="btn btn-light position-absolute top-50 start-0 translate-middle-y">
                <i className="bi bi-chevron-left"></i>
            </button>
            <div
                className="d-flex overflow-auto gap-3 px-5"
                style={{ scrollBehavior: "smooth", whiteSpace: "nowrap" }}
                ref={scrollRef}
            >
                {currencyRates.map((rate) => (
                    <div key={rate.code} className="bar-card flex-shrink-0 p-3" onClick={() => {
                        setSelectedCurrency(rate);
                        setSelectedCurrencyBuyRate(rate.buy_rate);
                        setSelectedCurrencySellRate(rate.sell_rate)
                    }}>
                        <h6>{rate.name} ({rate.code})</h6>
                        <p className="mb-1"><strong>Buy:</strong> {rate.buy_rate} PLN</p>
                        <p className="mb-0"><strong>Sell:</strong> {rate.sell_rate} PLN</p>
                    </div>
                ))}
            </div>
            <button onClick={() => scroll(200)} className="btn btn-light position-absolute top-50 end-0 translate-middle-y">
                <i className="bi bi-chevron-right"></i>
            </button>

            {selectedCurrency && (
                <div className="currency-modal" onClick={closeModal}>
                    <div className="currency-modal-content" onClick={(e) => e.stopPropagation()}>
                        <h4>{selectedCurrency.name} ({selectedCurrency.code})</h4>
                        <form className="update-rate-form" onSubmit={updateRate}>
                            <label><strong>Buy rate:</strong><input
                                type="number"
                                defaultValue={selectedCurrency.buy_rate}
                                id="r-amount" name="amount" min="0.01" step="0.01"
                                onChange={(e) => setSelectedCurrencyBuyRate(e.target.value)}
                            /></label>
                            <label>
                                <strong>Sell rate:</strong>
                                <input
                                    type="number"
                                    defaultValue={selectedCurrency.sell_rate}
                                    id="r-amount" name="amount" min="0.01" step="0.01"
                                    onChange={(e) => setSelectedCurrencySellRate(e.target.value)}
                                    />
                            </label>
                            <div className="update-rate-buttons">
                                <button className="btn btn-success mt-3" type="submit">Save</button>
                                <button className="btn btn-danger mt-3" onClick={closeModal}>Close</button>
                            </div>
                        </form>

                    </div>
                </div>
            )}

        </div>
    );
};

export default CurrencyBar;
