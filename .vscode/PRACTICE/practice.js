'use strict'

const openModalBtn = document.querySelectorAll('.show-modal');
const modal = document.querySelector('.modal');

for(let i=0; i<=openModalBtn.length; i++) 
openModalBtn[i].addEventListener('click', function(){
modal.classList.remove('hidden')
});
