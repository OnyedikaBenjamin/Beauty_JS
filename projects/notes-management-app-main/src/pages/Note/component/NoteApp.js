
import Header from "./Header"
import NoteTaker from "./NoteTaker"
import "../styles/NoteApp.css"
import "../styles/Header.css"
import { useLocation } from "react-router-dom"

const NoteApp = () =>{
    const location = useLocation()
    const userName = location.state
    return(
        <div className="note-app">
            <Header userName = {userName} />
            <NoteTaker userName = {userName}/>
        </div>
    )
}

export default NoteApp