/** @type {import('tailwindcss').Config} */
module.exports = {
    content: ['*'],
    theme: {
      extend: {
        colors: {
          primary: '#121A21',
          secondary: '#94ADC7',
          accent: '#1975E3',
        }
      }
    },
    plugins: [
      require('daisyui'),
    ],
  }