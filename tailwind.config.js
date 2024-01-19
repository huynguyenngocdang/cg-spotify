/** @type {import('tailwindcss').Config} */
module.exports = {
    content: ['src/main/resources/templates/*.html'],
    theme: {
        // ...
    },
    extend: {
        '.color-green-spotify': {
            color:'#1ed760',
        }, 'bg-green-spotify': {
            backgroundColor: '#1ed760',
        }
    },
}
