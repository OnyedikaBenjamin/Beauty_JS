# REGISTRATION ENDPOINT

*For New Users*
1.  This endpoint allows appUser to register to the platform.
2.  This appUser remains unverified until email verified. 
3. Unverified User will not be allowed access to dashboard
4. This endpoint sends a token which expires in 10 minutes for
   verification to a users valid email address.
5. A code sample in Node JS using Axios is shown below

```
var axios = require('axios');
var data = JSON.stringify({
 "firstName": "Lekan", // This field is required
  "lastName": "Daniel", // This field is optional
  "email": "gab.oyinlola@gmail.com",
  // email field is required with a correct email format
  "phoneNumber": "08069580949",
  // Phone number field is required with a correct Nigerian phoneNumber format
  "password": "Test123!@#",
  // Password field is required with at least 8 caracters, 1 uppercase caracter, 1 lowercase caracter and 1 symbol
  "category": "COHORT_III"
  // Category field is required with a list of the available voting category available
  "imageURL": "www.cloudinary.com" //This field is required
});

var config = {
  method: 'post',
maxBodyLength: Infinity,
  url: 'https://africa-smart.onrender.com/api/v1/registration/register',
  headers: { 
    'Content-Type': 'application/json'
  },
  data : data
};

axios(config)
.then(function (response) {
  console.log(JSON.stringify(response.data));
})
.catch(function (error) {
  console.log(error);
});

```

# Response from Registration End-Point

*This is a sample of the response from the Registration !!!*
```
{
    "timestamp": "2023-03-03 || 11:03:56",
    "status": "CREATED",
    "data": {
        "data": "User Registration successful"
    },
    "path": "/api/v1/registration/register",
    "successful": true
}
```

# Create User End Point
*For User Verification*

1.  This endpoint allows appUser to verify account to the platform. 
2. Unverified User will not be allowed access to dashboard
3. This endpoint responds with a Json Web Token which expires in 3 Hours
4. The Json web Token would be used to access other end-points
5. A code sample in Node JS using Axios is shown below
```
var axios = require('axios');
var data = JSON.stringify({
  "token": "1073", //This field is required
  "email": "lekan.sofuyi@outlook.com" //This field is required
});

var config = {
  method: 'post',
maxBodyLength: Infinity,
  url: 'https://africa-smart.onrender.com/api/v1/appUser/create',
  headers: { 
    'Content-Type': 'application/json'
  },
  data : data
};

axios(config)
.then(function (response) {
  console.log(JSON.stringify(response.data));
})
.catch(function (error) {
  console.log(error);
});

```

# Response from Create User EndPoint
*This is a sample of the response from the Verify User Endpoint !!!*

```
{
    "timestamp": "2023-03-05 || 23:47:11",
    "status": "OK",
    "data": {
        "data": "Welcome Lekan, Account Verified Successfully"
    },
    "path": "/api/v1/appUser/create",
    "successful": true
}
```

# Login in User EndPoint

*Login Verified Users*

1.  This endpoint allows appUser to logIn to the platform.
2. Unverified User will not be allowed to logIn to the dashboard
3. Unverified User would have to resend Token to a Valid email from the resend token endpoint
4. This endpoint responds with a Json Web Token which expires in 3 Hours 
5. The Json web Token would be used to access other end-points
6. A code sample in Node JS using Axios is shown below

```
const axios = require('axios');
let data = JSON.stringify({
  "email": "gab.oyinlola@gmail.com", // This field is required
  "password": "Test123!@#" // This field is required
});

let config = {
  method: 'post',
maxBodyLength: Infinity,
  url: 'https://africa-smart.onrender.com/api/v1/appUser/login',
  headers: { 
    'Content-Type': 'application/json'
  },
  data : data
};

axios(config)
.then((response) => {
  console.log(JSON.stringify(response.data));
})
.catch((error) => {
  console.log(error);
});
```

# Response from Login User EndPoint
*This is a sample of the response from the Login User Endpoint !!!*
```
{
    "timestamp": "2023-03-03 || 11:08:17",
    "status": "OK",
    "data": {
        "data": <token>
    },
     "path": "/api/v1/appUser/login",
    "successful": true
}
```
# Resend Token EndPoint

