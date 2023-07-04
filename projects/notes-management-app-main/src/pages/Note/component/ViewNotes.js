import { useLocation, useNavigate } from "react-router-dom"
import "../styles/ViewNotes.css"
import Note from "./Note"
import { useState } from "react"

const ViewNotes = (props) => {
    const navigate = useNavigate()
    const location = useLocation()
    const notes = location.state.data
    const [newNotes, setNewNotes] = useState(notes)
    const [error, setError] = useState("")
    const userName = location.state.userName

    const refetch = async () => {
      try{
      const response = await fetch(`http://localhost:8080/notes/${userName}`);
      if (!response.ok){
        throw new Error("Something is wrong")
      }
      const data = await response.json()
      setNewNotes(data)
      }catch(error){
        setError(error.message)
      }
    }

  return (
    <div className="view-div">
        <div className="head">
            <button className = "back" onClick={()=> navigate(-1)}>Back to notes</button>
            <p>{notes.length === 0 && "Nothing to display"}</p>
            <p>{error}</p>
         </div>
        <div className="view">
            {newNotes.map(note => <Note key={note.id} id={note.id} title={note.title} content = {note.content} 
            creationTime = {note.creationTime}
            creationDate = {note.creationDate}
            editStatus = {note.editStatus}
            editDate = {note.editDate}
            editTime = {note.editTime}
            userName = {userName}
            refetch = {refetch}
        />)}
        </div>
    </div>
  )
}

export default ViewNotes