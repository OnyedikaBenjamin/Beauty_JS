import "./ForgetPassword.css";
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ResetImg from "../Assets/resetPassword.png";
import flowImage from "../Assets/resetFlow.png";
import Button from "../ReUsableComponent/Button";
import ImageCard from "../ReUsableComponent/ImageCard";

const ForgotPassword = () => {

  const [emailAddress, setEmailAddress] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    setLoading(true);

    try {
      const response = await fetch('https://aa94-154-113-161-131.eu.ngrok.io/api/v1/forgot-password', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ emailAddress }),
      });

      if (response.status === 200) {
        navigate("/VerifyOTP");
      } else {
        setError(await response.json());
      }
    } catch (e) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="reset-password-container">
      <div className="reset-password-left-container">
        <ImageCard
          src={ResetImg}
          alt={"reset password"}
          className={"resetPasswordImage"}
        />
      </div>
      <div className="reset-password-right-container">
          <h4 className="reset-password-header-text">Reset Password </h4>
          <img className="header-reset-flow-image" src={flowImage} alt="" />
          <p className="header-password-paragraph">Enter Email Address to reset Password</p>

          <form className="forms" onSubmit={handleSubmit}>
            <label>  
              <input
                type="email"                
                className="reset-email-input"
                value={emailAddress}
                onChange={(event) => setEmailAddress(event.target.value)}
                placeholder="Email address"
                required
              />
              {error && <p>{error}</p>}
            </label>
            <Button
              name="NEXT"
              width="84%"
              height="65px"
              backgroundColor="#55229e"
              border="none"
              outline="none"
              color="white"
              borderRadius="10px"
              padding="20px"
              fontSize="large"
              cursor="pointer"
              marginTop="20px"
              type="submit"
              onSubmit={handleSubmit}
              // disabled={loading}
            />
          </form>
      </div>
    </div>
  );
};

export default ForgotPassword;
