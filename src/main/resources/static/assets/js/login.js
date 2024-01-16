let password = document.getElementById("password-login");
let eyeIcon = document.getElementById("eye-open-login");
let eyeHideIcon = document.getElementById("eye-close-login");

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