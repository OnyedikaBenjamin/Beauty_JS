import "../styles/NoteView.css"
import { useLocation, useNavigate } from 'react-router-dom'
import { useState } from "react"
import Modals from "../../../Reusables/Modals"

const NoteView = () => {
    const location = useLocation()
    const navigate = useNavigate()
    console.log(location.state)
    const {title, content, noteId, userName} = location.state
    const [notification, setNotification] = useState("")
    const [modal, showModal] = useState(false)
    
    const postHandler = async (value) => {
        const response = await fetch("http://localhost:8080/patch_note", {
                method: 'PATCH',
                body: JSON.stringify(value),
                headers: {
                    'Content-Type': 'application/json'
                },
        });
        const data = await response.json()
        setNotification(data.message)
        showModal(true)
    }

    const saveNote = () => {
        const title = document.querySelector(".title").innerHTML
        const content = document.querySelector(".content").innerHTML
        const editedNote = {
            noteId: noteId,
            userName: userName,
            title:title,
            content: content
        }
        showModal(false)
        postHandler(editedNote)
    }

  return (
    <div className="note-view">
        {modal && <Modals notification={notification}/>}
        <h1>Notes...</h1>
        <div className="head">
            <button onClick={()=> navigate(-2)}>Back to notes</button>
            <p>Click on text to edit</p>
        </div>
        <div className="editor">
            <p className = "title" contentEditable suppressContentEditableWarning>{title}</p>
            <p className = "content" contentEditable suppressContentEditableWarning>{content}</p>
            <button onClick={saveNote}>SAVE</button>
        </div>
    </div>
  )
}

export default NoteView