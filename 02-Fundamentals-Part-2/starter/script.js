// Defining functions
'use strict';
//----------------HOW TO NAME WRITE FUNCTIONS(METHODS)-----------------------------
// let year;
// let birthYear;

// (1) Arrow function 
// const age = (birthYear, year) => year - birthYear; // Multiple parameter
// const age2 = birthYear => 2023 - birthYear;   // Single parameter
 
// const realAge = age(1998, 2023);   
// const realAge2 = age2(1997);
// console.log(realAge);
// console.log(realAge2);

// let number1, number2, number3, average;
// const calculateAverage = (number1, number2, number3) =>{
//     return (number1 + number2 + number3)/3;
// }
//  average=calculateAverage(12, 13, 14);
// alert(`Therefore the average is ${average}.`)

// const calculateAverage = () =>{
//          number1=Number(prompt("Enter the first number"));
//          number2=Number(prompt("Enter the second number"));
//          number3=Number(prompt("Enter the third number"));
//          return (number1 + number2 + number3)/3; 
//      }
// average = calculateAverage();
// alert(`Therefore the average of ${number1}, ${number2} and ${number3} is ${average}.`)
     
// (2) Using function identifier

// function removeModal() {  
//     console.log('I have removed the modal')
//     }

//---------------------DEFINING ARRAYS-------------------

// const subjects = new Array('Critical-Thinking', 'JAVA', 'DSA');
// console.log(subjects);

// const subject = ['Critical-Thinking', 1991, 'JAVA', 'DSA'];
// console.log(subject);
// console.log(subject[1]);

// ---------------- Adding elements to an array----------------
//  =============== To the end of the array :
// console.log(subject);
// console.log(subject.length)
// =============== To the begining of the array :
// subject.unshift('john')
// console.log(subject);

// ------------------- Removing elements from an Array ---------
// ============== From the end of the array :
// subject.pop();
// console.log(subject);
// ==============From the beginning of the array  :
// subject.shift();
// console.log(subject);

// // Checking the index of an element that exist
// console.log(subject.indexOf('JAVA'));

// // CHecking if an element truly exist in the array
// console.log(subject.includes('Html'));
// console.log(subject.includes('JAVA'));
// console.log(subject.includes('HTML'));

// ------------------------ OBJECTS AND ARRAYS ------------------------

// const benjaminArray=['Benjamin',
//                      'Billion',
//                     '1998',
//                     'Software Engineer',
//                     ['Timmy', 'Tope', 'Dera-Billion']];
// console.log(benjaminArray[4]);

// const benjaminObject={
//     firstName:'Benjamin',
//     lastName:'Billion',
//     dob:1998,
//     job:'software-engineer',
//     friends:['Timmy', 'Tope', 'Dera-Billion']
// };
// console.log(benjaminObject)
// console.log(benjaminObject.friends)  
// console.log(benjaminObject['dob']);  // returns 1998

// const nameKey = 'Name';
// console.log(benjaminObject['first' + nameKey]);
// console.log(benjaminObject['last' + nameKey]);

// const interestedIn = prompt('What do you want to know about Ben-Billion?' +
//                             'Choose between firstName, lastName, age, job, and friends');

// if (benjaminObject[interestedIn]) {
//   alert(benjaminObject[interestedIn]);
// } else {
//   alert('Wrong request! Choose between firstName, lastName, age, job, and friends');
// };

//-------ADDING NEW OBJECTS PROPERTIES USING THE dot Operator or arrow operartor
// benjaminObject.location='312, Herbert Mercauly way, sabo, Yaba, Lagos.';
// benjaminObject['gitHub']='github.com/onyedikabenjamin'
// console.log(benjaminObject);

// ===================  FOR LOOP ==============================
// for(let i=0; i<=4; i++){
//     console.log(`I'm Ben-Billion ${i}`);
// }

// for(let i=benjaminArray.length-1; i >=0; i--){
//     console.log(benjaminArray[i]);
// }

// ===================  WHILE LOOP ==============================
// let i=benjaminArray.length-1;
// while(i>=0){
//     console.log(benjaminArray[i])
//     i--
// }

let diceOutcome = Math.trunc(Math.random() * 6) + 1;
let count=0;

while(diceOutcome!==6){
    console.log(`The dice you rolled is ${diceOutcome}`);
    count++;
    diceOutcome = Math.trunc(Math.random() * 6) + 1;
}
console.log(`You rolled the dice ${count} times before you got a 6.`);