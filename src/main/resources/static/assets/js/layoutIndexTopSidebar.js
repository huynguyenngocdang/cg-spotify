// let homeIcon = document.getElementsByClassName("home-icon");
// let searchIcon = document.getElementsByClassName("search-icon");
// let isHomeClick = false;
//
// function homeClick() {
//     let searchCurrentClass = searchIcon.getAttribute("class");
//     if (!isHomeClick) {
//         isHomeClick = true;
//     }
//     homeIcon.style.color = "white";
//     searchIcon.setAttribute("class", searchCurrentClass + " " + "bg-white-5")
// }
//
// function searchClick() {
//     let homeCurrentClass = homeIcon.getAttribute("class");
//     if (isHomeClick) {
//         isHomeClick = false;
//     }
//     searchIcon.style.color = "white";
//     homeIcon.setAttribute("class", homeCurrentClass + " " + "bg-white-5")
// }
//
// if(isHomeClick) {
//
// }
//
// homeIcon.addEventListener("click", homeClick);
// searchIcon.addEventListener("click", searchClick);
function toggleActiveClass(element) {
    element.style.color="white";
}