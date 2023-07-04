import React, { useState, useEffect } from "react";
import "./otp.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import Image from "../ReUsableComponent/ImageCard";
import otpimage from "../Assets/otpimage.png";






function Verification({ emailAddress, password }) {
  const [alert, setAlert] = useState("");
  const [otpInputFields, setOtpInputFields] = useState([
    { name: "field1", value: "" },
    { name: "field2", value: "" },
    { name: "field3", value: "" },
    { name: "field4", value: "" },
  ]);

  const navigate = useNavigate();

  const handleOtpInputChange = (event, index) => {
    const newOtpInputFields = [...otpInputFields];
    newOtpInputFields[index].value = event.target.value;
    setOtpInputFields(newOtpInputFields);
  };

  const OTP = otpInputFields.map((input) => input.value).join("");

  useEffect(() => {
    if (alert) {
      setTimeout(() => {
        setAlert("");
      }, 3000);
    }
  }, [alert]);

  const verifyOTP = async () => {
    const response = await axios.post("https://aa94-154-113-161-131.eu.ngrok.io/api/v1/registration/verify", {
      emailAddress,
      password,
      OTP,
    });

    const { data } = response;
    console.log(data);
    if (data.message === "Verified") {
      navigate("/");
    } else {
      setAlert("Invalid OTP");
    }
  };

  return (
    <div className="verification-container">
      <div className="sign-up-left-container">
          <Image className="logo" src={otpimage} alt="" />
        </div>
      <h2 className="header-text">Verify your email address</h2>
      <h4 className="header-text-paragraph">
        We've sent an OTP to your email address
      </h4>
      <div className="form-container">
        {alert && <div className="alert">{alert}</div>}
        <form className="verification-form">
          {otpInputFields.map((input, index) => (
            <label key={input.name}>
              <input
                type="text"
                maxLength="1"
                value={input.value}
                onChange={(event) => handleOtpInputChange(event, index)}
                required
              />
            </label>
          ))}
          <button onClick={verifyOTP}>Verify</button>
        </form>
      </div>
    </div>
  );
}

export default Verification;

