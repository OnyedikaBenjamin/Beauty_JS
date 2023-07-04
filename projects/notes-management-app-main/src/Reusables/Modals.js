import React from 'react'
import Modal from "react-modal"
import "./Modals.css"
import { useState } from 'react'

Modal.setAppElement('#root')
const Modals = (props) => {
  const [open, setOpen] = useState(true)
  const handleClick =()=>{
    setOpen(!open)
  }
  const customStyles = {
    overlay:{background: "grey", opacity: "90%"}
  }
  return (
    <Modal className="modal-body" isOpen={open} onRequestClose={handleClick} style={customStyles}>
      <p className='modal-p'>{props.notification}</p>
      <button onClick={handleClick}>ok</button>
    </Modal>
  )
}

export default Modals