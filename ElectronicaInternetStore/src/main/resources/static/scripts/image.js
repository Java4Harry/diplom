"use strict";

document.querySelector('input[type="file"]').addEventListener('change', function() {
    let file_image = this.files[0].name;
    if(file_image != ""){
        document.getElementById("choose_file").innerHTML = '<span class="input-file-choose">Выбранный файл: ' + file_image + '</span>';
    }
});