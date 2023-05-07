/** @type {import('tailwindcss').Config} */
export default {
  content: [
    './src/*/*.{html,js,jsx}',
  ],
  theme: {
    extend: {
      spacing: {
        'df': '4rem',
        'df-sm': '2rem',
        'df-xs': '1rem',
        'df-lg': '6rem',
        'df-xl': '8rem',
        'df-2xl': '16rem',
      },
      padding: {
        'df': '1rem',
        'df-sm': '0.5rem',
        'df-xs': '0.25rem',
        'df-lg': '2rem',
        'df-xl': '4rem',
        'df-2xl': '8rem',
      },
      colors: {
        'default': '#00052E',
        'default-900': '#000421',
        'default-400': '#00094F',
        'primary': '#E6A410',
        'primary-100': '#F4C560',
        'primary-400': '#F0B22A',
        'primary-700': '#C48A0E',
        'primary-900': '#835C09',
        'secondary': '#144580',
        'secondary-100': '#2275D9',
        'secondary-400': '#1B5DAD',
        'secondary-700': '#0E315B',
        'secondary-900': '#0A2240',
        
      }
    },
  },
  plugins: [],
}