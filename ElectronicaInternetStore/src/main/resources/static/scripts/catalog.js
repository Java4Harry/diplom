"use strict";

let cat_visible = document.querySelector('.catalog-visibility');
let cat_click = document.querySelector('.container__headerMenu--catalog');

cat_click.onclick = function() {
    if (cat_visible.style.display == 'block'){
        cat_visible.style.display = 'none';
    } else {
        cat_visible.style.display = 'block';
    }
};

/*/!*Ваниант №2*!/
let cat_visible = document.querySelector('.catalog-visibility');

let cat_click = document.querySelector('.container__headerMenu--catalog');
cat_click.addEventListener('mouseover', function (event){
    cat_visible.style.display = 'block';
});

cat_visible.addEventListener('mouseover', function (event){
    cat_visible.style.display = 'block';
});

cat_click.addEventListener('mouseout', function (event){
    cat_visible.style.display = 'none';
});

cat_visible.addEventListener('mouseout', function (event){
    cat_visible.style.display = 'none';
});*/



