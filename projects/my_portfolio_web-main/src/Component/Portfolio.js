import React from "react";
import "./port.css";
import logo from "../asset/img/logo.png";
import img from "../asset/img/img.png";
import wallet1 from "../asset/img/wallet1.png";
import wallet2 from "../asset/img/wallet2.png";
import wallet3 from "../asset/img/wallet3.png";
import wallet4 from "../asset/img/wallet4.png";
import Godman from "../asset/img/Godman.png";
// import logos from "../asset/img/logos.jpg";
import code from "../asset/img/code.jpg";
import instagram from "../asset/img/instagram.png";
import twitter from "../asset/img/twitter.png";
import whatsapp from "../asset/img/whatsapp.png";
import linkedin from "../asset/img/linkedin.png";
import GitHub from "../asset/img/GitHub.png";
import { BsFillTelephoneFill } from "react-icons/bs";
import { AiOutlineMail } from "react-icons/ai";

function Portfolio() {
  return (
    <div className="port">
      <div className="port_menu">
        <div className="port_logo">
          <img src={logo} />
        </div>
        <div className="port_menu">
          <ul>
            <li>Home</li>
            <li>Services</li>
            <li>Projects</li>
            <li>About</li>
            <li>Contact</li>
          </ul>
        </div>
      </div>
      <div className="hero">
        <div className="hero_content">
          <h1>KENNY GODMAN</h1>
          <p>HTML | CSS | JAVASCRIPT | REACT | WORDPRESS | JAVA | SPRINGBOOT</p>
          <button>Get Started</button>
        </div>
        <div className="hero_image">
          <img src={Godman} />
        </div>
      </div>
      <div className="head-text">
        <h1>SERVICES</h1>
      </div>
      <div className="services">
        <div className="serv">WEB DESIGN</div>
        <div className="serv">WEB DEVEOPMENT</div>
        <div className="serv">WEB APP DEVELOPMENT</div>
        <div className="serv">UX DESIGN</div>
      </div>

      <div className="projects">
        <h1 className="proj">PROJECTS</h1>
        <div className="projects_display">
          <div>
            <img src={wallet1} />
          </div>
          <div>
            <img src={wallet2} />
          </div>
          <div>
            <img src={wallet3} />
          </div>
          <div>
            <img src={wallet4} />
          </div>
        </div>
      </div>

      <div>
        <h1 className="head">ABOUT</h1>
      </div>

      <div className="about">
        <div className="imgs">
          <img src={code} />
        </div>
        <div className="text">
          <p>
            I am a software engineer skilled in both object-oriented and
            functional programming languages, web frameworks, relational and
            non-relational databases, and continuous integration and development
            tools for building and deploying modern web applications. I am a
            solution-driven software engineer, I can work with diverse team of
            developers to build innovative and end-user friendly solution for
            businesses and organizations. I use different frameworks and
            methodologies to achieve given tasks or projects. Throughout my
            career, I have exhibited unmatchable creativity and professionalism
            in software development with excellent and strategic client
            alignment for the deliverable objectives of organizations.
          </p>
        </div>
      </div>
      <div className="contact-text">
        <h1>CONTACT</h1>
      </div>
      <div className="contact"></div>
      <div className="cont">
        <a href="https://www.instagram.com/iamstunnerlee/" target="_blank">
          <img src={instagram} />
        </a>
        <a href="https://twitter.com/kenny_godman" target="_blank">
          <img src={twitter} />
        </a>
        <a
          href="https://api.whatsapp.com/send?phone=%2B2347034760719&text&app_absent=0"
          target="_blank"
        >
          <img src={whatsapp} />
        </a>
        <a
          href="https://www.linkedin.com/in/kenny-daniels-515002163/"
          target="_blank"
        >
          <img src={linkedin} />
        </a>
        <a href="https://www.github.com/KennyGodman" target="_blank">
          <img src={GitHub} />
        </a>
        {/*  */}
      </div>
      <hr />
      <footer>
        <p id="phone">
          <BsFillTelephoneFill />
        </p>{" "}
        <p phones>07034760719</p>
        <p id="mail">
          {" "}
          <AiOutlineMail />
        </p>
        <p id="mail1"> kennydaniels138@gmail.com</p>
        <p id="mails">
          {" "}
          <AiOutlineMail />
        </p>
        <p id="mail2">dannynilmar789@yahoo.com</p>
      </footer>
    </div>
  );
}

export default Portfolio;
