const ImageCard = (props) => (
    <img 
    src={props.src} 
    alt={props.alt} 
    className={props.className} />
  );

export default ImageCard;