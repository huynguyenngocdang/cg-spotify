let userIcon = document.getElementById('user-icon');
let accountOverlay = document.getElementById('account-overlay');

function accountOverlayDisplay() {

    if (accountOverlay.style.display === "flex") {
        accountOverlay.style.display = "none";
    } else {
        accountOverlay.style.display = "flex";
    }
}

userIcon.addEventListener("click", accountOverlayDisplay);
document.addEventListener('click', function(event) {
    let targetElement = event.target;

    // Check if the clicked element is not the target element or its descendant
    if (!accountOverlay.contains(targetElement) && !userIcon.contains(targetElement)) {
        // The click occurred outside the specified element
        accountOverlay.style.display = "none"
    }
});