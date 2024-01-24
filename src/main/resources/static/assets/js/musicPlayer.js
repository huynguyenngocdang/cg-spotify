let audio = document.getElementById('audio');
let play = document.getElementById('play');
let stop = document.getElementById('stop');
let button = document.getElementById('play-button');

// Update the playback position in local storage on time update
// audio.addEventListener('timeupdate', function () {
//     localStorage.setItem('audioPlaybackPosition', audio.currentTime);
//     localStorage.setItem('audioSource', audio.getAttribute("src"));
// });
//
// // Set the playback position from local storage on page load
// window.addEventListener('load', function () {
//     let source = localStorage.getItem('audioSource');
//     audio.src = "/assets/song/" + source;
//     audio.load();
//     const savedPlaybackPosition = localStorage.getItem('audioPlaybackPosition');
//     if (savedPlaybackPosition !== null) {
//         audio.currentTime = parseFloat(savedPlaybackPosition);
//     }
//     audio.play();
// });

button.addEventListener("click", function () {
    if (audio.paused) {
        play.style.display = "none";
        stop.style.display = "inline";
        audio.play();
    } else {
        play.style.display = "inline";
        stop.style.display = "none";
        audio.pause();
    }
})


// let changeSongButton = document.querySelectorAll(".change-song")
//
// document.addEventListener('DOMContentLoaded', function () {
//
//     changeSongButton.addEventListener('click', function () {
//         let songId = event.target.closest('.songId').innerHTML; // Replace with the actual song ID
//         // Make an AJAX request to change the current song
//         fetch(`/api/songs/change/${songId}`)
//             .then(response => response.json())
//             .then(songDetails => {
//                 console.log('Changed Song:', songDetails);
//                 // Update the audio source
//                 audio.src = '/assets/song/' + songDetails.filename;
//                 audio.load(); // Load the new audio source
//                 audio.currentTime = 0;
//                 audio.play(); // Start playing
//
//                 // Display current song details in the div
//                 displayCurrentSongDetails(songDetails);
//             })
//             .catch(error => {
//                 console.error('Error:', error);
//             });
//
//
//         // Function to display current song details in the div
//         function displayCurrentSongDetails(songDetails) {
//             let title = document.getElementById("songTitle");
//             let artist = document.getElementById("songArtist");
//             title.innerHTML = songDetails.title;
//             artist.innerHTML = songDetails.artist;
//         }
//     })
// })