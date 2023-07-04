import React, { useState } from "react";
import LoginImg from '../Assets/login.jpeg'
import "./LogIn.css";
import { useNavigate } from "react-router-dom";
import { Link} from "react-router-dom";
import Button from "../ReUsableComponent/Button";



function Login() {
  const [userLoginDetail, setUserLoginDetail] = useState({
    emailAddress: "",
    password: "",
  });
  
  const [errorMessage, setErrorMessage] = useState("");

  const [showPassword, setShowPassword] = useState(false);
  const [showDashboard, setShowDashboard] = useState(false)


  const navigate = useNavigate();

  const BASE_URL = "https://aa94-154-113-161-131.eu.ngrok.io/api/v1/users/login";

  const handleChange = (event) => {
    const { name, value } = event.target
    setUserLoginDetail((prevState) => {
        return {
            ...prevState,
            [name]: value
        }
    })
  }


  const handleSubmit =  async (event) => {
    event.preventDefault();
    try {
      const response = await fetch(BASE_URL, {
        method: "POST",
        body: JSON.stringify(userLoginDetail),
        headers: {
          "Content-type": "application/json",
        },
      })
      .then(response => {
        if (!response.ok){
          throw new Error(response.statusText)
        }
        return response.json()
      })
      .then(data => {
        console.log(data);
        setShowDashboard(true);
        console.log("Login successful", data);
      })
      .catch(error => {
        if (error.message === "Unauthorized"){
          setErrorMessage("Incorrect password");
          console.log("Incorrect password");
        } else if (error.message === "Not Found"){
          setErrorMessage("Email not found");
          console.log("Email not found");
        } else {  
          setErrorMessage("Incorrect email");
          console.log("Incorrect email");
        }
      })
    } catch (error) {
      setErrorMessage(error.message);
    }
  }

  const login = (event) =>{
    handleSubmit(event)
    if (showDashboard){
      navigate("/Dashboard", {
        state: {
          emailAddress: userLoginDetail.emailAddress,
          password: userLoginDetail.password
        }
      })
    }
  }
  

  return (
    <div className="login-container">
      <div className="login-left-container">
        <img className="image-container" src={LoginImg} alt=""/>
      </div>
      <div className="login-right-container">
        <h2 className="header-text">Welcome Back!</h2>
        <h4 className="header-paragraph">Log in to your Dashboard</h4>
        {/* {showDashboard === false ? errorMessage : ""} */}
        {showDashboard === false ? errorMessage && <p className="error" >{errorMessage}</p> : ""}
        
        <div className="login_form-container">
          <form onSubmit={login}>
            <label>
              <input
                type="email"
                className="email"
                name="emailAddress"
                placeholder="Email"
                onChange={handleChange}
                value={userLoginDetail.emailAddress || ""}
              />
            </label>
            <label>
              <input
                className="password-input"
                placeholder="Password"
                type={showPassword ? "text" : "password"}
                name="password"
                value={userLoginDetail.password || ""}
                onChange={handleChange}
                required
              />
            </label>
            <div className="checker-forgot-password-container">
              <div className="checker">
                <input  
                  className="checkbox"
                  onChange={() => setShowPassword(!showPassword)}
                  type="checkbox"  />
                <label className="rem-me">Remember me</label>
              </div>
            <div className="forgot-password">
              <p>
                <Link className="forgot-password-link" to="/ForgetPassword" style={{textDecorator: 'none'}}>
                  {""}
                  Forgot Password?
                </Link>
              </p>
            </div>
            </div>
            <Button 
              name="Login"
              width="77%"
              height="65px"
              backgroundColor="#55229e"
              border="none"
              outline="none"
              color="white"
              borderRadius="10px"
              padding="20px"
              fontSize="large"
              cursor="pointer"
              margin-top="20px"
              type="submit"
            />
            
          </form>
        
          <div className="login-container">
            <p className="create-account-options">Don't have an Account?</p>
              <p>
               <Link to="/SignUp">
                 {""}
                 <span className="sign-up-text">Sign Up</span>
               </Link>
             </p>
          </div>
        </div>
        </div>
      </div>
  );
}

export default Login;