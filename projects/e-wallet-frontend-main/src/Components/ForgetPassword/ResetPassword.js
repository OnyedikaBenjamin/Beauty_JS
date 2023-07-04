import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const ResetPassword = () => {
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [showModal, setShowModal] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    setLoading(true);

    try {
      const response = await fetch('https://aa94-154-113-161-131.eu.ngrok.io/api/v1/forgot-password/reset-password', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ password }),
      });

      if (response.status === 200) {
        setShowModal(true);
      } else {
        setError(await response.text());
      }
    } catch (e) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <form onSubmit={handleSubmit}>
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        {error && <p>{error}</p>}
        <button type="submit" disabled={loading}>
          Reset Password
        </button>
      </form>
      {showModal && (
        <div>
          <p>Password reset successful!</p>
          <button onClick={() => navigate('/Dashboard')}>Go to Dashboard</button>
        </div>
      )}
    </>
  );
};

export default ResetPassword;






































// import React, { useState, useEffect } from 'react';
// import { useNavigate } from 'react-router-dom';

// const ResetPassword = () => {
//   const [password, setPassword] = useState('');
//   const [loading, setLoading] = useState(false);
//   const [error, setError] = useState('');
//   const [showModal, setShowModal] = useState(false);
//   const navigate = useNavigate();

//   const handleSubmit = async (event) => {
//     event.preventDefault();
//     setLoading(true);

//     try {
//       const response = await fetch('https://aa94-154-113-161-131.eu.ngrok.io/api/v1/forgot-password/reset-password', {
//         method: 'POST',
//         headers: {
//           'Content-Type': 'application/json',
//         },
//         body: JSON.stringify({ password }),
//       });

//       if (response.status === 200) {
//         setShowModal(true);
//       } else {
//         setError(await response.text());
//       }
//     } catch (e) {
//       setError(e.message);
//     } finally {
//       setLoading(false);
//     }
//   };

//   return (
//     <>
//       <form onSubmit={handleSubmit}>
//         <input
//           type="password"
//           value={password}
//           onChange={(e) => setPassword(e.target.value)}
//         />
//         {error && <p>{error}</p>}
//         <button type="submit" disabled={loading}>
//           Reset Password
//         </button>
//       </form>
//       {showModal && (
//         <div>
//           <p>Password reset successful!</p>
//           <button onClick={() => navigate('/dashboard')}>Go to Dashboard</button>
//         </div>
//       )}
//     </>
//   );
// };

// export default ResetPassword;
