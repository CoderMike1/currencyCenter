import {useState,useEffect} from "react";
import { useNavigate } from "react-router-dom";
import "./Unauthorized.css"
const Unauthorized = () => {
    const navigate = useNavigate();
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        const token = sessionStorage.getItem("token");
        console.log(token);
        if(token === null || token === ""){
            setIsLoggedIn(false);
            navigate("/login");
        }
        else{
            setIsLoggedIn(true);
        }
    })

    return (
        <>
            {isLoggedIn ?
                <div className="permission-denied-container">
                    <h3 className="permission-denied-h3">Permission Denied</h3>
                </div>
                :
                <>
                </>
            }
        </>
    )
}

export default Unauthorized;