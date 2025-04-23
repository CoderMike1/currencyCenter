
import "./SettingsBar.css"
import { useState} from "react";
import {useTranslation} from 'react-i18next';
const SettingsBar = ({onLogout,isLoggedIn}) => {
    const {i18n} = useTranslation();
    const [selectedLanguage,setSelectedLanguage] = useState(i18n.language);

    const changeLanguage = (lang) => {
        i18n.changeLanguage(lang);
        setSelectedLanguage(lang);
    }


    return (
        <div className="settings-bar">

            <div className="logout-button-section">
                {isLoggedIn &&
                    <button className="logout-button" onClick={() => onLogout()}>Log out</button>
                }
            </div>
            <div className="language-switcher">
                <button className={`lang-btn ${selectedLanguage==='pl' ? 'selected-lang' :''}`} onClick={() => changeLanguage('pl')}>
                    <img src="/flags/pl.png" alt="PL" className="flag-icon" />
                    PL
                </button>
                <button className={`lang-btn ${selectedLanguage==='en' ? 'selected-lang' :''}`} onClick={() => changeLanguage('en')}>
                    <img src="/flags/gb.png" alt="EN" className="flag-icon" />
                    EN
                </button>
            </div>
        </div>

    )
}
export default SettingsBar

