/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    extend: {
      spacing: {
        default: '4rem',
        'default-sm': '2rem',
        'default-xs': '1rem',
        'default-lg': '6rem',
        'default-xl': '8rem',
        'default-2xl': '16rem',
      },
      padding: {
        default: '1rem',
        'default-sm': '0.5rem',
        'default-xs': '0.25rem',
        'default-lg': '2rem',
        'default-xl': '4rem',
        'default-2xl': '8rem',
      },
      colors: {
        default: '#00052E',
        'default-900': '#000421',
        'default-400': '#00094F',
        primary: '#E6A410',
        'primary-100': '#F4C560',
        'primary-400': '#F0B22A',
        'primary-700': '#C48A0E',
        'primary-900': '#835C09',
        secondary: '#144580',
        'secondary-100': '#2275D9',
        'secondary-400': '#1B5DAD',
        'secondary-700': '#0E315B',
        'secondary-900': '#0A2240',
      },
    },
  },
  plugins: [],
};
