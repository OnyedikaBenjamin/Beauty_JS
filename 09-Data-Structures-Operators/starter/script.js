'use strict';
const restaurant = {
  name: 'Classico Italiano',
  location: 'Via Angelo Tavanti 23, Firenze, Italy',
  categories: ['Italian', 'Pizzeria', 'Vegetarian', 'Organic'],
  starterMenu: ['Focaccia', 'Bruschetta', 'Garlic Bread', 'Caprese Salad'],
  mainMenu: ['Pizza', 'Pasta', 'Risotto'],

  // openingHours: {
  //   thu: {
  //     open: 12,
  //     close: 22,
  //   },
  //   fri: {
  //     open: 11,
  //     close: 23,
  //   },
  //   sat: {
  //     open: 0, // Open 24 hours
  //     close: 24,
  //   },
  // },
};
// DESTRUCTURING AN ARRAY
const arr =[2,3,4];
const [x, y, z] = arr;                        
for(let i=0; i< [x,y,z].length; i++){
  console.log([x,y,z][i]);                  // Print out every element of the destructured array.     
console.log(arr);}

// If we intend to take the first two element of our resturant categories, 
const[ist, second] = restaurant.categories;
console.log(ist, second);
// If we then intend to take the first and second element of the array, 
let[first, , third] = restaurant.categories;
console.log(first, third);

// Trying to switch the values using a temporary variable
// let temp = first;
// first=third;
// third = temp;
// console.log(first, third);

// Trying to switch the values using DESTRUCTURING
[first, third] = [third, first]
console.log(first, third);