*For Users with unverified account*

1.  This endpoint resends a token which expires in 10 minutes for
verification to a users valid email address.
2. Once verified User can access the login endpoint to login to endpoint
```
const axios = require('axios');
let data = JSON.stringify({
  "email": "gab.oyinlola@gmail.com"
});

let config = {
  method: 'post',
maxBodyLength: Infinity,
  url: 'https://africa-smart.onrender.com/api/v1/appUser/resend',
  headers: { 
    'Content-Type': 'application/json'
  },
  data : data
};

axios(config)
.then((response) => {
  console.log(JSON.stringify(response.data));
})
.catch((error) => {
  console.log(error);
});
```

# Response from Resend Token Endpoint
*This is a sample of the response from the Resend Token Endpoint !!!*
```
{
  "timestamp": "2023-03-03 || 11:15:19",
  "status": "OK",
  "data": {
    "data": "Token sent to email"
  },
  "path": "/api/v1/appUser/resend",
  "successful": true
}
```

# Verify Token Endpoint

1. This endpoint verifies token provided by the appUser

```
const axios = require('axios');
let data = JSON.stringify({
  "token": "1193",
  "email": "lekan.sofuyi01@gmail.com"
});

let config = {
  method: 'post',
maxBodyLength: Infinity,
  url: 'https://africa-smart.onrender.com/api/v1/appUser/verify',
  headers: { 
    'Content-Type': 'application/json'
  },
  data : data
};

axios(config)
.then((response) => {
  console.log(JSON.stringify(response.data));
})
.catch((error) => {
  console.log(error);
});

```
# Response from Verify  Token EndPoint
*This is a sample of the response from the Verify Token Endpoint !!!*

```
{
    "timestamp": "2023-03-03 || 16:34:04",
    "status": "OK",
    "data": {
        "data": "Token Verified"
    },
    "path": "/api/v1/appUser/verify",
    "successful": true
}
```
# Delete User End Point
1. This endpoint deletes a User from the Database
2. The Method is a Delete Method that takes a Token in the Header

```
const axios = require('axios');

let config = {
  method: 'delete',
maxBodyLength: Infinity,
  url: 'https://africa-smart.onrender.com/api/v1/delete',
  headers: { 
    'Authorization': 'Bearer <Json Web Token>'
  }
};

axios(config)
.then((response) => {
  console.log(JSON.stringify(response.data));
})
.catch((error) => {
  console.log(error);
});

```

# Delete User Endpoint Response 

*This is a sample of the response from the Delete User Endpoint for development Mode alone !!!*

```
{
    "timestamp": "2023-03-03 || 17:05:10",
    "status": "OK",
    "data": {
        "data": "User Deleted Successfully"
    },
    "path": "/api/v1/delete",
    "successful": true
}
```


# Updating User Details

```
var axios = require('axios');
var data = JSON.stringify({
  "firstName": "Lekan",
  "lastName": "Daniel",
  "phoneNumber": "080345",
  "password": "23456",
  "imageURL": "www.newURL.com"
});

var config = {
  method: 'put',
maxBodyLength: Infinity,
  url: 'https://africa-smart.onrender.com/api/v1/welcome/update',
  headers: { 
    'Authorization': 'Bearer <JSON token>', 
    'Content-Type': 'application/json'
  },
  data : data
};

axios(config)
.then(function (response) {
  console.log(JSON.stringify(response.data));
})
.catch(function (error) {
  console.log(error);
});

```

# Create a Poll
*To create a Poll End-point*

1. This endpoint is used to create a poll for other users in the same category
2. It is only accessed by a verified appUser with a valid authorization
```
var axios = require('axios');
var data = JSON.stringify({
  "title": "Priest",
  "question": "Whos the next Priest",
  "startDateTime": "2023-03-07 09:10:00",
  "endDateTime": "2023-03-07 09:30:00",
  "category": "COHORT_IV",
  "candidates": [
    {
      "candidateName": "Name 10"
    },
    {
      "candidateName": "Name 11"
    },
    {
      "candidateName": "Name 12"
    }
  ]
});

var config = {
  method: 'post',
maxBodyLength: Infinity,
  url: 'https://africa-smart.onrender.com/api/v1/poll/create',
  headers: { 
    'Authorization': 'Bearer <>JSON token', 
    'Content-Type': 'application/json'
  },
  data : data
};

axios(config)
.then(function (response) {
  console.log(JSON.stringify(response.data));
})
.catch(function (error) {
  console.log(error);
});


```
# Create poll response
*This is a sample of the response from the Create Poll !!!*
```
{
    "timestamp": "2023-03-06 || 23:07:42",
    "status": "OK",
    "data": {
        "data": "Poll Successfully Created!!! ",
        "pollId": "ee569d50-e731-4f64-8c8f-64b68224336b"
    },
    "path": "/api/v1/poll/create",
    "successful": true
}
```

