import { useState } from "react";
import "../styles/NoteTaker.css"
import Modals from "../../../Reusables/Modals";
const NoteTaker = (props) => {
    const [note, setNote] = useState({
        title: "",
        content: ""
    })
    const [error, setError] = useState("")
    const [notification, setNotification] = useState("")
    const [modal, showModal] = useState(false)

    const handleChange = (event) => {
        const {name, value} = event.target
        setError("")
        setNote(prevNote => {
            return {
                ...prevNote,
                [name]: value
            }
        })
    }

    const postHandler = async (value) => {
        try{
            const response = await fetch("http://localhost:8080/create_note", {
                method: 'POST',
                body: JSON.stringify(value),
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            if (!response.ok){
                throw new Error("Invalid title or content")
            }
            const data = await response.json()
            setNotification(data.message)
            showModal(true)
        }catch(error){
            setError(error.message)
        }
    }
    const handleSubmit = (event) => {
        event.preventDefault()
        showModal(false)
        const newNote = {
            userName: props.userName,
            title: note.title,
            content: note.content
        }
        postHandler(newNote)
        setNote({
            title: "",
            content: ""
        })
    }
    return (
        <div>
            {modal && <Modals notification={notification} />}
            <p>{error}</p>
            <form className="note-form">
                <input name="title" placeholder="Title" value={note.title} onChange={handleChange} required/>
                <textarea name="content" placeholder="Take a note..." value={note.content} onChange = {handleChange} required/>
                <button onClick = {handleSubmit}>Add</button>
              </form>
        </div>
    )
}
export default NoteTaker