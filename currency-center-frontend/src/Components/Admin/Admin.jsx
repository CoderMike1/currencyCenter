import {jwtDecode} from "jwt-decode";
import {useEffect,useState} from "react"

import Authorized from "./Authorized/Authorized.jsx";
import Unauthorized from "./Unauthorized/Unauthorized.jsx";
const Admin = () =>{

    const [isAdmin,setIsAdmin] = useState(false);

    useEffect(() => {
        try{
            const token = sessionStorage.getItem("token");
            const decoded = jwtDecode(token);
            if(decoded.role === "ADMIN"){
                setIsAdmin(true);
            }
            else{
                setIsAdmin(false);
            }
        }
        catch{
            setIsAdmin(false);
        }
    })

    return (
        <>
            {isAdmin ?
                <Authorized />
                :
                <Unauthorized/>
            }
        </>
    )

}


export default Admin;