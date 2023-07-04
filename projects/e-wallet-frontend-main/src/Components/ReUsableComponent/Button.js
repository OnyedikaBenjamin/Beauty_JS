import { type } from "@testing-library/user-event/dist/type";
import React from "react";

const Button = ({
    name, 
    width,
    height,
    backgroundColor,
    border, 
    color, 
    marginLeft,
    borderTopLeft,
    borderTopRight,
    borderBottomLeft, 
    borderBottomRight,
    borderRadius, 
    borderRightRadius,
    borderLeftRadius,
    fontSize, passData
}) => {
    return(
        <button onClick={() => passData()} style={{
            width: width,
            height: height,
            borderTopLeft: borderTopLeft,
            borderBottomLeft: borderBottomLeft,
            borderBottomRight: borderBottomRight,
            borderRightRadius: borderRightRadius,
            borderLeftRadius: borderLeftRadius,
            borderTopRight: borderTopRight,
            borderRadius: borderRadius,
            color: color,
            backgroundColor: backgroundColor,
            border: border,
            marginLeft: marginLeft,
            fontSize: fontSize,            

        }}
        >{name}</button>
    )
}

export default Button

// import React from "react";

// const Button = ({
//   name,
//   width = "100px",
//   height = "40px",
//   backgroundColor = "#fff",
//   border = "none",
//   color = "#000",
//   marginLeft = 0,
//   fontSize = "16px",
//   passData,
//   className = "",
// }) => {
//   return (
//     <button
//       className={`button ${className}`}
//       onClick={passData}
//       style={{
//         width,
//         height,
//         backgroundColor,
//         border,
//         color,
//         marginLeft,
//         fontSize,
//       }}
//     >
//       {name}
//     </button>
//   );
// };

// export default Button;

