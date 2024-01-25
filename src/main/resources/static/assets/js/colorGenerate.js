let colors = ["#dc148c", "#8300e6", "#0d72eb", "#1e3264", "#148908", "#e81429", "#457990", "#503750"];
let src = ["pink", "purple", "blue", "darkblue", "green", "red", "mint", "brown"];
document.addEventListener("DOMContentLoaded", function () {
    const elements = document.querySelectorAll(".color-random");
    const images = document.querySelectorAll(".genre-img");
    let j =0;
    for (let i = 0; i < elements.length; i++) {
        if (j === colors.length) j = 0;
        elements[i].style.backgroundColor = colors[j];
        images[i].src = "/assets/img/genre/" + src[j] + ".jpg";
        j++;
    }
});