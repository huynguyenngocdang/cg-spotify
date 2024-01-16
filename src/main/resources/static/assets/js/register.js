let password = document.getElementById("password-register");
let eyeIcon = document.getElementById("eye-open-register");
let eyeHideIcon = document.getElementById("eye-close-register");

function togglePasswordVisibility() {
    if (password.type === "password") {
        password.type = "text";
        eyeIcon.style.visibility = "hidden";
        eyeHideIcon.style.visibility = "visible";
    } else {
        password.type = "password";
        eyeIcon.style.visibility = "visible";
        eyeHideIcon.style.visibility = "hidden";
    }
}

eyeIcon.addEventListener("click", togglePasswordVisibility);
eyeHideIcon.addEventListener("click", togglePasswordVisibility);
