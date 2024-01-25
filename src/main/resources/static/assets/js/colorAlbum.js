document.addEventListener('DOMContentLoaded', function () {
    const img = document.getElementById('album-image');
    const colorContainer = document.getElementById('color-container');

    const colorThief = new ColorThief();

    img.onload = function () {
        // Get the dominant color
        const dominantColor = colorThief.getColor(img);

        // Apply the dominant color to the background of the div
        // colorContainer.style.backgroundColor = `rgb(${dominantColor[0]}, ${dominantColor[1]}, ${dominantColor[2]})`;
        colorContainer.style.background = `linear-gradient(to bottom, rgba(${dominantColor[0]}, ${dominantColor[1]}, ${dominantColor[2]}, 1) 0%, rgba(${dominantColor[0]}, ${dominantColor[1]}, ${dominantColor[2]}, 0.5) 7%, rgba(255, 255, 255, 0.07) 25%)`;
    };
});
