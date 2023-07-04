import { useState} from "react";
import "../styles/Authentication.css"
import { Link, useNavigate } from "react-router-dom";
const Login = () => {
    const [loginData, setLoginData] = useState({
        email: "",
        password: ""
    }) 
    const [error, setError] = useState("")
    const navigate = useNavigate()

    const handleChange = (event) => {
        const {name, value} = event.target
        setLoginData(prevUserData => {
            return {
                ...prevUserData,
                [name]: value
            }
        }) 
    }

    const postHandler = async (value) => {
        try{
            const response = await fetch("http://localhost:8080/login", {
                method: 'POST',
                body: JSON.stringify(value),
                headers: {
                    'Content-Type': 'application/json'
                },
        });
        if (!response.ok){
            throw new Error("Invalid email or password")
        }
        const data = await response.json()
        navigate("/note", {
            state: data.userName
        }) 
        }catch(error){
            setError(error.message)
        }
    }

    const handleSubmit = (event) => {
        event.preventDefault()
        const newUserData = {
            email: loginData.email,
            password: loginData.password
        }
        postHandler(newUserData)
        
    }


    return(
        <div>
             <h1><em>Notes...</em></h1>
             <div className="form-div">
                <h1>Login</h1>
                <p>{error}</p>
                <form className="auth-form">
                    <input name = "email" type="email" placeholder="Email" value={loginData.email}  onChange = {handleChange} required/>
                    <input name = "password" type="password" placeholder="Password" value = {loginData.password} 
                     onChange = {handleChange} required/>
                    <button type="submit" onClick={handleSubmit}>Login</button>
                </form>
                <p>Not registered? <Link className = "link" to="/register">Sign up</Link></p>
            </div>
            </div>
      );
}

export default Login