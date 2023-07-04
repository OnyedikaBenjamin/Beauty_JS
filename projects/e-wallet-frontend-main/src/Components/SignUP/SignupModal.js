import React from "react";
import "./Modal.css";


const SignupModal = props => {

    if(!props.show) {
        return null;
    }



  return (
    <div className="modal">
        <div className="modal-content">
            <div className="modal-body">
            Kindly Check your mail to see your OTP number for Account verification 
            </div>
        </div>
    </div>
  );
}

export default SignupModal;