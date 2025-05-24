import {useState} from "react";
import "./Settings.css";

const Settings = () =>{
    const [showChangePasswordForm,setShowChangePasswordForm] = useState(false);
    const [changePasswordFormData, setChangePasswordFormData] = useState({
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
    });

    const handleChange = (e) =>{
        setChangePasswordFormData({
            ...changePasswordFormData,
            [e.target.name]: e.target.value
        })
    }

    const handleSubmit = async (e) =>{
        e.preventDefault();

        if(changePasswordFormData.newPassword !== changePasswordFormData.confirmPassword){
            alert("Hasła nie są takie same")
            return;
        }
        else{

            const resp = await fetch("http://localhost:8080/auth/change-password", {
                method:"POST",
                headers:{
                    "content-type":"application/json",
                    "authorization":"Bearer "+sessionStorage.getItem("token")
                },
                body: JSON.stringify({"old_password":changePasswordFormData.oldPassword, "new_password":changePasswordFormData.newPassword})
            })

            if(resp.status === 201){
                alert("Pozytywnie zmieniono haslo")
                location.reload()
            }
            else{
                const data = await resp.json();
                alert("Error : "+data.message);
            }
        }
    }



    return (
        <div className="change-password-tile">
            <div className="tile-header" onClick={() => setShowChangePasswordForm(!showChangePasswordForm)}>
                <h3>🔒 Zmiana hasła</h3>
            </div>

            {showChangePasswordForm && (
                <form onSubmit={handleSubmit} className="password-form">
                    <label>
                        Stare hasło:
                        <input
                            type="password"
                            name="oldPassword"
                            value={changePasswordFormData.oldPassword}
                            onChange={handleChange}
                            required
                        />
                    </label>

                    <label>
                        Nowe hasło:
                        <input
                            type="password"
                            name="newPassword"
                            value={changePasswordFormData.newPassword}
                            onChange={handleChange}
                            required
                        />
                    </label>

                    <label>
                        Powtórz nowe hasło:
                        <input
                            type="password"
                            name="confirmPassword"
                            value={changePasswordFormData.confirmPassword}
                            onChange={handleChange}
                            required
                        />
                    </label>

                    <button type="submit">Zmień hasło</button>
                </form>
            )}
        </div>
    );
};




export default Settings;