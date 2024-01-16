let colors = ["#dc148c", "#8300e6", "#0d72eb", "#1e3264", "#148908", "#e81429", "#457990"];

document.addEventListener("DOMContentLoaded", function() {
    const elements = document.querySelectorAll(".color-random");
    for (let i = 0; i < elements.length; i++) {
        elements[i].style.backgroundColor = colors[i];
        // if (i === elements.length - 1) {
        //     i = 0;
        // }
    }
});