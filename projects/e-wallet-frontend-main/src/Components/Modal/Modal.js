import React from 'react';

const Modal = ({ showModal, toggleModal, children }) => {
  if (!showModal) return null;
  
  return (
    <div className="modal-overlay">
      <div className="modal-container">
        <button className="close-modal" onClick={toggleModal}>
          Close
        </button>
        {children}
      </div>
    </div>
  );
};

export default Modal;



































// import React, { useState } from 'react';

// const Verification = ({ isOpen, onClose, children }) => {
//   const [visibility, setVisibility] = useState(isOpen);

//   const handleClose = () => {
//     setVisibility(false);
//     onClose();
//   };

//   return (
//     visibility && (
//       <div className="modal-container">
//         <div className="modal-content">
//           <button onClick={handleClose}></button>
//           {children}
//         </div>
//       </div>
//     )
//   );
// };

// export default Verification;
