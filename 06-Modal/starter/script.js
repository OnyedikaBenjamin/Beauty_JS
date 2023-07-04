'use strict';

const modal = document.querySelector('.modal');  // The modal class here is the hidden class, so here we just assigned it to a variable
const overlay = document.querySelector('.overlay');
const btnCloseModal = document.querySelector('.close-modal');
const btnsOpenModal = document.querySelectorAll('.show-modal');

    const openModal = function(){
        modal.classList.remove('hidden')         // This brings out the hidden class. Remember not to use the dot operator to call the hidden class in this case
        overlay.classList.remove('hidden')      // This makes the background outside the modal to be blury, The whole logic behind this is in the css
    }                                          // modal.style.display = 'block';  this will also bring out the modal class    
    const closeModal = function(){                        // This function closes the modal
    modal.classList.add('hidden'); 
    overlay.classList.add('hidden');
    }

    btnsOpenModal.forEach(element => element.addEventListener('click', openModal)); // for each of the modal class clicked, display the hidden modal
    btnCloseModal.addEventListener('click', closeModal)  // This function closes the modal by clicking the modal close button
    overlay.addEventListener('click', closeModal)       // This function also closes the modal by clicking the background outta the modal
    
    //----------------- READING INPUT FROM KEYBOARD KEYS---------------------------
    document.addEventListener('keydown', function(e){   // e is just a variable holding all the key-board keys. If we log it into the console, it will bring a list of all the keyboard keys and their details.
        if(e.key==='Escape' && !modal.classList.contains('hidden')){
                closeModal() }}) // Close the modal if we press the ESC key
                document.querySelector