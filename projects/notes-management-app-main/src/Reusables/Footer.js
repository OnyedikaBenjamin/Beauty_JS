import "./Footer.css"

let date = new Date()
let currentYear = date.getFullYear()
const Footer = () => {
    return(
        <footer className="footer">
            <p>Copyright &copy; {currentYear}</p>
        </footer>
    )
}
export default Footer