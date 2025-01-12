"use strict";
let btn_prev = document.querySelector('.banner__buttons--left');
let btn_next = document.querySelector('.banner__buttons--right');
let images = document.querySelectorAll('.container__banner--image img');
let imageIndex = 0;

function slider(){
    if (imageIndex < images.length - 1) {
        images[imageIndex].style.display = 'none';
        imageIndex++;
        images[imageIndex].style.display = 'block';
    } else {
        images[imageIndex].style.display = 'none';
        imageIndex = 0;
        images[imageIndex].style.display = 'block';
    }
}

btn_next.onclick = slider;

btn_prev.onclick = function() {
    if(imageIndex == 0){
        images[imageIndex].style.display = 'none';
        imageIndex = images.length - 1;
        images[imageIndex].style.display = 'block';
    }
    else if (imageIndex > images.length - 1 || imageIndex < 0) {
        images[imageIndex].style.display = 'none';
        imageIndex = 0;
        images[imageIndex].style.display = 'block';
    } else {
        images[imageIndex].style.display = 'none';
        imageIndex--;
        images[imageIndex].style.display = 'block';
    }
}

setInterval(slider, 6000);