# Deleting a Poll

```
var axios = require('axios');

var config = {
  method: 'delete',
maxBodyLength: Infinity,
  url: 'https://africa-smart.onrender.com/api/v1/poll/vote/{pollId}',
  headers: { 
    'Authorization': 'Bearer <JSON token>'
  }
};

axios(config)
.then(function (response) {
  console.log(JSON.stringify(response.data));
})
.catch(function (error) {
  console.log(error);
});

```

# View Active Poll
1. This endpoint is used to view the active polls
2. It is only accessed by a verified appUser with a valid authorization
3. This is a Get Request

```
const axios = require('axios');

let config = {
  method: 'get',
maxBodyLength: Infinity,
  url: 'https://africa-smart.onrender.com/api/v1/poll/active',
  headers: { 
    'Authorization': 'Bearer <Json Web Token>'
  }
};

axios(config)
.then((response) => {
  console.log(JSON.stringify(response.data));
})
.catch((error) => {
  console.log(error);
});

```

# View Active Poll Response
*This is a sample of the response from the View Active Poll Endpoint !!!*
```
{
    "timestamp": "2023-03-06 || 23:54:06",
    "status": "OK",
    "data": [
        {
            "title": "Class Priest",
            "question": "Whos the next Class Priest",
            "startDateTime": "2023-03-06T23:10:00",
            "endDateTime": "2023-04-01T08:00:00",
            "category": "COHORT_III",
            "candidates": [
                {
                    "candidateId": "60e90ae6-4a78-4370-8629-cf9e650c3017",
                    "candidateName": "Bolaji",
                    "candidateImageURL": "www.bolaji.com",
                    "status": "ACTIVE"
                },
                {
                    "candidateId": "ae9eef4f-3db5-49cb-a9ae-2b6dddac19be",
                    "candidateName": "James",
                    "candidateImageURL": "www.james.com",
                    "status": "ACTIVE"
                },
                {
                    "candidateId": "8317770a-2be2-4a77-b3c8-9ae5fbcad58c",
                    "candidateName": "Acha",
                    "candidateImageURL": "www.acha.com",
                    "status": "INACTIVE"
                }
            ]
        }
    ],
    "path": "/api/v1/poll/active",
    "successful": true
}

```
# View Recent Poll
1. This endpoint is used to view the Non-active polls
2. It is only accessed by a verified appUser with a valid authorization
```
const axios = require('axios');

let config = {
  method: 'get',
maxBodyLength: Infinity,
  url: 'https://africa-smart.onrender.com/api/v1/poll/recent',
  headers: { 
    'Authorization': 'Bearer <Json Web Token>'
  }
};

axios(config)
.then((response) => {
  console.log(JSON.stringify(response.data));
})
.catch((error) => {
  console.log(error);
});
```

# View Recent Poll Response
*This is a sample of the response from the View Recent Poll !!!*

