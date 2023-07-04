import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router';
import otpimage from "../Assets/otpimage.png";
import Image from '../ReUsableComponent/ImageCard';



const VerifyOTP = () => {
  const [otp, setOtp] = useState('');
  // const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

    const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    // setLoading(true);

    try {
      const response = await fetch('https://aa94-154-113-161-131.eu.ngrok.io/api/v1/forgot-password/verify-otp', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ otp }),
      });

      if (response.status === 200) {
        console.log('OTP verified!');
        navigate('/ResetPassword')
      } else {
        setError(await response.text());
      }
    } catch (e) {
      setError(e.message);
    // } finally {
    //   setLoading(false);
    }
   
  };

  return (
    <div className='verify-otp-container'>
      <div className="sign-up-left-container">
          <Image className="logo" src={otpimage} alt="" />
        </div>
      
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          value={otp}
          onChange={(e) => setOtp(e.target.value)}
        />
        {error && <p>{error}</p>}
        <button type="submit">
          Verify OTP
        </button>
      </form>
    </div>
  );
};

export default VerifyOTP;
