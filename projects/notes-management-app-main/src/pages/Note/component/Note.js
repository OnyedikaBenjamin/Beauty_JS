import "../styles/Note.css"
import { useState } from "react"
import { useNavigate} from "react-router-dom"
import Modals from "../../../Reusables/Modals"
const Note = (props) => {
    const [modal, showModal] = useState(false)
    const [notification, setNotification] = useState("")
    const navigate = useNavigate()
    const editNote = () => {
        navigate("/note-view", {
            state : {
                title : props.title,
                content: props.content,
                noteId: props.id,
                userName: props.userName
            }
        })
    }

    const deleteHandler = async (value) => {
        const response = await fetch("http://localhost:8080/delete_note", {
                method: 'DELETE',
                body: JSON.stringify(value),
                headers: {
                    'Content-Type': 'application/json'
                },
        });
        const data = await response.json()
        setNotification(data.message)
        showModal(true)
    }
    const deleteNote = () => {
        showModal(false)
        const selectedNote = {
            noteId: props.id,
            userName: props.userName
        }
        deleteHandler(selectedNote)
        props.refetch()
    }
    return (
        <div className="note" >
            {modal && <Modals notification={notification}/>}
            <p>Title: {props.title}</p>
            <p>Date-created: {props.creationDate}</p>
            <p>Time-created: {props.creationTime}</p>
            <p>{props.editStatus} {props.editDate} {props.editTime}</p>
            <div className="btns">
                <button onClick={editNote}>READ</button>
                <button onClick={deleteNote}>DELETE</button>
            </div>
      </div>
    )
}
 
export default Note