```
{
    "timestamp": "2023-03-07 || 00:39:13",
    "status": "OK",
    "data": [
        {
            "title": "Food Priest",
            "question": "Whos the next Food Priest",
            "startDateTime": "2023-03-06T23:20:00",
            "endDateTime": "2023-03-06T23:50:00",
            "category": "COHORT_II",
            "candidates": [
                {
                    "candidateName": "Bolaji",
                    "candidateImageURL": "www.bolaji.com",
                    "candidateResult": 0,
                    "status": "INACTIVE",
                    "pollId": "a02876e6-52f2-4d1b-8f87-acf7de3c70ee"
                },
                {
                    "candidateName": "James",
                    "candidateImageURL": "www.james.com",
                    "candidateResult": 0,
                    "status": "INACTIVE",
                    "pollId": "a02876e6-52f2-4d1b-8f87-acf7de3c70ee"
                },
                {
                    "candidateName": "Acha",
                    "candidateImageURL": "www.acha.com",
                    "candidateResult": 0,
                    "status": "INACTIVE",
                    "pollId": "a02876e6-52f2-4d1b-8f87-acf7de3c70ee"
                }
            ]
        }
        {
            "title": "Priestest",
            "question": "Whos the next Priestiest",
            "startDateTime": "2023-03-06T23:20:00",
            "endDateTime": "2023-03-06T23:50:00",
            "category": "COHORT_I",
            "candidates": [
                {
                    "candidateName": "hadiza",
                    "candidateImageURL": "www.bolaji.com",
                    "candidateResult": 0,
                    "status": "INACTIVE",
                    "pollId": "ee569d50-e731-4f64-8c8f-64b68224336b"
                },
                {
                    "candidateName": "Jenifer",
                    "candidateImageURL": "www.james.com",
                    "candidateResult": 0,
                    "status": "INACTIVE",
                    "pollId": "ee569d50-e731-4f64-8c8f-64b68224336b"
                },
                {
                    "candidateName": "Kelz",
                    "candidateImageURL": "www.acha.com",
                    "candidateResult": 0,
                    "status": "INACTIVE",
                    "pollId": "ee569d50-e731-4f64-8c8f-64b68224336b"
                }
            ]
        }
    ],
    "path": "/api/v1/poll/recent",
    "successful": true
}
```

# Vote Endpoint
1. This endpoint is used to cast votes
2. It is only accessed by a verified appUser with a valid authorization
```
const axios = require('axios');
let data = JSON.stringify({
  "candidateId": 1
});

let config = {
  method: 'put',
maxBodyLength: Infinity,
  url: 'https://africa-smart.onrender.com/api/v1/poll/vote/{pollId}',
  headers: { 
    'Authorization': 'Bearer <Json Web Token>', 
    'Content-Type': 'application/json'
  },
  data : data
};

axios(config)
.then((response) => {
  console.log(JSON.stringify(response.data));
})
.catch((error) => {
  console.log(error);
});
```

# Vote Endpoint Response
*This is a sample of the response from the Vote Endpoint !!!*
```
{
    "timestamp": "2023-03-03 || 13:57:40",
    "status": "OK",
    "data": "You have successfully casted your vote",
    "path": "/api/v1/poll/vote/1",
    "successful": true
}
```
# Viewing results from poll
*This is a sample of the response from the Viewing Result !!!*

```
var axios = require('axios');
var data = '';

var config = {
  method: 'get',
maxBodyLength: Infinity,
  url: 'https://africa-smart.onrender.com/api/v1/candidate/results/{pollId}',
  headers: { 
    'Authorization': 'Bearer <JSON token>'
  },
  data : data
};

axios(config)
.then(function (response) {
  console.log(JSON.stringify(response.data));
})
.catch(function (error) {
  console.log(error);
});

```
# Viewing results from poll Response Body
*This is a sample of the response from the Result Endpoint !!!*
```
{
    "timestamp": "2023-03-07 || 08:07:35",
    "status": "OK",
    "data": [
        {
            "candidateName": "Bolaji",
            "candidateImageURL": "www.bolaji.com",
            "candidateResult": 0,
            "pollId": "38f4c2fb-a701-456a-a67f-0b257400d676",
            "status": "INACTIVE"
        },
        {
            "candidateName": "James",
            "candidateImageURL": "www.james.com",
            "candidateResult": 1,
            "pollId": "38f4c2fb-a701-456a-a67f-0b257400d676",
            "status": "INACTIVE"
        },
        {
            "candidateName": "Acha",
            "candidateImageURL": "www.acha.com",
            "candidateResult": 0,
            "pollId": "38f4c2fb-a701-456a-a67f-0b257400d676",
            "status": "INACTIVE"
        }
    ],
    "path": "/api/v1/candidate/results/38f4c2fb-a701-456a-a67f-0b257400d676",
    "successful": true
}
```