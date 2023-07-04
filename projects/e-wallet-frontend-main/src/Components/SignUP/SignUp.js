import "./SignUp.css";
import { Link} from "react-router-dom";
import  {useNavigate } from 'react-router-dom';
import image from "../Assets/img.jpeg";
import React, {useState, useEffect} from 'react';
import Button from "../ReUsableComponent/Button";
import Modal from "./SignupModal";
import SignupModal from "./SignupModal";


function SignUp(){
   
    const [usersDetail, setUsersDetail] = useState({
        firstName: "",
        lastName: "",
        emailAddress: "",
        password: ""
    })

    const navigate = useNavigate();

    const [show, setShow] = useState(false);

    //const toggleModal = () => setShowModal(!showModal);

    
    const [alert, setAlert] = useState(false);

    

    useEffect(() => {
        if(alert) {
            setTimeout(() => {
                setAlert(false);
            
            }, 3000)
        }
    }, [alert])


   const handleChange = (event) => {
    const {name, value} = event.target
    setUsersDetail((prevState) => {
        return {
            ...prevState,
            [name]: value
        }
    })
   }
   
   const url = "https://aa94-154-113-161-131.eu.ngrok.io/api/v1/registration/register"

   const postData = async () => {
    //event.preventDefault();
    console.log(usersDetail)
    console.log("clicked")
    const response = await fetch(url, {
      method: 'POST',
      body: JSON.stringify(usersDetail),
      headers: {
        "Content-type": "application/json"
      }
    });
    const data = await response.json();
    console.log(data);
    if (data.message === 'Email already exists') {
      setAlert(true);
      return;
    }
    // setShowModal(true)

    navigate("/OTP", {
      state: {
        emailAddress: usersDetail.emailAddress,
        password: usersDetail.password
      }
    });
    // toggleModal();
    
    
  };
    
    return (
      <div className="signup-container">
        <div className="sign-up-left-container">
          <img className="logo" src={image} alt="" />
        </div>

        <div className="sign-up-right-container">
          <h2 className="header-texts">Welcome!</h2>
          <h4 className="headers-paragraph">
            Sign up by entering the information below
          </h4>
          <div className="form-container">
            {alert && usersDetail.emailAddress ? (
              <div className="alert"> Email already exists.</div>
            ) : null}
            {alert && usersDetail.password ? (
              <div className="alert"> Password already exists.</div>
            ) : null}
            <form method="post" className="sign-up-form">
              <label>
                <input
                  className="firstName"
                  value={usersDetail.firstName}
                  onChange={handleChange}
                  name="firstName"
                  type="text"
                  placeholder="First Name"
                  required
                />
              </label>

              <label>
                <input
                  className="lastName"
                  value={usersDetail.lastName}
                  onChange={handleChange}
                  type="text"
                  name="lastName"
                  placeholder="Last Name"
                  required
                />
              </label>

              <label>
                <input
                  className="email"
                  type="email"
                  value={usersDetail.emailAddress}
                  onChange={handleChange}
                  name="emailAddress"
                  placeholder="email"
                  required
                />
              </label>

              <label>
                <input
                  className="passwords"
                  value={usersDetail.password}
                  onChange={handleChange}
                  type="password"
                  name="password"
                  placeholder="Password"
                  required
                />
              </label>
            </form>

            <div className="options-container">
              <p className="signup-option">Already have an account?</p>
              <p>
                <Link
                  to="/LogIn"
                  className="login-option"
                  style={{ textDecoration: "none" }}
                >
                  {""}
                  <span className="login-option">Login</span>
                </Link>
              </p>
            </div>
            <Button
              name="Sign up"
              width="72%"
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
              postData={postData}
            >
              {""}
            </Button>
            <div className="modal-container">
              <button onClick={() => setShow(true)}> Show Modal</button>
              <SignupModal show={show} />
            </div>
          </div>
        </div>
      </div>
    );
}

export default SignUp;
